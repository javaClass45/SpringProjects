package my.springREST.client.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.springREST.client.model.ResponseToken;
import my.springREST.client.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Authenticate {

    RestTemplate restTemplate = new RestTemplate();



    private static final String AUTHENTICATION_URL = "http://localhost:8080/auth/login";
    private static final String HELLO_URL = "http://localhost:8080/testText";


    public String getResponse() throws JsonProcessingException {

        String response = null;


        try {

                // create user authentication object
                User authenticationUser = getAuthenticationUser();
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
                    // Use Token to get Response for Request URL
                    ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity,
                            String.class);
                    if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                        response = helloResponse.getBody();
                    }
                }


        }catch(Exception ex)
        {
            System.out.println("exception");

        }
        return response;

    }



    private User getAuthenticationUser() {

        return new User("mikhail", "1234567" );
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.ALL_VALUE);
        return headers;
    }

    private String getBody(final User user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }



}
