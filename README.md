# Hotel Booking API Automation Framework

**Project maintained by:** Manas Behera

## 1. Project Overview

This project is a Java-based REST API test automation framework.

The objective is to demonstrate practical knowledge of API testing, Java programming, automation framework design, and Behaviour-Driven Development (BDD).

The framework uses **Rest Assured** to interact with REST APIs and **Cucumber** to define test scenarios in readable Gherkin syntax.

The application under test is a hotel booking system that provides REST endpoints for creating, retrieving, updating, and deleting bookings, as well as user authentication.

## 2. Project Objectives

The main objectives of this project are to:

- Build a reusable API test automation framework using Java.
- Automate REST API tests using Rest Assured.
- Implement BDD using Cucumber and Gherkin.
- Validate HTTP status codes, response bodies, and API behaviour.
- Cover positive and negative testing scenarios.
- Apply suitable design patterns to improve maintainability and reusability.
- Manage project configuration and test data separately.
- Use Git for version control and maintain a clear commit history.

## 3. Application Under Test

This uses the Restful Booker hotel booking application.

**Application:**  
https://automationintesting.online/

**API base URL:**
`https://automationintesting.online/api`

**API documentation:**

- [Booking API Swagger Documentation](https://automationintesting.online/booking/swagger-ui/index.html)
- [Authentication API Swagger Documentation](https://automationintesting.online/auth/swagger-ui/index.html)

### OpenAPI Specification

The OpenAPI specification is maintained locally in this repository:

`src/test/resources/spec/booking.yaml`

This file serves as the reference for API endpoints, request structures, response formats, and validation rules.

## 4. Technologies and Tools

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Maven | Build and dependency management |
| Rest Assured | REST API automation |
| Cucumber | BDD and Gherkin scenarios |
| JUnit 5 | Test execution |
| Jackson | JSON serialization and deserialization |
| Lombok | Java boilerplate reduction, where applicable |
| Git | Version control |
| GitHub | Source code repository |
| SoapUI | Planned API mocking and local response validation |


---

**Author:** Manas Behera

**Project:** Hotel Booking API Automation

**Purpose:** Java API Test Automation Practice