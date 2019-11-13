package com.atmecs.jenkins_parameter.testscripts;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.helper.JavaScriptHelper;
import com.atmecs.jenkins_parameter.helper.SeleniumHelper;
import com.atmecs.jenkins_parameter.helper.ValidaterHelper;
import com.atmecs.jenkins_parameter.helper.WaitForElement;
import com.atmecs.jenkins_parameter.helper.WebTableHelper;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
import com.atmecs.jenkins_parameter.pages.HeatClinicHomePage;
import com.atmecs.jenkins_parameter.testbase.TestBase;
import com.atmecs.jenkins_parameter.utils.ExcelReader;
import com.atmecs.jenkins_parameter.utils.PropertiesReader;

public class TestVerifyHeatClinic extends TestBase{
	WaitForElement waitobject=new WaitForElement();
	LogReporter log=new LogReporter();
	ExcelReader excelread=new ExcelReader();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	PropertiesReader propread=new PropertiesReader();
	JavaScriptHelper javascript=new JavaScriptHelper();
	WebTableHelper webtable=new WebTableHelper();
	HeatClinicHomePage heatclinic=new HeatClinicHomePage();
	
	@BeforeClass
	public void webURLLoader() {
		driver.get(prop.getProperty("heatclinicURL"));
		waitobject.waitForPageLoadTime(driver);
		driver.manage().window().maximize();
	}
	
	@Test(priority=1)
	public void verifyHeatClinic() throws IOException, InterruptedException {
		log.logReportMessage("STEP 1: Starting browser :"+chooser[1]);
		log.logReportMessage("STEP 2: URL is loaed"+ driver.getCurrentUrl());
		Properties prop1=propread.keyValueLoader(FileConstants.HEATCLINICLOCATORS_PATH);
		log.logReportMessage("STEP 3: Validate all the Menu");
			heatclinic.verifyHeatclinicMenu(driver,prop1);
			
		log.logReportMessage("STEP 4: Mouse Over Merchandise and click Men");
			seleniumhelp.mouseOver(driver,prop1.getProperty("loc.menu.merchandise"));
			waitobject.waitForElementToBeClickable(driver, prop1.getProperty("loc.submenu.mens"));
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.submenu.mens"));
			
		log.logReportMessage("STEP 5: Verify the Text 'Viewing Mens'");
			waitobject.waitForElementToBeClickable(driver,prop1.getProperty("loc.txt.viewing"));
			String mensPageTitle=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "MenPageTitle");
			validatehelp.assertValidater(driver.getTitle().toString(),mensPageTitle);
			String expectedViewingText=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Viewing Mens Text");
			String viewingMensText=validatehelp.getText(driver, prop1.getProperty("loc.txt.viewing"));
			validatehelp.assertValidater(viewingMensText,expectedViewingText);
			
		log.logReportMessage("STEP 6: Click the Buy now of Hawt Like a Habanero Shirt (Men's)");
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.btn.buynow"));
			String inputName=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Name");
			heatclinic.handlePopUp(driver, prop1,inputName);
			seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.popupbuynow"));
			
		log.logReportMessage("STEP 7: click the Cart");
			seleniumhelp.clickElement(driver,prop1.getProperty("loc.link.cart"));
			
		log.logReportMessage("STEP 8: Validation of the product");
			String productName=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Product Name");
			heatclinic.validateProduct(driver, prop1,productName);
			String expectedProductPrice=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Price");
			String expectedTotalPrice=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Total");
			heatclinic.verifyGrandTotal(driver, prop1, expectedProductPrice,expectedTotalPrice);
			
		log.logReportMessage("STEP 9: Increase the Qty count");
			WebElement element=waitobject.WaitForFluent(driver, prop1.getProperty("loc.btn.qtyincrease"));
			element.clear();
			String productQty=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "Qty");
			seleniumhelp.sendKeys(prop1.getProperty("loc.btn.qtyincrease"), driver,productQty);
			
		log.logReportMessage("STEP 10: Update the  Cart");
			waitobject.waitForElementToBeClickable(driver, prop1.getProperty("loc.btn.update"));
			seleniumhelp.clickElement(driver, prop1.getProperty("loc.btn.update"));
			String expectedProductPriceAfter=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "PriceAfter");
			String expectedTotalPriceAfter=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0,"Ts-01", "TotalAfter");
			heatclinic.verifyGrandTotal(driver, prop1, expectedProductPriceAfter,expectedTotalPriceAfter);
	}
}
