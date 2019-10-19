package pageobjects.registration;

import org.openqa.selenium.By;

public class Mailbox {

	public By mailboxUsername = By.xpath("//*[@id='username']");
	public By mailboxPassword = By.xpath("//*[@id='password']");
	public By mailboxSignin = By.xpath("//*[@type='submit']");
	public By searchText = By.xpath("//*[@id='txtS']");
	public By searchButton = By.xpath("//*[@id='imgS']");
	public By openMail = By.xpath("//*[@class='baseIL gbIL']/div[2]/div/div[1]/div[4]");
	public By mailFrom= By.xpath("//*[@id='spnFrom']");

	public By linktosetNewPassword= By.xpath("//*[@color='#019FE3']");
	public By linktosetNewPassword_ANWB=By.xpath("//*[@id='divBdy']/div/div/font/span/table[3]/tbody/tr[1]/td/table/tbody/tr/td/div[7]/table/tbody/tr/td/table/tbody/tr[3]/td[2]/table/tbody/tr/td[1]/span/a/font/span/b");

	public By clientNumber=By.xpath("//*[@style='padding-top:3px;padding-bottom:12px;']");
	public By linkToResetPassword = By.xpath("//a[contains(text(), 'auth/password/reset')]");


	public By registrationreject=By.xpath("//*[@id='divBdy']/div/div/font/span/table[3]/tbody/tr[1]/td/table/tbody/tr/td/font[2]");
	public By manualvalidationemail=By.xpath("//*[@id='divBdy']/div/div/font/span/table[3]/tbody/tr[1]/td/table/tbody/tr/td");
}
