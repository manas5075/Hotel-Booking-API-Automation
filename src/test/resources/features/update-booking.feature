@BookingAPI
@UpdateBooking
Feature: Update Booking API
Background:
  Given the booking API base URL is configured

  @UB_01 @PositiveTest @SmokeTest
  Scenario: Update an existing booking with valid details
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with valid details
    Then the API response status code should be 200
    And the updated booking details should match the request

  @UB_02 @PositiveTest
  Scenario: Update booking with minimum valid field lengths
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with firstname length 3, lastname length 3 and phone length 11
    Then the API response status code should be 200
    And the updated booking details should match the request

  @UB_03 @PositiveTest
  Scenario: Update booking with maximum valid field lengths
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with firstname length 18, lastname length 18 and phone length 21
    Then the API response status code should be 200
    And the updated booking details should match the request

  @UB_04 @NegativeTest
  Scenario Outline: Reject update with invalid name lengths
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with firstname length <firstnameLength> and lastname length <lastnameLength>
    Then the booking update should be rejected with validation errors
    Examples:
      | firstnameLength | lastnameLength |
      | 2               | 3              |
      | 19              | 3              |
      | 3               | 2              |
      | 3               | 19             |

  @UB_05 @NegativeTest
  Scenario Outline: Reject update with invalid phone length
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with phone length <phoneLength>
    Then the booking update should be rejected with validation errors
    Examples:
      | phoneLength |
      | 10          |
      | 22          |

  @UB_06 @NegativeTest
  Scenario Outline: Reject update with invalid email
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with email "<email>"
    Then the booking update should be rejected with validation errors
    Examples:
      | email            |
      | invalid-email    |
      | user@            |
      | @example.com     |
      | user.example.com |

  @UB_07 @NegativeTest
  Scenario: Reject update with checkout date before checkin date
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking with checkout date before checkin date
    Then the booking update should be rejected with validation errors

  @UB_08 @NegativeTest
  Scenario: Reject update without authentication
    Given no authentication token is available
    When I update the booking with valid details
    Then the API response status code should be 401

  @UB_09 @NegativeTest
  Scenario Outline: Reject update with missing required fields
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking using test data "<fileName>"
    Then the booking update should be rejected with validation errors
    Examples:
      | fileName                 |
      | missing-bookingdates.json |
      | missing-depositpaid.json  |

  @UB_10 @NegativeTest
  Scenario Outline: Reject update with invalid field data types
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking using test data "<fileName>"
    Then the booking update should be rejected with validation errors
    Examples:
      | fileName                    |
      | invalid-roomid-type.json    |
      | invalid-firstname-type.json |
      | invalid-lastname-type.json  |
      | invalid-bookingdates-type.json |
      | invalid-email-type.json     |
      | invalid-phone-type.json     |

  @UB_11 @NegativeTest
  Scenario Outline: Reject update with invalid date formats
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking using test data "<fileName>"
    Then the booking update should be rejected with validation errors
    Examples:
      | fileName                         |
      | invalid-checkin-date-format.json |
      | invalid-checkout-date-format.json |

  @UB_12 @NegativeTest
  Scenario Outline: Reject update with empty field values
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking using test data "<fileName>"
    Then the booking update should be rejected with validation errors
    Examples:
      | fileName            |
      | empty-firstname.json |
      | empty-lastname.json  |
      | empty-email.json     |
      | empty-phone.json     |

  @UB_13 @NegativeTest
  Scenario Outline: Reject update with invalid room ID
    Given a booking has been created successfully
    And I am authenticated
    When I update the booking using test data "<fileName>"
    Then the booking update should be rejected with validation errors
    Examples:
      | fileName             |
      | negative-roomid.json |
      | zero-roomid.json     |

  @UB_14 @NegativeTest
  Scenario: Reject update with invalid authentication token
    Given an invalid authentication token is available
    When I update the booking with valid details
    Then the API response status code should be 401