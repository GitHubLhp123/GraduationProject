package myproject.exexecuter.plug.sftp;

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

public class SftpToSftp implements Runnable {

    private SftpToSftpMeta sftpToSftpMeta;
    private ExecuteLog executeLog;
    JdbcTemplate jdbcTemplate;
    private String filePath;
    List<String> errorFileNameList;

    public SftpToSftp(SftpToSftpMeta sftpToSftpMeta, ExecuteLog executeLog, JdbcTemplate jdbcTemplate, String filePath, List<String> errorFileNameList) {
        this.sftpToSftpMeta = sftpToSftpMeta;
        this.executeLog = executeLog;
        this.jdbcTemplate = jdbcTemplate;
        this.filePath = filePath;
        this.errorFileNameList = errorFileNameList;
    }

    public SftpToSftp(SftpToSftpMeta sftpToSftpMeta, ExecuteLog executeLog, JdbcTemplate jdbcTemplate) {
        this.sftpToSftpMeta = sftpToSftpMeta;
        this.executeLog = executeLog;
        this.jdbcTemplate = jdbcTemplate;
        handle();
    }

    int corePoolSize = 5;
    int maximumPoolSize = 5;
    long keepAliveTime = 3000;

    ThreadPoolExecutor exec = null;

    ChannelSftp sourceChannel = null;
    ChannelSftp targetChannel = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    Session sshSession = null;

    private static Semaphore semaphore = new Semaphore(1);


    protected int handle() {
        errorFileNameList =Collections.synchronizedList(new ArrayList<String>());
        exec = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        try {
            Set<String> files = getDbFiles();
            if (files.size() == 0) {
                executeLog.log("??????????????????");
                return 0;
            }
            Iterator<String> it = files.iterator();
            exec.prestartAllCoreThreads();
            while (it.hasNext()) {
                exec.execute(new SftpToSftp(sftpToSftpMeta, executeLog, jdbcTemplate, it.next(),errorFileNameList));
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
            executeLog.log("????????????????????????" + queueSize);
            int activeCount = exec.getActiveCount();
            executeLog.log("????????????????????????" + activeCount);
            long completedTaskCount = exec.getCompletedTaskCount();
            executeLog.log("????????????????????????" + completedTaskCount);
            long taskCount = exec.getTaskCount();
            executeLog.log("???????????????" + taskCount);
//
//            if (!FilesContext.getInstance().getIsConnected()) {
//                return 1;
//            }
            if (errorFileNameList.size() > 0) {
                executeLog.log("INFO:???????????????????????????:");
                for (int i = 0; i < errorFileNameList.size(); i++) {
                    executeLog.log("INFO:" + String.format("????????? [%s]", Thread.currentThread().getName()));
                    executeLog.log("WARN:       " + errorFileNameList.get(i));
                }

            }
            return 0;
        } catch (Exception e) {
            executeLog.log("ERROR:????????????????????????:" + e.getMessage());
            return 1;
        }
    }

    protected Set<String> getDbFiles() throws Exception {
        Set<String> flies = new HashSet<>();
        String[] arrayList = sftpToSftpMeta.getFileNameList().split(",");
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
                executeLog.log("ERROR" + String.format("??????????????? [%s] ?????? : [%s]", hostName, e.getMessage()));
            }
        }
        if (hostInfoMap == null || hostInfoMap.size() == 0) {
            executeLog.log("ERROR" + String.format("????????????????????? [%s] ???????????????", hostName));
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
            executeLog.log("ERROR" + String.format("????????? [%s] ???????????? :[%s]", dsInfo.get("ds_inst_loc").toString().split(":")[0], e.getMessage()));
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


    @Override
    public void run() {
        String outPutFilePath = "";
        try {
            sourceChannel = getChannel(getHostConfig(sftpToSftpMeta.getSourceDsName()));
            targetChannel = getChannel(getHostConfig(sftpToSftpMeta.getTargetDsName()));
            inputStream = sourceChannel.get(filePath);
            if (filePath.startsWith("/")) {
                outPutFilePath = sftpToSftpMeta.getTargetFilePath() + filePath;
            } else {
                outPutFilePath = sftpToSftpMeta.getTargetFilePath() + "/" + filePath;
            }
            String outPutFileDir = outPutFilePath.substring(0, outPutFilePath.lastIndexOf("/"));
            lstatDir(targetChannel, outPutFileDir);
            executeLog.log("INFO" + String.format("????????? [%s] ???????????????????????? [%s] ????????? [%s]", Thread.currentThread().getName(), filePath, outPutFilePath));
            outputStream = targetChannel.put(outPutFilePath, ChannelSftp.OVERWRITE);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteread);
            }
            executeLog.log("INFO" + String.format("????????? [%s] ?????? [%s] ??????", Thread.currentThread().getName(), filePath, outPutFilePath));
        } catch (SftpException | IOException e) {
            errorFileNameList.add(filePath);
            executeLog.log("ERROR" + String.format("??????????????? [%s] ?????? :[%s]", filePath, e.getMessage()));
        } finally {
            try {
                closeIO();
            } catch (IOException e) {
                executeLog.log("ERROR" + String.format("????????? [%s] ??????????????????", filePath));
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
                // ftp server???????????????????????????,????????????????????????
                for (String dirName : dirSplit) {
                    dirPath.append(dirName);
                    mkDirSingleHierarchy(targetChannel, dirPath.toString());
                    dirPath.append("/");
                }
            } catch (SftpException e) {
                String message = String
                        .format("????????????:%s?????????I/O??????,????????????ftp????????????????????????,????????????????????????, errorMessage:%s",
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
                executeLog.log("INFO" + String.format("???????????????????????? [%s]", directoryPath));
                targetChannel.mkdir(directoryPath);
                return true;
            }
        }
        if (!isDirExist) {
            executeLog.log("INFO" + String.format("???????????????????????? [%s]", directoryPath));
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
        if (sourceChannel != null) {
            sourceChannel.disconnect();
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
