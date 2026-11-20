
@BookingAPI
@CreateBooking
Feature: Create Booking API
  Background:
    Given the booking API client is initialized

  @CB_01 @PositiveTest @SmokeTest
  Scenario: Create booking with valid details
    When I create a booking with valid details
    Then the API response status code should be 200
    And the booking response should match the JSON schema
    And the booking ID should be generated
    And the created booking details should match the request

  @CB_02 @PositiveTest
  Scenario Outline: Create booking with valid boundary values
    When I create a booking with firstname length <firstnameLength>, lastname length <lastnameLength> and phone length <phoneLength>
    Then the API response status code should be 200
    And the booking ID should be generated
    Examples:
      | firstnameLength | lastnameLength | phoneLength |
      | 3               | 3              | 11          |
      | 18              | 18             | 21          |

  @CB_03 @PositiveTest
  Scenario Outline: Create booking with valid JSON test data
    When I create a booking using test data "<fileName>"
    Then the API response status code should be 200
    And the booking response should match the JSON schema
    And the booking ID should be generated
    Examples:
      | fileName                    |
      | mandatory-fields-only.json  |
      | checkin-only.json           |
      | checkout-only.json          |
      | leap-year-dates.json        |
      | same-checkin-checkout-date.json |
      | negative-roomid.json        |
      | zero-roomid.json            |

  @CB_04 @NegativeTest
  Scenario Outline: Reject booking with invalid name lengths
    When I create a booking with firstname length <firstnameLength> and lastname length <lastnameLength>
    Then the booking creation should be rejected with validation errors
    Examples:
      | firstnameLength | lastnameLength |
      | 2               | 3              |
      | 19              | 3              |
      | 3               | 2              |
      | 3               | 19             |

  @CB_05 @NegativeTest
  Scenario Outline: Reject booking with invalid phone length
    When I create a booking with phone length <phoneLength>
    Then the booking creation should be rejected with validation errors
    Examples:
      | phoneLength |
      | 10          |
      | 22          |

  @CB_06 @NegativeTest
  Scenario Outline: Reject booking with invalid email
    When I create a booking with email "<email>"
    Then the booking creation should be rejected with validation errors
    Examples:
      | email              |
      | invalid-email      |
      | user@              |
      | @example.com       |
      | user.example.com   |

  @CB_07 @NegativeTest
  Scenario: Reject booking when checkout date is before checkin date
    When I create a booking with checkout date before checkin date
    Then the booking creation should be rejected with validation errors

  @CB_08 @NegativeTest
  Scenario: Reject booking when firstname is missing
    When I create a booking without the firstname field
    Then the booking creation should be rejected with validation errors

  @CB_09 @NegativeTest
  Scenario: Reject booking when lastname is missing
    When I create a booking without the lastname field
    Then the booking creation should be rejected with validation errors

  @CB_10 @NegativeTest
  Scenario: Reject booking when email is missing
    When I create a booking without the email field
    Then the booking creation should be rejected with validation errors

  @CB_11 @NegativeTest
  Scenario: Reject booking when phone is missing
    When I create a booking without the phone field
    Then the booking creation should be rejected with validation errors

  @CB_12 @NegativeTest
  Scenario: Reject booking when room ID is missing
    When I create a booking without the roomid field
    Then the booking creation should be rejected with validation errors

  @CB_13 @NegativeTest
  Scenario: Reject booking when depositpaid has invalid data type
    When I create a booking with depositpaid value "invalid"
    Then the booking creation should be rejected with validation errors

  @CB_14 @NegativeTest
  Scenario Outline: Reject booking with invalid JSON test data
    When I create a booking using test data "<fileName>"
    Then the booking creation should be rejected with validation errors
    Examples:
      | fileName                         |
      | missing-bookingdates.json        |
      | missing-depositpaid.json         |
      | invalid-roomid-type.json         |
      | invalid-firstname-type.json      |
      | invalid-lastname-type.json       |
      | invalid-bookingdates-type.json   |
      | invalid-email-type.json          |
      | invalid-phone-type.json          |
      | invalid-checkin-date-format.json |
      | invalid-checkout-date-format.json|
      | empty-firstname.json             |
      | empty-lastname.json              |
      | empty-email.json                 |
      | empty-phone.json                 |