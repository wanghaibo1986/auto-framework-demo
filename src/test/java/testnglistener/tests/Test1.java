package testnglistener.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import utils.custom_annotations.CaseId;

public class Test1 {
	
	@Test
	@CaseId({"001","003"})
	public void verify_for_Test1() {
		Assert.assertEquals(3, 3);
	}

}
