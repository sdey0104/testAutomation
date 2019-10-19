package utils;


import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * generate 10 digit number for BTW and KVK number
     *
     * @return
     */
    public static long generateNumber() {
        long number = (long) (Math.random() * 100000 + 1330000000L);
        return number;
    }

    public static void interPolateVariables(Map<String, String> targetMap, Map<String, String> configMap){
        for(String key : targetMap.keySet()) {
            String value = targetMap.get(key);
            StrSubstitutor substitutor = new StrSubstitutor(configMap);
            String newValue = substitutor.replace(value);
            targetMap.put(key, newValue);
        }
    }

    public static String interPolateVariable(String originalString, String key, String value){
        Map<String, String> configMap = new HashMap<>();
        configMap.put(key, value);
        StrSubstitutor substitutor = new StrSubstitutor(configMap);
        String replacedString = substitutor.replace(originalString);
        return replacedString;
    }


    public static Map<String, String> loadPlainPropertiesFromResourceFile(String fileName){
        Properties properties = new Properties();
        Map<String, String> propertyMap = new HashMap<>();
        InputStream inputStream = null;
        try{
            inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(inputStream);
            for(String key : properties.stringPropertyNames()){
                propertyMap.put(key, properties.getProperty(key));
            }
        }catch (Exception e){
            logger.error(String.format("Unknown exception occured, msg : %s", e.getMessage()));
        }finally {
           if(null != inputStream){
               try {
                   inputStream.close();
               }catch (Exception e){
                   logger.error(String.format("Exception occured while closing the input stream, msg : %s", e.getMessage()));
               }
           }
        }
        return propertyMap;
    }




}
