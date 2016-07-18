package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.utility.Constants;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Time;


public class LogIn implements Constants{
	Time timeobject = new Time();
	LoadPages pageObject = new LoadPages();
	XlsDataProvider xls=new XlsDataProvider("AddNote.xls","AddNote");
	ReportLogService report = new ReportLogServiceImpl(LogIn.class);
	
	public void testLogIn(AppiumDriver driver) throws IOException {

		Properties page = pageObject.getObjectRepository("login.properties");
		
		
		//Tap on login button 
		driver.findElementById(page.getProperty("loginButton")).click();
		report.info("Tap on the login Button for entering username and password");
		report.info("");

		//Entering user name	
		report.info("Add Username in Email text field on login page");
		report.info("");
		driver.findElementById(page.getProperty("userName")).sendKeys(xls.getByRow("User Name", 1));
		
        report.info("Add password in Password text field on log in page");
        report.info("");
        driver.findElementById(page.getProperty("password")).sendKeys(xls.getByRow("Password", 1));
		
		//Tap on log button for home page
		report.info("Tap on log in button to logged in application");
		report.info("");
		driver.findElementById(page.getProperty("loginButtonForHome")).click();
		
		
		timeobject.waitForVisible(By.name("Home"), driver);
       VerificationManager.verifyString(driver.findElementByName("Home").getText(), "Home", "Verifying user is successfully logged in or not");
       report.info("");
	}
}
