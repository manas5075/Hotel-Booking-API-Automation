# Hotel Booking API Automation

A Java 17-based API automation framework for testing the Restful Booker API using REST Assured, Cucumber, and JUnit 5.

The framework supports data-driven testing, JSON Schema validation, reusable authentication, request and response assertions, logging, and Cucumber and ExtentReports test reporting.

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

**API Base URL:**  
https://automationintesting.online/api

## Technology Stack

- **Java 17** – Programming language
- **Maven** – Build and dependency management
- **REST Assured** – API automation
- **Cucumber** – Behaviour-Driven Development (BDD)
- **JUnit 5** – Test execution
- **Jackson** – JSON serialization and deserialization
- **JSON Schema Validator** – Request and response schema validation
- **Log4j2** – Logging
- **ExtentReports** – HTML test execution reporting

## Framework Features

- Reusable API client classes for endpoint interactions.
- Centralized request specification.
- Reusable authentication and token management through scenario context.
- Positive and negative API test scenarios.
- Boundary-value testing for applicable booking fields.
- Data-driven testing using external JSON files.
- JSON Schema validation for applicable API requests and responses.
- Reusable API response assertions.
- Retry mechanism for selected transient failures.
- Scenario-level context management.
- Cucumber execution reporting and logging.
- ExtentReports integration for detailed HTML execution reports.
- Timestamped ExtentReports output to preserve reports from different executions.

## Project Structure

