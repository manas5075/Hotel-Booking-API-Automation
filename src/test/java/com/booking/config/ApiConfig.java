package com.booking.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {

    private static final Properties PROPERTIES = new Properties();

    private static final String ENVIRONMENT =
            System.getProperty("env", "qa").trim().toLowerCase();

    static {
        loadProperties();
    }

    // Prevent object creation
    private ApiConfig() {
    }

    // Load environment-specific configuration
    private static void loadProperties() {

        String configFile =
                "config/config-" + ENVIRONMENT + ".properties";

        try (InputStream inputStream =
                     ApiConfig.class.getClassLoader()
                             .getResourceAsStream(configFile)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + configFile);
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to load configuration file: " + configFile, e);
        }

        validateConfiguration();
    }

    // Validate mandatory configuration properties
    private static void validateConfiguration() {

        String baseUrl = PROPERTIES.getProperty("base.url");
        String contentType = PROPERTIES.getProperty("content.type");

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("base.url is missing");
        }

        if (contentType == null || contentType.isBlank()) {
            throw new IllegalStateException("content.type is missing");
        }
    }

    // Get base URL
    public static String getBaseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    // Get content type
    public static String getContentType() {
        return PROPERTIES.getProperty("content.type");
    }

    // Get active environment
    public static String getEnvironment() {
        return ENVIRONMENT;
    }
    
    public static String getUsername() {
        return PROPERTIES.getProperty("auth.username");
    }

    public static String getPassword() {
        return PROPERTIES.getProperty("auth.password");
    }
}