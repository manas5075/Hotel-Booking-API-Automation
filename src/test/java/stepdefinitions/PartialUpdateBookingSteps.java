package stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.builders.BookingBuilder;
import com.booking.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import assertions.ApiAssertions;
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

        sendValidPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with lastname {string}")
    public void updateBookingLastname(String lastname) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lastname", lastname);

        sendValidPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking depositpaid to {word}")
    public void updateBookingDepositPaid(boolean depositpaid) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("depositpaid", depositpaid);

        sendValidPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with firstname {string}, lastname {string} and depositpaid {word}")
    public void updateMultipleBookingFields(
            String firstname, String lastname, boolean depositpaid) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstname", firstname);
        requestBody.put("lastname", lastname);
        requestBody.put("depositpaid", depositpaid);

        sendValidPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking with invalid depositpaid value {string}")
    public void updateBookingWithInvalidDepositPaid(String value) {

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("depositpaid", value);

        sendPartialUpdateRequest(requestBody);
    }

    @When("I partially update the booking using test data {string}")
    public void updateBookingUsingTestData(String fileName) {

        String resourcePath = "testdata/partial-update/" + fileName;

        try (InputStream inputStream =
                getClass().getClassLoader()
                        .getResourceAsStream(resourcePath)) {

            assertNotNull(inputStream,
                    "Test-data file not found: " + resourcePath);

            String requestBody = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            sendPartialUpdateRequest(requestBody);

        } catch (IOException exception) {

            throw new AssertionError(
                    "Could not read test-data file: " + resourcePath,
                    exception
            );
        }
    }

    private void sendValidPartialUpdateRequest(Object requestBody) {

        ApiAssertions.assertRequestSchema(
                requestBody,
                "schemas/request/partial-update-booking-request-schema.json"
        );

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

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/booking-response-schema.json"
        );

        assertEquals(
                expectedFirstname,
                response.jsonPath().getString("firstname"));
    }

    @Then("the booking lastname should be {string}")
    public void verifyBookingLastname(String expectedLastname) {

        Response response = scenarioContext.getResponse();

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/booking-response-schema.json"
        );

        assertEquals(
                expectedLastname,
                response.jsonPath().getString("lastname"));
    }

    @Then("the booking depositpaid should be {word}")
    public void verifyBookingDepositPaid(boolean expectedDepositPaid) {

        Response response = scenarioContext.getResponse();

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/booking-response-schema.json"
        );

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

        ApiAssertions.assertStatusCode(response, 400);

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/create-booking-validation-error-response-schema.json"
        );

        List<String> errors = response.jsonPath().getList("errors");

        assertNotNull(errors,
                "Expected validation errors in response");

        assertFalse(errors.isEmpty(),
                "Expected at least one validation error");
    }
}