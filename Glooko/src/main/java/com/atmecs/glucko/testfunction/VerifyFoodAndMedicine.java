package com.atmecs.glucko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.atmecs.glucko.testfunction.GotoLeftMenu;
import com.atmecs.glucko.testfunction.TrimString;
import com.atmecs.glucko.utility.Constants;
import com.atmecs.glucko.utility.LoadPages;
import com.atmecs.glucko.utility.Log;
import com.atmecs.glucko.utility.ReadExcel;

public class VerifyFoodAndMedicine implements Constants{
	
	TapOnDayView tapOnDayView;
	ReadExcel readTestData= new ReadExcel(); 
	LoadPages pageObject = new LoadPages();
	TrimString trimStringobj = new TrimString();
	GotoLeftMenu leftmenu = new GotoLeftMenu();
public void verifyFoodAndMedicine(boolean swipe,AppiumDriver<MobileElement> driver) throws IOException, InterruptedException {
		
		Log.startTestCase("Verify Food and Medicine Quntity from graph to the History ");
		
		Properties page = pageObject.getObjectRepository("dayViewId.properties");
		// Go to left menu
		leftmenu.leftMenu(driver);
		Log.info("Tap on left menu");
		
		// Go to Day View
		tapOnDayView = new TapOnDayView();
		tapOnDayView.tapOnDayview(driver);
		Log.info("Tap on Day View in left side menu");
		
		//Swipe to given date
		if(swipe)
		{

			Thread.sleep(SWIPETIME);
			TouchAction tAction=new TouchAction(driver);
	           tAction.press(150,704).moveTo(900,704).release().perform();
		 }
			
		
		
		// Tap on food and medicine in history of day view page
		WebElement medicineByHistory = driver.findElementById(page.getProperty("historyListView"));
		medicineByHistory.findElement(By.id(page.getProperty("medicineByHistory"))).click();
        Log.info("Tap on the Food and medicine below the graph");
		
        // Put all the same id's of item in list
		MobileElement graphScrubber = driver.findElement(By.id(page.getProperty("GraphScrubberId")));
		List<MobileElement> foodAndInsulinvalue = graphScrubber.findElements(By.id(page.getProperty("valueId")));

		// Get the medicine quntity from graph
		String medicineQuntityByGraph = foodAndInsulinvalue.get(0).getText();
		Log.info("Get the medicine quantity from the graph: "+medicineQuntityByGraph);
		
		// Get the cabs quntity from graph
		String foodQuntityByGraph = foodAndInsulinvalue.get(1).getText();
		Log.info("Get the carbs Quantity from graph: "+foodQuntityByGraph);
		
		// Get the Medicine quantity from history
		driver.findElementById(page.getProperty("medicineNameIdByHistory")).getText();
		String medicineQuntityByHistory = driver.findElementById(page.getProperty("medicineQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("medicineTimeIdByHistory")).getText();
        Log.info("Get the Medicine quantity from history");
		
		// Get the Food quantity from history
		driver.findElementById(page.getProperty("foodNameIdByHistory")).getText();
		String foodQuntityByHistory = driver.findElementById(page.getProperty("foodQuntityIdByHistory")).getText();
		driver.findElementById(page.getProperty("foodTimeIdByHistory")).getText();
		Log.info("Get the Food quantity from history");
		
		// Cut the String to match the food and medicine Quntity
		String medicineQuntityFromHistory = trimStringobj.trimString(medicineQuntityByHistory);
		String foodQuntityFromHistory = trimStringobj.trimString(foodQuntityByHistory);

		// Verify medicine content matches or not
		Log.info("Verifying Food and Medicine Quntity between graph and history ");
		
		Log.info("Medicine Quantity From history: "+medicineQuntityFromHistory);
		Log.info("Medicine Quantity From Graph: "+medicineQuntityByGraph);
		assert medicineQuntityFromHistory.equals(medicineQuntityByGraph) : "Medicine Quntity from graph does not matches with history ";
		
		
		Log.info("Food Quantity From history: "+foodQuntityFromHistory);
		Log.info("Food Quantity From Graph: "+foodQuntityByGraph);
		assert foodQuntityFromHistory.equals(foodQuntityByGraph) : "Food Quntity from graph does not matches with history";
		
		Log.endTestCase("Verify Food and Medicine Quntity");
	}
	

}
