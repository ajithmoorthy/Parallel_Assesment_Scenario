package com.atmecs.jenkins_parameter.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
/**
 * This ValidaterHelper class extent from the Extent class and 
 * this class contains the assertion validation method for the web elements, time convertion methods
 * no of elements finder method and month number finder.
 * @author ajith.periyasamy
 *
 */
public class ValidaterHelper {
	LogReporter log=new LogReporter();
	WaitForElement wait=new WaitForElement();
	By by;
	/**
	 * This textOfElement method take below parameters
	 * @param webdriver
	 * @param locator
	 * get the text of the element  
	 *  and @return the content string.
	 */
	public String getText(WebDriver webdriver,String locator) {
		String content = null;
		try {
			WebElement element=wait.WaitForFluent(webdriver, locator);
			content=element.getText();
		}catch (Exception e) {
			System.out.println("Element is not available or not clickable");
		}
		return content;
	}
	/**
	 * This matchElement method take input as below parameters:
	 * @param locators
	 * and perform the separate the locators and options.
	 * using that locators create the Object of By class
	 * and @return by object.
	 */
	public By matchElement(String locators) {
		String[] locatortypearray=locators.split(",");
		switch(locatortypearray[0].toUpperCase())
		{
		case "XPATH":
			by=By.xpath(locatortypearray[1]);
			break;
		case "ID":
			by=By.id(locatortypearray[1]);
			break;
		case "NAME":
			by=By.name(locatortypearray[1]);
			break;
		case "CSS":
			by=By.cssSelector(locatortypearray[1]);
			break;
		case "CLASS":
			by=By.className(locatortypearray[1]);
			break;
		case "LINK_TEXT":
			by=By.linkText(locatortypearray[1]);
			break;
		case "PARTIAL_LINK_TEXT":
			by=By.partialLinkText(locatortypearray[1]);
			break;
		case "TAG_NAME":
			by=By.tagName(locatortypearray[1]);
			break;
		}
		return by;
	}
	/**
	 * This webElementsValidater method take the below parameters
	 * @param driver
	 * @param locators
	 * @param footerarray
	 * and validate the each web elements is present or not using assertions.
	 */
	public void footerValidater(WebDriver driver,String locators,String[] footerarray) {
		wait.waitForElementToBeClickable(driver, locators);
		List<WebElement> list=driver.findElements(matchElement(locators));
		int count=0;
		while(count<1) {
			for(WebElement element:list)
			{
				String[] elementarray=element.getText().split("\n");
				for(int variable=0; variable<elementarray.length; variable++) {
					assertValidater(elementarray[variable],footerarray[count]);
					count++;
				}
			}
		}
	}
	/**
	 * This assertValidater method take the below parameters
	 * @param actual
	 * @param expected
	 * and check the actual and expected are equal or not by using the assertion.
	 */
	public void assertValidater(String actual,String expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");	
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
		}
	}
	/**
	 * This contains Validations take the below parameters
	 * @param webdriver
	 * @param locator
	 * @param expected
	 * and check the element content and expected value using the if content is equal 
	 *  then @return boolean variable bool true.
	 */
	public boolean containsValidater(WebDriver webdriver,String locator,String expected) {
		boolean bool=false;
		WebElement element=webdriver.findElement(matchElement(locator));
		String content=element.getText();
		if(content.contains(expected))
		{
			bool=true;
		}
		return bool;
	}
	/**
	 * This assertValidater method take the below parameters
	 * @param actual
	 * @param expected
	 * and check the actual and expected are equal or not by using the assertion.
	 */
	public void assertValidater(boolean actual,boolean expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated successfully");
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
		}
	}
	/**
	 * This timeConverter method take the below parameters 
	 * @param driver
	 * @param locator
	 * find the no of days between current date to review date for each review and stored in array.  
	 *  finally @return the array.
	 * @throws ParseException
	 */
	public int[] timeConverter(WebDriver driver,String locator) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		wait.waitForElementToBeClickable(driver, locator);
		List<WebElement> list=driver.findElements(matchElement(locator));
		int count=0;
		int[] datearray=new int[(list.size())];
		for(WebElement element:list) {
			if(count<list.size()) {
				String[] tempdate=element.getText().split(" ");	
				String exam=tempdate[1]+"/"+getMonthNumber(tempdate[2])+"/"+tempdate[3];
				exam=exam.replace("\\s", "");
				String secon=formatter.format(date);
				Date first=formatter.parse(exam);
				Date second=formatter.parse(secon);
				long numdays=daysBetween(first,second);
				if(numdays>90) {
					System.out.println(numdays);
					datearray[count]=(int) numdays;
				}
				count++;
			}
		}
		return datearray;
	}
	/**
	 * This  daysBetween method take the two dates:
	 * @param one
	 * @param two
	 * Convert the difference between two dates 
	 *  and @return the difference between two dates
	 */
	private static long daysBetween(Date one, Date two) {
		long difference =  (one.getTime()-two.getTime())/86400000;
		return Math.abs(difference);
	}
	/**
	 * This getMonthNumber method take the below parameter
	 * @param monthName
	 * Covert the monthName to number
	 * and @return integer value of the month.
	 */
	private int getMonthNumber(String monthName) {
		return Month.valueOf(monthName.toUpperCase()).getValue();
	}
}
