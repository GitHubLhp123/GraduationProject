package myproject.exexecuter;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.Layout;


import myproject.exexecuter.util.ProcessUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
class ExexecuterApplicationTests {



    @Test
    public void execute() throws Exception {

    }


    private String generateTemJsonFile(String jobJson) {
        return "";
    }

    @Test
    void contextLoads() {
    }

}
