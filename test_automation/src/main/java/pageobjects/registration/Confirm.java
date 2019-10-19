package pageobjects.registration;

import org.openqa.selenium.By;

public class Confirm {
	
	public By radioAutomaticCollection = By.xpath("//div[@class='type-of-payment-row']/div/input/following-sibling::label[1]/span");
	
	public By creditCard = By.xpath("//div[@class='type-of-payment-row cc-payment-row ']/input/following-sibling::label/span");
	
	public By iban = By.xpath("//*[@id='accountNr']");
	
	public By accountHolderName = By.id("accountHolderName");
	
	public By checkBoxinfoOath = By.xpath("//label[@for='accordAutoincasso']/span[@class='box']");
	
	public By checkBoxAutoDebit = By.xpath("//label[@for='directDebitPermission']/span[@class='box']");

	public By messageForConfirmation= By.xpath("//div[@id='content-main-block-wrap']/div/h1");
	
	public By completeRegistration = By.xpath("//*[@id='automatischIncasso']/div[2]/div/div[2]");
	
	public By selectIban = By.xpath("//*[@class='type-of-payment-row']/div/label[@for='radio_A']");
	
	public By selectCC = By.xpath("//div[@class='type-of-payment-row cc-payment-row ']/input/following-sibling::label/span");

	public By ccnumber=By.xpath("//*[@id='ekashu_card_number']");

	public By ccexpiryEndMonth = By.xpath("//*[@id='ekashu_input_expires_end_month']");

	public By ccexpiryEndyYear = By.xpath("//*[@id='ekashu_input_expires_end_year']");

	public By ccSecurityCode = By.xpath("//*[@id='ekashu_verification_value']");

	public By ccbtnContinue = By.xpath("//*[@id='ekashu_submit_continue_button']");

	public By bicCode = By.xpath("//*[@id='bicCode']");
	public By elementClick = By.xpath("//*[@id='content-column-2-page']/div[2]");
	/**
	 * Error message Fields
	 */
	public By ibanerror=By.xpath("//*[@id='erroraccountNr']");
	public By accountholdernamerror=By.xpath("//*[@id='erroraccountHolderName']");
	public By errorBlock =By.xpath("//div[@id='ShowErrorText'][contains(@style,'display: block')]");
	/**
	 * IBAN validation in registration
	 */
	public By ibanValidation=By.xpath("//*[@name='_eventId_validateIban']");

	public By selectBank = By.xpath("//*[@id='bicCodePreselect']");

	public By btnContinueAfterSelectingBank = By.xpath("//input[@id='button_continue']");

	public By btnSubmitSucessIbanTestValidation = By.xpath(".//*[@value='Submit status']");

	public By IBANStatus=By.xpath("//*[@id='contentPanel']/table/tbody/tr[2]/td/select");

	public By pageheader=By.xpath("//*[@id='content-wrap']/h1");

	public By previousButton=By.xpath("//*[@id='lastBtn']/span[2]");

}
