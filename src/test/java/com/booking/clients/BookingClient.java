
package com.booking.clients;

import com.booking.factory.RequestSpecFactory;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingClient {

    private static final String BOOKING_ENDPOINT = "/booking";

    // Create a booking
    public Response createBooking(Object requestBody) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .body(requestBody)
                .when()
                .post(BOOKING_ENDPOINT);
    }

    // Get a booking by ID
    public Response getBooking(int bookingId, String token) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .cookie("token", token)
                .when()
                .get(BOOKING_ENDPOINT + "/" + bookingId);
    }

    // Update an entire booking
    public Response updateBooking(
            int bookingId, String token, Object requestBody) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .cookie("token", token)
                .body(requestBody)
                .when()
                .put(BOOKING_ENDPOINT + "/" + bookingId);
    }

    // Partially update a booking
    public Response partialUpdateBooking(
            int bookingId, String token, Object requestBody) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .cookie("token", token)
                .body(requestBody)
                .when()
                .patch(BOOKING_ENDPOINT + "/" + bookingId);
    }

    // Delete a booking
    public Response deleteBooking(int bookingId, String token) {

        return given()
                .spec(RequestSpecFactory.getRequestSpecification())
                .cookie("token", token)
                .when()
                .delete(BOOKING_ENDPOINT + "/" + bookingId);
    }
}