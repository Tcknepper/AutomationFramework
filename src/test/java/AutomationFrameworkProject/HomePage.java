package AutomationFrameworkProject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.LandingPage;
import resources.base;

public class HomePage extends base {

	public WebDriver driver; //declaring driver so it is local to this class and not overwritten when running in parallel
	public static Logger log = LogManager.getLogger(base.class.getName()); //used for logging
	
	@BeforeTest
	public void initialize() throws IOException {
		driver =initializeDriver(); //initializing driver before test
	}
	
	@Test(dataProvider="getData")
	public void verifySearchSuggestions(String term) {
		driver.get(prop.getProperty("homepageurl")); //navigating to home page
		LandingPage lp =new LandingPage(driver);
		lp.getSearchBar().sendKeys(term); //entering search term into search bar
		List<WebElement> suggestions =lp.getSearchSuggestions(); //list of all suggested search results
		//filtering original list into a new list containing only elements that contain the search term
		List<WebElement> filteredSuggestions = suggestions.stream().
				filter(suggestion->suggestion.getText().toLowerCase().contains(term)).
				collect(Collectors.toList());
		//checking that no elements were removed for not containing the search term
		Assert.assertEquals(filteredSuggestions.size(), suggestions.size(), "Search results failed with term: "+term);
	}
	@DataProvider
	public Object[] getData() {
		//data to be used in searchSuggestions
		Object[] data = new Object[4];
		data[0]=prop.getProperty("searchTerm1");
		data[1]=prop.getProperty("searchTerm2");
		data[2]=prop.getProperty("searchTerm3");
		data[3]=prop.getProperty("searchTerm4");
		return data;
	}
	@Test
	public void verifyFooterLinks() {
		SoftAssert softAssert =new SoftAssert();
		driver.get(prop.getProperty("homepageurl")); //navigating to home page
		LandingPage lp =new LandingPage(driver);
		List<WebElement> links =lp.getAllFooterLinks(); //getting all links in footer
		for(WebElement link:links) { //iterate through all links
			int respCode =testLink(link); //store response code received
			//checking that no exceptions were thrown
			if(respCode==0) softAssert.assertTrue(false,"Exception when opening url: "+link.getText());
			//verify that response code does not indicate an error
			else softAssert.assertTrue(respCode<400, "The link with Text "+link.getText()+" is broken with code "+respCode);
		}
		softAssert.assertAll();
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
