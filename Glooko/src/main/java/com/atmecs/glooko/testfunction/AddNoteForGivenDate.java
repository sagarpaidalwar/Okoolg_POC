package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.atmecs.falcon.automation.mobileui.components.DatePicker;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;
import com.atmecs.glooko.utility.ReadExcel;
import com.atmecs.glooko.utility.Time;
public class AddNoteForGivenDate {
	XlsDataProvider xls;
	LoadPages pageObject;
	Properties page;
	DatePicker addDate;
	
	ReportLogService report = new ReportLogServiceImpl(AddNoteForGivenDate.class);
	
	/*
	 * Add Food and Medicine For the Given Date
	 */
	public void addForGivenDate(AppiumDriver<MobileElement> driver,int rowNo) throws IOException
	{
		
		 Time timeDate=new Time();
		 xls=new XlsDataProvider("AddNote.xls", "AddNote");
		 pageObject = new LoadPages();
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
			 pageObject = new LoadPages();
			 page = pageObject.getObjectRepository("AddFood.properties");
			 xls=new XlsDataProvider("AddNote.xls", "AddNote");
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
		
		
	

}
