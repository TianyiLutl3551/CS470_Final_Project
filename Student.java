import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * A Student and register or unregister a course.
 * @author admin
 *
 */
public class Student extends APerson {

	/** the registered courses id set*/
	private List<String> courses; 
	
	public Student() {
		this(null, null, null, null);
	}
	public Student(String id, String pwd, String name, String phone) {
		super(id, pwd, name, phone);
		this.courses = new ArrayList<>();
	}
	
	public void addCourse(Course course) {
		courses.add(course.getCourseId());
	}
	public void delCourse(Course course) {
		courses.remove(course.getCourseId());
	}
	public List<String> getCourses() {
		return this.courses;
	}
	
	public boolean isSelected(Course course) {
		return this.courses.contains(course.getCourseId());
	}
}
