package pageobjects.taxameter;
import org.openqa.selenium.By;
public class PPlusPass {
    public By ppluspasssearchtext = By.xpath("//*[@name='search_cardnr']");
    public By ppluspasssearchbutton = By.xpath("//*[@value='Start searching']");
    public By customerrefNr=By.xpath("//*[@id='form2']//*[@name='customerNr']");
    public By attachcustomerrefNr=By.xpath("//*[@value='Attach']");
    public By changestatus=By.xpath("//*[@value='Change status']");
    public By cardstatus=By.xpath("//tbody[tr[td[contains(text(),'Status')]]]/tr[3]/td[2]");
    public By cardstatusdropdown=By.xpath("//*[@id='process_statusid_new']");
    public By cardstatussave=By.xpath("//*[@value='Save']");
    public By confirmrevoke=By.xpath("//button[text()='OK']");
}
