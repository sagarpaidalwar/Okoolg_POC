package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Time;

public class LogOut {
	Time timeobject = new Time();
	LoadPages pageObject = new LoadPages();
	XlsDataProvider xls=new XlsDataProvider("AddNote.xls","AddNote");
	ReportLogService report = new ReportLogServiceImpl(LogIn.class);
	
	public void testLogOut(AppiumDriver driver) throws IOException, InterruptedException
	{
		Properties page = pageObject.getObjectRepository("logout.properties");
		report.info("App is going to log out");
		report.info("");
		
		//Tap on left menu
		report.info("Tap on left menu");
		report.info("");
		driver.findElementByClassName(page.getProperty("leftmenu")).click();
		
		//Tap on settings in left menu
		report.info("Tap on Settings from left menu ");
		report.info("");
		driver.findElementByName("Settings").click();
		
		//Tap on account button
		report.info("Tap on accounts on Settings Screen");
		report.info("");
		driver.findElementById(page.getProperty("account_button")).click();
		
		//Tap on Logout button
		report.info("Tap on Log out button on Account screen");
		report.info("");
		driver.findElementById(page.getProperty("log_out_button")).click();
		
		//Tap on yes button to log out
		report.info("Tap on yes button on pop up log out message screen");
		report.info("");
		driver.findElementByName("Yes").click();
		timeobject.waitForVisible(By.id(page.getProperty("loginID")), driver);
		
	}
}
