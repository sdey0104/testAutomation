@personalDetails @regression
Feature: Changing user's personal details
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario: Check update user's personal info functionality
    Given   Customer logs in to "myyellowbrick" with 203198
    When    Customer navigates to "Personal details"
    Then    Updating personal information with test data
    And     Compare updated info with database
    Then    Restore personal information with test data

