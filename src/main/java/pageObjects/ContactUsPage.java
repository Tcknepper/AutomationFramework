package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactUsPage {

public WebDriver driver;
	
	//private By  =By.cssSelector("");
	private By heading =By.cssSelector("[id='id_contact']"); //heading dropdown
	private By headingOptions =By.cssSelector("[id='id_contact']>option"); //options in heading dropdown
	private By email =By.cssSelector("[id='email']"); //email field
	private By reference =By.cssSelector("[name='id_order']"); //order reference dropdown
	private By referenceOptions =By.cssSelector("[name='id_order']>option"); //options in order reference dropdown
	private By send =By.cssSelector("[id='submitMessage']"); //send button
	
	//constructor
	public ContactUsPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public WebElement getHeading() {
		return driver.findElement(heading);
	}
	public List<WebElement> getHeadingOptions() {
		return driver.findElements(headingOptions);
	}
	public List<String> getHeadingString() {
		//only need to grab text so returns the text from the options as a string list
		List<WebElement> elements =driver.findElements(headingOptions);
		List<String> strings =new ArrayList<>();
		for(WebElement element:elements) {
			strings.add(element.getText());
		}
		return strings;
	}
	public WebElement getEmail() {
		return driver.findElement(email);
	}
	public WebElement getReference() {
		return driver.findElement(reference);
	}
	public List<WebElement> getReferenceOptions() {
		return driver.findElements(referenceOptions);
	}
	public List<String> getReferenceString(){
		//only need to grab text so returns the text from the options as a string list
		List<WebElement> elements=getReferenceOptions();
		List<String> strings = new ArrayList<>();
		for(WebElement element:elements) {
			String[] split =element.getText().split(" ");
			strings.add(split[0]);
		}
		return strings;
	}
	public void clickSend() {
		driver.findElement(send).click();
	}
}
