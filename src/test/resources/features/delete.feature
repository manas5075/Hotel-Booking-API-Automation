
@BookingAPI
@DeleteBooking
Feature: Delete Booking API

  Background:
    Given the booking API client is initialized

  @PositiveTest
  Scenario: Delete an existing booking
    Given a booking has been created successfully
    When I delete the booking
    Then the API response status code should be 201

  @PositiveTest
  Scenario: Verify a booking cannot be retrieved after deletion
    Given a booking has been created successfully
    When I delete the booking
    Then the API response status code should be 201
    When I retrieve the deleted booking
    Then the API should return an appropriate error response

  @NegativeTest
  Scenario: Reject booking deletion without authentication
    Given a booking has been created successfully
    And no authentication token is available
    When I delete the booking
    Then the API response status code should be 401

  @NegativeTest
  Scenario: Reject booking deletion with invalid authentication
    Given a booking has been created successfully
    And an invalid authentication token is available
    When I delete the booking
    Then the API response status code should be 401

  @NegativeTest
  Scenario: Delete a booking with an unknown ID
    When I delete a booking with an unknown ID
    Then the API should return an appropriate error response