# Hotel Booking API Automation Framework

A modular Java-based REST API automation framework designed to validate hotel booking services using **Rest Assured, Cucumber, Java 17, Maven, and JUnit 5**.

The framework focuses on API functional testing, authentication, request and response validation, negative testing, reusable automation components, and Behaviour-Driven Development (BDD).

The objective is to provide a maintainable and extensible automation solution that supports reliable validation of hotel booking API workflows.

---

## 1. Project Overview

### Purpose

This project demonstrates the design and implementation of a reusable REST API automation framework for a hotel booking application.

It automates API operations across the booking lifecycle, including authentication, booking creation, retrieval, modification, and deletion.

The framework separates API communication, test execution, configuration, test data, and validation logic to improve maintainability and reusability.

### Key Objectives

- Develop a reusable API automation framework using Java.
- Automate REST APIs using Rest Assured.
- Implement BDD using Cucumber and Gherkin.
- Validate HTTP status codes, response payloads, and API behaviour.
- Cover positive, negative, and boundary-value scenarios.
- Implement authentication and token management.
- Apply suitable design patterns to improve maintainability.
- Separate configuration and test data from automation logic.
- Support API contract and JSON schema validation.
- Generate readable test execution reports.

### Application Under Test

**Application:** Restful Booker

**Application URL:**  
https://automationintesting.online/

**API Base URL:**

```text
https://automationintesting.online/api
```

**API Documentation:**

