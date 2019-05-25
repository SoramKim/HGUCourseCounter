package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String,Integer> semestersByYearAndSemester;
	
	public Student(String studentId) {
		this.studentId= studentId;
		coursesTaken = new ArrayList<Course>();
		semestersByYearAndSemester = new HashMap<String,Integer>();
	}
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int semester=1;
		for(Course course : coursesTaken) {
			String temp1= Integer.toString(course.getYearTaken());
			String temp2= Integer.toString(course.getSemesterCourseTaken());
			String temp3= temp1+"-"+ temp2;
			
			if(!semestersByYearAndSemester.containsKey(temp3)) {
				semestersByYearAndSemester.put(temp3, semester);
				semester++;
			}
			
		}
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		int count= 0;
		for(Course course : coursesTaken) {
			String temp1= Integer.toString(course.getYearTaken());
			String temp2= Integer.toString(course.getSemesterCourseTaken());
			String temp3= temp1+ "-" +temp2;
			
			int tSemester= semestersByYearAndSemester.get(temp3);
			if(semester== tSemester) {
				count++;
			}
		}
		return count;
	}
	
}