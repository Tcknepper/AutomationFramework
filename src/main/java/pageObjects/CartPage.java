package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By cartItems =By.cssSelector("[class*='cart_item']"); //Items in cart
	private By price =By.id("total_product"); //total price of cart
	private By productName =By.cssSelector("[class='product-name']"); //name of product
	private By checkout =By.cssSelector("[class*='standard-checkout']"); //checkout button
	private By proceedAddress =By.name("processAddress"); //proceed through address screen
	private By proceedAgreement =By.name("processCarrier"); //proceed through agreement screen
	private By agreeCheckbox =By.id("cgv"); //agree to terms checkbox
	private By agreeError =By.cssSelector("[class='fancybox-error']"); //popup error box if you dont agree to terms
	private By closeError =By.cssSelector("[class='fancybox-item fancybox-close']"); //close the agree to terms error box
	private By bankwire =By.cssSelector("[class='bankwire']"); //pay by bankwire button
	private By check =By.cssSelector("[class='cheque']"); //pay by check button
	private By confirm =By.cssSelector("[class*='button btn btn-']"); //confirm order button
	private By orderReference =By.cssSelector("[class*='box']"); //line containing order reference number
	private By confirmationMessage =By.xpath(".//p/strong"); //line with order confirmed method
	
	//constructor
	public CartPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public List<WebElement> getCartItems() {
		return driver.findElements(cartItems);
	}
	public float getPrice() {
		//only need the price as a float from this element so I put the code here
		return Float.parseFloat((driver.findElement(price).getText()).substring(1));
	}
	public String getProductName(WebElement item) {
		//only need to get the product name of a single element as a string so put code here
		return item.findElement(productName).getText();
	}
	public List<String> getCartNames(){
		List<String> cartNames = new ArrayList<>(); //list of product names in cart
		List<WebElement> cartContents =getCartItems(); //storing cart contents elements
		for(WebElement cartContent:cartContents) { //for loop get the names of cart contents
			cartNames.add(getProductName(cartContent));
		}
		return cartNames;
	}
	public void clickCheckout() {
		driver.findElement(checkout).click();
	}
	public void clickProceedAddress() {
		driver.findElement(proceedAddress).click();
	}
	public void clickProceedAgreement() {
		driver.findElement(proceedAgreement).click();
	}
	public void clickAgreeCheckbox() {
		driver.findElement(agreeCheckbox).click();
	}
	public String getPopupError() {
		//only need the text as string
		try { //if popup does not appear try catch will handle it to allow rest of test to run and the test will still fail
		return driver.findElement(agreeError).getText();
		} catch(NoSuchElementException e) {
			return "element not found";
		}
	}
	public void clickCloseError() {
		//if assertion fails in test and error box does not popup then the try catch will handle exception so rest of test can continue
		try {
		driver.findElement(closeError).click();
		} catch(NoSuchElementException e) {}
	}
	public void clickPaymentType(boolean payment) {
		if(payment) {
			driver.findElement(bankwire).click();
		}
		else{
			driver.findElement(check).click();
		}
	}
	public void clickConfirm() {
		driver.findElement(confirm).click();
	}
	public String getOrderReference() {
		String ref =driver.findElement(orderReference).getText(); //getting line of text
		String[] split= ref.split("reference "); //split in half before order reference
		String[] split2 =split[1].split(" "); //split again on spaces
		return split2[0]; //first instance is the reference so return it
	}
	public String getConfirmationMessage() {
		//only need the text as a string
		return driver.findElement(confirmationMessage).getText();
	}
}
