package com.atmecs.glooko.testscripts;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.testfunction.AddNote;
import com.atmecs.glooko.testfunction.LogIn;
import com.atmecs.glooko.testfunction.LogOut;
import com.atmecs.glooko.testfunction.Swipe;
import com.atmecs.glooko.testfunction.VerifyFoodAndMedicine;
import com.atmecs.glooko.utility.Constants;

public class AddFoodMedicine extends TestBase implements Constants{

	VerifyFoodAndMedicine verifyFoodMedicine;
	String addOnDate;
	boolean swipe;
	AddNote addNote; 
	ReportLogService report;
	LogIn loginObject;
	LogOut logoutObject;
	
	public AddFoodMedicine() {
		super("AddFood.properties","AddNote.xls","AddNote");
	}



	@BeforeTest
	public void initializer() throws IOException {
		super.initializer();
		verifyFoodMedicine = new VerifyFoodAndMedicine();
		swipeObject = new Swipe();
		addNote=new AddNote();
		report = new ReportLogServiceImpl(AddFoodMedicine.class);
		loginObject=new LogIn();
		logoutObject=new LogOut();
		
	}

	@Test
	public void addFoodMedicine() throws Exception {
		//Log in 
		loginObject.testLogIn(driver);
		
		//VerificationManager.verifyString("Home", driver.findElementByName("Home").getText(), "App is not started");
		for (int rowNo = 1; rowNo <= maxRow; rowNo++) {
			
			
			addOnDate = xls.getByRow("Date", rowNo).trim();
			// Add and verify food and medicine for current day
			if (addOnDate.equalsIgnoreCase("Current")) {
				addNote.addNoteForCurrentDate(driver, rowNo);
				verifyFoodMedicine.verifyFoodAndMedicine(swipe, driver,rowNo);
			}
			if (addOnDate.equalsIgnoreCase("Previous")) {
				// Click on add event button
				addNote.clickAddEventButton(driver);

				// add food and medicine for the previous day
				addNote.addForGivenDate(driver, rowNo);

				swipe = true;
				// Verify food and medicine in day view
				verifyFoodMedicine.verifyFoodAndMedicine(swipe, driver,rowNo);
			}

		}
		// Delete Food and medicine
		addNote.deleteFoodAndMedicineForCurrentDay(driver);
		logoutObject.testLogOut(driver); 
	}

	@AfterTest
	public void afterTest() throws IOException, URISyntaxException 
	{
		
		Desktop desktop=Desktop.getDesktop();
		desktop.browse(new URI(reportURL));
		swipeObject = null;
		report=null;
		addNote=null;
	}

}
