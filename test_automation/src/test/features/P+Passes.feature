@PPlusPasses @regression
Feature: Regression testing for P+pass updates.
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario Outline: Change P+Passes in MyYellowbrick
    Given Customer logs in to "myyellowbrick" with <customer number>
    When  Customer navigates to Pplus pass
    Then  Customer updates ppluspass <Change> to <value>
    And   Customer validates ppluspass <Change> as <value>
    Examples:
     | customer number | Change | value               |
     |     203198      | User   | transpondercard     |
     |     203198      | State  | Geblokkeerd         |
     |     203198      | State  | Actief              |

  Scenario Outline: Unlink or Link P+Passes in Taxameter at Customerdetails to transpondercard.
    Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    And   Administrator searches for customer "203198"
    And   Administrator navigates to <tab>
    When  Administrator <links> PPlusPass to Transpondercard
    Then  Administrator validates <links> for PPluspass to Transpondercard
    Examples:
      | menu item | tab         | links  |
      | Customer  | Other cards | Unlink |
      | Customer  | Other cards | Link   |

  Scenario Outline: Block, activate P+Passes in Taxameter at P+Passes
    Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    And   Administrator searches for pplupass and validates if the "old" status is <status>
    When  Administrator changes PPlusPass status to <changed>
    Then  Administrator searches for pplupass and validates if the "new" status is <changed>

    Examples:
      |menu item| status     |changed       |
      |P+ cards | Actief     |Geblokkeerd   |
      |P+ cards |Geblokkeerd |Actief        |
      |P+ cards | Actief     |Ingeleverd    |

  Scenario Outline: Assign P+Passes to a customer in Taxameter
    Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    And   Administrator searches for pplupass and validates if the "old" status is <status>
    When  Administrator assigns customer to PPlusPass
    Then  Administrator validates the Customer Reference for PPlusPass

    Examples:
      |menu item  | status    |
      | P+ cards  | Ingeleverd|

  Scenario Outline: Revoke P+Pass in Pending status
    Given   Customer logs in to "myyellowbrick" with 203198
    When    Customer navigates to "Order"
    And     Customer selects <value> of <Cardtype> and orders
    And     Customer revokes PPlusPass
    Then   Customer validates the order "Revoked" status for "203198" having <Cardtype> of value <value>
    Examples:
      | Cardtype          | value |
      | P+ pas            | 6     |



