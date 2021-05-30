package GUI;



import javax.swing.JWindow;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import config.db;

import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.awt.Color;

public class gui {
    
    public gui() {
        Connection connection = db.getConnection();
        long millis=System.currentTimeMillis();
        Date now = new Date(millis);
        String query1 = "UPDATE tasks SET status = 'finished' WHERE status = 'pending' AND date < ?";
        String query2 = "UPDATE cases SET status='open' WHERE status = 'pending' AND openDate <= ? AND closeDate >= ?";
        String query3 = "UPDATE cases SET status = 'closed' WHERE status = 'open' AND closeDate < ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
                       preparedStatement.setDate(1, now);
                       preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setDate(1, now);
            preparedStatement.setDate(2, now);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setDate(1, now);
            preparedStatement.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }

        JWindow window = new JWindow();
try{
        URL url = new URL("https://media.tenor.com/images/6247e40eeac15ada772cbfdd2056a882/tenor.gif");
        JLabel label = new JLabel("", new ImageIcon(url), SwingConstants.CENTER);
        label.setBackground(new Color(199, 21, 133));
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(gui.class.getResource("/GUI/Pictures/icon.jpg")));
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
         
         new Login();  
    } catch(MalformedURLException m){
    m.printStackTrace();
}
       
    }
}