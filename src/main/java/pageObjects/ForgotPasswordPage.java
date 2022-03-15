package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By email =By.id("email"); //email field
	private By retrieve =By.xpath("//*[text()='Retrieve Password']"); //retrieve password button
	private By confirmation =By.cssSelector("[class*='alert']"); //confirmation popup
	
	//constructor
	public ForgotPasswordPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public WebElement getEmail() {
		return driver.findElement(email);
	}
	public WebElement getRetrieve() {
		return driver.findElement(retrieve);
	}
	public String getConfirmation() {
		//I only ever use the text for this element so I put the code for it in here
		return driver.findElement(confirmation).getText();
	}
}
