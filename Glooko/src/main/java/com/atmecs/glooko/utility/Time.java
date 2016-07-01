package com.atmecs.glooko.utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Time {
	XlsDataProvider xls;
	LoadPages pageObject;
	Properties page;
	/*
	 * Add time in time picker
	 */
	public void addTime(AppiumDriver<MobileElement> driver,String time) throws IOException
	{
		    pageObject = new LoadPages();
		    page = pageObject.getObjectRepository("AddFood.properties");
		   
	         
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
	 		
	 		if(label.equalsIgnoreCase("AM"))
	 			driver.findElementById(page.getProperty("amLabel")).sendKeys(label);
	 		if(label.equalsIgnoreCase("PM"))
	 			driver.findElementById(page.getProperty("pmLabel")).sendKeys(label);	
	 		
	 		driver.findElementById(page.getProperty("okButton")).click();
	}
	
	/*
	 * Get the previous date
	 */
	public String getPreviousDate()
	{
		  Calendar cal  = Calendar.getInstance();
		   
			 //subtracting a day
			 cal.add(Calendar.DATE, -1);
			 SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
			 String previousDate = date.format(new Date(cal.getTimeInMillis()));
			 return previousDate;
	}

}
