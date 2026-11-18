
package framework.utils;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryUtilsTest {

    private Response createResponse(int statusCode) {
        return (Response) Proxy.newProxyInstance(
                Response.class.getClassLoader(),
                new Class<?>[]{Response.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("getStatusCode")) {
                        return statusCode;
                    }
                    return null;
                }
        );
    }

    @Test
    void shouldExecuteOnlyOnceWhenRetryCountIsZero() {

        AtomicInteger attempts = new AtomicInteger();

        Response response = RetryUtils.executeWithRetry(
                () -> {
                    attempts.incrementAndGet();
                    return createResponse(503);
                },
                0
        );

        assertEquals(503, response.getStatusCode());
        assertEquals(1, attempts.get());
    }

    @Test
    void shouldRetryOnceWhenRetryCountIsOne() {

        AtomicInteger attempts = new AtomicInteger();

        Response response = RetryUtils.executeWithRetry(
                () -> {
                    int currentAttempt = attempts.incrementAndGet();

                    if (currentAttempt == 1) {
                        return createResponse(503);
                    }

                    return createResponse(200);
                },
                1
        );

        assertEquals(200, response.getStatusCode());
        assertEquals(2, attempts.get());
    }

    @Test
    void shouldNotRetryNonRetryableStatusCode() {

        AtomicInteger attempts = new AtomicInteger();

        Response response = RetryUtils.executeWithRetry(
                () -> {
                    attempts.incrementAndGet();
                    return createResponse(400);
                },
                1
        );

        assertEquals(400, response.getStatusCode());
        assertEquals(1, attempts.get());
    }

    @Test
    void shouldRejectNegativeRetryCount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> RetryUtils.executeWithRetry(
                        () -> createResponse(200),
                        -1
                )
        );
    }
}
