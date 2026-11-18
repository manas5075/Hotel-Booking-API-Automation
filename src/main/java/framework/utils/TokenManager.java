
package framework.utils;

public final class TokenManager {

    private static String authToken;

    private TokenManager() {
        // Prevent object creation
    }

    // Store authentication token
    public static void setToken(String token) {
        authToken = token;
    }

    // Retrieve authentication token
    public static String getToken() {
        return authToken;
    }

    // Check whether token is available
    public static boolean hasToken() {
        return authToken != null && !authToken.isBlank();
    }

    // Clear authentication token
    public static void clearToken() {
        authToken = null;
    }
}
