Feature: Rest API Smoke Test

  Scenario:   Customer logs in and performs transaction related actions
    Given     Customer logs in through "Mobile"
    When      Customer performs a "Start Parking" transaction
    Then      Customer should have open transactions
    Then      Customer can stop transaction
    Then      Customer can get transaction history
    Then      Customer can get nearby parking zones
    Then      Customer can get nearby parking advice
    Then      Customer can get news
    Then      Customer can get user details
    Then      Customer can get disclaimer
    Then      Customer can get localized message
    Then      Customer can update user information
    Then      Customer can reset password
    Then      Customer can signup
