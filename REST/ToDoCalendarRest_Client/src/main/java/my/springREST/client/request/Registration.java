package my.springREST.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.springREST.client.model.ResponseToken;
import my.springREST.client.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Registration {

    RestTemplate restTemplate = new RestTemplate();

    private static final String AUTHENTICATION_URL = "http://localhost:8080/auth/login";
    private static final String REGISTRATION_URL = "http://localhost:8080/auth/regPage";
    private static final String HELLO_URL = "http://localhost:8080/testText";

    public String regPerson(String username, String password, String email) throws JsonProcessingException {

        String response = null;
        // create user registration object
        User registrationUser = getRegistrationUser(username, password, email);
        // convert the user registration object to JSON
        String registrationBody = getBody(registrationUser);
        // create headers specifying that it is JSON request
        HttpHeaders registrationHeaders = getHeaders();
        HttpEntity<String> registrationEntity = new HttpEntity<String>(registrationBody, registrationHeaders);

        try {
            // Register User
            ResponseEntity<String> registrationResponse = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST,
                    registrationEntity, String.class);
            // if the registration is successful
            System.out.println(registrationResponse.getStatusCode().equals(HttpStatus.OK));

            if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {

                // create user authentication object
                User authenticationUser = getAuthenticationUser(username,password);
                // convert the user authentication object to JSON
                String authenticationBody = getBody(authenticationUser);
                // create headers specifying that it is JSON request
                HttpHeaders authenticationHeaders = getHeaders();
                HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
                        authenticationHeaders);

                // Authenticate User and get JWT
                ResponseEntity<ResponseToken> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                        HttpMethod.POST, authenticationEntity, ResponseToken.class);

                // if the authentication is successful
                System.out.println(authenticationResponse.getStatusCode().equals(HttpStatus.OK));
                // if the authentication is successful
                if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) {
                    String token = "Bearer " + authenticationResponse.getBody().getToken();
                    HttpHeaders headers = getHeaders();
                    headers.set("Authorization", token);
                    HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                    // Use Token to get Response
                    ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity,
                            String.class);
                    if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                        response = helloResponse.getBody();
                    }
                }

            }


        }catch(Exception ex)
        {
            System.out.println("exception");

        }
        return response;

    }

    private User getRegistrationUser(String username, String password, String email) {
        return new User(username, password, email);
    }

    private User getAuthenticationUser(String username, String password) {
        return new User(username, password);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private String getBody(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }


}
