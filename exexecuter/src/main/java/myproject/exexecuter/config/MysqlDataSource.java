package myproject.exexecuter.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class MysqlDataSource {
    public static BasicDataSource getJobDB(String dbName,String user,String password) {
        BasicDataSource dataSource = null;
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://1.15.112.44:3306/"+dbName+"?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
