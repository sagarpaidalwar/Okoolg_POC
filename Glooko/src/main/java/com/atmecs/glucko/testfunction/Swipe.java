package com.atmecs.glucko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.Dimension;

import com.atmecs.glucko.utility.Constants;

public class Swipe implements Constants{
	Dimension size;
	
	public void swipeLeftToRight(AppiumDriver<MobileElement> driver)
	{
		 //Get the size of screen.
		  size = driver.manage().window().getSize();
		  
		//Find startx point which is at right side of screen.
		  int startx = (int) (size.width * 0.70);
		  //Find endx point which is at left side of screen.
		  int endx = (int) (size.width * 0.30);
		  //Find vertical point where you wants to swipe. It is in middle of screen height.
		  int starty = size.height / 2;
		  driver.swipe(endx, starty, startx, starty, SWIPETIMERIGHTTOLEFT);
		  
	}


}
