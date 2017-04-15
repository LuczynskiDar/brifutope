package com.java.window.brifutope;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.Workbook;

public class JxlOpener 
{
	
	 String [] header;
	 String cellData;
	 Map<Integer, String> container;	 
	 Workbook xlBook;
	 Sheet xlSheet;
	 Cell xlCell;
	 String cellContents;
	 int xlColumnsNo;
	 int xlRowsNo;
	 String [] fileLocationTable;
	 String [] dict;
	 String quote;
	 StringBuilder contents2;


	 JxlOpener() throws IOException, BiffException
	 {
		 this.header = new String[] {"item no","att","disp","change","reason"};
		 this.container = new HashMap <Integer, String>();
		 
		 //Dictionaries
		 this.dict = new String [] {",","\n"};
	 }
	 
//	 private Sheet xlOpen()
//	 {
//		 Sheet xls = xlBook.getSheet(0);
//		 return xls;
//	 }
	 
	 private void xlClose()
	 {
		 xlBook.close();
	 }
	 
	 public void toSetFileLocationTable(String [] t)
	 {
		 fileLocationTable=t;
	 }
	 
	 public String toSetPath()
	 {
		String t = fileLocationTable[0] + "\\" + fileLocationTable[1];
		return t;
	 }
	 
	 private void setWorkBook() throws BiffException, IOException
	 {
		 this.xlBook = Workbook.getWorkbook(new File(toSetPath()));
	 }
	 
	 private String toWrapSpecialChars(String data)
	 {
		 
		 if(data.toString().contains(dict[0]))
		 {
			 data=data.replace(dict[0]," ");
		 }
		 if(data.toString().contains(dict[1]))
		 {
			 data=data.replace(dict[1]," ");
		 }	
		 return data;
		 
	 }
	 
	 private String readData(int c, int r) throws BiffException, IOException {
		//odczyt kolumny 1 (item no), - (Att 0 lub 1), 12 (disp), 14 (change), 15 (reason)
//		Sheet arkusz = skoroszyt.getSheet(0);
//		xlSheet = xlBook.getSheet(0);
//		Cell komorka = xlSheet.getCell(0, 0);
//		setWorkBook();

		xlCell = xlSheet.getCell(c, r);
		cellContents = xlCell.getContents();
//		System.out.println(cellContents);
//		xlBook.close();
		return cellContents;	
	}
	
	public  Map<Integer, String> collectData() throws BiffException, IOException
	{
		setWorkBook();
		xlSheet = xlBook.getSheet(0);
		xlColumnsNo = xlSheet.getColumns();
		xlRowsNo = xlSheet.getRows();		
		for(int j=1; j<xlRowsNo; j++)
		{
			cellData="";
			List<String> list = new ArrayList<String>();
			String columnPartNo;
			
			// Walk through columns
			for(int k = 0; k<xlColumnsNo; k++)
			{
				String rowZero = readData(k, 0).toLowerCase();	
				
				//Walk through dictionary
				for(int s = 0; s<header.length; s++)
				{
					if(rowZero.equals(header[s]))
					{
						if(rowZero.equals(header[header.length-1]))	
						{
							// To write final column
							String contents = readData(k, j);
							String temp = toWrapSpecialChars(contents);
							cellData += temp;
							list.add(rowZero);							
						}
						else
						{
							// To write inside column							
							// If no Att column
							if(rowZero.equals(header[2]) && list.size() == 1)
							{
								cellData += "0" + ",";
								String contents = readData(k, j);
								String temp = toWrapSpecialChars(contents);
								cellData +=  temp + ",";
								list.add(rowZero);
							}
							
							// If there are other columns as in header
							else
							{
								String contents = readData(k, j);
								if(contents.isEmpty())
								{
									cellData += "0" + ",";
								}
								else
								{
									//Att column contain data
									if(rowZero.equals(header[1]))
									{
										// y = 1 or n = 0, other data switched to 0
										switch (contents) {
										case "y":
											cellData += "1" + ",";											
											break;
										case "n":
											cellData += "0" + ",";											
											break;
										default:
											cellData += "0" + ",";	
											break;
										}
									}
									else
									{
										String temp = toWrapSpecialChars(contents);
										cellData +=  temp + ",";										
									}
									
								}

								list.add(rowZero);
							}

						}							
						
					}
				
				}				
			}
			list.clear();
			container.put(j, cellData);
		}

		xlClose();

		return container;
		
	}
	
	
	// For code evalation
	public void printer()
	{
		int k=0;
		for( String i : container.values())
			{
			System.out.println("Line " + k);	
			System.out.println(i);
			k+=1;
			}
			
	}

	
}
