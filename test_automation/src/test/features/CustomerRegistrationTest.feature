@crossbrowsertest @regression
Feature: [CROSS BROWSER] Customer Registration

  Scenario: Register a customer in a chrome browser
    Given    Open the registration application for "YB" with "BASC" plan
    When    Customer wants to register the application as "Private"
    Then    Customer fills details in "Product Selection Page"
    And    Customer fills details in "Your Data page"
    And    Customer validate details in "Summary Page"
    And   Customer validate details in "Confirmation page" and select payment method as "debitcard"


  Scenario: Register a customer in a Firefox browser
    Given  Open the registration application for "YB" with "BASC" plan
    When   Customer wants to register the application as "Business"
    Then   Customer fills details in "Product Selection Page"
    And   Customer fills details in "Your Data page"
    And   Customer validate details in "Summary Page"
    And   Customer validate details in "Confirmation page" and select payment method as "debitcard"








	
