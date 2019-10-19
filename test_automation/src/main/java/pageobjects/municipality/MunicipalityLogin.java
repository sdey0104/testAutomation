package pageobjects.municipality;

import org.openqa.selenium.By;

public class MunicipalityLogin {


    public By BEmyClientNumber = By.xpath(".//*[@id='username']");
    public By NLmyClientNumber = By.xpath(".//*[@id='j_username']");
    public By BEmypassword = By.xpath(".//*[@id='password']");
    public By NLmypassword = By.xpath(".//*[@id='j_password']");
    public By btnLogin = By.xpath("//*[@id='loginButton']");
}
