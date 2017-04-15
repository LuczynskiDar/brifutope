package com.java.window.brifutope;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class CsvWriter {

	String csvName;
    Map<Integer, String> container;	
	File f;
	boolean overwrite;
	
	
	CsvWriter() throws IOException
	 {
		this.overwrite = true;
	 }
	
	public Map<Integer, String> getContainer()
	// Getter which returns hashmap with data
	{
		return container;
	}
	
	public void setContainer(Map<Integer, String> c)
	// Hashmap setter
	{
		this.container = c;
	}
	
	public String getCsvName()
	// Getter of the CSV file name
	{
		return csvName;				
	}
	
	public void setCsvName(String s)
	// Setter of the CSV file name
	{
		this.csvName = s;
	}
	
	public void toCreateCsvWriter(Map<Integer, String> c, String s) throws IOException	
	// Creates a CSV file and sets file name and  data to the container
	{
		setContainer(c);
		setCsvName(s);
		this.f = new File(csvName);
	}
	
	public boolean toFileExistCheck()
	// Checks if CSV file exists
	{
		if(f.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public void setOverwrite(boolean ov)
	// Set CSV file overwrite boolean value
	{
		overwrite = ov;
	}	
	
	public void toCsvFile() throws IOException
	// Print data to the CSV file
	{
		PrintWriter csvSave = null;	
		try {
			// Overwrites existing one
			if(overwrite){
				// When true, then appends
				csvSave = new PrintWriter(new FileWriter(csvName, true));
	            for(String u : container.values())
	            {
	            	csvSave.println(u);
	            }
			}
			//Saves to the new file
			else
			{
				//When false then overwrites
				csvSave = new PrintWriter(new FileWriter(csvName, false));
	            for(String u : container.values())
	            {
	            	csvSave.println(u);
	            }
			}
		}
		
		finally {
			if (csvSave != null) 
			{
				csvSave.close();
			}
        }
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