package com.atmecs.jenkins_parameter.testbase;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
import com.atmecs.jenkins_parameter.utils.PropertiesReader;

/**
 * @author ajith.periyasamy
 * This Class is extend from the Extent class and class contains the browser selection method.
 * baseSetup method is used to select the script run in the Selenium grid or user system.
 */
public class TestBase{
	public WebDriver driver;
	protected Properties prop=null;
	PropertiesReader propertyReader = new PropertiesReader();
	LogReporter log=new LogReporter();
	protected String[] chooser;
	/**
	 * This baseSetup method will provide the option to choose the browser driver to run the script.
	 *  The configuration details is read from the config.properties file.
	 *  and this browser details passed to the switch case and based on the configuration details script is running in different browsers. 
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	@BeforeTest
	@Parameters("Browser")
	public void baseSetup(String Browser) throws IOException, InstantiationException, IllegalAccessException {
		prop = propertyReader.keyValueLoader(FileConstants.CONFIG_PATH);
		//chooser=prop.getProperty("webdrivername").split(",");
		//chooser=System.getenv("webdrivername").split(",");
		chooser=Browser.split(",");
		if(chooser[0].equals("GRID")) {
			String Node = "http://192.168.1.114:4444/wd/hub";
			switch (chooser[1]) {
			case "CHROME":
				System.out.println("Executing on chrome");
				DesiredCapabilities chromecapabilities =new DesiredCapabilities();
				chromecapabilities.setBrowserName("chrome");
				chromecapabilities.setCapability("--disable-notifications",true);
				chromecapabilities.setCapability("disable-geolocation",true);
				driver = new RemoteWebDriver(new URL(Node), chromecapabilities);
				break;
			case "FIREFOX":
				System.out.println("Executing on FireFox");
				DesiredCapabilities firefoxcapabilities = new DesiredCapabilities();
				firefoxcapabilities.setBrowserName("firefox");
				firefoxcapabilities.setCapability("dom.webnotifications.enabled", false);
				driver = new RemoteWebDriver(new URL(Node), firefoxcapabilities);
				break;
			case "IE":
				System.out.println("Executing on IE");
				DesiredCapabilities iecapabilities = new DesiredCapabilities();
				iecapabilities.setBrowserName("internet explorer");
				iecapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL," ");
				iecapabilities.setCapability("requireWindowFocus", true);
				iecapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				driver = new RemoteWebDriver(new URL(Node), iecapabilities);
				break;
			}
			//driver.get(url);
			//ThreadPool.setDriver(driver);
		}
		else {
			switch (chooser[1]) {
			case "CHROME":
				System.setProperty("webdriver.chrome.driver", FileConstants.CHROME_DRIVER_PATH);
				ChromeOptions chromeoptions = new ChromeOptions();
				chromeoptions.addArguments("--disable-notifications");
				chromeoptions.addArguments("disable-geolocation");
				driver = new ChromeDriver(chromeoptions);
				break;
			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", FileConstants.FIREFOX_DRIVER_PATH);
				FirefoxOptions firefoxoptions = new FirefoxOptions();
				firefoxoptions.addPreference("dom.webnotifications.enabled", false);
				driver = new FirefoxDriver(firefoxoptions);
				break;
			case "IE":
				System.setProperty("webdriver.ie.driver", FileConstants.IE_DRIVER_PATH);
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, " ");
				capabilities.setCapability("requireWindowFocus", true);  
				capabilities.setVersion("3.14.0.0");
				capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				driver=new InternetExplorerDriver(capabilities);
				break;
			case "EDGE":
				System.setProperty("webdriver.edge.driver", FileConstants.EDGE_DRIVER_PATH);
				driver = new EdgeDriver();
				break;
			}
			//driver.get(url);
			//ThreadPool.setDriver(driver);
		}
	}
	/**
	 * This endDriver method call the driver quit method.
	 */
	@AfterTest
	public void endDriver() 
	{
		driver.quit();
	}
}
