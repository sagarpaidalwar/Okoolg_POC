package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

import com.atmecs.falcon.automation.mobileui.components.DatePicker;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;
import com.atmecs.glooko.utility.Time;
public class AddNote {
	
	
	Properties page;
	DatePicker addDate;
	LoadPages pageObject=new LoadPages();
	XlsDataProvider xls=new XlsDataProvider("AddNote.xls", "AddNote");
	ReportLogService report = new ReportLogServiceImpl(AddNote.class);
	Swipe swipeObject=new Swipe();
	/*
	 * Add Food and Medicine For the Given Date
	 */
	public void addForGivenDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
	{
		
		 Time timeDate=new Time();
		
		 page = pageObject.getObjectRepository("AddFood.properties");
		 report.info("Adding for the given date");
		 String time=xls.getByRow("Time", rowNo);

		 //Add previous day date 
		 String previousDate=timeDate.getPreviousDate();
		 driver.findElement(By.id(page.getProperty("noteDateText"))).click();
		 
		 try {
			 DatePicker datePicker=new DatePicker();
			 datePicker.selectDate("", previousDate, "dd/MM/yyyy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 report.info("Date added successfully");
         
		 //Add time 
		 timeDate.addTime(driver, time);
		 report.info("Time added successfully");
        		 
		// Add Food

		 addFoodMedicine(driver, rowNo);
	 
	}
	
	/*
	 * Add Food and Medicine
	 */
		public void addFoodMedicine(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
		{
			 page = pageObject.getObjectRepository("AddFood.properties");
			driver.findElementById(page.getProperty("foodText")).sendKeys(xls.getByRow("Food Name", rowNo));
			report.info("Adding Food name");
				
		     // Add carbs
			driver.findElementById(page.getProperty("carbsText")).sendKeys(xls.getByRow("Food Quantity", rowNo));
			report.info("Adding quantity of carbs");
						
			// Add medicine Quntity
			driver.findElementById(page.getProperty("medicineQuntity")).sendKeys(xls.getByRow("Medicine Quantity", rowNo));
			report.info("Adding Medicine quantity");
						
			// Save the note
			driver.findElementById(page.getProperty("saveNote")).click();
			report.info("Saving Food and Medicine");
			
		}
		/*
		 * Add food and Medicine for current date
		 */
	    public void addNoteForCurrentDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
		 {
	    		
			 
			    page = pageObject.getObjectRepository("AddFood.properties");
			
			    Log.startTestCase("Add Food and Medicine");
				
				
				// Tap on add note button
				driver.findElementById(page.getProperty("addNote")).click();
				report.info("Tap on the add note button");
				
			   //Add Food and Medicine
				addFoodMedicine(driver, rowNo);

		 }
	    
	    /*
	     * Click on add event button
	     */
		public void clickAddEventButton(AppiumDriver<MobileElement> driver) throws IOException
		{
			 page = pageObject.getObjectRepository("AddFood.properties");
			 driver.findElementByClassName( page.getProperty("imageButton")).click();
             driver.findElementById(page.getProperty("addEventButton")).click();
			 
			
		}
		
		/*
		 * Deleting the food and medicine from history for the current day
		 */
		public void deleteFoodAndMedicineForCurrentDay(AppiumDriver<MobileElement> driver) throws IOException, InterruptedException {
			
			int maxRow=xls.getRowCount("AddNote.xls", "AddNote");
			Properties page = pageObject.getObjectRepository("historyId.properties");
			// Go to left menu
			driver.findElementByClassName( page.getProperty("imageButton")).click();
	        
			report.info("Tap on left menu");
			
	        // Tap on history
			driver.findElementByName("History").click();
			report.info("Tap on History in left menu");
			
	   
			for(int i=1;i<=maxRow;i++)
			{
			//Swipe Top to Bottom on history page
			swipeObject.swipeTopToBottom(driver);
			report.info("Done Swiping from top to bottom");
			
			// Delete food item from history
			driver.findElementById(page.getProperty("foodItemId")).click();
			driver.findElementById(page.getProperty("deleteButton")).click();
			report.info("Deleted the food item from history");
			
	        // Delete medicine from from history
			driver.findElementById(page.getProperty("medicineItemId")).click();
			driver.findElementById(page.getProperty("deleteButton")).click();
			report.info("Deleted the Medicine item from history");
			}
		
	

   }
		}
