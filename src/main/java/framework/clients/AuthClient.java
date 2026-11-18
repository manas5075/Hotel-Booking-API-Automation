
package framework.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import framework.specification.RequestSpecFactory;

public class AuthClient {

    private static final String LOGIN_ENDPOINT = "/auth/login";

    // Authenticate user and retrieve token
    public Response login(String username, String password) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .body(createLoginRequest(username, password))
                .when()
                .post(LOGIN_ENDPOINT);
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