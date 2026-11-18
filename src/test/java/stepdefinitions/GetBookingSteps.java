
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

    private Response response;
    private int bookingId;
    private String token;

    public GetBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("a booking has been created successfully")
    public void createBookingSuccessfully() {

        Booking booking = BookingBuilder.createValidBooking();

        response = bookingClient.createBooking(booking);

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

        response = bookingClient.getBooking(bookingId, token);
    }

    @When("I retrieve a booking with an unknown ID")
    public void retrieveBookingWithUnknownId() {

        bookingId = Integer.MAX_VALUE;

        response = bookingClient.getBooking(bookingId, token);
    }

    @Given("no authentication token is available")
    public void noAuthenticationTokenAvailable() {

        token = null;
    }

    @Given("an invalid authentication token is available")
    public void invalidAuthenticationTokenAvailable() {

        token = "invalid-token";
    }

    @Then("the booking response should contain valid booking details")
    public void verifyBookingDetails() {

        assertNotNull(response.getBody());

        assertNotNull(response.jsonPath().get("roomid"));
        assertNotNull(response.jsonPath().get("firstname"));
        assertNotNull(response.jsonPath().get("lastname"));
        assertNotNull(response.jsonPath().get("bookingdates"));
        assertNotNull(response.jsonPath().get("email"));
        assertNotNull(response.jsonPath().get("phone"));
    }

    @Then("the API should return an appropriate error response")
    public void verifyUnknownBookingError() {

        assertTrue(response.getStatusCode() >= 400,
                "Expected an error response for an unknown booking ID");
    }

    @Then("the response should contain an authorization error")
    public void verifyAuthorizationError() {

        assertEquals(401, response.getStatusCode());

        String error = response.jsonPath().getString("error");

        assertNotNull(error,
                "Expected an authorization error message");
    }
}