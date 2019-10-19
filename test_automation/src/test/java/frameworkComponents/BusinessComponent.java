package frameworkComponents;

import cucumber.api.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import base.WebBase;
import dto.Customer;
import dto.Mobile;
import dto.ProductGroup;
import factory.ProductgroupFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjects.municipality.Municipality;
import pageobjects.myyellowbrick.MyYellowBrick;
import pageobjects.myyellowbrick.MyYellowBrickLogin;
import pageobjects.myyellowbrick.PasswordRecovery;
import pageobjects.myyellowbrick.ResetPassword;
import pageobjects.registration.Confirm;
import pageobjects.registration.Mailbox;
import pageobjects.registration.ProductSelection;
import pageobjects.registration.YourData;
import pageobjects.registration.Summary;

import stepDefinitions.SmokeStepDefinition;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.NoSuchElementException;

public class BusinessComponent extends WebBase {

    YourData yourData = new YourData();
    AutomationComponent ac = new AutomationComponent();
    PasswordRecovery passwordRecovery = new PasswordRecovery();
    ProductSelection productSelection = new ProductSelection();
    Mailbox mailbox = new Mailbox();
    Summary summary = new Summary();
    ResetPassword resetPassword = new ResetPassword();
    MyYellowBrickLogin myYellowBrickLogin = new MyYellowBrickLogin();
    MyYellowBrick myYellowBrick = new MyYellowBrick();
    Municipality municipality = new Municipality();
    Confirm confirm = new Confirm();
    DatabaseConnections db = new DatabaseConnections();
    WebElement popupOk;
    Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);


    // Filling with Default Values
    public void fillProductSelectionPage(int count) throws InterruptedException {
        ac.inputTextinTextBox(productSelection.ppluspasstext, "0");
        ac.inputTextinTextBox(productSelection.licencePlate, getValue("licenseplate").trim());
        ac.clickOnElement(productSelection.elementClick);
        ac.inputTextinTextBox(productSelection.mobileNumber, getValue("mobilenummer").trim());
        ac.clickOnElement(productSelection.elementClick);
        int i = 0;
        if (!(COUNTRY.equals("BE"))) {
            while (i < count) {
                ac.clickOnElement(productSelection.pPlusPassAdd);
                i++;
            }
        }
//        // Input Discount code
//        if (ac.checkElementisDisplayed(productSelection.actioncodelink)) {
//            int CostBeforeDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
//            ac.clickOnElement(productSelection.actioncodelink);
//            ac.inputTextinTextBox(productSelection.actioncodetext, getValue("actioncode").trim());
//            ac.clickOnElement(productSelection.applyactioncode);
//            if (ac.checkElementisDisplayed(productSelection.actioncodeapplied)) {
//                int CostAfterDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
//                if (CostAfterDiscount < CostBeforeDiscount) {
//                    Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeapplied), getValue("actioncodeapplied").replaceAll(" ", ""));
//                }
//            } else {
//                Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeinvalid), getValue("actioncodeinvalid").replaceAll(" ", ""));
//            }
//        }
    }

    // Filling with Defined Table Values
    public String fillProductSelectionPage(DataTable userdata, String CustType, int count) throws IOException, InterruptedException {
        String ErrFlag = "N";
        // Creating a map to update empty as null
        Map<String, String> userdataMapupdated = new HashMap<String, String>();
        //Iterating through head row and each consecutive row as hashmap
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            for (String key : userdatamap.keySet()) {
                //Creating a new hashmap with updated values (empty as nulls)
                userdataMapupdated.put(key, userdatamap.get(key).trim().replace("empty", ""));
            }
            ac.inputTextinTextBox(productSelection.ppluspasstext, "0");
            ac.inputTextinTextBox(productSelection.licencePlate, userdataMapupdated.get("LicensePlate"));
            ac.clickOnElement(productSelection.elementClick);
            ac.inputTextinTextBox(productSelection.mobileNumber, userdataMapupdated.get("Phonenumber"));
            ac.clickOnElement(productSelection.elementClick);
            int i = 0;
            if (!(COUNTRY.equals("BE"))) {
                while (i < count) {
                    ac.clickOnElement(productSelection.pPlusPassAdd);
                    i++;
                }
            }
            //Load Error flag as Yes(Y) or No(N)
            ErrFlag = ErrorValidation(ErrFlag, CustType, "Product Selection Page");
        }
