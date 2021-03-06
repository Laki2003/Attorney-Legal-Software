package GUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import schema.Case;
import schema.Task;
import javax.swing.JTextField;

import schema.Task.PRIORITY;
import schema.Task.STATUST;
public class TaskFrame extends JFrame {

	private JPanel contentPane;
	private JTextField taskId;
	private JTextField taskName;
	

	public TaskFrame(Task task) {
		setTitle("Add Task");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TaskFrame.class.getResource("/GUI/Pictures/icon.jpg")));
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Description:");
		lblNewLabel.setBounds(10, 122, 68, 14);
		getContentPane().add(lblNewLabel);
		
		JTextArea description = new JTextArea();
		description.setBounds(85, 117, 152, 83);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
getContentPane().add(description);
		
	

JLabel lblNewLabel_9 = new JLabel("task ID:");
lblNewLabel_9.setBounds(10, 11, 68, 14);
contentPane.add(lblNewLabel_9);

taskId = new JTextField();
taskId.setEditable(false);
taskId.setBounds(75, 8, 158, 20);
contentPane.add(taskId);
taskId.setColumns(10);

JLabel lblNewLabel_1 = new JLabel("Task name:");
lblNewLabel_1.setBounds(10, 55, 68, 14);
contentPane.add(lblNewLabel_1);

taskName = new JTextField();
taskName.setBounds(75, 52, 162, 20);
contentPane.add(taskName);
taskName.setColumns(10);

		
		JLabel lblNewLabel_2 = new JLabel("Case:");
		lblNewLabel_2.setBounds(10, 215, 55, 14);
		getContentPane().add(lblNewLabel_2);
	
	ArrayList<Case> caseList = Case.find(null, null, "");
	Vector<String> caseVector = new Vector<String>();
	caseVector.add("");
	if(caseList!=null)
	for(int i = 0;i<caseList.size();i++){
		caseVector.add(new String(caseList.get(i).getId()));
	}
		JComboBox<String> caseId = new JComboBox<>(caseVector);
		caseId.setBounds(75, 211, 162, 22);
		getContentPane().add(caseId);
	
		
		JLabel lblNewLabel_5 = new JLabel("Date:");
		lblNewLabel_5.setBounds(314, 55, 62, 14);
		getContentPane().add(lblNewLabel_5);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		JFormattedTextField date = new JFormattedTextField(dateFormat);
	
		date.setBounds(386, 52, 121, 20);
		getContentPane().add(date);
		
		JLabel lblNewLabel_7 = new JLabel("Status:");
		lblNewLabel_7.setBounds(314, 95, 62, 14);
		getContentPane().add(lblNewLabel_7);
		
		JComboBox<STATUST> status = new JComboBox<>();
		status.setBounds(396, 91, 121, 22);
		status.setModel(new DefaultComboBoxModel<>(STATUST.values()));
		getContentPane().add(status);
		status.setEnabled(false);
		JLabel lblNewLabel_8 = new JLabel("Priority:");
		lblNewLabel_8.setBounds(314, 139, 62, 14);
		getContentPane().add(lblNewLabel_8);
		
		JComboBox<PRIORITY> priority = new JComboBox<>();
		priority.setBounds(396, 135, 121, 22);
		priority.setModel(new DefaultComboBoxModel<>(PRIORITY.values()));
		getContentPane().add(priority);

		if(task !=null){
			setTitle("Update Task");
			description.setText(task.getDescription());
			date.setText(task.getDate().toString());
			status.setSelectedItem(task.getStatus());
			priority.setSelectedItem(task.getPriority());
			if(task.getCase()==null){
				caseId.setSelectedIndex(0);
			}else{
			for(int i = 0;i<caseList.size();i++){
				if(caseList.get(i).getId().equals(task.getCase().getId())){
					caseId.setSelectedIndex(i+1);
				}
			}
		}
			taskName.setText(task.getTaskName());
			taskId.setText(task.getId());
		}
		


		JButton save = new JButton("SAVE");
		save.setBounds(420, 386, 137, 60);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(caseId.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(contentPane, "You didn't fill all fields!");
					return;
				}
				long millis=System.currentTimeMillis(); 
				if(Date.valueOf(date.getText()).compareTo(new Date(millis))<0){
					status.setSelectedIndex(1);
					}
					else{
						status.setSelectedIndex(0);
					}
				if(task==null){
				Task newTask = new Task(taskName.getText(), description.getText(),
				caseList.get(caseId.getSelectedIndex()-1),PRIORITY.valueOf(priority.getSelectedItem().toString()),
				 STATUST.valueOf(status.getSelectedItem().toString()), Date.valueOf(date.getText()));
				 newTask.save();
				}else{
task.setTaskName(taskName.getText());
task.setDescription(description.getText());
task.setCase(caseList.get(caseId.getSelectedIndex()-1));
task.setPriority(PRIORITY.valueOf(priority.getSelectedItem().toString()));
task.setStatus(STATUST.valueOf(status.getSelectedItem().toString()));
task.setDate(Date.valueOf(date.getText()));
task.update();
}
dispose();
			}
		});
		getContentPane().add(save);
		
		JButton cancel = new JButton("CANCEL");
		cancel.setBounds(266, 386, 146, 60);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		 dispose();
			}
		});
		getContentPane().add(cancel);
		
		
	}
}
