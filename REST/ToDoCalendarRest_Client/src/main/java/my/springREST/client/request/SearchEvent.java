package my.springREST.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.springREST.client.model.Event;
import my.springREST.client.model.ResponseToken;
import my.springREST.client.model.Search;
import my.springREST.client.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

//todo переделать
public class SearchEvent {

    RestTemplate restTemplate = new RestTemplate();

    private static final String AUTHENTICATION_URL = "http://localhost:8080/auth/login";
    private static final String POST_URL = "http://localhost:8080/event/search";

    public void searchEvent(String search, String username, String password)
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
                String reqBody = getBodySearch(patchEvent(search));
                //System.out.println(reqBody);
                // create request
                HttpEntity<String> request = new HttpEntity<String>(reqBody, headers);
                // take a response
                ResponseEntity<String> response = restTemplate.exchange(POST_URL, HttpMethod.POST, request,
                        String.class);

               System.out.println(response.getBody());

            }

        } catch (Exception ex) {
            System.out.println("exception!!");
            ex.printStackTrace();
        }


    }

    private Search patchEvent(String search ) {
        return new Search(search);
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

    private String getBodySearch(final Search search) throws JsonProcessingException {
         return new ObjectMapper().writeValueAsString(search);
    }
    private String getBodyUser(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }





}
