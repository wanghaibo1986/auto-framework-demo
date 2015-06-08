package testnglistener.tests;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utils.custom_annotations.CaseId;

public class Test2 {
	
	@Test
	@CaseId({"002","004"})
	public void verify_for_Test2() {
		Assert.assertEquals(3, 3);
	}

}
