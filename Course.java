import java.util.ArrayList;
import java.util.List;

/***
 * A Course
 * @author admin
 *
 */
public class Course implements java.io.Serializable {
	private String courseId;
	private String courseName;
	private String instructor; //instructor id
	private int maxStudent;
	
	public Course() {
		
	}
	
	public Course(String id, String cn, String i, int ms) {
		this.courseId = id;
		this.courseName = cn;
		this.instructor = i;
		this.maxStudent = ms;		
	}

    @Override
	public String toString() {
    	int count = CourseRegistrationSystem.getInstance().getRegistCount(this);
		return "Course [" + courseId + " - " + courseName + ", instructor=" + instructor
				+ ", maxStudent=" + maxStudent + ", current=" + count + "]";
	}

	// Getters & Setters
	public String getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getMaxStudent() {
		return maxStudent;
	}

	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}
}
