package stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import builders.BookingBuilder;
import framework.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import assertions.ApiAssertions;
import context.ScenarioContext;
import framework.clients.BookingClient;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class UpdateBookingSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final ScenarioContext scenarioContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Booking expectedBooking;
    private Object requestBody;

    public UpdateBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I update the booking with valid details")
    public void updateBookingWithValidDetails() {

        expectedBooking = BookingBuilder.createValidBooking();
        requestBody = expectedBooking;

        ApiAssertions.assertRequestSchema(
                requestBody,
                "schemas/request/update-booking-request-schema.json"
        );

        sendUpdateRequest();
    }

    @When("I update the booking with firstname length {int}, lastname length {int} and phone length {int}")
    public void updateBookingWithBoundaryValues(
            int firstnameLength, int lastnameLength, int phoneLength) {

        expectedBooking = BookingBuilder.createBookingWithNameLengths(
                firstnameLength, lastnameLength);

        expectedBooking.setPhone("1".repeat(phoneLength));
        requestBody = expectedBooking;

        sendUpdateRequest();
    }

    @When("I update the booking with firstname length {int} and lastname length {int}")
    public void updateBookingWithInvalidNameLengths(
            int firstnameLength, int lastnameLength) {

        expectedBooking = BookingBuilder.createBookingWithNameLengths(
                firstnameLength, lastnameLength);

        requestBody = expectedBooking;

        sendUpdateRequest();
    }

    @When("I update the booking with phone length {int}")
    public void updateBookingWithInvalidPhoneLength(int phoneLength) {

        expectedBooking = BookingBuilder.createBookingWithPhoneLength(phoneLength);
        requestBody = expectedBooking;

        sendUpdateRequest();
    }

    @When("I update the booking with email {string}")
    public void updateBookingWithInvalidEmail(String email) {

        expectedBooking = BookingBuilder.createBookingWithEmail(email);
        requestBody = expectedBooking;

        sendUpdateRequest();
    }

    @When("I update the booking with checkout date before checkin date")
    public void updateBookingWithInvalidDates() {

        ObjectNode bookingJson = objectMapper.valueToTree(
                BookingBuilder.createValidBooking());

        ObjectNode bookingDates =
                (ObjectNode) bookingJson.get("bookingdates");

        bookingDates.put("checkin", "2026-12-10");
        bookingDates.put("checkout", "2026-12-05");

        requestBody = bookingJson;

        sendUpdateRequest();
    }

    @When("I update the booking using test data {string}")
    public void updateBookingUsingTestData(String fileName) {

        String resourcePath = "testdata/booking/" + fileName;

        try (InputStream inputStream =
                getClass().getClassLoader()
                        .getResourceAsStream(resourcePath)) {

            assertNotNull(inputStream,
                    "Test-data file not found: " + resourcePath);

            requestBody = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            sendUpdateRequest();

        } catch (IOException exception) {

            throw new AssertionError(
                    "Could not read test-data file: " + resourcePath,
                    exception
            );
        }
    }

    private void sendUpdateRequest() {

        int bookingId = scenarioContext.getBookingId();
        String token = scenarioContext.getAuthToken();

        Response response = bookingClient.updateBooking(
                bookingId, token, requestBody);

        scenarioContext.setResponse(response);
    }

    @Then("the updated booking details should match the request")
    public void verifyUpdatedBookingDetails() {

        Response response = scenarioContext.getResponse();

        assertNotNull(expectedBooking);

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/booking-response-schema.json"
        );

        assertEquals(
                expectedBooking.getFirstname(),
                response.jsonPath().getString("firstname"));

        assertEquals(
                expectedBooking.getLastname(),
                response.jsonPath().getString("lastname"));

        assertEquals(
                expectedBooking.getEmail(),
                response.jsonPath().getString("email"));

        assertEquals(
                expectedBooking.getPhone(),
                response.jsonPath().getString("phone"));

        assertEquals(
                expectedBooking.getRoomid(),
                response.jsonPath().getInt("roomid"));
    }

    @Then("the booking update should be rejected with validation errors")
    public void verifyBookingUpdateValidationErrors() {

        Response response = scenarioContext.getResponse();

        ApiAssertions.assertStatusCode(response, 400);

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/create-booking-validation-error-response-schema.json"
        );

        List<String> errors = response.jsonPath().getList("errors");

        assertNotNull(errors, "Expected validation errors in response");
        assertFalse(errors.isEmpty(),
                "Expected at least one validation error");
    }
}