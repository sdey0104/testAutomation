package pageobjects.municipality;

import org.openqa.selenium.By;

public class Municipality {

    public By parkedCarTab = By.xpath("//*[@id='nav-service-cars']/a");
    public By closedTransaction= By.xpath("//*[@id='nav-transactions']/a");
    public By openTab= By.xpath("//table[@class='data-table']/tbody/tr[2]/td[1]/a");
    public By clickopenTabfromLicencePlate(String licenseplate){
        By element = By.xpath("//*[text()='"+licenseplate+"']/ancestor::tr[1]/td[1]/a");
        return element;
    }

    public By stopParaking = By.xpath("//*[@id='stopButton']");
    public By municipalityEndingTime = By.xpath(".//*[@id='endTime']");
    public By municipalityParkingCost = By.xpath(".//*[@id='parkingCostsString']");
}
