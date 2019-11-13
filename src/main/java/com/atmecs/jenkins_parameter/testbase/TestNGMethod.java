package com.atmecs.jenkins_parameter.testbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.extentreport.ExtentReportGenerater;
import com.atmecs.jenkins_parameter.utils.ExcelReader;
import com.atmecs.jenkins_parameter.utils.PropertiesReader;



/**
 * This Class create the dynamic testNG suite file to run the scripts parallel
 * @author ajith.periyasamy
 *
 */
public class TestNGMethod {
	ExcelReader excelread=new ExcelReader();
	PropertiesReader propread=new PropertiesReader();
	public TestNGMethod() {
		excelread.filePathProviderMethod(FileConstants.CLASS_NAME_PATH);
	}
	/**
	 * This XmlSuiteRunner method take the below parameters
	 * generate the xml suite dynamically and run the test script in parallel and non parallel mode using testng
	 * @param classname
	 * @param classstatus
	 * @param websitename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(dataProvider = "testdata", dataProviderClass = ExcelReader.class)
	public void XmlSuiteRunner(String classname, String classstatus,String websitename) throws IOException, ClassNotFoundException {
	

			Properties property = propread.keyValueLoader(FileConstants.CONFIG_PATH);
			List<String> browserList = new ArrayList<String>();
			String[] browserarray = property.getProperty("webdrivername").split(",");
			String[] parallelarray=browserarray[1].split("=");
			String array[]=parallelarray[1].split(":");
			for (String browserName : array) {
				browserList.add(browserName);
			}
			if (classstatus.equalsIgnoreCase("y")) {
			XmlSuite xmlSuite = new XmlSuite();
			xmlSuite.setName("suite");
			xmlSuite.setVerbose(1);
			if(parallelarray[0].equalsIgnoreCase("parallel")) 
			{
			xmlSuite.setParallel(ParallelMode.TESTS);
			}
			xmlSuite.setThreadCount(array.length*5);

			List<XmlSuite> suites = new ArrayList<XmlSuite>();

			for (String browserName : browserList) {

				XmlTest xmlTest = new XmlTest(xmlSuite);
				Map<String, String> parameter = new HashMap<String, String>();
				parameter.put("browser", browserarray[0]+","+browserName);
				parameter.put("url", websitename);
				xmlTest.setParameters(parameter);
				xmlTest.setName("Test Validate " +browserName+classname);
				Class<?> class1 = Class.forName(classname);  
				XmlClass myClass = new XmlClass(class1);
				List<XmlClass> xmlClassList = new ArrayList<XmlClass>();
				xmlClassList.add(myClass);
				xmlTest.setXmlClasses(xmlClassList);

			}

			suites.add(xmlSuite);

			TestNG testng = new TestNG();
			List<Class<? extends ITestNGListener>>listenerClasses=new ArrayList<Class<? extends ITestNGListener>>();
			listenerClasses.add(ExtentReportGenerater.class);
			testng.setListenerClasses(listenerClasses);
			testng.setXmlSuites(suites);
			testng.run();
		}

	}
}