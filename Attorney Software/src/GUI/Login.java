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
import java.awt.Toolkit;
import schema.Account;
import schema.Attorney;
 


public  class Login extends JFrame{
	private final Panel panel = new Panel();
    public  Login(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/GUI/Pictures/icon.jpg")));
setTitle("Login");
        setResizable(false);
setForeground(Color.WHITE);
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
getContentPane().add(passwordTxt);
getContentPane().add(l1);
getContentPane().add(l2);
getContentPane().add(b);
getContentPane().add(usernameTxt);
setSize(592,501);
setLocationRelativeTo(null);
getContentPane().setLayout(null);
panel.setBounds(0, 0, 265, 462);
getContentPane().add(panel);
panel.setLayout(null);
JLabel lblNewLabel = new JLabel("New label");
lblNewLabel.setBounds(0, 0, 265, 462);
lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/GUI/Pictures/Attorney.jpg")));
panel.add(lblNewLabel);
JLabel lblNewLabel_1 = new JLabel("Attorney Managment Software");
lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
lblNewLabel_1.setBounds(281, 26, 295, 91);
getContentPane().add(lblNewLabel_1);
JLabel warning = new JLabel("");
warning.setForeground(new Color(255, 0, 0));
warning.setBounds(282, 115, 294, 37);
getContentPane().add(warning);
setVisible(true);
b.addActionListener(new ActionListener(){

    public void actionPerformed(ActionEvent e){
        
        Account a = Account.find(usernameTxt.getText());
        if(a==null || (!a.getPassword().equals(String.valueOf(passwordTxt.getPassword())))){
        warning.setText("Pogresan username ili sifra");
    return;    
    } 
    Attorney attorney = Attorney.find(null, a.getId(), "").get(0);
   new Start(attorney, a);
    dispose();

    }
});
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}