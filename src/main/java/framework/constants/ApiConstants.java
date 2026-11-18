
package framework.constants;

public final class ApiConstants {

    private ApiConstants() {
        // Prevent object creation
    }

    // API endpoint paths
    public static final String HEALTH = "/booking/actuator/health";
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String BOOKING = "/booking";

    // Booking endpoint path
    public static final String BOOKING_BY_ID = "/booking/{id}";
}