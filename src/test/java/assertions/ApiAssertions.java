
package assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class ApiAssertions {

    private ApiAssertions() {
        // Prevent object creation
    }

    public static void assertStatusCode(
            Response response, int expectedStatusCode) {

        assertNotNull(response, "API response should not be null");

        assertEquals(expectedStatusCode, response.getStatusCode(),
                "Unexpected API response status code");
    }

    public static void assertResponseBodyNotNull(Response response) {
        assertNotNull(response, "API response should not be null");
        assertNotNull(response.getBody(),
                "API response body should not be null");
    }

    // Validate outgoing request JSON
    public static void assertRequestSchema(
            Object requestBody, String schemaPath) {

        try {
            String requestJson =
                    new ObjectMapper().writeValueAsString(requestBody);

            assertThat(requestJson,
                    matchesJsonSchemaInClasspath(schemaPath));

        } catch (JsonProcessingException exception) {
            throw new AssertionError(
                    "Could not serialize request body", exception);
        }
    }

    // Validate incoming response JSON
    public static void assertResponseSchema(
            Response response, String schemaPath) {

        assertNotNull(response, "API response should not be null");

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
    
    public static void assertBookingResponseSchema(Response response) {

        assertResponseSchema(
                response,
                "schemas/response/booking-response-schema.json"
        );
    }

}
