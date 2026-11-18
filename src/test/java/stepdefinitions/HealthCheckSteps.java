
package stepdefinitions;

import framework.config.ApiConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HealthCheckSteps {

    private Response response;

    @Given("the health API client is initialized")
    public void initializeHealthApiClient() {
        // API configuration is loaded through ApiConfig
        assertNotNull(ApiConfig.getBaseUrl(),
                "API base URL should be configured");
    }

    @When("I send a GET request to the health endpoint")
    public void sendGetRequestToHealthEndpoint() {
        response = given()
                .baseUri(ApiConfig.getBaseUrl())
                .accept("application/json")
                .when()
                .get("/booking/actuator/health");
    }


    @Then("the health response status should be {string}")
    public void verifyHealthResponseStatus(String expectedStatus) {
        String actualStatus = response.jsonPath().getString("status");

        assertEquals(expectedStatus, actualStatus,
                "Unexpected health status");
    }

    @Then("the health response should contain the {string} field")
    public void verifyHealthResponseContainsField(String fieldName) {
        assertNotNull(response.jsonPath().get(fieldName),
                "Expected response field is missing: " + fieldName);
    }
}