Feature:  Validate messages when IBAN validation fails.


  Scenario Outline: Validate error messages when IBAn validation fails in Registration.
    Given   Open the registration application with "basic" plan
    And     Customer wants to register the application as "Private"
    And     Customer fills details in "Product Selection Page"
    And     Customer fills details in "Your Data page"
    And     Customer validate details in "Summary Page"
    And     Customer validate details in "Confirmation page"
    And     Select payment method as "debitcard"
    And     Customer validates IBAN
    When    IBAN validation fails because of <reason>
    Then    Customer validates the <error message>

    Examples:
      | reason     | error message |
      | Cancelled  | xyz           |
      | systom off | uyt           |

  Scenario Outline: Validate error messages when IBAn validation fails in MyYellowbrick.
    Given   Customer logs in to "myyellowbrick" with "220600"
    And     Customer navigates to "Payment Method"
    And     Customer selects "direct debit" payment method
    And     Customer validates IBAN
    When    IBAN validation fails because of <reason>
    Then    Customer validates the <error message>

    Examples:
      | reason     | error message |
      | Cancelled  | xyz           |
      | systom off | uyt           |



