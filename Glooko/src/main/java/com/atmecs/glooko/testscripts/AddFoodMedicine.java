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
import com.atmecs.glooko.testfunction.AddNote;
import com.atmecs.glooko.testfunction.Swipe;
import com.atmecs.glooko.testfunction.VerifyFoodAndMedicine;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;

public class AddFoodMedicine extends TestBase  {

     String addOnDate;
	 boolean swipe;
	 ReportLogService report = new ReportLogServiceImpl(AddFoodMedicine.class);
	
    @BeforeTest
    public void beforeTest() throws IOException
    {
    
    	swipe = false;
    	verify=new VerifyFoodAndMedicine();
    	swipeObject=new Swipe();
        
    }

    @Test
    public void test() throws Exception
    {
    	
        System.out.println("inside testScript in test");
		  for(int rowNo=1;rowNo<=maxRow;rowNo++)
		  {
			  addOnDate = xls.getByRow("Date", rowNo).trim();
			    //Add and verify food and medicine for current day
			 if(addOnDate.equalsIgnoreCase("Current"))
			 {
				 addNote.addNoteForCurrentDate(driver, rowNo); 
				  verify.verifyFoodAndMedicine(swipe, driver);
		     }
			 if(addOnDate.equalsIgnoreCase("Previous")) 
		     {
				 //Click on add event button
		    	 addNote.clickAddEventButton(driver); 
		    	 
	             //add food and medicine for the previous day
		    	 addNote.addForGivenDate(driver, rowNo);
				 
		    	 swipe = true;
		    	 //Verify food and medicine in day view
				 verify.verifyFoodAndMedicine(swipe, driver);
		    }
			    
		  }  
		         //Delete Food and medicine 
		        addNote.deleteFoodAndMedicineForCurrentDay(driver);
		       
    }
    
    @AfterTest
    public void afterTest() 
    {
  }



	

  }
