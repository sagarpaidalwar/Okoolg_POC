package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.testfunction.GotoLeftMenu;
import com.atmecs.glooko.testfunction.TrimString;
import com.atmecs.glooko.utility.Constants;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;
import com.atmecs.glooko.utility.ReadExcel;

public class VerifyFoodAndMedicine implements Constants{
	
	ReportLogService report = new ReportLogServiceImpl(VerifyFoodAndMedicine.class);
	TapOnDayView tapOnDayView;
	LoadPages pageObject = new LoadPages();
	TrimString trimStringobj = new TrimString();
	GotoLeftMenu leftmenu = new GotoLeftMenu();
	Swipe swipeObject=new Swipe();
	
	
public void verifyFoodAndMedicine(boolean swipe,AppiumDriver<MobileElement> driver) throws IOException, InterruptedException {
		
		Log.startTestCase("Verify Food and Medicine Quntity from graph to the History ");
		
		Properties page = pageObject.getObjectRepository("dayViewId.properties");
		// Go to left menu
		leftmenu.leftMenu(driver);
		report.info("Tap on left menu");
		VerificationManager.verifyString("History", driver.findElementByName("History").getText(), "Not navigate to the left menu");
		
		// Go to Day View
		tapOnDayView = new TapOnDayView();
		tapOnDayView.tapOnDayview(driver);
		report.info("Tap on Day View in left side menu");
		//VerificationManager.verifyString("", "", "");
		
		//Swipe to given date
		if(swipe)
		{

			Thread.sleep(WAIT_TIME);
			swipeObject.swipeLeftToRight(driver);
		 }
			
		
		
		// Tap on food and medicine in history of day view page
		WebElement medicineByHistory = driver.findElementById(page.getProperty("historyListView"));
		medicineByHistory.findElement(By.id(page.getProperty("medicineByHistory"))).click();
		report.info("Tap on the Food and medicine below the graph");
		
        // Put all the same id's of item in list
		MobileElement graphScrubber = driver.findElement(By.id(page.getProperty("GraphScrubberId")));
		List<MobileElement> foodAndInsulinvalue = graphScrubber.findElements(By.id(page.getProperty("valueId")));

		// Get the medicine quntity from graph
		String medicineQuntityByGraph = foodAndInsulinvalue.get(0).getText();
		report.info("Get the medicine quantity from the graph: "+medicineQuntityByGraph);
		
		// Get the cabs quntity from graph
		String foodQuntityByGraph = foodAndInsulinvalue.get(1).getText();
		report.info("Get the carbs Quantity from graph: "+foodQuntityByGraph);
		
		// Get the Medicine quantity from history
		driver.findElementById(page.getProperty("medicineNameIdByHistory")).getText();
		String medicineQuntityByHistory = driver.findElementById(page.getProperty("medicineQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("medicineTimeIdByHistory")).getText();
		report.info("Get the Medicine quantity from history");
		
		// Get the Food quantity from history
		driver.findElementById(page.getProperty("foodNameIdByHistory")).getText();
		String foodQuntityByHistory = driver.findElementById(page.getProperty("foodQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("foodTimeIdByHistory")).getText();
		report.info("Get the Food quantity from history");
		
		// Cut the String to match the food and medicine Quntity
		String medicineQuntityFromHistory = trimStringobj.trimString(medicineQuntityByHistory);
		String foodQuntityFromHistory = trimStringobj.trimString(foodQuntityByHistory);

		// Verify medicine content matches or not
		report.info("Verifying Food and Medicine Quntity between graph and history ");
		
		report.info("Medicine Quantity From history: "+medicineQuntityFromHistory);
		report.info("Medicine Quantity From Graph: "+medicineQuntityByGraph);
		VerificationManager.verifyString(medicineQuntityFromHistory, medicineQuntityByGraph, "Medicine Quantity not matches with graph");
		
		report.info("Food Quantity From history: "+foodQuntityFromHistory);
		report.info("Food Quantity From Graph: "+foodQuntityByGraph);
		VerificationManager.verifyString( foodQuntityFromHistory, foodQuntityByGraph,"Food Quantity not matches with graph");
	}
	

}
