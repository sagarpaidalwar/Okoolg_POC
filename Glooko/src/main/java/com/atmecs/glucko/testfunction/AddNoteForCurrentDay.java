package com.atmecs.glucko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.Properties;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.glucko.utility.LoadPages;
import com.atmecs.glucko.utility.Log;

public class AddNoteForCurrentDay {
	    XlsDataProvider xls;
    	LoadPages pageObject = new LoadPages();
	
    	public void addNoteForCurrentDate(AppiumDriver<MobileElement> driver,int i) throws IOException
	 {
		 
		 xls=new XlsDataProvider("AddNote.xls", "AddNote");
		 Properties page = pageObject.getObjectRepository("AddFood.properties");
		
		    Log.startTestCase("Add Food and Medicine");
			
			
			// Tap on add note button
			driver.findElementById(page.getProperty("addNote")).click();
			Log.info("Tap on the add note button");
			
			// Add Food
			driver.findElementById(page.getProperty("foodText")).sendKeys(xls.getByRow("Food Name", i));
	        Log.info("Adding Food name");
			
	        // Add carbs
			driver.findElementById(page.getProperty("carbsText")).sendKeys(xls.getByRow("Food Quantity", i));
			Log.info("Adding quantity of carbs");
			
			// Add medicine Quntity
			driver.findElementById(page.getProperty("medicineQuntity")).sendKeys(xls.getByRow("Medicine Quantity", i));
			Log.info("Adding Medicine quantity");
			
			// Save the note
			driver.findElementById(page.getProperty("saveNote")).click();
			Log.info("Saving Food and Medicine");
			Log.endTestCase("Add Food and Medicine");
		 
	 }
}
