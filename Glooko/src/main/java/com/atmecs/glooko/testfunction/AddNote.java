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
	Time timeObject = new Time();
	
	
	/*
	 * Add Food and Medicine For the Given Date
	 */
	public void addForGivenDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
	{
		
		 Time timeDate=new Time();
		
		 page = pageObject.getObjectRepository("AddFood.properties");
		 report.info("Adding for the given date");
		 report.info(" ");
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
		 
		 try
		 {
		 Assert.assertTrue(driver.findElementById(page.getProperty("noteDateText")).getText().equals(timeObject.getpreviousDatebyQuikAdd()), "");
		 report.info("In Quick Add screen : Correct date selected in date picker");
		 }
		 catch(Exception e)
		 {
			 report.info("Date check in Quick add screen: Incorrect date selected in date picker");
		 }
		 
		 report.info(" ");
		 //Add time 
		 timeDate.addTime(driver, time);
		 report.info("Time added successfully");
		 report.info(" ");
		 VerificationManager.verifyString(time, driver.findElementById(page.getProperty("noteTimeTtext")).getText(), "Time Check in Quick Add Screen - Time selected in Time Picker and Time in Time Text DOESNOT match");
		       
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
			report.info("Adding Food name "+xls.getByRow("Food Name", rowNo));
			report.info(" ");
			 
		     // Add carbs
			driver.findElementById(page.getProperty("carbsText")).sendKeys(xls.getByRow("Food Quantity", rowNo));
			report.info("Adding; quantity of carbs "+xls.getByRow("Food Quantity", rowNo));
			report.info(" ");
			 
			// Add medicine Quntity
			driver.findElementById(page.getProperty("medicineQuntity")).sendKeys(xls.getByRow("Medicine Quantity", rowNo));
			report.info("Adding Medicine quantity "+xls.getByRow("Medicine Quantity", rowNo));
			report.info(" ");
			 
			// Save the note
			driver.findElementById(page.getProperty("saveNote")).click();
			report.info("Saving Food and Medicine");
			report.info(" ");
			timeObject.waitForVisible(By.xpath(page.getProperty("history")), driver);
			VerificationManager.verifyString("History", driver.findElementByXPath(page.getProperty("history")).getText(), "App not navigate to the History screen");
			report.info(" ");
		}
	
		/*
		 * Add food and Medicine for current date
		 */
	    public void addNoteForCurrentDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
		 {
			    page = pageObject.getObjectRepository("AddFood.properties");
			
				// Tap on add note button
				driver.findElementById(page.getProperty("addNote")).click();
				report.info("Tap on the add note button");
				report.info(" ");
				timeObject.waitForVisible(By.xpath(page.getProperty("quickAdd")), driver);
				VerificationManager.verifyString("Quick Add", driver.findElementByXPath(page.getProperty("quickAdd")).getText(), "Not navigate to the Quick add screen ");
				
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
             report.info(" ");
             timeObject.waitForVisible(By.xpath(page.getProperty("quickAdd")), driver);
             VerificationManager.verifyString("Quick Add", driver.findElementByXPath(page.getProperty("quickAdd")).getText(), "Not navigate to the Quick add screen ");
             report.info(" ");
             
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
			timeObject.waitForVisible(By.xpath(page.getProperty("addEvent")), driver);
			report.info(" ");
			VerificationManager.verifyString("Add Event",driver.findElementByXPath(page.getProperty("addEvent")).getText(), "Not navigate to the left menu");			
			report.info(" ");
		
			// Tap on history
			driver.findElementByName("History").click();
			report.info("Tap on History in left menu");
			report.info(" ");
			timeObject.waitForVisible(By.xpath(page.getProperty("history")), driver);
			VerificationManager.verifyString("History",  driver.findElementByXPath(page.getProperty("history")).getText(), "Not navigate to the History Screen");
			report.info(" ");
			
			for(int rowNo=1;rowNo<=maxRow;rowNo++) 
			{
			//Swipe Top to Bottom on history page
			swipeObject.swipeTopToBottom(driver);
			report.info("Done Swiping from top to bottom");
			report.info(" ");
			
			// Delete food item from history
			driver.findElementById(page.getProperty("foodItemId")).click();
			driver.findElementById(page.getProperty("deleteButton")).click();
			report.info("Deleted the food item from history");
			report.info(" ");
	        // Delete medicine from from history
			driver.findElementById(page.getProperty("medicineItemId")).click();
			driver.findElementById(page.getProperty("deleteButton")).click();
			
			}
			 historyElement=driver.findElements(By.id(page.getProperty("historySection")));
			 
			 Assert.assertFalse(historyElement.get(0).getText().toUpperCase().equals(timeObject .getDateByHistory().toUpperCase()), "Data is not deleted for the current date");
	         report.info("Data is deleted successfully");
	         report.info(" ");
		}
      
}
		
		

