package stepDefinitions;

import base.WebBase;
import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import frameworkComponents.AutomationComponent;
import frameworkComponents.DatabaseConnections;
import frameworkComponents.TaxameterComponent;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageobjects.taxameter.Customers;
import pageobjects.taxameter.Cards;
import pageobjects.taxameter.TaxameterLogin;
import pageobjects.taxameter.CustomersValidation;
import pageobjects.taxameter.PPlusPass;
import org.openqa.selenium.Alert;

@RunWith(Cucumber.class)
public class TaxameterStepDefinition extends WebBase {

    Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);
    AutomationComponent ac = new AutomationComponent();
    DatabaseConnections db=new DatabaseConnections();
    TaxameterComponent tc=new TaxameterComponent();
    TaxameterLogin taxameter = new TaxameterLogin();
    Cards cards = new Cards();
    Customers customers = new Customers();
    CustomersValidation customersvalidation=new CustomersValidation();
    PPlusPass PPlusPass=new PPlusPass();
    public TaxameterStepDefinition() {
        super(CommonConfig.getInstance().getValue(CommonConstants.WEB_AUTOMATION_FILE));
    }

    @Then("^Administrator logs into Taxameter$")
    public void Admin_logs_in_to_Taxameter_with_something() throws Throwable {
        try {
            if (driver != null) {
                driver.quit();
            }
            driver = initializeDriver(BROWSER);
            logger.info("Driver initialised");
            driver.get(getValue("taxameter"));
            if (COUNTRY.equals("NL")) {
                tc.AdministratorLogIn(getValue("taxameter_userID"), getValue("taxameterpassword"));
            } else if (COUNTRY.equals("BE")) {
                tc.AdministratorLogIn(getValue("taxameter_userID"), getValue("taxameterpassword"));
            } else {
                tc.AdministratorLogIn(getValue("taxameter_userID"), getValue("taxameterpassword"));
            }
            logger.info("Administrator Logged in Successfully");
        } catch (Exception ex) {
            db.updateemail_blacklisted("2");
        }
    }
    //Administrator chooses a main menu item
    @And("^Administrator chooses (.*) from menu$")
    public void Administrator_Chooses_MenuItem(String Choice) throws Throwable {
        driver.switchTo().frame(0) ;
        ac.selectTextFromDropdown(taxameter.menuselect, Choice);
        logger.info(Choice + " page Loaded");
    }
    // Administrator chooses batch validation
    @And("^Administrator chooses Batch validation for product group \"([^\"]*)\"$")
    public void Administrator_Batch_validation(String productGroup) throws Throwable {
        Thread.sleep(50);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
        ac.selectTextFromDropdown(cards.productgroupsdropdown, productGroup);
        Thread.sleep(50);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
        ac.clickOnElement(cards.buttonbatchvalidate);
        ac.clickOnElement(cards.confirmbatchvalidation);
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(50);
        }
        catch(Exception ex){
            logger.info("No Confirmation alert present");
        }
    }
    //Administrator updates card value and does single validation on the card
    @And("^Administrator updates (.*) value to (.*)$")
    public void Administrator_Updates_CardValues(String CardType,String Value) throws Throwable {
       if(COUNTRY.equals("BE")){
           if(CardType.equals("Extra gebruiker")){
               ac.selectFromDropdown(cards.amountselected, Value);
               ac.clickOnElement(cards.savecardupdate);
               try {
                   Alert alert = driver.switchTo().alert();
                   alert.accept();
                   Thread.sleep(50);
               }
               catch(Exception ex){
                   logger.info("No Confirmation alert present");
               }
           }
       }
       else {
           ac.selectFromDropdown(cards.amountselected, Value);
           if (CardType.equals("RaamSticker")) {
               ac.clickOnElement(cards.savecardupdate);
               try {
                   Alert alert = driver.switchTo().alert();
                   alert.accept();
                   Thread.sleep(1000);
               } catch (Exception ex) {
                   logger.info("No Confirmation alert present");
               }
           } else {
               ac.clickOnElement(cards.savecardupdate);
               try {
                   Alert alert = driver.switchTo().alert();
                   alert.accept();
                   Thread.sleep(50);
               } catch (Exception ex) {
                   logger.info("No Confirmation alert present");
               }
           }
       }
    }

    //Administrator searches for the customer on the page and selects single validation for the customer specified
    @And("^Administrator finds customer \"([^\"]*)\" in \"([^\"]*)\" and chooses Single validation of (.*)$")
    public void Administrator_finds_Customer_and_Chooses_Validation(String CustomerNr,String provider, String CardType) throws Throwable {
       if(COUNTRY.equals("BE")) {
           if (CardType.equals("Extra gebruiker")) {
               Thread.sleep(50);
               //Switching between frames, based on name of frame
               driver.switchTo().parentFrame();
               driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
               driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
               ac.selectTextFromDropdown(cards.productgroupsdropdown, "YELLOWBRICKBE");
               Thread.sleep(50);
               driver.switchTo().parentFrame();
               driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
               tc.selectcardorder(CustomerNr, CardType);
           }
       }
       else
       {
           Thread.sleep(50);
           //Switching between frames, based on name of frame
           driver.switchTo().parentFrame();
           driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
           driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
           ac.selectTextFromDropdown(cards.productgroupsdropdown, provider);
           Thread.sleep(50);
           driver.switchTo().parentFrame();
           driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
           tc.selectcardorder(CustomerNr, CardType);
       }
    }

    @And("^Activate if the customer type is (.*) for the following data in (.*) for (.*)$")
    public void Activate_if_customer_isOfType_Business(String type,String plan,String provider,DataTable userData) throws Throwable{
        if(type.equals("Business")) {
            if (provider.equals("ANWB")) {
                if (plan.equals("BASC")) {
                    plan = "ANWB";
                } else if (plan.equals("SUBC")) {
                    plan = "ANWB-SUBSCRIPTION";
                }
            }
            if(provider.equals("YB")) {
                if (plan.equals("FLEX")) {
                    plan = "YELLOWBRICK-FLEX";
                } else if (plan.equals("SUBC")) {
                    plan = "YELLOWBRICK-SUBSCRIPTION";
                } else if (plan.equals("BASC")) {
                    plan = "YELLOWBRICK-BASIC";
                }
            }
            Administrator_finds_customer_inValidation("Validation", plan, userData);
            ac.clickOnElement(customersvalidation.dataOK);
            Administrator_validates_inTaxamater("Activate");
        }
    }

    //Administrator searches for customer in search frame
    @And ("^Administrator searches for customer \"([^\"]*)\"$")
    public void Administrator_Searches_Customer(String CustomerNr) throws Throwable {
        Thread.sleep(50);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
        ac.inputTextinTextBox(customers.customername, CustomerNr);
        ac.clickOnElement(customers.searchcustomer);
    }
    //Administrator selects Options given Inline
    @And ("^Administrator navigates to (.*)$")
    public void Administrator_Navigates_within_CustomerTab(String Option) throws Throwable {
        Thread.sleep(50);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechtsboven']")));
        tc.Navigates_To_Menu(Option);
    }
    
    //Administrator orders cards with a value
    @And("^Administrator orders (.*) of (.*)$")
    public void Administrator_Orders_Cards(String Value,String CardType) throws Throwable{
        Thread.sleep(50);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechtsonder']")));
        if(CardType.equals("Extra gebruiker"))
        {
            ac.clickOnElement(customers.ordertranspondercards);
            ac.selectFromDropdown(customers.cardsvalue,Value);
            ac.clickOnElement(customers.addcards);
            Thread.sleep(1000);
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                Thread.sleep(50);
            }
            catch(Exception ex){
                logger.info("No Confirmation alert present");
            }
        }
        else if(CardType.equals("RaamSticker"))
        {
            ac.clickOnElement(customers.ordersleeves);
            ac.selectFromDropdown(customers.cardsvalue,Value);
            ac.clickOnElement(customers.addcards);
            try{
                Alert alert = driver.switchTo().alert();
                alert.accept();}
            catch(Exception ex)
            {
                logger.info("No Confirmation alert present");
            }
        }
        else if(CardType.equals("P+ pas"))
        {
            ac.clickOnElement(customers.orderppluspases);
            ac.selectFromDropdown(customers.cardsvalue,Value);
            ac.clickOnElement(customers.addcards);
            try{
                Alert alert = driver.switchTo().alert();
                alert.accept();}
            catch(Exception ex)
            {
                logger.info("No Confirmation alert present");
            }
        }
    }

    // Administrator validates if Batch validation on cards are updated on the page
    @And("^Administrator validates Batch Order Accepted Status of (.*)$")
    public void Administrator_Validates_Batch_Card_Orders(String menuItem) throws Throwable {
        // number of card orders validated in the page
        int totalRows = driver.findElements(cards.rowcountbatchvalidation).size();
        //getting the total number of columns as the td's index
        int lastColumn = driver.findElements(cards.colcountbatchvalidation).size();
        String status;
        if (menuItem.equals("Card orders to be validated")) {
            status = "Gevalideerd";
        } else {
            status = "lblValidated";
        }
        // the first card detail starts from row 3, below the header row
        int row=3;
        while(row<= totalRows) {
            // Asserts through every odd rows's last column value
            Assert.assertEquals(ac.retrieveTextFromElement(By.xpath("//tbody[tr[td[contains(text(),'Product group')]]]/tr[" + row + "]/td[" + lastColumn + "]")).trim(), status);
            row=row+2;
        }
    }

    // Administrator validates if single validation on cards are updated on the page
    @And("^Administrator validates the order \"([^\"]*)\" status for \"([^\"]*)\" having (.*) of value (.*)$")
    public void Administrator_Validates_SingleCardOrders_inDB(String status,String customerNr,String CardType,String value) throws Throwable {
       if(COUNTRY.equals("BE")){
           if(CardType.equals("Extra gebruiker")){
               tc.validatecardOrders(status,customerNr,CardType,value);
           }
       }
       else {
           tc.validatecardOrders(status, customerNr, CardType, value);
       }
    }
    //Administrator selects a menu item
    @And("^Administrator selects \"([^\"]*)\" from menu$")
    public void Administrator_Selects(String Choice) throws Throwable {
        try {
            driver.switchTo().frame(0);
            ac.selectTextFromDropdown(taxameter.menuselect, Choice);
            logger.info(Choice + " page Loaded");
        }
        catch(Exception ex){
            db.updateemail_blacklisted("2");
        }
    }
    @And("^Administrator finds customer based in \"([^\"]*)\" from product group \"([^\"]*)\"$")
    public void Administrator_finds_customer_inValidation(String validationType,String productGroup,DataTable userdata) throws Throwable {
        try {
            driver.switchTo().parentFrame();
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
            if(COUNTRY.equals("BE")){
                productGroup="YELLOWBRICKBE";
            }
            ac.selectTextFromDropdown(customersvalidation.productgroupsdropdown, productGroup);
            driver.switchTo().parentFrame();
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
            tc.findCustomr_in_Taxamater(validationType, userdata);
        }
        catch(Exception ex){
        db.updateemail_blacklisted("2");
        }
    }
    @And("^Administrator validates the following data for \"([^\"]*)\" in Taxameter and Database$")
    public void Administrator_validates_customer_in_TaxamaterandDB(String validationType,DataTable userdata) throws Throwable {
       try {
           tc.validatecustomerinTaxamaterandDB(validationType, userdata);
       }
       catch(Exception ex){
           db.updateemail_blacklisted("2");
       }
    }
    @And("^Administrator (.*) validation$")
    public void Administrator_validates_inTaxamater(String decision) throws Throwable {
        try {
            if (decision.equals("Activate")) {
                ac.clickOnElement(customersvalidation.activate);
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception ex) {
                    logger.info("No Confirmation alert present");
                }
            }
            if (decision.equals("Reject")) {
                ac.clickOnElement(customersvalidation.delete);
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception ex) {
                    logger.info("No Confirmation alert present");
                }
            }
        } catch (Exception ex) {
            db.updateemail_blacklisted("2");
        }
    }

    //Linking or Unlinking PPlus Pass
    @When("^Administrator (.*) PPlusPass to Transpondercard$")
    public void Administrator_LinksUnlinks_Cards(String Link) throws Throwable {
        String transpondercard=getValue("transpondercard");
        if(Link.equals("Unlink")){
            // modifies transponder card to be empty as it is an unlink
            transpondercard="";
        }
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechtsonder']")));
        tc.Administrator_links_ppluspass(getValue("ppluspass"),transpondercard,"Link/Unlink");
    }
    //Validating Link or Unlink of PPlusPass
    @And("^Administrator validates (.*) for PPluspass to Transpondercard$")
    public void Administrator_Validates_LinkUnlink(String Link) throws Throwable {
        String transpondercard=getValue("transpondercard");
        if(Link.equals("Unlink")){
            // modifies the transponder card values to be checked
            transpondercard="(geen)/(geen)";
        }
        else
        {
            // modifies the transponder card values to be checked
            transpondercard = transpondercard.replace("-", "(geen)");
        }
        tc.Administrator_links_ppluspass(getValue("ppluspass"),transpondercard,"Validate Link/Unlink");
    }
    //Assigning Customer Reference Number to PPlusPass
    @And("^Administrator assigns customer to PPlusPass$")
    public void Administrator_Assigns_Customer_To_Card() throws Throwable {
        ac.clickOnElement(PPlusPass.changestatus);
        ac.inputTextinTextBox(PPlusPass.customerrefNr,getValue("customerNr"));
        ac.clickOnElement(PPlusPass.attachcustomerrefNr);
    }
    //Validating Customer Reference Number to PPlusPass
    @And("^Administrator validates the Customer Reference for PPlusPass$")
    public void Administrator_Validates_CustomerRefNumber() throws Throwable {
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
        ac.inputTextinTextBox(PPlusPass.ppluspasssearchtext,getValue("ppluspass"));
        ac.clickOnElement(PPlusPass.ppluspasssearchbutton);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
        tc.Administrator_links_customerNr(getValue("ppluspass"),getValue("customerNr"),"validate customerNr");
    }
    //Changing PPlusPass Status
    @When("^Administrator changes PPlusPass status to (.*)$")
    public void Administrator_Changes_PPlusPassStatus(String Status) throws Throwable {
        ac.clickOnElement(PPlusPass.changestatus);
        if(Status.equals("Geblokkeerd")) {
            // Check if the card is already Blocked
            ac.selectTextFromDropdown(PPlusPass.cardstatusdropdown, "cardstatus.Geblokkeerd");
            ac.clickOnElement(PPlusPass.cardstatussave);
        }
        // Check if the card is already Active
        if(Status.equals("Actief")) {
            ac.selectTextFromDropdown(PPlusPass.cardstatusdropdown, "cardstatus.Actief");
            ac.clickOnElement(PPlusPass.cardstatussave);
        }
        // Check if the card is already Delivered
        if(Status.equals("Ingeleverd")) {
            ac.selectTextFromDropdown(PPlusPass.cardstatusdropdown, "cardstatus.Ingeleverd");
            ac.clickOnElement(PPlusPass.cardstatussave);
        }
    }
    //Validating PPlusPass Status
    @Then("^Administrator searches for pplupass and validates if the \"([^\"]*)\" status is (.*)$")
    public void Administrator_Validates_PPlusPassStatus(String change,String Status) throws Throwable {
        if(change.equals("old")){
            driver.switchTo().parentFrame();
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='topOnder']")));
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));
        }
        else
        {
            driver.switchTo().parentFrame();
            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='links']")));

        }
        ac.inputTextinTextBox(PPlusPass.ppluspasssearchtext,getValue("ppluspass"));
        ac.clickOnElement(PPlusPass.ppluspasssearchbutton);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='rechts']")));
        Assert.assertEquals(ac.retrieveTextFromElement(PPlusPass.cardstatus).trim(),Status);
    }

}
