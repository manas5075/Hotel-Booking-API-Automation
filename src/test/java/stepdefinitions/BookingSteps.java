
package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import framework.clients.BookingClient;

public class BookingSteps {

    private BookingClient bookingClient;
    private Response response;

    @Given("the booking API client is initialized")
    public void initializeBookingClient() {
        bookingClient = new BookingClient();
    }

    @When("I send a GET request to an unknown booking endpoint")
    public void sendGetRequestToUnknownEndpoint() {

        response = bookingClient.getBooking(999999, "invalid-token");
    }

    @Then("the API response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {

        assertEquals(expectedStatusCode, response.getStatusCode());
    }
}