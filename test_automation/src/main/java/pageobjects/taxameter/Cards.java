package pageobjects.taxameter;
import org.openqa.selenium.By;
public class Cards {
    public By buttonbatchvalidate = By.xpath("//*[@id='validateAll']");
    public By confirmbatchvalidation = By.xpath("//*[text()='Ja']");
    //counting the number of rows validated in batch
    public By rowcountbatchvalidation=By.xpath("//table[tbody[tr[td[contains(text(),'Product group')]]]]/tbody/tr");
    //counting the number of columns in each row, validated in batch
    public By colcountbatchvalidation=By.xpath("//table[tbody[tr[td[contains(text(),'Product group')]]]]/tbody/tr[1]/td");
    public By productgroupsdropdown = By.xpath("//*[@name='groupid']");
    public By amountselected=By.xpath("//*[@id='amountSelection']");
    public By savecardupdate=By.xpath("//*[@value='Save']");

}
