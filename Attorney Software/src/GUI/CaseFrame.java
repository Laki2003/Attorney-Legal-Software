package GUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import schema.Attorney;
import schema.Case;
import schema.Case.PERMISSION;
import schema.Case.STATUS;
import schema.Contacts.Contacts;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

public class CaseFrame extends JFrame {

	private JPanel contentPane;
	private JTextField caseId;
	

	public CaseFrame(Case case1) {
		setVisible(true);
		setResizable(false);
		setTitle("Add Case");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CaseFrame.class.getResource("/GUI/Pictures/icon.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	getContentPane().setLayout(null);
		contentPane.setLayout(null);
		caseId = new JTextField();
		caseId.setBounds(75, 8, 158, 20);
		contentPane.add(caseId);
		caseId.setColumns(10);
			
		JLabel dateMismatch = new JLabel("Date mismatch");
		dateMismatch.setFont(new Font("Tahoma", Font.BOLD, 15));
		dateMismatch.setForeground(Color.RED);
		dateMismatch.setBounds(383, 83, 152, 26);
		contentPane.add(dateMismatch);
		dateMismatch.setVisible(false);
		
		
		JLabel lblNewLabel = new JLabel("Description:");
		lblNewLabel.setBounds(10, 55, 68, 14);
		getContentPane().add(lblNewLabel);
		
		JTextArea description = new JTextArea();
		description.setBounds(75, 55, 152, 83);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
getContentPane().add(description);
		
		JLabel lblNewLabel_1 = new JLabel("Attorney:");
		lblNewLabel_1.setBounds(10, 153, 55, 14);
		getContentPane().add(lblNewLabel_1);
		
		Vector<String> attorneyVector = new Vector<String>();
		attorneyVector.add("");
  ArrayList<Attorney> attorneyList = Attorney.find(null, null, "");
  if(attorneyList!=null)
for(int i = 0;i<attorneyList.size();i++){
	attorneyVector.add(new String(attorneyList.get(i).getFirstName() + " " + attorneyList.get(i).getLastName()));
}

		JComboBox<String> attorney = new JComboBox<>(attorneyVector);
		attorney.setBounds(75, 149, 162, 22);

	attorney.getSelectedIndex();
		getContentPane().add(attorney);
		
		JLabel lblNewLabel_2 = new JLabel("Customer:");
		lblNewLabel_2.setBounds(10, 215, 55, 14);
		getContentPane().add(lblNewLabel_2);
		Vector<String> customerVector = new Vector<String>();
		customerVector.add("");
		ArrayList<Contacts> customerList = Contacts.find(null, null, "", null, true, null, "", "", "");
		if(customerList!=null)
		for(int i = 0;i<customerList.size();i++){
			customerVector.add(new String(customerList.get(i).getName() + " " + customerList.get(i).getType().toString()));
		}
		JComboBox<String> customer = new JComboBox<>(customerVector);
		customer.setBounds(75, 211, 162, 22);
		getContentPane().add(customer);
		
		JLabel lblNewLabel_3 = new JLabel("Against:");
		lblNewLabel_3.setBounds(10, 275, 46, 14);
		getContentPane().add(lblNewLabel_3);
		Vector<String> againstVector = new Vector<String>();
		againstVector.add("");
		ArrayList<Contacts> againstList = Contacts.find(null, null, "", null, false, null, "", "", "");
		if(againstList!=null)
		for(int i = 0;i<againstList.size();i++){
			againstVector.add(new String(againstList.get(i).getName() + " " + againstList.get(i).getType().toString()));
		}
		JComboBox<String> against = new JComboBox<>(againstVector);
		against.setBounds(75, 271, 162, 22);
		getContentPane().add(against);
		Vector<String> contactsVector = new Vector<String>();
		ArrayList<Contacts> contactsList = Contacts.find(null, null, "", null, null, null, "", "", "");
		if(contactsList!=null)
		for(int i = 0;i<contactsList.size();i++){
			contactsVector.add(new String(contactsList.get(i).getName() + " " + contactsList.get(i).getTitle().toString()));
			
		}
		JList<String> contacts = new JList<>(contactsVector);
		contacts.setBounds(75, 314, 162, 169);
		
		
		JLabel lblNewLabel_4 = new JLabel("Contacts:");
		lblNewLabel_4.setBounds(10, 386, 55, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Open Date:");
		lblNewLabel_5.setBounds(314, 55, 62, 14);
		getContentPane().add(lblNewLabel_5);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		JFormattedTextField openDate = new JFormattedTextField(dateFormat);
	
		openDate.setBounds(386, 52, 121, 20);
		getContentPane().add(openDate);
		
		JLabel lblNewLabel_6 = new JLabel("Close Date:");
		lblNewLabel_6.setBounds(314, 134, 62, 14);
		getContentPane().add(lblNewLabel_6);
		
		JFormattedTextField closeDate = new JFormattedTextField(dateFormat);
		closeDate.setBounds(386, 131, 121, 20);
		getContentPane().add(closeDate);
		
		JLabel lblNewLabel_7 = new JLabel("Status:");
		lblNewLabel_7.setBounds(314, 211, 62, 14);
		getContentPane().add(lblNewLabel_7);
		
		JComboBox<STATUS> status = new JComboBox<>();
		status.setBounds(386, 211, 121, 22);
		status.setModel(new DefaultComboBoxModel<>(STATUS.values()));
		getContentPane().add(status);
		status.setEnabled(false);
		
		JLabel lblNewLabel_8 = new JLabel("Permission:");
		lblNewLabel_8.setBounds(314, 275, 62, 14);
		getContentPane().add(lblNewLabel_8);
		
		JComboBox<PERMISSION> permission = new JComboBox<>();
		permission.setBounds(386, 271, 121, 22);
		permission.setModel(new DefaultComboBoxModel<>(PERMISSION.values()));
		getContentPane().add(permission);
		
		if(case1!=null){
			setTitle("Update Case");
			description.setText(case1.getDescription());
			if(case1.getAttorney()==null){
				attorney.setSelectedIndex(0);
			}
			else{
			for(int i = 0;i<attorneyList.size();i++){
				if(attorneyList.get(i).getId().equals(case1.getAttorney().getId())){
					attorney.setSelectedIndex(i+1);
				}
			}
		}
		if(case1.getCustomer() == null){
			customer.setSelectedIndex(0);
		}else{
			for(int i = 0;i<customerList.size();i++){
				if(customerList.get(i).getId().equals(case1.getCustomer().getId())){
					customer.setSelectedIndex(i+1);
				}
			}
		}
		if(case1.getAgainst()==null){
			against.setSelectedIndex(0);
		}else{
			for(int i = 0;i<againstList.size();i++){
				if(againstList.get(i).getId().equals(case1.getAgainst().getId())){
					against.setSelectedIndex(i+1);
				}
			}
		}
			openDate.setText(case1.getOpenDate().toString());
			closeDate.setText(case1.getCloseDate().toString());
			permission.setSelectedItem(case1.getPermission());
			status.setSelectedItem(case1.getStatus());
			
			Vector<Integer> selectedIndiciesContacts = new Vector<Integer>();
			for(int i = 0;i<case1.getPersons().size();i++){
				if(case1.getPersons().get(i)==null){
					case1.getPersons().remove(i);
			}
		}
			for(int i=0;i<contactsList.size();i++){
				for(int j = 0; j<case1.getPersons().size(); j++){
				if(contactsList.get(i).getId().equals(case1.getPersons().get(j).getId())){
					selectedIndiciesContacts.add(i);
				}
				}

			}
			
			int[] arr = new int[selectedIndiciesContacts.size()];
			for(int i = 0;i<selectedIndiciesContacts.size();i++){
			arr[i] = selectedIndiciesContacts.get(i);
			}
			
			contacts.setSelectedIndices(arr);
						caseId.setText(case1.getId());
			caseId.setEditable(false);
		}
		getContentPane().add(contacts);


		JButton save = new JButton("SAVE");
		save.setBounds(420, 386, 137, 60);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(customer.getSelectedIndex() == 0 || against.getSelectedIndex() == 0 || attorney.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(contentPane, "You didn't fill all fields!");
					return;
				}
				ArrayList<Contacts> contactss = new ArrayList<Contacts>();
				int[] indexes =  contacts.getSelectedIndices();
				for(int i = 0; i<indexes.length;i++){
					contactss.add(contactsList.get(indexes[i]));
				}
				if(Date.valueOf(openDate.getText()).compareTo(Date.valueOf(closeDate.getText()))>0){
				dateMismatch.setVisible(true);
					return;
				}
				long millis=System.currentTimeMillis();  
				if(Date.valueOf(openDate.getText()).compareTo(new Date(millis))>0){
status.setSelectedIndex(2);
				}
				else if(Date.valueOf(closeDate.getText()).compareTo(new Date(millis))<0){
				status.setSelectedIndex(1);
				}
				else{
					status.setSelectedIndex(0);
				}
				if(case1==null){
				
				
					Case newCase = new Case(caseId.getText(), description.getText(), attorneyList.get(attorney.getSelectedIndex()-1), customerList.get(customer.getSelectedIndex()-1), againstList.get(against.getSelectedIndex()-1),
					contactss,Date.valueOf(openDate.getText()),Date.valueOf(closeDate.getText()), STATUS.valueOf(status.getSelectedItem().toString()), PERMISSION.valueOf(permission.getSelectedItem().toString()));
					newCase.save();
				}else{
case1.setDescription(description.getText());
case1.setPersons(contactss);
case1.setAttorney(attorneyList.get(attorney.getSelectedIndex()-1));
case1.setCustomer(customerList.get(customer.getSelectedIndex()-1));
case1.setAgainst(againstList.get(against.getSelectedIndex()-1));
case1.setOpenDate(Date.valueOf(openDate.getText()));
case1.setCloseDate(Date.valueOf(closeDate.getText()));
case1.setStatus(STATUS.valueOf(status.getSelectedItem().toString()));
case1.setPermission(PERMISSION.valueOf(permission.getSelectedItem().toString()));
case1.update();
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
		
		JLabel lblNewLabel_9 = new JLabel("Case ID:");
		lblNewLabel_9.setBounds(10, 11, 68, 14);
		contentPane.add(lblNewLabel_9);
	
	
	}
}
