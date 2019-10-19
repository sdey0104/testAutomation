//E-mail to customer service will only be send when following condition is met:
//Customer is activated within 90 days
//AND licensplate is in table SUSPECT_Licenseplate
//AND zone is in SUSPECT_ZONE

Scenario Outline:
Given Customer logs in to "myyellowbrick"
When Customer starts a transaction in <zone> with <Licenseplate>
Then administrator validates if e-mail is sent.

Examples:
|zone|Licenseplate|
|1|XX-02-XX|
|2|XX-02-XX|
|1|OI-09-JK|