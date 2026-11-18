
package framework.utils;

import framework.config.ApiConfig;
import io.restassured.response.Response;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.NoRouteToHostException;
import java.util.function.Supplier;

public final class RetryUtils {

    private static final long RETRY_DELAY_MS = 500;

    private RetryUtils() {
        // Prevent object creation
    }

    public static Response executeWithRetry(
            Supplier<Response> request) {

        return executeWithRetry(request, ApiConfig.getRetryCount());
    }

    // Package-private overload for unit testing
    static Response executeWithRetry(
            Supplier<Response> request, int maxRetries) {

        if (maxRetries < 0) {
            throw new IllegalArgumentException(
                    "Retry count cannot be negative");
        }

        int attempt = 0;

        while (true) {
            try {
                Response response = request.get();

                if (!isRetryableStatus(response.getStatusCode())
                        || attempt >= maxRetries) {
                    return response;
                }

                attempt++;

                System.out.println(
                        "Retrying request. Attempt: " + attempt);

                waitBeforeRetry();

            } catch (RuntimeException exception) {

                if (!isRetryableException(exception)
                        || attempt >= maxRetries) {
                    throw exception;
                }

                attempt++;

                System.out.println(
                        "Retrying after connection failure. Attempt: "
                                + attempt);

                waitBeforeRetry();
            }
        }
    }

    private static boolean isRetryableStatus(int statusCode) {
        return statusCode == 429
                || statusCode == 502
                || statusCode == 503
                || statusCode == 504;
    }

    private static boolean isRetryableException(
            Throwable exception) {

        Throwable cause = exception;

        while (cause != null) {

            if (cause instanceof ConnectException
                    || cause instanceof SocketTimeoutException
                    || cause instanceof NoRouteToHostException) {
                return true;
            }

            cause = cause.getCause();
        }

        return false;
    }

    private static void waitBeforeRetry() {

        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            throw new IllegalStateException(
                    "Retry interrupted", exception);
        }
    }
}
