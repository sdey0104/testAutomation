@registrationFieldValidation @regression
Feature: Field validation in Registration (configuration in DB to make all fields required)

  Scenario Outline: Check the validation for each field in registration.
    Given    Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page : 2 p+passes"
      | LicensePlate | Phonenumber |
      | empty        | empty       |
      | 951          | 091  |
    Then   Customer fills details in "Product Selection Page : 2 p+passes"
    When   Customer enters this information and validates Errors in "Your Data page"
      | Company name | COC   | VAT   | Initials | Firstname | Infix | Lastname | Birthdate  | Phone | Email | Repeat email | Zip code | House number | Address | Extra information | City  |
      | empty        | empty | empty | empty    | empty     | empty | empty    | empty      | empty | empty | empty        | empty    | empty        | empty   | empty             | empty |
      | empty        | 345   | 432   | empty    | empty     | empty | empty    | 02-02-2018 | 036   | 123   | 321          | 956      | empty        | empty   | empty             | empty |
    Then   Customer fills details in "Your Data page"
    And    Customer validate details in "Summary Page"
    When   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN  | Name  |
      | empty | empty |
      | 3847  | empty |
    Then    Customer validate details in "Confirmation page" and select payment method as "debitcard"
      Examples:
        | type     | plan  | provider |
        | Private  | BASC  | YB       |
        | Business | BASC  | YB       |