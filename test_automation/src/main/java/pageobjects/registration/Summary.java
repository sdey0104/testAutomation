package pageobjects.registration;

import org.openqa.selenium.By;

public class Summary {

    /**
     * Validate all the input in the previous screen
     */
    public By chosenTypeofParking = By.xpath("//*[contains(text(),'Particulier parkeren')]");//*[@id='content-main-block']/form/div[1]/div/font/font");
    public By mobileTelephone = By.xpath("//*[text()='Mobiel telefoonnummer:']/following-sibling::span");


    public By name = By.xpath("//*[text()='Contactpersoon']/following-sibling::p[1]");
    public By dob = By.xpath("//*[text()='Contactpersoon']/following-sibling::p[2]");

    public By telephone() {
        By telephone = null;
        if (System.getProperty("country").equals("NL")) {
            telephone = By.xpath("//*[text()='Contactinformatie']/following-sibling::p[1]");
        }
        if (System.getProperty("country").equals("BE")) {
            telephone = By.xpath("//*[text()='Contactinformatie']/following-sibling::p[1]");
        }
        if (System.getProperty("country").equals("DE")) {
            telephone = By.xpath("//*[text()='Kontaktdaten']/following-sibling::p[1]");
        }
        return telephone;
    }

    public By email() {
        By email = null;
        if (System.getProperty("country").equals("NL")) {
            email = By.xpath("//*[text()='Contactinformatie']/following-sibling::p[2]");
        }
        if (System.getProperty("country").equals("BE")) {
            email = By.xpath("//*[text()='Contactinformatie']/following-sibling::p[2]");
        }
        if (System.getProperty("country").equals("DE")) {
            email = By.xpath("//*[text()='Kontaktdaten']/following-sibling::p[2]");
        }
        return email;
    }

    public By postCode = By.xpath("//*[text()='Jouw adres']/following-sibling::p[3]");
    public By city = By.xpath("//*[text()='Jouw adres']/following-sibling::p[4]");

    //*[@id="content-main-block-page"]/div/text()


}
