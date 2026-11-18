
package stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.builders.BookingBuilder;
import com.booking.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import context.ScenarioContext;
import framework.clients.BookingClient;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class PartialUpdateBookingSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final ScenarioContext scenarioContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Booking originalBooking =
            BookingBuilder.createValidBooking();

    public PartialUpdateBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I partially update the booking with firstname {string}")
    public void updateBookingFirstname(String firstname) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstname);

        sendPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with lastname {string}")
    public void updateBookingLastname(String lastname) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lastname", lastname);

        sendPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking depositpaid to {word}")
    public void updateBookingDepositPaid(boolean depositpaid) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("depositpaid", depositpaid);

        sendPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with firstname {string}, lastname {string} and depositpaid {word}")
    public void updateMultipleBookingFields(
            String firstname, String lastname, boolean depositpaid) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("depositpaid", depositpaid);

        sendPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with invalid depositpaid value {string}")
    public void updateBookingWithInvalidDepositPaid(String value) {

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("depositpaid", value);

        sendPartialUpdateRequest(requestBody);
    }

    private void sendPartialUpdateRequest(Object requestBody) {

        int bookingId = scenarioContext.getBookingId();
        String token = scenarioContext.getAuthToken();

        Response response = bookingClient.partialUpdateBooking(
                bookingId, token, requestBody);

        scenarioContext.setResponse(response);
    }

    @Then("the booking firstname should be {string}")
    public void verifyBookingFirstname(String expectedFirstname) {

        Response response = scenarioContext.getResponse();

        assertEquals(
                expectedFirstname,
                response.jsonPath().getString("firstname"));
    }

    @Then("the booking lastname should be {string}")
    public void verifyBookingLastname(String expectedLastname) {

        Response response = scenarioContext.getResponse();

        assertEquals(
                expectedLastname,
                response.jsonPath().getString("lastname"));
    }

    @Then("the booking depositpaid should be {word}")
    public void verifyBookingDepositPaid(boolean expectedDepositPaid) {

        Response response = scenarioContext.getResponse();

        assertEquals(
                expectedDepositPaid,
                response.jsonPath().getBoolean("depositpaid"));
    }

    @Then("the other booking details should remain unchanged")
    public void verifyOtherBookingDetailsUnchanged() {

        Response response = scenarioContext.getResponse();

        assertEquals(
                originalBooking.getRoomid(),
                response.jsonPath().getInt("roomid"));

        assertEquals(
                originalBooking.getEmail(),
                response.jsonPath().getString("email"));

        assertEquals(
                originalBooking.getPhone(),
                response.jsonPath().getString("phone"));

        assertEquals(
                originalBooking.getBookingdates().getCheckin(),
                response.jsonPath().getString("bookingdates.checkin"));

        assertEquals(
                originalBooking.getBookingdates().getCheckout(),
                response.jsonPath().getString("bookingdates.checkout"));
    }

    @Then("the booking partial update should be rejected with validation errors")
    public void verifyPartialUpdateValidationErrors() {

        Response response = scenarioContext.getResponse();

        assertEquals(400, response.getStatusCode(),
                "Expected HTTP 400 for invalid partial update");

        List<String> errors = response.jsonPath().getList("errors");

        assertNotNull(errors,
                "Expected validation errors in response");

        assertFalse(errors.isEmpty(),
                "Expected at least one validation error");
    }
}
