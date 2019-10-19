package stepDefinitions;

import config.CommonConfig;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.WebBase;

public class Hooks extends WebBase {

    private Logger logger = LoggerFactory.getLogger(Hooks.class);

    public Hooks() {
        ENVIRONMENT = CommonConfig.getInstance().getEnvironment();
        PRODUCTGROUP = CommonConfig.getInstance().getProductGroup();
        COUNTRY = CommonConfig.getInstance().getCountry();
        BROWSER = CommonConfig.getInstance().getBrowser();
        TAG=System.getProperty("automation-type");

    }

    @Before("@regression")
    public void beforeValidation() {
        logger.info("Before Validation");

    }
    @After("@regression")
    public void afterValidation() {
        logger.info("After Validation");
        if (null != driver) {
            driver.quit();
        }
    }
}