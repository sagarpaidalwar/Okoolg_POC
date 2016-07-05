package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.Assert;


import com.atmecs.falcon.automation.mobileui.components.DatePicker;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Time;
import com.atmecs.glooko.utility.Constants;
public class AddNote implements Constants{
	
	
	Properties page;
	DatePicker addDate;
	LoadPages pageObject=new LoadPages();
	XlsDataProvider xls=new XlsDataProvider("AddNote.xls", "AddNote");
	ReportLogService report = new ReportLogServiceImpl(AddNote.class);
	Swipe swipeObject=new Swipe();
	List<MobileElement> historyElement;
	Time dateTime=new Time();
	
	
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
		 VerificationManager.verifyString(time, driver.findElementById(page.getProperty("noteTimeTtext")).getText(), "Incorrect time added");
      
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
			report.info("Adding; quantity of carbs");
						
			// Add medicine Quntity
			driver.findElementById(page.getProperty("medicineQuntity")).sendKeys(xls.getByRow("Medicine Quantity", rowNo));
			report.info("Adding Medicine quantity");
						
			// Save the note
			driver.findElementById(page.getProperty("saveNote")).click();
			report.info("Saving Food and Medicine");
			VerificationManager.verifyString("History", driver.findElementByName("History").getText(), "App not navigate to the History screen");
			
		}
	
		/*
		 * Add food and Medicine for current date
		 */
	    public void addNoteForCurrentDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
		 {
	            report.info("Inside the addNoteCurrentDate method");    	
			    page = pageObject.getObjectRepository("AddFood.properties");
			
				// Tap on add note button
				driver.findElementById(page.getProperty("addNote")).click();
				report.info("Tap on the add note button");
				VerificationManager.verifyString("Quick Add", driver.findElementByName("Quick Add").getText(), "Not navigate to the Quick add screen ");
				
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
             report.info("Tap on add event button"); 
             VerificationManager.verifyString("Quick Add", driver.findElementByName("Quick Add").getText(), "Not navigate to the Quick add screen ");
			
		}
		
		/*
		 * Deleting the food and medicine from history for the current day
		 */
		public void deleteFoodAndMedicineForCurrentDay(AppiumDriver<MobileElement> driver) throws IOException, InterruptedException
		{
			
			int maxRow=xls.getRowCount("AddNote.xls", "AddNote");
			Properties page = pageObject.getObjectRepository("historyId.properties");
			// Go to left menu
			driver.findElementByClassName( page.getProperty("imageButton")).click();
			report.info("Tap on left menu");
			VerificationManager.verifyString("History", driver.findElementByName("History").getText(), "Not navigate to the left menu");			
 
			// Tap on history
			driver.findElementByName("History").click();
			report.info("Tap on History in left menu");
			VerificationManager.verifyString("History", driver.findElementByName("History").getText(), "Not navigate to the History Screen");
	   
			for(int rowNo=1;rowNo<=maxRow;rowNo++) 
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
			Thread.sleep(WAIT_TIME);
			
			}
			historyElement=driver.findElements(By.id("com.glooko.logbook:id/history_section"));
			
			 Assert.assertFalse(historyElement.get(0).getText().toUpperCase().equals(dateTime.getDateByHistory().toUpperCase()), "Data is not deleted for the current date");
	         report.info("Data is deleted successfully");
		}
      
		}
		
		

