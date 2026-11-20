@HealthAPI
Feature: Health Check API
  Background:
    Given the health API client is initialized

  @HC_01 @PositiveTest @SmokeTest
  Scenario: Verify API health status
    When I send a GET request to the health endpoint
    Then the API response status code should be 200
    And the health response status should be "UP"

  @HC_02 @PositiveTest
  Scenario: Verify health response contains status field
    When I send a GET request to the health endpoint
    Then the API response status code should be 200
    And the health response should contain the "status" field