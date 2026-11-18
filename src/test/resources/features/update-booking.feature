
@BookingAPI
@UpdateBooking
Feature: Update Booking API

  Background:
    Given the booking API client is initialized

  @PositiveTest
  Scenario: Update an existing booking with valid details
    Given a booking has been created successfully
    When I update the booking with valid details
    Then the API response status code should be 200
    And the updated booking details should match the request

  @PositiveTest
  Scenario: Update booking with minimum valid field lengths
    Given a booking has been created successfully
    When I update the booking with firstname length 3, lastname length 3 and phone length 11
    Then the API response status code should be 200
    And the updated booking details should match the request

  @PositiveTest
  Scenario: Update booking with maximum valid field lengths
    Given a booking has been created successfully
    When I update the booking with firstname length 18, lastname length 18 and phone length 21
    Then the API response status code should be 200
    And the updated booking details should match the request

  @NegativeTest
  Scenario Outline: Reject update with invalid name lengths
    Given a booking has been created successfully
    When I update the booking with firstname length <firstnameLength> and lastname length <lastnameLength>
    Then the booking update should be rejected with validation errors

    Examples:
      | firstnameLength | lastnameLength |
      | 2               | 3              |
      | 19              | 3              |
      | 3               | 2              |
      | 3               | 19              |

  @NegativeTest
  Scenario Outline: Reject update with invalid phone length
    Given a booking has been created successfully
    When I update the booking with phone length <phoneLength>
    Then the booking update should be rejected with validation errors

    Examples:
      | phoneLength |
      | 10          |
      | 22          |

  @NegativeTest
  Scenario Outline: Reject update with invalid email
    Given a booking has been created successfully
    When I update the booking with email "<email>"
    Then the booking update should be rejected with validation errors

    Examples:
      | email            |
      | invalid-email    |
      | user@            |
      | @example.com     |
      | user.example.com |

  @NegativeTest
  Scenario: Reject update with checkout date before checkin date
    Given a booking has been created successfully
    When I update the booking with checkout date before checkin date
    Then the booking update should be rejected with validation errors

  @NegativeTest
  Scenario: Reject update without authentication
    Given a booking has been created successfully
    And no authentication token is available
    When I update the booking with valid details
    Then the API response status code should be 401