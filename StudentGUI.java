

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/***
 * GUI for a student.
 * The upper is the available courses.  Mouse double click for register a course.
 * The bottom is the registered courses.  Mouse double click for unregister a course.
 * @author admin
 *
 */
public class StudentGUI extends JFrame {

	private DefaultListModel<Course> availListModel;
	private DefaultListModel<Course> selectedListModel;
	private Student student;
	
	public StudentGUI(Student student) {
		this.student = student;
		this.setTitle(String.format("Welcome %s-%s", student.getId(), student.getName()));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel topLabel = new JLabel("Available Courses");
		topLabel.setBounds(0, 10, 400, 16);
		contentPane.add(topLabel);
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, topLabel.getY() + topLabel.getHeight(), 600, 100);
		topPanel.setBackground(Color.white);
		contentPane.add(topPanel);
		
		availListModel = new DefaultListModel<>();		
		JList<Course> listAvailBox = new JList<>(availListModel);		
		topPanel.add(listAvailBox);
		listAvailBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Course course = getSelectedCourse(event);
				if(course != null) {
				    CourseRegistrationSystem.getInstance().registCourse(student, course);
				    CourseRegistrationSystem.getInstance().save();
				    refresh();
				}
			}
		});
		
		JLabel bottomLabel = new JLabel("You already selected courses");
		bottomLabel.setBounds(0, topPanel.getY() + topPanel.getHeight() + 20, 400, 16);
		contentPane.add(bottomLabel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, bottomLabel.getY() + bottomLabel.getHeight(), 600, 100);
		bottomPanel.setBackground(Color.green);
		contentPane.add(bottomPanel);
		selectedListModel = new DefaultListModel<>();		
		JList<Course> listStudentBox = new JList<>(selectedListModel); 		
		bottomPanel.add(listStudentBox);
		listStudentBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Course course = getSelectedCourse(event);
				if(course != null) {
				    CourseRegistrationSystem.getInstance().unRegistCourse(student, course);
				    CourseRegistrationSystem.getInstance().save();
				    refresh();
				}
			}
		});
		
		refresh();
	}
	
	private Course getSelectedCourse(MouseEvent event) {
		if(event.getClickCount() == 2) {
			JList parent = (JList) event.getSource();
			return (Course) parent.getModel().getElementAt(parent.getSelectedIndex());
		}
		return null;
	}
	
	private void refresh() {
		availListModel.clear();
		for(Course course : CourseRegistrationSystem.getInstance().getCourses()) {
			if(!student.isSelected(course)) {
				availListModel.addElement(course);
			}
		}
		
		selectedListModel.clear();
		for(String cid : this.student.getCourses()) {
			selectedListModel.addElement(CourseRegistrationSystem.getInstance().getCourse(cid));
		}
	}
}
