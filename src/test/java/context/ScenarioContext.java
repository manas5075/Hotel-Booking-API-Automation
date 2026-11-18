
package context;

import io.restassured.response.Response;

public class ScenarioContext {

    private String authToken;
    private Response response;
    private int bookingId;

    // Store authentication token
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    // Store API response
    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    // Store booking ID
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookingId() {
        return bookingId;
    }
    public void clear() {
        authToken = null;
        response = null;
        bookingId = 0;
    }
}
