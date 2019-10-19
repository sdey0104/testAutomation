package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import constants.CommonConstants;
import dto.RequestData;
import dto.TransactionHistoryResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processors.RestProcessor;
import utils.CommonUtil;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;

public class YellowSoapRestBase {
    protected String endpointFileSource;
    protected String configFileSource;
    protected Map<String, String> endpoints;
    protected Map<String, String> configs;

    protected RestProcessor restProcessor;
    private String token = null;
    Logger logger = LoggerFactory.getLogger(YellowSoapRestBase.class);

    public YellowSoapRestBase(String configFileSource, String endpointFileSource){
        this.endpointFileSource = endpointFileSource;
        this.configFileSource = configFileSource;

        endpoints = CommonUtil.loadPlainPropertiesFromResourceFile(endpointFileSource);
        configs = CommonUtil.loadPlainPropertiesFromResourceFile(configFileSource);
        CommonUtil.interPolateVariables(endpoints, configs);

        if(configs.containsKey(CommonConstants.REST_API_CONFIG_KEY_TOKEN)){
            token = configs.get(CommonConstants.REST_API_CONFIG_KEY_TOKEN);
        }

        restProcessor = new RestProcessor(endpoints, configs);
    }

    protected void setToken(String token){
        this.token = token;
    }

    protected void setTransactionId(Long transactionId){
        String transactionIdString = String.valueOf(transactionId);
        configs.put("transactionId", transactionIdString);
        configs.put("transaction_id", transactionIdString);
    }

    private String performActualRequest(WebResource.Builder builder, RequestData requestData){
        String responseString = null;
        String valueString = JSON.serialize(requestData.getParams());
        switch (requestData.getRequestType()) {
            case CommonConstants.REST_REQUEST_TYPE_GET:
                responseString = builder.get(String.class);
                break;
            case CommonConstants.REST_REQUEST_TYPE_POST:
                responseString = builder.post(String.class, valueString);
                break;
            case CommonConstants.REST_REQUEST_TYPE_PUT:
                responseString = builder.put(String.class, valueString);
                break;
            default:
                logger.error("This shouldn't exist");
                break;
        }
        return responseString;
    }

    protected <T> T performRequest(RequestData requestData, Class<T> classType){
        WebResource resource = Client.create(new DefaultClientConfig()).resource(requestData.getEndpoint());
        WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
        builder.type(MediaType.APPLICATION_JSON);
        if(null != token){
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
        try {
            String responseString = performActualRequest(builder, requestData);
            return mapObjectFromResponseString(responseString, classType);
        }catch (Exception e){
            logger.error("Something went wrong while performing the Rest Request");
        }
        return null;
    }

    private <T> T mapObjectFromResponseString(String responseString, Class<T> classType){
        /*
         * Extra check due to not ideal json response
         */
        ObjectMapper objectMapper = new ObjectMapper();
        if(classType.equals(String.class)){
            return (T) responseString;
        }
        T mappedObject = null;
        try {
            if(classType.equals(TransactionHistoryResponse.class)){
                TransactionHistoryResponse.TransactionHistory[] histories = objectMapper.readValue(responseString, TransactionHistoryResponse.TransactionHistory[].class);
                TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
                transactionHistoryResponse.setTransactionHistories(histories);
                mappedObject = (T) transactionHistoryResponse;
            }else{
                mappedObject = objectMapper.readValue(responseString, classType);
            }
        }catch (Exception e){
            logger.error(String.format("Couldn't parse into object, object type : %s", classType));
        }
        return mappedObject;
    }

    private void addDefaultHeaders(HttpGet httpGet){
        httpGet.addHeader("content-type", "application/json");
        if(null != token) {
            httpGet.addHeader("Authorization", String.format("Bearer %s", token));
        }
    }







}
