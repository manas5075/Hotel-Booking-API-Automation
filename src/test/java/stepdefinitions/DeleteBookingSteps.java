
package stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import context.ScenarioContext;
import framework.clients.BookingClient;

import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class DeleteBookingSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final ScenarioContext scenarioContext;

    public DeleteBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I delete the booking")
    public void deleteBooking() {

        int bookingId = scenarioContext.getBookingId();
        String token = scenarioContext.getAuthToken();

        Response response = bookingClient.deleteBooking(bookingId, token);

        scenarioContext.setResponse(response);

        assertEquals(201, response.getStatusCode(),
                "Expected HTTP 201 after successful booking deletion");

        assertTrue(response.asString().isEmpty(),
                "Expected an empty response body after booking deletion");
    }

    @When("I retrieve the deleted booking")
    public void retrieveDeletedBooking() {

        int bookingId = scenarioContext.getBookingId();
        String token = scenarioContext.getAuthToken();

        Response response = bookingClient.getBooking(bookingId, token);

        scenarioContext.setResponse(response);
    }

    @When("I delete a booking with an unknown ID")
    public void deleteBookingWithUnknownId() {

        int unknownBookingId = Integer.MAX_VALUE;
        String token = scenarioContext.getAuthToken();

        Response response = bookingClient.deleteBooking(
                unknownBookingId, token);

        scenarioContext.setResponse(response);
    }
}
