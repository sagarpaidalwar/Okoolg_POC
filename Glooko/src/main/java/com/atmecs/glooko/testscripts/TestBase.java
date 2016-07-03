package com.atmecs.glooko.testscripts;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;
import com.atmecs.falcon.automation.mobileui.dataprovider.XlsDataProvider;
import com.atmecs.glooko.testfunction.AddNote;
import com.atmecs.glooko.testfunction.Swipe;
import com.atmecs.glooko.testfunction.VerifyFoodAndMedicine;
import com.atmecs.glooko.utility.LoadPages;

public class TestBase  extends UserBaseTest{
	 protected LoadPages pageObject;
	 protected AddNote addNote; 
	 protected VerifyFoodAndMedicine verify;
	 protected Properties page;
	 protected XlsDataProvider xls;
	 protected Swipe swipeObject;
	 protected int maxRow;
	 
	 @BeforeClass
	 public void initializer() throws IOException
	 {
		    pageObject = new LoadPages();
	    	addNote=new AddNote();
	    	page=pageObject.getObjectRepository("AddFood.properties");
	    	verify=new VerifyFoodAndMedicine();
	    	xls=new XlsDataProvider("AddNote.xls", "AddNote");
	    	swipeObject=new Swipe();
	    	maxRow=xls.getRowCount("AddNote.xls", "AddNote");
	 }
	 @AfterClass
    public void deIntializer()
	 {
		 pageObject =null;
	    	addNote=null;
	    	page=null;
	    	xls=null;
	    	swipeObject=null;
	 }
}
