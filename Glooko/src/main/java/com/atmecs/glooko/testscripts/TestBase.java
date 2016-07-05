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
	
	 protected Properties page;
	 protected XlsDataProvider xls;
	 protected Swipe swipeObject;
	 protected int maxRow;
	 protected String properties;
	 protected String sheetName;
	 protected String fileName;
	 
	 public TestBase(String properties,String filename,String sheetName  ) {
		this.properties = properties;
		this.fileName = filename;
		this.sheetName = sheetName;
		
		
	}
	 
	@BeforeClass
	 public void initializer() throws IOException
	 {
		    pageObject = new LoadPages();
	        page=pageObject.getObjectRepository(properties);
	    	xls=new XlsDataProvider(fileName, sheetName);
	    	maxRow=	xls.getRowCount(fileName, sheetName);

	 }
	@AfterClass 
    public void deIntializer()
	 {
		    pageObject =null;
	    	page=null;
	    	xls=null;
	    	swipeObject=null;
	 }
}
