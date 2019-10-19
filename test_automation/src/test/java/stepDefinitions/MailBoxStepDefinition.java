package stepDefinitions;

import base.WebBase;
import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import dto.IVRCustomerdetails;
import frameworkComponents.AutomationComponent;
import frameworkComponents.BusinessComponent;
import frameworkComponents.DatabaseConnections;
import org.junit.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageobjects.registration.Mailbox;

@RunWith(Cucumber.class)
public class MailBoxStepDefinition extends WebBase {
    org.slf4j.Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);
    AutomationComponent ac = new AutomationComponent();
    DatabaseConnections databaseConnections = new DatabaseConnections();
    BusinessComponent bc = new BusinessComponent();
    Mailbox mpg = new Mailbox();
    public static String linktosetPassword;
    public static String linkToResetPassword;
    public String custType = "";
    public final String THANK_YOU = "Thank you";
    public final String WELCOME = "Welcome";
    public final String FORGOT_PASSWORD = "Forgot password";
    public final String REGISTRATION = "Registration";
    public final String RESET_PASSWORD_SCREEN = "Reset password screen";
    public final String MANUAL_VALIDATION="Manual validation";
    public final String CLOSE = "Close";

    public MailBoxStepDefinition() {
        super(CommonConfig.getInstance().getValue(CommonConstants.WEB_AUTOMATION_FILE));
    }

    @And("^Customer checks (.+) mail for \"([^\"]*)\"$")
    public void customer_validate_receiving_mails(String strArg1,String username) throws Throwable {
        try {
            if (driver != null) {
                driver.quit();
            }
            String Firstname, Lastname;
            String[] names = username.split(" ");
            Firstname = names[0];
            Lastname = names[1];
            driver = initializeDriver("Chrome");
            logger.info("Driver initialised");
            driver.get(getValue("mailbox_web"));
            Thread.sleep(7000);
            bc.mailbox(getValue("mailbox_username"), getValue("mailbox_password"));
            if (strArg1.equals(WELCOME)) {
                ac.inputTextinTextBox(mpg.searchText, getValue("welcome_mail"));
                Thread.sleep(1000);
                ac.clickOnElement(mpg.searchButton);
                Thread.sleep(5000);
                ac.clickOnElement(mpg.openMail);
                Thread.sleep(3000);
                String welcomegreet = ac.retrieveTextFromElement(getValue("Beste") + " " + Firstname + " " + Lastname + ",");
                Assert.assertEquals(welcomegreet, getValue("Beste") + " " + Firstname
                        + " " + Lastname + ",");
                linktosetPassword = ac.retrieveTextFromElement(mpg.linktosetNewPassword);
                customerNumber = ac.retrieveTextFromElement(mpg.clientNumber);
                logger.info("Customer Activated Successfully");
                driver.quit();
            } else if (strArg1.equals(MANUAL_VALIDATION)) {
                ac.inputTextinTextBox(mpg.searchText, getValue("manualValidation_mail"));
                ac.clickOnElement(mpg.searchButton);
                Thread.sleep(5000);
                ac.clickOnElement(mpg.openMail);
                Thread.sleep(3000);
                String welcomegreet = ac.retrieveTextFromElement(getValue("Beste") + " " + Firstname + " " + Lastname + ",");
                Assert.assertEquals(welcomegreet, getValue("Beste") + " " + Firstname
                        + " " + Lastname + ",");
                driver.quit();
            } else if (strArg1.equals(CLOSE)) {
                ac.inputTextinTextBox(mpg.searchText, getValue("rejection_mail"));
                ac.clickOnElement(mpg.searchButton);
                Thread.sleep(5000);
                ac.clickOnElement(mpg.openMail);
                Thread.sleep(3000);
                String welcomegreet = ac.retrieveTextFromElement(getValue("Beste") + " " + Firstname + " " + Lastname + ",");
                Assert.assertEquals(welcomegreet, getValue("Beste") + " " + Firstname
                        + " " + Lastname + ",");
                logger.info("Customer has been Rejected");
                driver.quit();
            }
        }
        catch(Exception ex){
            databaseConnections.updateemail_blacklisted("2");
        }
    }

    @And("^Customer validate receiving \"([^\"]*)\" mail$")
    public void customer_validate_receiving_something_mails(String strArg1) throws Throwable {

        if (driver != null) {
            driver.quit();
        }
        driver = initializeDriver("Chrome");
        logger.info("Driver initialised");
        driver.get(getValue("mailbox_web"));
        Thread.sleep(2000);
        bc.mailbox(getValue("mailbox_username"), getValue("mailbox_password"));
        if (strArg1.equals(THANK_YOU)) {
            ac.inputTextinTextBox(mpg.searchText, getValue("thankyou_mail"));
            ac.clickOnElement(mpg.searchButton);
            Thread.sleep(7000);
            ac.clickOnElement(mpg.openMail);
            Thread.sleep(3000);
            if (COUNTRY.equals("NL")) {
                String thankyou = (new WebDriverWait(driver, 2))
                        .until(ExpectedConditions.presenceOfElementLocated(ac.findonElementbyTxt(getValue("thankyou") + " " + getValue("voornaam") + " " + getValue("achternaam") + ","))).getTagName();
                Assert.assertEquals(thankyou, "div");
            } else if (COUNTRY.equals("BE")) {
                String thankyou = ac.retrieveTextFromElement(getValue("thankyou") + " " + getValue("voornaam") + " " + getValue("achternaam") + ",");
                Assert.assertEquals(thankyou, getValue("thankyou") + " " + getValue("voornaam")
                        + " " + getValue("achternaam") + ",");
            }
            driver.quit();
        } else if (strArg1.equals(WELCOME)) {
            ac.inputTextinTextBox(mpg.searchText, getValue("welcome_mail"));
            ac.clickOnElement(mpg.searchButton);
            Thread.sleep(7000);
            ac.clickOnElement(mpg.openMail);
            Thread.sleep(3000);
            String welcomegreet = ac.retrieveTextFromElement(getValue("Beste") + " " + getValue("voornaam") + " " + getValue("achternaam") + ",");
            Assert.assertEquals(welcomegreet, getValue("Beste") + " " + getValue("voornaam")
                    + " " + getValue("achternaam") + ",");
            if(ac.checkElementisDisplayed(mpg.linktosetNewPassword_ANWB)){
                linktosetPassword = ac.retrieveTextFromElement(mpg.linktosetNewPassword_ANWB);
            }
            else {
                linktosetPassword = ac.retrieveTextFromElement(mpg.linktosetNewPassword);
            }
            customerNumber = ac.retrieveTextFromElement(mpg.clientNumber);
            driver.quit();
        } else if (strArg1.equals(FORGOT_PASSWORD)) {
            ac.inputTextinTextBox(mpg.searchText, getValue("forgot_password_email"));
            Thread.sleep(4000);
            ac.clickOnElement(mpg.searchButton);
            Thread.sleep(5000);
            ac.clickOnElement(mpg.openMail);
            Thread.sleep(4000);
            linkToResetPassword = ac.retrieveTextFromElement(mpg.linkToResetPassword);
            driver.quit();
        }
    }
    @When("^Customer sets new password in \"([^\"]*)\"$")
    public void customer_sets_new_password(String location) throws Throwable {
        String url = "";
        if (location.equals(REGISTRATION)) {
            url = linktosetPassword;
        } else if (location.equals(RESET_PASSWORD_SCREEN)) {
            url = linkToResetPassword;
        }
        driver = initializeDriver("Chrome");
        logger.info("Driver initialised");
        if (url.startsWith("http:")) {
            url = url.replaceAll("http", "https");
        }

        driver.get(url);
        bc.setNewPassword(getValue("myyellowbrickpassword"));
    }
    @Then("^Customer logs in to myyellowbrick$")
    public void customer_logs_in_to_myyellowbrick() throws Throwable {
      customer_logs_in_to_myyellowbrick(customerNumber, getValue("myyellowbrickpassword"));
    }

    @Given("^Customer logs in to myyellowbrick with \"(.+)\"$")
    public void customer_logs_in_to_myyellowbrickwith(String customernumber) throws Throwable {

        customer_logs_in_to_myyellowbrick(customernumber, getValue("myyellowbrickpassword"));

    }
    /**
     * Helper functions used by multiple other functions to login in My Yellowbrick
     *
     * @param String customerNumberList
     * @param String password
     * @throws Throwable
     */
    private void customer_logs_in_to_myyellowbrick(String clientNumber, String password) throws Throwable {
        customer_navigates_to_myyellowbrick();
        bc.myyellowbrickLogIn(clientNumber, password);
    }
    @Given("^Customer navigates to myyellowbrick$")
    public void customer_navigates_to_myyellowbrick() throws Throwable {
        if (driver != null) {
            driver.quit();
        }
        driver = initializeDriver(BROWSER);
        logger.info("Driver initialised");

        driver.get(getValue("myyellowbricks"));
    }

    @Given("^Customer logs in to My Yellowbrick using \"(.+)\" with the new password$")
    public void customer_logs_in_to_myyellowbrick_with_new_password(String clientNumber) throws Throwable {
        customer_logs_in_to_myyellowbrick(clientNumber, getValue("myyellowbrickNewPassword"));

    }
    @Given("^Customer logs in to \"([^\"]*)\" with (\\d+)$")
    public void customer_logs_in_to_myyellowbrick_with(String application, String customernumber) throws Throwable {
        if (application.equals("myyellowbrick")) {
            if (customernumber.equals("IVRcustomerlogin")) {
                IVRCustomerdetails ivrCustomerdetails = databaseConnections.getCustomerDetailsForIVR();
                if (driver != null) {
                    driver.quit();
                }

                driver = initializeDriver(BROWSER);
                logger.info("Driver initialised");
                driver.get(getValue("myyellowbricks"));
                bc.myyellowbrickLogIn(ivrCustomerdetails.getCustomerNr(), getValue("myyellowbrickpassword"));
            } else {
                if (driver != null) {
                    driver.quit();
                }
                driver = initializeDriver(BROWSER);
                logger.info("Driver initialised");
                driver.get(getValue("myyellowbricks"));
                bc.myyellowbrickLogIn(customernumber, getValue("myyellowbrickpassword"));
            }

        } else if (application.equals("municipality")) {

            if (driver != null) {
                driver.quit();
            }
            driver = initializeDriver(BROWSER);
            logger.info("Driver initialised");
            driver.get(getValue("municipality"));
            bc.myyellowbrickLogIn(customernumber, getValue("myyellowbrickpassword"));
        }
    }

    @Given("^Customer logs in to myyellowbrick with (.+) in (.+) for (.+)$")
    public void customer_logs_in_to_myyellowbrick_in_Environment(String customernumber,String environment,String country) throws Throwable {
        if (environment.equals(ENVIRONMENT) && country.equals(COUNTRY)) {
            if (driver != null) {
                driver.quit();
            }
            driver = initializeDriver(BROWSER);
            logger.info("Driver initialised");
            driver.get(getValue("myyellowbricks"));
            bc.myyellowbrickLogIn(customernumber, getValue("myyellowbrickpassword"));
        }
        else
        {
            throw new AssumptionViolatedException("Running test data only for "+COUNTRY+" in "+ENVIRONMENT+"");
        }
    }
}
