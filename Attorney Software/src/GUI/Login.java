package GUI;


import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.ImageIcon;
import java.awt.Component;

import schema.Account;
 


public  class Login{
	private final Panel panel = new Panel();
    public  Login(){
JFrame f = new JFrame("Login");
f.setResizable(false);
f.setForeground(Color.WHITE);
final JLabel label = new JLabel();
label.setBounds(20,150,200,50);
final JPasswordField passwordTxt = new JPasswordField();
passwordTxt.setBounds(282,261,201,30);
JLabel l1 = new JLabel("Username:");
l1.setBounds(281,164,80,30);
JLabel l2 = new JLabel("Password:");
l2.setBounds(281,236,80,30);
JButton b = new JButton("Login");
b.setFont(new Font("Arial Black", Font.BOLD, 16));
b.setBorder(null);
b.setForeground(Color.WHITE);
b.setBackground(Color.RED);
b.setBounds(282,320,201,30);
final JTextField usernameTxt = new JTextField();
usernameTxt.setBounds(282,195,201,30);
f.getContentPane().add(passwordTxt);
f.getContentPane().add(l1);
f.getContentPane().add(l2);
f.getContentPane().add(b);
f.getContentPane().add(usernameTxt);
f.setSize(592,501);
f.setLocationRelativeTo(null);
f.getContentPane().setLayout(null);
panel.setBounds(0, 0, 265, 462);
f.getContentPane().add(panel);
panel.setLayout(null);
JLabel lblNewLabel = new JLabel("New label");
lblNewLabel.setBounds(0, 0, 265, 462);
lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/GUI/Pictures/Attorney.jpg")));
panel.add(lblNewLabel);
JLabel lblNewLabel_1 = new JLabel("Attorney Managment Software");
lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
lblNewLabel_1.setBounds(281, 26, 295, 91);
f.getContentPane().add(lblNewLabel_1);
JLabel warning = new JLabel("");
warning.setForeground(new Color(255, 0, 0));
warning.setBounds(282, 115, 294, 37);
f.getContentPane().add(warning);
f.setVisible(true);
b.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        
        Account a = Account.find(usernameTxt.getText());
        System.out.println(String.valueOf(passwordTxt.getPassword()));
        if(a==null || (!a.getPassword().equals(String.valueOf(passwordTxt.getPassword())))){
        warning.setText("Pogresan username ili sifra");
    return;    
    } 
    f.dispose();
    }
});
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}