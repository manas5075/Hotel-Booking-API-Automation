
package com.booking.builders;

import java.io.IOException;
import java.io.InputStream;

import com.booking.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookingBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Booking createValidBooking() {

        try (InputStream inputStream =
                     BookingBuilder.class.getClassLoader()
                             .getResourceAsStream("testdata/booking.json")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "booking.json file not found in testdata folder");
            }

            return OBJECT_MAPPER.readValue(inputStream, Booking.class);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to read booking test data", e);
        }
    }
}