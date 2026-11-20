
package tests;

import org.junit.jupiter.api.Test;

import builders.BookingBuilder;
import framework.models.Booking;

import static org.junit.jupiter.api.Assertions.*;

class BookingBuilderTest {

    @Test
    void shouldCreateValidBookingWithDynamicData() {

        Booking booking = BookingBuilder.createValidBooking();

        assertNotNull(booking);

        assertNotNull(booking.getFirstname());
        assertTrue(booking.getFirstname().startsWith("Test"));

        assertNotNull(booking.getLastname());
        assertTrue(booking.getLastname().startsWith("User"));

        assertEquals(2, booking.getRoomid());
        assertTrue(booking.getDepositpaid());

        assertNotNull(booking.getEmail());
        assertTrue(booking.getEmail().endsWith("@example.com"));

        assertEquals(11, booking.getPhone().length());

        assertNotNull(booking.getBookingdates());
        assertNotNull(booking.getBookingdates().getCheckin());
        assertNotNull(booking.getBookingdates().getCheckout());
    }
}
