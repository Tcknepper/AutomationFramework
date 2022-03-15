package AutomationFrameworkProject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.ContactUsPage;
import pageObjects.LandingPage;
import pageObjects.OrderHistoryPage;
import pageObjects.SignInPage;
import resources.base;

public class ContactUs extends base {

	public WebDriver driver; //declaring driver so it is local to this class and not overwritten when running in parallel
	public List<String> referenceOptions;
	public static Logger log = LogManager.getLogger(base.class.getName()); //used for logging
	
	@BeforeTest
	public void initialize() throws IOException {
		driver =initializeDriver(); //initializing driver before test
	}
	
	@Test
	public void verifyContactOptions() throws InterruptedException {
		SoftAssert softAssert =new SoftAssert();
		driver.get(prop.getProperty("homepageurl")); //navigating to home page
		LandingPage lp =new LandingPage(driver);
		SignInPage sp =lp.clickSignin(); //clicking sign in button
		sp.getEmail().sendKeys(prop.getProperty("email")); //entering email
		sp.getPassword().sendKeys(prop.getProperty("password")); //entering password
		sp.clickSignin(); //click sign in
		ContactUsPage cu =sp.clickContactUs(); //clicking contact us
		cu.getHeading().click(); //click on heading dropdown
		List<String> headings =cu.getHeadingString(); //storing dropdown options for headings
		List<String> requiredHeadings=Arrays.asList(prop.getProperty("headingoptions").split(",")); //storing correct headings as list
		softAssert.assertTrue(headings.equals(requiredHeadings),"verify subject headings failed"); //verifying subject headings are correct
		//verifying email was autofilled correctly
		softAssert.assertEquals(cu.getEmail().getAttribute("value"), prop.getProperty("email"),"verify email autofill failed");
		cu.getReference().click(); //clicking order reference dropdown
		referenceOptions =cu.getReferenceString(); //storing dropdown options for later test
		referenceOptions.remove(0); //removing first index since it is a placeholder for the dropdown
		//cu.clickSend(); Did not test sending message to avoid resource limit
		softAssert.assertAll();
	}
	@Test(dependsOnMethods={"verifyContactOptions"}) //need order references from verifyContactOptions before running this test
	public void verifyReferenceOptions() {
		driver.get(prop.getProperty("orderhistoryurl")); //navigating to order history
		OrderHistoryPage oh =new OrderHistoryPage(driver);
		oh.clickDate(); //sorting orders by date since they are listed in desc order on contact us page
		List<String> referenceHistory =oh.getAllOrderReferences();
		Assert.assertTrue(referenceOptions.equals(referenceHistory),"verify reference options failed");
	}
	
	@AfterTest
	public void teardown() {
		driver.quit(); //closing driver after test
	}
	@AfterClass
	public void cleanUp() {
		driver.manage().deleteAllCookies(); //deleting all cookies after test class is finished
	}
}
