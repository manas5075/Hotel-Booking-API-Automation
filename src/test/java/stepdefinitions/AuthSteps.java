package stepdefinitions;

import context.ScenarioContext;
import framework.clients.AuthClient;
import framework.config.ApiConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // Send login request using an external JSON test-data file
    @When("I send a login request using test data {string}")
    public void sendLoginRequestUsingTestData(String fileName) {

        String resourcePath = "testdata/" + fileName;

        try (InputStream inputStream =
                getClass().getClassLoader()
                        .getResourceAsStream(resourcePath)) {

            assertNotNull(inputStream,
                    "Test-data file not found: " + resourcePath);

            String requestBody = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            response = authClient.loginWithRawJson(requestBody);

        } catch (IOException exception) {
            throw new AssertionError(
                    "Could not read test-data file: " + resourcePath,
                    exception
            );
        }
    }

    @Then("the authentication response status code should be {int}")
    public void verifyAuthenticationStatusCode(int expectedStatusCode) {

        assertNotNull(response, "Authentication response should not be null");

        assertEquals(expectedStatusCode, response.getStatusCode(),
                "Unexpected authentication response status code");
    }

    // Verify that invalid request data does not produce successful login
    @Then("the authentication response should indicate invalid request data")
    public void verifyInvalidAuthenticationRequest() {

        assertNotNull(response, "Authentication response should not be null");

        int statusCode = response.getStatusCode();

        assertTrue(statusCode >= 400 && statusCode < 500,
                "Expected a 4xx client error for invalid request data, "
                        + "but received: " + statusCode);
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