package myproject.project.utils.controller;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/utils")
@Controller
public class UtilsController {
    @ResponseBody
    @GetMapping("/getCronRecentTriggerTime")
    public Map<String, Object> getRecentTriggerTime(@RequestParam(value = "cron", defaultValue = "") String cron,
                                                    @RequestParam(value = "time", defaultValue = "5") int time) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        System.out.println("cron" + cron);
        try {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            // 这个是重点，一行代码搞定
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Date date : dates) {
                list.add(dateFormat.format(date));
            }
            map.put("data", list);
            map.put("status", 200);
        } catch (ParseException e) {
            e.printStackTrace();
            map.put("status", 100);
            map.put("message", e.getMessage());
        }
        return map;
    }
}

