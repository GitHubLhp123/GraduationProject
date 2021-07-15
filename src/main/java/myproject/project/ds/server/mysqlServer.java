package myproject.project.ds.server;

import myproject.project.ds.entity.DataQueryLogEntity;
import myproject.project.ds.entity.TeamDsEntity;

import myproject.project.utils.controller.UtilsServer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class mysqlServer {
    public BasicDataSource dataSource=null;
    public JdbcTemplate getMyslJdbcTemplate(DataQueryLogEntity dataQueryLogEntity, TeamDsEntity teamDsEntity) {
        String[] dsSchemas = teamDsEntity.getDsSchema().split(",");
        String url = "";
        for (int i = 0; i < dsSchemas.length; i++) {
            if (dsSchemas[i].equals(dataQueryLogEntity.getDsSchema())) {
                url = teamDsEntity.getDsUrl().split("\r\n")[i];
            }
        }
         dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        String password = null;
        try {
            password = new String(UtilsServer.decryptBasedDes(teamDsEntity.getDsPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataSource.setUrl(url);
        dataSource.setUsername(teamDsEntity.getDsUser());
        dataSource.setPassword(password.toString());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }


    public List<Object> runSql(JdbcTemplate jdbcTemplate, DataQueryLogEntity dataQueryLogEntity) {
        List<Object> list = new ArrayList<Object>();
        String[] runSqls = dataQueryLogEntity.getRunSql().split(";");//;不严谨
        for (String runSql : runSqls) {
            runSql = runSql.trim().toLowerCase();
            if (runSql.equals("")) {

            } else if (runSql.startsWith("select") || runSql.startsWith("desc") || runSql.startsWith("show")) {
                list.add(getResultData(jdbcTemplate, runSql));
            } else if (runSql.startsWith("update")) {
                list.add(getUpdateData(jdbcTemplate, runSql));

            } else {
                list.add(getExecuteData(jdbcTemplate, runSql));
            }
        }
        return list;
    }

    public Map<String, Object> getExecuteData(JdbcTemplate jdbcTemplate, String runSql) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!runSql.equals("") && runSql.length() > 0) {
                map.put("sql", runSql);
                long start = System.currentTimeMillis();
                jdbcTemplate.execute(runSql);
                long over = System.currentTimeMillis();
                map.put("executeTime", (over - start) + " ms");//查询耗时
                map.put("data", "执行成功");//查询耗时
                map.put("type", "ExecuteData");
            }
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> getUpdateData(JdbcTemplate jdbcTemplate, String runSql) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!runSql.equals("") && runSql.length() > 0) {
                map.put("sql", runSql);
                long start = System.currentTimeMillis();
                int successRow = jdbcTemplate.update(runSql);

                long over = System.currentTimeMillis();
                map.put("type", "UpdateData");
                map.put("executeTime", (over - start) + " ms");//查询耗时
                map.put("successRow", successRow);//查询耗时

            }
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    public Map<String, Object> getResultData(JdbcTemplate jdbcTemplate, String runsql) {
        //为了限制查询数据 增加limit

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!runsql.equals("") && runsql.length() > 0) {
                map.put("sql", runsql);
                //     runsql = updateSelectSql();
                long start = System.currentTimeMillis();
                List<Map<String, Object>> resultData = jdbcTemplate.queryForList(runsql);
                long over = System.currentTimeMillis();

                List<String> columnNames = null;
                if (resultData.size() == 0) {
                    //如果查询查询数据为空 就使用getColumnNames获得字段
                    SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(runsql);
                    SqlRowSetMetaData rowSetMetaData = sqlRowSet.getMetaData();
                    columnNames = Arrays.asList(rowSetMetaData.getColumnNames());
                } else {
                    //如果查询不为空  就解析第一个map的字段
                    columnNames = getColumnNames(resultData.get(0));
                }
                map.put("type", "ResultData");
                map.put("data", resultData);
                map.put("columnNames", columnNames);
                map.put("executeTime", (over - start) + " ms");//查询耗时

            }
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

//    public String updateSelectSql(String runSql) {
//
//        return "";
//    }

    public List<String> getColumnNames(Map<String, Object> data) {
        List<String> columnNames = new ArrayList<>();
        for (String columnName : data.keySet()) {
            columnNames.add(columnName);
        }
        return columnNames;
    }


    public static Map<String, Object> testDsDataOfMysql(TeamDsEntity teamDsEntity) {
        Map<String, Object> map = new HashMap<String, Object>();
        Connection conn = null;
        map.put("status", 200);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("连接数据库...");
            for (String url : teamDsEntity.getDsUrl().split("\r\n")) {
                String password = new String(UtilsServer.decryptBasedDes(teamDsEntity.getDsPassword()));

                System.out.println(password);


                conn = DriverManager.getConnection(url, teamDsEntity.getDsUser(), password);
                System.out.println(url);
                System.out.println(conn);
            }
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status", 100);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return map;
    }
}
