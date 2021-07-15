package myproject.project.config;


import com.google.gson.Gson;
import myproject.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    Gson gson;
    @Autowired
    RedisTemplate<Object, User> userRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截处理代码

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        System.out.println(request.getRequestURI());
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("Authentication-Token");
        System.out.println(token);

        if (!request.getRequestURI().contains("getUserAllInfo") && !request.getRequestURI().contains("getUserPagePermissions")) {
            if (token == null || token.equals("")) {
                String origin = request.getHeader("Origin");
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                map.put("status", 403);
                map.put("message", "请登录");
                writer.println(gson.toJson(map));
                writer.flush();
                writer.close();
                System.out.println(gson.toJson(map));
                return false;
            }
//            User user = userRedisTemplate.opsForValue().get(token);
//            if (user == null) {
//                String origin = request.getHeader("Origin");
//                response.setHeader("Access-Control-Allow-Origin", origin);
//                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//                response.setHeader("Access-Control-Max-Age", "3600");
//                response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setCharacterEncoding("UTF-8");
//                PrintWriter writer = response.getWriter();
//                map.put("status", 403);
//                map.put("message", token + "失效");
//                writer.println(gson.toJson(map));
//                writer.flush();
//                writer.close();
//                System.out.println(gson.toJson(map));
//                return false;
//            }
        }


        return true;


    }


}