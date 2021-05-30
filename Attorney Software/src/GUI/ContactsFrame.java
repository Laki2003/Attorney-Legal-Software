package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import schema.Contacts.Contacts;
import schema.Contacts.Contact.Address;
import schema.Contacts.Contact.Email;
import schema.Contacts.Contact.Phone;
import schema.Contacts.Contact.TYPE;
import schema.Contacts.Contacts.TITLE;
import schema.Contacts.Contacts.TYPEC;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

public class ContactsFrame extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTable addressTable;
	private JTable emailTable;
	private JTable phoneTable;
	private JTextField contactId;
	private JTextField street;
	private JTextField city;
	private JTextField state;
	private JTextField zip;
	private JTextField country;
	private JTextField phone;
	private JTextField email;
    private ArrayList<Address> addresses;
    private ArrayList<Phone> phones;
    private ArrayList<Email> emails;
	
	
	
	public ContactsFrame(Contacts contact) {
	 addresses = new ArrayList<Address>();
	 phones = new ArrayList<Phone>();
	 emails = new ArrayList<Email>();
	setTitle("Add Contact");
	setIconImage(Toolkit.getDefaultToolkit().getImage(ContactsFrame.class.getResource("/GUI/Pictures/icon.jpg")));
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 768, 679);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 85, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("title:");
		lblNewLabel_1.setBounds(10, 142, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("type:");
		lblNewLabel_3.setBounds(10, 113, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		name = new JTextField();
		name.setBounds(84, 82, 120, 20);
		contentPane.add(name);
		name.setColumns(10);
		
		JCheckBox customerCheck = new JCheckBox("customer");
		customerCheck.setBounds(10, 179, 97, 23);
		contentPane.add(customerCheck);
		
		JComboBox<TYPEC> type = new JComboBox<>();
		type.setBounds(84, 109, 120, 22);
		type.setModel(new DefaultComboBoxModel<>(TYPEC.values()));
		contentPane.add(type);
		
		JComboBox<TITLE> title = new JComboBox<>();
		title.setBounds(84, 138, 120, 22);
		title.setModel(new DefaultComboBoxModel<>(TITLE.values()));
		contentPane.add(title);
		
		addressTable = new JTable();
		
		
		addressTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Address ID", "Street", "City", "State", "Country", "Zip", "Type"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	
		addressTable.setBounds(401, 85, 305, 94);
		contentPane.add(addressTable);
		
		JButton deleteAddress = new JButton("Delete Address");
		deleteAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(addressTable.getSelectedRow() == -1)
				{
					JOptionPane.showMessageDialog(contentPane, "You didn't select row!");
					return;
				}
				addresses.remove(addressTable.getSelectedRow());
			DefaultTableModel addressModel = (DefaultTableModel) addressTable.getModel();
			addressModel.removeRow(addressTable.getSelectedRow());
				if(contact!=null){
					contact.setAddresses(addresses);
					addresses.get(addressTable.getSelectedRow()).delete();
				}

			}
		});
		deleteAddress.setBounds(500, 190, 107, 23);
		contentPane.add(deleteAddress);
		
		emailTable = new JTable();
		emailTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Email ID", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		emailTable.setBounds(401, 377, 305, 94);
		contentPane.add(emailTable);
		
		phoneTable = new JTable();
		phoneTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Phone ID", "Phone", "Type"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		phoneTable.setBounds(401, 223, 305, 94);
		contentPane.add(phoneTable);
		
		JLabel lblNewLabel_2 = new JLabel("Contact ID:");
		lblNewLabel_2.setBounds(10, 60, 64, 14);
		contentPane.add(lblNewLabel_2);
		
		contactId = new JTextField();
		contactId.setEditable(false);
		contactId.setBounds(84, 57, 120, 20);
		contentPane.add(contactId);
		contactId.setColumns(10);
		
		JButton deletePhone = new JButton("Delete Phone");
		deletePhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(phoneTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(contentPane, "You didn't select row!");
					return;
				} 
				phones.get(phoneTable.getSelectedRow()).delete();
				phones.remove(phoneTable.getSelectedRow());
				DefaultTableModel phoneModel = (DefaultTableModel) phoneTable.getModel();
				phoneModel.removeRow(phoneTable.getSelectedRow());

				if(contact!=null){
					contact.setPhones(phones);
				}
			}
		});
		deletePhone.setBounds(500, 327, 107, 23);
		contentPane.add(deletePhone);
		
		JButton deleteEmail = new JButton("Delete Email");
		deleteEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(emailTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(contentPane, "You didn't select row!");
					return;
				} 
				emails.get(emailTable.getSelectedRow()).delete();
				emails.remove(emailTable.getSelectedRow());
				DefaultTableModel emailModel = (DefaultTableModel) emailTable.getModel();
				emailModel.removeRow(emailTable.getSelectedRow());
								if(contact!=null){
					contact.setEmails(emails);
				}
			}
		});
		deleteEmail.setBounds(500, 482, 107, 23);
		contentPane.add(deleteEmail);
		
		JButton cancel = new JButton("CANCEL");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(385, 550, 146, 60);
		contentPane.add(cancel);
		
		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
