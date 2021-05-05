package GUI;



import javax.swing.JWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.SwingConstants;

import java.net.MalformedURLException;
import java.awt.Color;

public class gui {
    
    public gui() {
        JWindow window = new JWindow();
try{
        URL url = new URL("https://media.tenor.com/images/6247e40eeac15ada772cbfdd2056a882/tenor.gif");
        JLabel label = new JLabel("", new ImageIcon(url), SwingConstants.CENTER);
        label.setBackground(new Color(199, 21, 133));
        window.getContentPane().add(
            label);
    window.setSize(300,200);
    window.setLocationRelativeTo(null);
         window.setVisible(true);
         try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         window.setVisible(false);
         
         Login l = new Login();  
    } catch(MalformedURLException m){
    m.printStackTrace();
}
       
    }
}