
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
  Scenario Outline: Reject login when required credentials are missing
    When I send a login request using test data "<fileName>"
    Then the authentication response should indicate invalid request data
    Examples:
      | fileName                                     |
      | authentication/missing-password.json         |
      | authentication/missing-username.json         |
      | authentication/missing-both-credentials.json |

  @AUTH_04 @NegativeTest
  Scenario Outline: Reject login when credentials contain null values
    When I send a login request using test data "<fileName>"
    Then the authentication response should indicate invalid request data
    Examples:
      | fileName                          |
      | authentication/null-password.json |
      | authentication/null-username.json |

  @AUTH_05 @NegativeTest
  Scenario Outline: Reject login when credentials have invalid data types
    When I send a login request using test data "<fileName>"
    Then the authentication response should indicate invalid request data
    Examples:
      | fileName                                   |
      | authentication/invalid-username-type.json  |
      | authentication/invalid-password-type.json  |

  @AUTH_06 @NegativeTest
  Scenario Outline: Reject login with empty credentials
    When I send a login request using test data "<fileName>"
    Then the authentication response status code should be 401
    Examples:
      | fileName                                     |
      | authentication/empty-username.json           |
      | authentication/empty-password.json           |
      | authentication/empty-both-credentials.json   |

  @AUTH_07 @NegativeTest
  Scenario: Reject login when username contains leading and trailing spaces
    When I send a login request using test data "authentication/username-with-spaces.json"
    Then the authentication response status code should be 401

  @AUTH_08 @NegativeTest
  Scenario: Reject login with long username and password
    When I send a login request using test data "authentication/auth-long-credentials.json"
    Then the authentication response status code should be 401