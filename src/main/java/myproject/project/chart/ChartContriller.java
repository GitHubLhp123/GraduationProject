package myproject.project.chart;

import myproject.project.sysInfo.repository.SysInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/chart")
@Controller
public class ChartContriller {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SysInfoRepository sysInfoRepository;

    @ResponseBody
    @GetMapping("/getTransferNum")
    public Map<String, Object> getTransferNum(@RequestParam(value = "dateType", defaultValue = "daily") String dateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "";
        if (dateType.equals("YEAR")) {
            sql = "select sum(num) sum ,SUBSTR(create_dt FROM 1 FOR 4) as  'time' from jobTranNum_log  where  is_valid=0  GROUP BY time   order  by time limit 12;";
        } else if (dateType.equals("MONTH")) {
            sql = "select sum(num) sum ,SUBSTR(create_dt FROM 1 FOR 7) as  'time' from jobTranNum_log  where  is_valid=0  GROUP BY time  order  by time limit 12;";
        } else if (dateType.equals("DAILY")) {
            sql = "select sum(num) sum ,SUBSTR(create_dt FROM 1 FOR 10) as  'time' from jobTranNum_log  where  is_valid=0  GROUP BY time   order  by time limit 7;";
        } else if (dateType.equals("HOUR")) {
            sql = "select sum(num) sum ,SUBSTR(create_dt FROM 1 FOR 13) as  'time' from jobTranNum_log  where  is_valid=0  GROUP BY time   order  by time limit 24;";
        }
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        map.put("data", data);
        return map;
    }

    @ResponseBody
    @GetMapping("/getJobTriggerNum")
    public Map<String, Object> getJobTriggerNum(@RequestParam(value = "dateType", defaultValue = "daily") String dateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "";
        if (dateType.equals("YEAR")) {
            sql = "select count(1) count ,CONCAT(SUBSTR(create_dt FROM 1 FOR 4) ,',',state) flag from job_trigger  where is_valid=0 GROUP BY  flag ORDER BY SUBSTR(create_dt FROM 1 FOR 13)  desc  limit 12";
        } else if (dateType.equals("MONTH")) {
            sql = "select count(1) count ,CONCAT(SUBSTR(create_dt FROM 1 FOR 7) ,',',state) flag from job_trigger  where is_valid=0 GROUP BY  flag ORDER BY SUBSTR(create_dt FROM 1 FOR 13)  desc  limit 12";
        } else if (dateType.equals("DAILY")) {
            sql = "select count(1) count ,CONCAT(SUBSTR(create_dt FROM 1 FOR 10) ,',',state) flag from job_trigger  where is_valid=0 GROUP BY  flag ORDER BY SUBSTR(create_dt FROM 1 FOR 13) desc    limit 7";
        } else if (dateType.equals("HOUR")) {
            sql = "select count(1) count ,CONCAT(SUBSTR(create_dt FROM 1 FOR 13) ,',',state) flag from job_trigger  where is_valid=0 GROUP BY  flag ORDER BY SUBSTR(create_dt FROM 1 FOR 13)  desc  limit 24";
        }
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        map.put("data", data);
        return map;
    }

    @ResponseBody
    @GetMapping("/getSysInfoList")
    public Map<String, Object> getSysInfoList() {
        Map<String, Object> map = new HashMap<String, Object>();


        //  select  * from (select  * from sys_info order by create_dt desc ) tab group by tab.host_name;

        ;

        map.put("data", sysInfoRepository.findBySQl());


        return map;

    }


}
