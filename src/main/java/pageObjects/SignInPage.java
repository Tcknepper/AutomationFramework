package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By email =By.id("email"); //email field
	private By password =By.id("passwd"); //password field
	private By signin =By.id("SubmitLogin"); //sign in button
	private By forgot =By.linkText("Forgot your password?"); //forgot password link
	private By signOut =By.cssSelector("[class='logout']"); //sign out button
	private By contactUs =By.cssSelector("[title='Contact Us']"); //contact us button
	
	//constructor
	public SignInPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public void clickSignin() {
		driver.findElement(signin).click();
	}
	public WebElement getEmail() {
		return driver.findElement(email);
	}
	public WebElement getPassword() {
		return driver.findElement(password);
	}
	public ForgotPasswordPage clickForgot() {
		//this element is only clicked so I put the code to click and go to the page in this method
		driver.findElement(forgot).click();
		return new ForgotPasswordPage(driver);
	}
	public ContactUsPage clickContactUs() {
		driver.findElement(contactUs).click();
		return new ContactUsPage(driver);
	}
	public boolean checkSignOut() {
		//checks if sign out button is present, if it is not present it catches nosuchelement exception and returns false
		try {
			//had to put By here so exception is not thrown when running "By signOut" above
			driver.findElement(signOut);
		} catch(NoSuchElementException e) {
			return false;
		}
		return true;
	}
	public boolean checkSignIn() {
		//checks if sign in button is present, if it is present it returns true
		try {
			//had to put By here so exception is not thrown when running "By signin" above
			driver.findElement(signin);
		} catch(NoSuchElementException e) {
			return false;
		}
		return true;
	}
}
