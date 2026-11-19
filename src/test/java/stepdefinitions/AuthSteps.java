
package stepdefinitions;

import context.ScenarioContext;
import framework.clients.AuthClient;
import framework.config.ApiConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthSteps {

    private final ScenarioContext scenarioContext;
    private AuthClient authClient;
    private Response response;

    public AuthSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("the authentication API client is initialized")
    public void initializeAuthClient() {
        authClient = new AuthClient();
    }

    @Given("I am authenticated")
    public void authenticateScenario() {

        authClient = new AuthClient();

        response = authClient.login(
                ApiConfig.getUsername(),
                ApiConfig.getPassword()
        );

        assertEquals(200, response.getStatusCode(),
                "Authentication should be successful");

        storeAuthenticationToken();
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
        storeAuthenticationToken();
    }

    private void storeAuthenticationToken() {
        String token = response.jsonPath().getString("token");

        assertNotNull(token, "Authentication token should not be null");
        assertFalse(token.isBlank(), "Authentication token should not be blank");

        scenarioContext.setAuthToken(token);
    }
}