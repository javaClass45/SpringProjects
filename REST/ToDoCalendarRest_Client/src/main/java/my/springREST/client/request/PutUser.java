package my.springREST.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.springREST.client.model.ResponseToken;
import my.springREST.client.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class PutUser {

    RestTemplate restTemplate = new RestTemplate();

    private static final String AUTHENTICATION_URL = "http://localhost:8080/auth/login";
    private static final String PUT_URL = "http://localhost:8080/person/";
    private static final String HELLO_URL = "http://localhost:8080/testText";

    public void putUser(int id, String user, String email, String role, String username, String password)
            throws JsonProcessingException {

        // create user authentication object
        User authenticationUser = getAuthenticationUser(username, password);
        // convert the user authentication object to JSON
        String authenticationBody = getBody(authenticationUser);
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
                String reqBody = getBody(patchPerson(user, email, role));
            // create request
                HttpEntity<String> request = new HttpEntity<String>(reqBody, headers);
                // take a response
                ResponseEntity<String> response = restTemplate.exchange(PUT_URL + id + "/edit", HttpMethod.PUT, request,
                        String.class);

                System.out.println(response.getBody());

            }

        } catch (Exception ex) {
            System.out.println("exception!");
            ex.printStackTrace();
        }


    }

    private User patchPerson(String user, String email, String role) {
        return new User(user, email, role);
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

    private String getBody(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }


}
