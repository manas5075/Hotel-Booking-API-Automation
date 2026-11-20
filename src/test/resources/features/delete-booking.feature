@BookingAPI
@DeleteBooking
Feature: Delete Booking API
  Background:
    Given the booking API client is initialized

  @DB_01 @PositiveTest @SmokeTest
  Scenario: Delete an existing booking
    Given a booking has been created successfully
    And I am authenticated
    When I delete the booking
    Then the API response status code should be 201

  @DB_02 @PositiveTest
  Scenario: Verify a booking cannot be retrieved after deletion
    Given a booking has been created successfully
    And I am authenticated
    When I delete the booking
    Then the API response status code should be 201
    When I retrieve the deleted booking
    Then the API should return an appropriate error response

  @DB_03 @NegativeTest
  Scenario: Reject booking deletion without authentication
    Given a booking has been created successfully
    And no authentication token is available
    When I delete the booking
    Then the API response status code should be 401

  @DB_04 @NegativeTest
  Scenario: Reject booking deletion with invalid authentication
    Given a booking has been created successfully
    And an invalid authentication token is available
    When I delete the booking
    Then the API response status code should be 401

  @DB_05 @NegativeTest
  Scenario: Delete a booking with an unknown ID
    When I delete a booking with an unknown ID
    Then the API should return an appropriate error response

  @DB_06 @NegativeTest
  Scenario: Reject deletion with booking ID zero
    When I delete a booking with ID 0
    Then the API should return an appropriate error response

  @DB_07 @NegativeTest
  Scenario: Reject deletion with a negative booking ID
    When I delete a booking with ID -1
    Then the API should return an appropriate error response