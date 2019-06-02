package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVRecord;

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
	
	public Course(CSVRecord line) {
		studentId = line.get(0);
		yearMonthGraduated = line.get(1);
		firstMajor = line.get(2);
		secondMajor = line.get(3);
		courseCode = line.get(4);
		courseName = line.get(5);
		courseCredit = line.get(6);
		yearTaken = Integer.parseInt(line.get(7));
		semesterCourseTaken = Integer.parseInt(line.get(8));	
		
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
	
	public String getCourseCode() {
		return courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
}
