package myproject.project.plug.controller;

import com.google.gson.Gson;
import myproject.project.plug.entity.JobGraphEntity;
import myproject.project.plug.repository.JobGraphRepository;
import myproject.project.plug.repository.PlugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/plug")
@Controller
public class PlugController {
    @Autowired
    Gson gson;
    @Autowired
    JobGraphRepository jobGraphRepository;
    @Autowired
    PlugRepository plugRepository;

    @ResponseBody
    @PostMapping(value = "saveGraphData")
    public Map<String, Object> saveGraphData(@RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("body");
        System.out.println(body.get("plugData").toString());
        System.out.println(body.get("linkList").toString());
        System.out.println(body.get("jobName"));
        jobGraphRepository.deleteByJobName(body.get("jobName").toString());
        JobGraphEntity jobGraphEntity = new JobGraphEntity();
        String uuid = UUID.randomUUID().toString();
        jobGraphEntity.setId(uuid);
        jobGraphEntity.setJobName(body.get("jobName").toString());
        jobGraphEntity.setLinkData(body.get("linkList").toString());
        jobGraphEntity.setPlungData(body.get("plugData").toString());
        jobGraphRepository.save(jobGraphEntity);
        return map;
    }


    @ResponseBody
    @GetMapping(value = "getGraphData")
    public Map<String, Object> getGraphData(@RequestParam String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();
        JobGraphEntity jobGraphEntity = jobGraphRepository.findByJobName(jobName);
        map.put("data", jobGraphEntity);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "getPlugList")
    public Map<String, Object> getPlugList(@RequestParam String jobName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", plugRepository.findAll());
        return map;
    }


}