```text
API_Testing_Kata/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── framework/
│   │           ├── clients/
│   │           ├── config/
│   │           ├── constants/
│   │           ├── models/
│   │           ├── specification/
│   │           └── utils/
│   │
│   └── test/
│       ├── java/
│       │   ├── assertions/
│       │   ├── builders/
│       │   ├── context/
│       │   ├── hooks/
│       │   ├── runners/
│       │   ├── stepdefinitions/
│       │   └── tests/
│       │
│       └── resources/
│           ├── config/
│           ├── features/
│           ├── logging/
│           ├── schemas/
│           │   ├── request/
│           │   ├── response/
│           │   └── contracts/
│           ├── spec/
│           ├── testdata/
│           │   ├── authentication/
│           │   ├── booking/
│           │   └── partial-update/
│           ├── extent.properties
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
- Minimum and maximum field lengths.
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
- Minimum and maximum field lengths.
- Invalid name and phone lengths.
- Invalid email formats.
- Invalid booking dates.
- Authentication validation.
- JSON-driven negative test cases.

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

*The scenarios above describe implemented test coverage, not confirmation that every endpoint currently passes against the live API.*

## JSON Schema Validation

JSON schemas are maintained under:

```text
src/test/resources/schemas/
```

- `request/` – Request payload schemas.
- `response/` – API response schemas.
- `contracts/` – Additional API response contract checks.

The framework uses JSON Schema Draft-07 for schema validation.

The schemas were designed using the supplied OpenAPI YAML specification as the reference for request and response structures.

## Test Data Management

External JSON files are maintained under:

```text
src/test/resources/testdata/
```

The test data is organised into:

- `authentication/` – Authentication test payloads.
- `booking/` – Create Booking and Update Booking test data.
- `partial-update/` – Partial Update Booking test data.

This approach separates test data from Gherkin scenarios and Java step definitions, making test cases easier to maintain and extend.

## Configuration

The framework uses centralized configuration through `ApiConfig` and request specifications through `RequestSpecFactory`.

Configuration files are maintained under:

```text
src/test/resources/config/
```

- `config-qa.properties`
- `config-mock.properties`

The QA configuration is intended for the target API environment. Do not commit real credentials, authentication tokens, or other secrets to the repository.

## Current API Testing Status

The framework is currently under development and live API verification is not yet complete.

| API | Current Status | Observation |
|---|---|---|
| Authentication | Working | Successful authentication and applicable negative scenarios are working. |
| Create Booking | Partially working | Some booking creation functionality works. Unique test-data generation and repeatability require further observation. Response schema verification also has a known mismatch. |
| Get Booking | Not working currently | Live API execution is not producing reliable expected results. |
| Update Booking | Not working currently | Requires further investigation against the live API. |
| Partial Update Booking | Not working currently | Requires further investigation against the live API. |
| Delete Booking | Not working currently | Requires further investigation against the live API. |
| Health Check | Not working currently | Expected health-check response is not being received reliably. |

### Create Booking – Further Observation Required

Some Create Booking scenarios are working, but repeatable execution needs further stabilisation.

The framework generates dynamic booking data, including unique names and email addresses. However, booking conflicts and environment-dependent behaviour have been observed.

Further investigation is required to ensure that generated booking data is sufficiently unique and that repeated executions do not produce unexpected conflicts.

### Create Booking – Response Schema Verification Issue

An additional issue was identified during Create Booking response validation.

The supplied OpenAPI specification and the corresponding JSON response schema expect the booking response to contain `email` and `phone` fields. However, the actual Create Booking API response does not include these two fields.

As a result:

- The booking creation request can succeed and return HTTP `201 Created`.
- The response contains the generated booking ID and other booking details.
- Response schema validation fails because the required `email` and `phone` properties are missing.
- This indicates a mismatch between the documented API contract and the actual API response.

**Current observation:** Booking creation and response schema verification are separate outcomes. A successful HTTP response does not necessarily mean that response schema validation has passed.

Further investigation is required to confirm whether the OpenAPI specification or the live API response needs to be corrected. The existing contract expectations should be retained until the discrepancy is clarified.

### API Availability and Contract Considerations

The supplied OpenAPI YAML specification is used as the reference for:

- API endpoints and HTTP methods.
- Request parameters and payload structures.
- Mandatory fields and supported data types.
- Expected response structures and contract status codes.

Differences have been observed between the supplied API specification and some actual live API responses. These differences should be investigated and documented rather than changing expected results simply to make tests pass.

## Test Execution Results

The following results were recorded during a full Cucumber test execution:

| Result | Count |
|---|---:|
| Passed | 76 |
| Failed | 43 |
| Total scenario executions | 119 |

These figures represent scenario executions, including individual examples from Scenario Outlines, rather than individual test steps.

### Schema Validation Results

The separate schema validation execution completed successfully:

| Result | Count |
|---|---:|
| Tests executed | 2 |
| Passed | 2 |
| Failed | 0 |
| Errors | 0 |

**Build result:** Successful.

The schema validation result confirms that the schema validation tests passed locally. It does not confirm that all live API endpoints are working.

*Execution results are a snapshot from a particular test run. Results may vary depending on API availability, booking data, and environment state.*

## Running the Tests

Run all commands from the project root directory.

### 1. Compile the project

```bash
mvn test-compile
```

### 2. Run schema validation tests

```bash
mvn -Dtest=SchemaValidationTest test
```

### 3. Run retry utility tests

```bash
mvn -Dtest=RetryUtilsTest test
```

### 4. Run the complete Cucumber test suite

```bash
mvn clean test -Dtest=TestRunner
```

### 5. Run the Smoke Test scenarios

```bash
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@SmokeTest"
```

### 6. Run Create Booking scenarios

```bash
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@CreateBooking"
```

### 7. Run a specific scenario by tag

For example, to execute the UB_01 Update Booking scenario:

```bash
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@UB_01"
```

### 8. Run an individual feature file

```bash
mvn test -Dtest=TestRunner -Dcucumber.features=src/test/resources/features/create-booking.feature
```

Replace `create-booking.feature` with the feature file you want to execute.

## Logging and Reports

The framework uses Log4j2 for execution logging.

Cucumber execution reports are configured through the Maven test configuration.

### Cucumber Reports

Cucumber HTML and JSON reports are generated using the configured Maven Surefire plugin.

The reports use timestamped filenames to distinguish different test executions.


### ExtentReports Execution Report

The framework generates interactive HTML execution reports with scenario-level results, execution status, and test details.

![ExtentReports Execution Report](Screenshots/Execution Report.png)

The framework integrates **ExtentReports with Cucumber 7** using the ExtentReports Cucumber adapter.

ExtentReports generates a detailed HTML execution report with:

- Feature-wise execution details.
- Scenario-wise execution results.
- Pass, Fail, and Skip statuses.
- Execution summaries.
- Timestamped report directories.

**Configuration file:**

```text
src/test/resources/extent.properties
```

**Report output directory:**

```text
target/extent-reports/
```

Each execution generates a separate timestamped report directory, helping preserve previous execution reports.

The current configuration uses `Spark.html` as the report filename inside the timestamped directory.

### Publishing Execution Reports to GitHub

Generated ExtentReports can be committed to the repository to showcase test execution results.

If the report is under the Git-ignored `target/` directory, a specific report file can be force-added without changing `.gitignore`:

```bash
git add -f target/extent-reports/<report-folder>/Spark.html
git commit -m "Add ExtentReports execution results"
git push origin main
```

Before publishing reports to a public repository, ensure that they do not contain credentials, authentication tokens, cookies, or other sensitive information.

### Other Maven Reports and Logs

After execution, generated reports and test outputs can be found under:

```text
target/
```

Common Maven output locations include:

- `target/surefire-reports/` – Maven test execution reports.
- `target/cucumber-reports/` – Configured Cucumber HTML and JSON reports.
- `target/extent-reports/` – ExtentReports HTML execution reports.
- Configured logging output – Framework execution logs.

## Future Improvements

- Stabilise dynamic and unique booking test-data generation.
- Investigate live API failures across Get, Update, Partial Update, Delete, and Health Check.
- Verify positive, negative, and boundary scenarios against the live API.
- Review differences between the OpenAPI specification and actual API responses.
- Investigate the missing `email` and `phone` fields in the Create Booking response.
- Improve test repeatability and execution reliability.
- Update documentation as API functionality becomes stable.
- Introduce Continuous Integration when the framework is ready.

## Author

Hotel Booking API Automation Project