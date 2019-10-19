package pageobjects.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductSelection {
	
	public WebDriver driver;
	/**
	 * Park Type
	 */
	public By parkTypeBusiness = By.xpath("//input[@id='radioBusiness']/ancestor-or-self::div[1]");
	public By ParkTypeConsumerSelected = By.xpath("//*[@class='consumer selected']");
	/**
	 * Add and reduce cars
	 */
	public By addNumberofCars = By.xpath(".//*[@class='add-reduce-buttons buttons-cars']/a[2]");
	public By reduceNumberofCars = By.xpath(".//*[@class='add-reduce-buttons buttons-cars']/a[1]");
	/**
	 * License Plate
	 */
	public By licencePlate = By.xpath("//*[@id='cars0.licensePlate']");
	/**
	 * Mobile Number
	 */
	public By mobileNumber = By.xpath("//*[@id='cars0.mobile']");
	/**  Action Code*/
	public By actioncodelink = By.xpath("//*[@id='applyActionCodeForm']/div/a");
	public By actioncodetext=By.xpath("//*[@id='actionCode']");
	public By applyactioncode=By.xpath("//*[@id='applyActionCodeForm']/div/div/div[2]/div[2]/input");
	public By costlabel=By.xpath("//*[@id='costs-overview']/div[1]/table/tbody/tr[1]/th[2]/span");
	public By actioncodeapplied=By.xpath("//*[@id='applyActionCodeForm']/div/div");
	public By actioncodeinvalid=By.xpath("//*[@id='applyActionCodeForm']/div/div/div[1]");
	/**
	 * P+ card(Add/Reduction)
	 */
	public By ppluspasstext=By.xpath("//*[@id='cars[0].extraPlusPassAmount']");
	public By pPlusPassAdd = By.xpath("//*[@id='content-main-block']/form/div[1]/div[3]/div[1]/div[3]/div[3]/a[1]");
	public By pPlusPassReduce = By.xpath("//*[@id='content-main-block']/form/div[1]/div[3]/div[1]/div[3]/div[3]/a[2]");
	/**
	 * Next Button
	 */
	public By nextButton = By.xpath("//*[@id='nextBtn']");
	public By acceptcookies = By.xpath("//*[@id='cookie-consent']/div[1]/a[@class='cc-set-cookie btn btn-green']");
	//*[contains(text(),'Accepteer cookies')][2]
	//TODO :remove below path once stabilized 
	//*[@id='cookie-consent']/div[1]/a[4]
	public By kvkNumber= By.xpath("//*[@id='identificationFields0.bid.value']");
	public By radiobtnOtherAddNo = By.xpath(".//*[@class='form-inner-row']/label[2]/span");
	public By elementclick = By.xpath("//*[@id='content-column-2-page']/div[2]");
	/**
	 * Error message Fields
	 */
	public By licenseplateerror=By.xpath("//*[@id='errorcars0licensePlate']");
	public By mobilenumbererror=By.xpath("//*[@id='errorcars0mobile']");
	public By ErrorBlock=By.xpath("//div[@id='ShowErrorText'][contains(@style,'display: block')]");


	public By elementClick = By.xpath("//*[@id='content-column-2-page']/div[2]");


	/**
	 * ANWB Suscription code
	 */
	public By subscriptionCode=By.xpath("//*[@id='externalMembershipCodeValue']");
}
