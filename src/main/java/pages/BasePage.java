package pages;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	public WebDriver driver;
	public WebDriverWait wait;
	
	public String PAGE_URL;
	public String PAGE_TITLE;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
	}
	
	public String getPageUrl() {
		return PAGE_URL;
	}
	
	public String getPageTitle() {
		return PAGE_TITLE;
	}

	public void loadPage() {
		driver.get(getPageUrl());
	}
	
	public void clickElement(WebElement ele) {
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		ele.click();
	}
	
	public void inputText(WebElement ele, String text) {
		ele.clear();
		ele.sendKeys(text);
		Assert.assertEquals(text, ele.getAttribute("value"));
	}
	
	public void selectOption(WebElement selectEle, String optionValue) {
		Select select = new Select(selectEle);
		select.selectByValue(optionValue);
	}
}
