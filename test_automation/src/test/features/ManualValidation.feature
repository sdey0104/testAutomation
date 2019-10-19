@manualvalidation @regression
Feature: Regression for manual activation or rejection of failed registered customers.

  Scenario Outline: Register a private customer which fails on validation
    Given   Check for the existence of following data in Database for "Private-Blacklisted"
      | Firstname   | Lastname   | Birthday    | Licenseplate|Status |
      | Olaf        | Elsa       | 02-02-2001  | AU-TO-12    |  3    |
    And     Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber   |
      | AU-TO-12     | +31633715309  |
    And    Customer enters this information and validates Errors in "Your Data page"
      | Firstname  |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
      | Olaf       |  Elsa          | 02-02-2001  |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com  | 5611CE |    12      | markt | Almere   |
    And     Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31633715309 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               |  Name       |
      | NL60ABNA6044978370 | Owen Wilson |
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <registrationemail> mail for "Olaf Elsa"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname    | Lastname    |
      | Olaf         | Elsa        |
    Then   Administrator validates the following data for "Private-Blacklisted" in Taxameter and Database
      | Firstname   | Lastname   | Birthday    | Licenseplate| Status|
      | Olaf        | Elsa       | 02-02-2001  | AU-TO-12    |  1    |
    And   Administrator <decide> validation
    Then   Customer wait for "2" minutes to get the mails
    And   Customer checks <confirmationemail> mail for "Olaf Elsa"

    Examples:
      | provider|plan |type     |  registrationemail |decide    |confirmationemail|
      | YB      |BASC |Private  |  Manual validation | Activate | Welcome         |
      | YB      |BASC |Private  |  Manual validation | Reject   | Close           |

  Scenario Outline: Register a business customer which fails on validation
    Given   Check for the existence of following data in Database for "Business-Blacklisted"
       |Email                     |Phonenumber  |Status |
       |testteam@brickparking.com |+31635987159 |3      |
    And    Open the registration application for <provider> with <plan> plan
    And    Customer wants to register the application as <type>
    When   Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber   |
      | 02-PH-VL     | +31635987159  |
    And    Customer enters this information and validates Errors in "Your Data page"
      |Company name| COC         | VAT             | Firstname  |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
      |   rob      | 12312312    |  NL185323339B01 | Clint      |  Eastwood      | 03-02-2001  |  +31635987159   |testteam@brickparking.com  | testteam@brickparking.com  | 5611CE |    12      | markt | Almere   |
    And    Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31635987159 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               |  Name       |Email                         |
      | NL60ABNA6044978370 | Owen Wilson |testteam@brickparking.com     |
    Then   Customer wait for "2" minutes to get the mails
    And    Customer checks <registrationemail> mail for "Clint Eastwood"
    Then   Administrator logs into Taxameter
    And    Administrator selects "Customers to be validated" from menu
    When   Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname    | Lastname     |
      | Clint        | Eastwood     |
    Then   Administrator validates the following data for "Business-Blacklisted" in Taxameter and Database
      |Email                     |Phonenumber  |Status |
      |testteam@brickparking.com |+31635987159 |  1    |
    And   Administrator <decide> validation
    Then  Customer wait for "2" minutes to get the mails
    And   Customer checks <confirmationemail> mail for "Clint Eastwood"
    And   Remove email "testteam@brickparking.com" from blacklist

    Examples:
      | provider|plan |type      |  registrationemail |decide    |confirmationemail|
      | YB      |BASC |Business  |  Manual validation | Activate | Welcome         |
      | YB      |BASC |Business  |  Manual validation | Reject   | Close           |

  Scenario Outline: Activate a business customer with empty COC and VAT.(check present in billing billing_table_1 (creditcard token) and payment_details))
    Given   Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page" for multiple users
      | USERS | P+Pass | LicensePlate | Phonenumber   |
      | 10    | 30     | GG-11-11     | +31633715309  |
    And    Customer enters this information and validates Errors in "Your Data page"
      | Company name  |   COC    | VAT    |Firstname | Lastname     | Birthdate  |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City     |
      | rob           |  empty   | empty  | James    |   Bond       | 02-02-2000 |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com |  5611CE|    12      | markt |Almere   |
    And     Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31633715309 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               | Name       |
      | NL63ABNA7052105551 | Owen Wilson|
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <confirmationemail> mail for "James Bond"
    Given  Administrator logs into Taxameter
    And    Administrator selects "Customers to be validated" from menu
    When   Administrator finds customer based in "Risk" from product group "YELLOWBRICK-BASIC"
      | Firstname | Lastname |
      | James     | Bond     |
    Then   Administrator validates the following data for "VAT-COC-Empty" in Taxameter and Database
      |Firstname|Lastname|
      | James   |  Bond  |
    Examples:
      | provider|plan |type     |confirmationemail   |
      | YB      |BASC |Business |Welcome             |

  Scenario Outline: Register a private customer which fails on validation SUSPECT_LICENSE_PLATE
    Given   Check for the existence of following data in Database for "SUSPECT_LICENSE_PLATE"
      | Licenseplate  |Status |
      | TKWY17        |  3    |
    And     Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber   |
      | TKWY17       | +31633715309  |
    And    Customer enters this information and validates Errors in "Your Data page"
     | Firstname |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
     | Virat     |  Kohli         | 03-02-2001  |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com | 5611CE |    12      | markt | Almere   |
    And     Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31633715309 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               | Name       |
      | NL60ABNA6044978370 | Owen Wilson|
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <registrationemail> mail for "Virat Kohli"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname    | Lastname    |
      | Virat        | Kohli       |
    Then   Administrator validates the following data for "SUSPECT_LICENSE_PLATE" in Taxameter and Database
    |Firstname|Lastname  |  Licenseplate     | Status|
    | Virat   | Kohli    |   TK-WY-17        |    1  |

    Examples:
      | provider|plan |type     |  registrationemail |
      | YB      |BASC |Private  |  Manual validation |

  Scenario Outline: Register a business customer which fails on validation COC_EXISTS
    Given   Check for the existence of following data in Database for "COC/VAT_EXISTS"
      | Firstname|Lastname | COC       | VAT            |Status |
      |Albert    |Andriek  | 34206825  | NL123456789B01 |  2    |
    And     Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber     |
      | GG-11-11     | +31633715309    |
    And    Customer enters this information and validates Errors in "Your Data page"
      |Company name|      COC      |    VAT          | Firstname   |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
      |   rob      | 34206825      |   NL123456789B01| Albert      |  Andriek      | 03-02-2001  |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com | 5611CE |    12      | markt |Almere   |
    And     Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31633715309 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               | Name       |
      | NL60ABNA6044978370 | Owen Wilson|
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <registrationemail> mail for "Albert Andriek"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname    | Lastname    |
      | Albert       | Andriek     |
    Then   Administrator validates the following data for "COC/VAT_EXISTS" in Taxameter and Database
      | Firstname|Lastname | COC       | VAT            |Status |
      |Albert    |Andriek  | 34206825  |  NL123456789B01|  2    |

    Examples:
      | provider|plan |type      |  registrationemail |
      | YB      |BASC |Business  |  Manual validation |

  Scenario Outline: Register a private customer which fails on validation KEYWORDS_CHECK
    Given   Check for the existence of following data in Database for "KEYWORDS_CHECK"
      | Keyword      |
      | 11-11-AA     |
    And     Open the registration application for <provider> with <plan> plan
    And     Customer wants to register the application as <type>
    When    Customer enters this information and validates Errors in "Product Selection Page"
      | LicensePlate | Phonenumber   |
      | 11-11-AA     | +31633715309  |
    And    Customer enters this information and validates Errors in "Your Data page"
      | Firstname |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
      | William   |  Oosterveld    | 03-02-2001  |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com | 5611CE |    12      | markt |Almere   |
    And     Customer validate details in "Summary Page" with the following data
      |        Email               | Phone         |
      | testteam@brickparking.com  |  +31633715309 |
    Then   Customer enters this information and validates Errors in "Confirmation page"
      | IBAN               | Name       |
      | NL15AEGO0740632722 | Owen Wilson|
    Then    Customer wait for "2" minutes to get the mails
    And     Customer checks <registrationemail> mail for "William Oosterveld"
    Then    Administrator logs into Taxameter
    And     Administrator selects "Customers to be validated" from menu
    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
      | Firstname    | Lastname    |
      | William      | Oosterveld  |
    Then   Administrator validates the following data for "KEYWORDS_CHECK" in Taxameter and Database
      | Firstname|Lastname     | Keyword    |Status |
      | William  |Oosterveld   | 11-11-AA   |  1    |

    Examples:
      | provider|plan |type     |  registrationemail |
      | YB      |BASC |Private  |  Manual validation |


