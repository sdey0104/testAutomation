package config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class - responsible to hold the common configuration across the application.
 * Based on the environment passed as -D options it will provide the corresponding configs
 * You can call it from anywhere in the application.
 */

public class CommonConfig {
    private Logger logger = LoggerFactory.getLogger(CommonConfig.class);
    private static final String common_config_file = "common_config.properties";
    private Map<String, String> configs = null;
    @Getter
    @Setter
    private String environment = "LOCAL";
    @Getter
    private String productGroup = "1";
    @Getter
    @Setter
    private String country = "NL";
    @Getter
    @Setter
    private String configPrefix = "";
    @Getter
    @Setter
    private String browser = "Chrome";

    public String getProductGroup() {
        return productGroup;
    }

    private static class CommonConfigHolder {
        private static CommonConfig _instance = new CommonConfig();
    }

    private CommonConfig() {
        initialize();
    }

    private void initialize() {
        configs = new HashMap<>();

        if (System.getProperty("environment") != null) {
            environment = System.getProperty("environment");
        }
        if (System.getProperty("country") != null) {
            country = System.getProperty("country");
        }
        if (System.getProperty("productgroup") != null) {
            productGroup = System.getProperty("productgroup");
        }
        if (System.getProperty("browser") != null) {
            browser = System.getProperty("browser");
        }

        if (System.getProperty("productgroup") != null) {
            productGroup = System.getProperty("productgroup");
        }

        configPrefix = environment + "_" + country + "_";
        if (environment.equalsIgnoreCase("LOCAL")) {
            configPrefix = environment + "_";
        }

        configs = CommonUtil.loadPlainPropertiesFromResourceFile(common_config_file);
        logger.info(String.format("The application is running in %s environment in %s country, the config prefix is %s using %s", environment, country, configPrefix, browser));
        System.out.println(String.format("The application is running in %s environment in %s country, the config prefix is %s using %s", environment, country, configPrefix, browser));

    }

    public static CommonConfig getInstance() {
        return CommonConfigHolder._instance;
    }

    /**
     * Similar as getValue(Key), this overloaded function provides getting default value.
     *
     * @param key          : the property name, if it doesn't match exactly with one of the keys, it will return with the matching environment.
     * @param defaultValue : the value returned once the config key isn't found.
     * @return value : if no value found or no defaultValue provided, a null is returned.
     */
    public String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Once initialized, it provides api to get the config values loaded at the beginning when the application runs.
     *
     * @param key : the property name, if it doesn't match exactly with one of the keys, it will return with the matching environment.
     * @return value : if no value found or no defaultValue provided, a null is returned.
     */
    public String getValue(String key) {
        if (configs.containsKey(key)) {
            return configs.get(key);
        }

        String newKey = configPrefix + key;
        if (configs.containsKey(newKey)) {
            return configs.get(newKey);
        }
        return null;
    }
}
