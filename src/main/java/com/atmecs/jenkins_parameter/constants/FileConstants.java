package com.atmecs.jenkins_parameter.constants;

import java.io.File;

/**
 *  File constants are created for the user can access the file constants
 *  and use that file path constants to perform read and,write data from
 *  any type of the file.
 * @author ajith.periyasamy
 */
public class FileConstants {
	public static int IMPLICIT_WAIT=20;
	public static int EXPLICIT_WAIT=20;
	public static int FLUENT_WAIT=30;
	public static int FLUENT_POLL=5;
	public static int PAGE_LOAD_TIME=10;
	
	public final static String USER_HOME= System.getProperty("user.dir")+File.separator;
	public static final String CONFIG_PATH =USER_HOME+"config.properties";

	public static final String LOG4J_CONFIG_PROPERTY_PATH =USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"log4j"+File.separator+"log4j.properties";

	public static final String EXTENT_OUPUT_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"extent"+File.separator+"extent.html";
	public static final String SCREENSHOT_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"extent"+File.separator+"screenshot"+File.separator;
	public static final String EXTENT_CONFIG_PATH =USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"extent"+File.separator+"extent-config.xml";

	public static final String CLASS_NAME_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testdata"+File.separator+"classname.xlsx";
	public static final String NINJATESTDATA_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testdata"+File.separator+"ninjaStore.xlsx";
	public static final String HEATTESTDATA_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"testdata"+File.separator+"heatClinic.xlsx";
	
	public static final String CHROME_DRIVER_PATH =USER_HOME+"libs"+File.separator+"chromedriver.exe";
	public static final String FIREFOX_DRIVER_PATH =USER_HOME+"libs"+File.separator+"geckodriver.exe";
	public static final String IE_DRIVER_PATH=USER_HOME+"libs"+File.separator+"IEDriverServer.exe";
	public static final String EDGE_DRIVER_PATH=USER_HOME+"libs"+File.separator+"msedgedriver.exe";
	
	public static final String NINJALOCATORS_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"locators"+File.separator+"ninjaStore.properties";
	public static final String HEATCLINICLOCATORS_PATH=USER_HOME+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"locators"+File.separator+"heatClinic.properties";
}
