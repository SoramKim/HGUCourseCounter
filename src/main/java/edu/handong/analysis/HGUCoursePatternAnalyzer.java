package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	String input;
	String output;
	int analysis;
	String coursecode;
	int startyear;
	int endyear;
	boolean help;
	
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			
			String dataPath = input; // csv file to be analyzed
			String resultPath = output; // the file path where the results are saved.
			ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
			
			
			students = loadStudentCourseRecords(lines);
			
			// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
			Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
			
			if (help){
				printHelp(options);
				return;
			}
			
	
			if(analysis == 1) {
				
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);
			
			}
			else if(analysis == 2) {
				
		
				String year;
				String semester;
				String yearAndSemester;
				String courseCode;
				String courseName;
				int numOfCourseTakenStudents;
				int numOfRegisteredStudents;
				double rate;
				String rateString;
				
				HashMap<String, Integer> numberOfCourseTakenStudents;
				HashMap<String, Integer> numberOfRegisteredStudents;
				ArrayList<String> linesToBeSaved = new ArrayList<String>();
				
				courseCode = coursecode;
				courseName = getCourseName(sortedStudents, courseCode);
				

				numberOfCourseTakenStudents = getNumberOfCourseTakenStudents(sortedStudents);
				numberOfRegisteredStudents = getNumberOfRegisteredStudents(sortedStudents, numberOfCourseTakenStudents);
				
				Map<String, Integer> sortednumberOfCourseTakenStudents = new TreeMap<String, Integer>(numberOfCourseTakenStudents);
				Map<String ,Integer> sortednumberOfRegisteredStudents = new TreeMap<String, Integer>(numberOfRegisteredStudents);
				
				Set<String> yearCoursesTaken = sortednumberOfCourseTakenStudents.keySet();
				Object[] yearCoursesTakenArray = yearCoursesTaken.toArray();
				linesToBeSaved.add("Year,Semester,CouseCode,CourseName,RegisteredStudents,TakenStudents,Rate");
				
				for(Object o : yearCoursesTakenArray) {
					
					yearAndSemester = o.toString();
					year = yearAndSemester.trim().split("-")[0];
					semester = yearAndSemester.trim().split("-")[1];
					numOfCourseTakenStudents = sortednumberOfCourseTakenStudents.get(yearAndSemester);
					numOfRegisteredStudents = sortednumberOfRegisteredStudents.get(yearAndSemester);
					rate = (double) numOfCourseTakenStudents/ (double) numOfRegisteredStudents;
					rateString = Double.toString(Math.round(rate*10)/10.0);//소수점 한자리로 출력
					
					linesToBeSaved.add(year + "," + semester + "," + courseCode + "," + courseName + "," + Integer.toString(numOfRegisteredStudents) + ","+ Integer.toString(numOfCourseTakenStudents) + "," + rateString);
				}
				
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);
				
			}
			 
		}
	


	
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {
		
		// TODO: Implement this method
		students = new HashMap<String,Student>();
		String tempStudentId;
		Student student= null;
		boolean exist;
		int tempYear;
		
		for(CSVRecord line : lines) {
			tempStudentId= line.get(0);
			tempYear = Integer.parseInt(line.get(7));
			
			exist= students.containsKey(tempStudentId);
			
			if(tempYear >= startyear && tempYear <= endyear) {
				if(exist== false) {
				student = new Student(tempStudentId);
				Course course = new Course(line);
				student.addCourse(course);
				
				students.put(tempStudentId,student);
				}
				else {
					Course course = new Course(line);
					student.addCourse(course);
				}
			
			}
			
		}
		
		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		ArrayList<String> linesToBeSaved = new ArrayList<String>();
		
		Collection collection = sortedStudents.values();
		Iterator iter = collection.iterator();
		Student student;
		
		String studentId;
		int TotalNumberOfSemestersRegistered;
		int Semester;
		int NumCoursesTakenInTheSemester;
		String tempLine;
		String head= "StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester";
		linesToBeSaved.add(head);
		
		
		while(iter.hasNext()) {
			student=(Student) iter.next();
			studentId= student.getstudentId();
			TotalNumberOfSemestersRegistered= student.getSemestersByYearAndSemester().size();
			
			for(int i=1; i<=TotalNumberOfSemestersRegistered;i++) {
				Semester=i; 
				NumCoursesTakenInTheSemester=student.getNumCourseInNthSementer(Semester);
				tempLine= studentId +","+ Integer.toString(TotalNumberOfSemestersRegistered)+","+Integer.toString(Semester)+","+Integer.toString(NumCoursesTakenInTheSemester);
				linesToBeSaved.add(tempLine);
			}
				
			
		}
		
		return linesToBeSaved; 
}

