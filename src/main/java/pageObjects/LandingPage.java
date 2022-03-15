package pageObjects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage {

public WebDriver driver;
	//private By  =By.cssSelector("");
	private By signin =By.cssSelector("[class='login']"); //sign in button
	private By contactUs =By.cssSelector("[title='Contact Us']"); //contact us button
	private By searchBar =By.id("search_query_top"); //search bar field
	private By searchButton =By.name("submit_search"); //search button
	private By searchSuggestions =By.cssSelector("li[class*='ac_']"); //dropdown menu of suggested search results
	private By cart =By.cssSelector("[title*='View my']"); //cart button
	private By continuePopup =By.cssSelector("[title='Continue shopping']"); //continue button in the popup after adding items to cart
	private By cartContents =By.cssSelector("[data-id*='cart_block_product']"); //all items that are in the cart dropdown
	private By bestSellersProducts =By.xpath("//ul[@id='blockbestsellers']/li"); //all products featured in best sellers tab
	private By popularProducts =By.xpath("//ul[@id='homefeatured']/li"); //all products featured in popular tab
	private By bestSeller =By.cssSelector("[class='blockbestsellers']"); //best sellers button
	private By popular =By.cssSelector("[class='homefeatured']"); //popular products button
	private By addToCart =By.cssSelector("[class*='ajax_add_to']"); //add to cart button
	private By productName =By.cssSelector("[class='product-name']"); //name of the products
	private By productPrice =By.xpath(".//div[@class='right-block']/div/span"); //price of product
	private By myOrders =By.cssSelector("[title='My orders']"); //My orders link
	private By categoryLinks =By.cssSelector("[class='tree dynamized']>li>a"); //links in footer under category
	private By informationLinks =By.cssSelector("[id*='block_various']>ul>li>a"); //links in footer under information
	private By myAccountLinks =By.cssSelector("[class='bullet']>li>a"); //links in footer under my account
	
	//constructor
	public LandingPage(WebDriver driver) {
		this.driver=driver; //setting the public driver to the parameter driver
	}
	
	//get methods for use in tests
	public SignInPage clickSignin() {
		driver.findElement(signin).click();
		return new SignInPage(driver);
	}
	public ContactUsPage clickContactUs() {
		driver.findElement(contactUs).click();
		return new ContactUsPage(driver);
	}
	public WebElement getSearchBar() {
		return driver.findElement(searchBar);
	}
	public WebElement getSearchButton() {
		return driver.findElement(searchButton);
	}
	public List<WebElement> getSearchSuggestions() {
		return driver.findElements(searchSuggestions); //list to store all elements in search suggestion
	}
	public CartPage clickCart() {
		//only click the element so put code here
		driver.findElement(cart).click();
		return new CartPage(driver);
	}
	public void clickContinuePopup() {
		//only click element so put code here
		driver.findElement(continuePopup).click();
	}
	public List<WebElement> getCartContents() {
		return driver.findElements(cartContents);
	}
	public List<WebElement> getPopularProducts() {
		return driver.findElements(popularProducts);
	}
	public List<WebElement> getBestSellersProducts() {
		return driver.findElements(bestSellersProducts);
	}
	public WebElement getBestSeller() {
		return driver.findElement(bestSeller);
	}
	public WebElement getPopular() {
		return driver.findElement(popular);
	}
	public void clickAddToCart(WebElement product) {
		 product.findElement(addToCart).click();
	}
	public String getProductName(WebElement product) {
		//only need to name as a string so put code here
		return product.findElement(productName).getText();
	}

	public List<String> addItemsToCart(List<WebElement> popularProducts) throws InterruptedException {
		List<String> productNames = new ArrayList<>(); //list for product names
		for(WebElement popularProduct:popularProducts) { //for loop to iterate through each product
			clickAddToCart(popularProduct); //adding item to cart
			clickContinuePopup(); //clicking continue shopping
			productNames.add(getProductName(popularProduct)); //adding product name to list
			Thread.sleep(3000); //sleep to avoid 508 resource limit
		}
		return productNames; //returning product names to test for assertion
	}
	public float getProductPrice(WebElement product) {
		//only need the price as a float from this element so I put the code here
		return Float.parseFloat((product.findElement(productPrice).getText()).substring(1)); //trimming $
	}

	public float getTotalPrice(List<WebElement> popularProducts) {
		float totalPrice =0;
		for(WebElement popularProduct:popularProducts) { //for loop to iterate through products
			totalPrice+=getProductPrice(popularProduct); //adding price of each product to get the total price
		}
		DecimalFormat df = new DecimalFormat("###.##");
		totalPrice =Float.parseFloat(df.format(totalPrice)); //rounding to 2 decimal places
		return totalPrice; //returning totalPrice to test for assertion
	}
	public OrderHistoryPage clickMyOrders() {
		//only click the element so put code here
		driver.findElement(myOrders).click();
		return new OrderHistoryPage(driver);
	}
	public WebElement getCategoryLinks() {
		return driver.findElement(categoryLinks);
	}
	public List<WebElement> getInformationLinks() {
		return driver.findElements(informationLinks);
	}
	public List<WebElement> getMyAccountLinks() {
		return driver.findElements(myAccountLinks);
	}
	public List<WebElement> getAllFooterLinks() {
		List<WebElement> links = getInformationLinks();
		links.addAll(getMyAccountLinks());
		links.add(getCategoryLinks());
		return links;
	}
}
