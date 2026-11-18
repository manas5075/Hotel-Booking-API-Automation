package com.booking;

import org.junit.jupiter.api.Test;

import com.booking.builders.BookingBuilder;
import com.booking.models.Booking;

import static org.junit.jupiter.api.Assertions.*;

class BookingBuilderTest {

    @Test
    void shouldLoadValidBookingTestData() {

        Booking booking = BookingBuilder.createValidBooking();

        assertNotNull(booking);
        assertEquals("Manas", booking.getFirstname());
        assertEquals("Behera", booking.getLastname());
        assertEquals(2, booking.getRoomid());
        assertTrue(booking.getDepositpaid());
    }
}