- [Booking API Swagger](https://automationintesting.online/booking/swagger-ui/index.html)
- [Authentication API Swagger](https://automationintesting.online/auth/swagger-ui/index.html)

**OpenAPI Specification:**

The API specification is maintained locally:

`src/test/resources/spec/booking.yaml`

---

## 2. Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Maven | Build and dependency management |
| Rest Assured | REST API automation |
| Cucumber | Behaviour-Driven Development |
| Gherkin | Business-readable test scenarios |
| JUnit 5 | Test execution platform |
| Jackson | JSON serialization and deserialization |
| Lombok | Boilerplate code reduction |
| PicoContainer | Scenario-level dependency sharing |
| JSON Schema Validator | API response schema validation |
| SoapUI | Planned API mocking |
| Git | Version control |
| GitHub | Source code management |

---

## 3. Framework Architecture

The framework follows a modular, layered architecture. Each component has a specific responsibility, helping to separate test scenarios from API communication and validation logic.

### Architecture Diagram

```text
                  Cucumber Feature Files
                           |
                           v
                    Test Runner
                           |
                           v
                    Step Definitions
                           |
                           v
                    API Client Layer
                    /              \
                   v                v
              AuthClient       BookingClient
                   \                /
                    v              v
                Request Specification
                       Factory
                           |
                           v
                       Rest Assured
                           |
                           v
                    Hotel Booking API
```

### Supporting Components

```text
+------------------------------------------------+
|                Supporting Layer                |
+------------------------------------------------+
| Configuration Management                       |
| API Constants                                  |
| Authentication and Token Management            |
| Request and Response Models                    |
| Booking Test Data Builder                      |
| Scenario Context                               |
| Reusable API Assertions                        |
| Cucumber Hooks                                 |
| JSON Schema Validation                         |
| Test Reporting                                 |
+------------------------------------------------+
```

### Component Responsibilities

| Component | Responsibility |
|---|---|
| Feature Files | Define API scenarios in Gherkin |
| Test Runner | Configure and execute Cucumber tests |
| Step Definitions | Connect Gherkin steps to automation logic |
| API Clients | Encapsulate API endpoint interactions |
| RequestSpecFactory | Build reusable Rest Assured specifications |
| ApiConfig | Manage API configuration |
| TokenManager | Handle authentication token management |
| Models | Represent request and response payloads |
| BookingBuilder | Create reusable booking test data |
| ScenarioContext | Share data between steps within a scenario |
| CucumberHooks | Manage scenario-level setup and teardown |
| ApiAssertions | Centralize reusable API assertions |

---

## 4. Project Structure

The project follows a Maven-based directory structure.

```text
Hotel-Booking-API-Automation/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── framework/
│   │           ├── clients/
│   │           │   ├── AuthClient.java
│   │           │   └── BookingClient.java
│   │           │
│   │           ├── config/
│   │           │   └── ApiConfig.java
│   │           │
│   │           ├── constants/
│   │           │   └── ApiConstants.java
│   │           │
│   │           ├── models/
│   │           │   ├── Booking.java
│   │           │   └── Bookingdates.java
│   │           │
│   │           ├── specification/
│   │           │   └── RequestSpecFactory.java
│   │           │
│   │           └── utils/
│   │               └── TokenManager.java
│   │
│   └── test/
│       ├── java/
│       │   ├── assertions/
│       │   │   └── ApiAssertions.java
│       │   │
│       │   ├── com/booking/
│       │   │   ├── BookingBuilderTest.java
│       │   │   ├── builders/
│       │   │   │   └── BookingBuilder.java
│       │   │   └── stepdefinitions/
│       │   │       └── GetMessages.java
│       │   │
│       │   ├── context/
│       │   │   └── ScenarioContext.java
│       │   │
│       │   ├── framework/specification/
│       │   │   └── RequestSpecFactoryTest.java
│       │   │
│       │   ├── hooks/
│       │   │   └── CucumberHooks.java
│       │   │
│       │   ├── runners/
│       │   │   └── TestRunner.java
│       │   │
│       │   └── stepdefinitions/
│       │       ├── AuthSteps.java
│       │       ├── BookingSteps.java
│       │       ├── CreateBookingSteps.java
│       │       ├── DeleteBookingSteps.java
│       │       ├── GetBookingSteps.java
│       │       ├── HealthCheckSteps.java
│       │       ├── PartialUpdateBookingSteps.java
│       │       └── UpdateBookingSteps.java
│       │
│       └── resources/
│           ├── config/
│           │   ├── config-qa.properties
│           │   └── config-mock.properties
│           │
│           ├── features/
│           │   ├── authentication.feature
│           │   ├── boooking.feature
│           │   ├── create-booking.feature
│           │   ├── delete.feature
│           │   ├── get-booking.feature
│           │   ├── healthcheck.feature
│           │   ├── messages.feature
│           │   ├── partial-update-booking.feature
│           │   └── update-booking.feature
│           │
│           ├── schemas/
│           │   └── booking-response-schema.json
│           │
│           ├── spec/
│           │   └── booking.yaml
│           │
│           └── testdata/
│               └── booking.json
│
├── .gitignore
├── pom.xml
└── README.md
```

### Package Responsibilities

**framework.clients**

Contains API client classes that encapsulate endpoint interactions.

**framework.config**

Centralizes API configuration and environment-related settings.

**framework.constants**

Maintains reusable API constants such as endpoint paths and configuration keys.

**framework.models**

Contains Java models representing API request and response structures.

**framework.specification**

Provides reusable Rest Assured request specifications.

**framework.utils**

Contains utility components such as authentication token management.

**stepdefinitions**

Contains Cucumber step definitions that implement the actions described in feature files.

**context**

Manages shared scenario-level data.

**hooks**

Contains Cucumber lifecycle hooks.

**assertions**

Provides reusable API validation methods.

**builders**

Contains reusable test-data construction logic.

**resources**

Stores feature files, configuration, test data, API specifications, and schemas.

---

## 5. Design Patterns and Framework Principles

The framework uses several reusable design approaches.

### Builder Pattern

**Implementation:** `BookingBuilder`

The Builder pattern is used to construct booking test data.

It helps create reusable booking objects without duplicating object construction logic across test scenarios.

**Benefits:**
- Centralized test-data construction.
- Improved readability.
- Easier maintenance.
- Flexible booking data creation.

### Factory Pattern

**Implementation:** `RequestSpecFactory`

The Factory pattern is used to create reusable Rest Assured request specifications.

It centralizes common request configuration and reduces duplication across API clients.

**Benefits:**
- Consistent request configuration.
- Reduced duplication.
- Easier maintenance of common API settings.

### Scenario Context

**Implementation:** `ScenarioContext`

Scenario-level context is used to share data between Cucumber steps.

For example, a booking ID created in one step can be reused in subsequent retrieval, update, or deletion steps.

**Benefits:**
- Avoids unnecessary repeated API calls.
- Supports multi-step API workflows.
- Keeps scenario data isolated.

### Separation of Concerns

The framework separates:
- API communication.
- Configuration.
- Test data.
- Scenario execution.
- Assertions.
- Reporting.

This makes the framework easier to extend and maintain.

---

## 6. API Test Coverage

The framework covers the core hotel booking API lifecycle.

| API | Method | Test Objective |
|---|---|---|
| Health Check | GET | Verify service health |
| Authentication | POST | Obtain authentication token |
| Create Booking | POST | Create a booking |
| Get Booking | GET | Retrieve booking details |
| Update Booking | PUT | Replace booking details |
| Partial Update | PATCH | Update selected booking fields |
| Delete Booking | DELETE | Delete a booking |

### Functional Test Scenarios

**Health Check**
- Validate successful health-check response.
- Validate the returned service status.

**Authentication**
- Validate successful authentication.
- Validate invalid authentication credentials.
- Validate token handling.

**Create Booking**
- Create a booking using valid request data.
- Validate response status and booking details.
- Validate missing mandatory fields.
- Validate invalid field values.
- Validate booking date rules.

**Get Booking**
- Retrieve an existing booking.
- Validate booking response fields.
- Validate invalid booking identifiers.
- Validate authorization behaviour.

**Update Booking**
- Replace existing booking information.
- Validate updated response data.
- Validate invalid booking identifiers.
- Validate authorization behaviour.

**Partial Update Booking**
- Update selected booking attributes.
- Verify unchanged attributes remain intact.
- Validate invalid update requests.

**Delete Booking**
- Delete an existing booking.
- Validate deletion response.
- Verify subsequent retrieval behaviour.
- Validate authorization behaviour.

*The scenarios above describe the intended coverage areas. Actual scenario implementation and execution status should be verified against the feature files and test reports.*

---

## 7. Test Data Management

Test data is maintained separately from automation logic.

Current test-data resources:

```text
src/test/resources/testdata/booking.json
```

The framework uses `BookingBuilder` to construct booking objects from reusable test data.

The booking models represent request and response payloads, while Jackson supports JSON serialization and deserialization.

### Benefits

- Reduced hardcoded test data.
- Reusable booking objects.
- Easier maintenance.
- Better separation between test data and test logic.

---

## 8. Configuration Management

The framework maintains separate configuration files for different execution environments.

```text
src/test/resources/config/
    ├── config-qa.properties
    └── config-mock.properties
```

### Configuration Responsibilities

- API base URL.
- Environment-specific settings.
- Common endpoint configuration.
- Mock service configuration.

The configuration layer is managed through `ApiConfig`.

Sensitive credentials and tokens should not be committed to source control. They should be supplied through environment variables or a secure secrets-management solution.

*The exact configuration selection mechanism and supported command-line properties should be documented once verified in the implementation.*

---

## 9. Prerequisites

Install the following tools before running the framework:

- Java Development Kit 17.
- Apache Maven.
- Git.
- Eclipse or IntelliJ IDEA (optional).

Verify installations:

```bash
java -version
mvn -version
git --version
```

---

## 10. Setup and Installation

Clone the repository:

```bash
git clone https://github.com/manas5075/Hotel-Booking-API-Automation.git
```

Navigate to the project:

```bash
cd Hotel-Booking-API-Automation
```

Compile the project:

```bash
mvn clean compile
```

Compile test classes:

```bash
mvn test-compile
```

---

## 11. Test Execution

Execute the complete Maven test suite:

```bash
mvn clean test
```

Execute the Cucumber runner:

```bash
mvn clean test -Dtest=TestRunner
```

Capture test execution output locally:

```bash
mvn clean test -Dtest=TestRunner > test-run-log.txt 2>&1
```

### Cucumber Tag Execution

The Cucumber JUnit Platform engine supports tag filtering.

Once the corresponding tags are added to the feature scenarios, selected tests can be executed using:

```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

Other useful tag groups may include:

```text
@regression
@authentication
@booking
@negative
```

Only tags defined in the feature files will be selected.

---

## 12. Test Reporting

The framework uses Maven Surefire and Cucumber test execution infrastructure.

### Maven Surefire Reports

Maven Surefire generates execution reports under:

```text
target/surefire-reports/
```

These reports provide test execution summaries and failure information.

### Cucumber Reports

Cucumber HTML and JSON reports can provide:
- Feature-level execution results.
- Scenario-level status.
- Step-level results.
- Failure details.

The exact report paths depend on the Cucumber runner configuration.

### Allure Reporting

Allure is a potential future enhancement for:
- Rich test execution dashboards.
- Historical execution trends.
- Detailed test-step reporting.
- Attachments and failure diagnostics.

---

## 13. CI/CD Integration

Automated CI/CD execution is a planned enhancement.

GitHub Actions can be introduced to:
- Compile the Java project.
- Execute Maven test suites.
- Publish test execution results.
- Provide automated feedback on pull requests.

The pipeline will be introduced after the test suite and environment configuration are stable.

---

## 14. Troubleshooting

### Maven Build Failure

Check:
- Java 17 installation.
- Maven installation.
- Dependency resolution.
- Compilation errors.

Commands:

```bash
java -version
mvn -version
mvn clean test
```

### Undefined Cucumber Steps

Possible causes:
- Missing step definitions.
- Incorrect Cucumber glue configuration.
- Mismatch between Gherkin expressions and Java step annotations.

### API Connection Errors

Possible causes:
- API service unavailable.
- Incorrect base URL.
- Network connectivity issues.
- Environment configuration problems.

### Unexpected HTTP Status Codes

Check:
- Request payload.
- Authentication token.
- API endpoint.
- Expected API behaviour.
- External service availability.

### Booking Creation Conflicts

A booking API may reject requests because of application-specific validation or conflicting test data. Check the response body and server behaviour before changing assertions.

### Authentication Failures

Check:
- Credentials.
- Token generation.
- Token propagation.
- Authorization requirements for protected endpoints.

---

## 15. Future Enhancements

The framework will continue to evolve with the following improvements:

- Complete and stabilize all Cucumber scenarios.
- Expand positive, negative, and boundary-value testing.
- Improve authentication and token lifecycle management.
- Integrate SoapUI mock services.
- Enhance structured logging.
- Improve test isolation and execution reliability.
- Implement comprehensive Cucumber HTML reporting.
- Evaluate Allure reporting.
- Improve environment configuration and secrets management.
- Introduce GitHub Actions CI/CD after stabilizing the test suite.
- Strengthen API contract validation.
- Introduce reliable parallel execution where appropriate.

---

## 16. Author

**Manas Behera**

Java | REST API Automation | Rest Assured | Cucumber | Maven

**Project:** Hotel Booking API Automation Framework

**GitHub Repository:**

https://github.com/manas5075/Hotel-Booking-API-Automation