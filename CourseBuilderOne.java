
/***
 * This is one by one course builder.
 * The instructor is "HeadTeacher" and max student is 1.
 * @author admin
 *
 */
public class CourseBuilderOne extends ACourseBuilder {
	
	public CourseBuilderOne(String id, String name) {
		super(id, name, "HeadTeacher");
	}
	
	public Course build() {
		return new Course(id, name, instructor, 1);
	}

}
