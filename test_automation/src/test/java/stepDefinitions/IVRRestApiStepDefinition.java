package stepDefinitions;

import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import dto.IVRCustomerdetails;
import enums.IVRRestEndPoint;
import enums.IVRRestKeyParam;
import frameworkComponents.DatabaseConnections;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import base.IVRRestBase;
import utils.IVRUtil;

import java.io.IOException;
import java.sql.SQLException;

@RunWith(Cucumber.class)
public class IVRRestApiStepDefinition extends IVRRestBase {
    IVRUtil ivrComponent = new IVRUtil();
    Logger logger = LoggerFactory.getLogger(IVRRestApiStepDefinition.class);
    DatabaseConnections databaseConnections = new DatabaseConnections();
    IVRCustomerdetails ivrCustomerdetails = new IVRCustomerdetails();


    public IVRRestApiStepDefinition() {
        super(CommonConfig.getInstance().getValue(CommonConstants.IVR_REST_API_CONFIG_FILE), CommonConfig.getInstance().getValue(CommonConstants.IVR_REST_API_ENDPOINT_FILE));
    }

    @Given("^an active IVRCustomer with unique phone number$")
    public void an_active_ivrcustomer_with_unique_phone_number() throws SQLException, IOException , InterruptedException{

       // Thread.sleep(10000);
        ivrCustomerdetails = databaseConnections.getCustomerDetailsForIVR();
        //databaseConnections.getZoneCode();
        ivrCustomerdetails.setZoneCode("1");
        if (ivrCustomerdetails.getMobileNr().contains("+")) {
            logger.info("The mobile number to work further for the test is " + ivrCustomerdetails.getMobileNr());
        }
        ivrCustomerdetails.setCallUUID(IVRUtil.generateCallUUID());
        ivrCustomerdetails.setFrom(IVRUtil.queryParameterFrom(ivrCustomerdetails.getMobileNr()));
    }

    @When("^IVRCustomer calls IVR to start transaction$")
    public void ivrcustomer_calls_ivr_to_start_transaction() throws Throwable {
        String strResponse = "";
        Response response = performRequest(IVRRestEndPoint.IVR_RECEIVE, ivrCustomerdetails);
        response.then().assertThat().statusCode(200).and().contentType(ContentType.XML);
        strResponse = response.asString();
        logger.info(strResponse);
        String responseValue = getResponseValue(IVRRestKeyParam.IVRRESPONSERECIEVE);
        Assert.assertEquals(strResponse.contains(responseValue), true);
        XmlPath xmlPath = new XmlPath(strResponse);
        String redirectBaseUrl = xmlPath.get("Response.Redirect");
        String[] redirectBaseUrlstr = redirectBaseUrl.split("/after");
        ivrCustomerdetails.setRedirectBaseUrl(redirectBaseUrlstr[0]);
        /**
         * after welcome call
         */
        response = performRequest(IVRRestEndPoint.IVR_AFTERWELCOME, ivrCustomerdetails);
        response.then().assertThat().statusCode(200).and().contentType(ContentType.XML);
        strResponse = response.asString();
        logger.info(strResponse);
        responseValue = getResponseValue(IVRRestKeyParam.IVRAFTERWELCOME);
        Assert.assertEquals(strResponse.contains(responseValue), true);
    }

    @Then("^IVRCustomer confirms start in IVR$")
    public void ivrcustomer_confirms_start_in_ivr() throws Throwable {

        /**
         * after start transaction By IVR
         */
        String strResponse = "";
        Response response = performRequest(IVRRestEndPoint.IVR_CHOOSE1TOCONTINUE0TOGOBACK, ivrCustomerdetails);
        response.then().assertThat().statusCode(200).and().contentType(ContentType.XML);
        strResponse = response.asString();
        logger.info(strResponse);
        String responseValue = getResponseValue(IVRRestKeyParam.IVRCHOOSE1TOCONTINUE0TOGOBACK);
        Assert.assertEquals(strResponse.contains(responseValue), true);
        throw new PendingException();
    }

    @Then("^IVRCustomer confirms stop in IVR$")
    public void ivrcustomer_confirms_stop_in_ivr() throws Throwable {
        ivrcustomer_calls_ivr_to_start_transaction();
    }

    @And("^IVRCustomer chooses an active zone to park and starts parking$")
    public void ivrcustomer_chooses_an_active_zone_to_park_and_start_parking() throws Throwable {

        /**
         * get zone code call
         */
        String strResponse = "";
        Response response = performRequest(IVRRestEndPoint.IVR_GETZONECODE, ivrCustomerdetails);
        response.then().assertThat().contentType(ContentType.XML);
        strResponse = response.asString();
        String responseValue = getResponseValue(IVRRestKeyParam.IVRGETZONECODE);
        Assert.assertEquals(strResponse.contains(responseValue), true);

    }

    @And("^IVRCustomer starts parking in IVR with details below$")
    public void ivrcustomer_starts_parking_in_ivr_with_details_below() throws Throwable {
        throw new PendingException();
    }

    @And("^IVRCustomer calls IVR to stop transaction$")
    public void ivrcustomer_calls_ivr_to_stop_transaction() throws Throwable {
        /**
         * stop transaction call
         */
        String strResponse = "";
        Response response = performRequest(IVRRestEndPoint.IVR_CHOOSE1TOSTOPTRANSACTION, ivrCustomerdetails);
        response.then().assertThat().statusCode(200).and().contentType(ContentType.XML);
        strResponse = response.asString();
        logger.info(strResponse);
        String responseValue = getResponseValue(IVRRestKeyParam.IVRCHOOSE1TOSTOPTRANSACTION);
        Assert.assertEquals(strResponse.contains(responseValue), true);
    }
}
