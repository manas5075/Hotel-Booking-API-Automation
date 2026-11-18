
package stepdefinitions;

import framework.clients.AuthClient;
import framework.utils.TokenManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthSteps {

    private AuthClient authClient;
    private Response response;

    @Given("the authentication API client is initialized")
    public void initializeAuthClient() {
        authClient = new AuthClient();
    }

    @When("I send a login request with username {string} and password {string}")
    public void sendLoginRequest(String username, String password) {
        response = authClient.login(username, password);
    }

    @Then("the authentication response status code should be {int}")
    public void verifyAuthenticationStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("the authentication token should be generated")
    public void verifyAuthenticationToken() {
        String token = response.jsonPath().getString("token");

        assertNotNull(token, "Authentication token should not be null");

        TokenManager.setToken(token);
    }
}
