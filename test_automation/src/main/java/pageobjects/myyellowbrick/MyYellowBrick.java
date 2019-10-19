package pageobjects.myyellowbrick;

import org.openqa.selenium.By;

public class MyYellowBrick {

    public By parkingbuttonOnLandingPage = By.xpath("//*[@id='nav-parking']/a");
    public By customerMainMenuButton = By.xpath("//*[@id='nav-customer']");
    public By successMessage = By.xpath("//*[@class='success-message']");

    //Internet Parking Page
    public By drpdwnVehicleToPark = By.xpath("//select[@id='transponderCardNr']");
    public By numberPlate = By.xpath("//input[@id='licenseplate']");
    public By zoneCode = By.xpath(".//*[@id='zoneCode']");
    public By remark = By.xpath(".//*[@id='mark']");
    public By start = By.xpath("//*[@value='Start']");
    public By stop = By.xpath("//*[@value='Stop']");
    public By OkStopParking = By.xpath("//button[text()='OK']");

    //Card navigation
    public By cardLinkNavigation_NL = By.xpath("//*[@id='nav-cards']");
    public By cardLinkNavigation_BE = By.xpath("//*[@id='nav-cards-be']");
    public By orders=By.xpath("//li[a[contains(text(),'Bestellen')]]/a");
    public By transpondercardsnr=By.xpath("//*[@id='orderList0.amount']");
    public By ppluspassNr=By.xpath("//*[@id='orderList1.amount']");
    public By cardlinknavigation = By.xpath("//*[@id='nav-cards']/a");
    public By ppluspass = By.xpath("//li[a[contains(text(),'P+ passen')]]/a");
    public By passstatus = By.xpath("//*[@id='content']/div[2]/div[2]/div");
    public By passstatuschange = By.xpath("//*[@id='status']");
    public By transpondercardchange=By.xpath("//*[@id='transponderCard.id']");
    public By linkedtranspondercard = By.xpath("//*[@id='content']/div[2]/div[3]/div");
    public By modifySubmit = By.xpath("//*[@value='Opslaan']");
    public By cardsleeveNr=By.xpath("//*[@id='orderList2.amount']");
    public By orderconfirm=By.xpath("//*[@id='orderForm']/input");
    public By backBtn = By.xpath("//form[@id='command'][1]/input");
    public By customerDetails = By.xpath("//*[@id='nav-customer']");
    public By customerPasswordNavigation = By.xpath("//*[@id='nav-customer']/ul/li/a[contains(@href, 'password')]");

    public By licenseplate=By.xpath("//*[@id='card.licenseplate']");
    //Car transaction valiodation
    public By tblLicencePlate = By.xpath("//table[@class='data-table align-middle']/tbody/tr[2]/td[4]");
    public By tblZoneCode = By.xpath("//table[@class='data-table align-middle']/tbody/tr[2]/td[5]");
    public By tblProvider = By.xpath("//table[@class='data-table align-middle']/tbody/tr[2]/td[6]");

