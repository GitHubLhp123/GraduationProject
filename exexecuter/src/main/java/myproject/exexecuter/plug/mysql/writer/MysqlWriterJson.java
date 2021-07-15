package myproject.exexecuter.plug.mysql.writer;

import com.google.gson.Gson;
import myproject.exexecuter.plug.mysql.reader.MysqlReader;
import myproject.exexecuter.util.JdbcTemplateUtils;
import myproject.exexecuter.util.UtilsServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.*;

public class MysqlWriterJson {
    public MysqlWriter mysqlWriter;
    private String teamName;
    public JdbcTemplate jdbcTemplate;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MysqlWriter getMysqlWriter() {
        return mysqlWriter;
    }

    public void setMysqlWriter(MysqlWriter mysqlWriter) {
        this.mysqlWriter = mysqlWriter;
    }

    public MysqlWriterJson() {




    }

    public String toJson() {
        Writer writer = new Writer();
        writer.setName("mysqlwriter");
        Parameter parameter = new Parameter();
        parameter.setColumn(Arrays.asList(mysqlWriter.getColumns().split(",")));
        parameter.setPreSql(Arrays.asList(mysqlWriter.getPreSql()));
        Map map = getUrlAndAuth(mysqlWriter.getDsName(), mysqlWriter.getDsSchema(), getTeamName());
        Connection connection = new Connection();
        connection.setJdbcUrl(map.get("url").toString());
        connection.setTable(Arrays.asList(mysqlWriter.getTableName()));
        parameter.setConnection(Arrays.asList(connection));
        parameter.setPassword(map.get("password").toString());
        parameter.setUsername(map.get("username").toString());
        writer.setParameter(parameter);

        Gson gson = new Gson();
        gson.toJson(writer);

        return gson.toJson(writer);

    }

    public Map<String, Object> getUrlAndAuth(String dsName, String dsSchema, String teamName) {
        Map map = null;
        String sql = "select * from my_project.team_ds  where ds_name='" + dsName + "' and ds_schema like '%" + dsSchema + "%' and team_name='" + teamName + "' limit 1;";

        System.out.println(jdbcTemplate);
        map = jdbcTemplate.queryForMap(sql);


        System.out.println(map);
        String[] dsSchemas = map.get("ds_schema").toString().split(",");
        String url = "";
        for (int i = 0; i < dsSchemas.length; i++) {
            if (dsSchemas[i].equals(dsSchema)) {
                url = map.get("ds_url").toString().split("\r\n")[i];
                break;
            }
        }


        String password = null;
        try {
            password = new String(UtilsServer.decryptBasedDes(map.get("ds_password").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String username = map.get("ds_user").toString();
        map.clear();
        map.put("url", url);
        map.put("username", username);
        map.put("password", password);


        return map;
    }
}
