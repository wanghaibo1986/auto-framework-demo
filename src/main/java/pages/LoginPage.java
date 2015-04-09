package pages;

import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
		this.PAGE_URL = "http://www.mylogin.com";
		this.PAGE_TITLE = "mytitle";
	}

}
