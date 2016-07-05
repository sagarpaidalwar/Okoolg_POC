package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.Properties;

import com.atmecs.glooko.utility.LoadPages;

//Action for clicking on Dayview
public class TapOnDayView {

	String closeTutorialScreenbtn = null;
	String viewDayView = null;

	public void tapOnDayview(AppiumDriver<MobileElement> driver)
			throws IOException {

		LoadPages pageObject = new LoadPages();
		Properties page = pageObject.getObjectRepository("dayViewId.properties");

		// Tap on DayView
		viewDayView = page.getProperty("dayViewButton");
		driver.findElementByName(viewDayView).click();
		
	
	}

}
