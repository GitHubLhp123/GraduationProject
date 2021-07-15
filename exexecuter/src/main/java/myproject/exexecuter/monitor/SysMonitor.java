package myproject.exexecuter.monitor;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;



import static myproject.exexecuter.monitor.SystemInfo.*;

@Service
public class SysMonitor {

    @Autowired
    Gson gson;
    @Autowired
    SysInfoRepository sysInfoRepository;
    @Scheduled(cron = "0 * * * * ?")  //每4秒执行一次
    public void hello() {
        try {

            System.out.println(System.getProperty("java.library.path"));
            System.out.println("-----------System信息，从jvm获取如下-----------------------");
            // System信息，从jvm获取
            property();
            System.out.println("------------cpu信息如下----------------------");
            // cpu信息
            cpu();
         //   System.out.println("------------内存信息如下----------------------");
            // 内存信息
            memory();
          //  System.out.println("--------------文件系统信息如下--------------------");
            // 文件系统信息
            file();
        //    System.out.println(SystemInfo.hashMap);
            System.out.println(gson.toJson(SystemInfo.hashMap));
            SysInfoEntity sysInfo = gson.fromJson(gson.toJson(SystemInfo.hashMap), SysInfoEntity.class);
            sysInfo.setId(UUID.randomUUID().toString());
            System.out.println(sysInfo);
            System.out.println(sysInfo);
            System.out.println(sysInfo);System.out.println(sysInfo);

             sysInfoRepository.save(sysInfo);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
}

