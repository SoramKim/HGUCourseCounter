package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		String[] tempString;
		
		tempString = line.trim().split(",");
		
		studentId= tempString[0].trim();
		yearMonthGraduated= tempString[1].trim();
		firstMajor= tempString[2].trim();
		secondMajor= tempString[3].trim();
		courseCode= tempString[4].trim();
		courseName= tempString[5].trim();
		courseCredit= tempString[6].trim();
		yearTaken= Integer.parseInt(tempString[7].trim());
		semesterCourseTaken= Integer.parseInt(tempString[8].trim());
		
		
	}
	
	public String getStudentId() {
		return studentId;
	}
	
	public int getYearTaken() {
		return yearTaken;
	}
	
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
}
