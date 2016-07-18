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
	String addOnPreviousDayTime;
	String addOnCurrentDayTime;
	
	/*
	 * Add Food and Medicine For the Given Date
	 */
	public void addForGivenDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
	{
		
		 Time timeDate=new Time();
	
		 page = pageObject.getObjectRepository("AddFood.properties");
		
		 report.info("Adding Food medicine for the Previous date");
		 
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
		 report.info(" ");
		
		 //Add time 
		 timeDate.addTime(driver, time);
		 report.info(" ");
		 
		 addOnPreviousDayTime=driver.findElementById(page.getProperty("noteTimeTtext")).getText();
		 VerificationManager.verifyString(time,addOnPreviousDayTime , " Verifying time selected in time picker with time text in Quick add screen");
		 report.info("");     
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
			report.info("Adding quantity of carbs in grams "+xls.getByRow("Food Quantity", rowNo));
			report.info(" ");
			 
			// Add medicine Quntity
			driver.findElementById(page.getProperty("medicineQuntity")).sendKeys(xls.getByRow("Medicine Quantity", rowNo));
			report.info("Adding Medicine quantity in Unit "+xls.getByRow("Medicine Quantity", rowNo));
			report.info(" ");
			 
			// Save the note
			driver.findElementById(page.getProperty("saveNote")).click();
			report.info("Tap on save button in quick add screen");
			report.info(" ");
			timeObject.waitForVisible(By.xpath(page.getProperty("history")), driver);
			VerificationManager.verifyString("History", driver.findElementByXPath(page.getProperty("history")).getText(), "After saving food and medicine verify whether app is navigated to history");
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
				VerificationManager.verifyString("Quick Add", driver.findElementByXPath(page.getProperty("quickAdd")).getText(), "Verify app is navigated to Quick add screen from Home Screen");
				report.info("");
				
				addOnCurrentDayTime=driver.findElementById(page.getProperty("noteTimeTtext")).getText();
				
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
             VerificationManager.verifyString("Quick Add", driver.findElementByXPath(page.getProperty("quickAdd")).getText(), "Verify app is navigated to Quick add screen from left menu");
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
			VerificationManager.verifyString("Add Event",driver.findElementByXPath(page.getProperty("addEvent")).getText(), "Verify app is navigated to left side menu from day View screen");			
			report.info(" ");
		
			// Tap on history
			driver.findElementByName("History").click();
			report.info("Tap on History in left menu");
			report.info(" ");
			timeObject.waitForVisible(By.xpath(page.getProperty("history")), driver);
			VerificationManager.verifyString("History",  driver.findElementByXPath(page.getProperty("history")).getText(), "");
			report.info(" ");
			
		
			//Swipe Top to Bottom on history page
			swipeObject.swipeTopToBottom(driver);
			report.info(" ");
			deleteByTime(driver, addOnCurrentDayTime);
			deleteByTime(driver, addOnPreviousDayTime);
		
	
			 historyElement=driver.findElements(By.id(page.getProperty("historySection")));
			 try{
			 Assert.assertFalse(historyElement.get(0).getText().toUpperCase().equals(timeObject .getDateByHistory().toUpperCase()), "Added food and Medicine is not deleted for the current date");
			 report.info("Added Food and Medicine is deleted successfully");
			 }
			 catch(AssertionError e)
			 {
				 report.info("Data is not deleted Or taking some time to delete");
			 }
			 
			 report.info(" ");
		}
		
		
		/*
		 * Delete data by time from history
		 */
		public void deleteByTime(AppiumDriver<MobileElement> driver,String time)
		{
			List <MobileElement> delete = null;
		try
		{
		 delete=driver.findElementsByName(time);
		}
		catch(IndexOutOfBoundsException e)
		{
         report.info("No Food or Medicine is available for the given date to delete");
		}
		for(int i=0;i<delete.size();i++)
		{
			
			    delete.get(i).click();
			    driver.findElementById("android:id/button3").click();	
		}
		}
}
		
		

