package com.atmecs.glooko.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcel implements Constants{
	
	File file;
    HSSFWorkbook workbook;
    FileInputStream inputStream;
	HSSFSheet sheet;
	public ReadExcel() {
	
		file = new File(EXCELPATH);
		try {
			inputStream = new FileInputStream(file);
			workbook = new HSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getValueByColumnNameAndRowNo(String columnName,int rowNo)
	{
		String cellData = "";
		int rowCell = 0;
		HSSFRow row = sheet.getRow(0);
		int maxRow = sheet.getRow(0).getLastCellNum();
		System.out.println("Max rows :: " +maxRow);
		
		while( rowCell <= maxRow ){
			String columnTitle = row.getCell(rowCell).getStringCellValue();
			if(columnTitle.equals(columnName))
			{
				DataFormatter formatter = new DataFormatter();
				HSSFCell cell = sheet.getRow(rowNo).getCell(rowCell);	
				cellData = formatter.formatCellValue(cell);
				System.out.println("Value :: " + cellData);
				
				break;
			}
			else
			{
				rowCell++;
			}
			
		}
		return cellData;
	}
	
	public int getSheetRows() throws IOException
	  {
		
		System.out.println("I am in get sheet rows");
		System.out.println(sheet.getLastRowNum());
       return(sheet.getLastRowNum() + 1);
      
      }
	
	public List<String> getColumnRows(int columnNumber) throws IOException
	{
		
	List<String> columnValues = new ArrayList<String>();
	for(Row r : sheet) {
	   Cell c = r.getCell(columnNumber);
	   if(c != null) {
	      if(c.getCellType() == Cell.CELL_TYPE_STRING) {
	         columnValues.add(c.getStringCellValue());
	      }
	   }
	   
	} 
	return columnValues;
 }
}