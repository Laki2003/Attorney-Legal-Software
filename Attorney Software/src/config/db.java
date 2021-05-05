package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class db {
    private static final String url = "jdbc:mysql://localhost:3306/attorney";
    private static final  String username = "root";
   private static final String password = "polovina6";

   private static Connection connection = null;
   static{

    try{
                connection = DriverManager.getConnection(url, username, password);
   if(connection !=null){
       System.out.println("Connected to the database");
   }
    } catch (SQLException ex){
        System.out.println("An error occured. Maybe user/password is invalid");
        ex.printStackTrace();
    } 
   }
   public static Connection getConnection(){
       return connection;
   }
}