if(contact==null){
	Contacts newContact = new Contacts(name.getText(), TITLE.valueOf(title.getSelectedItem().toString()), customerCheck.isSelected(), TYPEC.valueOf(type.getSelectedItem().toString()));
	for(int i = 0;i<addresses.size();i++)
	addresses.get(i).setContactId(newContact.getId());
	for(int i = 0;i<phones.size();i++)
	phones.get(i).setContactId(newContact.getId());
	for(int i = 0;i<emails.size();i++)
	emails.get(i).setContactId(newContact.getId());
	newContact.setAddresses(addresses);
	newContact.setPhones(phones);
	newContact.setEmails(emails);
	newContact.save();
} else{
contact.setName(name.getText());
contact.setTitle(TITLE.valueOf(title.getSelectedItem().toString()));
contact.setCustomer(customerCheck.isSelected());
contact.setType(TYPEC.valueOf(type.getSelectedItem().toString()));
contact.setAddresses(addresses);
contact.setPhones(phones);
contact.setEmails(emails);
contact.update();
}
dispose();
			}
		});
		save.setBounds(548, 550, 137, 60);
		contentPane.add(save);
		
		JPanel addressPane = new JPanel();
		addressPane.setBounds(0, 252, 366, 128);
	
		addressPane.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Street: ");
		lblNewLabel_4.setBounds(10, 11, 46, 14);
		addressPane.add(lblNewLabel_4);
		
		street = new JTextField();
		street.setBounds(47, 8, 113, 20);
		addressPane.add(street);
		street.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("City:");
		lblNewLabel_5.setBounds(10, 45, 58, 14);
		addressPane.add(lblNewLabel_5);
		
		city = new JTextField();
		city.setBounds(47, 42, 113, 20);
		addressPane.add(city);
		city.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("State:");
		lblNewLabel_6.setBounds(10, 69, 58, 14);
		addressPane.add(lblNewLabel_6);
		
		state = new JTextField();
		state.setBounds(47, 70, 113, 20);
		addressPane.add(state);
		state.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Zip:");
		lblNewLabel_7.setBounds(170, 11, 46, 14);
		addressPane.add(lblNewLabel_7);
		
		zip = new JTextField();
		zip.setBounds(216, 8, 113, 20);
		addressPane.add(zip);
		zip.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Country:");
		lblNewLabel_8.setBounds(170, 45, 58, 14);
		addressPane.add(lblNewLabel_8);
		
		country = new JTextField();
		country.setBounds(216, 42, 113, 20);
		addressPane.add(country);
		country.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Type:");
		lblNewLabel_9.setBounds(170, 69, 46, 14);
		addressPane.add(lblNewLabel_9);
		
		JComboBox<TYPE> typeAddress = new JComboBox<>();
		typeAddress.setBounds(216, 65, 113, 22);
		typeAddress.setModel(new DefaultComboBoxModel<>(TYPE.values()));
		addressPane.add(typeAddress);
		
		JButton addAddress = new JButton("Add Address");
		addAddress.setBounds(108, 101, 120, 23);
		addressPane.add(addAddress);
		addAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Address newAddress = new Address(street.getText(), city.getText(), state.getText(), Integer.parseInt(zip.getText()),
				country.getText(), TYPE.valueOf(typeAddress.getSelectedItem().toString()), contactId.getText());
					addresses.add(newAddress);
				if(contact!=null){
				contact.setAddresses(addresses);
				newAddress.save();
							}
				
				DefaultTableModel addressModel = (DefaultTableModel) addressTable.getModel();
				addressModel.addRow(new Object[]{newAddress.getId(),street.getText(), city.getText(), state.getText(), Integer.parseInt(zip.getText()),
					country.getText(), typeAddress.getSelectedItem().toString()});
		
			}
		});
		contentPane.add(addressPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 391, 366, 128);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton addPhone = new JButton("Add Phone");
		addPhone.setBounds(50, 94, 120, 23);
		panel.add(addPhone);
		
		JLabel lblNewLabel_10 = new JLabel("Phone:");
		lblNewLabel_10.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel_10);
		
		phone = new JTextField();
		phone.setBounds(49, 8, 121, 20);
		panel.add(phone);
		phone.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("Type:");
		lblNewLabel_11.setBounds(10, 54, 46, 14);
		panel.add(lblNewLabel_11);
		
		JComboBox<TYPE> typePhone = new JComboBox<>();
		typePhone.setModel(new DefaultComboBoxModel<>(TYPE.values()));
		typePhone.setBounds(49, 50, 121, 22);
		panel.add(typePhone);
		
		JLabel lblNewLabel_12 = new JLabel("Email:");
		lblNewLabel_12.setBounds(180, 54, 46, 14);
		panel.add(lblNewLabel_12);
		
		email = new JTextField();
		email.setBounds(221, 51, 121, 20);
		panel.add(email);
		email.setColumns(10);
		
		JButton addEmail = new JButton("Add Email");
		addEmail.setBounds(221, 94, 120, 23);
		panel.add(addEmail);
		addEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Email newEmail = new Email(email.getText(), contactId.getText());
				emails.add(newEmail);
				if(contact!=null) {
					contact.setEmails(emails);
					newEmail.save();
									}
				
			DefaultTableModel emailModel = (DefaultTableModel) emailTable.getModel();
		emailModel.addRow(new Object[]{newEmail.getId(), email.getText()});
			}
		});
		addPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Phone newPhone = new Phone(phone.getText(), TYPE.valueOf(typePhone.getSelectedItem().toString()), contactId.getText());
				phones.add(newPhone);
				if(contact!=null){
					contact.setPhones(phones);
					newPhone.save();
				} 

				DefaultTableModel phoneModel = (DefaultTableModel) phoneTable.getModel();
				phoneModel.addRow(new Object[]{newPhone.getId(), phone.getText(), typePhone.getSelectedItem().toString()});
				
			}
		});
		addressPane.add(typeAddress);

		if(contact!=null){
			setTitle("Update Contact");
			contactId.setText(contact.getId());
			name.setText(contact.getName());
			customerCheck.setSelected(contact.getCustomer());
			type.setSelectedItem(contact.getType());
			title.setSelectedItem(contact.getTitle());
		DefaultTableModel model = (DefaultTableModel) addressTable.getModel();
		for(int i = 0; i<contact.getAddresses().size();i++){
			model.addRow(new Object[]{contact.getAddresses().get(i).getId(), contact.getAddresses().get(i).getStreet(), 
				contact.getAddresses().get(i).getCity(), contact.getAddresses().get(i).getState(), contact.getAddresses().get(i).getCountry(),
				contact.getAddresses().get(i).getZip(), contact.getAddresses().get(i).getType()});
		addresses.add(contact.getAddresses().get(i));
			}
		
		model = (DefaultTableModel) phoneTable.getModel();
		for(int i = 0;i<contact.getPhones().size();i++){
			model.addRow(new Object[]{contact.getPhones().get(i).getId(), contact.getPhones().get(i).getPhone(), contact.getPhones().get(i).getType()});
			phones.add(contact.getPhones().get(i));
		}

		model = (DefaultTableModel) emailTable.getModel();
for(int i = 0;i<contact.getEmails().size();i++){
	model.addRow(new Object[]{contact.getEmails().get(i).getId(), contact.getEmails().get(i).getEmail()});
	emails.add(contact.getEmails().get(i));
}
emails = contact.getEmails();
		}
		
	}
}
