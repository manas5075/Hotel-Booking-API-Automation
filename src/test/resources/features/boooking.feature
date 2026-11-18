
@BookingAPI
Feature: Booking API

  @NegativeTest 
  Scenario: Verify booking API returns 500 for an unknown booking ID
    Given the booking API client is initialized
    When I send a GET request to an unknown booking endpoint
    Then the API response status code should be 500
    #Then the booking response should match the JSON schema
