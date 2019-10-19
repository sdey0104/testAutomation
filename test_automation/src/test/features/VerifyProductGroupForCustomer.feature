 @souravTest
Feature: Verify Product group for customer

  Scenario Outline: To check product group by logging into my yellobrick

    Given  Loaded product group data from database
    When    <productgroup_id> exists in database
    Then    Customer navigates to myyellowbrick
    And     Login to myYellobrick with all retrieved_customer_number to validate productgroups

    Examples:
      | productgroup_id |
 #     | 22              |
      | 23              |
 #     | 423             |