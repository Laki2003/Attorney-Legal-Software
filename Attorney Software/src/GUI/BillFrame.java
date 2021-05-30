package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import schema.Bill;
import schema.Bill.PAYMENT;
import schema.Contacts.Contacts;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class BillFrame extends JFrame {

	private JPanel contentPane;
	private JTextField idBill;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	



	public BillFrame(Bill bill) {
		setVisible(true);
		setResizable(false);
		setTitle("Add Bill");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BillFrame.class.getResource("/GUI/Pictures/icon.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bill ID:");
		lblNewLabel.setBounds(10, 11, 68, 14);
		contentPane.add(lblNewLabel);
		
		idBill = new JTextField();
		idBill.setEditable(false);
		idBill.setBounds(75, 8, 158, 20);
		contentPane.add(idBill);
		idBill.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Description:");
		lblNewLabel_1.setBounds(10, 55, 68, 14);
		contentPane.add(lblNewLabel_1);
		JTextArea description = new JTextArea();
		description.setBounds(75,55,152,83);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		getContentPane().add(description);
		
		lblNewLabel_2 = new JLabel("To pay:");
		lblNewLabel_2.setBounds(10, 171, 46, 14);
		contentPane.add(lblNewLabel_2);
	
		NumberFormat ammountFormat = NumberFormat.getCurrencyInstance();
		JFormattedTextField toPay = new JFormattedTextField(ammountFormat);
		toPay.setBounds(75, 168, 152, 20);
		contentPane.add(toPay);
		

		JLabel lblNewLabel_3 = new JLabel("Payment:");
		lblNewLabel_3.setBounds(10, 239, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JComboBox<PAYMENT> payment = new JComboBox<>();
		payment.setBounds(75, 235, 152, 22);
		payment.setModel(new DefaultComboBoxModel<>(PAYMENT.values()));
		contentPane.add(payment);
		
		JLabel lblNewLabel_4 = new JLabel("Customer:");
		lblNewLabel_4.setBounds(10, 317, 68, 14);
		contentPane.add(lblNewLabel_4);
		Vector<String> customerVector = new Vector<String>();
		customerVector.add("");
		ArrayList<Contacts> customerList = Contacts.find(null, null, "", null, true, null, "", "", "");
		if(customerList!=null)
		for(int i = 0;i<customerList.size();i++){
			customerVector.add(new String(customerList.get(i).getName() + " " + customerList.get(i).getType().toString()));
		}
		

		JComboBox<String> customer = new JComboBox<>(customerVector);
		customer.setBounds(75, 313, 152, 22);
		contentPane.add(customer);
		
		if(bill!=null){
			setTitle("Update Bill");
			if(bill.getCustomer()==null)
			customer.setSelectedIndex(0);
			else{
			for(int i = 0; i<customerList.size();i++){
				if(customerList.get(i).getId().equals(bill.getCustomer().getId())){
					customer.setSelectedIndex(i+1);
				}
			}
		}
			idBill.setText(bill.getId());
			description.setText(bill.getDescription());
		    payment.setSelectedItem(bill.getPayment());
			toPay.setText(bill.getToPay());

		}


		JButton cancel = new JButton("CANCEL");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(266, 386, 146, 60);
		contentPane.add(cancel);
		
		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
               
				if(bill == null){
					if(customer.getSelectedIndex() == 0){
						JOptionPane.showMessageDialog(contentPane, "You didn't fill all fields!");
						return;
					}
					Bill newBill = new Bill(customerList.get(customer.getSelectedIndex()-1), description.getText(), toPay.getText(),PAYMENT.valueOf(payment.getSelectedItem().toString()));
					newBill.save();
				} else{
					bill.setCustomer(customerList.get(customer.getSelectedIndex()-1));
					bill.setDescription(description.getText());
					bill.setToPay(toPay.getText());
					bill.setPayment(PAYMENT.valueOf(payment.getSelectedItem().toString()));
					bill.update();
				}
				dispose();
			}
		});
		save.setBounds(420, 386, 137, 60);
		contentPane.add(save);
		
	}
}
