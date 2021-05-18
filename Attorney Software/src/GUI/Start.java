package GUI;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import schema.Attorney;
import schema.Bill;
import schema.Case;
import schema.Contacts.Contacts;
import schema.Contacts.Contacts.TITLE;

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

public class Start extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField search;
	private JTextField addressTxt;
	private JTextField phoneTxt;
	private JTextField emailTxt;
	private int operation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Start() {
		operation = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setBounds(305, 181, 374, 337);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(598, 135, 65, 28);
		
		JButton taskBtn = new JButton("Tasks");
		taskBtn.setBounds(145, 44, 140, 23);
		
		JButton billBtn = new JButton("Bills");
		billBtn.setBounds(285, 44, 136, 23);
		
		JButton contactBtn = new JButton("Contacts");
		contactBtn.setBounds(421, 44, 140, 23);
		
		JButton attorneyBtn = new JButton("Attorney");
		attorneyBtn.setBounds(561, 44, 140, 23);
		attorneyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton caseBtn = new JButton("Cases");
	
		caseBtn.setBounds(5, 44, 140, 23);
		
		JButton exitBtn = new JButton("Logout & Exit");
		exitBtn.setBounds(5, 458, 126, 51);
		
		search = new JTextField();
		search.setBounds(277, 135, 303, 28);
		search.setToolTipText("");
		search.setColumns(10);
		contentPane.setLayout(null);
		contentPane.add(exitBtn);
		contentPane.add(table);
		contentPane.add(caseBtn);
		contentPane.add(taskBtn);
		contentPane.add(billBtn);
		contentPane.add(contactBtn);
		contentPane.add(attorneyBtn);
		contentPane.add(search);
		contentPane.add(searchBtn);
		
		JButton updateBtn = new JButton("Update");
		
		updateBtn.setBounds(5, 422, 89, 23);
		contentPane.add(updateBtn);
		
		JButton deleteBtn = new JButton("Delete");
		
		deleteBtn.setBounds(104, 424, 89, 23);
		contentPane.add(deleteBtn);
		
		JButton addBtn = new JButton("Add");
	
		addBtn.setBounds(5, 388, 89, 23);
		contentPane.add(addBtn);
		
		JLabel searchLabel = new JLabel("Search:");
		searchLabel.setBounds(231, 142, 46, 14);
		contentPane.add(searchLabel);
		
		JPanel contactsPanel = new JPanel();
		contactsPanel.setBackground(Color.WHITE);
		contactsPanel.setBounds(10, 181, 209, 192);
		contentPane.add(contactsPanel);
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
		
		JComboBox<TITLE> titleCombo = new JComboBox<>();
		titleCombo.setModel(new DefaultComboBoxModel<>(TITLE.values()));
		titleCombo.setBounds(97, 134, 86, 22);
		contactsPanel.add(titleCombo);
		
		JLabel titleLabel = new JLabel("title:");
		titleLabel.setBounds(10, 138, 46, 14);
		contactsPanel.add(titleLabel);
		contactsPanel.setVisible(false);

		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if(operation == 0){
		Case.find(table, null, search.getText());
			}
			else if(operation == 1){
				//Task.find(table, search.getText())
			}
			else if(operation == 2){
				Bill.find(table, search.getText());
			}
			else if(operation == 3){
				Contacts.find(table, null, search.getText(), TITLE.valueOf(titleCombo.getSelectedItem().toString()),
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
				operation = 0;
			}
		});
		taskBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				operation = 1;
			}
		});
		billBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				operation = 2;
			}
		});
		contactBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(true);
				operation = 3;
			}
		});
			attorneyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contactsPanel.setVisible(false);
				operation = 4;
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
				}
				else if(operation == 3){
				}
				else if(operation == 4){
				}
				
			}
		});
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(operation == 0){

				}
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
					
				}
				else if(operation == 1){
				}
				else if(operation == 2){
				}
				else if(operation == 3){
				}
				else if(operation == 4){
					new AttorneyFrame(Attorney.find(null, model.getValueAt(table.getSelectedRow(), 0).toString(), "").get(0));
				}
			}
		});
	}
}
