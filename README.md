# Hotel Booking API Automation

A Java-based API automation framework for testing the Restful Booker API using REST Assured, Cucumber, and JUnit 5.

The framework supports data-driven testing, JSON Schema validation, reusable authentication, request and response assertions, logging, and test reporting.

## Project Overview

The project automates positive and negative test scenarios for the following API endpoints:

| API | HTTP Method | Endpoint |
|---|---|---|
| Authentication | POST | `/auth/login` |
| Create Booking | POST | `/booking` |
| Get Booking | GET | `/booking/{id}` |
| Update Booking | PUT | `/booking/{id}` |
| Partial Update Booking | PATCH | `/booking/{id}` |
| Delete Booking | DELETE | `/booking/{id}` |
| Health Check | GET | `/booking/actuator/health` |

## Technology Stack

- **Java 17** – Programming language
- **Maven** – Build and dependency management
- **REST Assured** – API automation
- **Cucumber** – Behaviour-Driven Development (BDD)
- **JUnit 5** – Test execution
- **Jackson** – JSON serialization and deserialization
- **JSON Schema Validator** – Request and response schema validation
- **Log4j2** – Logging

## Framework Features

- Reusable API client classes for endpoint interactions.
- Centralized request specification.
- Reusable authentication and token management through scenario context.
- Positive and negative API test scenarios.
- Boundary-value testing for applicable booking fields.
- Data-driven testing using external JSON files.
- JSON Schema validation for applicable API requests and responses.
- Reusable API response assertions.
- Retry mechanism for selected transient failures on GET requests.
- Scenario-level context management to support parallel execution.
- Timestamped execution logs and Cucumber reports.

## Project Structure

```text
API_Testing_Kata/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── assertions/
│   │       ├── framework/
│   │       │   ├── clients/
│   │       │   ├── config/
│   │       │   ├── constants/
│   │       │   ├── specification/
│   │       │   └── utils/
│   │       └── com/
│   │           └── booking/
│   │               ├── builders/
│   │               └── models/
│   │
│   └── test/
│       ├── java/
│       │   ├── context/
│       │   ├── hooks/
│       │   ├── runners/
│       │   └── stepdefinitions/
│       │
│       └── resources/
│           ├── features/
│           ├── schemas/
│           │   ├── request/
│           │   ├── response/
│           │   └── contracts/
│           ├── testdata/
│           │   ├── booking/
│           │   └── partial-update/
│           ├── logging/
│           └── junit-platform.properties
│
├── pom.xml
└── README.md
```

## API Test Coverage

### 1. Authentication API

- Successful authentication.
- Invalid credentials.
- Missing and null credentials.
- Invalid credential data types.
- Empty credentials.
- Leading and trailing whitespace.
- Long username and password values.
- Authentication response validation.

### 2. Create Booking API

- Successful booking creation.
- Minimum and maximum valid field lengths.
- Invalid name and phone lengths.
- Invalid email formats.
- Invalid booking dates.
- Missing mandatory fields.
- Invalid field data types.
- Room ID boundary values.
- Booking request and response schema validation.

### 3. Get Booking API

- Retrieve an existing booking.
- Retrieve an unknown booking ID.
- Retrieve without authentication.
- Retrieve with invalid authentication.

### 4. Update Booking API (PUT)

- Update an existing booking.
- Minimum and maximum valid field lengths.
- Invalid name and phone lengths.
- Invalid email formats.
- Invalid booking dates.
- Authentication validation.
- Additional JSON-driven negative test cases.

### 5. Partial Update Booking API (PATCH)

- Update firstname only.
- Update lastname only.
- Update depositpaid only.
- Update multiple fields in one request.
- Verify fields not included in the request remain unchanged.
- Authentication validation.
- Empty, null, and invalid data values.
- Partial update request schema validation.

### 6. Delete Booking API

