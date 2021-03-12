package schema.Contacts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.db;
import interfaces.mySQL;


public class Person extends Contact implements mySQL<Person>{
  public enum TITLE {CEO, director, worker};
   int id; 
   String firstName;
    String lastName;
    Company company;
    TITLE title;
boolean customer;
 public   Person(String firstName, String lastName, TITLE title, boolean customer, Company company){
    super();
    this.id = this.hashCode();
this.firstName = firstName;
this.lastName = lastName;
this.title = title;
this.company = company;
this.customer = customer;
   }
 @Override
 public Person save() {
     for(int i = 0;i<addresses.size();i++){
        addresses.get(i).save();
     }
     for(int i = 0;i<phones.size();i++){
        phones.get(i).save();
     }
     for(int i = 0;i<emails.size();i++){
        emails.get(i).save();
     }
     Connection connection = db.getConnection();
     String query = "insert into person (id, firstName, lastName, title, customer)" + " values(?,?,?,?,?)";
     try{
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, this.getId());
preparedStatement.setString(2, this.getFirstName());
preparedStatement.setString(3, this.getLastName());
preparedStatement.setString(4, this.getTitle().name());
preparedStatement.setBoolean(5, this.getCustomer());
preparedStatement.execute();
     } catch(SQLException e){
        e.printStackTrace();
     }
   return this;
   }

   @Override
   public void update() {
      for(int i = 0;i<addresses.size();i++){
         addresses.get(i).update();
      }
      for(int i = 0;i<phones.size();i++){
         phones.get(i).update();
      }
      for(int i = 0;i<emails.size();i++){
         emails.get(i).update();
      }
      Connection connection = db.getConnection();
      String query = "UPDATE person SET firstName=?, lastName=?, title=?, customer=? WHERE id = ?";
      try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, this.getFirstName());
         preparedStatement.setString(2, this.getLastName());
         preparedStatement.setString(3, this.getTitle().name());
         preparedStatement.setBoolean(4, this.getCustomer());
         preparedStatement.setInt(5, this.getId());
      } catch(SQLException e){
         e.printStackTrace();
      }
   }

   @Override
   public void delete() {
      for(int i = 0;i<addresses.size();i++){
         addresses.get(i).delete();
      }
      for(int i = 0;i<phones.size();i++){
         phones.get(i).delete();
      }
      for(int i = 0;i<emails.size();i++){
         emails.get(i).delete();
      }
      Connection connection = db.getConnection();
      String query = "DELETE FROM person WHERE id = ?";
      try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1, this.getId());
         preparedStatement.execute();
      } catch(SQLException e){
         e.printStackTrace();
      }
   }

public static ArrayList<Person> find(Integer id, String firstName, String lastName, TITLE title, Boolean customer, String mail,
 String number, TYPE numbertype, String street, String city, String state, Integer Zip, String country, TYPE addressType, Integer emailId, Integer phoneId, Integer addressId){
Connection connection = db.getConnection();
   String idString = id==null?"IS NOT NULL":"="+id.toString();
String titleString = title==null?"IS NOT NULL":"="+title.name();
String customerString = customer == null?"IS NOT NULL":"="+customer.toString();
String query = "SELECT * FROM person WHERE(id "+idString+" AND firstName LIKE '%"+firstName+"%' AND lastName LIKE '%"+lastName+"%' AND title "+titleString+" AND customer "+customerString+")";
try{
   ResultSet rs = connection.prepareStatement(query).executeQuery();
   while(rs.next()){
      ArrayList<Email> emails = Email.find(null, "", id);
      ArrayList<Phone> phones = Phone.find(null, "", id, numbertype);
      ArrayList<Address> addresses = Address.find(addressId, street, city, state, Zip, country, addressType, id);
      
   }
}
}

 public int getId(){return this.id;}
 public String getFirstName(){return this.firstName;}
 public String getLastName(){return this.lastName;}
 public TITLE getTitle(){return this.title;}
 public boolean getCustomer(){return this.customer;}


 public void setFirstName(String firstName){this.firstName = firstName;}
 public void setLastName(String lastName){this.lastName = lastName;}
 public void setTitle(TITLE title){this.title = title;}
 public void setCustomer(boolean customer){this.customer = customer;}
 }
