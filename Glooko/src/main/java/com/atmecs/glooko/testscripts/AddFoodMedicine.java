package com.atmecs.glooko.testscripts;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.glooko.testfunction.AddNote;
import com.atmecs.glooko.testfunction.Swipe;
import com.atmecs.glooko.testfunction.VerifyFoodAndMedicine;

public class AddFoodMedicine extends TestBase {

	VerifyFoodAndMedicine verifyFoodMedicine;
	String addOnDate;
	boolean swipe;
	AddNote addNote; 
	ReportLogService report = new ReportLogServiceImpl(AddFoodMedicine.class);
	
	public AddFoodMedicine() {
		super("AddFood.properties","AddNote.xls","AddNote");
	}

	@Override
	public void initializer() throws IOException {
		super.initializer();
		verifyFoodMedicine = new VerifyFoodAndMedicine();
		swipeObject = new Swipe();
		
		
	}

	@BeforeTest
	public void beforeTest() throws IOException {
		swipe = false;
		swipeObject = new Swipe();
		addNote=new AddNote();
		initializer();
		report = new ReportLogServiceImpl(AddFoodMedicine.class);
	}

	@Test
	public void test() throws Exception {
	
		report.info("Value for the maxRows"+maxRow);
		VerificationManager.verifyString("Home", driver.findElementByName("Home").getText(), "App is not started");
		for (int rowNo = 1; rowNo <= maxRow; rowNo++) {
			
			
			report.info("Inside the for loop========="+maxRow);
			addOnDate = xls.getByRow("Date", rowNo).trim();
			// Add and verify food and medicine for current day
			if (addOnDate.equalsIgnoreCase("Current")) {
				addNote.addNoteForCurrentDate(driver, rowNo);
				verifyFoodMedicine.verifyFoodAndMedicine(swipe, driver);
			}
			if (addOnDate.equalsIgnoreCase("Previous")) {
				// Click on add event button
				addNote.clickAddEventButton(driver);

				// add food and medicine for the previous day
				addNote.addForGivenDate(driver, rowNo);

				swipe = true;
				// Verify food and medicine in day view
				verifyFoodMedicine.verifyFoodAndMedicine(swipe, driver);
			}

		}
		// Delete Food and medicine
		addNote.deleteFoodAndMedicineForCurrentDay(driver);

	}

	@AfterTest
	public void afterTest() 
	{
		swipeObject = null;
		report=null;
	}

}
