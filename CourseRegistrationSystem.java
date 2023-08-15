import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * Singleton pattern
 * 
 * @author admin
 *
 */
public class CourseRegistrationSystem {
	/** students information and the registered courses ids*/
	private final String studentFileName = "students.ser";
	
	/** administrator information*/
	private final String adminFileName = "admins.ser";
	
	/** the course information */
	private final String courseFileName = "courses.ser";
	
	/** all students */
	private List<Student> students;
	
	/** all administrators */
	private List<Administrator> admins;
	
	/** all courses */
	private List<Course> courses;
	
	/** courseid -> students that register this course*/
	private Map<String, Set<Student> > registInfo; //
	private static CourseRegistrationSystem instance = new CourseRegistrationSystem();
	
	 public static CourseRegistrationSystem getInstance() {
	      return instance;
	}
	 
	private CourseRegistrationSystem() {
		students = new ArrayList<>();
		admins = new ArrayList<>();
		courses = new ArrayList<>();
		registInfo = new HashMap<>();
		init();
	}
	
	/**
	 * serialize all data into disk
	 * */	
	public void save() {
		try {
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(studentFileName));
			for(Student student : this.students) {
				outStream.writeObject(student);
			}
			outStream.writeObject(null);
			outStream.close();			
			
			outStream = new ObjectOutputStream(new FileOutputStream(adminFileName));
			for(Administrator admin : this.admins) {
				outStream.writeObject(admin);
			}
			outStream.writeObject(null);
			outStream.close();
			
			outStream = new ObjectOutputStream(new FileOutputStream(courseFileName));
			for(Course course : this.courses) {
				outStream.writeObject(course);
			}
			outStream.writeObject(null);
			outStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * read serialized data from disk
	 */
	private void init()  {
		File fileStudent = new File(studentFileName);
		File fileCourse = new File(courseFileName);
		File fileAdmin = new File(adminFileName);
		
		if(!fileStudent.exists() || !fileCourse.exists() || !fileAdmin.exists()) {
			Student s1 = new Student("s1", "s1p", "Tom", "133");
			Student s2 = new Student("s2", "s2p", "Jerry", "133");
			Administrator admin1 = new Administrator("a1", "a1p", "Doctor", "999");
			Course c1 = new Course("c1", "Java", "Right", 2);
			Course c2 = new Course("c2", "Math", "MathAllRight", 2);
			try {
				ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(studentFileName));
				outStream.writeObject(s1);
				outStream.writeObject(s2);
				outStream.writeObject(null);
				outStream.close();
				
				outStream = new ObjectOutputStream(new FileOutputStream(adminFileName));
				outStream.writeObject(admin1);
				outStream.writeObject(null);
				outStream.close();
				
				outStream = new ObjectOutputStream(new FileOutputStream(courseFileName));
				outStream.writeObject(c1);
				outStream.writeObject(c2);
				outStream.writeObject(null);
				outStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
			
		try {
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(studentFileName));
			while(true) {
				Student student = (Student) inStream.readObject();
				if(student == null) {
					break;
				}
				this.students.add(student);
				System.out.println(student.getName());
			}
			inStream.close();
			
			inStream = new ObjectInputStream(new FileInputStream(adminFileName));
			while(true) {
				Administrator administrator = (Administrator) inStream.readObject();
				if(administrator == null) {
					break;
				}
				this.admins.add(administrator);
			}
			inStream.close();
			
			inStream = new ObjectInputStream(new FileInputStream(courseFileName));
			while(true) {
				Course course = (Course) inStream.readObject();
				if(course == null) {
					break;
				}
		        this.courses.add(course);
		        this.registInfo.put(course.getCourseId(), new HashSet<>());
			}
			inStream.close();
			
			for(Student student : this.students) {
				for(String cid : student.getCourses()) {
					this.registInfo.get(cid).add(student);
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	/***
	 * One student tries to login. If success, the student GUI is shown.
	 * @param id student id
	 * @param pwd student name
	 */
	public void studentLogin(String id, String pwd) {
		for(Student student : this.students) {
			if(student.equals(id,  pwd)) {
				StudentGUI studentGUI = new StudentGUI(student);
				studentGUI.setVisible(true);
				break;
			}
		}
	}
	
	/***
	 * One administrator tries to login. If success, the Administrator GUI is shown.
	 * @param id
	 * @param pwd
	 */
	public void adminLogin(String id, String pwd) {
		for(Administrator admin : this.admins) {
			if(admin.equals(id, pwd)) {
				AdministratorGUI adminGUI = new AdministratorGUI(admin);
				adminGUI.setVisible(true);
				break;
			}
		}
	}

	/***
	 * Course getter
	 * @return the course list
	 */
	public List<Course> getCourses() {
		return courses;
	}
	
	/***
	 * Add one course. 
	 * If the instructor is HT or HeadTeacher, then the new course is ONE_TO_ONE course.
	 * Otherwise it is the NORMAL course.
	 * @param id course id
	 * @param name course name
	 * @param instructor course instructor. 
	 */
	public void addCourse(String id, String name, String instructor) {
		if(!this.registInfo.containsKey(id)) {
			Course course = null;
			if(instructor.equalsIgnoreCase("HeadTeacher") || instructor.equalsIgnoreCase("HT")) {
				course = new CourseBuilderOne(id, name).build();
			} else {
				course = new CourseBuilderNormal(id, name, instructor).build();
			}
			this.courses.add(course);
		    this.registInfo.put(course.getCourseId(), new HashSet<>());
		}  else {
		    for(Course oldCourse : courses) {
		    	if(oldCourse.getCourseId().equals(id)) {
		    	    oldCourse.setCourseName(name);
		    	    oldCourse.setInstructor(instructor);
		    	}
		    }
		}
	}
	
	/***
	 * A student register a course.
	 * @param student the student
	 * @param course the course
	 */
	public void registCourse(Student student, Course course) {
		String cid = course.getCourseId();
		
		if(this.registInfo.containsKey(cid)) {
			Set<Student> students = this.registInfo.get(cid);
			if(!students.contains(student)) {
				students.add(student);
				student.addCourse(course);
			}
		}
	}
	
	/***
	 * A student unregister a course.
	 * @param student the student
	 * @param course the course
	 */
	public void unRegistCourse(Student student, Course course) {
		String cid = course.getCourseId();
		
		if(this.registInfo.containsKey(cid)) {
			Set<Student> students = this.registInfo.get(cid);
			if(students.contains(student)) {
				students.remove(student);
				student.delCourse(course);
			}
		}
	}
	
	/***
	 * Get the course object with specified course id
	 * @param cid course id
	 * @return the course object
	 */
	public Course getCourse(String cid) {
		for(Course course : this.courses) {
			if(course.getCourseId().equals(cid)) {
				return course;
			}
		}
		return null;
	}
	
	/**
	 * For the registered student count for one course.
	 * @param course the course
	 * @return the student count
	 */
	public int getRegistCount(Course course) {
		String cid = course.getCourseId();
		
		if(this.registInfo.containsKey(cid)) {
			return this.registInfo.get(cid).size();
		}
		return 0;
	}

	/**
	 * Generate report on the disk with name "reports.txt".
	 */
	public void genReport() {
		try {
			FileOutputStream outStream = new FileOutputStream(new File("reports.txt"));
			OutputStreamWriter writer = new OutputStreamWriter(outStream);
			writer.write("Students Information:" + System.lineSeparator());
			for(Student student : this.students) {
				writer.write(String.format("id:%s,name:%s,phone:%s" + System.lineSeparator(), 
						student.getId(), student.getName(), student.getPhone()));
				for(String cid : student.getCourses()) {
					Course course = this.getCourse(cid);
					writer.write(String.format("  ** %s,%s,%s" + System.lineSeparator(), 
							course.getCourseId(), course.getCourseName(), course.getInstructor()));
				}
			}
			
			writer.write("----------" + System.lineSeparator());
			writer.write("Courses Information:" + System.lineSeparator());
			for(Course course : this.courses) {
				Set<Student> students = this.registInfo.get(course.getCourseId());
				writer.write(String.format("id:%s,name:%s,instructor:%s,max students:%d,current students:%d" + System.lineSeparator(), 
						course.getCourseId(), course.getCourseName(), course.getInstructor(), 
						course.getMaxStudent(), students.size()));
				for(Student student : students) {
				    writer.write(String.format("  ** %s,%s,%s" + System.lineSeparator(), 
				    		student.getId(), student.getName(), student.getPhone()));
				}
				
			}
			writer.close();
			outStream.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
}
