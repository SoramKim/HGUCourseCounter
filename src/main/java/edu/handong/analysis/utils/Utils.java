package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		ArrayList<String> lines = new ArrayList<String>();
		String row;
		BufferedReader csvReader= null;
		
		try {
			csvReader = new BufferedReader(new FileReader(file));
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}

		
		if(removeHeader) {
		    try {
		    	csvReader.readLine();
		    		
		    } catch(IOException e) {
		    		
		    }
		}
		
		try {
			while ((row = csvReader.readLine()) != null) {  
			    lines.add(row);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	
		return lines;
		
	}
		
		
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(targetFileName);
			
		} catch(IOException e){
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);	
		}
		for(String s: lines) {
			try {
				csvWriter.write(s);
				csvWriter.append("\n");
			} catch (IOException e) {
				e.getMessage();
			}
		}
			
		
		try {
			csvWriter.flush();  
			csvWriter.close();  
		}catch(IOException e) {
		
		}
		
	}


}
