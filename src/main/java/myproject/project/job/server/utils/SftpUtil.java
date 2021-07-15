package myproject.project.job.server.utils;


import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;


@Service
public class SftpUtil {
    static ChannelSftp sftp1;

    static {
        sftp1 = connect("1.15.112.44", 22, "root", "lhp,,123");
    }

    public static ChannelSftp connect(String host, int port, String username, String password) {

        ChannelSftp nChannelSftp = null;
        try {
            // 1.声明连接Sftp的通道

            // 2.实例化JSch
            JSch nJSch = new JSch();
            // 3.获取session
            Session nSShSession = null;
            nSShSession = nJSch.getSession(username, host, port);

            System.out.println("Session创建成功");
            // 4.设置密码
            nSShSession.setPassword(password);
            // 5.实例化Properties
            Properties nSSHConfig = new Properties();
            // 6.设置配置信息
            nSSHConfig.put("StrictHostKeyChecking", "no");
            // 7.session中设置配置信息
            nSShSession.setConfig(nSSHConfig);
            // 8.session连接
            nSShSession.connect();
            System.out.println("Session已连接");
            // 9.打开sftp通道
            Channel channel = nSShSession.openChannel("sftp");
            // 10.开始连接
            channel.connect();
            nChannelSftp = (ChannelSftp) channel;
            System.out.println("连接到主机" + host + ".");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return nChannelSftp;
    }


    public static StringBuffer download(
            int id, String fileName
    ) {

        if (sftp1 == null) {
            sftp1 = connect("1.15.112.44", 22, "root", "lhp,,123");
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            sftp1.cd("/bigdata/log/");
            InputStream ins = null;


            // 从SFTP服务器上读取指定的文件

            ins = sftp1.get(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    ins, "gbk"));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line+"\r\n");
            }
            reader.close();
            ins.close();

        } catch (Exception e) {


        }
        return stringBuffer;
    }


}