- Delete an existing booking.
- Verify a deleted booking cannot be retrieved.
- Delete without authentication.
- Delete with invalid authentication.
- Delete using an unknown booking ID.
- Boundary booking ID tests.

### 7. Health Check API

- Verify HTTP 200 response.
- Verify health status is `UP`.
- Verify the response contains the expected status field.
- Health response schema validation.

## JSON Schema Validation

JSON schemas are maintained under:

```text
src/test/resources/schemas/
```

- `request/` – Request payload schemas.
- `response/` – API response schemas.
- `contracts/` – Additional API response contract checks.

The framework uses JSON Schema Draft-07 for schema validation.

The schemas were created using the supplied OpenAPI YAML specification as the reference for request and response structures.

## Test Data Management

External JSON files are maintained under:

```text
src/test/resources/testdata/
```

The test data is organised into:

- `booking/` – Create Booking and Update Booking test data.
- `partial-update/` – Partial Update Booking test data.

This approach separates test data from Gherkin scenarios and Java step definitions, making test cases easier to maintain and extend.

## Configuration

The framework uses centralized configuration through `ApiConfig` and request specifications through `RequestSpecFactory`.

Configure the API base URL and credentials using the project's existing configuration files or supported environment settings.

Do not commit real credentials, authentication tokens, or other secrets to the repository.

## API Environment and Current Testing Status

The project targets the Restful Booker API.

**API base URL:**

https://automationintesting.online/api

**Endpoint availability:** During framework development, the API endpoints were inactive or unavailable for reliable live testing.

To continue framework development despite this limitation, the supplied OpenAPI YAML specification was used as the primary reference for:

- Identifying API endpoints and HTTP methods.
- Understanding request parameters and response structures.
- Designing request and response JSON schemas.
- Creating positive and negative test scenarios.
- Identifying mandatory fields and supported data types.
- Defining contract-based response expectations.

### Schema Development and Local Validation

JSON Schema files were generated based on the OpenAPI YAML specification.

The schemas were validated locally using the project's `SchemaValidationTest`.

The schema validation execution completed successfully:

- 2 tests executed.
- 0 failures.
- 0 errors.
- Build successful.

This confirms that the schema documents are valid and that the intentionally invalid schema definition is rejected.

### Important Testing Limitation

Successful local schema validation and Java compilation do not confirm that the live API behaves as expected.

Full end-to-end API execution, including actual response status codes, authentication behaviour, booking creation, updates, and deletion, remains subject to endpoint availability.

Some additional negative and boundary scenarios are robustness tests whose expected behaviour is not explicitly defined in the supplied OpenAPI specification. These expectations must be confirmed against the live API when it becomes available.

The API environment may reset periodically, so existing booking data and availability can change between executions.

## Running the Tests

Run the following commands from the project root.

**Compile the project and test sources:**

```bash
mvn test-compile
```

**Run schema validation tests:**

```bash
mvn -Dtest=SchemaValidationTest test
```

**Run unit tests for the retry utility:**

```bash
mvn -Dtest=RetryUtilsTest test
```

**Run the complete test suite:**

```bash
mvn test -Dtest=TestRunner
```

**Run an individual feature:**

```bash
mvn test -Dtest=TestRunner -Dcucumber.features=src/test/resources/features/create-booking.feature
```

Replace `create-booking.feature` with the feature file you want to execute.

Note: Complete end-to-end API verification requires an available API environment.

## Logging and Reports

The framework uses Log4j2 for execution logging.

Cucumber execution reports are configured through the test runner and Maven test configuration.

Generated logs and reports are available in the project's `target/` directory after execution.

Timestamped execution logs and reports help distinguish between different test runs.

## Future Improvements

- Complete end-to-end execution when the API endpoints become available.
- Verify all positive, negative, and boundary scenarios against the live API.
- Review and stabilise environment-dependent test cases.
- Finalise execution reports and documentation.
- Continuous Integration integration.

## Author

Hotel Booking API Automation Project
