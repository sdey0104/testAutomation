package pageobjects.registration;

import org.openqa.selenium.By;
public class YourData{
	/**
	 * General details
	 */
	public By sex = By.xpath("//*[@id='gender']");
	public By initials= By.xpath("//*[@id='initials']");
	public By firstName = By.xpath("//*[@id='firstname']");
	public By lastName = By.xpath("//*[@id='lastname']");
	public By optionalInsert = By.xpath("//*[@id='infix']");
	/**
	 * Date of birth
	 */
	public By birthDay = By.xpath("//*[@id='birthDay']");
	public By birthMonth = By.xpath("//*[@id='birthMonth']");
	public By birthYear = By.xpath("//*[@id='birthYear']");
	/**
	 * Contact information
	 */
	public By telephone = By.xpath("//*[@id='phoneNr']");
	public By emailAddress = By.xpath("//*[@id='email']");
	public By repeatemailAddress = By.xpath("//*[@id='emailRepeat']");	
	public By newsLetterSignUp = By.xpath("//*[@class='box']");
	/**
	 * Address
	 */
	public By country = By.xpath("//*[@id='address.country']");
	public By postalCode= By.xpath("//*[@id='address.zipCode']");
	public By houseNumber=By.xpath("//*[@id='address.houseNr']");
	public By address=By.xpath("//*[@id='address.addressLine1']");
	public By additionalHNr =By.xpath("//*[@id='address.addressLine2']");
	public By residence = By.xpath("//*[@id='address.city']");

	public By doorgaanPopUp = By.xpath("//button[@class='ui-state-default ui-corner-all']/button[2]");

	//div[@id='emailExistsDialog']/following-sibling::div[1]/button[2]
	public By elementClick = By.xpath("//*[@id='content-column-2-page']/div[2]");
	public By businessName = By.xpath("//input[@id='businessName']");
	public By btwNummer = By.xpath("//input[@id='identificationFields1.bid.value']");
	public By kvknumber=By.xpath("//input[@id='identificationFields0.bid.value']");
	/**
	 * Error message Fields
	 */
	public By businessNameerror=By.xpath("//*[@id='errorbusinessName']");
	public By btwNummererror=By.xpath("//*[@id='identificationFields1.bid.value']");
	public By kvkNummererror=By.xpath("//*[@id='erroridentificationFields0bidvalue']");
	public By firstnameerror=By.xpath("//*[@id='errorfirstname']");
	public By lastnameerror=By.xpath("//*[@id='errorlastname']");
	public By birthdateerror=By.xpath("//*[@id='errorbirthDay']");
	public By mobilenumbererror=By.xpath("//*[@id='errorphoneNr']");
	public By emailerror=By.xpath("//*[@id='erroremail']");
	public By repeatemailerror=By.xpath("//*[@id='erroremailRepeat']");
	public By zipcodeerror=By.xpath("//*[@id='erroraddresszipCode']");
	public By houseNrerror=By.xpath("//*[@id='erroraddresshouseNr']");
	public By addresserror=By.xpath("//*[@id='erroraddressaddressLine1']");
	public By cityerror=By.xpath("//*[@id='erroraddresscity']");
	public By ErrorBlock=By.xpath("//div[@id='ShowErrorText'][contains(@style,'display: block')]");

}
