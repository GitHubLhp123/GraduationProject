package myproject.exexecuter.plug.oss;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.jcraft.jsch.*;
import myproject.exexecuter.log.ExecuteLog;
import myproject.exexecuter.util.AesCipher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class OssToSftp implements Runnable {

    private OssToSftpMeta ossToSftpMeta;
    private ExecuteLog executeLog;
    JdbcTemplate jdbcTemplate;
    private String filePath;
    List<String> errorFileNameList;

    public OssToSftp(OssToSftpMeta ossToSftpMeta, ExecuteLog executeLog, JdbcTemplate jdbcTemplate, String filePath, List<String> errorFileNameList) {
        this.ossToSftpMeta = ossToSftpMeta;
        this.executeLog = executeLog;
        this.jdbcTemplate = jdbcTemplate;
        this.filePath = filePath;
        this.errorFileNameList = errorFileNameList;
    }

    public OssToSftp(OssToSftpMeta ossToSftpMeta, ExecuteLog executeLog, JdbcTemplate jdbcTemplate) {
        this.ossToSftpMeta = ossToSftpMeta;
        this.executeLog = executeLog;
        this.jdbcTemplate = jdbcTemplate;
        handle();
    }

    int corePoolSize = 5;
    int maximumPoolSize = 5;
    long keepAliveTime = 3000;

    ThreadPoolExecutor exec = null;

    OSS ossClient = null;
    OSSObject ossObject = null;
    ChannelSftp targetChannel = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    Session sshSession = null;

    private static Semaphore semaphore = new Semaphore(1);


    protected int handle() {
        errorFileNameList = Collections.synchronizedList(new ArrayList<String>());
        exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        try {
            Set<String> files = getDbFiles();
            if (files.size() == 0) {
                executeLog.log("未查询到文件");
                return 0;
            }
            Iterator<String> it = files.iterator();
            exec.prestartAllCoreThreads();
            while (it.hasNext()) {
                exec.execute(new OssToSftp(ossToSftpMeta, executeLog, jdbcTemplate, it.next(), errorFileNameList));
            }
            exec.shutdown();
            while (true) {
                long taskCount = exec.getTaskCount();
                long completedTaskCount = exec.getCompletedTaskCount();
                if (taskCount == completedTaskCount) {
                    exec.shutdownNow();
                }
                if (exec.isTerminated()) {
                    break;
                }
                Thread.sleep(1000);
            }
            int queueSize = exec.getQueue().size();
            executeLog.log("当前排队线程数：" + queueSize);
            int activeCount = exec.getActiveCount();
            executeLog.log("当前活动线程数：" + activeCount);
            long completedTaskCount = exec.getCompletedTaskCount();
            executeLog.log("执行完成线程数：" + completedTaskCount);
            long taskCount = exec.getTaskCount();
            executeLog.log("总线程数：" + taskCount);
//
//            if (!FilesContext.getInstance().getIsConnected()) {
//                return 1;
//            }
            if (errorFileNameList.size() > 0) {
                executeLog.log("INFO:失败源文件列表如下:");
                for (int i = 0; i < errorFileNameList.size(); i++) {
                    executeLog.log("INFO:" + String.format("主线程 [%s]", Thread.currentThread().getName()));
                    executeLog.log("WARN:       " + errorFileNameList.get(i));
                }

            }
            return 0;
        } catch (Exception e) {
            executeLog.log("ERROR:主任务捕获到异常:" + e.getMessage());
            return 1;
        }
    }

    protected Set<String> getDbFiles() throws Exception {
        Set<String> flies = new HashSet<>();
        String[] arrayList = ossToSftpMeta.getFileNameList().split(",");
        for (String ele : arrayList) {
            flies.add(ele);
        }

        return flies;
    }


    protected Map getHostConfig(String hostName) {
        Map hostInfoMap = null;

        if (hostInfoMap == null) {
            try {

                hostInfoMap = jdbcTemplate.queryForMap(" select * from my_project.team_ds where ds_name='" + hostName + "'");
            } catch (Exception e) {
                executeLog.log("ERROR" + String.format("获取服务器 [%s] 失败 : [%s]", hostName, e.getMessage()));
            }
        }
        if (hostInfoMap == null || hostInfoMap.size() == 0) {
            executeLog.log("ERROR" + String.format("没找到服务器名 [%s] 的配置信息", hostName));
            return hostInfoMap;
        } else {
            return hostInfoMap;
        }
    }

    protected synchronized ChannelSftp getChannel(Map dsInfo) {
        ChannelSftp channelSftp = null;
        try {
            JSch jSch = new JSch();
            sshSession = jSch.getSession(dsInfo.get("ds_user").toString(), dsInfo.get("ds_inst_loc").toString().split(":")[0], Integer.parseInt(dsInfo.get("ds_inst_loc").toString().split(":")[1]));
            sshSession.setPassword(AesCipher.decrypt(dsInfo.get("ds_password").toString()));
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.setDaemonThread(true);
            sshSession.setTimeout(300000);
            sshSession.connect();
            channelSftp = (ChannelSftp) sshSession.openChannel("sftp");
            channelSftp.connect();
        } catch (Exception e) {
            executeLog.log("ERROR" + String.format("服务器 [%s] 连接失败 :[%s]", dsInfo.get("ds_inst_loc").toString().split(":")[0], e.getMessage()));
            if (sshSession != null) {
                sshSession.disconnect();
            }
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        } finally {
            return channelSftp;
        }
    }

    public synchronized InputStream inputStreamOfOss(Map<String, Object> dsInfo, String filePath) {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setSupportCname(false);
        String endpoint = dsInfo.get("ds_inst_loc").toString();
        String accessKeyId = dsInfo.get("ds_user").toString();
        String accessKeySecret = AesCipher.decrypt(dsInfo.get("ds_password").toString());
        String[] fileBucketAndPath = filePath.replaceFirst("^[(/)|(\\\\)]+", "").split("[(/)|(\\)]+");
        String bucketName = fileBucketAndPath[0];
        String objectName = filePath.replaceFirst(bucketName, "").replaceFirst("^[(/)|(\\\\)]+", "");
        executeLog.log("INFO" + String.format("bucketName [%s]", bucketName) + "," + String.format("objectName [%s]", objectName));
        boolean exists = false;

        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
            exists = ossClient.doesBucketExist(bucketName);
        } catch (OSSException e) {
            errorFileNameList.add(filePath);
            executeLog.log("ERROR" + String.format("连接oss出现异常[%s]", e.getMessage()));
            return inputStream;
        }
        if (exists == true) {
            try {
                ossObject = ossClient.getObject(bucketName, objectName);
                inputStream = ossObject.getObjectContent();

            } catch (OSSException e) {
                errorFileNameList.add(filePath);
                executeLog.log("ERROR" + String.format("获取oss文件出现异常[%s]", e.getMessage()));
            }
        } else {
            errorFileNameList.add(filePath);
            executeLog.log("ERROR" + String.format("桶不存在 [%s]", bucketName));
        }
        return inputStream;

    }

    @Override
    public void run() {
        String outPutFilePath = "";
        try {
            targetChannel = getChannel(getHostConfig(ossToSftpMeta.getTargetDsName()));
            inputStream = inputStreamOfOss(getHostConfig(ossToSftpMeta.getSourceDsName()), filePath);
            if (inputStream == null) {
                executeLog.log("INFO" + "获取oss输出流失败:" + filePath);
                return;
            }

            if (filePath.startsWith("/")) {
                outPutFilePath = ossToSftpMeta.getTargetFilePath() + filePath;
            } else {
                outPutFilePath = ossToSftpMeta.getTargetFilePath() + "/" + filePath;
            }
            String outPutFileDir = outPutFilePath.substring(0, outPutFilePath.lastIndexOf("/"));
            lstatDir(targetChannel, outPutFileDir);
            executeLog.log("INFO" + String.format("子线程 [%s] 准备开始将源文件 [%s] 写入到 [%s]", Thread.currentThread().getName(), filePath, outPutFilePath));
            outputStream = targetChannel.put(outPutFilePath, ChannelSftp.OVERWRITE);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteread);
            }
            executeLog.log("INFO" + String.format("子线程 [%s] 写入 [%s] 完成", Thread.currentThread().getName(), filePath, outPutFilePath));
        } catch (SftpException | IOException e) {
            errorFileNameList.add(filePath);
            executeLog.log("ERROR" + String.format("传输源文件 [%s] 失败 :[%s]", filePath, e.getMessage()));
        } finally {
            try {
                closeIO();
            } catch (IOException e) {
                executeLog.log("ERROR" + String.format("源文件 [%s] 资源关闭异常", filePath));
            }
        }
    }

    public synchronized void lstatDir(ChannelSftp targetChannel, String directoryPath) {
        boolean isDirExist;
        try {
            semaphore.acquire();
            SftpATTRS sftpATTRS = targetChannel.lstat(directoryPath);
            isDirExist = sftpATTRS.isDir();
        } catch (SftpException | InterruptedException e) {
            isDirExist = false;
        }
        if (!isDirExist) {
            StringBuilder dirPath = new StringBuilder();
            dirPath.append("/");
            String[] dirSplit = StringUtils.split(directoryPath, "/");
            try {
                // ftp server不支持递归创建目录,只能一级一级创建
                for (String dirName : dirSplit) {
                    dirPath.append(dirName);
                    mkDirSingleHierarchy(targetChannel, dirPath.toString());
                    dirPath.append("/");
                }
            } catch (SftpException e) {
                String message = String
                        .format("创建目录:%s时发生I/O异常,请确认与ftp服务器的连接正常,拥有目录创建权限, errorMessage:%s",
                                directoryPath, e.getMessage());
                executeLog.log("ERROR" + message);
            }
        }
        semaphore.release();
    }

    public synchronized boolean mkDirSingleHierarchy(ChannelSftp targetChannel, String directoryPath) throws SftpException {
        boolean isDirExist = false;
        try {
            SftpATTRS sftpATTRS = targetChannel.lstat(directoryPath);
            isDirExist = sftpATTRS.isDir();
        } catch (SftpException e) {
            if (!isDirExist) {
                executeLog.log("INFO" + String.format("正在逐级创建目录 [%s]", directoryPath));
                targetChannel.mkdir(directoryPath);
                return true;
            }
        }
        if (!isDirExist) {
            executeLog.log("INFO" + String.format("正在逐级创建目录 [%s]", directoryPath));
            targetChannel.mkdir(directoryPath);
        }
        return true;
    }

    public synchronized void closeIO() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (ossObject != null) {
            ossObject.close();
        }
        if (targetChannel != null) {
            targetChannel.disconnect();
        }
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }

//    static class FilesContext {
//
//        private static volatile FilesContext instance;
//
//        public static synchronized FilesContext getInstance() {
//            if (instance == null) {
//                synchronized (FilesContext.class) {
//                    if (instance == null) {
//                        instance = new FilesContext();
//                    }
//                }
//            }
//            return instance;
//        }
//
//        private static List<String> files = new ArrayList<>();
//
//        public void addFiles(String file) {
//            files.add(file);
//        }
//
//        public List<String> getFiles() {
//            return files;
//        }
//
//        private static boolean isConnected = true;
//
//        public void setIsConnected(boolean flag) {
//            isConnected = flag;
//        }
//
//        public Boolean getIsConnected() {
//            return isConnected;
//        }
//    }
}
