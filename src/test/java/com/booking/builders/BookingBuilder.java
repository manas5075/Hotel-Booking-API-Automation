
package com.booking.builders;

import java.io.IOException;
import java.io.InputStream;

import com.booking.models.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BookingBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private BookingBuilder() {
        // Prevent object creation
    }

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

    public static Booking createBookingWithNameLengths(
            int firstnameLength, int lastnameLength) {

        Booking booking = createValidBooking();

        booking.setFirstname("A".repeat(firstnameLength));
        booking.setLastname("B".repeat(lastnameLength));

        return booking;
    }

    public static Booking createBookingWithPhoneLength(int phoneLength) {

        Booking booking = createValidBooking();
        booking.setPhone("1".repeat(phoneLength));

        return booking;
    }

    public static Booking createBookingWithEmail(String email) {

        Booking booking = createValidBooking();
        booking.setEmail(email);

        return booking;
    }
}