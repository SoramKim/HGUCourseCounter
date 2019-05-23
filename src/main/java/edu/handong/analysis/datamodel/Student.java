package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> courseTaken;
	private HashMap<String,Integer> semestersByYearAndSemester;
	
	public Student(String studentId) {
		
	}
	public void addCourse(Course newRecord) {
		
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		return semester;
	}
	
}