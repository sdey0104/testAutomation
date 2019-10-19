@myybchangepaymentmethod @regression
Feature: Test change payment method via MYB and upload mandate
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario: Change payment method to Direct Debit
    Given   Customer logs in to "myyellowbrick" with 203198
    When    Customer navigates to "Payment Method"
    Then Customer selects "direct debit" payment method
    And Customer enters "direct debit" details
  #  And Customer "direct debit" details are saved for "203198"

  Scenario: Customer wants to change payment method to CC
    Given   Customer logs in to "myyellowbrick" with 203198
    When    Customer navigates to "Payment Method"
    Then Customer selects "credit card" payment method
    And Customer enters "credit card" details
   # And Customer "credit card" details are saved for "203198"

 # Scenario: Customer wants to upload a mandate
   # Given   Customer logs in to "myyellowbrick" with "203198"
   # When    Customer navigates to "Payment Method"
   # Then Customer uploads "mandate"
   # And Customer verifies that "mandate is uploaded"
    