private	HashMap<String, Integer> getNumberOfCourseTakenStudents(Map<String, Student> sortedStudents) {
	HashMap<String, Integer> numberOfCourseTakenStudents = new HashMap<String, Integer>();
	Student student = null;
	ArrayList<Course> tempCoursesTaken;
	int year;
	int semester;
	String yearAndSemester;
	int numberOfStudents;
	
	Collection<Student> collection = sortedStudents.values();
	Iterator<Student> iter = collection.iterator();
	while(iter.hasNext()) {
		student = iter.next();
		tempCoursesTaken = student.getCoursesTaken();
		
		for(Course c : tempCoursesTaken) {
			if(coursecode.equals(c.getCourseCode())) {
				year = c.getYearTaken();
				semester = c.getSemesterCourseTaken();
				yearAndSemester = Integer.toString(year) + "-" + Integer.toString(semester);
				if(numberOfCourseTakenStudents.containsKey(yearAndSemester)) {
					numberOfStudents = numberOfCourseTakenStudents.get(yearAndSemester);
					numberOfCourseTakenStudents.replace(yearAndSemester, numberOfStudents+1);
				}
				else {
					numberOfCourseTakenStudents.put(yearAndSemester, 1);
				}
			}
		}
	}
	
	return numberOfCourseTakenStudents;
}

private HashMap<String, Integer> getNumberOfRegisteredStudents(Map<String, Student> sortedStudents, HashMap<String, Integer> numberOfCourseTakenStudents){
	HashMap<String, Integer> numberOfRegisteredStudents = new HashMap<String, Integer>();
	Student student = null;
	int numberOfStudents;
	
	Collection<Student> collection = sortedStudents.values();
	Iterator<Student> iter = collection.iterator();
	Set<String> yearSemester = numberOfCourseTakenStudents.keySet();
	Object[] yearSemesterArray = yearSemester.toArray();
	for(Object a : yearSemesterArray) {
		numberOfRegisteredStudents.put(a.toString(), 0);
	}
	
	while(iter.hasNext()) {
		student = iter.next();
		student.getSemestersByYearAndSemester();
		for(Object a : yearSemesterArray) {
			if(student.isRegistered(a.toString())) {
				numberOfStudents = numberOfRegisteredStudents.get(a.toString());
				numberOfRegisteredStudents.replace(a.toString(), numberOfStudents+1);
			}
		}
	}
	
	return numberOfRegisteredStudents;
}

private String getCourseName(Map<String, Student> sortedStudents, String courseCode) {
	String courseName = null;
	Student student = null;
	boolean exist = false;
	ArrayList<Course> tempCourse = null;
	
	
	Collection<Student> collection = sortedStudents.values();
	Iterator<Student> iter = collection.iterator();
	
	while(iter.hasNext()){
		student = iter.next();
		tempCourse = student.getCoursesTaken();
		
		for(Course tempCourses : tempCourse) {
			if(courseCode.equals(tempCourses.getCourseCode())){
				courseName = tempCourses.getCourseName();
				exist = true;
				break;
			}
		}
		if(exist == true) break;	
	}
	
	return courseName;
}






private boolean parseOptions(Options options, String[] args) {
	CommandLineParser parser = new DefaultParser();

	try {

		CommandLine cmd = parser.parse(options, args);
		
		input = cmd.getOptionValue("i");
		output = cmd.getOptionValue("o");
		analysis = Integer.parseInt(cmd.getOptionValue("a"));
		
		if(analysis == 2) {
			coursecode = cmd.getOptionValue("c");
			
			
		}
		
		startyear = Integer.parseInt(cmd.getOptionValue("s"));
		endyear = Integer.parseInt(cmd.getOptionValue("e"));
		help = cmd.hasOption("h");
		
	
	} catch (Exception e) {
		printHelp(options);
		return false;
	}

	return true;
}

// Definition Stage
private Options createOptions() {
	Options options = new Options();

	// add options by using OptionBuilder
	options.addOption(Option.builder("i").longOpt("input")
			.desc("Set an input file path")
			.hasArg()
			.argName("Input path")
			.required()
			.build());

	// add options by using OptionBuilder
	options.addOption(Option.builder("o").longOpt("output")
			.desc("Set an output file path")
			.hasArg()     
			.argName("Output path")
			.required() 
			.build());
	
	// add options by using OptionBuilder
	options.addOption(Option.builder("a").longOpt("analysis")
			.desc("1: Count courses per semester, 2: Count per course name and year")
			.hasArg()
			.argName("Analysis option")
			.required()
			.build());
	
	// add options by using OptionBuilder
	options.addOption(Option.builder("c").longOpt("coursecode")
			.desc("Course code for '-a 2' option")
			.hasArg()
			.argName("course code")
			.required(analysis == 2)
			.build());
	
	// add options by using OptionBuilder
	options.addOption(Option.builder("s").longOpt("startyear")
			.desc("Set the start year for analysis e.g., -s 2002")
			.hasArg()
			.argName("Start year for analysis")
			.required()
			.build());		

	// add options by using OptionBuilder
	options.addOption(Option.builder("e").longOpt("endyear")
			.desc("Set the end year for analysis e.g., -e 2005")
			.hasArg()
			.argName("End year for analysis")
			.required()
			.build());
	
	// add options by using OptionBuilder
	options.addOption(Option.builder("h").longOpt("help")
	        .desc("Show a Help page")
	        //.hasArg()
	        .argName("Help")
			//.required()
	        .build());
	
	return options;
}
  private void printHelp(Options options) {
	// automatically generate the help statement
	HelpFormatter formatter = new HelpFormatter();
	String header = "HGU Course Analyzer";
	String footer = "";
	formatter.printHelp("HGUCourseCounter", header, options, footer, true);
  }
}
