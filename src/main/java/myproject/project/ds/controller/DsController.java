package myproject.project.ds.controller;

import com.google.gson.Gson;
import myproject.project.ds.entity.DataQueryLogEntity;
import myproject.project.ds.entity.TeamDsEntity;


import myproject.project.ds.repository.DataQueryLogRepository;
import myproject.project.ds.repository.TeamDsRepository;
import myproject.project.ds.server.DsServer;
import myproject.project.utils.controller.UtilsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RequestMapping("/ds")
@Controller
public class DsController {
    @Autowired
    TeamDsRepository teamDsRepository;
    @Autowired
    DataQueryLogRepository dataQueryLogRepository;
    @Autowired
    DsServer dsServer;
    @Autowired
    Gson gson;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/getDsListByTeamName")
    public Map<String, Object> getDsListByTeamName(
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // Pageable pageable = PageRequest.of(pageIndex - 1, 10);//分页属性
            List<TeamDsEntity> teamDsEntities = teamDsRepository.findAllByTeamName(teamName);


            System.out.println(teamDsEntities);
            map.put("data", teamDsEntities);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/getDsListByTeamNameAndDsType")
    public Map<String, Object> getDsListByTeamNameAndDsType(
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "dsType", defaultValue = "") String dsType
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<TeamDsEntity> teamDsEntities = teamDsRepository.findByTeamNameAndDsType(teamName, dsType);
            map.put("data", teamDsEntities);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping("/getDsByDsId")
    public Map<String, Object> getDsByDsId(
            @RequestParam(value = "dsId", defaultValue = "") String dsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("data", teamDsRepository.findByDsId(dsId));
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping("/getRunSqlLog")
    public Map<String, Object> getRunSqlLog(
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "runSql", defaultValue = "") String runSql,
            @RequestParam(value = "runTime", defaultValue = "") String runTime,
            @RequestParam(value = "dsName", defaultValue = "") String dsName,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex

    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String sqlParam = "";
            String sql = "select d.log_id ,d.ip_address ipAddress, d.team_name teamName ,d.ds_name dsName,d.ds_schema dsSchema,d.ds_type dsType ,d.run_sql runSql,DATE_FORMAT(d.run_time,'%Y-%m-%d %H:%i:%s') runTime,d.result_type resultType,d.user_name userName,d.copy_content copyContent from data_query_log d  ";

            sqlParam += " where 1=1 and  ";
            sqlParam += " d.user_name like'%" + userName + "%' and";
            sqlParam += " d.ds_Name like'%" + dsName + "%' and";
            sqlParam += " d.run_Sql  like '%" + runSql + "%' and";
            sqlParam += " d.run_Time >='" + runTime + "' ";

            int dataLength = jdbcTemplate.queryForObject("select count(1) from data_query_log d  " + sqlParam, Integer.class);
            sqlParam += " order by run_Time desc limit " + (pageSize * (pageIndex - 1)) + " , " + pageSize + ";";
            System.out.println("sql===========================================" + sql + sqlParam);
            List<Map<String, Object>> runSqlLogList = jdbcTemplate.queryForList(sql + sqlParam);

//            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
//            List<DataQueryLogEntity> dataQueryLogEntityList = dataQueryLogRepository.findAllByUserNameLikeAndRunSqlLikeAndRunTimeLikeAndDsNameLike("%" + userName + "%", "%" + runSql + "%", "%" + runTime + "%", "%" + dsName + "%", pageable);
//            System.out.println("======================================");
//            System.out.println(dataQueryLogEntityList);
            map.put("status", 200);
            map.put("data", runSqlLogList);
            map.put("dataLength", dataLength);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @PostMapping(value = "runSql")
    public Map<String, Object> runSql(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        DataQueryLogEntity dataQueryLogEntity = gson.fromJson(body.get("runSqlData").toString(), DataQueryLogEntity.class);
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            dataQueryLogEntity.setRunSql("select * from model limit 30;show tables;");
        }
        try {
            dataQueryLogEntity.setLogId(UUID.randomUUID().toString().trim().replaceAll("-", ""));
            List<Object> list = dsServer.checkDsTypeAndGetData(dataQueryLogEntity);
            dataQueryLogEntity.setResultType("success");
            dataQueryLogEntity.setIpAddress(request.getRemoteAddr());
            Timestamp ts = new Timestamp(new Date().getTime());
            dataQueryLogEntity.setRunTime(ts);
            map.put("data", list);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
            dataQueryLogEntity.setResultType("false");
        } finally {
            dataQueryLogRepository.save(dataQueryLogEntity);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/testDsConnectivity")
    public Map<String, Object> testDsConnectivity(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            TeamDsEntity teamDsEntity = gson.fromJson(body.get("dsData").toString(), TeamDsEntity.class);
            map = dsServer.testDsData(teamDsEntity);

            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody

    @PostMapping(value = "saveDs")
    public Map<String, Object> saveDs(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Gson gson = new Gson();
            TeamDsEntity teamDsEntity = gson.fromJson(body.get("dsData").toString(), TeamDsEntity.class);
            String relaId = teamDsEntity.getDsId() + "_" + teamDsEntity.getTeamName();
            teamDsEntity.setRelaId(relaId);
            teamDsRepository.save(teamDsEntity);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "encryptorDsPassword")
    public Map<String, Object> encryptorDsPassword(
            @RequestParam(value = "dsPassword", defaultValue = "") String dsPassword
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            System.out.println("==========================元数据");
            System.out.println(dsPassword);
            ;
            System.out.println("==========================加密");
            String encry = new String(UtilsServer.encryptBasedDes(dsPassword));
            System.out.println(encry);
            System.out.println("==========================解密");
            String password = new String(UtilsServer.decryptBasedDes(encry));
            System.out.println(password);


            map.put("data", encry);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
            System.out.println("message" + e.getMessage());
        }
        return map;
    }

}
