@restIVR
  Feature: IVR Start and Stop Transaction

  Scenario: Start a transaction from IVR, stops it from myYellowbrick
    Given an active IVRCustomer with unique phone number
    When IVRCustomer calls IVR to start transaction
    And  IVRCustomer chooses an active zone to park and starts parking
    Then Customer logs in to "myyellowbrick" with "IVRcustomerlogin"
    Then Customer Stops parking from "myyellowbrick"
      | Action | Stop |

  Scenario: Start a transaction from IVR, stops in IVR
    Given an active IVRCustomer with unique phone number
    When IVRCustomer calls IVR to start transaction
    And  IVRCustomer chooses an active zone to park and starts parking
    And  IVRCustomer calls IVR to stop transaction
    Then  IVRCustomer confirms stop in IVR




