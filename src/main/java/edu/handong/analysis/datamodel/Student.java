package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> courseTaken;
	private HashMap<String,Integer> semestersByYearAndSemester;
	
	public Student(String studentId) {
		this.studentId= studentId;
		courseTaken = new ArrayList<Course>();
		semestersByYearAndSemester = new HashMap<String,Integer>();
	}
	public void addCourse(Course newRecord) {
		courseTaken.add(newRecord);
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int semester=1;
		for(Course course : courseTaken) {
			String temp1= Integer.toString(course.getYearTaken());
			String temp2= Integer.toString(course.getSemesterCourseTaken());
			String temp3= temp1+"-"+ temp2;
		}
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		int count= 0;
		for(Course course : coursesTaken) {
			String temp1= Integer.toString(course.getYearTaken());
			String temp2= Integer.toString(course.getSemesterCourseTaken());
			String temp3= temp1+ "-" temp2;
			
			
		}
		return semester;
	}
	
}