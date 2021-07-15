package myproject.project.ds.server;

import com.jcraft.jsch.*;
import myproject.project.ds.entity.TeamDsEntity;
import myproject.project.utils.controller.UtilsServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class sftpServer {
    public static Map<String, Object> testDsDataOfSftp(TeamDsEntity teamDsEntity) {
        Map<String, Object> map = new HashMap<String, Object>();
        ChannelSftp nChannelSftp = null;
        Session nSShSession = null;
        Channel channel = null;
        map.put("status", 200);
        try {
            String password = new String(UtilsServer.decryptBasedDes(teamDsEntity.getDsPassword()));
            System.out.println(password);
            String username = teamDsEntity.getDsUser();
            String host = teamDsEntity.getDsInstLoc().split(":")[0];
            int port = Integer.parseInt(teamDsEntity.getDsInstLoc().split(":")[1]);
            JSch nJSch = new JSch();
            // 3.获取session
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
            channel = nSShSession.openChannel("sftp");
            // 10.开始连接
            channel.connect();
            nChannelSftp = (ChannelSftp) channel;
            System.out.println("连接到主机" + host + ".");

        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status", 100);
        } finally {
            try {
                channel.disconnect();
                nSShSession.disconnect();
                nChannelSftp.disconnect();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return map;
    }
}
