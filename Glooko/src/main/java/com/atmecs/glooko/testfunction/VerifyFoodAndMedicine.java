package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.utility.Constants;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Time;

public class VerifyFoodAndMedicine implements Constants{
	
	ReportLogService report = new ReportLogServiceImpl(VerifyFoodAndMedicine.class);
	LoadPages pageObject = new LoadPages();
	TrimString trimStringobj = new TrimString();
	Swipe swipeObject=new Swipe();
	Time timeObject = new Time();
	XlsDataProvider xls=new XlsDataProvider("AddNote.xls","AddNote");
	
public void verifyFoodAndMedicine(boolean swipe,AppiumDriver<MobileElement> driver,int rowNo) throws IOException, InterruptedException {
		
		
		Properties page = pageObject.getObjectRepository("dayViewId.properties");
		
		// Go to left menu
		driver.findElementByClassName( page.getProperty("imageButton")).click();
		report.info("Tap on left menu");
		report.info(" ");
		timeObject.waitForVisible(By.xpath(page.getProperty("addEvent")), driver);
		VerificationManager.verifyString(driver.findElementByXPath(page.getProperty("addEvent")).getText(),"Add Event", "Verify app is navigated to left menu from History Screen");
		report.info(" ");
		
		// Go to Day View
		report.info("Tap on Day View in left side menu");
		driver.findElementByName(page.getProperty("dayViewButton")).click();
		report.info(" ");
		timeObject.waitForVisible(By.xpath(page.getProperty("dateXpath")), driver);
		VerificationManager.verifyString(driver.findElementByXPath(page.getProperty("dateXpath")).getText(),timeObject.getDateByDayView() ,"Verify app is navigated to the Day View Screen from left menu");
		report.info("");
		
		//Swipe to given date
		if(swipe)
		{
			swipeObject.swipeLeftToRight(driver);
			timeObject.waitForVisible(By.xpath(page.getProperty("dateXpath")), driver);
			VerificationManager.verifyString(driver.findElementByXPath(page.getProperty("dateXpath")).getText(),timeObject.getPreviousDateByDayView() ,"Verify app is navigated to the Previous Day View Screen from the current Day View Screen");
			report.info(" ");
		}
		
		// Tap on food and medicine in history of day view page
		WebElement medicineByHistory = driver.findElementById(page.getProperty("historyListView"));
		medicineByHistory.findElement(By.id(page.getProperty("medicineByHistory"))).click();
		report.info("Tap on the Food and medicine below the graph");
		report.info(" ");
		
        // Put all the same id's of item in list
		MobileElement graphScrubber = driver.findElement(By.id(page.getProperty("GraphScrubberId")));
		List<MobileElement> foodAndInsulinvalue = graphScrubber.findElements(By.id(page.getProperty("valueId")));

		// Get the medicine quntity from graph
		String medicineQuntityByGraph = foodAndInsulinvalue.get(0).getText();
		report.info("Get the medicine quantity from the graph in Unit: "+medicineQuntityByGraph);
		report.info(" ");
		
		// Get the cabs quntity from graph
		String foodQuntityByGraph = foodAndInsulinvalue.get(1).getText();
		report.info("Get the carbs Quantity from graph in grams: "+foodQuntityByGraph);
		report.info(" ");
		
		// Get the Medicine quantity from history
		driver.findElementById(page.getProperty("medicineNameIdByHistory")).getText();
		String medicineQuntityByHistory = driver.findElementById(page.getProperty("medicineQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("medicineTimeIdByHistory")).getText();
		
		// Get the Food quantity from history
		driver.findElementById(page.getProperty("foodNameIdByHistory")).getText();
		String foodQuntityByHistory = driver.findElementById(page.getProperty("foodQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("foodTimeIdByHistory")).getText();
		
		// Cut the String to match the food and medicine Quntity
		String medicineQuntityFromHistory = trimStringobj.trimString(medicineQuntityByHistory);
		String foodQuntityFromHistory = trimStringobj.trimString(foodQuntityByHistory);

		// Verify medicine content matches or not
		report.info("Verifying Food and Medicine Quntity between graph and List on day View Screen");
		report.info(" ");
		report.info("Medicine Quantity From List on Day View Screen: "+medicineQuntityFromHistory);
		report.info(" ");
		report.info("Medicine Quantity From Graph on Day View Screen : "+medicineQuntityByGraph);
		report.info("");
		
	    Assert.assertTrue(medicineQuntityFromHistory.equals(medicineQuntityByGraph), "Medicine Quantity does not matches with Graph Value");
	    
		report.info("Food Quantity From List on Day View Screen: "+foodQuntityFromHistory);
		report.info(" ");
		report.info("Food Quantity From Graph on Day View Screen: "+foodQuntityByGraph);
		report.info(" ");
		Assert.assertTrue(foodQuntityFromHistory.equals(foodQuntityByGraph), "Food Quantity does not matches with Graph Value");
	
	}
	

}
