package edu.handong.analysis.utils;

public class NotEnoughArgumentException extends Exception {
	public NotEnoughArgumentException() {
		super ("No CLI argument Exception! Please put a file path.");	
		System.exit(0); 
	}
	public NotEnoughArgumentException(String message) {
		super ("The file path does not exist. Please check your CLI argument!");
		System.exit(0); 
		
	}

	

}