#  Scenario Outline: Register a private customer which fails on validation IBAN_EXISTS
#    Given   Check for the existence of following data in Database for "IBAN_EXISTS"
#      | IBAN                 |Status  |
#      | BE68539007547034     |  98    |
#    And     Open the registration application for <provider> with <plan> plan
#    And     Customer wants to register the application as <type>
#    When    Customer enters this information and validates Errors in "Product Selection Page"
#      | LicensePlate | Phonenumber   |
#      | GG-11-11     | +31633715309  |
#    And    Customer enters this information and validates Errors in "Your Data page"
#    | Firstname |  Lastname      | Birthdate   |   Phone         |      Email                |     Repeat email          |Zip code|House number|Address|City      |
#    | Robert    |  Ianson        | 03-02-2001  |  +31633715309   |testteam@brickparking.com  | testteam@brickparking.com | 5611CE |    12      | markt |Almere   |
#    And     Customer validate details in "Summary Page" with the following data
#      |        Email               | Phone         |
#      | testteam@brickparking.com  |  +31633715309 |
#    Then   Customer enters this information and validates Errors in "Confirmation page"
#      | IBAN               | Name       |
#      | BE68539007547034   | Owen Wilson|
#    Then    Customer wait for "2" minutes to get the mails
#    And     Customer checks <registrationemail> mail for "Robert Ianson"
#    Then    Administrator logs into Taxameter
#    And     Administrator selects "Customers to be validated" from menu
#    When    Administrator finds customer based in "Validation" from product group "YELLOWBRICK-BASIC"
#      | Firstname    | Lastname    |
#      | Robert       | Ianson      |
#    Then   Administrator validates the following data for "IBAN_EXISTS" in Taxameter and Database
#      | Firstname|Lastname | IBAN                 |Status |
#      | Robert   |Ianson   | NL15AEGO0740632722   |  1    |
#
#    Examples:
#      | provider|plan |type     |  registrationemail |
#      | YB      |BASC |Private  |  Manual validation |