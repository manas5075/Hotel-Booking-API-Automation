package tests;

import org.junit.jupiter.api.Test;

import framework.specification.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import static org.junit.jupiter.api.Assertions.*;

class RequestSpecFactoryTest {

    @Test
    void shouldCreateValidRequestSpecification() {

        RequestSpecification specification =
                RequestSpecFactory.getRequestSpecification();

        assertNotNull(specification,
                "Request specification should not be null");
    }
}