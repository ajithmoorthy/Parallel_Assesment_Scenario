package com.atmecs.jenkins_parameter.testbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.atmecs.jenkins_parameter.constants.FileConstants;
import com.atmecs.jenkins_parameter.utils.ExcelReader;
import com.atmecs.jenkins_parameter.utils.PropertiesReader;

public class TestNGPrallel 
{
	ExcelReader excelread=new ExcelReader();
	PropertiesReader propread=new PropertiesReader();
	public List<XmlSuite> suiteXml(String[] className,String[] activeStatus,String[] browserArray) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setName("mysuite");
		xmlSuite.setParallel(ParallelMode.TESTS);
		xmlSuite.setThreadCount(className.length*10);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		for(int classNameIndex=0; classNameIndex<className.length; classNameIndex++) 
		{
			if(activeStatus[classNameIndex].equals("TRUE")) {
				String[] browserMode=browserArray[classNameIndex].split(",");
				String browser[]=browserMode[1].split(":");
				for (int browserCount=0; browserCount<browser.length; browserCount++) 
				{
					XmlTest xmlTest1 = new XmlTest(xmlSuite);
					Map<String, String> parameter1 = new HashMap<String, String>();
					parameter1.put("Browser", browserMode[0]+","+browser[browserCount]);
					xmlTest1.setParameters(parameter1);
					xmlTest1.setName("Test validate "+browser[browserCount]+className[classNameIndex]);
					Class<?> class1 = Class.forName(className[classNameIndex]);  
					XmlClass myClass = new XmlClass(class1);
					List<XmlClass> xmlClassList1 = new ArrayList<XmlClass>();
					xmlClassList1.add(myClass);
					xmlTest1.setXmlClasses(xmlClassList1);
				}
			}
			}
			suites.add(xmlSuite);
			return suites;
		}
		@Test
		public void xmlsuite() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
			String[] classes=excelread.excelDataProviderArray(FileConstants.CLASS_NAME_PATH, 0, "ClassName");
			String[] activestatus=excelread.excelDataProviderArray(FileConstants.CLASS_NAME_PATH, 0, "ActiveStatus");
			String[] browserarray=excelread.excelDataProviderArray(FileConstants.CLASS_NAME_PATH, 0, "Browsername");
			List<XmlSuite> suites = suiteXml(classes,activestatus,browserarray);
			TestNG testng = new TestNG();
			testng.setXmlSuites(suites);
			testng.run();
		}

	}
