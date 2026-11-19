
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

  @NegativeTest
  Scenario: Reject login when password is missing
    When I send a login request using test data "authentication/missing-password.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when username is missing
    When I send a login request using test data "authentication/missing-username.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when both credentials are missing
    When I send a login request using test data "authentication/missing-both-credentials.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when password is null
    When I send a login request using test data "authentication/null-password.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when username is null
    When I send a login request using test data "authentication/null-username.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when username has an invalid data type
    When I send a login request using test data "authentication/invalid-username-type.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when password has an invalid data type
    When I send a login request using test data "authentication/invalid-password-type.json"
    Then the authentication response should indicate invalid request data

  @NegativeTest
  Scenario: Reject login when username is empty
    When I send a login request using test data "authentication/empty-username.json"
    Then the authentication response status code should be 401

  @NegativeTest
  Scenario: Reject login when password is empty
    When I send a login request using test data "authentication/empty-password.json"
    Then the authentication response status code should be 401

  @NegativeTest
  Scenario: Reject login when both username and password are empty
    When I send a login request using test data "authentication/empty-both-credentials.json"
    Then the authentication response status code should be 401

  @NegativeTest
  Scenario: Reject login when username contains leading and trailing spaces
    When I send a login request using test data "authentication/username-with-whitespace.json"
    Then the authentication response status code should be 401

  @NegativeTest
  Scenario: Test with long username and password
    When I send a login request using test data "authentication/long-credentials.json"
    Then the authentication response status code should be 401