package pageobjects.taxameter;
import org.openqa.selenium.By;
public class Customers {
    public By customername=By.xpath("//*[@name='search_customernr']");
    public By searchcustomer=By.xpath("//*[@value='Start searching']");
    public By general=By.xpath("//*[text()='General']");
    public By membership=By.xpath("//*[text()='Membership']");
    public By status=By.xpath("//*[text()='Status']");
    public By pincode=By.xpath("//*[text()='Pin code']");
    public By transactions=By.xpath("//*[text()='Transactions']");
    public By transpondercards=By.xpath("//*[text()='Transponder cards']");
    public By mobiletelephonenumbers=By.xpath("//*[text()='Mobile telephone numbers']");
    public By othercards=By.xpath("//*[text()='Other cards']");
    public By cardorders=By.xpath("//*[text()='Card orders']");
    public By customerhistory=By.xpath("//*[text()='Customer history']");
    public By ordertranspondercards=By.xpath("//*[@value='Order transponder cards']");
    public By ordersleeves=By.xpath("//*[@value='Bestel hoesje']");
    public By orderppluspases=By.xpath("//*[@value='P+ pas Bestellen']");
    public By cardsvalue=By.xpath("//*[@id='numberOfCards']");
    public By addcards=By.xpath("//*[@value='Add']");
}