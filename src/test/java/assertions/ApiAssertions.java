
package assertions;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class ApiAssertions {

    private ApiAssertions() {
        // Prevent object creation
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertNotNull(response, "API response should not be null");
        assertEquals(expectedStatusCode, response.getStatusCode(),
                "Unexpected API response status code");
    }

    public static void assertResponseBodyNotNull(Response response) {
        assertNotNull(response, "API response should not be null");
        assertNotNull(response.getBody(), "API response body should not be null");
    }

    public static void assertBookingResponseSchema(Response response) {
        assertNotNull(response, "API response should not be null");

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/booking-response-schema.json"));
    }
}