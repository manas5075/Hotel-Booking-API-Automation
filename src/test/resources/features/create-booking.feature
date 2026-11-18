
@BookingAPI
@CreateBooking
Feature: Create Booking API

  Background:
    Given the booking API client is initialized

  @PositiveTest @SmokeTest
  Scenario: Create booking with valid details
    When I create a booking with valid details
    Then the API response status code should be 200
    And the booking response should match the JSON schema
    And the booking ID should be generated
    And the created booking details should match the request

  @PositiveTest
  Scenario Outline: Create booking with valid boundary values
    When I create a booking with firstname length <firstnameLength>, lastname length <lastnameLength> and phone length <phoneLength>
    Then the API response status code should be 200
    And the booking ID should be generated

    Examples:
      | firstnameLength | lastnameLength | phoneLength |
      | 3               | 3              | 11          |
      | 18              | 18             | 21          |

  @NegativeTest
  Scenario Outline: Reject booking with invalid name lengths
    When I create a booking with firstname length <firstnameLength> and lastname length <lastnameLength>
    Then the booking creation should be rejected with validation errors

    Examples:
      | firstnameLength | lastnameLength |
      | 2               | 3              |
      | 19              | 3              |
      | 3               | 2              |
      | 3               | 19              |

  @NegativeTest
  Scenario Outline: Reject booking with invalid phone length
    When I create a booking with phone length <phoneLength>
    Then the booking creation should be rejected with validation errors

    Examples:
      | phoneLength |
      | 10          |
      | 22          |

  @NegativeTest
  Scenario Outline: Reject booking with invalid email
    When I create a booking with email "<email>"
    Then the booking creation should be rejected with validation errors

    Examples:
      | email              |
      | invalid-email      |
      | user@              |
      | @example.com       |
      | user.example.com   |

  @NegativeTest
  Scenario: Reject booking when checkout date is before checkin date
    When I create a booking with checkout date before checkin date
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when firstname is missing
    When I create a booking without the firstname field
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when lastname is missing
    When I create a booking without the lastname field
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when email is missing
    When I create a booking without the email field
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when phone is missing
    When I create a booking without the phone field
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when room ID is missing
    When I create a booking without the roomid field
    Then the booking creation should be rejected with validation errors

  @NegativeTest
  Scenario: Reject booking when depositpaid has invalid data type
    When I create a booking with depositpaid value "invalid"
    Then the booking creation should be rejected with validation errors