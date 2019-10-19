package processors;

import com.google.common.collect.ImmutableMap;
import constants.CommonConstants;
import dto.IVRCustomerdetails;
import dto.RequestData;
import enums.IVRRestEndPoint;
import enums.RestKeyParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class IVRParamProcessor {

    Logger logger = LoggerFactory.getLogger(RestProcessor.class);
    private Map<String, String> endpoints;
    private Map<String, String> configs;
    private ImmutableMap<String, String> paramValueMap;
    IVRCustomerdetails ivrCustomerdetails = new IVRCustomerdetails();


    public IVRParamProcessor(Map<String, String> endpoints, Map<String, String> configs) {
        this.endpoints = endpoints;
        this.configs = configs;
        populateParamValueMap();
    }

    //String CallUUID = IVRUtil.generateCallUUID();
    //String From = IVRUtil.queryParameterFrom(getMobileNr());

    public RequestData createRequestData(IVRRestEndPoint endpoint,IVRCustomerdetails ivrCustomerdetails) {
        RequestData requestData = new RequestData();
        switch (endpoint) {
            case IVR_RECEIVE:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(IVRRestEndPoint.IVR_RECEIVE.toString()));
                addParam(requestData, "From", ivrCustomerdetails.getMobileNr());
                addParam(requestData, "CallUUID", ivrCustomerdetails.getCallUUID());
                break;
            case IVR_AFTERWELCOME:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(IVRRestEndPoint.IVR_AFTERWELCOME.toString()));
                addParam(requestData, "From", ivrCustomerdetails.getMobileNr());
                addParam(requestData, "CallUUID", ivrCustomerdetails.getCallUUID());
                logger.info(ivrCustomerdetails.getFrom()+"  "+ ivrCustomerdetails.getCallUUID()+" "+requestData);
                break;
            case IVR_GETZONECODE:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(IVRRestEndPoint.IVR_GETZONECODE.toString()));
                addParam(requestData, "From", ivrCustomerdetails.getMobileNr());
                addParam(requestData, "CallUUID", ivrCustomerdetails.getCallUUID());
                addParam(requestData, "Digits", ivrCustomerdetails.getZoneCode());
                break;
            case IVR_CHOOSE1TOSTOPTRANSACTION:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(IVRRestEndPoint.IVR_CHOOSE1TOSTOPTRANSACTION.toString()));
                addParam(requestData, "From", ivrCustomerdetails.getMobileNr());
                addParam(requestData, "CallUUID", ivrCustomerdetails.getCallUUID());
                addParam(requestData, "Digits", ivrCustomerdetails.getZoneCode());
                break;
            default:
                logger.warn("This code block shouldn't be executed");
                break;

        }


        return requestData;
    }

    private void addParam(RequestData requestData, String key, String value) {
        requestData.addParam(key, value);
    }

    private void addParam(RequestData requestData, RestKeyParam restKeyParam) {
        String key = restKeyParam.toString();
        String configKey = key;
        if (paramValueMap.containsKey(key)) {
            configKey = paramValueMap.get(key);
        }
        if (configs.containsKey(configKey) == false) {
            logger.error(String.format("Config missing, key = %s", configKey));
            return;
        }
        String configValue = configs.get(configKey);
        requestData.addParam(restKeyParam.toString(), configValue);
    }

    private void populateParamValueMap() {
        paramValueMap = ImmutableMap.<String, String>builder()
                .put("clientId", "client_id")
                .put("password", "password")
                .put("username", "username")
                .put("cardCode", "card_code")
                .put("countryCode", "country_code")
                .put("licensePlate", "license_plate")
                .put("zoneCode", "zone_code")
                .put("latitude", "latitude")
                .put("longitude", "longitude")
                .put("transactionId", "transaction_id")
                .put("zoneCodeId", "zone_code_id")
                .put("maxResults", "max_result")
                .put("email", "new_email_address")
                .put("appVersion", "app_version")
                .put("messageVersion", "message_version")
                .build();
    }
}
