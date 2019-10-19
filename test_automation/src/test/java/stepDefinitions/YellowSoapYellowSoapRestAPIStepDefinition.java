package stepDefinitions;

import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import dto.*;
import enums.RestEndpoint;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.YellowSoapRestBase;
import utils.CommonUtil;
import utils.RestUtil;

@RunWith(Cucumber.class)
public class YellowSoapYellowSoapRestAPIStepDefinition extends YellowSoapRestBase {

    Logger logger = LoggerFactory.getLogger(YellowSoapYellowSoapRestAPIStepDefinition.class);

    public YellowSoapYellowSoapRestAPIStepDefinition(){

        super(CommonConfig.getInstance().getValue(CommonConstants.REST_API_CONFIG_FILE), CommonConfig.getInstance().getValue(CommonConstants.REST_API_ENDPOINT_FILE));
    }

    @Given("^Customer logs in through \"([^\"]*)\"$")
    public void customerLogin(String loginFrom) {
        logger.info(String.format("Customer is logging in from %s", loginFrom));
        LoginResponse response = performRequest(restProcessor.createRequestData(RestEndpoint.LOGIN), LoginResponse.class);
        setToken(response.getToken());
        logger.debug(response.toString());
    }

    @When("^Customer performs a \"([^\"]*)\" transaction$")
    public void customerPerformsATransaction(String transactionTypeString) {
        logger.info("Customer is performing a transaction of type " + transactionTypeString);
        TransactionResponse transactionResponse = performRequest(restProcessor.createRequestData(RestEndpoint.START_TRANSACTION), TransactionResponse.class);
        setTransactionId(transactionResponse.getId());
        logger.info(String.format("Transaction Started Successfully with id : %d", transactionResponse.getId()));
        logger.debug(transactionResponse.toString());
    }

    @Then("^Customer should have open transactions$")
    public void hasOpenTransactions() {
        logger.info("Customer has an open transaction");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.OPEN_TRANSACTION), String.class);
        Long id = RestUtil.findTransactionIdInJson(response);
        logger.debug(String.format("Found open transaction with id = %d", id));
        Long storedTransactionId = null;
        if (configs.containsKey("transaction_id")) {
            storedTransactionId = Long.valueOf(configs.get("transaction_id"));
        }
        if (storedTransactionId != null && id != null && id.equals(storedTransactionId)) {
            logger.info("Opened Transaction and stored transaction matched, transaction id = %d", id);
        } else {

            logger.error("Opened Transaction and stored transaction didn't match");
        }
    }


    @Then("^Customer can stop transaction$")
    public void canStopTransaction() {
        logger.info("Customer trying to stop a transaction");
        RequestData requestData = restProcessor.createRequestData(RestEndpoint.STOP_TRANSACTION);
        String storedTransactionId = null;
        Long id = null;
        if (configs.containsKey("transaction_id")) {
            storedTransactionId = configs.get("transaction_id");
            String endpoint = CommonUtil.interPolateVariable(requestData.getEndpoint(), "transaction_id", storedTransactionId);
            requestData.setEndpoint(endpoint);
            StopTransactionResponse response = performRequest(requestData, StopTransactionResponse.class);
            id = response.getId();
        }
        if (null != id) {
            logger.info(String.format("Customer successfully stopped the transaction having id = %d", id));
        }

    }

    @Then("^Customer can get transaction history$")
    public void getTransactionHistory() {
        logger.info("Customer is trying to get parking history");
        TransactionHistoryResponse response = performRequest(restProcessor.createRequestData(RestEndpoint.TRANSACTION_HISTORY), TransactionHistoryResponse.class);
        logger.info(String.format("Total number of transaction found = %d", response.getTransactionHistories().length));
    }

    @Then("^Customer can get nearby parking zones$")
    public void getNearbyParkingZones() {
        logger.info("Customer is trying to get nearby parking zones");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.NEARBY_PARKING_ZONES), String.class);
    }

    @Then("^Customer can get nearby parking advice$")
    public void getNearbyParkingAdvice() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.NEARBY_PARKING_ADVICE), String.class);
    }


    @Then("^Customer can get news$")
    public void getNews() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.NEWS), String.class);
    }


    @Then("^Customer can get user details$")
    public void getUserDetails() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.USER_DETAILS), String.class);
    }


    @Then("^Customer can get disclaimer$")
    public void getDisclaimer() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.DISCLAIMER), String.class);
    }


    @Then("^Customer can get localized message$")
    public void getLocalizedMessage() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.LOCALIZED_MESSAGES), String.class);
    }


    @Then("^Customer can update user information$")
    public void updateUserInformation() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.UPDATE_USER), String.class);
    }


    @Then("^Customer can reset password$")
    public void resetPassword() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.PASSWORD_REST), String.class);
    }

    @Then("^Customer can signup$")
    public void signup() {
        logger.info("Customer is trying to get nearby parking advice");
        String response = performRequest(restProcessor.createRequestData(RestEndpoint.SIGNUP_REQUEST), String.class);
    }



}
