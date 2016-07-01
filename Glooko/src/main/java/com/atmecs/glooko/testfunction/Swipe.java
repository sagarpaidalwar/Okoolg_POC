package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.Dimension;

import com.atmecs.glooko.utility.Constants;

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
		  driver.swipe(endx, starty, startx, starty, SWIPETIME);
		  
	}
	public void swipeTopToBottom(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		 //Get the size of screen.
		  size = driver.manage().window().getSize();
		   
		  //Find swipe start and end point from screen's with and height.
		  //Find starty point which is at bottom side of screen.
		  int starty = (int) (size.height * 0.90);
		  //Find endy point which is at top side of screen.
		  int endy = (int) (size.height * 0.50);
		  //Find horizontal point where you wants to swipe. It is in middle of screen width.
		  int startx = size.width / 2;
		  System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);

		   Thread.sleep(500);
		  //Swipe from Top to Bottom.
		  driver.swipe(startx, endy, startx, starty, SWIPETIME);
	}


}
