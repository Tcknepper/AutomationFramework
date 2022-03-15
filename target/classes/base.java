package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class base {

		//static can be added to webdriver if you need to run sequentially
		public WebDriver driver; //global so it is accessible by all test cases
		public Properties prop;
		
		public WebDriver initializeDriver() throws IOException {
		/*initializes the driver for needed driver type based on jenkins parameters
		Can be changed to initialize based on the data.properties file*/
			
			prop =new Properties();
			FileInputStream fis =new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");
			
			prop.load(fis);
			//used for passing Jenkins parameters instead of data.properties. Send command "mvn test -Dbrowser=desiredbrowser"
			String browserName =System.getProperty("browser"); //comment this to run from data.properties
			
			//used for general testing using data.properties file instead of jenkins parameters
			//String browserName =prop.getProperty("browser"); //comment this to run from maven
			
			if(browserName.contains("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\chromedriver.exe");
				ChromeOptions options =new ChromeOptions();
				if(browserName.contains("headless")) options.addArguments("headless"); //check if it should be run in headless mode
				driver = new ChromeDriver(options); //passing options to be implemented in driver
			}
			else if(browserName.contains("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\geckodriver.exe");
				FirefoxOptions options =new FirefoxOptions();
				if(browserName.contains("headless")) options.addArguments("headless"); //check if it should be run in headless mode
				driver = new FirefoxDriver(options); //passing options to be implemented in driver
			}
			else if(browserName.contains("edge")) {
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\msedgedriver.exe");
				EdgeOptions options =new EdgeOptions();
				if(browserName.contains("headless")) options.addArguments("headless"); //check if it should be run in headless mode
				driver = new EdgeDriver(options); //passing options to be implemented in driver
			}
			
			//declare timeouts so it affects all test cases
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return driver;
		}
		
		public int testLink(WebElement link) {
			int respCode; //int to store response code
			String url =link.getAttribute("href"); //getting url address
			HttpURLConnection conn;
			try { //try catch so code runs even if there is an issue with a link
				conn = (HttpURLConnection)new URL(url).openConnection();
				conn.setRequestMethod("HEAD"); //connecting in headless mode
				conn.connect();
				respCode =conn.getResponseCode(); //getting response code
			} catch (MalformedURLException e) {
				return 0; //response code 0 so I know if it was an issue with test and not the website
			} catch (IOException e) {
				return 0; //response code 0 so I know if it was an issue with test and not the website
			}
			return respCode;
		}
}

//code to log in with cookie, does not work when driver is quit and reinitialized after each test
/*public WebDriver addCookies(WebDriver driver) {
			driver.manage().deleteAllCookies();
			driver.manage().addCookie(new Cookie(prop.getProperty("key"),prop.getProperty("value")));
			driver.navigate().refresh();
			return driver;
  }
  @BeforeClass
	public void getCookies() throws IOException {
		driver =initializeDriver();
		driver.get(prop.getProperty("signinpageurl")); //navigating to sign in page
		SignInPage sp =new SignInPage(driver);
		driver.manage().deleteAllCookies(); //deleting cookies
		sp.getEmail().sendKeys(prop.getProperty("email")); //entering email
		sp.getPassword().sendKeys(prop.getProperty("password")); //entering password
		sp.getSignin().click(); //click sign in
		Set<Cookie> cookies =driver.manage().getCookies(); //grabbing cookies
		Cookie s =cookies.iterator().next(); //getting the sign in cookie
		prop.replace("key", s.getName()); //storing cookie key
		prop.replace("value", s.getValue()); //storing cookie value
		driver.manage().deleteAllCookies(); //deleting cookies
	}*/
