
package stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import assertions.ApiAssertions;
import context.ScenarioContext;
import framework.clients.BookingClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class BookingSteps {

    private final BookingClient bookingClient;
    private final ScenarioContext scenarioContext;

    public BookingSteps(ScenarioContext scenarioContext) {
        this.bookingClient = new BookingClient();
        this.scenarioContext = scenarioContext;
    }

    @Given("the booking API client is initialized")
    public void initializeBookingClient() {
        // BookingClient is initialized through the constructor.
    }

    @When("I send a GET request to an unknown booking endpoint")
    public void sendGetRequestToUnknownEndpoint() {
        scenarioContext.setResponse(
            bookingClient.getBooking(999999, "invalid-token")
        );
    }

    @Then("the API response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        assertEquals(
            expectedStatusCode,
            scenarioContext.getResponse().getStatusCode()
        );
    }

    @Then("the booking response should match the JSON schema")
    public void verifyBookingResponseSchema() {
        ApiAssertions.assertBookingResponseSchema(
            scenarioContext.getResponse()
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
