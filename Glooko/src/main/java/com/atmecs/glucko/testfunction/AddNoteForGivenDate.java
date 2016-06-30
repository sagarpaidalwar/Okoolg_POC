package com.atmecs.glucko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.glucko.utility.LoadPages;
import com.atmecs.glucko.utility.Log;
import com.atmecs.glucko.utility.ReadExcel;
public class AddNoteForGivenDate {
	Dimension size;
	XlsDataProvider xls;
	ReadExcel readTestData= new ReadExcel();
	LoadPages pageObject;
	Properties page;
	
	public void addForGivenDate(AppiumDriver<MobileElement> driver,int i) throws IOException
	{
		xls=new XlsDataProvider("AddNote.xls", "AddNote");
		System.out.println("=====Inside====aadd for given date=======");
		 pageObject = new LoadPages();
		 page = pageObject.getObjectRepository("AddFood.properties");
		// driver.findElementById(page.getProperty("addNote")).click();
		 Log.info("Adding for the given date");
			 
		 Calendar cal  = Calendar.getInstance();
			   
		 //subtracting a day
		 cal.add(Calendar.DATE, -2);
		 SimpleDateFormat date = new SimpleDateFormat("dd");
		 String previousDate = date.format(new Date(cal.getTimeInMillis()));
			
		 driver.findElement(By.id(page.getProperty("noteDateText"))).click();
        //  driver.findElement(By.xpath("//android.view.View[@index='"+previousDate+"']")).click();
		 String xPath=page.getProperty("date");
		 driver.findElement(By.xpath(xPath+previousDate+"']")).click();
         driver.findElement(By.id(page.getProperty("okButton"))).click();
         Log.info("Date added successfully");
         
        // String time=readTestData.getValueByColumnNameAndRowNo("Time", i);
         String time=xls.getByRow("Time", i);
         
         String timeParts[] = time.split(":");
         String hour  = timeParts[0];
         String minuteAndlabel  = timeParts[1];
         String timepart2[]=minuteAndlabel.split(" ");
         String minute=timepart2[0];
         String label= timepart2[1];
         
         //String label = timeParts[2];
         
        driver.findElementById(page.getProperty("noteTimeTtext")).click();
 		driver.findElementById(page.getProperty("hour")).sendKeys(hour);
 		driver.findElementById(page.getProperty("minute")).sendKeys(minute);
 		
 		if(label.equals("AM"))
 		driver.findElementById(page.getProperty("amLabel")).sendKeys(label);
 		else
 		driver.findElementById(page.getProperty("pmLabel")).sendKeys(label);	
 		
 		driver.findElementById(page.getProperty("okButton")).click();
		Log.info("Time added successfully");
		 
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
	public void swipeBottomToTop(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		  Thread.sleep(3000);
		  driver.swipe(535, 560, 535, 1608, 3000);
		  
      }

}
