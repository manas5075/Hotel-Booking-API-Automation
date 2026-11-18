package com.booking.factory;

import com.booking.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
        // Prevent object creation
    }

    public static RequestSpecification getRequestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}