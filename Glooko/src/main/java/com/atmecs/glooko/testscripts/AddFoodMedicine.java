package com.atmecs.glooko.testscripts;



import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.glooko.testfunction.AddNoteForCurrentDay;
import com.atmecs.glooko.testfunction.AddNoteForGivenDate;
import com.atmecs.glooko.testfunction.GotoLeftMenu;
import com.atmecs.glooko.testfunction.Swipe;
import com.atmecs.glooko.testfunction.VerifyFoodAndMedicine;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;

public class AddFoodMedicine extends UserBaseTest  {

	 LoadPages pageObject;
	 GotoLeftMenu leftmenu;
	 AddNoteForCurrentDay addFoodAndMedicine;
	 AddNoteForGivenDate addFoodAndMedForDate;
	 VerifyFoodAndMedicine verify;
	 boolean swipe;
	 Properties page;
	 XlsDataProvider xls;
	 Swipe swipeObject;
	 int maxRow;
	 ReportLogService report = new ReportLogServiceImpl(AddFoodMedicine.class);
     
    @BeforeTest
    public void beforeTest() throws IOException
    {
    
    	pageObject = new LoadPages();
    	leftmenu = new GotoLeftMenu();
    	addFoodAndMedicine=new AddNoteForCurrentDay();
    	addFoodAndMedForDate=new AddNoteForGivenDate();
    	swipe = false;
    	page=pageObject.getObjectRepository("AddFood.properties");
    	verify=new VerifyFoodAndMedicine();
    	xls=new XlsDataProvider("AddNote.xls", "AddNote");
    	swipeObject=new Swipe();
    	maxRow=xls.getRowCount("AddNote.xls", "AddNote");
        
    }

    @Test
    public void test() throws Exception
    {
        System.out.println("inside testScript in test");
		  for(int rowNo=1;rowNo<=maxRow;rowNo++)
		  {
			    //Add and verify food and medicine for current day
			 if(xls.getByRow("Date", rowNo).equals("Current"))
			 {
				  addFoodAndMedicine.addNoteForCurrentDate(driver, rowNo); 
				  verify.verifyFoodAndMedicine(swipe, driver);
		     }else
		     {
		    	 //Add and verify food and medicine for previous day
			     leftmenu.leftMenu(driver); 
                 driver.findElementById(page.getProperty("addEventButton")).click();
				 addFoodAndMedForDate.addForGivenDate(driver, rowNo);
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
        
		report.info("Tap on left menu");
		
        // Tap on history
		driver.findElementByName("History").click();
		report.info("Tap on History in left menu");
		Properties page = pageObject.getObjectRepository("historyId.properties");
   
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
		Log.endTestCase("Delete Food and Medicines");
		}

  }
}
