package com.atmecs.jenkins_parameter.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.helper.JavaScriptHelper;
import com.atmecs.jenkins_parameter.helper.SeleniumHelper;
import com.atmecs.jenkins_parameter.helper.ValidaterHelper;
import com.atmecs.jenkins_parameter.helper.WaitForElement;
import com.atmecs.jenkins_parameter.helper.WebTableHelper;
import com.atmecs.jenkins_parameter.logreports.LogReporter;
import com.atmecs.jenkins_parameter.utils.ExcelReader;


public class HeatClinicHomePage {
	LogReporter log = new LogReporter();
	WaitForElement waitobject = new WaitForElement();
	ExcelReader excelread = new ExcelReader();
	JavaScriptHelper javascript = new JavaScriptHelper();
	ValidaterHelper validatehelp = new ValidaterHelper();
	SeleniumHelper seleniumhelp = new SeleniumHelper();
	WebTableHelper webtable=new WebTableHelper();

	public void verifyHeatclinicMenu(WebDriver driver, Properties prop) throws IOException {
		String[] heatClinicData = excelread.excelDataProviderArray(FileConstants.HEATTESTDATA_PATH, 1, "Titles");
		String[] locatorData = excelread.excelDataProviderArray(FileConstants.HEATTESTDATA_PATH, 1, "Locators");
		for (int count = 0; count < heatClinicData.length; count++) {
			seleniumhelp.clickElement(driver, prop.getProperty(locatorData[count]));
			validatehelp.assertValidater(driver.getTitle(),heatClinicData[count]);
		}
	}
	public void handlePopUp(WebDriver driver, Properties prop, String inputname) {
		seleniumhelp.sendKeys(prop.getProperty("loc.txtfield.name"), driver, inputname);
		seleniumhelp.clickElement(driver, prop.getProperty("loc.btn.color"));
		seleniumhelp.clickElement(driver, prop.getProperty("loc.btn.size"));
	}

	public void validateProduct(WebDriver driver,Properties prop,String expectedProductName) throws IOException { 
		String[] columnName= {"Size","Name","Color"};
		String productName=validatehelp.getText(driver, prop.getProperty("loc.txt.name"));
		String[] productNameArray=productName.split("\n");
		validatehelp.assertValidater(productNameArray[0], expectedProductName);
		int initial=0;
		for(int count=1; count<=3; count++) {
			String productDetails=validatehelp.getText(driver, prop.getProperty("loc.txt.proddetails").replace("xxx", ""+count));
			String[] productDetailsArray=productDetails.split(":");
			String expectedProductDetails=excelread.getCellData(FileConstants.HEATTESTDATA_PATH, 0, "Ts-01", columnName[initial]);
			validatehelp.assertValidater(productDetailsArray[1],expectedProductDetails);
			initial++;
		}
	}
	public void verifyGrandTotal(WebDriver driver,Properties prop,String expectedProductPrice,String expectedTotalPrice) {
		String productPrice=validatehelp.getText(driver, prop.getProperty("loc.txt.price"));
		validatehelp.assertValidater(productPrice.substring(1, productPrice.length()),expectedProductPrice);
		String productTotalPrice=validatehelp.getText(driver, prop.getProperty("loc.txt.totalprice"));
		validatehelp.assertValidater(productTotalPrice.substring(1,productTotalPrice.length()),expectedTotalPrice);
		
	}
}
