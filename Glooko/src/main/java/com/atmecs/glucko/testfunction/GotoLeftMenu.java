package com.atmecs.glucko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.IOException;
import java.util.Properties;

import com.atmecs.glucko.utility.LoadPages;

public class GotoLeftMenu {

	LoadPages pageObject = new LoadPages();

	// Action for tapping on left menu
	public void leftMenu(AppiumDriver<MobileElement> driver) throws IOException {

		Properties page = pageObject.getObjectRepository("dayViewId.properties");

		String clickOnLeftMenu = page.getProperty("imageButton");

		driver.findElementByClassName(clickOnLeftMenu).click();

	}
}
