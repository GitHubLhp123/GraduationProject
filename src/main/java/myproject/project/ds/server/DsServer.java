package myproject.project.ds.server;

import myproject.project.ds.entity.DataQueryLogEntity;
import myproject.project.ds.entity.TeamDsEntity;

import myproject.project.ds.repository.TeamDsRepository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Service
public class DsServer {
    @Autowired
    TeamDsRepository teamDsRepository;

    public TeamDsEntity getDsData(DataQueryLogEntity dataQueryLogEntity) {
        TeamDsEntity tenantDsEntity = teamDsRepository.findByTeamNameAndDsName(dataQueryLogEntity.getTeamName(), dataQueryLogEntity.getDsName());
        return tenantDsEntity;
    }


    public List<Object> checkDsTypeAndGetData(DataQueryLogEntity dataQueryLogEntity) {
        String dsType = dataQueryLogEntity.getDsType();
        List<Object> list = null;
        if (dsType.equals("mysql")) {
            mysqlServer mysqlServer = new mysqlServer();
            JdbcTemplate jdbcTemplate = mysqlServer.getMyslJdbcTemplate(dataQueryLogEntity, getDsData(dataQueryLogEntity));
            list = mysqlServer.runSql(jdbcTemplate, dataQueryLogEntity);
            try {
                mysqlServer.dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        } else if (dsType.equals("hive")) {
            hiveServer hiveServer = new hiveServer();
            JdbcTemplate jdbcTemplate = hiveServer.getHiveJdbcTemplate(dataQueryLogEntity, getDsData(dataQueryLogEntity));
            list = hiveServer.runSql(jdbcTemplate, dataQueryLogEntity);
            try {
                hiveServer.dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }


        return list;
    }

    public Map<String, Object> testDsData(TeamDsEntity teamDsEntity) {

        if (teamDsEntity.getDsType().equals("mysql")) {
            return mysqlServer.testDsDataOfMysql(teamDsEntity);
        } else if (teamDsEntity.getDsType().equals("hive")) {

            return hiveServer.testDsDataOfHive(teamDsEntity);

        } else if (teamDsEntity.getDsType().equals("sftp")) {
            return sftpServer.testDsDataOfSftp(teamDsEntity);
        } else if (teamDsEntity.getDsType().equals("oss")) {
            return ossServer.testDsDataOfOss(teamDsEntity);
        } else if (teamDsEntity.getDsType().equals("mongodb")) {
            return mongodbServer.testDsDataOfMongodb(teamDsEntity);
        } else {
            return null;
        }


    }


}
