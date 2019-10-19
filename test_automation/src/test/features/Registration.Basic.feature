@RegBasic
Feature: End To End[Registration Activation StartParking]

  Scenario Outline: Register a customer with basic plan
    Given  Check <provider> and <plan> exist
    Given  Open the registration application for <provider> with <plan> plan
    When   Customer wants to register the application as <type>
    Then   Customer fills details in "Product Selection Page : 2 p+passes"
    And    Customer fills details in "Your Data page"
    And    Customer validate details in "Summary Page"
    And    Customer validate details in "Confirmation page" and select payment method as "debitcard"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    And    Activate if the customer type is <type> for the following data in <plan> for <provider>
      | Firstname|Lastname | COC       | VAT            |Status |
      |Virat     |Kohli    | 12345678  |  NL185323339B01|  2    |
    Then   Customer wait for "2" minutes to get the mails
    And     Customer validate receiving "Thank you" mail
    And     Customer validate receiving "Welcome" mail
    Then   System should have assigned the right productGroup to the customer
    And    Validate number of Qcards from database is "2"
    Examples:
      | type     | plan | provider |
      | Private  | FLEX | YB       |
      | Private  | SUBC | YB       |
      | Private  | BASC | YB       |
      | Business | BASC | YB       |
      | Business | SUBC | YB       |
      | Business | FLEX | YB       |
      | Private  | BASC | ANWB     |
      | Private  | SUBC | ANWB     |
      | Business | BASC | ANWB     |
      | Business | SUBC | ANWB     |
#      | Private  | BASC | ICS      |
#      | Private  | SUBC | ICS      |
#      | Business | BASC | ICS      |
#      | Business | SUBC | ICS      |