package com.atmecs.glucko.testscripts;


import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.glucko.testfunction.AddNoteForCurrentDay;
import com.atmecs.glucko.testfunction.AddNoteForGivenDate;
import com.atmecs.glucko.testfunction.GotoLeftMenu;
import com.atmecs.glucko.testfunction.TapOnDayView;
import com.atmecs.glucko.testfunction.VerifyFoodAndMedicine;
import com.atmecs.glucko.utility.LoadPages;
import com.atmecs.glucko.utility.Log;
import com.atmecs.glucko.utility.ReadExcel;

public class AddFoodMedicine extends UserBaseTest {

	 ReadExcel readTestData;
	 LoadPages pageObject;
	 GotoLeftMenu leftmenu;
	 List<String> noOfCol;
	 int noOfColumnValues;
	 AddNoteForCurrentDay addFoodAndMedicine;
	 AddNoteForGivenDate addFoodAndMedForDate;
	 VerifyFoodAndMedicine verify;
	 TapOnDayView tapOnDayView;
	 boolean swipe;
	 Properties page;
	 XlsDataProvider xls;
	 
   
    @BeforeTest
    public void beforeTest() throws IOException
    {
        readTestData= new ReadExcel(); 
    	pageObject = new LoadPages();
    	leftmenu = new GotoLeftMenu();
    	addFoodAndMedicine=new AddNoteForCurrentDay();
    	addFoodAndMedForDate=new AddNoteForGivenDate();
    	swipe = false;
    	page=pageObject.getObjectRepository("AddFood.properties");
    	verify=new VerifyFoodAndMedicine();
    	xls=new XlsDataProvider("AddNote.xls", "AddNote");
    	
    	  System.out.println("==============Check sheet data=============="+xls.getByRow("Food Name", 1));
	         System.out.println("No of rows in a sheet======================================="+xls.getRowCount("AddNote.xls", "AddNote"));
    }

    @Test
    public void test() throws IOException, InterruptedException
    {
        System.out.println("inside testScript in test");
    	noOfCol=readTestData.getColumnRows(0);
	    noOfColumnValues=noOfCol.size();
		  for(int i=1;i<noOfColumnValues;i++)
		  {
			  String time=xls.getByRow("Time", i);
		         System.out.println("===============Time inside add note=========="+time);
			    //Add and verify food and medicine for current day
			 if(readTestData.getValueByColumnNameAndRowNo("Date", i).equals("Current"))
			 {
				  addFoodAndMedicine.addNoteForCurrentDate(driver, i);
				  verify.verifyFoodAndMedicine(swipe, driver);
		     }else
		     {
		    	 //Add and verify food and medicine for previous day
			     leftmenu.leftMenu(driver); 
                 driver.findElementById(page.getProperty("addEventButton")).click();
				 addFoodAndMedForDate.addForGivenDate(driver, i);
				 swipe = true;
				 verify.verifyFoodAndMedicine(swipe, driver);
		    }
			    
		  }  
		         //Delete Food and medicine 
		         deleteFoodAndMedicineForCurrentDay();
		       
    }
    
    @AfterTest
    public void afterTest() 
    {
        readTestData=null;
        pageObject =null;
    	leftmenu = null;
    	addFoodAndMedicine=null;
    	addFoodAndMedForDate=null;
    	page=null;
    }



	/*
	 * Deleting the food and medicine from history for the current day
	 */
	public void deleteFoodAndMedicineForCurrentDay() throws IOException, InterruptedException {
		
		Log.startTestCase("Deleting the food and medicine from history for the current day");
		// Go to left menu
		leftmenu.leftMenu(driver);
        Log.info("Tap on left menu");
		
        // Tap on history
		driver.findElementByName("History").click();
        Log.info("Tap on History in left menu");
		Properties page = pageObject.getObjectRepository("historyId.properties");
   
		for(int i=1;i<noOfColumnValues;i++)
		{
		 addFoodAndMedForDate.swipeBottomToTop(driver);
		// Delete food item from history
		driver.findElementById(page.getProperty("foodItemId")).click();
		driver.findElementById(page.getProperty("deleteButton")).click();
        Log.info("Deleted the food item from history");
		
        // Delete medicine from from history
		driver.findElementById(page.getProperty("medicineItemId")).click();
		driver.findElementById(page.getProperty("deleteButton")).click();
		Log.info("Deleted the Medicine item from history");
		Log.endTestCase("Delete Food and Medicines");
		}

  }
}
