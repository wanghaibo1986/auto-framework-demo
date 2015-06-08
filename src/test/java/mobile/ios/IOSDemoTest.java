package mobile.ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class IOSDemoTest {

	private AppiumDriver driver;

	@Test
	public void verify() throws MalformedURLException {
		try {
			File appDir = new File("/Users/baijingjiao/Library/Developer/Xcode/DerivedData/Everbridge-cnmlrnwkwrokdwbfgsooyybxquas/Build/Products/Debug-iphonesimulator");
			File app = new File(appDir, "Everbridge.app");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "iPhone 6");
			capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("platformVersion", "8.1");
//			capabilities.setCapability("udid", "8821390ffc5ca5c582d943c21e350b1658f9733e"); // 不设置“udid”就是用模拟器
			System.out.println(app.getAbsolutePath());
			capabilities.setCapability("app", app.getAbsolutePath());
			
			driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
			System.out.println(driver.getPageSource());
			
			WebElement e = driver.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAButton[1]");
			e.sendKeys("blablabla");
			
		} finally {
						
			driver.quit();
			
		}
	}
	
	/*
	 * Seems like Appium doesn't support custom views.
	 * 
	 * 
	 * 
	 * Set contexts = driver.getContextHandles();
Iterator iterator = contexts.iterator();

	// check values
	while (iterator.hasNext()){
		System.out.println("Value: "+iterator.next() + " ");
	 */
	
	public void swipe() {  
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    HashMap<String, Double> swipeObject = new HashMap<String, Double>();
	    swipeObject.put("startX", 0.95);
	    swipeObject.put("startY", 0.5);
	    swipeObject.put("endX", 0.05);
	    swipeObject.put("endY", 0.5);
	    swipeObject.put("duration", 1.8);
	    js.executeScript("mobile: swipe", swipeObject);
    }
	
	/*
	输入汉字需设置capabilities如下：
	capabilities.setCapability("unicodeKeyboard", "True");
    capabilities.setCapability("resetKeyboard", "True");
    
	TouchAction action = new TouchAction(driver);
	acton.tap(x,y).perform();
	
	// this is important part.
	driver.switchTo().window("WEBVIEW");
 	...
	// we are now logged in app and we proceed with native app
	driver.switchTo().window("NATIVE_APP");

	 */
	
	/*
	 * getDriver().tap(1, lp.login.getLocation().x, lp.login.getLocation().y, 1);
	 */
	
}
