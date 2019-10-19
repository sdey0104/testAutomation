package dto;

import lombok.Data;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Data
public class RequestData {
    Logger logger = LoggerFactory.getLogger(RequestData.class);
    private String endpoint;
    private Map<String, String> params;
    private String serializedParams;
    private String requestType;

    public RequestData() {
        params = new HashMap<>();
    }

    public void addParam(String key, String value){
        if(params.containsKey(key)){
            logger.warn(String.format("Key already exists , existing key = %s , existing value = %s", key, value));
        }
        params.put(key, value);
    }

    public String getParam(String key){
        if(params.containsKey(key) == false){
            logger.warn(String.format("Param doesn't exist, provided key = %s", key));
            return null;
        }
        return params.get(key);
    }
}
