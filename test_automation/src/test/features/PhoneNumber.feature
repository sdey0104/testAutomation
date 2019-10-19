@smoke @regression
Feature: Changing user's phone number
  Scenario Outline: Customer adds a mobile number
    Given   Customer logs in to myyellowbrick with <customer number> in <Environment> for <Country>
    When    Customer navigates to "Mobile phone Addition"
    Then    Add mobile phone number <mobile number> for <customer number>
    And     Validate mobile number "Addition" for <mobile number>

    Examples:
    |customer number|mobile number | Environment| Country|
    |   203198      | 096          | SIT        |  NL    |
    |   203198      | 0619981200   | SIT        |  NL    |
    |   200170      | 096          | SIT        |  NL    |
    |   200170      | 0619981211   | SIT        |  NL    |
    |   203102      | 096          | ACC        |  NL    |
    |   203102      | 0619981200   | ACC        |  NL    |
    |   203198      | 096          | ACC        |  NL    |
    |   203198      | 0619981211   | ACC        |  NL    |
    |   3213476     | 096          | SIT        |  BE    |
    |   3213476     | 0619981200   | SIT        |  BE    |
    |   3214094     | 096          | SIT        |  BE    |
    |   3214094     | 0619981211   | SIT        |  BE    |
    |   469781      | 096          | ACC        |  BE    |
    |   469781      | 0619981200   | ACC        |  BE    |
    |   469782      | 096          | ACC        |  BE    |
    |   469782      | 0619981211   | ACC        |  BE    |

  Scenario Outline: Customer changes a mobile number
    Given   Customer logs in to myyellowbrick with <customer number> in <Environment> for <Country>
    When    Customer navigates to "Mobile phone Overview"
    Then    Update mobile phone number <old number> to <new number>
    Then    Validate mobile number "Update" for <new number>

    Examples:
      |customer number|old number    |new number  | Environment| Country  |
      |   203198      | 0619981200   | 096        | SIT        |  NL      |
      |   203198      | 0619981200   | 0619981300 | SIT        |  NL      |
      |   200170      | 0619981211   | 096        | SIT        |  NL      |
      |   200170      | 0619981211   | 0619981311 | SIT        |  NL      |
      |   203102      | 0619981200   | 096        | ACC        |  NL      |
      |   203102      | 0619981200   | 0619981300 | ACC        |  NL      |
      |   203198      | 0619981211   | 096        | ACC        |  NL      |
      |   203198      | 0619981211   | 0619981311 | ACC        |  NL      |
      |   3213476     | 0619981200   | 096        | SIT        |  BE      |
      |   3213476     | 0619981200   | 0619981300 | SIT        |  BE      |
      |   3214094     | 0619981211   | 096        | SIT        |  BE      |
      |   3214094     | 0619981211   | 0619981311 | SIT        |  BE      |
      |   469781      | 0619981200   | 096        | ACC        |  BE      |
      |   469781      | 0619981200   | 0619981300 | ACC        |  BE      |
      |   469782      | 0619981211   | 096        | ACC        |  BE      |
      |   469782      | 0619981211   | 0619981311 | ACC        |  BE      |


  Scenario Outline: Customer deletes a mobile number
    Given   Customer logs in to myyellowbrick with <customer number> in <Environment> for <Country>
    When    Customer navigates to "Mobile phone Overview"
    Then    Delete mobile phone number <mobile number>
    Then    Validate mobile number "Deletion" for <mobile number>

    Examples:
      |customer number|mobile number |Environment| Country  |
      |   203198      | 0619981300  | SIT        |  NL      |
      |   200170      | 0619981311  | SIT        |  NL      |
      |   203102      | 0619981300  | ACC        |  NL      |
      |   203198      | 0619981311  | ACC        |  NL      |
      |   3213476     | 0619981300  | SIT        |  BE      |
      |   3214094     | 0619981311  | SIT        |  BE      |
      |   469781      | 0619981300  | ACC        |  BE      |
      |   469782      | 0619981311  | ACC        |  BE      |