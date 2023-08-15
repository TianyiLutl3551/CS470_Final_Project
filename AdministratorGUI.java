

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AdministratorGUI extends JFrame {
	private DefaultListModel<Course> courseListModel;
	private JTextField idText;
	private JTextField nameText;
	private JTextField instructorText;
	
	private Administrator administrator;
	
	public AdministratorGUI(Administrator administrator) {
		this.administrator = administrator;
	    this.setTitle(String.format("Welcome Administrator", administrator.getId(), administrator.getName()));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel topLabel = new JLabel("All Courses");
		topLabel.setBounds(0, 10, 400, 16);
		contentPane.add(topLabel);
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, topLabel.getY() + topLabel.getHeight(), 600, 100);
		topPanel.setBackground(Color.white);
		contentPane.add(topPanel);
		
		courseListModel = new DefaultListModel<>();		
		JList<Course> courseBox = new JList<>(courseListModel);		
		topPanel.add(courseBox);
		
		JLabel idLabel = new JLabel("course id", SwingConstants.RIGHT);
		idLabel.setBounds(10, topPanel.getY() + topPanel.getHeight() + 20, 100, 16);
		contentPane.add(idLabel);
		idText = new JTextField();
		idText.setBounds(idLabel.getX() + idLabel.getWidth() + 8, idLabel.getY() - 5, 130, 26);
		contentPane.add(idText);
		idText.setColumns(10);
		
		JLabel nameLabel = new JLabel("course name", SwingConstants.RIGHT);
		nameLabel.setBounds(idLabel.getX(), idLabel.getY() + idLabel.getHeight() + 20, 100, 16);
		contentPane.add(nameLabel);
		nameText = new JTextField();
		nameText.setBounds(idText.getX(), nameLabel.getY() - 5, 130, 26);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		JLabel instructorLabel = new JLabel("instructor", SwingConstants.RIGHT);
		instructorLabel.setBounds(idLabel.getX(), nameLabel.getY() + nameLabel.getHeight() + 20, 100, 16);
		contentPane.add(instructorLabel);
		instructorText = new JTextField();
		instructorText.setBounds(idText.getX(), instructorLabel.getY() - 5, 130, 26);
		contentPane.add(instructorText);
		instructorText.setColumns(10);
		
	    JButton addButton = new JButton("Add or Update Course");
	    addButton.setBounds(idLabel.getX(), instructorText.getY() + instructorText.getHeight() + 16, instructorText.getX() + instructorText.getWidth(), 30);
	    contentPane.add(addButton);
	    addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idText.getText().trim().isEmpty() || nameText.getText().trim().isEmpty()
						|| instructorText.getText().trim().isEmpty()) {
					return;
				}
				
				CourseRegistrationSystem.getInstance().addCourse(idText.getText().trim(), nameText.getText().trim(),
						instructorText.getText().trim());
				System.out.println("add new course " + idText.getText());
				CourseRegistrationSystem.getInstance().save();
				refresh();
			}
		});
		
	    
	    JButton reportButton = new JButton("Generate Reports");
	    reportButton.setBounds(addButton.getX() + addButton.getWidth() + 40, addButton.getY(), addButton.getWidth(), 30);
	    contentPane.add(reportButton);
	    reportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CourseRegistrationSystem.getInstance().genReport();
			}
	    	
	    });
		refresh();
	}
	
	private void refresh() {
		courseListModel.clear();
		for(Course course : CourseRegistrationSystem.getInstance().getCourses()) {
			courseListModel.addElement(course);
		}
		
		idText.setText("");
		nameText.setText("");
		instructorText.setText("");
	}
}
