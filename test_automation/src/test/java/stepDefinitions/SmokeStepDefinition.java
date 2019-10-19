package stepDefinitions;

import base.WebBase;
import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import dto.DirectDebitDetails;
import enums.PaymentMethod;
import frameworkComponents.AutomationComponent;
import frameworkComponents.BusinessComponent;
import frameworkComponents.DatabaseConnections;
import frameworkComponents.TaxameterComponent;
import org.junit.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageobjects.taxameter.CustomersValidation;
import pageobjects.taxameter.PPlusPass;
import pageobjects.municipality.Municipality;
import pageobjects.myyellowbrick.MyYellowBrick;
import pageobjects.myyellowbrick.MyYellowBrickLogin;
import pageobjects.registration.Summary;
import pageobjects.registration.Mailbox;
import pageobjects.registration.Confirm;
import pageobjects.registration.ProductSelection;
import pageobjects.registration.YourData;

import java.sql.SQLException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@RunWith(Cucumber.class)
public class SmokeStepDefinition extends WebBase {

    Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);
    ProductSelection productSelection = new ProductSelection();
    YourData yourData = new YourData();
    AutomationComponent ac = new AutomationComponent();
    BusinessComponent bc = new BusinessComponent();
    DatabaseConnections databaseConnections = new DatabaseConnections();
    Summary summary = new Summary();
    Confirm confirm = new Confirm();
    Mailbox mpg = new Mailbox();
    MyYellowBrick myYellowBrick = new MyYellowBrick();
    MyYellowBrickLogin myYellowbrickLoginPageObject = new MyYellowBrickLogin();
    Municipality municipality = new Municipality();
    TaxameterComponent tc=new TaxameterComponent();
    PPlusPass PPlusPass = new PPlusPass();
    public String custType = "";
    public String provider_type="";
    public static String clientNumber;
    public final String REGISTRATION = "Registration";
    public final String THANK_YOU = "Thank you";
    public final String WELCOME = "Welcome";
    public final String FORGOT_PASSWORD = "Forgot password";
    public final String RESET_PASSWORD_SCREEN = "Reset password screen";

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String CurrentDate = formatter.format(date);
    public SmokeStepDefinition() {
        super(CommonConfig.getInstance().getValue(CommonConstants.WEB_AUTOMATION_FILE));
    }


    @Given("^Start \"([^\"]*)\"$")
    public void open_browser(String strArg1) throws IOException, InterruptedException {
        driver = initializeDriver(strArg1);
        logger.info("Driver initialised");
    }


    @Given("^Open the registration application for (.+) with (.+) plan$")
    public void open_the_application_in_something(String provider, String payplanType) throws IOException, InterruptedException {
        provider = provider.replaceAll("\"", "");
        provider_type=provider;
        payplanType = payplanType.replaceAll("\"", "");
        if(COUNTRY.equals("NL")) {
            PRODUCTGROUP = bc.findProductgroup(bc.ProductGroupsByCountry.get(COUNTRY).get(provider), payplanType).getProductGroupId();
        }
        else
        {
            PRODUCTGROUP="11";
        }
        driver = initializeDriver(BROWSER);
        driver.get(getValue("registration") + PRODUCTGROUP);
        logger.info("Browser is invoked with the url");
        Thread.sleep(1000);
        if (driver.findElement(productSelection.acceptcookies).isDisplayed()) {
            ac.clickOnElement(productSelection.acceptcookies);
        }
    }

    @When("^Customer wants to register the application as (.*)$")
    public void customer_wants_to_register_the_application_as_something(String strArg1)
            throws InterruptedException, IOException {

        strArg1 = strArg1.replace("\"", "");
        /**
         * implement ACTIONCODE while executing in production
         */
        if (ENVIRONMENT.equals("PRD") && COUNTRY.equals("NL")) {
            // Input action code as "BRICKEN"
            bc.actioncode();
        }
        if (strArg1.equals("Private")) {
            ac.clickOnElement(productSelection.ParkTypeConsumerSelected);
            logger.info("Park Type Consumer Selected");
            custType = "C";
        } else if (strArg1.equals("Business")) {
            ac.clickOnElement(productSelection.parkTypeBusiness);
            custType = "B";
            logger.info("Park Type Consumer Selected");
        }
    }

    @When("^Customer enters this information and validates Errors in \"([^\"]*)\"$")
    public void customer_enters_this_information_and_checks_errors(String strArg1,DataTable userdata) throws SQLException, IOException, InterruptedException {
        String ErrorFlag="N";
        String[] count;
        String pPlusCount = "";
        String[] pPluspass = strArg1.split(":");
        strArg1 = pPluspass[0].trim();
        if (strArg1.equals("Product Selection Page")) {
            if (pPluspass.length != 1) {
                count = pPluspass[1].split("p");
                pPlusCount = count[0];
            } else {
                pPlusCount = "0";
            }
            ErrorFlag = bc.fillProductSelectionPage(userdata,custType,Integer.parseInt(pPlusCount.trim()));
            if(ErrorFlag.equals("N"))
            {
                bc.pressNext();
            }
        }
        if (strArg1.equals("Your Data page")) {
            ErrorFlag = bc.fillYourDataPage(userdata,custType);
            if(ErrorFlag.equals("N"))
            {
                bc.pressNext();
            }
        }
        if (strArg1.equals("Confirmation page")) {
            ErrorFlag = bc.fillConfirmationPage(userdata,custType);
            if(ErrorFlag.equals("N"))
            {
                ac.clickOnElement(confirm.checkBoxinfoOath);
                ac.clickOnElement(confirm.checkBoxAutoDebit);
                for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
                    if(userdatamap.containsKey("Email")){
                        databaseConnections.updateemail_blacklisted("3");
                    }
                }
                ac.clickOnElement(confirm.completeRegistration);
                ac.clickOnElement(confirm.btnSubmitSucessIbanTestValidation);
                logger.info("Checking confirmation message");
                for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
                    if (userdatamap.containsKey("Email")) {
                        if (ac.retrieveTextFromElement(confirm.messageForConfirmation).isEmpty()) {
                            databaseConnections.updateemail_blacklisted("2");
                        }
                    }
                }
            }
            logger.info("Confirmation page filled");
        }
        Thread.sleep(1000);
    }
    @When("^Customer enters this information and validates Errors in \"([^\"]*)\" for multiple users$")
    public void customer_enters_this_information_for_MultipleUsers(String strArg1, DataTable userdata) throws IOException, InterruptedException {
        String ErrFlag = "N";
        String[] count;
        String pPlusCount = "";
        String[] pPluspass = strArg1.split(":");
        strArg1 = pPluspass[0].trim();
        if (strArg1.equals("Product Selection Page")) {
            if (pPluspass.length != 1) {
                count = pPluspass[1].split("p");
                pPlusCount = count[0];
            } else {
                pPlusCount = "0";
            }
            ErrFlag = bc.fillProductSelectionPageForMultipleUsers(userdata, custType, Integer.parseInt(pPlusCount.trim()));
            if (ErrFlag.equals("N")) {
                bc.pressNext();
            }
        }
    }

    @Then("^Customer fills details in \"([^\"]*)\"$")
    public void customer_fills_details_in_something(String strArg1) throws IOException, InterruptedException {
        String[] count;
        String pPlusCount = "";
        String[] pPluspass = strArg1.split(":");
        strArg1 = pPluspass[0].trim();
        if (strArg1.equals("Product Selection Page")) {
            if (pPluspass.length != 1) {
                count = pPluspass[1].split("p");
                pPlusCount = count[0];
            } else {
                pPlusCount = "0";
            }
            if(provider_type.equals("ANWB"))
            {
                ac.inputTextinTextBox(productSelection.subscriptionCode,"1234567890");
            }
            bc.fillProductSelectionPage(Integer.parseInt(pPlusCount.trim()));
            bc.pressNext();
            logger.info("Productselectionpagefilled");
        } else if (strArg1.equals("Your Data page")) {
            bc.fillYourDataPage(custType);
            bc.pressNext();
            logger.info("Your Data page filled");

        }
    }

    @And("^Customer validate details in \"([^\"]*)\"$")
    public void customer_validate_details_in_something(String strArg1) throws IOException, InterruptedException {
        // TODO: refactor

        if (strArg1.equals("Summary Page")) {
            // Assert.assertEquals(ac.retrieveTextFromElement(Summary.chosenTypeofParking),
            // getValue("chosentypeofparking"));
            // Assert.assertEquals(ac.retrieveTextFromElement(getValue("licenseplate")),
            // getValue("licenseplate"));
            Assert.assertEquals(ac.retrieveTextFromElement(summary.email()), getValue("emailaddress"));
            Assert.assertEquals(ac.retrieveTextFromElement(summary.telephone()), getValue("mobilenummer"));
            // TODO: check xpaths
            // Assert.assertEquals(ac.retrieveTextFromElement(getValue("postcode")),
            // getValue("postcode"));
            // Assert.assertEquals(ac.retrieveTextFromElement(Summary.city),
            // getValue("city"));
            bc.pressNext();
        }
    }

    @And("^Customer validate details in \"([^\"]*)\" with the following data$")
    public void customer_validate_details_in_something(String strArg1,DataTable userdata) throws IOException, InterruptedException {
        // TODO: refactor
        if (strArg1.equals("Summary Page")) {
            bc.validateSummaryPage(userdata);
            // TODO: check xpaths
            bc.pressNext();
        }
    }

    @And("^Customer validate details in \"([^\"]*)\" and select payment method as \"([^\"]*)\"$")
    public void customer_validate_details_in_something(String strArg1, String paymentMethod) throws IOException, InterruptedException {
        // TODO: refactor

        if (strArg1.equals("Summary Page")) {
            // Assert.assertEquals(ac.retrieveTextFromElement(Summary.chosenTypeofParking),
            // getValue("chosentypeofparking"));
            Assert.assertEquals(ac.retrieveTextFromElement(getValue("licenseplate")),
                    getValue("licenseplate"));
            Assert.assertEquals(ac.retrieveTextFromElement(summary.email()), getValue("emailaddress"));
            Assert.assertEquals(ac.retrieveTextFromElement(summary.telephone()), getValue("mobilenummer"));
            // TODO: check xpaths
            // Assert.assertEquals(ac.retrieveTextFromElement(getValue("postcode")),
            // getValue("postcode"));
            // Assert.assertEquals(ac.retrieveTextFromElement(Summary.city),
            // getValue("city"));
            bc.pressNext();
        } else if (strArg1.equals("Confirmation page")) {

            if (paymentMethod.equals("creditcard")) {

                bc.processCreditCard();
                Thread.sleep(4000);
                ac.clickOnElement(confirm.checkBoxinfoOath);
                ac.clickOnElement(confirm.checkBoxAutoDebit);
                ac.clickOnElement(confirm.completeRegistration);

            } else if (paymentMethod.equals("debitcard")) {
                bc.ibanValidationInRegistration();
                ac.clickOnElement(confirm.checkBoxinfoOath);
                ac.clickOnElement(confirm.checkBoxAutoDebit);
                ac.clickOnElement(confirm.completeRegistration);
                ac.clickOnElement(confirm.btnSubmitSucessIbanTestValidation);
                logger.info("Checking confirmation message");
                Assert.assertEquals(ac.retrieveTextFromElement(confirm.messageForConfirmation).trim(),
                        (getValue("welcomemsg") + getValue("voornaam") + ".").replaceAll(" ",
                                ""));
            }
        }
    }

    @And("^Customer completes registration with direct debit$")
    public void Customer_entersDirectDebitdetails_toproceed() throws Throwable {
        bc.ibanValidationInRegistration();
        ac.clickOnElement(confirm.checkBoxinfoOath);
        ac.clickOnElement(confirm.checkBoxAutoDebit);
        ac.clickOnElement(confirm.completeRegistration);
    }

    @And("^Customer cancels IBAN validation$")
    public void Customer_cancels_IBANValidation() throws Throwable {
        ac.selectTextFromDropdown(confirm.IBANStatus,"890 - Cancelled by user");
        ac.clickOnElement(confirm.btnSubmitSucessIbanTestValidation);
    }

    @And("^Customer navigates back to \"([^\"]*)\"$")
    public void Customer_navigates_backtosomePage(String Destinationpage) throws Throwable {
        if(Destinationpage.equals("Your Data page")){
           while(!ac.retrieveTextFromElement(confirm.pageheader).equals("Wat are your details"))
            {
                ac.clickOnElement(confirm.previousButton);
            }
        }
    }

    @Given("^Login to the application$")
    public void login_to_the_application(DataTable data) throws Throwable {
        List<List<String>> obj = data.raw();
        System.out.println(obj.get(0).get(0));
        System.out.println(obj.get(0).get(1));
        System.out.println(obj.get(0).get(2));
        System.out.println(obj.get(0).get(3));
        throw new PendingException();
    }

    @Then("^Customer wait for \"([^\"]*)\" minutes to get the mails$")
    public void customer_wait_for_something_minutes_to_get_the_mails(String strArg1) throws Throwable {
        int time = Integer.parseInt(strArg1) * 10000;
        Thread.sleep(time);

    }

    @Then("^Customer resets password$")
    public void customer_resets_password() throws Throwable {

        bc.fillResetPasswordFields(getValue("myyellowbrickpassword"), getValue("myyellowbrickNewPassword"));

    }

    @Then("^Customer changes the password to the original one$")
    public void customer_changes_the_password_to_original() throws Throwable {
        customer_navigates_to_change_password();
        bc.fillResetPasswordFields(getValue("myyellowbrickNewPassword"), getValue("myyellowbrickpassword"));
    }

    @Then("^Customer starts parking$")
    public void customer_starts_parking() throws Throwable {
        bc.clickLinkParking();
        bc.startInternetParking();
    }

    @Then("^Customer starts parking with details below")
    public void customer_starts_parking(Map<String, String> parkingdetails) throws Throwable {
        bc.clickLinkParking();
        bc.startInternetParking(parkingdetails);
    }

    @And("^Customer Stops parking from \"([^\"]*)\"$")
    public void customer_validates_stops_transaction_in_something(String strArg1, Map<String, String> stopParkingdetails) throws Throwable {
        if (strArg1.equals("myyellowbrick")) {
            bc.clickLinkParking();
            bc.stopParkingFromMYB();
        }
        if (strArg1.equals("municipality")) {
            bc.stopParkingFromMunicipality(stopParkingdetails);
        }
        Thread.sleep(5000);
    }

    @Then("^Customer navigates to reset password screen$")
    public void customer_navigates_to_forgot_password_screen() throws Throwable {
        ac.clickOnElement(myYellowbrickLoginPageObject.forgetPasswordLink);
    }

    @Then("^Customer inserts customer number \"(.+)\"$")
    public void customer_inserts_customer_number(String customerNumber) throws Throwable {
        bc.fillCustomerNumber(customerNumber);
    }

    @When("Customer navigates to Pplus pass")
    public void customer_navigates_to_ppass() throws Throwable {
        if(COUNTRY.equals("BE")){
            ac.mouseOverElement(myYellowBrick.cardLinkNavigation_BE);
        }
        else {
            ac.mouseOverElement(myYellowBrick.cardLinkNavigation_NL);
        }
        ac.clickOnElement(myYellowBrick.ppluspass);
    }

    @When("Customer navigates to change password screen")
    public void customer_navigates_to_change_password() throws Throwable {
        ac.mouseOverElement(myYellowBrick.customerDetails);
        ac.clickOnElement(myYellowBrick.customerPasswordNavigation);
    }

    @Then("^Customer navigates to \"([^\"]*)\"$")
    public void customer_navigates_to_some_page_in_myb(String strArg1) throws Throwable {
        if (strArg1.equals("Payment Method")) {
            ac.mouseOverElement(myYellowBrick.customerMainMenuButton);
            ac.clickOnElement(myYellowBrick.paymentDropDownButton);
            Thread.sleep(2000);
        }
        if (strArg1.equals("Personal details")) {
            ac.mouseOverElement(myYellowBrick.customerMainMenuButton);
            ac.clickOnElement(myYellowBrick.personalDetailsDropDownButton);
            Thread.sleep(2000);
        }
        if (strArg1.equals("Mobile phone Addition")) {
            ac.mouseOverElement(myYellowBrick.mobileMainMenuButton);
            ac.clickOnElement(myYellowBrick.mobileDropDownButton);
            Thread.sleep(2000);
        }
        if (strArg1.equals("Mobile phone Overview")) {
            ac.mouseOverElement(myYellowBrick.mobileMainMenuButton);
            ac.clickOnElement(myYellowBrick.mobileListDropDownButton);
            Thread.sleep(2000);
        }
        if (strArg1.equals("Order")) {
            if(COUNTRY.equals("BE")){
                ac.mouseOverElement(myYellowBrick.cardLinkNavigation_BE);
            }
            else {
                ac.mouseOverElement(myYellowBrick.cardLinkNavigation_NL);
            }
            ac.clickOnElement(myYellowBrick.orders);
            Thread.sleep(2000);
        }
    }

    @Then("^Add mobile phone number (.+) for (.+)$")
    public void mobile_number_Addition(String mobileNumber,String customerNr) throws Throwable {
        bc.AddmobilePhone(mobileNumber,customerNr);
    }
    @Then("^Update mobile phone number (.+) to (.+)$")
    public void mobile_number_Update(String oldNumber,String newNumber) throws Throwable {
        bc.UpdatemobilePhone(oldNumber,newNumber);
    }
    @Then("^Delete mobile phone number (.+)$")
   public void mobile_number_Delete(String mobileNumber) throws Throwable {
        bc.DeletemobilePhone(mobileNumber);
    }
    @Then("^Validate mobile number \"([^\"]*)\" for (.+)$")
    public void mobile_number_compare(String action, String mobileNumber) throws Throwable {
        bc.mobilePhoneCheck(action,mobileNumber);
    }

    @Then("^Updating personal information with test data")
    public void personal_information_update() throws Throwable {
        bc.fillPersonalData("");
    }

    @Then("^Restore personal information with test data")
    public void personal_information_restore() throws Throwable {
        bc.fillPersonalData("_restore");
    }

    @And("^Compare updated info with database")
    public void personal_information_compare() throws Throwable {
        bc.comparePersonalData();
    }

    @Then("Customer updates ppluspass (.+) to (.+)$")
    public void customer_updates_ppass_using_something(String change, String status) throws Throwable {
        ac.PPlusPassState(getValue("ppluspass"), "modify");
        Thread.sleep(2000);
        if (change.equals("State")) {
            if (status.equals("Actief")) {
                ac.selectFromDropdown(myYellowBrick.passstatuschange, "Actief");
            }
            if (status.equals("Geblokkeerd")) {
                ac.selectFromDropdown(myYellowBrick.passstatuschange, "Geblokkeerd");
            }
        }
        if (change.equals("User")) {
            status = status.replace("transpondercard", getValue("transpondercard"));
            ac.selectTextFromDropdown(myYellowBrick.transpondercardchange, status);
        }
        ac.clickOnElement(myYellowBrick.modifySubmit);
    }

    @And("^Customer validates ppluspass (.+) as (.+)$")
    public void customer_validates_PPlusPass_details_using_something(String change, String status) throws Throwable {
        ac.PPlusPassState(getValue("ppluspass"), "details");
        if (change.equals("State")) {
            if (status.equals("Actief")) {
                Assert.assertEquals(ac.retrieveTextFromElement(myYellowBrick.passstatus), "Actief");
            }
            if (status.equals("Geblokkeerd")) {
                Assert.assertEquals(ac.retrieveTextFromElement(myYellowBrick.passstatus), "Geblokkeerd");
            }
        }
        if (change.equals("User")) {
            status = getValue("transpondercard");
            String[] TransponderCard = status.split("/");
            Assert.assertEquals(ac.retrieveTextFromElement(myYellowBrick.linkedtranspondercard), TransponderCard[0].trim());
        }
        ac.clickOnElement(myYellowBrick.backBtn);
    }


    @Then("^Customer validates the start transaction from the table$")
    public void customer_validates_the_start_transaction_from_the_table(Map<String, String> validateDetails) throws Throwable {
        Assert.assertEquals(validateDetails.get("Licence plate") + COUNTRY, ac.retrieveTextFromElement(myYellowBrick.tblLicencePlate));
        Assert.assertEquals(validateDetails.get("Zone code"), ac.retrieveTextFromElement(myYellowBrick.tblZoneCode));
        Assert.assertEquals(validateDetails.get("Provider"), ac.retrieveTextFromElement(myYellowBrick.tblProvider));

    }

    @And("^Customer Validates the on going transaction from \"([^\"]*)\"$")
    public void customer_validates_the_on_going_transaction_from_something(String strArg1, Map<String, String> parkingdetails) throws Throwable {
        if (strArg1.equals("municipality")) {
            ac.clickOnElement(municipality.parkedCarTab);
            ac.clickOnElement(municipality.clickopenTabfromLicencePlate(parkingdetails.get("Licence plate")));
        }
    }

    @And("^Customer validate stop parking in \"([^\"]*)\"$")
    public void customer_validate_stop_parking_in_something(String strArg1) throws Throwable {
        if (strArg1.equals("municipality")) {
            Assert.assertEquals(ac.retrieveTextFromElement("labelReservationStopped"), "labelReservationStopped");
        } else if (strArg1.equals("myyellowbrick")) {
            bc.clickLinkParking();
            bc.noParkingInMYB();
        }
    }

    @Then("^Customer selects \"([^\"]*)\" payment method$")
    public void customer_selects_payment_method_in_myb(String strArg1) throws Throwable {
        if (strArg1.equals("direct debit")) {
            bc.selectDirectDebitPaymentMethod();
        } else if (strArg1.equals("credit card")) {
            bc.selectCreditCardPaymentMethod();
        }
    }

    @Then("^Customer enters \"([^\"]*)\" details$")
    public void customer_enters_some_details(String strArg1) throws Throwable {
        if (strArg1.equals("direct debit")) {
            bc.enterDirectDebitDetails();
            String successMessage = driver.findElement(myYellowBrick.successMessage).getTagName();
            Assert.assertEquals(successMessage, "div");
        } else if (strArg1.equals("credit card")) {
            bc.enterCreditCardDetails();
        }
    }

    @Then("^Customer uploads \"([^\"]*)\"$")
    public void customer_upload_some_file(String strArg1) throws Throwable {
        if (strArg1.equals("mandate")) {
            ac.uploadFile(myYellowBrick.selectMandateField, getValue("mandateFilePath"));
            ac.clickOnElement(myYellowBrick.uploadMandateButton);
            Thread.sleep(8000);
        }
    }

    @Then("^Customer verifies that \"([^\"]*)\"$")
    public void customer_verifies_something(String strArg1) throws Throwable {
        if (strArg1.equals("mandate is uploaded")) {
            Assert.assertTrue(driver.switchTo().alert().getText().contains("Upload geslaagd")
                    || driver.switchTo().alert().getText().contains("Upload succesfully"));
            driver.switchTo().alert().accept();
        }
    }

    @Then("^Customer \"([^\"]*)\" details are saved for \"(.+)\"")
    public void check_if_customer_details_are_saved(String strArg1, String customerNumber) throws Throwable {
        long customerId = databaseConnections.getCustomer(customerNumber).getId();
        if (strArg1.equals("direct debit")) {
            customerId = databaseConnections.getCustomer(customerNumber).getCustomerId();
            DirectDebitDetails details = databaseConnections.getDirectDebitDetails(customerId);

            Assert.assertEquals(details.getAccountholder(), getValue("accountholdername"));
            Assert.assertEquals(details.getSepa(), getValue("iban"));

            Assert.assertEquals(databaseConnections.getPaymentMethod(customerId), PaymentMethod.DIRECTDEBIT);

        } else if (strArg1.equals("credit card")) {

            customerId = databaseConnections.getCustomer(customerNumber).getCustomerId();
            Assert.assertEquals(databaseConnections.getPaymentMethod(customerId), PaymentMethod.CREDITCARD);
            Assert.assertTrue(databaseConnections.creditCardReferenceExists(customerId));
        }
    }

    @Given("^Check (.*) and (.*) exist$")
    public void check_something_and_something_exists(String provider, String plan) {
        if(COUNTRY.equals("NL")) {
            if (null != bc.findProductgroup(bc.ProductGroupsByCountry.get(COUNTRY).get(provider), plan)) {
                logger.info(provider + "and " + plan + "exist for country" + COUNTRY);
            } else {
                logger.info(provider + "and " + plan + "not exist for country" + COUNTRY);
                throw new AssumptionViolatedException(provider + " OR " + plan + " Doesn't exist in " + COUNTRY);
            }
        }
    }
    @And("^Customer selects (.+) of (.+) and orders$")
    public void Customer_selects_CardValue(String Value, String CardType) throws Throwable {
        if (CardType.equals("Extra gebruiker")) {
            ac.selectFromDropdown(myYellowBrick.transpondercardsnr, Value);
        }
        if(COUNTRY.equals("NL")) {
            if (CardType.equals("P+ pas")) {
                ac.selectFromDropdown(myYellowBrick.ppluspassNr, Value);
            } else if (CardType.equals("RaamSticker")) {
                ac.selectFromDropdown(myYellowBrick.cardsleeveNr, Value);
            }
        }
        ac.clickOnElement(myYellowBrick.orderconfirm);
    }
    @And("^Customer validates the order \"([^\"]*)\" status for \"([^\"]*)\" having (.*) of value (.*)$")
    public void Customer_validates_CardStatus(String status,String customerNr,String CardType,String value) throws Throwable {
        if(COUNTRY.equals("BE")) {
            if(CardType.equals("Extra gebruiker")) {
                tc.validatecardOrders(status, customerNr, CardType, value);
            }
            else
                {
                    tc.validatecardOrders(status, customerNr, CardType, value);
                }
        }
    }

    @Given ("^Check for the existence of following data in Database for \"([^\"]*)\"$")
    public void Check_data_inDB(String validationField,DataTable userData) throws Throwable {
        databaseConnections.Administrator_checkCustomerDetails(userData,validationField);
    }

   @And ("^Remove email \"([^\"]*)\" from blacklist$")
   public void Remove_email_from_Blacklist(String email) throws Throwable {
       databaseConnections.updateemail_blacklisted("2");
   }

    @And("^Customer revokes PPlusPass$")
    public void Customer_revokes_PPlusPass() throws Throwable {
        ac.clickOnElement(By.xpath("//tr[td[span[contains(text(),'Aangevraagd')]] and td[contains(text(),'"+CurrentDate+"')]  and td[contains(text(),'P+ pas')]]/td[5]/button"));
        ac.clickOnElement(PPlusPass.confirmrevoke);
    }
}