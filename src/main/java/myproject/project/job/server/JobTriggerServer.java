package myproject.project.job.server;

import myproject.project.job.server.utils.SftpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobTriggerServer {


    public StringBuffer getJobTriggerLog(String fileName) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer = SftpUtil.download(0, fileName);
        return stringBuffer;
    }







}
