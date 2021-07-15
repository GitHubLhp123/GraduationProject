package myproject.exexecuter.config;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;


//@Configuration
//public class SigarConfig {
//
//    //静态代码块
//    static {
//        try {
//            initSigar();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //初始化sigar的配置文件
//    public static void initSigar() throws IOException {
//        SigarLoader loader = new SigarLoader(Sigar.class);
//        String lib = null;
//
//        try {
//            lib = loader.getLibraryName();
//
//        } catch (ArchNotSupportedException var7) {
//
//        }
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath:/sigar.so/" + lib);
//        if (resource.exists()) {
//            InputStream is = resource.getInputStream();
//       String path=     "/var/log";
//       if(System.getProperty("os.name").contains("windows"))
//       {
//           path="F:\\java\\jdk\\bin\\log";
//       }
//            File tempDir = new File(path);
//            if (!tempDir.exists()){
//                tempDir.mkdirs();
//            }
//            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
//            int lentgh = 0;
//            while ((lentgh = is.read()) != -1){
//                os.write(lentgh);
//            }
//
//            is.close();
//            os.close();
//            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
//System.out.println("1111111111111111111111111");
//        }
//
//    }
//}
