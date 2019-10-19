@endToendSmoke1 @regression @smoke
Feature: Start and Stop Transaction
   # Use the below customer numbers for the following countries
   # BE-SIT/ACC : 416004
   # NL-SIT/ACC : 203198
   # DE-SIT/ACC : 203180

  Scenario: User starts and stops transaction from My yellowbrick
    Given   Customer logs in to "myyellowbrick" with 203198
    When    Customer starts parking with details below
      | Licence plate | 11-11-AA |
      | Zone code     | 2        |
      | Remark        | test     |
    Then  Customer validates the start transaction from the table
      | Licence plate | 11-11-AA  |
      | Zone code     | 2        |
      | Provider      | Haarlem  |
    And Customer Stops parking from "myyellowbrick"
      | Action | Stop |
    And Customer validate stop parking in "myyellowbrick"

#  Scenario: User starts transaction in myyellowbrick and stops transaction from municipality
 #   Given   Customer logs in to "myyellowbrick" with "203198"
 #   When    Customer starts parking with details below
 #    | Licence plate | PH-VL-07 |
  #    | Zone code     | 77774    |
   #   | Remark        | test     |
   # Then  Customer validates the start transaction from the table
   #  | Licence plate | PH-VL-07 |
   #  | Zone code     | 77774    |
   #  | Provider      | AIRPORT  |
   # Then Customer logs in to "municipality" with "pbf"
   # And  Customer Validates the on going transaction from "municipality"
   #   | Licence plate | PH-VL-07 |
   # And Customer Stops parking from "municipality"
   #   | Ending Time  | today |
   #   | Parking Cost | 3.00  |
   #And Customer validate stop parking in "municipality"
   # Then Customer logs in to "myyellowbrick" with "220600"
   # And Customer validate stop parking in "MyYellowbrick"



