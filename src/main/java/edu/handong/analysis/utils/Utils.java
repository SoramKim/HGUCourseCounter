package edu.handong.analysis.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {

	public static ArrayList<CSVRecord> getLines(String file, boolean removeHeader) {
		ArrayList<CSVRecord> lines = new ArrayList<CSVRecord>();
		CSVParser csvParser = null;
		Reader csvReader = null;

		
		try {
			csvReader = Files.newBufferedReader(Paths.get(file));
			if(removeHeader) {
				csvParser = new CSVParser(csvReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			}
			else csvParser = new CSVParser(csvReader, CSVFormat.DEFAULT);
		} catch (IOException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
		for(CSVRecord csvRecord : csvParser) {
			lines.add(csvRecord);
		}
		
		return lines;
	}

	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		
		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(targetFileName);
		} catch (IOException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
		for(String s : lines) {
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
		} catch (IOException e) {
			e.getMessage();
		}


        
	}

}