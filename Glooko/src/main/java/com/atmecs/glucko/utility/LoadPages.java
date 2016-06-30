package com.atmecs.glucko.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPages implements Constants{

	Properties page = new Properties();

	// Method for reading data from object.proporties

	public Properties getObjectRepository(String fileName) throws IOException {	
		String str=fileName;
		 final InputStream stream = new FileInputStream(new
				 File(PAGEPATH+ str));
		 page.load(stream);
		return page;
	}

}