package com.atmecs.glooko.testscripts;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.glooko.testfunction.StatisticsTest;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;
import com.atmecs.glooko.utility.ReadExcel;

public class Statistics extends UserBaseTest {

	Properties page;
	ReadExcel TestDataFile;
	List<String> readTestData;
	LoadPages pageObject;
	StatisticsTest statisticsView;
	XlsDataProvider xls;

	@BeforeTest
	public void beforeTest() throws IOException {
		System.out.println("inside testScript1 in before test");

		pageObject = new LoadPages();
		TestDataFile = new ReadExcel();
		page = pageObject.getObjectRepository("statistcs.properties");
		Log.startTestCase("Verify Statistics Data in all Readings and Readings by Meal");
		statisticsView = new StatisticsTest();
		// xls = new XlsDataProvider("AddNote.xls", "AddNote");
		xls = new XlsDataProvider("AddNote.xls", "StatisticsData");
		// System.out.println("==============Check sheet data==============" +
		// xls.getByRow("Food Name", 1));
		// System.out.println("No of rows in a
		// sheet======================================="+xls.getRowCount("AddNote.xls",
		// "AddNote"));
		System.out.println("==============Check sheet data1==============" + xls.getByRow("Time Frames", 1));
		System.out.println("No of rows in a sheet======================================="+ xls.getRowCount("AddNote.xls", "StatisticsData"));

	}

	@Test
	public void testStatisticsData() throws IOException, InterruptedException {
		System.out.println("inside testScript1 in test");
		readTestData = TestDataFile.getColumnRows(11); // parameter need to
														// change
		statisticsView.StatisticsTest1(driver);
		// To display Statistics screen
		statisticsView.goToStatisticsScreen(page.getProperty("statistics"));
		// To check data availability and verify statistics for selected time
		// frame

		if (readTestData.size() > 0) {
			for (int i = 1; i < readTestData.size(); i++)
				verifyStatistics(readTestData.get(i));
		}

		Log.endTestCase("End of Verify Statistics Data ");
	}

	public void verifyStatistics(String timeFrame) {
		statisticsView.checkAndVerifyStatisticsForSelectedTimeFrame(timeFrame);
	}

	@AfterTest
	public void afterTest() {
		System.out.println("inside testScript1 in after test");
		readTestData = null;
		pageObject = null;
		page = null;
		statisticsView = null;
	}
}
