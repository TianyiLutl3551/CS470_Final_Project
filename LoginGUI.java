import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/***
 * Display login GUI. 
 * A Student or Administrator can login.
 * @author admin
 *
 */
public class LoginGUI extends JFrame {

	
	public LoginGUI() {
		CourseRegistrationSystem instance = CourseRegistrationSystem.getInstance();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 235);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Please Login");
		titleLabel.setBounds(117, 16, 176, 16);
		contentPane.add(titleLabel);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(41, 55, 99, 16);
		contentPane.add(idLabel);
		
		JLabel pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(41, 92, 61, 16);
		contentPane.add(pwdLabel);
		
		JTextField ldText = new JTextField();
		ldText.setBounds(143, 50, 130, 26);
		contentPane.add(ldText);
		ldText.setColumns(10);
		
		JTextField pwdText = new JTextField();
		pwdText.setBounds(143, 87, 130, 26);
		contentPane.add(pwdText);
		pwdText.setColumns(10);
		
		JButton studentButton = new JButton("Student");
		studentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseRegistrationSystem.getInstance().studentLogin(ldText.getText().trim(), pwdText.getText().trim());
				//CourseRegistrationSystem.getInstance().studentLogin("s1", "s1");
				dispose();
			}
		});
		
		studentButton.setBounds(41, 152, 117, 29);
		contentPane.add(studentButton);
		
		JButton adminButton = new JButton("Administrator");
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseRegistrationSystem.getInstance().adminLogin(ldText.getText().trim(), pwdText.getText().trim());
				//CourseRegistrationSystem.getInstance().adminLogin("a1", "a1p");
				dispose();
			}
		});
		adminButton.setBounds(182, 152, 117, 29);
		contentPane.add(adminButton);
	}
	
	public static void main(String[] args) {
		LoginGUI w0 = new LoginGUI();
		w0.setVisible(true);
		//w0.show();		
	}
}
