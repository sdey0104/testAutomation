package frameworkComponents;
import base.WebBase;
import cucumber.api.DataTable;
import dto.CardOrders;
import org.openqa.selenium.By;
import java.io.IOException;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageobjects.taxameter.Customers;
import pageobjects.taxameter.CustomersValidation;
import pageobjects.taxameter.TaxameterLogin;
import stepDefinitions.SmokeStepDefinition;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TaxameterComponent extends WebBase {
    Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);
    AutomationComponent ac = new AutomationComponent();
    TaxameterLogin taxameter=new TaxameterLogin();
    Customers customers = new Customers();
    CustomersValidation customersvalidation = new CustomersValidation();
    DatabaseConnections databaseConnections = new DatabaseConnections();

    //Logging into Taxameter application (NL/BE/DE has same username and Password)
    public void AdministratorLogIn(String username, String password) throws  InterruptedException {
        ac.inputTextinTextBox(taxameter.username, username);
        ac.inputTextinTextBox(taxameter.password, password);
        ac.clickOnElement(taxameter.login);
    }

    //Selecting an option from main menu item
    public void Navigates_To_Menu(String Option) throws  InterruptedException {
        switch(Option){
            case "General":ac.clickOnElement(customers.general);break;
            case "Membership":ac.clickOnElement(customers.membership);break;
            case "Status":ac.clickOnElement(customers.status);break;
            case "PinCode": ac.clickOnElement(customers.pincode);break;
            case "Transactions":ac.clickOnElement(customers.transactions);break;
            case "Transponder cards":ac.clickOnElement(customers.transpondercards);break;
            case "Mobile Telephone Numbers":ac.clickOnElement(customers.mobiletelephonenumbers);break;
            case "Other cards":ac.clickOnElement(customers.othercards);break;
            case "Card orders":ac.clickOnElement(customers.cardorders);break;
            case "Customer History": ac.clickOnElement(customers.customerhistory);break;
            default :ac.clickOnElement(customers.general); }
    }

    // Selecting single validation option of cards based on card type and customer number
    public void selectcardorder(String CustomerNr,String cardType) throws InterruptedException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        cardType=cardType.replace("Extra gebruiker","Transponderkaart").replace("RaamSticker","Card sleeve");
        if (cardType.equals("Card sleeve")) {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + CustomerNr + "')]and td[contains(text(),'"+dateFormat.format(date)+"')]]/td[7]/input"));
        } else {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + CustomerNr + "')] and td[contains(text(),'" + cardType + "')] and td[contains(text(),'"+dateFormat.format(date)+"')]]/td[8]/input"));
        }
    }

    // Administrator validates if single validation on cards are updated in DB
    public void validatecardOrders(String status,String customerNr,String CardType,String value) throws IOException, SQLException {
        //Replacing status codes based on Database values
        status = status.replace("Pending", "1")
                .replace("Order accepted", "2")
                .replace("Ordered", "3")
                .replace("Revoked","4");
        CardOrders order = databaseConnections.getcardOrderDetails(customerNr, CardType);
        try {
            Assert.assertEquals(order.getStatus(), status);
            Assert.assertEquals(order.getAmount(), value);
            logger.info(CardType + "Card order is activated successfully");
        } catch (Exception ex) {
            logger.info(CardType + "Card order has not been activated");
        }
    }

    public void findCustomr_in_Taxamater(String validationType, DataTable userdata) throws IOException, SQLException, InterruptedException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String CurrentDate = formatter.format(date);
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            if (validationType.equals("Validation")) {
                Thread.sleep(2000);
                ac.clickOnElement(customersvalidation.sortnames);
                ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + userdatamap.get("Firstname") + "')] and td[contains(text(),'" + userdatamap.get("Lastname") + "')] and td[contains(text(),'" + CurrentDate + "')]]/td[12]/input"));

            } else {
                ac.clickOnElement(customersvalidation.risktab);
                ac.clickOnElement(customersvalidation.sortnames);
                ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + userdatamap.get("Firstname") + "')] and td[contains(text(),'" + userdatamap.get("Lastname") + "')] and td[contains(text(),'" + CurrentDate + "')]]/td[12]/input"));
            }
        }
    }

    public void validatecustomerinTaxamaterandDB(String validationType, DataTable userdata) throws IOException, ParseException, SQLException, InterruptedException {
        // Check if customer details are present in Taxamater validation/Risk
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            if (validationType.equals("Private-Blacklisted")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Firstname"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Blacklisted")) {
                    logger.info("Customer name is Blacklisted and needs to be validated");
                }
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Birthday"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Blacklisted")) {
                    logger.info("Customer Date of birth is Blacklisted and needs to be validated");
                }
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Licenseplate"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Blacklisted")) {
                    logger.info("Customer License plate is Blacklisted and needs to be validated");
                }
                ac.clickOnElement(customersvalidation.dataOK);
            }
            if (validationType.equals("Business-Blacklisted")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Email"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Blacklisted")) {
                    logger.info("Customer Email is Blacklisted and needs to be validated");
                }
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Phonenumber"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Blacklisted")) {
                    logger.info("Customer Phone number is Blacklisted and needs to be validated");
                }
                ac.clickOnElement(customersvalidation.dataOK);
            }
            if (validationType.equals("VAT-COC-Empty")) {
                if (ac.retrieveTextFromElement(customersvalidation.Riskreasonautovalidation).contains("VAT not specified")) {
                    logger.info("Customer has registered with empty VAT and details have landed in Risk");
                }
                if (ac.retrieveTextFromElement(customersvalidation.Riskreasonautovalidation).contains("COC not specified")) {
                    logger.info("Customer has registered with empty COC and details have landed in Risk");
                }
            }
            if (validationType.equals("SUSPECT_LICENSE_PLATE")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Licenseplate"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("already in use by customer")) {
                    logger.info("Customer has registered with a suspect license plate and needs to be validated");
                }
            }
            if (validationType.equals("COC/VAT_EXISTS")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("COC"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("already in use by customer")) {
                    logger.info("Customer has registered with an existing COC and needs to be validated");
                }
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("VAT"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("already in use by customer")) {
                    logger.info("Customer has registered with an existing VAT and needs to be validated");
                }
            }
            if (validationType.equals("KEYWORDS_CHECK")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("Keyword"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("is used")) {
                    logger.info("Customer has registered with a Blacklisted keyword and needs to be validated");
                }
            }
            if (validationType.equals("IBAN_EXISTS")) {
                if (ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains(userdatamap.get("IBAN"))
                        && ac.retrieveTextFromElement(customersvalidation.validatereasonautovalidation).contains("Irrecoverable")) {
                    logger.info("Customer has registered with an existing COC and needs to be validated");
                }
            }
        }
        // Check if customer details are present in Database with given status
        databaseConnections.Administrator_checkCustomerDetails(userdata, validationType);
    }

    // Administrator Selects a PPlusPass and links it to a Transpondercard / changes its state
    public void Administrator_links_ppluspass(String ppluspass,String transponderCard,String action) throws IOException, InterruptedException {
        if (action.equals("Link/Unlink")) {
            By ppluspassSelect=By.xpath("//tr[td[b[contains(text(),'"+ppluspass+"')]]]/td[4]/select");
            By ppluspassLink=By.xpath("//tr[td[b[contains(text(),'"+ppluspass+"')]]]/td[4]/input");
            ac.selectTextFromDropdown(ppluspassSelect, transponderCard.trim());
            ac.clickOnElement(ppluspassLink);
        }
        if (action.equals("Validate Link/Unlink")) {
            By ppluspassUpdate = By.xpath("//tr[td[b[contains(text(),'" + ppluspass.trim() + "')]]]/td[2]");
           try{
               Assert.assertEquals(ac.retrieveTextFromElement(ppluspassUpdate),transponderCard.replace(" ",""));
               logger.info("PPlus Pass link/unlink to Transponder card is validated");
               }
            catch(Exception ex)
               {
                   logger.info("PPlus Pass is not linked/Unlinked to transponder card");
               }
        }
    }
    // Administrator assigns a customer reference Number to a PPlusPass
    public void Administrator_links_customerNr(String ppluspass,String customerNr,String action) throws IOException, InterruptedException {
        if(action.equals("validate customerNr")){
            Assert.assertEquals(ac.retrieveTextFromElement(By.xpath("//tr[td[contains(text(),'"+customerNr+"')]]/td[3]")).trim(),customerNr);
        }
    }
}