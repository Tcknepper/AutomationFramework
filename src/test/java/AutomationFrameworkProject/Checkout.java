package AutomationFrameworkProject;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.CartPage;
import pageObjects.LandingPage;
import pageObjects.OrderHistoryPage;
import pageObjects.SignInPage;
import resources.base;

public class Checkout extends base {

	public WebDriver driver; //declaring driver so it is local to this class and not overwritten when running in parallel
	public String orderReference;
	public static Logger log = LogManager.getLogger(base.class.getName()); //used for logging
	
	@BeforeTest
	public void initialize() throws IOException {
		driver =initializeDriver(); //initializing driver before test
	}
	
	@Test
	public void verifyCartContents() throws InterruptedException {
		SoftAssert softAssert =new SoftAssert();
		driver.get(prop.getProperty("homepageurl")); //navigating to home page
		LandingPage lp =new LandingPage(driver);
		List<WebElement> popularProducts =lp.getPopularProducts(); //storing popular product elements
		List<String> productNames =lp.addItemsToCart(popularProducts); //adding items to cart and getting names
		float totalProductPrices =lp.getTotalPrice(popularProducts); //getting the total price of products added to cart
		CartPage cp =lp.clickCart(); //clicking cart
		List<String> cartNames = cp.getCartNames(); //getting names of products in cart
		for(int i =0;i<productNames.size();i++) { //for loop to compare the items added to cart vs the actual items in cart
			softAssert.assertEquals(productNames.get(i), cartNames.get(i), "Adding items to cart failed");
		}
		softAssert.assertEquals(cp.getPrice(), totalProductPrices, "Verify prices failed"); //verifying prices match
		softAssert.assertAll();
	}
	@Test(dependsOnMethods= {"verifyCartContents"}) //need items in cart to checkout so made it run after verifyCartContents
	public void verifyCheckout() {
		SoftAssert softAssert =new SoftAssert();
		driver.get(prop.getProperty("carturl")); //navigating to cart page
		CartPage cp = new CartPage(driver);
		cp.clickCheckout(); //clicking checkout
		SignInPage sp =new SignInPage(driver);
		sp.getEmail().sendKeys(prop.getProperty("email")); //entering email
		sp.getPassword().sendKeys(prop.getProperty("password")); //entering password
		sp.clickSignin(); //click sign in
		cp =new CartPage(driver);
		cp.clickProceedAddress(); //clicking to proceed
		cp.clickProceedAgreement(); //clicking to proceed
		//verifying error message pops up when you do not agree to the terms
		softAssert.assertEquals(cp.getPopupError(), prop.getProperty("agreeerror"));
		cp.clickCloseError(); //closing error message
		cp.clickAgreeCheckbox(); //agreeing to terms
		cp.clickProceedAgreement(); //clicking to proceed
		cp.clickPaymentType(true); //used when implementing data. selecting payment type
		cp.clickConfirm(); //clicking confirm order
		//verifying that the order is successful
		softAssert.assertEquals(cp.getConfirmationMessage(), prop.getProperty("orderconfirmed"));
		orderReference =cp.getOrderReference(); //storing the order reference
		softAssert.assertAll();
	}
	@Test(dependsOnMethods={"verifyCheckout"}) //need an order to be placed before I can verify it so made it run after verifyCheckout
	public void verifyOrderPlaced() {
		driver.get(prop.getProperty("homepageurl")); //navigating to homepage
		LandingPage lp =new LandingPage(driver);
		OrderHistoryPage oh =lp.clickMyOrders(); //clicking my orders link
		Assert.assertEquals(oh.getOrderReference(), orderReference); //verifying order reference matches the one from verifyCheckout
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
