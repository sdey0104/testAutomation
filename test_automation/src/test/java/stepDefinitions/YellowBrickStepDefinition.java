package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class YellowBrickStepDefinition {

      @Then("^Check displayed product group from customer under provider$")
    public void check_displayed_product_group_from_customer_under_provider() throws Throwable {
        throw new PendingException();
    }

    @And("^verify product groups in database for customer under provider$")
    public void verify_product_groups_in_database_for_customer_under_provider() throws Throwable {
        throw new PendingException();
    }

}