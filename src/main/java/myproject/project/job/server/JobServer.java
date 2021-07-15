package myproject.project.job.server;

import myproject.project.job.entity.JobEntity;
import myproject.project.job.repository.JobRepository;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServer {
    @Autowired
    JobRepository jobRepository;

    public List<String> computedJobDepend(String[] inputDataList) {
        List<String> dependJob = new ArrayList<String>();
        for (String input : inputDataList) {
            List<JobEntity> jobEntityList = jobRepository.findAllByInputTabsLike("'%" + input + ",%'");
            for (JobEntity ele : jobEntityList) {
                dependJob.add(ele.getJobName());
            }
        }

        return dependJob;
    }


}
