package stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import assertions.ApiAssertions;
import context.ScenarioContext;
import framework.constants.ApiConstants;
import framework.config.ApiConfig;
import framework.specification.RequestSpecFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HealthCheckSteps {

    private final ScenarioContext scenarioContext;

    public HealthCheckSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("the health API client is initialized")
    public void initializeHealthApiClient() {

        assertNotNull(ApiConfig.getBaseUrl(),
                "API base URL should be configured");
    }

    @When("I send a GET request to the health endpoint")
    public void sendGetRequestToHealthEndpoint() {

        Response response = given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .when()
                .get(ApiConstants.HEALTH);

        scenarioContext.setResponse(response);
    }

    @Then("the health response status should be {string}")
    public void verifyHealthResponseStatus(String expectedStatus) {

        Response response = scenarioContext.getResponse();

        ApiAssertions.assertStatusCode(response, 200);

        String actualStatus = response.jsonPath().getString("status");

        assertEquals(expectedStatus, actualStatus,
                "Unexpected health status");

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/healthcheck-response-schema.json"
        );
    }

    @Then("the health response should contain the {string} field")
    public void verifyHealthResponseContainsField(String fieldName) {

        Response response = scenarioContext.getResponse();

        ApiAssertions.assertStatusCode(response, 200);

        assertNotNull(response.jsonPath().get(fieldName),
                "Expected response field is missing: " + fieldName);

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/healthcheck-response-schema.json"
        );
    }
}