package com.atmecs.glooko.testfunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.Dimension;

import com.atmecs.glooko.utility.Constants;

public class Swipe implements Constants{
	Dimension size;
	float startPoint;
	float endPoint; 
	int divideScreen=2;
	
	public void swipeLeftToRight(AppiumDriver<MobileElement> driver)
	{
		startPoint=0.30f;
		endPoint=0.70f;
		
		 //Get the size of screen.
		  size = driver.manage().window().getSize();
		  
		  //Find startx point which is at right side of screen.
		  int startx = (int) (size.width * endPoint);
		
		  //Find endx point which is at left side of screen.
		  int endx = (int) (size.width * startPoint);
		  
		  //Find vertical point where you wants to swipe. It is in middle of screen height.
		  int starty = size.height / divideScreen;
		  driver.swipe(endx, starty, startx, starty, SWIPETIME);
		  
	}
	public void swipeTopToBottom(AppiumDriver<MobileElement> driver) throws InterruptedException
	{
		startPoint=0.50f;
		endPoint=0.90f;
		
		 //Get the size of screen.
		  size = driver.manage().window().getSize();
		   
		  //Find starty point which is at bottom side of screen.
		  int starty = (int) (size.height * endPoint);
		 
		  //Find endy point which is at top side of screen.
		  int endy = (int) (size.height * startPoint);
		  
		  //Find horizontal point where you wants to swipe. It is in middle of screen width.
		  int startx = size.width / divideScreen;

		  //Swipe from Top to Bottom.
		  driver.swipe(startx, endy, startx, starty, SWIPETIME);
	}


}
