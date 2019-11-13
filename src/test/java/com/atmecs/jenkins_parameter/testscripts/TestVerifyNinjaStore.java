package com.atmecs.jenkins_parameter.testscripts;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.helper.JavaScriptHelper;
import com.atmecs.jenkins_parameter.helper.SeleniumHelper;
import com.atmecs.jenkins_parameter.helper.ValidaterHelper;
import com.atmecs.jenkins_parameter.helper.WaitForElement;
import com.atmecs.jenkins_parameter.helper.WebTableHelper;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
import com.atmecs.jenkins_parameter.pages.NinjaStroreHomePage;
import com.atmecs.jenkins_parameter.testbase.TestBase;
import com.atmecs.jenkins_parameter.utils.ExcelReader;
import com.atmecs.jenkins_parameter.utils.PropertiesReader;



public class TestVerifyNinjaStore extends TestBase {
	WaitForElement waitobject=new WaitForElement();
	LogReporter log=new LogReporter();
	ExcelReader excelread=new ExcelReader();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	PropertiesReader propread=new PropertiesReader();
	NinjaStroreHomePage ninjapage=new NinjaStroreHomePage();
	JavaScriptHelper javascript=new JavaScriptHelper();
	WebTableHelper webtable=new WebTableHelper();
	
	@BeforeClass
	public void webURLLoader() {
		driver.get(prop.getProperty("ninjastoreURL"));
		waitobject.waitForPageLoadTime(driver);
		driver.manage().window().maximize();
		log.logReportMessageBase("STEP 2: URL is loaed"+ driver.getCurrentUrl());
	}
	@Test(priority=2)
	public void verifyNinjaStore() throws IOException, InterruptedException
	{
		Properties prop1=propread.keyValueLoader(FileConstants.NINJALOCATORS_PATH);
		log.logReportMessage("STEP 3: Validating the Home Page Title");
				String expectedPageTitle=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 0, "Ts-01", "Home Page Title");
				validatehelp.assertValidater(driver.getTitle(),expectedPageTitle);
				
		log.logReportMessage("STEP 4: Changing the currency to Dollar");
				ninjapage.verifyNinjaStoreProduct(driver,prop1);
				
		log.logReportMessage("STEP 13: Click the Cart link");
				seleniumhelp.clickElement(driver,prop1.getProperty("loc.btn.cart"));
				log.logReportMessage("Successfully navigate to the cart");
				
		log.logReportMessage("STEP 14: Validate the cart Product");
				String iphoneText=validatehelp.getText(driver, prop1.getProperty("loc.txt.iphone"));
				String expectedIphoneText=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 0, "Ts-01", "iPhone Name");
				validatehelp.assertValidater(iphoneText,expectedIphoneText);
				String macBookText=validatehelp.getText(driver, prop1.getProperty("loc.txt.imacbook"));
				String expectedMacBookText=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 0, "Ts-01", "Mac Book Name");
				validatehelp.assertValidater(macBookText,expectedMacBookText);
				
		log.logReportMessage("STEP 15: Validate the Grand Total Before");
				String grandTotal=validatehelp.getText(driver, prop1.getProperty("loc.txt.grandtotal"));
				String expectedGrandTotal=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 0, "Ts-01", "Grand Total");
				validatehelp.assertValidater(grandTotal.substring(1, grandTotal.length()),expectedGrandTotal);
				
		log.logReportMessage("STEP 16: Remove the Product From the Cart");
				seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.remove"));
				log.logReportMessage("Product is Removed");
				
		log.logReportMessage("STEP 17: Validate the Grand Total After");
				waitobject.waitForInvisibilityOfElementLocated(driver, prop1.getProperty("loc.spinner.visibility"));
				String grandTotalAfter=validatehelp.getText(driver, prop1.getProperty("loc.txt.grandtotal"));
				String expectedTotalAfter=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 0, "Ts-01", "After Grand Total");
				validatehelp.assertValidater(grandTotalAfter.substring(1, grandTotalAfter.length()),expectedTotalAfter);
	}
}
