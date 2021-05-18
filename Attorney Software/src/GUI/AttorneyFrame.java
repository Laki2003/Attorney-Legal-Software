package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import schema.Attorney;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JButton;

public class AttorneyFrame extends JFrame {

	private JPanel contentPane;
	private JTextField attorneyId;
	private JTextField firstName;
	private JTextField lastName;

	

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaseFrame frame = new CaseFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AttorneyFrame(Attorney attorney) {
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Attorney ID:");
		lblNewLabel.setBounds(10, 11, 68, 14);
		contentPane.add(lblNewLabel);
		
		attorneyId = new JTextField();
		attorneyId.setEditable(false);
		attorneyId.setBounds(75, 8, 158, 20);
		contentPane.add(attorneyId);
		attorneyId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("First name:");
		lblNewLabel_1.setBounds(10, 55, 68, 14);
		contentPane.add(lblNewLabel_1);
		
		firstName = new JTextField();
		firstName.setBounds(75, 52, 158, 20);
		contentPane.add(firstName);
		firstName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name:");
		lblNewLabel_2.setBounds(10, 99, 68, 14);
		contentPane.add(lblNewLabel_2);
		
		lastName = new JTextField();
		lastName.setBounds(75, 96, 158, 20);
		contentPane.add(lastName);
		lastName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Year birth:");
		lblNewLabel_3.setBounds(10, 143, 68, 14);
		contentPane.add(lblNewLabel_3);
		
		JFormattedTextField yearBirth = new JFormattedTextField();
		yearBirth.setBounds(75, 140, 158, 20);
		contentPane.add(yearBirth);
		
		JLabel lblNewLabel_4 = new JLabel("Education:");
		lblNewLabel_4.setBounds(10, 197, 68, 14);
		contentPane.add(lblNewLabel_4);
		
		JTextPane education = new JTextPane();
		education.setBounds(71, 197, 162, 152);
		contentPane.add(education);
		
		JTextPane workExperience = new JTextPane();
		workExperience.setBounds(424, 11, 162, 152);
		contentPane.add(workExperience);
		
		JLabel lblNewLabel_5 = new JLabel("Work experience:");
		lblNewLabel_5.setBounds(322, 11, 92, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Languages:");
		lblNewLabel_6.setBounds(351, 197, 63, 14);
		contentPane.add(lblNewLabel_6);
		
		JList languages = new JList();
		languages.setBounds(424, 197, 158, 159);
		contentPane.add(languages);
		
		JButton cancel = new JButton("CANCEL");
		cancel.setBounds(266, 386, 146, 60);
		contentPane.add(cancel);
		
		JButton save = new JButton("SAVE");
		save.setBounds(420, 386, 137, 60);
		contentPane.add(save);
		
	}
}
