package my.springREST.client.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.springREST.client.model.Event;
import my.springREST.client.model.ResponseToken;
import my.springREST.client.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

//todo переделать
public class PostEvent {

    RestTemplate restTemplate = new RestTemplate();

    private static final String AUTHENTICATION_URL = "http://localhost:8080/auth/login";
    private static final String POST_URL = "http://localhost:8080/event/new";

    public void postEvent(String title, String description, String deadlineDate, String deadlineTime,
                          String completed, String username, String password)
            throws JsonProcessingException {

        // create user authentication object
        User authenticationUser = getAuthenticationUser(username, password);
        // convert the user authentication object to JSON
        String authenticationBody = getBodyUser(authenticationUser);
        // create headers specifying that it is JSON request
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
                authenticationHeaders);
        try {
            // Authenticate User and get JWT
            ResponseEntity<ResponseToken> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                    HttpMethod.POST, authenticationEntity, ResponseToken.class);

            // if the authentication is successful
            System.out.println(authenticationResponse.getStatusCode().equals(HttpStatus.OK));
            // if the authentication is successful
            if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
                String token = "Bearer " + Objects.requireNonNull(authenticationResponse.getBody()).getToken();
                HttpHeaders headers = getHeaders();
                // bring token in header
                headers.set("Authorization", token);
                // create body of request
                String reqBody = getBodyEvent(patchEvent(title, description, deadlineDate, deadlineTime, completed));
                //System.out.println(reqBody);
                // create request
                HttpEntity<String> request = new HttpEntity<String>(reqBody, headers);
                // take a response
                ResponseEntity<String> response = restTemplate.exchange(POST_URL, HttpMethod.POST, request,
                        String.class);

               System.out.println(response.getStatusCode());

            }

        } catch (Exception ex) {
            System.out.println("exception!!");
            ex.printStackTrace();
        }


    }

    private Event patchEvent(String title, String description, String deadlineDate,
                             String deadlineTime, String completed) {
        return new Event(title, description,deadlineDate, deadlineTime, completed);
    }

    private User getAuthenticationUser(String username, String password) {
        return new User(username, password);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept-Encoding", MediaType.ALL_VALUE);

        return headers;
    }

    private String getBodyEvent(final Event event) throws JsonProcessingException {

        // convert Date yyyy-MM-dd -> String "406771200000"
        try {
          Long l = (new SimpleDateFormat("yyyy-MM-dd")
                  .parse(event.getDeadlineDate()))
                  .getTime();
            event.setDeadlineDate(l.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // convert Date HH:mm -> String "71200000"
        try {
            Long l = (new SimpleDateFormat("HH:mm")
                    .parse(event.getDeadlineTime()))
                    .getTime();
            event.setDeadlineTime(l.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ObjectMapper().writeValueAsString(event);
    }
    private String getBodyUser(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }





}
