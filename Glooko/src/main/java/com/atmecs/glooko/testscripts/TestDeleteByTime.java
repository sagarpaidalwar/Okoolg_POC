package com.atmecs.glooko.testscripts;

import io.appium.java_client.MobileElement;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.testfunction.AddNote;
import com.atmecs.glooko.testfunction.Swipe;

public class TestDeleteByTime extends UserBaseTest{
	List<MobileElement> historyElement;
	List <MobileElement> delete;
	Swipe swipe=new Swipe();
	List <MobileElement> delete1;
	ReportLogService report = new ReportLogServiceImpl(TestDeleteByTime.class);
	
	@Test
	public void testDeleteByTime() throws InterruptedException
	{
		driver.findElementByClassName("android.widget.ImageButton").click();
		driver.findElementByName("History").click();
		swipe.swipeTopToBottom(driver);
		
		
		
		try
		{
		 delete=driver.findElementsByName("9:00 AM");
		}
		catch(IndexOutOfBoundsException e)
		{
         report.info("No data available for the given date");
		}
		for(int i=0;i<delete.size();i++)
		{
			
			    delete.get(i).click();
			    driver.findElementById("android:id/button3").click();	
		}
		
	   
		try
		{
		 delete1=driver.findElementsByName("11:45 AM");
		}
		catch(IndexOutOfBoundsException e)
		{
         report.info("No data available for the given date");
		}
	  
		
	    for(int i=0;i<delete1.size();i++)
		{
			
			    delete1.get(i).click();
			    driver.findElementById("android:id/button3").click();	
		}
	    
	 
	}
}
