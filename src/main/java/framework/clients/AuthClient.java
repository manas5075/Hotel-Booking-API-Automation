package framework.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import framework.specification.RequestSpecFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthClient {

    private static final Logger logger =
            LogManager.getLogger(AuthClient.class);

    private static final String LOGIN_ENDPOINT = "/auth/login";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Authenticate user and retrieve token
    public Response login(String username, String password) {

        String requestBody = createLoginRequest(username, password);

        return sendLoginRequest(requestBody);
    }

    // Send a login request using a raw JSON body
    public Response loginWithRawJson(String requestBody) {

        return sendLoginRequest(requestBody);
    }

    // Common method for sending authentication requests
    private Response sendLoginRequest(String requestBody) {

        logger.info("Sending authentication request to {}", LOGIN_ENDPOINT);

        Response response = given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT);

        logger.info("Authentication response received. Status code: {}",
                response.getStatusCode());

        if (response.getStatusCode() == 200) {
            logger.info("Authentication successful.");
        } else {
            logger.error("Authentication failed. Status code: {}",
                    response.getStatusCode());
        }

        return response;
    }

    // Create login request body
    private String createLoginRequest(
            String username, String password) {

        try {
            return objectMapper.writeValueAsString(
                    new LoginRequest(username, password)
            );

        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "Could not serialize authentication request",
                    exception
            );
        }
    }

    // Login request model
    private static class LoginRequest {

        private final String username;
        private final String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}