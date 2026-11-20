
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

        booking.setRoomid(
                ThreadLocalRandom.current().nextInt(1, 100)
        );
        booking.setFirstname("Test" + uniqueId);
        booking.setLastname("User" + uniqueId);
        booking.setDepositpaid(true);
        booking.setEmail("test" + uniqueId + "@example.com");

        // Generate a random phone number
        booking.setPhone(randomDigits(11));

        // Generate random booking dates
        int randomCheckinDays = ThreadLocalRandom.current()
                .nextInt(1, 91);

        int randomStayDays = ThreadLocalRandom.current()
                .nextInt(1, 8);

        LocalDate checkinDate =
                LocalDate.now().plusDays(randomCheckinDays);

        LocalDate checkoutDate =
                checkinDate.plusDays(randomStayDays);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin(checkinDate.toString());
        bookingdates.setCheckout(checkoutDate.toString());

        booking.setBookingdates(bookingdates);

        return booking;
    }

    public static Booking createBookingWithNameLengths(
            int firstnameLength, int lastnameLength) {

        Booking booking = createValidBooking();

        booking.setFirstname(randomLetters(firstnameLength));
        booking.setLastname(randomLetters(lastnameLength));

        return booking;
    }

    public static Booking createBookingWithPhoneLength(int phoneLength) {

        Booking booking = createValidBooking();

        booking.setPhone(randomDigits(phoneLength));

        return booking;
    }

    public static Booking createBookingWithEmail(String email) {

        Booking booking = createValidBooking();
        booking.setEmail(email);

        return booking;
    }

    private static String randomLetters(int length) {

        return ThreadLocalRandom.current()
                .ints(length, 'a', 'z' + 1)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private static String randomDigits(int length) {

        return ThreadLocalRandom.current()
                .ints(length, 1, 10)
                .collect(StringBuilder::new,
                        (builder, digit) -> builder.append(digit),
                        StringBuilder::append)
                .toString();
    }
}
