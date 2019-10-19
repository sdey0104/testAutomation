package pageobjects.myyellowbrick;

import org.openqa.selenium.By;

public class ResetPassword {

	public By currentPassword = By.xpath(".//*[@id='currentPassword']");
    public By newPassword = By.xpath(".//*[@id='newPassword']");
    public By confirmnewPassword= By.xpath("//*[@id='confirmNewPassword']");
    public By save= By.xpath(".//*[@value='Opslaan']");

}
