
/***
 * Builder pattern.
 * The concrete builder class will build concrete course.
 * @author admin
 *
 */
abstract public class ACourseBuilder {
	protected String id;
	protected String name;
	protected String instructor;

	public ACourseBuilder(String id, String name, String instructor) {
		this.id = id;
		this.name = name;
		this.instructor = instructor;
	}
	
	abstract public Course build();
}
