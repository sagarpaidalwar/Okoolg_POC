package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.Properties;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;

public class AddNoteForCurrentDay {
	    XlsDataProvider xls;
    	LoadPages pageObject = new LoadPages();
    	AddNoteForGivenDate add=new AddNoteForGivenDate();
	    ReportLogService report = new ReportLogServiceImpl(AddNoteForCurrentDay.class);
	    
   /*
    * Add Food and Medicine For the current day
    */
	    public void addNoteForCurrentDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
	 {
    		
		 
		 xls=new XlsDataProvider("AddNote.xls", "AddNote");
		 Properties page = pageObject.getObjectRepository("AddFood.properties");
		
		    Log.startTestCase("Add Food and Medicine");
			
			
			// Tap on add note button
			driver.findElementById(page.getProperty("addNote")).click();
			report.info("Tap on the add note button");
			
		   //Add Food and Medicine
			add.addFoodMedicine(driver, rowNo);

	 }
}
