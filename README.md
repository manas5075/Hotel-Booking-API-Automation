# Hotel Booking API Automation Framework

A Java-based REST API automation framework designed to validate hotel booking services through reusable automation components, Behaviour-Driven Development (BDD), and structured API test scenarios.

The framework uses **Rest Assured, Java, Maven, Cucumber, and JUnit 5** to automate API workflows, validate HTTP responses, and verify business rules across booking and authentication services.

---

## Table of Contents

1. Project Overview
2. Key Objectives
3. Application Under Test
4. API Coverage
5. Technology Stack
6. Framework Architecture
7. Project Structure
8. Framework Design
9. Test Strategy
10. Prerequisites
11. Setup and Installation
12. Executing Tests
13. Test Reporting
14. Test Data Management
15. Mock API Testing
16. Future Enhancements
17. Author

---

## 1. Project Overview

This project implements a maintainable API automation framework for a hotel booking application.

The framework focuses on validating REST API functionality, request and response structures, authentication, booking lifecycle operations, and negative scenarios.

It follows a modular design to separate API clients, configuration, request specifications, data models, test steps, and test resources.

The framework aims to demonstrate practical implementation of API automation engineering principles, reusable components, BDD, test-data management, and maintainable test architecture.

## 2. Key Objectives

- Develop a reusable REST API automation framework using Java.
- Automate API endpoints using Rest Assured.
- Implement BDD scenarios using Cucumber and Gherkin.
- Validate HTTP status codes, response payloads, headers, and business rules.
- Cover positive, negative, and boundary-value scenarios.
- Validate authentication and protected API operations.
- Implement reusable API clients and request specifications.
- Maintain separation between test data, configuration, and automation logic.
- Support environment-specific execution.
- Integrate automated test reporting.
- Improve reliability through controlled API mocking and test isolation.

## 3. Application Under Test

**Application:** Restful Booker

**Application URL:**  
https://automationintesting.online/

**API Base URL:**

```text
https://automationintesting.online/api
```

**API Documentation:**

