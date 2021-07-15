package myproject.project.aop;



import myproject.project.user.entity.RequestLogEntityMongodb;


import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.*;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wuwf on 17/4/27.
 * 日志切面
 */
@Aspect
@Component
public class RequestLogAspect {

//    @Autowired
//    private static Mars mars;

    @Pointcut("execution(public * myproject.project.*.controller.*.*(..))")
    public void RequestLog() {
    }

    @Before("RequestLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("RequestLog");
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            RequestLogEntityMongodb requestLogEntityMongodb=new RequestLogEntityMongodb();
            String token = request.getHeader("Authentication-Token");
            requestLogEntityMongodb.setUrl(request.getRequestURL().toString());
            requestLogEntityMongodb.setRequestWay(request.getMethod());
            requestLogEntityMongodb.setIp(request.getRemoteAddr());
            requestLogEntityMongodb.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            requestLogEntityMongodb.setArgs(Arrays.toString(joinPoint.getArgs()));
            requestLogEntityMongodb.setId(UUID.randomUUID().toString());
            requestLogEntityMongodb.setTeamName("");
            requestLogEntityMongodb.setCreateDt(new Date());
//            mars.save(requestLogEntityMongodb);
        }catch (Exception e){
           System.out.println(e.getMessage());
        }

    }

//    @AfterReturning(returning = "ret", pointcut = "RequestLog()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        System.out.println("方法的返回值 : " + ret);
//    }
//
//    //后置异常通知
//    @AfterThrowing("RequestLog()")
//    public void throwss(JoinPoint jp) {
//        System.out.println("方法异常时执行.....");
//    }
//
//    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
//    @After("RequestLog()")
//    public void after(JoinPoint jp) {
//        System.out.println("方法最后执行.....");
//    }
//
//    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("RequestLog()")
//    public Object arround(ProceedingJoinPoint pjp) {
//        System.out.println("方法环绕start.....");
//        try {
//            Object o = pjp.proceed();
//            System.out.println("方法环绕proceed，结果是 :" + o);
//            return o;
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}