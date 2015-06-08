package mobile.android;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;


public class AndroidDemoTest {
	
	private AppiumDriver driver;

	@Test
	public void verify() throws MalformedURLException {
		try {
			
			File appDir = new File("/Users/baijingjiao/Downloads/");
			File app = new File(appDir, "Manager_QA1_45_1131_031215.apk");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			//mandatory capabilities
			capabilities.setCapability("deviceName", "Android");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", "4.2");
			capabilities.setCapability("appPackage", "net.everbridge");
			capabilities.setCapability("appActivity", "phone.ui.SplashUI");
			capabilities.setCapability("app", app.getAbsolutePath());
			
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
			WebElement e = driver.findElementByXPath("//android.widget.EditText[contains(@text,'Username')]");
			e.sendKeys("blablabla");
			
		} finally {
			
			driver.quit();
			
		}
	}

}
