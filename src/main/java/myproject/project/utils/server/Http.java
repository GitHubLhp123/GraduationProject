package myproject.project.utils.server;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Http {
    public static String httpGet(String url) {
        StringBuilder sb = new StringBuilder();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        System.out.println(strbody);
        return strbody;
    }
}
