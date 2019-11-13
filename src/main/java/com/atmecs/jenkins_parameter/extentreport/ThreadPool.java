package com.atmecs.jenkins_parameter.extentreport;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
/**
 * This TreadPoll class get the driver object and test report object and setting the value to the extent report 
 * @author ajith.periyasamy
 *
 */
public class ThreadPool {
	
	private static ThreadPool thisPool = new ThreadPool();

	public final ThreadLocal<WebDriver> userThreadLocal = new ThreadLocal<WebDriver>();
	public final ThreadLocal<ExtentTest> extentThreadLocal = new ThreadLocal<ExtentTest>();
	/**
	 * This getTest method is get the Test object from the  extent class
	 *  and @return the object
	 */
	public static ExtentTest getTest() {
		return thisPool.extentThreadLocal.get();
	}
	/**
	 * This setTest method is set the test object to the ThreadPool Extent test object 
	 *  
	 */
	public static void setTest(ExtentTest test) {

		thisPool.extentThreadLocal.set(test);
	}
	/**
	 * This getDriver method is get the driver object from the Testbase 
	 *  and @return the object
	 */
	public static WebDriver getDriver() {
		return thisPool.userThreadLocal.get();
	}
	/**
	 * This  setDriver method is set the  WebDriver object to the ThreadPool driver
	 *
	 */
	public static void setDriver(WebDriver dInfo) {

		thisPool.userThreadLocal.set(dInfo);
	}
}
