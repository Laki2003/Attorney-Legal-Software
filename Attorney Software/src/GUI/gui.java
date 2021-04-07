package GUI;



import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.SwingConstants;
import java.net.MalformedURLException;
public class gui {
    public gui() {
        JWindow window = new JWindow();
try{
        URL url = new URL("https://media.tenor.com/images/6247e40eeac15ada772cbfdd2056a882/tenor.gif");
        window.getContentPane().add(
            new JLabel("", new ImageIcon(url), SwingConstants.CENTER));
    window.setBounds(500, 150, 300, 200);
         window.setVisible(true);
         try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         window.setVisible(false);
         JFrame frame = new JFrame();
         frame.add(new JLabel("Welcome to Attorney Legal Software"));
         frame.setVisible(true);
         frame.setSize(300,100);
         window.dispose();
    } catch(MalformedURLException m){
    m.printStackTrace();
}
       
    }
}