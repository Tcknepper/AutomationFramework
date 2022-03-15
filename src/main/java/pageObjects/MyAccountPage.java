package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By signOut =By.cssSelector("[class='logout']"); //sign out button
	private By accountInfo =By.cssSelector("[class='info-account']"); //forgot password link
	
	//constructor
	public MyAccountPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public String getAccountInfo() {
		//I only ever use the text for this element so I put the code for it in here
		return driver.findElement(accountInfo).getText();
	}
	public SignInPage clickSignOut() {
		driver.findElement(signOut).click();
		return new SignInPage(driver);
	}
}
