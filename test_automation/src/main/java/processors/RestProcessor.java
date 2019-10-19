package processors;

import com.google.common.collect.ImmutableMap;
import constants.CommonConstants;
import dto.RequestData;
import enums.RestEndpoint;
import enums.RestKeyParam;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
public class RestProcessor {
    Logger logger = LoggerFactory.getLogger(RestProcessor.class);
    private Map<String, String> endpoints;
    private Map<String, String> configs;
    private ImmutableMap<String, String> paramValueMap;
    public RestProcessor(Map<String, String> endpoints, Map<String, String> configs){
        this.endpoints = endpoints;
        this.configs = configs;
        populateParamValueMap();
    }

    public RequestData createRequestData(RestEndpoint endpoint){
        RequestData requestData = new RequestData();

        switch (endpoint){
            case LOGIN:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(RestEndpoint.LOGIN.toString()));
                addParam(requestData, RestKeyParam.CLIENT_ID);
                addParam(requestData, RestKeyParam.PASSWORD);
                addParam(requestData, RestKeyParam.USERNAME);
                break;
            case START_TRANSACTION:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(RestEndpoint.START_TRANSACTION.toString()));
                addParam(requestData, RestKeyParam.CARDCODE);
                addParam(requestData, RestKeyParam.COUNTRY_CODE);
                addParam(requestData, RestKeyParam.LICENSE_PLATE);
                addParam(requestData, RestKeyParam.ZONE_CODE);
                addParam(requestData, RestKeyParam.LATITUDE);
                addParam(requestData, RestKeyParam.LONGITUDE);
                break;

            case OPEN_TRANSACTION:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.OPEN_TRANSACTION.toString()));
                break;
            case STOP_TRANSACTION:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_PUT);
                requestData.setEndpoint(endpoints.get(RestEndpoint.STOP_TRANSACTION.toString()));
                break;
            case TRANSACTION_HISTORY:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.TRANSACTION_HISTORY.toString()));
                break;
            case NEARBY_PARKING_ZONES:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.NEARBY_PARKING_ZONES.toString()));
                break;
            case NEARBY_PARKING_ADVICE:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.NEARBY_PARKING_ADVICE.toString()));
                break;
            case NEWS:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.NEWS.toString()));
                break;
            case USER_DETAILS:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.USER_DETAILS.toString()));
                break;
            case DISCLAIMER:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.DISCLAIMER.toString()));
                break;
            case LOCATIONS:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.LOCATIONS.toString()));
                break;

            case LICENSE_PLATE_COUNTRIES:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.LICENSE_PLATE_COUNTRIES.toString()));
                break;
            case LOCALIZED_MESSAGES:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_GET);
                requestData.setEndpoint(endpoints.get(RestEndpoint.LOCALIZED_MESSAGES.toString()));
                break;
            case UPDATE_USER:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_PUT);
                requestData.setEndpoint(endpoints.get(RestEndpoint.UPDATE_USER.toString()));
                addParam(requestData, RestKeyParam.EMAIL);
                break;
            case PASSWORD_REST:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(RestEndpoint.PASSWORD_REST.toString()));
                break;
            case SIGNUP_REQUEST:
                requestData.setRequestType(CommonConstants.REST_REQUEST_TYPE_POST);
                requestData.setEndpoint(endpoints.get(RestEndpoint.SIGNUP_REQUEST.toString()));
                break;
            default:
                logger.warn("This code block shouldn't be executed");
                break;

        }


        return requestData;
    }

    private void addParam(RequestData requestData, RestKeyParam restKeyParam){
        String key = restKeyParam.toString();
        String configKey = key;
        if(paramValueMap.containsKey(key)){
            configKey = paramValueMap.get(key);
        }
        if(configs.containsKey(configKey) == false){
            logger.error(String.format("Config missing, key = %s", configKey));
            return;
        }
        String configValue = configs.get(configKey);
        requestData.addParam(restKeyParam.toString(), configValue);
    }
    private void populateParamValueMap(){
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
