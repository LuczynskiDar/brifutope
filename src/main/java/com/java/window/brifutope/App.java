package com.java.window.brifutope;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.java.window.brifutope.JxlOpener;
import com.java.window.brifutope.BriFutoPeWindow;
import com.java.window.brifutope.CsvWriter;

import jxl.read.biff.BiffException;

public class App 
{
 	static BriFutoPeWindow peWindow;	
	
    public static void main( String [] args ) throws BiffException, IOException 
    // App starter
    {
    	peWindow = new BriFutoPeWindow();
    	peWindow.start();
    }   
}
