
@AuthenticationAPI
Feature: Authentication API

  Background:
    Given the authentication API client is initialized

  @PositiveTest
  Scenario: Verify successful login with valid credentials
    When I send a login request with username "admin" and password "password"
    Then the authentication response status code should be 200
    And the authentication token should be generated

  @NegativeTest
  Scenario: Verify login with invalid credentials
    When I send a login request with username "invalid" and password "wrongpassword"
    Then the authentication response status code should be 401