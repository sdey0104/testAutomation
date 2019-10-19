package pageobjects.taxameter;
import org.openqa.selenium.By;
public class CustomersValidation {
    public By details = By.xpath("//*[@id='datatable']/tbody/tr[1]/td[12]/input");
    public By productgroupsdropdown = By.xpath("//*[@name='groupid']");
    public By risktab=By.xpath("//div[@class='page-menu']/a[2]");
    public By validatereasonautovalidation = By.xpath("//tr[td[contains(text(),'Reason auto validation')]]/td[3]");
    public By Riskreasonautovalidation=By.xpath("//tr[td[contains(text(),'Auto')]]/td[5]");
    public By sortnames = By.xpath("//*[@id='datatable']/thead/tr/td[1]");
    public By dataOK = By.xpath("//*[@value='Data OK']");
    public By activate=By.xpath("//*[@value='Activate']");
    public By delete = By.xpath("//*[@value='Delete']");
}
