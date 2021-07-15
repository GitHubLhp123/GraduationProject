package myproject.exexecuter.controller;

import myproject.exexecuter.jobhandler.ExecutorJob;
import myproject.exexecuter.server.JobServer;
import myproject.exexecuter.util.JdbcTemplateUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JobServer jobServer;

    @GetMapping(value = "/addJob")
    public HashMap<String, Object> addJob(
            @RequestParam(value = "jobClassName", defaultValue = "") String jobClassName,
            @RequestParam(value = "jobGroupName", defaultValue = "") String jobGroupName,
            @RequestParam(value = "cronExpression", defaultValue = "") String cronExpression
    ) throws Exception {
        HashMap map = new HashMap<String, Object>();
        try {
            jobServer.addJob(jobClassName, jobGroupName, cronExpression);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;

    }

    @GetMapping(value = "/pauseJob")
    public HashMap<String, Object> pauseJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        HashMap map = new HashMap<String, Object>();
        try {
            jobServer.jobPause(jobClassName, jobGroupName);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @GetMapping(value = "/rescheduleJob")
    public HashMap<String, Object> rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                                                 @RequestParam(value = "jobGroupName") String jobGroupName,
                                                 @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        HashMap map = new HashMap<String, Object>();
        try {
            jobServer.rescheduleJob(jobClassName, jobGroupName, cronExpression);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @GetMapping(value = "/deleteJob")
    public HashMap<String, Object> deleteJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        HashMap map = new HashMap<String, Object>();
        try {
            jobServer.deleteJob(jobClassName, jobGroupName);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }


}
