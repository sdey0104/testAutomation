@ChangePassword @regression
Feature: Verify that changing the password works
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario: Change password
    Given   Customer logs in to "myyellowbrick" with 203198
    And     Customer navigates to change password screen
    When    Customer resets password
    Then    Customer logs in to My Yellowbrick using "203198" with the new password
    Then    Customer changes the password to the original one

  Scenario: Reset password if you lost it
    Given   Customer navigates to myyellowbrick
    Then    Customer navigates to reset password screen
    Then    Customer inserts customer number "203198"
    Then    Customer validate receiving "Forgot password" mail
    Then    Customer sets new password in "Reset password screen"
    Then    Customer logs in to myyellowbrick with "203198"