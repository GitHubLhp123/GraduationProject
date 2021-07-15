package myproject.exexecuter.plug.mysql.reader;

import com.google.gson.Gson;
import myproject.exexecuter.util.JdbcTemplateUtils;

import myproject.exexecuter.util.UtilsServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MysqlReaderJson {

    private MysqlReader mysqlReader;
    private String  teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public MysqlReader getMysqlReader() {
        return mysqlReader;
    }
    public  JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setMysqlReader(MysqlReader mysqlReader) {
        this.mysqlReader = mysqlReader;
    }


    public String toJson() {
        Reader reader = new Reader();
        reader.setName("mysqlreader");
        Parameter parameter = new Parameter();
        parameter.setColumn(Arrays.asList(mysqlReader.getColumns().split(",")));
        Map map = getUrlAndAuth(mysqlReader.getDsName(), mysqlReader.getDsSchema(), getTeamName());
        Connection connection = new Connection();
        connection.setJdbcUrl(Arrays.asList(map.get("url").toString()));
        connection.setTable(Arrays.asList(mysqlReader.getTableName()));
        parameter.setConnection(Arrays.asList(connection));
        parameter.setPassword(map.get("password").toString());
        parameter.setUsername(map.get("username").toString());
        reader.setParameter(parameter);
        Gson gson = new Gson();
        gson.toJson(reader);
        return gson.toJson(reader);

    }

    public Map<String, Object> getUrlAndAuth(String dsName, String dsSchema, String teamName) {
        Map map = null;
        String sql = "select * from my_project.team_ds  where ds_name='" + dsName + "' and ds_schema like '%" + dsSchema + "%' and team_name='" + teamName + "' limit 1;";
        System.out.println("=====================");
        System.out.println(sql);
        map = jdbcTemplate.queryForMap(sql);
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
