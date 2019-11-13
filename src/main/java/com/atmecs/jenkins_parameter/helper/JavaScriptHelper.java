package com.atmecs.jenkins_parameter.helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * This JavaScriptHelper class contains the method to perform the operation 
 * of click,send keys,get text and etc.
 * @author ajith.periyasamy
 *
 */
public class JavaScriptHelper {
	WaitForElement waitobject=new WaitForElement();
	/**
	 * This javascriptClickElement method take the below parameters 
	 * and get the element of the locator and perform click operation using the Javascript executor 
	 * @param driver
	 * @param locator
	 */
	public void javascriptClickElement(WebDriver driver,String locator) {
		JavascriptExecutor javascript=(JavascriptExecutor) driver;
		WebElement element=waitobject.WaitForFluent(driver, locator);
		javascript.executeScript("arguments[0].click()", element);
	}
	/**
	 * This javascriptGetTitle method take the below parameter
	 * @param driver
	 * get the document title using the javascript executor and 
	 * @return the title  
	 */
	public String javascriptGetTitle(WebDriver driver) {
		JavascriptExecutor javascript=(JavascriptExecutor) driver;
		String title=javascript.executeScript("return document.title").toString();
		return title;
	}
	/**
	 * This javascriptGetURL method take the below parameter
	 * @param driver
	 * get the page URL using the javascript executor and 
	 * @return the URL
	 */
	public String javascriptGetURL(WebDriver driver) {
		JavascriptExecutor javascript=(JavascriptExecutor) driver;
		String pageUrl=javascript.executeScript("return document.URL").toString();
		return pageUrl;
	}
	/**
	 * This javascriptSendKeys method take the below parameters
	 * and find the element send the value to the specific element and perform using the javascript executor
	 * @param driver
	 * @param locator
	 * @param textvalue
	 */
	public void javascriptSendKeys(WebDriver driver,String locator,String textvalue) {
		JavascriptExecutor javascript=(JavascriptExecutor) driver;
		WebElement element=waitobject.WaitForFluent(driver, locator);
		javascript.executeScript("arguments[0].value='xxx';".replace("xxx", textvalue), element);
		element.sendKeys(Keys.TAB);
	}
	/**
	 * This javascriptGetText take the below parameters
	 * and get the inner text of the HTML elements and return the text
	 * @param driver
	 * @param locator
	 * @return inner text
	 */
	public String javascriptGetText(WebDriver driver,String locator) {
		JavascriptExecutor javascript=(JavascriptExecutor) driver;
		WebElement element=waitobject.WaitForFluent(driver, locator);
		String innertextofelement=javascript.executeScript("return arguments[0].innerHTML;", element).toString();
		return innertextofelement;
	}
	
	
}
