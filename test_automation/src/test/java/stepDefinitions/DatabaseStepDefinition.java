package stepDefinitions;

import base.WebBase;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import dto.Customer;
import dto.ProductGroup;
import frameworkComponents.AutomationComponent;
import frameworkComponents.BusinessComponent;
import frameworkComponents.DatabaseConnections;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.testng.Assert;
import pageobjects.myyellowbrick.MyYellowBrick;

import java.io.IOException;
import java.util.ArrayList;

@RunWith(Cucumber.class)
public class DatabaseStepDefinition extends WebBase {

    private static Logger logger = LogManager.getLogger(WebBase.class.getName());

    ArrayList<ProductGroup> productGroupList;
    ArrayList<String> customerNumberList;
    ArrayList<String> switchingOptions;
    String payplan;
    MyYellowBrick myYellowBrick = new MyYellowBrick();
    BusinessComponent bc = new BusinessComponent();
    DatabaseConnections databaseConnection = new DatabaseConnections();
    AutomationComponent ac = new AutomationComponent();

    @Then("^System should have assigned the right productGroup to the customer$")
    public void system_shoud_have_assigned_right_productGroup_id() throws Throwable {
        Customer customer = databaseConnection.getCustomer(customerNumber);

        Assert.assertEquals(PRODUCTGROUP, customer.getProductGroupId());
    }

    @And("Validate number of Qcards from database is \"(.*)\"")
    public void Validate_number_of_Qcards_from_database(String strArg1) throws Throwable {
        Customer customer = databaseConnection.getCustomer(customerNumber);

        Assert.assertEquals(strArg1, customer.getPPluscount());
    }

    @Given("^Loaded product group data from database$")

    public void load_productgroup_data_from_database() throws Throwable {
        productGroupList = databaseConnection.getProductgroupList();
    }

    @Given("^(.*) exists in database$")
    public void check_productgroup_exists_in_database(String productgroupId) {
        for (ProductGroup productGroup : productGroupList) {
            if (productGroup.getProductGroupId().equals(productgroupId)) {
                customerNumberList = productGroup.getCutomerNumberList();
                switchingOptions = productGroup.getSwitchingOptions();
                payplan = productGroup.getPayplanType();
                return;
            }
        }
        throw new AssumptionViolatedException(productgroupId + " Doesn't Exist in the database!");
    }

    @Given("^Login to myYellobrick with all retrieved_customer_number to validate productgroups$")
    public void login_for_all_customer_and_check_productgroup() throws IOException, InterruptedException, Exception {
        for (String customerNumber : customerNumberList) {
            try {
                bc.goToMyAccountFromMyYellowBrick(customerNumber);
            } catch (Exception ex) {
                continue;
            }
            logger.log(Level.INFO, "Verifying productgroup information for customer number: " + customerNumber);
            logger.log(Level.INFO, "Verifying MyPlan...");
            Assert.assertEquals( verifyMyYellowbrickUI(myYellowBrick.myPlanBasePath, payplan), true );

            logger.log(Level.INFO, "Verifying Switching options...");
            int matchedOptions = 0;
            for (String switchingOption : switchingOptions) {
                matchedOptions |= checkSwitchingOption(switchingOption);
            }
            Assert.assertEquals(Integer.bitCount(matchedOptions) == switchingOptions.size(), true );
            logger.info("Productgroup","Tested customer: " + customerNumber);
        }
    }

    /**
     * returns matched ui row number
     * @param payplanType
     */
    private int checkSwitchingOption(String payplanType){
        return verifyMyYellowbrickUI(myYellowBrick.changePlan1, payplanType) ? 1 :
                verifyMyYellowbrickUI(myYellowBrick.changePlan2, payplanType) ? (1<<1) : 0;
    }

    private boolean verifyMyYellowbrickUI(String path, String payplanType){
        try {
            for (ProductGroup productGroup : productGroupList)
                if (productGroup.getPayplanType().equals(payplanType)) {
                    return productGroup.isEquivalentTo(path);
                }
        } catch (Exception e) {
            //
        }
        return false;
    }
}