- [Booking API Documentation](https://automationintesting.online/booking/swagger-ui/index.html)
- [Authentication API Documentation](https://automationintesting.online/auth/swagger-ui/index.html)

### API Specification

The API specification is maintained within the repository:

```text
src/test/resources/spec/booking.yaml
```

This specification serves as a reference for endpoint definitions, request structures, response formats, and API validation requirements.

## 4. API Coverage

The framework is designed to cover the following API operations.

| API Operation | HTTP Method | Purpose |
|---|---|---|
| Health Check | GET | Verify service availability |
| Authentication | POST | Authenticate and obtain an access token |
| Create Booking | POST | Create a new booking |
| Get Booking | GET | Retrieve booking details |
| Update Booking | PUT | Replace booking information |
| Partial Update | PATCH | Modify selected booking fields |
| Delete Booking | DELETE | Remove an existing booking |

### Validation Coverage

- Successful API responses
- Invalid request payloads
- Missing mandatory fields
- Invalid data formats
- Booking date validation
- Invalid booking identifiers
- Authentication and authorization behaviour
- Response body validation
- HTTP status code validation
- JSON schema validation

*Note: The coverage list describes the intended framework scope. Individual scenarios and their current execution status are maintained in the feature files and test reports.*

## 5. Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Maven | Build and dependency management |
| Rest Assured | REST API automation |
| Cucumber | Behaviour-Driven Development |
| Gherkin | Readable test scenarios |
| JUnit 5 | Test execution platform |
| Jackson | JSON serialization and deserialization |
| Lombok | Boilerplate code reduction |
| PicoContainer | Scenario-level dependency sharing |
| JSON Schema Validator | Response contract validation |
| SoapUI | Planned API mocking and simulation |
| Git | Version control |
| GitHub | Source code management |

## 6. Framework Architecture

The framework follows a modular architecture with clear separation of responsibilities.

```text
Test Runner
    |
    v
Cucumber Feature Files
    |
    v
Step Definitions
    |
    v
API Clients
    |
    v
Request Specification Factory
    |
    v
Rest Assured
    |
    v
REST API
```

Supporting components include:

- Configuration management
- API constants
- Request and response models
- Test data builders
- Scenario context
- Shared assertions
- Cucumber hooks
- JSON schema definitions

## 7. Project Structure

```text
API_Testing_Kata/
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
│       │   ├── context/
│       │   │   └── ScenarioContext.java
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
│       │       ├── GetBookingSteps.java
│       │       ├── UpdateBookingSteps.java
│       │       ├── PartialUpdateBookingSteps.java
│       │       ├── DeleteBookingSteps.java
│       │       └── HealthCheckSteps.java
│       │
│       └── resources/
│           ├── config/
│           │   ├── config-qa.properties
│           │   └── config-mock.properties
│           │
│           ├── features/
│           │   ├── authentication.feature
│           │   ├── healthcheck.feature
│           │   ├── create-booking.feature
│           │   ├── get-booking.feature
│           │   ├── update-booking.feature
│           │   ├── partial-update-booking.feature
│           │   ├── delete.feature
│           │   └── messages.feature
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

*The structure above represents the current framework layout. Additional components and reporting configuration will be incorporated as the framework evolves.*

## 8. Framework Design

### API Client Layer

API clients encapsulate endpoint interactions and keep HTTP request logic separate from Cucumber step definitions.

Current clients:

- `AuthClient` – authentication-related API operations.
- `BookingClient` – booking-related API operations.

### Request Specification Factory

`RequestSpecFactory` provides reusable Rest Assured request specifications.

It helps centralize common request configuration such as base URI, content type, and request headers.

### Configuration Management

`ApiConfig` manages API configuration, while environment-specific property files separate QA and mock environment settings.

### Authentication Management

The authentication layer manages login operations and access-token handling for protected booking endpoints.

### Model Layer

Java models represent API request and response payloads.

Jackson supports JSON serialization and deserialization.

### Scenario Context

`ScenarioContext` provides scenario-level shared state between Cucumber steps, supporting workflows such as creating a booking and subsequently retrieving or updating it.

### Assertions

`ApiAssertions` provides reusable assertion logic to reduce duplication across test steps.

## 9. Test Strategy

The framework follows a layered API testing approach.

**Functional Testing**
- Verify endpoint functionality.
- Validate booking lifecycle operations.
- Verify authentication workflows.

**Negative Testing**
- Invalid request payloads.
- Missing mandatory fields.
- Invalid identifiers.
- Invalid authentication credentials or tokens.

**Contract Testing**
- Validate response structure.
- Verify mandatory response fields.
- Validate JSON schema where applicable.

**Integration Testing**
- Verify interactions between authentication and booking operations.
- Validate workflows involving multiple API calls.

**Test Data Validation**
- Use reusable test data and booking builders.
- Validate boundary values and business rules.

## 10. Prerequisites

Ensure the following tools are installed:

- Java Development Kit (JDK) 17
- Apache Maven
- Git
- Eclipse IDE or IntelliJ IDEA (optional)

Verify the installations:

```bash
java -version
mvn -version
git --version
```

## 11. Setup and Installation

Clone the repository:

```bash
git clone https://github.com/manas5075/Hotel-Booking-API-Automation.git
```

Navigate to the project:

```bash
cd Hotel-Booking-API-Automation
```

Build the project:

```bash
mvn clean compile
```

Install dependencies and compile tests:

```bash
mvn clean test-compile
```

## 12. Executing Tests

Execute the complete test suite:

```bash
mvn clean test
```

Execute the Cucumber test runner:

```bash
mvn clean test -Dtest=TestRunner
```

Capture execution logs locally:

```bash
mvn clean test -Dtest=TestRunner > test-run-log.txt 2>&1
```

Environment-specific execution can be configured through the project property files.

## 13. Test Reporting

The framework uses Maven and Cucumber test execution infrastructure.

Maven Surefire reports are generated under:

```text
target/surefire-reports/
```

Cucumber HTML and JSON reporting will be configured through the Cucumber runner.

The reporting strategy aims to provide:

- Scenario execution status
- Passed and failed test details
- Step-level execution information
- Failure messages and stack traces
- Execution summaries

Allure reporting is a potential future enhancement for richer dashboards, execution history, and trend analysis.

## 14. Test Data Management

Test data is maintained separately from automation logic.

Current test data resources include:

```text
src/test/resources/testdata/booking.json
```

The framework also uses a booking builder to support reusable booking test data creation.

This approach helps reduce hardcoded values and makes it easier to maintain test scenarios.

## 15. Mock API Testing

SoapUI is planned for controlled API mocking and response simulation.

Mock services will help validate automation behaviour against predictable responses, including:

- Successful responses
- Client and server error responses
- Invalid request payloads
- Authentication failures
- Edge cases and negative scenarios

Mock testing will complement integration testing against the live application.

## 16. Future Enhancements

The framework will continue to evolve with the following improvements:

- Complete and stabilize existing Cucumber scenarios.
- Expand positive, negative, and boundary-value coverage.
- Improve authentication and token lifecycle handling.
- Integrate SoapUI mock services.
- Enhance Cucumber HTML and JSON reporting.
- Evaluate Allure reporting.
- Introduce structured logging.
- Improve environment and test-data management.
- Add automated execution through GitHub Actions when the test suite is stable.
- Improve test isolation and support reliable parallel execution.
- Strengthen API contract validation.

## 17. Author

**Manas Behera**

Java | API Test Automation | Rest Assured | Cucumber | Maven

**Project:** Hotel Booking API Automation Framework

**Repository:**  
https://github.com/manas5075/Hotel-Booking-API-Automation