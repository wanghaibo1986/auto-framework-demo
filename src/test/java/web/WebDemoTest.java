package web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.BaseUtil;

public class WebDemoTest {
	
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.baidu.com");
		BaseUtil.setDriver(driver);
		
		BaseUtil kwInput = BaseUtil.id("kw");
		BaseUtil xpathKw = BaseUtil.xpath("//input[@id='kw']");
		System.out.println(kwInput.toString());
		System.out.println(xpathKw.toString());
		xpathKw.findElement().sendKeys("lkdsksdjlfsdjf");
	}

}
