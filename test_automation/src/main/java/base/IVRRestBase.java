package base;

import config.CommonConfig;
import constants.CommonConstants;
import dto.IVRCustomerdetails;
import dto.RequestData;
import enums.IVRRestEndPoint;
import enums.IVRRestKeyParam;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processors.IVRParamProcessor;
import utils.CommonUtil;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class IVRRestBase {

    protected String endpointFileSource;
    protected String configFileSource;
    protected Map<String, String> endpoints;
    protected Map<String, String> configs;

    protected IVRParamProcessor ivrParamProcessor;

    Logger logger = LoggerFactory.getLogger(IVRRestBase.class);

    public IVRRestBase(String configFileSource, String endpointFileSource) {
        this.endpointFileSource = endpointFileSource;
        this.configFileSource = configFileSource;

        endpoints = CommonUtil.loadPlainPropertiesFromResourceFile(endpointFileSource);
        configs = CommonUtil.loadPlainPropertiesFromResourceFile(configFileSource);

        CommonUtil.interPolateVariables(endpoints, configs);
        ivrParamProcessor = new IVRParamProcessor(endpoints, configs);
    }

    protected Response performRequest(IVRRestEndPoint restEndpoint, IVRCustomerdetails ivrCustomerdetails) {
        RequestData requestData ;
        if(ivrCustomerdetails.getRedirectBaseUrl()== null) {
            requestData = ivrParamProcessor.createRequestData(restEndpoint,ivrCustomerdetails);
            RestAssured.baseURI = requestData.getEndpoint();
        }
        else {
            endpoints = CommonUtil.loadPlainPropertiesFromResourceFile(endpointFileSource);
            configs = CommonUtil.loadPlainPropertiesFromResourceFile(configFileSource);
            configs.put("hostname",ivrCustomerdetails.getRedirectBaseUrl());
            System.out.println(configs.values());
            CommonUtil.interPolateVariables(endpoints, configs);
            ivrParamProcessor = new IVRParamProcessor(endpoints, configs);
            requestData = ivrParamProcessor.createRequestData(restEndpoint,ivrCustomerdetails);
            RestAssured.baseURI = requestData.getEndpoint();
        }
        RequestSpecification specification = given()
                .header("X-mocked-Signature", "dfrty68se5ggjkdt84krj45r90ahrAreh")
                .params(requestData.getParams()).when();
        logger.info(specification.toString());

        Response response = null;
        if (requestData.getRequestType().equalsIgnoreCase(CommonConstants.REST_REQUEST_TYPE_POST)) {
            response = specification.post();
        } else if (requestData.getRequestType().equalsIgnoreCase(CommonConstants.REST_REQUEST_TYPE_GET)) {
            response = specification.get();
        }
        if (requestData.getRequestType().equalsIgnoreCase(CommonConstants.REST_REQUEST_TYPE_PUT)) {
            response = specification.put();
        }
        return response;
    }

    public String getResponseValue(IVRRestKeyParam ivrRestKeyParam){

        String path = CommonConfig.getInstance().getValue(CommonConstants.IVR_REST_API_CONFIG_FILE);
        Map<String, String> getkeyValue = CommonUtil.loadPlainPropertiesFromResourceFile(path);
        String key = ivrRestKeyParam.toString();
        String responseValue = getkeyValue.get(key);
        return responseValue;
    }
}
