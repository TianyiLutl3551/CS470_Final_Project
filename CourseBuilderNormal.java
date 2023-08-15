
/***
 * This is NORMAL course builder.
 * The default max student is 20.
 * The administrator specify the instructor.
 * @author admin
 *
 */
public class CourseBuilderNormal extends ACourseBuilder {
	
	public CourseBuilderNormal(String id, String name, String instructor) {
		super(id, name, instructor);
	}
	
	public Course build() {
		return new Course(id, name, instructor, 20);
	}

}
