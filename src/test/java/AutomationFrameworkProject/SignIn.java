package AutomationFrameworkProject;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.ForgotPasswordPage;
import pageObjects.LandingPage;
import pageObjects.MyAccountPage;
import pageObjects.SignInPage;
import resources.base;

public class SignIn extends base {

	public WebDriver driver; //declaring driver so it is local to this class and not overwritten when running in parallel
	public static Logger log = LogManager.getLogger(base.class.getName()); //used for logging
	
	@BeforeTest
	public void initialize() throws IOException {
		driver =initializeDriver(); //initializing driver before test
	}
	
	@Test
	public void verifySignInAndOut() {
		SoftAssert softAssert = new SoftAssert();
		driver.get(prop.getProperty("homepageurl")); //navigating to home page
		LandingPage lp =new LandingPage(driver);
		SignInPage sp =lp.clickSignin(); //clicking sign in button
		sp.getEmail().sendKeys(prop.getProperty("email")); //entering email
		sp.getPassword().sendKeys(prop.getProperty("password")); //entering password
		sp.clickSignin(); //click sign in
		MyAccountPage ma= new MyAccountPage(driver);
		softAssert.assertEquals(ma.getAccountInfo(), prop.getProperty("signinWelcome"), "Sign in failed"); //verifying successful sign in
		
		//verifying signing out
		sp  =ma.clickSignOut(); //signing out
		softAssert.assertFalse(sp.checkSignOut(),"sign out failed"); //verifying that sign out button is not present
		softAssert.assertTrue(sp.checkSignIn(), "sign out failed"); //verifying sign in button is present
		softAssert.assertAll();
	}
	@Test
	public void verifyForgotPassword() {
		driver.get(prop.getProperty("signinpageurl"));
		SignInPage sp =new SignInPage(driver);
		ForgotPasswordPage fp =sp.clickForgot(); //clicking forgot password
		fp.getEmail().sendKeys(prop.getProperty("email")); //entering email
		fp.getRetrieve().click(); //clicking retrieve password
		Assert.assertEquals(fp.getConfirmation(), prop.getProperty("retrieveConfirmation"), "Retrieve password failed"); //verifying email was sent
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
