package stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class CommonSteps {

    private final ScenarioContext scenarioContext;

    public CommonSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Then("the API response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                scenarioContext.getResponse().getStatusCode()
        );
    }

    @Then("the API should return an appropriate error response")
    public void verifyAppropriateErrorResponse() {
        Response response = scenarioContext.getResponse();

        assertNotNull(response, "API response should not be null");

        assertEquals(
                true,
                response.getStatusCode() >= 400,
                "Expected an error HTTP status code"
        );

        assertNotNull(
                response.getBody(),
                "Error response body should not be null"
        );
    }
}