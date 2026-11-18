
package framework.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import framework.specification.RequestSpecFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthClient {

    private static final Logger logger =
            LogManager.getLogger(AuthClient.class);

    private static final String LOGIN_ENDPOINT = "/auth/login";

    // Authenticate user and retrieve token
    public Response login(String username, String password) {

        logger.info("Sending authentication request to {}", LOGIN_ENDPOINT);

        Response response = given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .body(createLoginRequest(username, password))
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
    private LoginRequest createLoginRequest(
            String username, String password) {

        return new LoginRequest(username, password);
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
