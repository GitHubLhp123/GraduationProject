package myproject.project.componentAnalysis.controller;

import com.google.gson.Gson;
import myproject.project.componentAnalysis.server.MysqlAnalysisServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/componentAnalysisController")
@Controller
public class ComponentAnalysisController {
    @Autowired
    Gson gson;

    @ResponseBody
    @RequestMapping(value = "mysqlReaderAnalysis", method = RequestMethod.POST)
    public Map<String, Object> mysqlAnalysis(@RequestBody Map<String, Object> body) {
        MysqlAnalysisServer mysqlAnalysisServer = new MysqlAnalysisServer();
        System.out.println(gson.toJson(body));
        return null;
    }
}
