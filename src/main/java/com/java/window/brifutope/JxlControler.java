package com.java.window.brifutope;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.read.biff.BiffException;

public class JxlControler {
	
	static Map<Integer, String> data;	
	JxlOpener xlsFile;
	static CsvWriter csvFile;
	String [] fileLocationTable;
	boolean agreement;

	JxlControler() throws IOException, BiffException 
	 {
		 this.data = new HashMap <Integer, String>();
		 this.csvFile = new CsvWriter();
		 xlsFile = new JxlOpener();
		 this.fileLocationTable = new String [] {"","","",""};
		 this.agreement = false;
	 }
	
	// DATA OPERATIONS
	
	public void toCollectData() throws BiffException, IOException
	// Collect data from ".xls" file
	{
		xlsFile.toSetFileLocationTable(fileLocationTable);
		data = xlsFile.collectData();
	}
	
	public void toClearData()
	// Clear the hashmap
	{
		data.clear();
	}
	
	//CSV DATA OPERATIONS
	public void toOverWrite(int s)
	// Overwrites or not the CSV file
	{
		// if agreed to overwrite
		if(s == 1)
		{
			csvFile.setOverwrite(true);
		}
		//if did not agreed to overwrite
		else if(s == 0)
		{
			csvFile.setOverwrite(false);
		}
//		else
//		{
//			agreement = true;
//		}
	}
	
	public boolean toFileExistCheck()
	// Checking an existance of CSV file
	{
		return csvFile.toFileExistCheck();
	}
	
	public void setCsvData() throws BiffException, IOException
	//Creates CSV file with the name, location and data
	{
		csvFile.toCreateCsvWriter(data, fileLocationTable[2] + "\\" + fileLocationTable[3]);
	}
    
	public void toSaveData() throws BiffException, IOException
	// Prints data the the CSV file
	{
	    csvFile.toCsvFile();
	}

	//ENVIROMENTAL DATA AND FILE OPERATIONS
	public void toSetFileLocationTable(String [] s)
	// File location table setter
	{
		fileLocationTable = s;
	}
	
	public String [] toGetFileLocationTable()
	// File location table getter
	{
		return fileLocationTable;
	}
	
	public String getEnviromentVariable()
	// Get enviromental data
	{
    	Map<String, String> a = System.getenv();
    	String interim="";
    	for(String u : a.keySet())
    	{
    		if (u.toLowerCase().equals("username"))
    		{
    			interim = a.get(u).toString();
    		}
    	}
		return interim;
	}
	
	public String removeXls()
	// Remove file type ".xls" from the file name
	{		
		String interim = fileLocationTable[3];
		interim = interim.substring(0,interim.length() - 4);
		return interim;
	}
    

	
}
