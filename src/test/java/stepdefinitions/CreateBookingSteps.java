package stepdefinitions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

public class CreateBookingSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ScenarioContext scenarioContext;

    private Response response;
    private Object requestBody;
    private Booking booking;

    public CreateBookingSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I create a booking with valid details")
    public void createBookingWithValidDetails() {

        booking = BookingBuilder.createValidBooking();
        requestBody = booking;

        ApiAssertions.assertRequestSchema(
                requestBody,
                "schemas/request/create-booking-request-schema.json"
        );

        sendBookingRequest();
    }

    @When("I create a booking using test data {string}")
    public void createBookingUsingTestData(String fileName) {

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

            sendBookingRequest();

        } catch (IOException exception) {
            throw new AssertionError(
                    "Could not read test-data file: " + resourcePath,
                    exception
            );
        }
    }

    @When("I create a booking with firstname length {int}, lastname length {int} and phone length {int}")
    public void createBookingWithBoundaryValues(
            int firstnameLength, int lastnameLength, int phoneLength) {

        booking = BookingBuilder.createBookingWithNameLengths(
                firstnameLength, lastnameLength);

        booking.setPhone("1".repeat(phoneLength));

        requestBody = booking;

        sendBookingRequest();
    }

    @When("I create a booking with firstname length {int} and lastname length {int}")
    public void createBookingWithInvalidNameLengths(
            int firstnameLength, int lastnameLength) {

        requestBody = BookingBuilder.createBookingWithNameLengths(
                firstnameLength, lastnameLength);

        sendBookingRequest();
    }

    @When("I create a booking with phone length {int}")
    public void createBookingWithInvalidPhoneLength(int phoneLength) {

        requestBody = BookingBuilder.createBookingWithPhoneLength(phoneLength);

        sendBookingRequest();
    }

    @When("I create a booking with email {string}")
    public void createBookingWithInvalidEmail(String email) {

        requestBody = BookingBuilder.createBookingWithEmail(email);

        sendBookingRequest();
    }

    @When("I create a booking with checkout date before checkin date")
    public void createBookingWithInvalidBookingDates() {

        ObjectNode bookingJson = objectMapper.valueToTree(
                BookingBuilder.createValidBooking());

        ObjectNode bookingDates =
                (ObjectNode) bookingJson.get("bookingdates");

        bookingDates.put("checkin", "2026-12-10");
        bookingDates.put("checkout", "2026-12-05");

        requestBody = bookingJson;

        sendBookingRequest();
    }

    @When("I create a booking without the {word} field")
    public void createBookingWithoutRequiredField(String fieldName) {

        ObjectNode bookingJson = objectMapper.valueToTree(
                BookingBuilder.createValidBooking());

        bookingJson.remove(fieldName);

        requestBody = bookingJson;

        sendBookingRequest();
    }

    @When("I create a booking with depositpaid value {string}")
    public void createBookingWithInvalidDepositPaid(String value) {

        ObjectNode bookingJson = objectMapper.valueToTree(
                BookingBuilder.createValidBooking());

        bookingJson.put("depositpaid", value);

        requestBody = bookingJson;

        sendBookingRequest();
    }

    private void sendBookingRequest() {

        response = bookingClient.createBooking(requestBody);
        scenarioContext.setResponse(response);
    }

    @Then("the booking ID should be generated")
    public void verifyBookingIdIsGenerated() {

        ApiAssertions.assertStatusCode(response, 200);

        ApiAssertions.assertResponseSchema(
                response,
                "schemas/response/create-booking-success-response-schema.json"
        );

        int bookingId = response.jsonPath().getInt("bookingid");

        assertTrue(bookingId > 0,
                "Expected a valid booking ID in the response");

        scenarioContext.setBookingId(bookingId);
    }

    @Then("the created booking details should match the request")
    public void verifyCreatedBookingDetails() {

        assertNotNull(booking);

        assertEquals(
                booking.getFirstname(),
                response.jsonPath().getString("booking.firstname"));

        assertEquals(
                booking.getLastname(),
                response.jsonPath().getString("booking.lastname"));

        assertEquals(
                booking.getEmail(),
                response.jsonPath().getString("booking.email"));

        assertEquals(
                booking.getPhone(),
                response.jsonPath().getString("booking.phone"));

        assertEquals(
                booking.getRoomid(),
                response.jsonPath().getInt("booking.roomid"));
    }

    @Then("the booking creation should be rejected with validation errors")
    public void verifyBookingValidationErrors() {

        assertEquals(400, response.getStatusCode(),
                "Expected HTTP 400 for invalid booking data");

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