    //Personal details page
    public By personalDetailsDropDownButton = By.xpath("//*[@id='nav-customer']/ul/li[1]/a");
    public By businessName = By.xpath("//*[@id='businessName']");
    public By businessIds0 = By.xpath("//*[@id='businessIds0.value']");
    public By businessIds1 = By.xpath("//*[@id='businessIds1.value']");
    public By customerNumber = By.xpath("//*[@id='customerNr']");
    public By gender = By.xpath("//*[@id='gender']");
    public By initials = By.xpath("//*[@id='initials']");
    public By firstName = By.xpath("//*[@id='firstname']");
    public By infix = By.xpath("//*[@id='infix']");
    public By lastName = By.xpath("//*[@id='lastname']");
    public By dateOfBirth = By.xpath("//*[@id='dateOfBirth']");
    public By email = By.xpath("//*[@id='email']");
    public By phoneNr = By.xpath("//*[@id='phoneNr']");
    public By correspondenceLocale = By.xpath("//*[@id='correspondenceLocale']");
    public By locationAddress1 = By.xpath("//*[@id='locationAddress.addressLine1']");
    public By locationAddress2 = By.xpath("//*[@id='locationAddress.addressLine2']");
    public By zipCode = By.xpath("//*[@id='locationAddress.zipCode']");
    public By city = By.xpath("//*[@id='locationAddress.city']");
    public By countryDropdown = By.xpath("//*[@id='countryDropdown']");
    public By havingBillingAddress1 = By.xpath("//*[@id='havingBillingAddress1']");
    public By havingBillingAddress2 = By.xpath("//*[@id='havingBillingAddress2']");
    public By billingAddressAddressLine1 = By.xpath("//*[@id='billingAddress.addressLine1']");
    public By billingAddressAddressLine2 = By.xpath("//*[@id='billingAddress.addressLine2']");
    public By billingAddressZipCode = By.xpath("//*[@id='billingAddress.zipCode']");
    public By billingAddressCity = By.xpath("//*[@id='billingAddress.city']");
    public By billingAddressCountryCode = By.xpath("//*[@id='billingAddress.countryCode']");
    public By savePersonalDataButton = By.xpath("//input[contains(@type, 'submit') and contains(@class ,'yellowbutton')]");

    //Payment page
    public By paymentDropDownButton = By.xpath("//*[@id='nav-customer']/ul/li[2]/a");
    public By selectIban = By.xpath("//*[@id='paymentMethod2']");
    public By selectCreditCard = By.xpath("//*[@id='paymentMethod1']");
    public By permission = By.xpath("//*[@id='permitted']");
    public By iban = By.xpath("//input[@id='directDebitDetails.sepaNumber']");
    public By holdername = By.xpath("//input[@id='accountHolderName']");
    public By savePaymentButton = By.xpath("//input[@id='submitButon']");
    public By confirmButton = By.xpath("//button[text()='OK']");
    public By selectMandateField = By.xpath("//input[@id='upload_file']");
    public By uploadMandateButton = By.xpath("//input[@id='ButtonPlace']");


    public By myAccount = By.xpath("//*[@id='nav-customer']/ul/li[last()]/a");
    public String myPlanBasePath = ".//*[@id='content-main']/div[1]/div/div/div/div/div";
    public String changePlan1 = ".//*[@id='content-main']/div[2]/div/div[1]/div/div/div";
    public String changePlan2 = ".//*[@id='content-main']/div[2]/div/div[2]/div/div/div";

    //Mobile phone number
    public By mobileMainMenuButton = By.xpath("//*[@id='nav-mobile']");
    public By mobileDropDownButton = By.xpath("//*[@id='nav-mobile']/ul/li[2]/a");
    public By mobileListDropDownButton = By.xpath("//*[@id='nav-mobile']/ul/li[1]/a");
    public By mobileNumber = By.xpath("//input[@id='mobileNumber']");
    public By languageselection=By.xpath("//*[@id='locale']");
    public By confirmmobiledelete=By.xpath("//button[text()='OK']");
    public By transponderCardId = By.xpath("//select[@id='transpondercardId']");
    public By sms = By.xpath("//select[@id='sms']");
    public By smsInterval = By.xpath("//select[@id='smsInterval']");
    public By smsBeforeEnd = By.xpath("//select[@id='smsBeforeEnd']");
    public By hasConfirmZone = By.xpath("//select[@id='hasConfirmZone']");
    public By tCardSwitchable = By.xpath("//select[@id='tCardSwitchable']");
    public By smsAnnotations1 = By.xpath("//input[@id='smsAnnotations1']");
    public By mobileSubmitButton = By.xpath("//*[@id='submitButton']");
    public By searchValue = By.xpath("//input[@id='searchValue']");
    public By deleteButton = By.xpath("//button[@id='delete']");
    public By confirmDeleteButton = By.xpath("//button[text()='OK']");
    public By searchbutton = By.xpath("//button[@class='searchbutton']");
    public By errorblock = By.xpath("//*[@id='mobileNumber.errors']");

    public By selectBankName = By.xpath("//Select[@id='directDebitDetails.bic']");
    public By btnChangeIbanButton = By.xpath("//input[@id='validateIbanButton']");
}