//        // Input Discount code
//        if (ac.checkElementisDisplayed(productSelection.actioncodelink)) {
//            int CostBeforeDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
//            ac.clickOnElement(productSelection.actioncodelink);
//            ac.inputTextinTextBox(productSelection.actioncodetext, getValue("actioncode").trim());
//            ac.clickOnElement(productSelection.applyactioncode);
//            if (ac.checkElementisDisplayed(productSelection.actioncodeapplied)) {
//                int CostAfterDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
//                if (CostAfterDiscount < CostBeforeDiscount) {
//                    Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeapplied), getValue("actioncodeapplied").replaceAll(" ", ""));
//                }
//            } else {
//                Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeinvalid), getValue("actioncodeinvalid").replaceAll(" ", ""));
//            }
//        }
        logger.info("ProductSelectionPageFilled");
        return ErrFlag;
    }

    // Filling with Defined Table Values
    public String fillProductSelectionPageForMultipleUsers(DataTable userdata, String CustType, int count) throws IOException, InterruptedException {
        String ErrFlag = "N";
        int i = 0;
        Map<String, String> userdataMapupdated = new HashMap<String, String>();// Creating a map to update empty as null
        //Iterating through first row and each consecutive row as hashmap
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            for (String key : userdatamap.keySet()) {
                //Creating a new hashmap with updated values (empty as nulls)
                userdataMapupdated.put(key, userdatamap.get(key).trim().replace("empty", ""));
            }
            while (i < Integer.parseInt(userdataMapupdated.get("USERS"))) {
                ac.inputTextinTextBox(By.xpath("//*[@id='cars[" + i + "].extraPlusPassAmount']"), "0");
                ac.inputTextinTextBox(By.xpath("//*[@id='cars" + i + ".licensePlate']"), userdataMapupdated.get("LicensePlate"));
                ac.clickOnElement(productSelection.elementclick);
                ac.inputTextinTextBox(By.xpath("//*[@id='cars" + i + ".mobile']"), userdataMapupdated.get("Phonenumber"));
                ac.clickOnElement(productSelection.elementclick);
                ac.inputTextinTextBox(By.xpath("//*[@id='cars[" + i + "].extraPlusPassAmount']"), userdataMapupdated.get("P+Pass"));
                i++;
                if (i != Integer.parseInt(userdataMapupdated.get("USERS"))) {
                    ac.clickOnElement(productSelection.addNumberofCars);
                }
            }
            //Load Error flag as Yes(Y) or No(N)
            ErrFlag = ErrorValidation(ErrFlag, CustType, "Product Selection Page");
        }
        // Action code is implemented only for ANWB
        //        // Input Discount code
        //        if (ac.checkElementisDisplayed(productSelection.actioncodelink)) {
        //            int CostBeforeDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
        //            ac.clickOnElement(productSelection.actioncodelink);
        //            ac.inputTextinTextBox(productSelection.actioncodetext, getValue("actioncode").trim());
        //            ac.clickOnElement(productSelection.applyactioncode);
        //            if (ac.checkElementisDisplayed(productSelection.actioncodeapplied)) {
        //                int CostAfterDiscount = Integer.parseInt(ac.retrieveTextFromElement(productSelection.costlabel).trim());
        //                if (CostAfterDiscount < CostBeforeDiscount) {
        //                    Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeapplied), getValue("actioncodeapplied").replaceAll(" ", ""));
        //                }
        //            } else {
        //                Assert.assertEquals(ac.retrieveTextFromElement(productSelection.actioncodeinvalid), getValue("actioncodeinvalid").replaceAll(" ", ""));
        //                    logger.info(ac.retrieveTextFromElement(productSelection.actioncodeapplied));
        //                }
        //            } else {
        //                logger.info(ac.retrieveTextFromElement(productSelection.actioncodeinvalid));
        //            }
        //        }
        logger.info("ProductSelectionPageFilled");
        return ErrFlag;
    }

    // Filling with Default Values
    public void fillYourDataPage(String CustType) throws InterruptedException {
        if (CustType.equals("B")) {
            ac.inputTextinTextBox(yourData.businessName, "rob");
            ac.clickOnElement(yourData.elementClick);
            Thread.sleep(2000);
            ac.inputTextinTextBox(yourData.btwNummer, getValue("BTWnummer"));
            ac.inputTextinTextBox(yourData.kvknumber, getValue("kvknummer"));
            ac.clickOnElement(yourData.elementClick);
        }
        ac.selectFromDropdown(yourData.sex, getValue("sex"));
        if (!(COUNTRY.equals("BE") || !(COUNTRY.equals("DE")))) {
            ac.inputTextinTextBox(yourData.initials, getValue("initials"));
            ac.clickOnElement(yourData.elementClick);
        }
        ac.inputTextinTextBox(yourData.firstName, getValue("voornaam"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.lastName, getValue("achternaam"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.birthDay, getValue("birthday"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.birthMonth, getValue("birthmonth"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.birthYear, getValue("birthyear"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.telephone, getValue("mobilenummer"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.emailAddress, getValue("emailaddress"));
        ac.clickOnElement(yourData.elementClick);
        Thread.sleep(27000);
        popupOk = driver.findElement(By.cssSelector("button.ui-state-default:nth-child(2)"));
        if (!(popupOk.equals(null)) && popupOk.isDisplayed()) {
            driver.findElement(By.cssSelector("button.ui-state-default:nth-child(2)")).click();
            //ac.clickOnElement(ac.findonElementbyTxt(getValue(COUNTRY+"_btnjadoorgan")));
        }
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.repeatemailAddress, getValue("emailaddress"));
        ac.clickOnElement(yourData.elementClick);
        ac.selectFromDropdown(yourData.country, getValue("land"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.postalCode, getValue("postcode"));
        ac.clickOnElement(yourData.elementClick);
        if (!(COUNTRY.equals("BE"))) {
            ac.inputTextinTextBox(yourData.additionalHNr, getValue("additionalhnr"));
            ac.clickOnElement(yourData.elementClick);
        }
        ac.inputTextinTextBox(yourData.address, getValue("address"));
        ac.inputTextinTextBox(yourData.houseNumber, getValue("housenummer"));
        ac.clickOnElement(yourData.elementClick);
        ac.inputTextinTextBox(yourData.residence, getValue("city"));
        ac.clickOnElement(yourData.elementClick);
        Thread.sleep(1000);

    }

    // Filling with Defined Table Values
    public String fillYourDataPage(DataTable userdata, String CustType) throws InterruptedException {
        String ErrFlag = "N";
        String Day = "", Month = "", Year = "";
        // Creating a map to update empty as null
        Map<String, String> userdataMapupdated = new HashMap<String, String>();
        //Iterating through head row and each consecutive row as hashmap
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            for (String key : userdatamap.keySet()) {
                //Creating a new hashmap with updated values (empty as nulls)
                userdataMapupdated.put(key, userdatamap.get(key).trim().replace("empty", ""));
            }
            if (CustType.equals("B")) {
                ac.inputTextinTextBox(yourData.businessName, userdataMapupdated.get("Company name"));
                ac.clickOnElement(yourData.elementClick);
                Thread.sleep(2000);
                ac.inputTextinTextBox(yourData.kvknumber, userdataMapupdated.get("COC"));
                ac.clickOnElement(yourData.elementClick);
                ac.inputTextinTextBox(yourData.btwNummer, userdataMapupdated.get("VAT"));
                ac.clickOnElement(yourData.elementClick);
            }
            ac.selectFromDropdown(yourData.sex, getValue("sex"));
            ac.inputTextinTextBox(yourData.firstName, userdataMapupdated.get("Firstname"));
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.lastName, userdataMapupdated.get("Lastname"));
            ac.clickOnElement(yourData.elementClick);
            if (!userdataMapupdated.get("Birthdate").isEmpty()) {
                String[] BirthDate = userdataMapupdated.get("Birthdate").split("-");
                Day = BirthDate[0].trim();
                Month = BirthDate[1].trim();
                Year = BirthDate[2].trim();
            }
            ac.inputTextinTextBox(yourData.birthDay, Day);
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.birthMonth, Month);
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.birthYear, Year);
            ac.clickOnElement(yourData.elementClick);
            if (userdataMapupdated.get("Phone").equals("")) {
                ac.inputTextinTextBox(yourData.telephone, userdataMapupdated.get(" "));
            }
            ac.inputTextinTextBox(yourData.telephone, userdataMapupdated.get("Phone"));
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.emailAddress, userdataMapupdated.get("Email"));
            ac.clickOnElement(yourData.elementClick);
            Thread.sleep(27000);
            if (!(driver.findElement(By.cssSelector("button.ui-state-default:nth-child(2)")).equals(null)) && driver.findElement(By.cssSelector("button.ui-state-default:nth-child(2)")).isDisplayed()) {
                driver.findElement(By.cssSelector("button.ui-state-default:nth-child(2)")).click();
                //ac.clickOnElement(ac.findonElementbyTxt(getValue(COUNTRY+"_btnjadoorgan")));
            }
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.repeatemailAddress, userdataMapupdated.get("Repeat email"));
            ac.clickOnElement(yourData.elementClick);
            ac.selectFromDropdown(yourData.country, getValue("land"));
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.postalCode, userdataMapupdated.get("Zip code"));
            ac.clickOnElement(yourData.elementClick);
            if (!(COUNTRY.equals("BE") || !(COUNTRY.equals("DE")))) {
                ac.inputTextinTextBox(yourData.initials, getValue("initials"));
                ac.clickOnElement(yourData.elementClick);
            }
            ac.inputTextinTextBox(yourData.houseNumber, userdataMapupdated.get("House number"));
            ac.inputTextinTextBox(yourData.address, userdataMapupdated.get("Address"));
            ac.clickOnElement(yourData.elementClick);
            ac.inputTextinTextBox(yourData.residence, userdataMapupdated.get("City"));
            ac.clickOnElement(yourData.elementClick);
            Thread.sleep(1000);
            //Load Error flag as Yes(Y) or No(N)
            ErrFlag = ErrorValidation(ErrFlag, CustType, "Your Data Page");
        }
        logger.info("YourDataPageFilled");
        return ErrFlag;
    }

    public String fillConfirmationPage(DataTable userdata, String CustType) throws InterruptedException {
        String ErrFlag = "N";
        if (COUNTRY.equals("BE")) {
            // Creating a map to update empty as null
            Map<String, String> userdataMapupdated = new HashMap<String, String>();
            //Iterating through head row and each consecutive row as hashmap
            for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
                for (String key : userdatamap.keySet()) {
                    //Creating a new hashmap with updated values (empty as nulls)
                    userdataMapupdated.put(key, userdatamap.get(key).trim().replace("empty", ""));
                }
                ac.clickOnElement(confirm.selectIban);
                ac.inputTextinTextBox(confirm.iban, userdatamap.get("IBAN"));
                ac.inputTextinTextBox(confirm.accountHolderName, userdatamap.get("Name"));
                ErrFlag = ErrorValidation(ErrFlag, CustType, "Confirmation Page");
            }
        } else {
            ac.clickOnElement(confirm.selectIban);
            ac.clickOnElement(confirm.ibanValidation);
            ac.selectFromDropdown(confirm.selectBank, "ABNANL2A");
            ac.clickOnElement(confirm.btnContinueAfterSelectingBank);
            ac.clickOnElement(confirm.btnSubmitSucessIbanTestValidation);
        }
        logger.info("Confirmation Page Filled");
        return ErrFlag;
    }

    public void pressNext() throws InterruptedException {
        ac.clickOnElement(productSelection.nextButton);
    }

    public HashMap loadErrorMessages(HashMap errorobjectsMap) throws InterruptedException {
        HashMap<String, String> errormessageMap = new HashMap<String, String>();
        for (Object key : errorobjectsMap.keySet()) {
            errormessageMap.put(key.toString(), getValue(key.toString()).replaceAll(" ", ""));
        }
        return errormessageMap;
    }

    public HashMap loadScreenErrorObjects(String PageType) throws InterruptedException {
        HashMap<String, String> errorobjectsMap = new HashMap<String, String>();
        if(PageType.equals("Product Selection Page")) {
            if (ac.checkElementisDisplayed(productSelection.licenseplateerror)) {
                errorobjectsMap.put("licenseplateInvalid", ac.retrieveTextFromElement(productSelection.licenseplateerror));
            }
            if (ac.checkElementisDisplayed(productSelection.mobilenumbererror)) {
                errorobjectsMap.put("mobilenumberProductPageInvalid", ac.retrieveTextFromElement(productSelection.mobilenumbererror));
            }
        }
        if(PageType.equals("Your Data Page")) {
            if (ac.checkElementisDisplayed(yourData.businessNameerror)) {
                errorobjectsMap.put("businessNameEmpty", ac.retrieveTextFromElement(yourData.businessNameerror));
            }
            if (ac.checkElementisDisplayed(yourData.kvkNummererror)) {
                errorobjectsMap.put("kvkNummerInvalid", ac.retrieveTextFromElement(yourData.kvkNummererror));
            }
            if (ac.checkElementisDisplayed(yourData.btwNummererror)) {
                errorobjectsMap.put("btwNummerInvalid", ac.retrieveTextFromElement(yourData.btwNummererror));
            }
            if (ac.checkElementisDisplayed(yourData.firstnameerror)) {
                errorobjectsMap.put("firstnameEmpty", ac.retrieveTextFromElement(yourData.firstnameerror));
            }
            if (ac.checkElementisDisplayed(yourData.lastnameerror)) {
                errorobjectsMap.put("lastnameEmpty", ac.retrieveTextFromElement(yourData.lastnameerror));
            }
            if (ac.checkElementisDisplayed(yourData.birthdateerror)) {
                errorobjectsMap.put("birthdateerror", ac.retrieveTextFromElement(yourData.birthdateerror));
                errorobjectsMap.put("agebelow16Error", ac.retrieveTextFromElement(yourData.birthdateerror));
            }
            if (ac.checkElementisDisplayed(yourData.mobilenumbererror)) {
                errorobjectsMap.put("mobilenumberDataPageEmpty", ac.retrieveTextFromElement(yourData.mobilenumbererror));
                errorobjectsMap.put("mobilenumberDataPageInvalid", ac.retrieveTextFromElement(yourData.mobilenumbererror));
            }
            if (ac.checkElementisDisplayed(yourData.emailerror)) {
                errorobjectsMap.put("emailEmpty", ac.retrieveTextFromElement(yourData.emailerror));
                errorobjectsMap.put("emailInvalid", ac.retrieveTextFromElement(yourData.emailerror));
            }
            if (ac.checkElementisDisplayed(yourData.repeatemailerror)) {
                errorobjectsMap.put("repeatEmailInvalid", ac.retrieveTextFromElement(yourData.repeatemailerror));
                errorobjectsMap.put("repeatEmailEmpty", ac.retrieveTextFromElement(yourData.repeatemailerror));
            }
            if (ac.checkElementisDisplayed(yourData.zipcodeerror)) {
                errorobjectsMap.put("zipcodeEmpty", ac.retrieveTextFromElement(yourData.zipcodeerror));
                errorobjectsMap.put("zipcodeInvalid", ac.retrieveTextFromElement(yourData.zipcodeerror));
            }
            if (ac.checkElementisDisplayed(yourData.houseNrerror)) {
                errorobjectsMap.put("houseNrEmpty", ac.retrieveTextFromElement(yourData.houseNrerror));
            }
            if (ac.checkElementisDisplayed(yourData.addresserror)) {
                errorobjectsMap.put("addressEmpty", ac.retrieveTextFromElement(yourData.addresserror));
            }
            if (ac.checkElementisDisplayed(yourData.cityerror)) {
                errorobjectsMap.put("cityEmpty", ac.retrieveTextFromElement(yourData.cityerror));
            }
        }
        if(PageType.equals("Confirmation Page")) {
            if (ac.checkElementisDisplayed(confirm.ibanerror)) {
                errorobjectsMap.put("ibanEmpty", ac.retrieveTextFromElement(confirm.ibanerror));
                errorobjectsMap.put("ibanInvalid", ac.retrieveTextFromElement(confirm.ibanerror));
            }
            if (ac.checkElementisDisplayed(confirm.accountholdernamerror)) {
                errorobjectsMap.put("accountholderEmpty", ac.retrieveTextFromElement(confirm.accountholdernamerror));
            }
        }
        return errorobjectsMap;
    }

    public String ErrorValidation(String ErrorFlag, String CustType, String StrArg1) throws InterruptedException {
        HashMap<String, String> errormessageMap = new HashMap<String, String>();
        HashMap<String, String> errorobjectsMap = new HashMap<String, String>();
        //Hashmap with on screen error messages
        errorobjectsMap = loadScreenErrorObjects(StrArg1);
        try{
            //Hashmap with pre-defined error messages
            errormessageMap = loadErrorMessages(errorobjectsMap);
            List<String> duplicateValues = new ArrayList<String>();
            //check if the xpath object is mapped to different error messages
            duplicateValues.add("mobilenumberDataPageEmpty");
            duplicateValues.add("mobilenumberDataPageInvalid");
            duplicateValues.add("emailEmpty");
            duplicateValues.add("emailInvalid");
            duplicateValues.add("birthdateerror");
            duplicateValues.add("agebelow16Error");
            duplicateValues.add("repeatEmailInvalid");
            duplicateValues.add("repeatEmailEmpty");
            duplicateValues.add("zipcodeEmpty");
            duplicateValues.add("zipcodeInvalid");
            duplicateValues.add("ibanEmpty");
            duplicateValues.add("ibanInvalid");
            int errorcount=0;
            //Sort the Hashmap, as one xpath object(errorObjectMap Value) can have more than one error messages mapped to it
            Map<String, String> treeMap = new TreeMap<String, String>(errorobjectsMap);
            for (Object key : treeMap.keySet()) {
                try {
                    // Check if an xpath object has atleast one matching pre defined error message
                    Assert.assertEquals(errorobjectsMap.get(key), errormessageMap.get(key));
                    errorcount=0; // a match is made
                    logger.info(errormessageMap.get(key));
                }
                catch (Error ex) {
                    errorcount++;
                    if(duplicateValues.contains(key)){
                        //check if all error messages mapped to this xpath object are assert failed
                        if(errorcount==2){
                            logger.info(errorobjectsMap.get(key) + " Error message does not match the database value");
                        }
                        //if there is another error message to be mapped with this xpath object, continue the loop
                        else {
                            continue;
                        }
                    }
                    //if there is no duplicate value to be checked for the xpath and assert failed
                    else {
                        logger.info(errorobjectsMap.get(key) + "Error message does not match the database value");
                        continue;
                    }
                }
                finally {
                    ErrorFlag = "Y"; // returning presence of error in page, to the calling method
                }
            }
        }
        catch (Exception ex){
            logger.info("Error Objects are not present on the page");
        }
        return ErrorFlag;
    }
    // Input Action code while Registering a customer in Product Selection page
    public void actioncode() throws InterruptedException {
        ac.clickOnElement(By.xpath("//*[@id='costs-overview']/div[2]/form/div/a"));
        ac.inputTextinTextBox(By.xpath("//*[@id='costs-overview']/div[2]/form/div/div/div[2]/div[1]/input"), "BRICKEN");
        ac.clickOnElement(By.xpath("//*[@id='applyActionCodeForm']/div/div/div[2]/div[2]/input"));
        Thread.sleep(1000);
    }

    // when email and phone number are different from default config file values
    public void validateSummaryPage(DataTable userdata) throws IOException, InterruptedException {
        //Iterating through first row and each consecutive row as hashmap
        for (Map<String, String> userdatamap : userdata.asMaps(String.class, String.class)) {
            Assert.assertEquals(ac.retrieveTextFromElement(summary.email()), userdatamap.get("Email"));
            Assert.assertEquals(ac.retrieveTextFromElement(summary.telephone()), userdatamap.get("Phone"));
        }
    }

    public void clickemailAlreadyExistPopUp() throws InterruptedException {
        try {

        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("no popup");

        }

    }

    public void mailbox(String username, String password) throws InterruptedException, IOException {

        ac.inputTextinTextBox(mailbox.mailboxUsername, username);
        ac.inputTextinTextBox(mailbox.mailboxPassword, password);
        ac.clickOnElement(mailbox.mailboxSignin);
        Thread.sleep(2000);
    }

    public void fillResetPasswordFields(String currentPassword, String newPassword) throws IOException, InterruptedException {
        ac.inputTextinTextBox(resetPassword.currentPassword, currentPassword);
        setNewPassword(newPassword);
    }

    public void setNewPassword(String password) throws IOException, InterruptedException {

        ac.inputTextinTextBox(resetPassword.newPassword, password);
        ac.inputTextinTextBox(resetPassword.confirmnewPassword, password);
        ac.clickOnElement(resetPassword.save);
        Thread.sleep(1000);
    }

    public void myyellowbrickLogIn(String username, String password) throws IOException, InterruptedException {

        if (COUNTRY.equals("NL")) {
            ac.inputTextinTextBox(myYellowBrickLogin.NLmyClientNumber, username);
            ac.inputTextinTextBox(myYellowBrickLogin.NLmypassword, password);
            ac.clickOnElement(myYellowBrickLogin.btnLogin);

        } else if (COUNTRY.equals("BE")) {
            ac.inputTextinTextBox(myYellowBrickLogin.BEmyClientNumber, username);
            ac.inputTextinTextBox(myYellowBrickLogin.BEmypassword, password);
            ac.clickOnElement(myYellowBrickLogin.btnLogin);
        } else if (COUNTRY.equals("DE")) {
            ac.inputTextinTextBox(myYellowBrickLogin.NLmyClientNumber, username);
            ac.inputTextinTextBox(myYellowBrickLogin.NLmypassword, password);
            ac.clickOnElement(myYellowBrickLogin.btnLogin);

        }
    }

    public void clickLinkParking() throws InterruptedException {
        ac.clickOnElement(myYellowBrick.parkingbuttonOnLandingPage);
        Thread.sleep(2000);
    }

    public void startInternetParking() throws IOException, InterruptedException {
        ac.selectFromDropdown(myYellowBrick.drpdwnVehicleToPark, 1);
        ac.inputTextinTextBox(myYellowBrick.numberPlate, getValue("licenseplate"));
        ac.inputTextinTextBox(myYellowBrick.zoneCode, getValue("zonecode"));
        ac.clickOnElement(myYellowBrick.start);
    }

    public void startInternetParking(Map<String, String> table) throws IOException, InterruptedException {

        ac.selectFromDropdown(myYellowBrick.drpdwnVehicleToPark, 11);
        ac.inputTextinTextBox(myYellowBrick.numberPlate, table.get("Licence plate"));
        ac.inputTextinTextBox(myYellowBrick.zoneCode, table.get("Zone code"));
        ac.inputTextinTextBox(myYellowBrick.remark, table.get("Remark"));
        ac.clickOnElement(myYellowBrick.start);
    }

    public void fillPersonalData(String param) throws Throwable, IOException, InterruptedException {
        if (getValue("business").equals("Y")) {
            ac.inputTextinTextBox(myYellowBrick.businessName, getValue("businessName" + param));
            ac.inputTextinTextBox(myYellowBrick.businessIds0, getValue("businessIds0.value" + param));
            ac.inputTextinTextBox(myYellowBrick.businessIds1, getValue("businessIds1.value" + param));
            if (getValue("havingBillingAddress1" + param).equals("true")) {
                ac.clickOnElement(myYellowBrick.havingBillingAddress1);
            } else {
                ac.clickOnElement(myYellowBrick.havingBillingAddress2);
                ac.inputTextinTextBox(myYellowBrick.billingAddressAddressLine1, getValue("billingAddressAddressLine1" + param));
                ac.inputTextinTextBox(myYellowBrick.billingAddressAddressLine2, getValue("billingAddressAddressLine2" + param));
                ac.inputTextinTextBox(myYellowBrick.billingAddressZipCode, getValue("billingAddressZipCode" + param));
                ac.inputTextinTextBox(myYellowBrick.billingAddressCity, getValue("billingAddressCity" + param));
                ac.selectFromDropdown(myYellowBrick.billingAddressCountryCode, getValue("billingAddressCountryCode" + param));
            }
        }
        ac.selectFromDropdown(myYellowBrick.gender, getValue("gender" + param));
        if(COUNTRY.equals("NL")) {
            ac.inputTextinTextBox(myYellowBrick.initials, getValue("initials" + param));
            ac.inputTextinTextBox(myYellowBrick.infix, getValue("infix" + param));
        }
        ac.inputTextinTextBox(myYellowBrick.firstName, getValue("firstName" + param));
        ac.inputTextinTextBox(myYellowBrick.lastName, getValue("lastName" + param));
        ac.inputTextinTextBox(myYellowBrick.dateOfBirth, getValue("dateOfBirth" + param));
        // Trick to make datetime picker focus out
        ac.clickOnElement(myYellowBrick.city);
        ac.inputTextinTextBox(myYellowBrick.email, getValue("email" + param));
        ac.inputTextinTextBox(myYellowBrick.phoneNr, getValue("phoneNr" + param));
        ac.selectFromDropdown(myYellowBrick.correspondenceLocale, getValue("correspondenceLocale" + param));
        ac.inputTextinTextBox(myYellowBrick.locationAddress1, getValue("locationAddress1" + param));
        ac.inputTextinTextBox(myYellowBrick.locationAddress2, getValue("locationAddress2" + param));
        ac.inputTextinTextBox(myYellowBrick.zipCode, getValue("zipCode" + param));
        ac.inputTextinTextBox(myYellowBrick.city, getValue("city" + param));
        ac.selectFromDropdown(myYellowBrick.countryDropdown, getValue("countryDropdown" + param));
        ac.clickOnElement(myYellowBrick.savePersonalDataButton);
        Thread.sleep(1000);
    }

    public void comparePersonalData() throws Throwable {
        Customer customer = db.getCustomerDetails(ac.retrieveTextFromElement(myYellowBrick.customerNumber));
        if (customer.getBusiness().equals("Y")) {
            Assert.assertEquals(getValue("businessName"), customer.getBusinessName());
            Assert.assertEquals(getValue("businessIds0.value"), customer.getBusinessIds0());
            Assert.assertEquals(getValue("businessIds1.value"), customer.getBusinessIds1());
            Assert.assertEquals(getValue("billingAddressAddressLine1"), customer.getBillingAddressAddressLine1());
            Assert.assertEquals(getValue("billingAddressAddressLine2"), customer.getBillingAddressAddressLine2());
            Assert.assertEquals(getValue("billingAddressZipCode"), customer.getBillingAddressZipCode());
            Assert.assertEquals(getValue("billingAddressCity"), customer.getBillingAddressCity());
            Assert.assertEquals(getValue("billingAddressCountryCode"), customer.getBillingAddressCountryCode());
        }
        Assert.assertEquals(getValue("gender"), customer.getGender());
        if(COUNTRY.equals("NL")) {
            Assert.assertEquals(getValue("initials"), customer.getInitials());
            Assert.assertEquals(getValue("infix"), customer.getInfix());
        }
        Assert.assertEquals(getValue("firstName"), customer.getFirstName());
        Assert.assertEquals(getValue("lastName"), customer.getLastName());
        Assert.assertEquals(getValue("dateOfBirth"), customer.getDateOfBirth());
        Assert.assertEquals(getValue("email"), customer.getEmail());
        Assert.assertEquals(getValue("phoneNr"), customer.getPhoneNr());
        Assert.assertEquals(getValue("correspondenceLocale"), customer.getCorrespondenceLocale());
        Assert.assertEquals(getValue("locationAddress1"), customer.getLocationAddress1());
        Assert.assertEquals(getValue("locationAddress2"), customer.getLocationAddress2());
        Assert.assertEquals(getValue("zipCode"), customer.getZipCode());
        Assert.assertEquals(getValue("city"), customer.getCity());
        Assert.assertEquals(getValue("countryDropdown"), customer.getCountryDropdown());
    }

    public void stopParkingFromMYB() throws IOException, InterruptedException {
        ac.clickOnElement(myYellowBrick.stop);
        ac.clickOnElement(myYellowBrick.OkStopParking);
    }

    public void processCreditCard() throws IOException, InterruptedException {
        ac.clickOnElement(confirm.selectCC);

        driver.switchTo().frame("iframe");

        ac.inputTextinTextBox(confirm.ccnumber, getValue("ccnumber"));
        ac.selectFromDropdown(confirm.ccexpiryEndMonth, getValue("ccexpmonth"));
        ac.selectFromDropdown(confirm.ccexpiryEndyYear, getValue("ccexpyear"));
        ac.inputTextinTextBox(confirm.ccSecurityCode, getValue("ccsecuritycode"));
        ac.clickOnElement(confirm.ccbtnContinue);
        Thread.sleep(1000);

        driver.switchTo().defaultContent();

    }


    public void stopParkingFromMunicipality(Map<String, String> stopParkingdetails) throws InterruptedException {
        String date;
        if (stopParkingdetails.get("Ending Time").equals("today") || stopParkingdetails.get("Ending Time").equals("tomorrow")) {
            date = ac.dateFormatter(stopParkingdetails.get("Ending Time"));
        } else
            date = stopParkingdetails.get("Ending Time");

        ac.inputTextinTextBox(municipality.municipalityEndingTime, date);
        ac.inputTextinTextBox(municipality.municipalityParkingCost, stopParkingdetails.get("Parking Cost"));
        ac.clickOnElement(municipality.stopParaking);
    }

    public boolean noParkingInMYB() {
        try {
            boolean flag = ac.elementExist(myYellowBrick.tblLicencePlate);
            return flag;
        } catch (Exception e) {
            return false;
        }

    }

    public void fillCustomerNumber(String customerNuber) throws Throwable {
        ac.inputTextinTextBox(passwordRecovery.customerNumberInput, customerNuber);
        ac.clickOnElement(passwordRecovery.submitButton);
    }


    public void selectDirectDebitPaymentMethod() throws InterruptedException {
        ac.clickOnElement(myYellowBrick.selectIban);
        Thread.sleep(1000);
    }

    public void selectCreditCardPaymentMethod() throws InterruptedException {
        ac.clickOnElement(myYellowBrick.selectCreditCard);
        Thread.sleep(1000);
    }

    public void enterDirectDebitDetails() throws IOException, InterruptedException {

        boolean permissionChecked = driver.findElement(myYellowBrick.permission)
                .getAttribute("checked") != null
                && driver.findElement(myYellowBrick.permission)
                .getAttribute("value").equals("true");

        if (!permissionChecked)
            ac.clickOnElement(myYellowBrick.permission);

        ac.selectFromDropdown(myYellowBrick.selectBankName,"ABNANL2A");
        ac.clickOnElement(myYellowBrick.btnChangeIbanButton);
        ac.clickOnElement(confirm.btnSubmitSucessIbanTestValidation);
       // ac.inputTextinTextBox(myYellowBrick.iban, getValue("iban"));
       // ac.inputTextinTextBox(myYellowBrick.holdername, getValue("accountholdername"));

        ac.clickOnElement(myYellowBrick.savePaymentButton);
        Thread.sleep(1000);
        ac.clickOnElement(myYellowBrick.confirmButton);
        Thread.sleep(3000);
    }

    public void enterCreditCardDetails() throws IOException, InterruptedException {
        driver.switchTo().frame("iframe");

        ac.inputTextinTextBox(confirm.ccnumber, getValue("ccnumber"));
        ac.selectFromDropdown(confirm.ccexpiryEndMonth, getValue("ccexpmonth"));
        ac.selectFromDropdown(confirm.ccexpiryEndyYear, getValue("ccexpyear"));
        ac.inputTextinTextBox(confirm.ccSecurityCode, getValue("ccsecuritycode"));
        ac.clickOnElement(confirm.ccbtnContinue);
        Thread.sleep(8000);

        driver.switchTo().defaultContent();
    }

    public void AddmobilePhone(String mobileNumber,String customerNr) throws  Throwable {
        ac.inputTextinTextBox(myYellowBrick.mobileNumber, mobileNumber);
        String transponderCard="";
        // Retrieving customer number if type is Private or business
        Customer customer=db.getCustomerDetails(customerNr);
        String type=customer.getBusiness();
        if(type.equals("Y")){
            transponderCard=getValue("transponder_business");
        }
        else
        {
            transponderCard=getValue("transponder_private");
        }
        ac.selectFromDropdown(myYellowBrick.transponderCardId, transponderCard);
        if (COUNTRY.equals("NL")) {
            ac.selectFromDropdown(myYellowBrick.sms, getValue("sms"));
            ac.selectFromDropdown(myYellowBrick.smsInterval, getValue("smsInterval"));
            ac.selectFromDropdown(myYellowBrick.smsBeforeEnd, getValue("smsBeforeEnd"));
            ac.selectFromDropdown(myYellowBrick.hasConfirmZone, getValue("hasConfirmZone"));
            // ac.selectFromDropdown(myYellowBrick.tCardSwitchable, getValue("tCardSwitchable"));
            ac.clickOnElement(myYellowBrick.smsAnnotations1);
        }
        if (COUNTRY.equals("BE")) {
            ac.selectTextFromDropdown(myYellowBrick.languageselection, "Engels");
        }
        ac.clickOnElement(myYellowBrick.mobileSubmitButton);
        if (ac.checkElementisDisplayed(myYellowBrick.errorblock)) {
            logger.info("Incorrect Telephone number");
        }
    }

    public void UpdatemobilePhone(String oldNumber,String newNumber) throws  Throwable {
        if (COUNTRY.equals("BE")) {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + oldNumber.replaceFirst("0", "+32") + "')]]//td[a[span]]/a/span"));
        }
        if (COUNTRY.equals("NL")) {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + oldNumber.replaceFirst("0", "+31") + "')]]//td[a[span]]/a/span"));
        }
        ac.inputTextinTextBox(myYellowBrick.mobileNumber, newNumber);
        ac.clickOnElement(myYellowBrick.mobileSubmitButton);
        if (ac.checkElementisDisplayed(myYellowBrick.errorblock)) {
            logger.info("Incorrect Telephone number");
        }
    }
    public void DeletemobilePhone(String mobileNumber) throws  Throwable {
        if (COUNTRY.equals("BE")) {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + mobileNumber.replaceFirst("0", "+32") + "')]]//td[button]/button"));
        }
        if (COUNTRY.equals("NL")) {
            ac.clickOnElement(By.xpath("//tr[td[contains(text(),'" + mobileNumber.replaceFirst("0", "+31") + "')]]//td[button]/button"));
        }
        ac.clickOnElement(myYellowBrick.confirmmobiledelete);
    }
    public void mobilePhoneCheck(String action,String mobileNumber) throws  Throwable {
        String transponderCard="";
        if (COUNTRY.equals("NL")) {
            mobileNumber = mobileNumber.replaceFirst("0", "+31");
        }
        if (COUNTRY.equals("BE")) {
            mobileNumber = mobileNumber.replaceFirst("0", "+32");
        }
        if (action.equals("Addition") || action.equals("Update")) {
            try {
                Mobile mobile = db.getMobileDetails(mobileNumber);
                Assert.assertEquals(mobileNumber, mobile.getMobileNr());
                if(mobile.getType().equals("Y")){
                    transponderCard=getValue("transponder_business");
                }
                else
                {
                    transponderCard=getValue("transponder_private");
                }
                Assert.assertEquals(transponderCard, mobile.getTransponderCardIdFk());
                if (COUNTRY.equals("NL")) {
                    Assert.assertEquals(getValue("smsDatabase"), mobile.getSms());
                    Assert.assertEquals(Float.parseFloat(getValue("smsInterval")), mobile.getSmsInterval());
                    Assert.assertEquals(Long.parseLong(getValue("smsBeforeEnd")), mobile.getSmsBeforeEnd());
                    Assert.assertEquals(Long.parseLong(getValue("hasConfirmZoneDatabase")), mobile.getConfirmZone());
                }
                logger.info("Mobile Number " + mobileNumber + " has been Updated");
            } catch (Error ex) {
                logger.info("Mobile number not updated due to errors in page");
            }
        } else {
            Mobile mobile = db.getMobileDetails(mobileNumber);
            Assert.assertEquals(null, mobile.getMobileNr());
            logger.info("Mobile Number " + mobileNumber + " is Deleted");
        }
    }

    //HashMap<Country, HashMap< providerCode, ArrayList<ProductGroup> > >
    public static HashMap<String, HashMap<String, ArrayList<ProductGroup>>> ProductGroupsByCountry;

    static {
        HashMap<String, HashMap<String, ArrayList<ProductGroup>>> tempMap = new HashMap<>();
        tempMap.put("NL", new HashMap<String, ArrayList<ProductGroup>>());
        tempMap.put("BE", new HashMap<String, ArrayList<ProductGroup>>());
        tempMap.put("DE", new HashMap<String, ArrayList<ProductGroup>>());

        //TODO
        //move data loading to corresponding property file later
        tempMap.get("NL").put("YB", new ArrayList<ProductGroup>());
        tempMap.get("NL").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("YB", "BASIC"));
        tempMap.get("NL").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("YB", "FLEX"));
        tempMap.get("NL").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("YB", "SUBC"));

        tempMap.get("NL").put("ANWB", new ArrayList<ProductGroup>());
        tempMap.get("NL").get("ANWB").add(ProductgroupFactory.getInstance().createProductgroup("ANWB", "BASC"));
        tempMap.get("NL").get("ANWB").add(ProductgroupFactory.getInstance().createProductgroup("ANWB", "SUBC"));

        tempMap.get("BE").put("YB", new ArrayList<ProductGroup>());
        tempMap.get("BE").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("BE", "BASC"));
        tempMap.get("BE").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("BE", "SUBC"));

        tempMap.get("DE").put("YB", new ArrayList<ProductGroup>());
        tempMap.get("DE").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("DE", "BASC"));
        tempMap.get("DE").get("YB").add(ProductgroupFactory.getInstance().createProductgroup("DE", "SUBC"));
        ProductGroupsByCountry = tempMap;
    }

    public static HashMap<String, String> productgroupToProviderMap;

    static {
        HashMap<String, String> temp = new HashMap<>();
        temp.put("1", "YB");
        temp.put("22", "YB");
        temp.put("23", "YB");
        temp.put("11", "YB");
        temp.put("10000", "YB");
        temp.put("4", "ANWB");
        temp.put("423", "ANWB");
        temp.put("10", "ICS");
        temp.put("1023", "ICS");
        temp.put("7", "FBTO");
        temp.put("723", "FBTO");
        temp.put("8", "SHELL");
        temp.put("20", "YBWINTER");
        temp.put("2023", "YBWINTER");
        temp.put("5", "HERTZ");
        temp.put("523", "HERTZ");
        temp.put("12", "RADIUZ");
        temp.put("19", "EXANWB");
        temp.put("1923", "EXANWB");

        productgroupToProviderMap = temp;
    }

    public static ProductGroup findProductgroup(ArrayList<ProductGroup> productgroupList, String payplan) {
        for (ProductGroup productGroup : productgroupList)
            if (productGroup.getPayplanType().equals(payplan)) {
                return productGroup;
            }
        return null;
    }
    public void mobilePhoneRemove() throws Throwable {
        ac.inputTextinTextBox(myYellowBrick.searchValue, getValue("mobileNumberDatabase"));
        ac.clickOnElement(myYellowBrick.searchbutton);
        ac.clickOnElement(myYellowBrick.deleteButton);
        ac.clickOnElement(myYellowBrick.confirmDeleteButton);
    }

    public void goToMyAccountFromMyYellowBrick(String custNumb) throws Exception {
        myyellowbrickLogIn(custNumb, getValue("myyellowbrickpassword"));
        ac.mouseOverElement(myYellowBrick.customerMainMenuButton);
        ac.clickOnElement(myYellowBrick.myAccount);
        Thread.sleep(2000);
    }

    public void ibanValidationInRegistration() throws InterruptedException{
        if(COUNTRY.equals("NL")) {
            ac.clickOnElement(confirm.selectIban);
            ac.selectFromDropdown(confirm.selectBank,"ABNANL2A");
        }
        else
        {
            ac.clickOnElement(confirm.selectIban);
            ac.inputTextinTextBox(confirm.iban, getValue("iban"));
            ac.inputTextinTextBox(confirm.bicCode, "ABNANL2A");
            ac.inputTextinTextBox(confirm.accountHolderName, getValue("accountholdername"));
        }
    }

    public void ibanFillWithNameForDebitCard() throws InterruptedException{
        ac.clickOnElement(confirm.selectIban);
        ac.inputTextinTextBox(confirm.iban, getValue("iban"));
        ac.inputTextinTextBox(confirm.bicCode, "ABNANL2A");
        ac.inputTextinTextBox(confirm.accountHolderName, getValue("accountholdername"));
    }

}