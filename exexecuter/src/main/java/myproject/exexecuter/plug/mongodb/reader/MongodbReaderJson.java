package myproject.exexecuter.plug.mongodb.reader;

import com.google.gson.Gson;

import myproject.exexecuter.util.UtilsServer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MongodbReaderJson {

    private MongodbReader mongodbReader;
    private String teamName;
    public JdbcTemplate jdbcTemplate;

    public String getTeamName() {
        return teamName;
    }

    public MongodbReader getMongodbReader() {
        return mongodbReader;
    }

    public void setMongodbReader(MongodbReader mongodbReader) {
        this.mongodbReader = mongodbReader;
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

    public String toJson() {
        Reader reader = new Reader();
        reader.setName("mongodbreader");
        Parameter parameter = new Parameter();
        parameter.setColumn(mongodbReader.getColumn());
        Map map = getUrlAndAuth(mongodbReader.getDsName(), mongodbReader.getDsSchema(), getTeamName());
        List urllist = new ArrayList<String>();
        urllist.add(map.get("url").toString());
        parameter.setAddress(urllist);
        parameter.setCollectionName(mongodbReader.getCollectionName());
        parameter.setDbName(mongodbReader.getDsSchema());//注意
        parameter.setUserName(map.get("username").toString());
        parameter.setUserPassword(map.get("password").toString());
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
        url = map.get("ds_inst_loc").toString();
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
