package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderHistoryPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By orderReference =By.cssSelector("[class='color-myaccount']"); //confirmation popup
	private By date =By.cssSelector("[class*='item footable-sortable']"); //sort by date button
	
	//constructor
	public OrderHistoryPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public String getOrderReference() {
		return driver.findElement(orderReference).getText();
	}
	public void clickDate() {
		driver.findElement(date).click();
	}
	public List<String> getAllOrderReferences(){
		//only need to grab text so returns the text from the references as a string list
		List<WebElement> elements =driver.findElements(orderReference);
		List<String> strings =new ArrayList<>();
		for(WebElement element:elements) {
			strings.add(element.getText());
		}
		return strings;
	}
}
