package myproject.project.ds.server;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import myproject.project.ds.entity.TeamDsEntity;
import myproject.project.utils.controller.UtilsServer;

import java.net.URL;
import java.util.*;

public class ossServer {
    public static Map<String, Object> testDsDataOfOss(TeamDsEntity teamDsEntity) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 200);
        OSS ossClient = null;

        try {
            String endpoint = teamDsEntity.getDsInstLoc();
            String accessKeySecret = new String(UtilsServer.decryptBasedDes(teamDsEntity.getDsPassword()));
            System.out.println(accessKeySecret);
            String accessKeyId = teamDsEntity.getDsUser();
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String[] bucketList = teamDsEntity.getDsSchema().split(",");
            for (String ele : bucketList) {
                ObjectListing objectListing = ossClient.listObjects(ele);
                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
//                for(OSSObjectSummary eleObject:sums){
//                    URL url = ossClient.generatePresignedUrl(ele, eleObject.getKey(),(new Date()));
//
//                    System.out.println(eleObject.getBucketName());
//                    System.out.println(url);
//                }

                System.out.println(objectListing.getBucketName());
            }
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status", 100);
        } finally {
            try {

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return map;
    }
}
