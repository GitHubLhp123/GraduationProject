package myproject.project.job.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import myproject.project.ds.entity.DataQueryLogEntity;
import myproject.project.ds.entity.TeamDsEntity;
import myproject.project.ds.repository.DataQueryLogRepository;
import myproject.project.ds.repository.TeamDsRepository;
import myproject.project.ds.server.DsServer;
import myproject.project.job.entity.JobDependEntity;
import myproject.project.job.entity.JobEntity;
import myproject.project.job.entity.JobTriggerEntity;
import myproject.project.job.repository.JobDependRepository;
import myproject.project.job.repository.JobRepository;
import myproject.project.job.repository.JobTriggerRepository;
import myproject.project.job.server.JobServer;
import myproject.project.job.server.JobTriggerServer;
import myproject.project.utils.server.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/job")
@Controller
public class JobController {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    Gson gson;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JobTriggerServer jobTriggerServer;
    @Autowired
    JobServer jobServer;
    @Autowired
    JobDependRepository jobDependRepository;
    @Autowired
    JobTriggerRepository jobTriggerRepository;

    @ResponseBody
    @PostMapping("/saveJobData")
    public Map<String, Object> saveJobData(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Gson gsonTemp = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();//为了解决时间问题

            JobEntity jobEntity = gsonTemp.fromJson(body.get("jonData").toString(), JobEntity.class);

            JobEntity jobEntityTemp = jobRepository.findByJobName(jobEntity.getJobName());
            if (jobEntityTemp == null) {
                //新创建的任务
                String url = "http://1.15.112.44:17070/job/addJob?jobClassName=@jobClassName&jobGroupName=@jobGroupName&cronExpression=@cronExpression";
                url = url.replace("@jobClassName", jobEntity.getJobName());
                url = url.replace("@jobGroupName", jobEntity.getTeamName());
                if (jobEntity.getCron() != null && !jobEntity.getCron().equals("")) {
                    url = url.replace("@cronExpression", jobEntity.getCron());
                    String resultBody = Http.httpGet(url);
                    System.out.println("========resultBody===============");
                    System.out.println(resultBody);
                }

            } else {
                if (jobEntity.getCron() != null && !jobEntity.getCron().equals(jobEntityTemp.getCron())) {
                    String url = "http://1.15.112.44:17070/job/rescheduleJob?jobClassName=@jobClassName&jobGroupName=@jobGroupName&cronExpression=@cronExpression";
                    url = url.replace("@cronExpression", jobEntity.getCron());
                    url = url.replace("@jobClassName", jobEntity.getJobName());
                    url = url.replace("@jobGroupName", jobEntity.getTeamName());
                    String resultBody = Http.httpGet(url);
                    System.out.println("========resultBody===============");
                    System.out.println(resultBody);
                }

            }

            System.out.println(jobEntity);
            jobRepository.save(jobEntity);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping({"/getJobList"})
    public Map<String, Object> getJobList(@RequestParam(value = "jobName", defaultValue = "") String jobName,
                                          @RequestParam(value = "jobLabel", defaultValue = "") String jobLabel,
                                          @RequestParam(value = "state", defaultValue = "") String state,
                                          @RequestParam(value = "teamName", defaultValue = "") String teamName,
                                          @RequestParam(value = "lastUserName", defaultValue = "") String lastUserName,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex) {
        HashMap map = new HashMap();

        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            List<JobEntity> jobEntityLIst = jobRepository.findAllByJobNameLikeAndJobLabelLikeAndStateLikeAndLastUserNameLikeAndTeamName("%" + jobName + "%", "%" + jobLabel + "%", "%" + state + "%", "%" + lastUserName + "%", teamName, pageable);
            Integer dataLength = jobRepository.countAllByJobNameLikeAndJobLabelLikeAndStateLikeAndLastUserNameLikeAndTeamName("%" + jobName + "%", "%" + jobLabel + "%", "%" + state + "%", "%" + lastUserName + "%", teamName);

            map.put("data", jobEntityLIst);
            map.put("dataLength", dataLength);
            map.put("status", 200);
        } catch (Exception var12) {
            map.put("status", 100);
            map.put("message", var12.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping({"/getJobTriggerList"})
    public Map<String, Object> getJobTriggerList(@RequestParam(value = "jobName", defaultValue = "") String jobName,
                                                 @RequestParam(value = "jobLabel", defaultValue = "") String jobLabel,
                                                 @RequestParam(value = "state", defaultValue = "") String state,
                                                 @RequestParam(value = "teamName", defaultValue = "") String teamName,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex) {
        HashMap map = new HashMap();


        System.out.println(jobName);
        System.out.println(jobLabel);
        System.out.println(state);
        System.out.println(teamName);
        System.out.println(pageSize);
        System.out.println(pageIndex);
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        List<JobTriggerEntity> jobTriggerEntityList = jobTriggerRepository.findAllByJobNameLikeAndJobLabelLikeAndStateLikeAndTeamNameAndIsValidOrderByJobName("%" + jobName + "%", "%" + jobLabel + "%", "%" + state + "%", teamName, 0, pageable);

        Integer dataLength = jobTriggerRepository.countAllByJobNameLikeAndJobLabelLikeAndStateLikeAndTeamNameAndIsValid("%" + jobName + "%", "%" + jobLabel + "%", "%" + state + "%", teamName, 0);
        try {
            map.put("data", jobTriggerEntityList);
            map.put("dataLength", dataLength);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/getJobInfoByJobName")
    public Map<String, Object> getJobInfoByJobName(@RequestParam(value = "jobName", defaultValue = "") String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JobEntity jobEntity = jobRepository.findByJobName(jobName);
            map.put("data", jobEntity);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 100);
            map.put("message", e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/getJobTriggerLog")
    public Map<String, Object> getJobTriggerLog(@RequestParam(value = "batchNo", defaultValue = "") String batchNo,
                                                @RequestParam(value = "jobName", defaultValue = "") String jobName
    ) {
        Map<String, Object> map = new HashMap<String, Object>();


        StringBuffer stringBuffer = new StringBuffer();
        String fileName = jobName + batchNo + ".log";
        stringBuffer = jobTriggerServer.getJobTriggerLog(fileName);
        map.put("data", stringBuffer);
        map.put("data2", "2222212222222");
        return map;
    }


    @ResponseBody
    @GetMapping("/getJobDepend")
    public Map<String, Object> getJobDepend(@RequestParam(value = "jobName", defaultValue = "") String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();


        List<JobDependEntity> jobDependEntityList = jobDependRepository.findAllByJobName(jobName);

        map.put("data", jobDependEntityList);

        return map;
    }

    @ResponseBody
    @GetMapping("/saveJobDepend")
    public Map<String, Object> saveJobDepend(@RequestParam(value = "jobName", defaultValue = "") String jobName,

                                             @RequestParam(value = "dependList", defaultValue = "") String dependList) {
        Map<String, Object> map = new HashMap<String, Object>();

        jobDependRepository.deleteAllByJobName(jobName);
        for (String ele : dependList.split(",")) {
            JobDependEntity jobDependEntity = new JobDependEntity();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");//uuid
            jobDependEntity.setDependId(uuid);
            jobDependEntity.setDependName(ele);
            jobDependEntity.setJobName(jobName);
            jobDependRepository.save(jobDependEntity);

        }

        map.put("data", "保存成功");

        return map;
    }

    @ResponseBody
    @GetMapping("/computedJobDepend")
    public Map<String, Object> computedJobDepend(@RequestParam(value = "inputDataList", defaultValue = "") String inputDataList) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(inputDataList);
        List<String> jobDepend = jobServer.computedJobDepend(inputDataList.split(","));
        map.put("data", jobDepend);
        return map;
    }
}

