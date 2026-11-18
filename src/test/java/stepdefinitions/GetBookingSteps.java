
package stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import com.booking.builders.BookingBuilder;
import com.booking.models.Booking;

import context.ScenarioContext;
import framework.clients.BookingClient;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class GetBookingSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final ScenarioContext scenarioContext;

    private int bookingId;

    public GetBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("a booking has been created successfully")
    public void createBookingSuccessfully() {

        Booking booking = BookingBuilder.createValidBooking();

        Response response = bookingClient.createBooking(booking);
        scenarioContext.setResponse(response);

        assertEquals(200, response.getStatusCode(),
                "Booking creation failed");

        bookingId = response.jsonPath().getInt("bookingid");

        assertTrue(bookingId > 0,
                "Booking ID was not generated");

        scenarioContext.setBookingId(bookingId);
    }

    @When("I retrieve the booking by its ID")
    public void retrieveBookingById() {

        bookingId = scenarioContext.getBookingId();

        Response response = bookingClient.getBooking(
                bookingId,
                scenarioContext.getAuthToken()
        );

        scenarioContext.setResponse(response);
    }

    @When("I retrieve a booking with an unknown ID")
    public void retrieveBookingWithUnknownId() {

        bookingId = Integer.MAX_VALUE;

        Response response = bookingClient.getBooking(
                bookingId,
                scenarioContext.getAuthToken()
        );

        scenarioContext.setResponse(response);
    }

    @Given("no authentication token is available")
    public void noAuthenticationTokenAvailable() {

        scenarioContext.setAuthToken(null);
    }

    @Given("an invalid authentication token is available")
    public void invalidAuthenticationTokenAvailable() {

        scenarioContext.setAuthToken("invalid-token");
    }

    @Then("the booking response should contain valid booking details")
    public void verifyBookingDetails() {

        Response response = scenarioContext.getResponse();

        assertNotNull(response.getBody());

        assertNotNull(response.jsonPath().get("roomid"));
        assertNotNull(response.jsonPath().get("firstname"));
        assertNotNull(response.jsonPath().get("lastname"));
        assertNotNull(response.jsonPath().get("bookingdates"));
        assertNotNull(response.jsonPath().get("email"));
        assertNotNull(response.jsonPath().get("phone"));
    }

    @Then("the response should contain an authorization error")
    public void verifyAuthorizationError() {

        Response response = scenarioContext.getResponse();

        assertEquals(401, response.getStatusCode());

        String error = response.jsonPath().getString("error");

        assertNotNull(error,
                "Expected an authorization error message");
    }
}