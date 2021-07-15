package myproject.project.user.controller;


import myproject.project.user.entity.User;
import myproject.project.user.entity.UserInfoEntity;
import myproject.project.user.entity.UserPagePermissionsEntity;


import myproject.project.user.repository.UserInfoRepository;
import myproject.project.user.repository.UserPagePermissionsRepository;
import myproject.project.utils.controller.UtilsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserPagePermissionsRepository userPagePermissionsRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RedisTemplate<Object, User> userRedisTemplate;

    @ResponseBody
    @GetMapping("/getUserList")
    public Map<String, Object> getListByTeam() {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select * from user_info");
        map.put("data", userList);
        return map;
    }

    @ResponseBody
    @GetMapping("/getUserListByTeamName")
    public Map<String, Object> getUserListByTeamName(@RequestParam(value = "teamName", defaultValue = "") String teamName,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Map<String, Object> map = new HashMap<String, Object>();


        String sql = "select * from (select info.user_name userName ,email,phone,role,permissions , info.team_name defaultTeamName ,page.team_name teamName, DATE_FORMAT(page.create_date,'%Y-%m-%d %H:%i:%s') createDate ,state from user_info info LEFT JOIN user_page_permissions page on (info.user_name=page.user_name  and info.team_name=page.team_name)) finalyTable   ";
        String param = " where teamName='" + teamName + "' ";
        String limit = " order by createDate desc limit " + (pageSize * (pageNum - 1)) + " , " + pageSize * (pageNum) + ";";
        System.out.println(sql + param + limit);
        Integer dataLength = userPagePermissionsRepository.countAllByTeamName(teamName);

        List<Map<String, Object>> userList = jdbcTemplate.queryForList(sql + param + limit);

        map.put("dataLength", dataLength);
        map.put("data", userList);
        return map;
    }

    @ResponseBody
    @GetMapping("/getUserPagePermissions")
    public Map<String, Object> getUserPagePermissions(
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "userName", defaultValue = "") String userName
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserPagePermissionsEntity userPagePermissionsEntity = userPagePermissionsRepository.findByTeamNameAndUserName(teamName, userName);
        if (userPagePermissionsEntity == null) {
            map.put("data", "".split(","));
        } else {
            map.put("data", userPagePermissionsEntity.getPermissions().split(","));
        }

        return map;
    }

    @ResponseBody
    @GetMapping("/getUserAllInfo")
    public Map<String, Object> getUserAllInfo(
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "userPassword", defaultValue = "password") String userPassword) {
        User user = new User(userName, userPassword);

        String token = new String(UtilsServer.encryptBasedDes(user.toString()));
       // userRedisTemplate.opsForValue().set(token, user);
        Map<String, Object> map = new HashMap<String, Object>();
        List<UserPagePermissionsEntity> userPagePermissionsEntities = userPagePermissionsRepository.findAllByUserName(userName);
        map.put("pagePermissionsData", userPagePermissionsEntities);
        UserInfoEntity userInfoEntity = userInfoRepository.findByUserName(userName);
        map.put("userInfoData", userInfoEntity);
        map.put("token", token);
        return map;
    }

    @ResponseBody
    @GetMapping("/getRequestLog")
    public Map<String, Object> getRequestLog(
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Map<String, Object> map = new HashMap<String, Object>();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);//分页属性


//        map.put("data", requestLogRepositoryMongodb.findAllByUserNameOrderByCreateDt(  userName , pageable));
//        map.put("dataLength", requestLogRepositoryMongodb.countAllByUserNameOrderByCreateDt(  userName,  url));
        return map;
    }

    @ResponseBody
    @GetMapping("/saveUserPermissions")
    public Map<String, Object> saveUserPermissions(
            @RequestParam(value = "teamName", defaultValue = "") String teamName,
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "role", defaultValue = "") String role,
            @RequestParam(value = "state", defaultValue = "") String state,
            @RequestParam(value = "permissions", defaultValue = "") String permissions
    ) {
        System.out.println(permissions);
        Map<String, Object> map = new HashMap<String, Object>();

        UserPagePermissionsEntity userPagePermissionsEntity = userPagePermissionsRepository.findByTeamNameAndUserName(teamName, userName);
        userPagePermissionsEntity.setRole(role);
        userPagePermissionsEntity.setState(state);
        userPagePermissionsEntity.setPermissions(permissions);
        userPagePermissionsRepository.save(userPagePermissionsEntity);
        return map;
    }

}
