@ordering
Feature: Regression end to end ordering
  #Regression end to end ordering (cards, P+passes, sleeves)
  #Card orders can be P+passes and/or Users (transponder cards)
  #Sleeve orders can be either stickers or sleeves.
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario Outline: Ordering from MyYellowbrick
    Given Customer logs in to "myyellowbrick" with 203198
    When  Customer navigates to "Order"
    And   Customer selects <value> of <Cardtype> and orders
    Then  Customer validates the order "Pending" status for "203198" having <Cardtype> of value <value>

    Examples:
      | Cardtype             | value |
      | Extra gebruiker      | 3     |
      | P+ pas               | 5     |
      | RaamSticker          | 6     |

  Scenario Outline: Administrator validates orders with single validation and updates customer order
   Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    And   Administrator finds customer "203198" in "YELLOWBRICK-BASIC" and chooses Single validation of <Cardtype>
    When  Administrator updates <Cardtype> value to <value>
    Then  Administrator validates the order "Order accepted" status for "203198" having <Cardtype> of value <value>

    Examples:
      | menu item                   | Cardtype           | value |
      | Card orders to be validated | Extra gebruiker    | 1     |
      | Card orders to be validated | P+ pas             | 2     |
      | Process sleeve orders       | RaamSticker        | 3     |

  Scenario Outline: Ordering from Taxameter
    Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    And   Administrator searches for customer "203198"
    And   Administrator navigates to <tab>
    When  Administrator orders <value> of <Cardtype>
    Then  Administrator validates the order "Pending" status for "203198" having <Cardtype> of value <value>

    Examples:
      | menu item | tab               | Cardtype          | value |
      | Customer  | Transponder cards | Extra gebruiker   | 3     |
      | Customer  | Transponder cards | RaamSticker       | 5     |
      | Customer  | Other cards       | P+ pas            | 6     |

  Scenario Outline: Administrator validates orders with Batch validation
    Given Administrator logs into Taxameter
    And   Administrator chooses <menu item> from menu
    When  Administrator chooses Batch validation for product group "YELLOWBRICK-BASIC"
    Then  Administrator validates Batch Order Accepted Status of <menu item>

    Examples:
      | menu item                   |
      | Card orders to be validated |
      | Process sleeve orders       |

#######  Needs to be implemented once QPark gets values ######
#  Scenario Outline: Administrator processes orders
#    Given Administrator logs into Taxameter
#    And   Administrator chooses <menu item> from menu
#    When  Administrator processes by generating file
#    Then  Administrator validates the generated file of the order
#
#    Examples:
#      | menu item                   |
#      | Card order processing       |
#      | Process sleeve orders       |









