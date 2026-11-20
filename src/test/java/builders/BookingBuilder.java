
package builders;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import framework.models.Booking;
import framework.models.Bookingdates;

public final class BookingBuilder {

    private BookingBuilder() {
        // Prevent object creation
    }

    public static Booking createValidBooking() {

        Booking booking = new Booking();

        String uniqueId = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);

        booking.setRoomid(2);
        booking.setFirstname("Test" + uniqueId);
        booking.setLastname("User" + uniqueId);
        booking.setDepositpaid(true);
        booking.setEmail("test" + uniqueId + "@example.com");
        booking.setPhone("12345678901");

        // Generate random check-in date between 1 and 30 days from today
        int randomDays = ThreadLocalRandom.current().nextInt(1, 31);

        LocalDate checkinDate = LocalDate.now().plusDays(randomDays);
        LocalDate checkoutDate = checkinDate.plusDays(3);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin(checkinDate.toString());
        bookingdates.setCheckout(checkoutDate.toString());

        booking.setBookingdates(bookingdates);

        return booking;
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
