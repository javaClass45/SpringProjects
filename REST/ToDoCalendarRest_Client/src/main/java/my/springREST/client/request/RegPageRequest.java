package my.springREST.client.request;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RegPageRequest {

    RestTemplate restTemplate = new RestTemplate();



    public void post(String post_url, String username, String password, String email) {

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("username", username);
        jsonToSend.put("password", password);
        jsonToSend.put("email", email);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        String response = restTemplate.postForObject(post_url, request, String.class);

        System.out.println(response);



    }




}
