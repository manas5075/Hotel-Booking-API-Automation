@BookingAPI
@PartialUpdateBooking
Feature: Partial Update Booking API
  Background:
    Given the booking API client is initialized

  @PositiveTest
  Scenario: Update firstname only
    Given a booking has been created successfully
    When I partially update the booking with firstname "UpdatedName"
    Then the API response status code should be 200
    And the booking firstname should be "UpdatedName"
    And the other booking details should remain unchanged

  @PositiveTest
  Scenario: Update lastname only
    Given a booking has been created successfully
    When I partially update the booking with lastname "UpdatedLastName"
    Then the API response status code should be 200
    And the booking lastname should be "UpdatedLastName"
    And the other booking details should remain unchanged

  @PositiveTest
  Scenario: Update depositpaid only
    Given a booking has been created successfully
    When I partially update the booking depositpaid to false
    Then the API response status code should be 200
    And the booking depositpaid should be false
    And the other booking details should remain unchanged

  @PositiveTest
  Scenario: Update multiple booking fields
    Given a booking has been created successfully
    When I partially update the booking with firstname "UpdatedName", lastname "UpdatedLastName" and depositpaid false
    Then the API response status code should be 200
    And the booking firstname should be "UpdatedName"
    And the booking lastname should be "UpdatedLastName"
    And the booking depositpaid should be false
    And the other booking details should remain unchanged

  @PositiveTest
  Scenario: Create, fully update and partially update a booking
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with valid details
    Then the API response status code should be 200
    When I partially update the booking with firstname "FinalUpdatedName"
    Then the API response status code should be 200
    And the booking firstname should be "FinalUpdatedName"
    And the other booking details should remain unchanged

  @NegativeTest
  Scenario: Reject partial update without authentication
    Given a booking has been created successfully
    And no authentication token is available
    When I partially update the booking with firstname "UpdatedName"
    Then the API response status code should be 401

  @NegativeTest
  Scenario: Reject partial update with invalid authentication
    Given a booking has been created successfully
    And an invalid authentication token is available
    When I partially update the booking with firstname "UpdatedName"
    Then the API response status code should be 401

  @NegativeTest
  Scenario Outline: Reject partial update with invalid request data
    Given a booking has been created successfully
    When I partially update the booking using test data "<fileName>"
    Then the booking partial update should be rejected with validation errors
    Examples:
      | fileName                      |
      | empty-partial-update.json     |
      | null-firstname.json           |
      | null-lastname.json            |
      | null-depositpaid.json         |
      | invalid-firstname-type.json   |
      | invalid-lastname-type.json    |
      | invalid-depositpaid-type.json |
      | empty-firstname.json          |
      | empty-lastname.json           |
      | empty-depositpaid.json        |