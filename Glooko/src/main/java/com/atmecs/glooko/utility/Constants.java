package com.atmecs.glooko.utility;

import java.io.File;


public interface Constants{

	int WAIT_TIME = 2000;
	int WEBDRIVER_WAIT_TIME = 180;
	int ELEMENT_WAIT_TIME = 50;
	int LONG_WAIT=400;
	int SWIPETIME=500;
	int colNo=0;
	String PAGEPATH=System.getProperty("user.dir") +File.separator+""+File.separator+"resources"+File.separator+""+File.separator+"locators"+File.separator+""+File.separator;
	String EXCELPATH=System.getProperty("user.dir") +File.separator+""+File.separator+"src"+File.separator+""+File.separator+"main"+File.separator+""+File.separator+"java"+File.separator+""+File.separator+"com"+File.separator+""+File.separator+"atmecs"+File.separator+""+File.separator+"glucko"+File.separator+""+File.separator+"testdata"+File.separator+""+File.separator+"AddNote.xls";
    
}
