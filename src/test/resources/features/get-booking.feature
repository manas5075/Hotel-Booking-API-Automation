@BookingAPI
@GetBooking
Feature: Get Booking API
Background:
  Given the booking API base URL is configured

  @GB_01 @PositiveTest @SmokeTest
  Scenario: Retrieve an existing booking
    Given I am authenticated
    And a booking has been created successfully
    When I retrieve the booking by its ID
    Then the API response status code should be 200
    And the booking response should contain valid booking details

  @GB_02 @NegativeTest
  Scenario: Retrieve a booking with an unknown ID
    When I retrieve a booking with an unknown ID
    Then the API should return an appropriate error response

  @GB_03 @NegativeTest
  Scenario: Retrieve a booking without an authentication token
    Given no authentication token is available
    When I retrieve the booking by its ID
    Then the API response status code should be 401
    And the response should contain an authorization error

  @GB_04 @NegativeTest
  Scenario: Retrieve a booking with an invalid authentication token
    Given an invalid authentication token is available
    When I retrieve the booking by its ID
    Then the API response status code should be 401
    And the response should contain an authorization error

  @GB_05 @NegativeTest
  Scenario Outline: Retrieve a booking with a boundary ID
    Given I am authenticated
    When I retrieve a booking using ID <bookingId>
    Then the API should return an appropriate error response
    Examples:
      | bookingId |
      | 0         |
      | -1        |