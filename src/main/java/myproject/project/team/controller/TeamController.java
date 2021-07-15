package myproject.project.team.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import myproject.project.ds.repository.TeamDsRepository;
import myproject.project.model.entity.ModelEntity;
import myproject.project.model.entity.ModelFieldEntity;
import myproject.project.model.repository.ModelFieldRepository;
import myproject.project.model.repository.ModelRepository;
import myproject.project.team.entity.TeamLevelEntity;
import myproject.project.team.entity.TeamPlugEntity;
import myproject.project.team.repository.TeamLevelRepository;
import myproject.project.team.repository.TeamPlugRepository;
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

@RequestMapping("/team")
@Controller
public class TeamController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TeamLevelRepository teamLevelRepository;
    @Autowired
    TeamPlugRepository teamPlugRepository;
    @Autowired
    Gson gson;


    @ResponseBody
    @PostMapping("/saveTeamLevel")
    public Map<String, Object> saveTeamLevel(
            @RequestBody Map<String, Object> body) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("====================");
        System.out.println(body.get("levelData"));
        teamLevelRepository.deleteAllByTeamName(body.get("teamName").toString());
        String teamName = body.get("teamName").toString();

        List<TeamLevelEntity> teamLevelEntities = gson.fromJson(body.get("levelData").toString(), new TypeToken<List<TeamLevelEntity>>() {
        }.getType());

        for (TeamLevelEntity ele : teamLevelEntities) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");//uuid
            ele.setId(uuid);
            ele.setTeamName(teamName);
        }


        teamLevelRepository.saveAll(teamLevelEntities);

        return map;
    }

    @ResponseBody
    @GetMapping("/getTeamLevel")
    public Map<String, Object> getTeamLevel(
            @RequestParam(value = "teamName", defaultValue = "") String teamName
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TeamLevelEntity> teamLevelEntities = teamLevelRepository.findByTeamName(teamName);
        map.put("data", teamLevelEntities);
        return map;
    }

    @ResponseBody
    @GetMapping("/getTeamPlug")
    public Map<String, Object> getTeamPlug(
            @RequestParam(value = "teamName", defaultValue = "") String teamName
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TeamPlugEntity> teamPlugEntities = teamPlugRepository.findByTeamName(teamName);
        map.put("data", teamPlugEntities);
        return map;
    }

    @ResponseBody
    @GetMapping("/deletePlugById")
    public Map<String, Object> deletePlugById(
            @RequestParam(value = "id", defaultValue = "") String id
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        teamPlugRepository.deleteById(id);
        map.put("data", "success");
        return map;
    }

    @ResponseBody
    @GetMapping("/addPlug")
    public Map<String, Object> addPlug(
            @RequestParam(value = "id", defaultValue = "") String id
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        teamPlugRepository.deleteById(id);
        map.put("data", "success");
        return map;
    }
}
