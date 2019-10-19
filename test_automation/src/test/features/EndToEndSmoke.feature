@endtoendmyb  @smoke @regression
Feature: End To End[Registration Activation StartParking]

  Scenario: Register a customer in a chrome browser
    Given  Open the registration application for "YB" with "BASC" plan
    When    Customer wants to register the application as "Private"
    Then   Customer fills details in "Product Selection Page : 2 p+passes"
    And     Customer fills details in "Your Data page"
    And     Customer validate details in "Summary Page"
    And     Customer validate details in "Confirmation page" and select payment method as "debitcard"
    Then    Customer wait for "2" minutes to get the mails
    And     Customer validate receiving "Thank you" mail
    And     Customer validate receiving "Welcome" mail
    And    Validate number of Qcards from database is "2"

  Scenario: Activate new customer
    Given   Customer validate receiving "Welcome" mail
    When    Customer sets new password in "Registration"
    Then    Customer logs in to myyellowbrick

  Scenario: User starts parking
    Given Customer logs in to myyellowbrick
    Then Customer starts parking
    And Customer wait for "5" minutes to get the mails
    And Customer Stops parking from "myyellowbrick"
      | Action | Stop |

