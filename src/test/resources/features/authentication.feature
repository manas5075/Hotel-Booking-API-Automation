@AuthenticationAPI
Feature: Authentication API
  Background:
    Given the authentication API client is initialized

  @AUTH_01 @PositiveTest @SmokeTest
  Scenario: Verify successful login with valid credentials
    When I send a login request with username "admin" and password "password"
    Then the authentication response status code should be 200
    And the authentication token should be generated

  @AUTH_02 @NegativeTest
  Scenario: Verify login with invalid credentials
    When I send a login request with username "invalid" and password "wrongpassword"
    Then the authentication response status code should be 401

  @AUTH_03 @NegativeTest
  Scenario: Reject login when password is missing
    When I send a login request using test data "authentication/missing-password.json"
    Then the authentication response should indicate invalid request data

  @AUTH_04 @NegativeTest
  Scenario: Reject login when username is missing
    When I send a login request using test data "authentication/missing-username.json"
    Then the authentication response should indicate invalid request data

  @AUTH_05 @NegativeTest
  Scenario: Reject login when both credentials are missing
    When I send a login request using test data "authentication/missing-both-credentials.json"
    Then the authentication response should indicate invalid request data

  @AUTH_06 @NegativeTest
  Scenario: Reject login when password is null
    When I send a login request using test data "authentication/null-password.json"
    Then the authentication response should indicate invalid request data

  @AUTH_07 @NegativeTest
  Scenario: Reject login when username is null
    When I send a login request using test data "authentication/null-username.json"
    Then the authentication response should indicate invalid request data

  @AUTH_08 @NegativeTest
  Scenario: Reject login when username has an invalid data type
    When I send a login request using test data "authentication/invalid-username-type.json"
    Then the authentication response should indicate invalid request data

  @AUTH_09 @NegativeTest
  Scenario: Reject login when password has an invalid data type
    When I send a login request using test data "authentication/invalid-password-type.json"
    Then the authentication response should indicate invalid request data

  @AUTH_10 @NegativeTest
  Scenario: Reject login when username is empty
    When I send a login request using test data "authentication/empty-username.json"
    Then the authentication response status code should be 401

  @AUTH_11 @NegativeTest
  Scenario: Reject login when password is empty
    When I send a login request using test data "authentication/empty-password.json"
    Then the authentication response status code should be 401

  @AUTH_12 @NegativeTest
  Scenario: Reject login when both username and password are empty
    When I send a login request using test data "authentication/empty-both-credentials.json"
    Then the authentication response status code should be 401

  @AUTH_13 @NegativeTest
  Scenario: Reject login when username contains leading and trailing spaces
    When I send a login request using test data "authentication/username-with-whitespace.json"
    Then the authentication response status code should be 401

  @AUTH_14 @NegativeTest
  Scenario: Test with long username and password
    When I send a login request using test data "authentication/long-credentials.json"
    Then the authentication response status code should be 401