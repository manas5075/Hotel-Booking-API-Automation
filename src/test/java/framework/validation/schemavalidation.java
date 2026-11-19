
package framework.validation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.InputStream;
import java.net.URI;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

class SchemaValidationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String[] SCHEMA_FILES = {
        "schemas/request/auth-login-request-schema.json",
        "schemas/request/create-booking-request-schema.json",
        "schemas/request/partial-update-booking-request-schema.json",
        "schemas/request/update-booking-request-schema.json",

        "schemas/response/auth-success-response-schema.json",
        "schemas/response/auth-unauthorized-response-schema.json",
        "schemas/response/booking-response-schema.json",
        "schemas/response/create-booking-success-response-schema.json",
        "schemas/response/create-booking-validation-error-response-schema.json",
        "schemas/response/healthcheck-response-schema.json",
        "schemas/response/unauthorized-response-schema.json"
    };

    @Test
    void allJsonSchemasShouldFollowDraft07Specification() throws Exception {

        JsonSchemaFactory factory =
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        JsonSchema draft07MetaSchema =
                factory.getSchema(
                        URI.create("http://json-schema.org/draft-07/schema#")
                );

        for (String schemaPath : SCHEMA_FILES) {

            try (InputStream input =
                    getClass().getClassLoader()
                            .getResourceAsStream(schemaPath)) {

                assertNotNull(input,
                        "Schema file not found: " + schemaPath);

                JsonNode schemaDocument =
                        objectMapper.readTree(input);

                Set<ValidationMessage> errors =
                        draft07MetaSchema.validate(schemaDocument);

                assertTrue(errors.isEmpty(),
                        "Invalid Draft-07 schema: " + schemaPath
                                + "\nValidation errors: " + errors);
            }
        }
    }

    @Test
    void invalidJsonSchemaDefinitionShouldBeRejected() throws Exception {

        JsonSchemaFactory factory =
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        JsonSchema draft07MetaSchema =
                factory.getSchema(
                        URI.create("http://json-schema.org/draft-07/schema#")
                );

        JsonNode invalidSchema =
                objectMapper.readTree(
                        "{\"type\":\"not-a-valid-json-type\"}"
                );

        Set<ValidationMessage> errors =
                draft07MetaSchema.validate(invalidSchema);

        assertFalse(errors.isEmpty(),
                "Invalid JSON Schema definition should be rejected");
    }
}