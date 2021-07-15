package myproject.exexecuter.mail.server;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Stmp {
    static Transport ts = null;
    static Session session = null;

    static {
        init();
    }

    public static void init() {
        try {
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            System.out.println("================初始化===");
            Properties prop = new Properties();
            prop.setProperty("mail.host", "smtp.qq.com");  //设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
            prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

            // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //1、创建定义整个应用程序所需的环境信息的 Session 对象
            //使用QQ邮箱的时候才需要，其他邮箱不需要这一段代码
            session = Session.getDefaultInstance(prop, new Authenticator() {//获取和SMTP服务器的连接对象
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、授权码
                    return new PasswordAuthentication("2446268838@qq.com", "zuugchwmpcjjeafe");
                }
            });

            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);

            //2、通过session得到transport对象(和SMTP服务器的连接对象获取发送邮件的传输对象)

            ts = session.getTransport();

            ts.connect("smtp.qq.com", "2446268838@qq.com", "zuugchwmpcjjeafe");
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        close();
    }

    public static void sendMail(String[] mails, String content) {
        try {
            if (ts == null && session == null) {
                init();
            }
            Address[] addresses = new Address[mails.length];
            for (int index = 0; index < mails.length; index++) {
                if (mails[index].matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"))
                    addresses[index] = new InternetAddress(mails[index]);
            }
            if (addresses.length == 0) {
                return;
            }
            //3、使用邮箱的用户名和授权码连上SMTP邮件服务器，即登陆

            System.out.println("=====================5");
            System.out.println("=====================5");
            System.out.println("=====================5");
            System.out.println("=====================5");
            //4、创建邮件对象MimeMessage——点击网页上的写信
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress("2446268838@qq.com"));
            //指明邮件的收件人
            ;


            message.setRecipients(Message.RecipientType.TO, addresses);

            //邮件的标题
            message.setSubject("监控平台任务失败");
            //邮件的文本内容
            message.setContent("<h2 style='color:red'>" + content + "</h2>", "text/html;charset=UTF-8");
            ts.sendMessage(message, message.getAllRecipients());
            System.out.println("=====================6");
            System.out.println("=====================6");
            System.out.println("=====================6");
            System.out.println("=====================6");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static void close() {
        //5、发送邮件——在网页上点击发送按钮

        //6、关闭连接对象，即关闭服务器上的连接资源
        try {
            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}