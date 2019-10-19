package pageobjects.taxameter;
import org.openqa.selenium.By;
public class TaxameterLogin {
    public By username = By.xpath("//*[@name='gebruikersnaam']");
    public By password = By.xpath("//*[@name='wachtwoord']");
    public By menuselect = By.xpath("//select[@name='kies_funktie']");
    public By login = By.xpath("//*[@name='aktie' and @value='2']");
}