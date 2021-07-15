package myproject.project.ds.server;



import myproject.project.ds.entity.TeamDsEntity;
import myproject.project.utils.controller.UtilsServer;

import java.util.*;


public class mongodbServer {

    public static Map<String, Object> testDsDataOfMongodb(TeamDsEntity teamDsEntity) {
//        System.out.println("==========================");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
//        try {
////            String[] dbNames = teamDsEntity.getDsSchema().split(",");
////            System.out.println("==========================");
////            for (String ele : dbNames) {
////                List<ServerAddress> addrs = new ArrayList<ServerAddress>();
////                System.out.println("==========================");
////                String ip = teamDsEntity.getDsInstLoc().split(":")[0];
////                System.out.println("=======================ip" + ip);
////                int port = Integer.parseInt(teamDsEntity.getDsInstLoc().split(":")[1]);
////                ServerAddress serverAddress = new ServerAddress(ip, port);
////                addrs.add(serverAddress);
////                List<MongoCredential> credentials = new ArrayList<MongoCredential>();
////                String userName = teamDsEntity.getDsUser();
////                String password = new String(UtilsServer.decryptBasedDes(teamDsEntity.getDsPassword()));
////                System.out.println("=======================password" + password);
////                MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName, ele, password.toCharArray());
////                credentials.add(mongoCredential);
////                //通过连接认证获取MongoDB连接
////                MongoClient mongoClient = new MongoClient(addrs, credentials);
////                //连接到数据库
////                MongoDatabase mongoDatabase = mongoClient.getDatabase(ele);
//
//            }
//
//
//            System.out.println("Connect to database successfully");
//        } catch (MongoException  t) {
//            System.out.println("======");
//            map.put("message", t.getMessage());
//            map.put("status", 100);
//            System.err.println(t.getClass().getName() + ": " + t.getMessage());
//        }
////              (MongoSecurityException e) {
////            System.out.println("======");
////            map.put("message", e.getMessage());
////            map.put("status", 100);
////            System.err.println(e.getClass().getName() + ": " + e.getMessage());
////        } catch (MongoCommandException e) {
////            System.out.println("======");
////            map.put("message", e.getMessage());
////            map.put("status", 100);
////            System.err.println(e.getClass().getName() + ": " + e.getMessage());
////        }
//        return map;
        return map;
    }
}
