package myproject.exexecuter.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;


public class JdbcTemplateUtils implements Closeable {
    BasicDataSource dataSource = null;

    public JdbcTemplate getMyslJdbcTemplate() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://1.15.112.44:3306/mysql?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("lhp,,123");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }


    @Override
    public void close() throws IOException {
        try {
            System.out.println("close");
            System.out.println("close");
            System.out.println("close");
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
