Feature:
  Customer switches payplan in MyYellowbrick. Private and Business customers are able to change plans
  Starting date explanation:
  Going from Basic -> Flex -> Subscription, the start date will be the next Monday after switch date.
  Going from Subscription -> Flex -> Basic, the start date will be on the third Monday after switch date.

  Scenario Outline:
  Private and Business customer switches from Basic to Flex to Subscription and starts/stops transaction to check if correct costs are used.
  Also administrator will check the subscription details of the customer.
    Given Customer logs in to "myyellowbrick" with <Customer Number>
    And   Customer navigates to "My subscription"
    And   Customer has <Payplan1>
    And   Administrator logs into Taxameter
    And   Administrator validates <Customer Number> "subscription details" before switch
    When  Customer switches to <Payplan2>
    And   Administrator validates <Customer Number> "subscription details" pending switch
    And   Customer cancels switch
    And   Customer switches to <Payplan2>
    And   Customer validates the start date of the switch.
    And   Administrator validates <Customer Number> "subscription" details after switch
    Then  Customer has new payplan on start date
    When    Customer starts parking with details below
      | Licence plate | PH-VL-07 |
      | Zone code     | 1        |
      | Remark        | test     |
    Then  Customer validates the start transaction from the table
      | Licence plate | PH-VL-07 |
      | Zone code     | 1        |
      | Provider      | Haarlem  |
    And Customer Stops parking from "myyellowbrick"
      | Action | Stop |
    And Customer validate stop parking in "myyellowbrick"
    And   Customer validates transaction costs after switch.

    Examples:
      | Customer Number | Payplan1     | Payplan2     |
      | 3214085         | Basic        | Flex         |
      | 3214064         | Basic        | Flex         |
      | 3214085         | Flex         | Subscription |
      | 3214064         | Flex         | Subscription |
      | 3214085         | Subscription | Flex         |
      | 3214064         | Subscription | Flex         |
      | 3214085         | Flex         | Basic        |
      | 3214064         | Flex         | Basic        |
      | 3214085         | Basic        | Subscription |
      | 3214064         | Basic        | Subscription |
      | 3214085         | Subscription | Basic        |
      | 3214064         | Subscription | Basic        |

  # Feature:
  # Check if the migration of the customers with subscription of productgroup 11 are migrated to productgroup 23
  # This is a onetime testcase to check if the migration goes well
  # It's done with Liquibase.
  # Ability: successful

  # Feature:
  # Generate the invoice after each switch.



