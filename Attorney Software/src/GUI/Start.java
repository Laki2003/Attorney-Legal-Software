package GUI;

import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import schema.Account;
import schema.Attorney;
import schema.Bill;
import schema.Case;
import schema.Task;
import schema.Bill.PAYMENT;
import schema.Contacts.Contacts;
import schema.Contacts.Contacts.TITLE;
import schema.Task.PRIORITY;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Start extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField search;
	private JTextField addressTxt;
	private JTextField phoneTxt;
	private JTextField emailTxt;
	private int operation;

	public Start(Attorney attorney, Account account) {
		operation = 0;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Start.class.getResource("/GUI/Pictures/icon.jpg")));
		setTitle("Start");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 567);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		table = new JTable();
	
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JButton searchBtn = new JButton("Search");
		
		JButton taskBtn = new JButton("Tasks");
		
		JButton billBtn = new JButton("Bills");
		
		JButton contactBtn = new JButton("Contacts");
		
		JButton attorneyBtn = new JButton("Attorney");
		attorneyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton caseBtn = new JButton("Cases");
		
		JButton exitBtn = new JButton("Logout & Exit");
		
		search = new JTextField();
		search.setToolTipText("");
		search.setColumns(10);
		
		JButton updateBtn = new JButton("Update");
		
		JButton deleteBtn = new JButton("Delete");
		
		JButton addBtn = new JButton("Add");
		
		JLabel searchLabel = new JLabel("Search:");
		
		JPanel contactsPanel = new JPanel();
		contactsPanel.setBackground(Color.WHITE);
		contactsPanel.setLayout(null);
		
		JLabel addressLabel = new JLabel("address:");
		addressLabel.setBounds(10, 14, 62, 14);
		contactsPanel.add(addressLabel);
		
		addressTxt = new JTextField();
		addressTxt.setBounds(97, 8, 86, 20);
		contactsPanel.add(addressTxt);
		addressTxt.setColumns(10);
		
		JLabel phoneLbl = new JLabel("phone:");
		phoneLbl.setBounds(10, 45, 46, 14);
		contactsPanel.add(phoneLbl);
		
		phoneTxt = new JTextField();
		phoneTxt.setBounds(97, 39, 86, 20);
		contactsPanel.add(phoneTxt);
		phoneTxt.setColumns(10);
		
		JLabel emailLbl = new JLabel("email:");
		emailLbl.setBounds(10, 76, 46, 14);
		contactsPanel.add(emailLbl);
		
		emailTxt = new JTextField();
		emailTxt.setBounds(97, 70, 86, 20);
		contactsPanel.add(emailTxt);
		emailTxt.setColumns(10);
		
		JCheckBox customerCheck = new JCheckBox("customer");
		customerCheck.setBounds(10, 97, 97, 23);
		contactsPanel.add(customerCheck);
		
		JPanel comboPanel = new JPanel();
		comboPanel.setBackground(Color.WHITE);
		comboPanel.setForeground(Color.WHITE);
		comboPanel.setLayout(null);
		
		JLabel comboLabel = new JLabel("title:");
		comboLabel.setBounds(10, 11, 46, 14);
		comboPanel.add(comboLabel);
		
		JComboBox<TITLE> comboTitle = new JComboBox<>();
		comboTitle.setBounds(97, 7, 86, 22);
		comboPanel.add(comboTitle);
		comboTitle.setModel(new DefaultComboBoxModel<>(TITLE.values()));
		
		JComboBox<PRIORITY> comboPriority = new JComboBox<>();
		comboPriority.setBounds(97, 7, 86, 22);
		comboPanel.add(comboPriority);
		comboPriority.setModel(new DefaultComboBoxModel<>(PRIORITY.values()));

		JComboBox<PAYMENT> comboPayment = new JComboBox<>();
		comboPayment.setBounds(97, 7, 86, 22);
		comboPanel.add(comboPayment);
		contactsPanel.setVisible(false);
		comboPanel.setVisible(false);
		comboPayment.setModel(new DefaultComboBoxModel<>(PAYMENT.values()));
		
		JLabel usernameLabel = new JLabel("username:");
		usernameLabel.setText("");
		JLabel attorneyLabel = new JLabel("Attorney:");
		attorneyLabel.setText("Attorney: "+attorney.getFirstName() + " " + attorney.getLastName());
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(226)
					.addComponent(searchLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addComponent(search, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(searchBtn))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(contactsPanel, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(comboPanel, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
						.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(updateBtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(deleteBtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(exitBtn, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
					.addGap(86)
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
					.addGap(22))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(attorneyLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(caseBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addComponent(taskBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addComponent(billBtn, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
					.addComponent(contactBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addComponent(attorneyBtn, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(attorneyLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(caseBtn)
						.addComponent(taskBtn)
						.addComponent(billBtn)
						.addComponent(contactBtn)
						.addComponent(attorneyBtn))
					.addGap(68)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(searchLabel))
						.addComponent(search, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchBtn, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(contactsPanel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addComponent(comboPanel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(50)
							.addComponent(addBtn)
							.addGap(11)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(updateBtn)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(2)
									.addComponent(deleteBtn)))
							.addGap(11)
							.addComponent(exitBtn, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
						.addComponent(table, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
					.addGap(5))
		);
		contentPane.setLayout(gl_contentPane);

		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if(operation == 0){
		Case.find(table, null, search.getText());
			}
			else if(operation == 1){
				Task.find(table,"", search.getText(), PRIORITY.valueOf(comboPriority.getSelectedItem().toString()));
			}
			else if(operation == 2){
				Bill.find(table,null, search.getText(), PAYMENT.valueOf(comboPayment.getSelectedItem().toString()));
			}
			else if(operation == 3){
				Contacts.find(table, null, search.getText(), TITLE.valueOf(comboTitle.getSelectedItem().toString()),
				customerCheck.isSelected(), null, addressTxt.getText(), phoneTxt.getText(), emailTxt.getText());
			}
			else if(operation == 4){
						Attorney.find(table, null, search.getText());
			}
			}
		});
		caseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				comboPanel.setVisible(false);
				operation = 0;
				caseBtn.setForeground(new ColorUIResource(255, 0, 0));
				taskBtn.setForeground(new ColorUIResource(0, 0, 0));
				billBtn.setForeground(new ColorUIResource(0, 0, 0));
				contactBtn.setForeground(new ColorUIResource(0, 0, 0));
				attorneyBtn.setForeground(new ColorUIResource(0, 0, 0));

			}
		});
		taskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				comboPriority.setVisible(true);
				comboPayment.setVisible(false);
				comboTitle.setVisible(false);
				comboPanel.setVisible(true);
							operation = 1;
				caseBtn.setForeground(new ColorUIResource(0, 0, 0));
				taskBtn.setForeground(new ColorUIResource(255, 0, 0));
				billBtn.setForeground(new ColorUIResource(0, 0, 0));
				contactBtn.setForeground(new ColorUIResource(0, 0, 0));
				attorneyBtn.setForeground(new ColorUIResource(0, 0, 0));
			}
		});
		billBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				comboPriority.setVisible(false);
				comboPayment.setVisible(true);
				comboTitle.setVisible(false);
				comboPanel.setVisible(true);
				operation = 2;
				caseBtn.setForeground(new ColorUIResource(0, 0, 0));
				taskBtn.setForeground(new ColorUIResource(0, 0, 0));
				billBtn.setForeground(new ColorUIResource(255, 0, 0));
				contactBtn.setForeground(new ColorUIResource(0, 0, 0));
				attorneyBtn.setForeground(new ColorUIResource(0, 0, 0));
			}
		});
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(true);
				comboPriority.setVisible(false);
				comboPayment.setVisible(false);
				comboTitle.setVisible(true);
				comboPanel.setVisible(true);
				operation = 3;
				caseBtn.setForeground(new ColorUIResource(0, 0, 0));
				taskBtn.setForeground(new ColorUIResource(0, 0, 0));
				billBtn.setForeground(new ColorUIResource(0, 0, 0));
				contactBtn.setForeground(new ColorUIResource(255, 0, 0));
				attorneyBtn.setForeground(new ColorUIResource(0, 0, 0));
			}
		});
			attorneyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboPanel.setVisible(false);
				contactsPanel.setVisible(false);
				operation = 4;
				caseBtn.setForeground(new ColorUIResource(0, 0, 0));
				taskBtn.setForeground(new ColorUIResource(0, 0, 0));
				billBtn.setForeground(new ColorUIResource(0, 0, 0));
				contactBtn.setForeground(new ColorUIResource(0, 0, 0));
				attorneyBtn.setForeground(new ColorUIResource(255, 0, 0));
			}
		});
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operation == 0){
					new CaseFrame(null);
				}
				else if(operation == 1){
				new TaskFrame(null);
				}
				else if(operation == 2){
					new BillFrame(null);
				}
				else if(operation == 3){
					new ContactsFrame(null);
				}
				else if(operation == 4){
					try {
						new AttorneyFrame(null);
					} catch (ParseException e1) {
			
						e1.printStackTrace();
					}
				}
				
			}
		});
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(contentPane, "You didn't select row!");
					return;
				}
				
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(operation == 0){
					Case.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "").get(0).delete();
				}
				else if(operation == 1){
					Task.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "", null).get(0).delete();
				}
				else if(operation == 2){
					Bill.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "", null).get(0).delete();
				}
				else if(operation == 3){
					Contacts.findById(model.getValueAt(table.getSelectedRow(), 0).toString()).delete();
				}
				else if(operation == 4){
				Attorney a =Attorney.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "").get(0);
					if(!a.getId().equals(account.getId())){
						JOptionPane.showMessageDialog(contentPane, "You don't have permission to delete this attorney/account!", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int result = JOptionPane.showConfirmDialog(contentPane, "Are you sure you wanna delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
				attorney.delete();
					account.delete();
					System.exit(0);
					} 
				}
				model.removeRow(table.getSelectedRow());
				
			}
		});
	
		updateBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(contentPane, "You didn't select row!");
					return;
				}
				
				
				TableModel model = table.getModel();
			
				
				if(operation == 0){
					new CaseFrame(Case.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "").get(0));
				}
				else if(operation == 1){
					new TaskFrame(Task.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "", null).get(0));
				}
				else if(operation == 2){
					new BillFrame(Bill.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "", null).get(0));
				}
				else if(operation == 3){
					new ContactsFrame(Contacts.findById(model.getValueAt(table.getSelectedRow(), 0).toString()));
				}
				else if(operation == 4){
					try {
						new AttorneyFrame(Attorney.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "").get(0));
					} catch (ParseException e1) {
					
						e1.printStackTrace();
					}
				}
			}
		});
		exitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				URL url;
				try {
					url = new URL("https://cdn.quotesgram.com/img/47/46/593648175-Ally-McBeal-quotes1111.gif");
					int dialogResult = JOptionPane.showConfirmDialog(contentPane, "", null, JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, new ImageIcon(url));
					if(dialogResult == JOptionPane.YES_OPTION){
						System.exit(0);
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				

			
			}
		});
	}
}
