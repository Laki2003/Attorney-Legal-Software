package GUI;



import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.SwingConstants;

import java.net.MalformedURLException;
import javax.swing.JButton;
public class gui {
    
    public gui() {
        JWindow window = new JWindow();
try{
        URL url = new URL("https://media.tenor.com/images/6247e40eeac15ada772cbfdd2056a882/tenor.gif");
        window.getContentPane().add(
            new JLabel("", new ImageIcon(url), SwingConstants.CENTER));
    window.setSize(300,200);
    window.setLocationRelativeTo(null);
         window.setVisible(true);
         try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         window.setVisible(false);
         JFrame frame = new JFrame("Attorney Legal Software");
         frame.add(new JLabel("Welcome to Attorney Legal Software"));
         JButton b=new JButton("Click");    
           b.setSize(10,10);
frame.add(b);    
         frame.setVisible(true);
         frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
         window.dispose();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    } catch(MalformedURLException m){
    m.printStackTrace();
}
       
    }
}