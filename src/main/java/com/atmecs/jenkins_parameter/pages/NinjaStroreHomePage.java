package com.atmecs.jenkins_parameter.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.helper.JavaScriptHelper;
import com.atmecs.jenkins_parameter.helper.SeleniumHelper;
import com.atmecs.jenkins_parameter.helper.ValidaterHelper;
import com.atmecs.jenkins_parameter.helper.WaitForElement;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
import com.atmecs.jenkins_parameter.utils.ExcelReader;


public class NinjaStroreHomePage {
	LogReporter log=new LogReporter();
	WaitForElement waitobject=new WaitForElement();
	ExcelReader excelread=new ExcelReader();
	JavaScriptHelper javascript=new JavaScriptHelper();
	ValidaterHelper validatehelp=new ValidaterHelper();
	SeleniumHelper seleniumhelp=new SeleniumHelper();
	public void verifyNinjaStoreProduct(WebDriver driver,Properties prop) throws InterruptedException, IOException {
		for(int count=1; count<=2; count++) {
			waitobject.waitForImplicit(driver);
		log.logReportMessage("STEP 5: Searching the Product");
			WebElement inputElement=waitobject.WaitForFluent(driver,prop.getProperty("loc.txtfield.search"));
			inputElement.clear();
			String searchInput=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Search Input");
			seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.search"), driver, searchInput);
			seleniumhelp.clickElement(driver,prop.getProperty("loc.btn.search"));
			log.logReportMessage("Product name "+searchInput+" is entered");
			
		log.logReportMessage("STEP 6: Select the Product");
			seleniumhelp.scrollPageMethod(driver,prop.getProperty("loc.imglink.iphone") );
			seleniumhelp.clickElement(driver, prop.getProperty("loc.imglink.iphone"));
			log.logReportMessage("Product is selected");
			
		log.logReportMessage("STEP 7: Validate the the  Product Availability");
			String availability=validatehelp.getText(driver, prop.getProperty("loc.txt.prodavailability"));
			String[] availabiltyArray=availability.split(":");
			String expectedAvailability=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Availability");
			validatehelp.assertValidater(availabiltyArray[1], expectedAvailability);
			
		log.logReportMessage("STEP 8: Validate the the  Product Price");
			String price=validatehelp.getText(driver, prop.getProperty("loc.txt.prodprice"));
			price=price.substring(1,price.length());
			String expectedPrice=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Price");
			validatehelp.assertValidater(price, expectedPrice);
			
		log.logReportMessage("STEP 9: Validate the the  Product Ex Tax");
			String extax=validatehelp.getText(driver, prop.getProperty("loc.txt.exprice"));
			String[] exTaxArray=extax.split(":");
			exTaxArray[1]=exTaxArray[1].substring(2,price.length());
			String expectedExTax=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Ex Tax");
			validatehelp.assertValidater(exTaxArray[1], expectedExTax);
			
		log.logReportMessage("STEP 10: Validate the the  Product Description");
			String description=validatehelp.getText(driver, prop.getProperty("loc.txt.proddiscription"));
			String expectedDescription=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Description");
			validatehelp.assertValidater(description, expectedDescription);
			
		log.logReportMessage("STEP 11: Change the Quantity");
			WebElement element=waitobject.WaitForFluent(driver, prop.getProperty("loc.txtfield.qty"));
			element.clear();
			String quantity=excelread.getCellData(FileConstants.NINJATESTDATA_PATH, 1, "Ts-0"+count, "Qty");
			seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.qty"), driver,quantity );
			
		log.logReportMessage("STEP 12: Click Add to Cart Button");
			javascript.javascriptClickElement(driver,prop.getProperty("loc.btn.addtocart") );
		}
	}
}

