@manualvalidationIBAN
Feature: Regression for manual activation with Iban validation.

  Scenario Outline: Register a private customer that cancels IBAN validation. After cancelling, continues with registration and passes IBAN validation.
                    Customer requires manual activation in Taxameter.
    Given   Check for the existence of following data in Database for "Private-Blacklisted"
      | Firstname | Lastname | Birthday   | Licenseplate | Status |
      | Olaf      | Elsa     | 02-02-2001 | AU-TO-12     | 3      |
    And     Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber  |
      | AU-TO-12     | +31633715309 |
    And    Customer enters this information and validates Errors in "Your Data page"
      | Firstname | Lastname | Birthdate  | Phone        | Email                    | Repeat email              | Zip code | House number | Address | City   |
      | Olaf      | Elsa     | 02-02-2001 | +31633715309 | fortest@brickparking.com | fortest@brickparking.com  | 5611CE   | 12           | markt   | Almere |
    And     Customer validate details in "Summary Page" with the following data
      | Email                     | Phone        |
      | fortest@brickparking.com  | +31633715309 |
    And Customer completes registration with direct debit
    And Customer cancels IBAN validation
    And Customer navigates back to "Your Data page"
    And Customer enters this information and validates Errors in "Your Data page"
      | Firstname | Lastname | Birthdate  | Phone        | Email                     | Repeat email              | Zip code | House number | Address | City   |
      | Olaf      | Elsa     | 02-02-2001 | +31633715309 | testteam@brickparking.com | testteam@brickparking.com | 5611CE   | 12           | markt   | Almere |
    And     Customer validate details in "Summary Page" with the following data
      | Email                     | Phone        |
      | testteam@brickparking.com | +31633715309 |
    And Customer completes registration with direct debit
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <registrationemail> mail for "Olaf Elsa"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname | Lastname |
      | Olaf      | Elsa     |
    Then   Administrator validates the following data for "Private-Blacklisted" in Taxameter and Database
      | Firstname | Lastname | Birthday   | Licenseplate | Status |
      | Olaf      | Elsa     | 02-02-2001 | AU-TO-12     | 1      |
    And   Administrator <decide> validation
    Then   Customer wait for "2" minutes to get the mails
    And   Customer checks <confirmationemail> mail for "Olaf Elsa"

    Examples:
      | provider | plan | type    | registrationemail | decide   | confirmationemail |
      | YB       | BASC | Private | Manual validation | Activate | Welcome           |
      | YB       | BASC | Private | Manual validation | Reject   | Close             |
