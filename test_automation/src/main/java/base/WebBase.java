package base;

import config.CommonConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebBase {

    public static WebDriver driver;
    public static WebDriver screenshotdriver;
    public static String ENVIRONMENT;
    public static String COUNTRY;
    public static String PRODUCTGROUP;
    public static String BROWSER;
    public static String TAG;
    public static String os;
    public final static String WINDOWS = "Windows";
    public final static String MAC = "Mac";
    private static final String TEST_DATA_FILE_KEY = "testdata";
    public static String customerNumber;

    private String configPrefix = "";
    static Map<String, String> propertiesMap;

    Logger logger = LoggerFactory.getLogger(WebBase.class);


    public WebBase(String configFileSource) {
            propertiesMap = CommonUtil.loadPlainPropertiesFromResourceFile(configFileSource);
            configPrefix = CommonConfig.getInstance().getConfigPrefix();
    }

    public WebBase() {
        configPrefix = CommonConfig.getInstance().getConfigPrefix();
    }
    /**
     * Initializing Driver
     *
     * @return
     * @throws IOException
     */

    @SuppressWarnings("deprecation")
    public WebDriver initializeDriver(String browser) throws IOException {

        String browserName = browser;
        if (browserName.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", getValue("chromedriver" + this.detectOS()));
            driver = new ChromeDriver();
        }

        if (browserName.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", getValue("firefoxdriver" + this.detectOS()));
            driver = new FirefoxDriver();
        }

        if (browserName.equals("IE")) {

            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability("requireWindowFocus", true);
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
            //capabilities.setCapability("ie.ensureCleanSession", true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
            File file = new File(getValue("internetedgeexplorer"));
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            driver = new InternetExplorerDriver(capabilities);
        }

        if (browserName.equals("Safari")) {

            driver = new SafariDriver();
            driver.get("google.com");
        }

        driver.manage().window().setSize(getMaxWindowSize());
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        screenshotdriver = driver;
        return driver;
    }

    /**
     * Calculate the maximum size for the window
     *
     * @return
     */
    private Dimension getMaxWindowSize() {
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        Dimension windowSize = new Dimension(width, height);
        return windowSize;
    }


    /**
     * Similar as getValue(Key), this overloaded function provides getting default value.
     * @param key : the property name, if it doesn't match exactly with one of the keys, it will return with the matching environment.
     * @param defaultValue : the value returned once the config key isn't found.
     * @return value : if no value found or no defaultValue provided, a null is returned.
     */
    public String getValue(String key, String defaultValue){
        String value = getValue(key);
        if(null == value){
            value = defaultValue;
        }
        return value;
    }

    /**
     * Once initialized, it provides api to get the config values loaded at the beginning when the application runs.
     * @param key : the property name, if it doesn't match exactly with one of the keys, it will return with the matching environment.
     * @return value : if no value found or no defaultValue provided, a null is returned.
     */
    public String getValue(String key){
        if(propertiesMap.containsKey(key)){
            return propertiesMap.get(key);
        }
        String newKey = configPrefix + key;
        if(propertiesMap.containsKey(newKey)){
            return propertiesMap.get(newKey);
        }
        logger.error(String.format("Couldn't find the value associated with the provided key, provided key = %s", key));
        return null;
    }

    /**
     * Screenshot functionality
     *
     * @param name
     * @throws IOException
     */
    public void getScreenshot(String name) throws IOException {

        screenshotdriver.manage().window().maximize();
        File src = ((TakesScreenshot) screenshotdriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(getValue("screenshots") + name + "screenshot.png"));

    }

    /**
     * Detecting current operating system
     *
     * @return
     */
    public String detectOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            os = WINDOWS;
        } else if (osName.contains("mac")) {
            os = MAC;
        } else {
            logger.error("Error! Unknown operating system is detected.");
        }
        return os;
    }
}
