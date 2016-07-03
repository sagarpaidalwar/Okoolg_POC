package com.atmecs.glooko.testfunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.atmecs.glooko.utility.Constants;
import com.atmecs.glooko.utility.LoadPages;
import com.atmecs.glooko.utility.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class StatisticsTest implements Constants {
	AppiumDriver<MobileElement> driver;
	public Properties page;
	String currentMealTag;
	int noOfMetricsPerDay = 4;
	LoadPages pageObject = new LoadPages();
	// for pie chart values
	MealReadings allReadingsByPercent;
	MealReadings allReadingsByNumbers;
	MealReadings readingsByMealPercent;
	MealReadings readingsByMealNumbers;

	MobileElement timeFrame;
	String timeFrameText;

	public void StatisticsTest1(AppiumDriver<MobileElement> driver1) throws IOException, InterruptedException {

		driver = driver1;
		page = pageObject.getObjectRepository("statistcs.properties");
	}

	/* check for home screen visibility */
	private void checkHomeScreen() {

		Log.info("wait for Home screen");
		try {
			WebDriverWait wait = new WebDriverWait(driver, WEBDRIVER_WAIT_TIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(page.getProperty("toolBarId"))));
			Log.info("Home screen is Displayed");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	/* checks for data availability with in time frames */
	private boolean checkforDataAvailability(String timeFrame) {
		driver.isAppInstalled("com.glooko.logbook.activity.FtueActivity");
		MobileElement readingsPerDay = driver.findElement(By.id(page.getProperty("stsc_readingsPerDayId")));
		String readingsText = readingsPerDay.getText();
		if (readingsText.equals("0") || readingsText.matches("-")) {
			Log.info("No statistcs for the time frame of " + timeFrame + ".");
			return false;
		} else {
			Log.info("Statistcs Available for the time frame of " + timeFrame + ".");
			return true;
		}
	}

	private MealReadings getStatistics(MobileElement legendLayout, boolean isFromAllReadings) {
		List<MobileElement> ReadingMetrics = legendLayout.findElements(By.id(page.getProperty("stsc_CvTextviewId")));
		if (ReadingMetrics.size() != 0) {
			MealReadings readings = new MealReadings();
			for (int index = 0; index < ReadingMetrics.size(); index++) {
				if (index == 0) {
					if (isFromAllReadings)
						readings.setAbove(Integer.parseInt(ReadingMetrics.get(index).getText()));
					else
						readings.setBelow(Integer.parseInt(ReadingMetrics.get(index).getText()));
				} else if (index == 1)
					readings.setInRange(Integer.parseInt(ReadingMetrics.get(index).getText()));
				else {
					if (isFromAllReadings)
						readings.setBelow(Integer.parseInt(ReadingMetrics.get(index).getText()));
					else
						readings.setAbove(Integer.parseInt(ReadingMetrics.get(index).getText()));
				}
			}
			return readings;
		}
		return null;
	}

	private void gotoReadingsByMeal() {

		MobileElement readingBYMealTab = driver.findElement(By.id(page.getProperty("readingBYMealTabId")));
		readingBYMealTab.click();
		waitForElementsToLoad(page.getProperty("stsc_rightPbrId"));
		checkReadingsByMealNextTimeFrame();

	}

	private void getBarGraphValues() {
		// TODO Auto-generated method stub
		readingsByMealPercent = ReadReadingsByMealvalues(true);
		Log.info("Bargraph readings ByPercent" + readingsByMealPercent.getInRange());
		tapStatisticsBarGraph();
		readingsByMealNumbers = ReadReadingsByMealvalues(false);
		Log.info("Bargraph readings ByNumbers" + readingsByMealNumbers.getInRange());

		validateBothGraphvalues();

	}

	private void validateBothGraphvalues() {
		// TODO Auto-generated method stub
		Log.info("Validating statistics in both Tabs ");
		Log.info("allReadingsByPercent " + allReadingsByPercent.toString() + "/n readingsByMealPercent"
				+ readingsByMealPercent.toString());
		Log.info("allReadingsByNumbers " + allReadingsByNumbers.toString() + "/n readingsByMealNumbers"
				+ readingsByMealNumbers.toString());

		if (allReadingsByPercent.getBelow() == readingsByMealPercent.getBelow()
				&& allReadingsByPercent.getInRange() == readingsByMealPercent.getInRange()
				&& allReadingsByPercent.getAbove() == readingsByMealPercent.getAbove()) {
			Log.info("Statistics by percent in Both tabs are matched for the time frame of" + timeFrameText);
			/*Assert.assertEquals(allReadingsByPercent, readingsByMealPercent,
					"Statistics by percent in Both tabs are matched for the time frame of" + timeFrameText);*/
		} else
			Log.info("Statistics by percent in Both tabs are not matched for the time frame of" + timeFrameText);

		if (allReadingsByNumbers.getBelow() == readingsByMealNumbers.getBelow()
				&& allReadingsByNumbers.getInRange() == readingsByMealNumbers.getInRange()
				&& allReadingsByNumbers.getAbove() == readingsByMealNumbers.getAbove()) {
			Log.info("Statistics by numbers in Both tabs are matched for the time frame of" + timeFrameText);
		} else
			Log.info("Statistics by Numbers in Both tabs are not matched for the time frame of" + timeFrameText);

	}

	private void checkReadingsByMealNextTimeFrame() {
		// TODO Auto-generated method stub
		Log.info("Getting Bar Graph Readings in READINGS BY MEAL Tab...");
		selectTimeFrame();
		waitForElementsToLoad(page.getProperty("stsc_rightPbrId"));
		getBarGraphValues();
	}

	@SuppressWarnings("null")
	private MealReadings ReadReadingsByMealvalues(boolean isPercent) {
		// TODO Auto-generated method stub
		timeFrame = driver.findElementById(page.getProperty("stsc_timeFrameId"));
		String timeFrameText = timeFrame.getText();
		Log.info("Time frame range" + timeFrameText);
		List<String> graphIds = new ArrayList<String>();
		graphIds.add(page.getProperty("mrngGraphId"));
		graphIds.add(page.getProperty("afternoonGraphId"));
		graphIds.add(page.getProperty("evngGraphId"));
		graphIds.add(page.getProperty("nightGraphId"));
		List<MealReadings> allReadingBymeals = new ArrayList<MealReadings>();
		for (String graphId : graphIds) {
			MobileElement eachMealLayout = driver.findElement(By.id(graphId));
			MealReadings eachMealValues = getStatistics(eachMealLayout, false);
			allReadingBymeals.add(eachMealValues);
		}
		MealReadings allRadings = addReadingByMealValues(allReadingBymeals, isPercent);
		return allRadings;
	}

	private MealReadings addReadingByMealValues(List<MealReadings> allReadingsbyMeal, boolean ispercent) {
		// TODO Auto-generated method stub
		MealReadings readings = new MealReadings();
		int below = 0, inRange = 0, above = 0;
		for (MealReadings index : allReadingsbyMeal) {
			below += index.getBelow();
			inRange += index.getInRange();
			above += index.getAbove();
		}
		if (ispercent) {
			below = getPercentRoundOffValue(below);
			inRange = getPercentRoundOffValue(inRange);
			above = getPercentRoundOffValue(above);
		}
		readings.setBelow(below);
		readings.setInRange(inRange);
		readings.setAbove(above);
		return readings;
	}

	private int getPercentRoundOffValue(int value) {
		// TODO Auto-generated method stub
		float percentvalue = ((float) value) / noOfMetricsPerDay;
		return Math.round(percentvalue);
	}

	private void tapStatisticsPieChart() {
		MobileElement statisticsChart = driver.findElement(By.id(page.getProperty("stsc_ChartViewID")));
		statisticsChart.click();
	}

	private void tapStatisticsBarGraph() {
		MobileElement statisticsBarGraph = driver.findElement(By.id(page.getProperty("stsc_BarGraphId")));
		statisticsBarGraph.click();
	}

	public void goToStatisticsScreen(String xpath) throws InterruptedException {
	
		MobileElement MenuIcon = driver.findElementByAccessibilityId(page.getProperty("AccIdNavigationDrawer"));
		MenuIcon.click();
		Log.info("left menu displayed");
		waitForVisible(By.id(page.getProperty("leftDrawerId")));
		Assert.assertTrue(driver.findElement(By.id(page.getProperty("leftDrawerId"))).isDisplayed());
		// click on left menu
		
		MobileElement ClickMenuIcon = driver.findElement(By.xpath(xpath));
		ClickMenuIcon.click();
		Log.info("selected statistics menu item");
	}

	public void checkStatisticsAvailabilityForSelectedTimeFrame() {
		// TODO Auto-generated method stub
		Log.info("checking for Data Availabity for" + timeFrameText);
		if (checkforDataAvailability(timeFrameText)) {
			getPiechartReadings(); 
			gotoReadingsByMeal();
		}
	}

	private void getPiechartReadings() {

		Log.info("Getting Piechart Readings in ALL READINGS Tab...");
		MobileElement percentLayout = driver.findElement(By.id(page.getProperty("stsc_ChartValuesLayoutId")));
		allReadingsByPercent = getStatistics(percentLayout, true);
		Log.info("allReadingsByPercent" + allReadingsByPercent.toString());
		tapStatisticsPieChart();
		MobileElement numbersLayout = driver.findElement(By.id(page.getProperty("stsc_ChartValuesLayoutId")));
		allReadingsByNumbers = getStatistics(numbersLayout, true);
		Log.info("allReadingsByNumbers" + allReadingsByNumbers.toString());

	}

	public void checkAndVerifyStatisticsForSelectedTimeFrame(String statisticsTimeFrame) {

		currentMealTag = statisticsTimeFrame;
		MobileElement allReadingsTab = driver.findElement(By.id(page.getProperty("allReadingsTabId")));
		allReadingsTab.click();
		selectTimeFrame();
		waitForElementsToLoad(page.getProperty("stsc_leftPbrId"));
		checkStatisticsAvailabilityForSelectedTimeFrame();
		
	}

	private void selectTimeFrame() {
		// TODO Auto-generated method stub
		timeFrame = driver.findElementById(page.getProperty("stsc_timeFrameId"));
		timeFrameText = timeFrame.getText();
		timeFrame.click();
		waitForVisible(By.id(page.getProperty("stsc_timeFrameTagId")));
		MobileElement mealTagelement = driver.findElement(By.xpath(page.getProperty("stsc_timeFrameTagXpath")+""+currentMealTag+"']"));
		mealTagelement.click();
		Log.info("Selected "+timeFrameText+" time frame.");
	}

	private void waitForElementsToLoad(String id) {
		try {
			MobileElement activityLoader = driver.findElement(By.id(id));
			Log.info("" + activityLoader.equals(driver.findElement(By.id(id))));
			Log.info("Loading...");
			while (activityLoader.isDisplayed() == true) {
			}
			Log.info("Loading is Done.");
		} catch (NoSuchElementException e) {
			Log.info("No such element found");

		}
	}

	public void waitForVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, ELEMENT_WAIT_TIME);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
		}
	}

}