package frameworkComponents;

import base.WebBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AutomationComponent extends WebBase {

    Logger logger = LoggerFactory.getLogger(AutomationComponent.class);
    public void clickOnElement(By elementId) throws InterruptedException {
        try {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(elementId));
        scrollElement(element);
        new Actions(driver).moveToElement(element).perform();
        element.click();
        }
        catch(Exception e){
            logger.info("Error" + e);
        }
        finally {
            logger.info("moving forward with the test");
        }
    }

    public void inputTextinTextBox(By elementId, String text) throws InterruptedException {
        try {
            WebElement element = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(elementId));
            scrollElement(element);
            //new Actions(driver).moveToElement(element).perform();
            if (os == MAC) {
                element.click();
            }
            element.clear();
            element.click();
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(text);
            element.click();
            Actions action = new Actions(driver);
            action.moveToElement(element).doubleClick().build().perform();
        }
        catch(Exception e){
            logger.info("Error" + e);
        }
        finally {
            logger.info("moving forward with the test");
        }
    }


    // selecting choice from a drop down based on value
    public void selectFromDropdown(By elementId, String option) throws InterruptedException {
        try{
            WebElement element = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(elementId));
            scrollElement(element);
            //new Actions(driver).moveToElement(element).perform();
            Select drpDown = new Select(element);
            drpDown.selectByValue(option);
        }
        catch(Exception e){
            logger.info("Error" + e);
        }
        finally {
            logger.info("moving forward with the test");
        }
    }

    public void selectFromDropdown(By elementId, int option) throws InterruptedException {
        try{
            WebElement element = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(elementId));
            scrollElement(element);
            new Actions(driver).moveToElement(element).perform();
            Select drpDown = new Select(element);
            drpDown.selectByIndex(option);
        }catch(Exception e){
            logger.info("Error" + e);
        }
        finally {
            logger.info("moving forward with the test");
        }
    }

    public void selectTextFromDropdown(By elementId, String option) throws InterruptedException {
        try {
            WebElement element = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(elementId));
            scrollElement(element);
            //new Actions(driver).moveToElement(element).perform();
            Select drpDown = new Select(element);
            drpDown.selectByVisibleText(option);
        }
        catch (Exception e) {
            logger.info("Error" + e);
        }
        finally {
            logger.info("moving forward with the test");
        }
    }

    public String retrieveTextFromElement(By elementId) throws InterruptedException {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(elementId));
        scrollElement(element);
        new Actions(driver).moveToElement(element).perform();
        String actText = element.getAttribute("value");
        try{
            if(actText.length()==0){
                actText=element.getText();
            }
        }
        catch (Exception exception) {
            actText=element.getText();
        }
        finally {
            return actText.replaceAll(" ", "").trim();
        }

    }

    public String retrieveTextFromElement(String text) throws InterruptedException {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + text + "')][1]")));
        new Actions(driver).moveToElement(element).perform();
        String actText = element.getText();

        if (actText.equals("")) {
            element = (new WebDriverWait(driver, 2))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='" + text + "']")));
            new Actions(driver).moveToElement(element).perform();
            actText = element.getText();
        }
        return actText;
    }

    //Checks if an element is displayed on the page
    public boolean checkElementisDisplayed(By elementId) throws InterruptedException {
        try
        {
            driver.findElement(elementId).isDisplayed();
            return true;
        }
        catch(Exception ex) {
            return false; }
    }
    public By findonElementbyTxt(String text) throws InterruptedException, IOException {
        By element = By.xpath("//*[contains(text(),'" + text + "')][1]");
        return element;

    }

    public void scrollElement(WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(2500);
    }

    public void mouseOverElement(By element) {
        Actions action = new Actions(driver);
        WebElement we = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(element));
        action.moveToElement(we).build().perform();
    }

    public boolean elementExist(By element) {
        boolean flag = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed();
        return flag;
    }

    public String dateFormatter(String condition) {
        String usedDate = "";
        Date date = new Date();
        if (condition.equals("today")) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            usedDate = dateFormat.format(Calendar.getInstance().getTime());
        } else if (condition.equals("tomorrow")) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            usedDate = dateFormat.format(date);
        }
        return usedDate;
    }

    public void uploadFile(By elementId, String filePath) throws InterruptedException {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(elementId));
        scrollElement(element);
        element.sendKeys(filePath);
    }

    // Yellowbrick components using customized xpath //
    public void PPlusPassState(String ppluspassNr, String action) throws IOException, InterruptedException {
        if(action.equals("modify")) {
            clickOnElement(By.xpath("//tr[td[contains(text(),'" + ppluspassNr + "')]]/td[6]/form/input"));
        }
        if(action.equals("details")){
            clickOnElement( By.xpath("//div[@id='qpark-data']/table/tbody/tr[td[contains(text(),'"+ppluspassNr+"')]]/td[1]/a"));
        }
    }
    /* To be implemented for a generic search of frames in Taxamater*/
//    public void switchframes(String name) throws InterruptedException {
//        driver.switchTo().defaultContent();
//        try {
//            // check for frame in the default content
//            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='" + name + "']")));
//        }
//        // checking if frames are present within the children of default content
//        catch(Exception ex) {
//            // getting the number of frames in default content
//            int size = driver.findElements(By.tagName("frame")).size();
//            int i=0;
//            // iterating through all frames of default content
//           while(i<size){
//                driver.switchTo().frame(i);
//                size = driver.findElements(By.tagName("frame")).size();
//                int j=0;
//                try {
//                    // if there are inner frames in child frame
//                    while (j < size) {
//                        try {
//                            driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='" + name + "']")));
//                            break;
//                        }
//                        catch(Exception e)
//                        {
//                            j++;
//                            driver.switchTo().frame(j);
//                            continue;
//                        }
//                    }
//                }
//                catch (Exception e){
//                    i++;
//                    continue;
//                }
//                finally{
//                    if(j==0){
//                        driver.switchTo().parentFrame();
//                        size = driver.findElements(By.tagName("frame")).size();
//                        i++;
//                        continue;
//                    }
//                }
//            }
//
//        }
//    }
}
