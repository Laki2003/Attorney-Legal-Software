package schema.Contacts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import config.db;
import interfaces.mySQL;

enum TYPE {work, home};
public abstract class Contact{
 static class Address implements mySQL<Address> {
     int id;
    String street;
    String city;
    String state;
    int Zip;
    String country;
    TYPE type;
    int contactId;
   public Address(String street, String city, String state, int Zip, String country, TYPE type, int contactId){
    this.id = this.hashCode();
this.street = street;
this.city = city;
this.state = state;
this.Zip = Zip;
this.country = country;
this.type = type;
this.contactId = contactId;
   }
   @Override
   public Address save() {
       Connection connection = db.getConnection();
       String query = "INSERT INTO address (id, street, city, state, Zip, country, type, idcontact)" + " values(?,?,?,?,?,?,?,?)";
       try{
           PreparedStatement preparedStmt = connection.prepareStatement(query);
           preparedStmt.setInt(1, this.getId());
           preparedStmt.setString(2, this.getStreet());
           preparedStmt.setString(3, this.getCity());
           preparedStmt.setString(4, this.getState());
           preparedStmt.setInt(5, this.getZip());
           preparedStmt.setString(6, this.getCountry());
           preparedStmt.setString(7, this.getType().name());
           preparedStmt.setInt(8, this.getContactId());
           preparedStmt.execute();
           connection.close();                      
        } catch(SQLException e){
            e.printStackTrace();
        }
        return this;
   }
   @Override
   public void update() {
       Connection connection = db.getConnection();
       String query = "UPDATE address SET street=?, city=?, state=?, Zip=?, country=?, type=? WHERE id = ?";
       try{
           PreparedStatement preparedStmt = connection.prepareStatement(query);
           preparedStmt.setString(1, this.getStreet());
           preparedStmt.setString(2, this.getCity());
           preparedStmt.setString(3, this.getState());
           preparedStmt.setInt(4, this.getZip());
           preparedStmt.setString(5, this.getCountry());
           preparedStmt.setString(6, this.getType().name());
           preparedStmt.setInt(7, this.getId());
preparedStmt.execute();
connection.close();
       } catch(SQLException e){
           e.printStackTrace();
       }
   }
   
   @Override
   public void delete() {
       Connection connection = db.getConnection();
       String query = "DELETE FROM address WHERE id = ?";
       try{
           PreparedStatement preparedStmt = connection.prepareStatement(query);
           preparedStmt.setInt(1, this.getId());
           preparedStmt.execute();
           connection.close();
                  } catch(SQLException e){
e.printStackTrace();
                  }
   }
   

   public int getId(){return this.id;}
   public String getStreet(){return this.street;}
   public String getCity(){return this.city;}
   public String getState(){return this.state;}
   public int getZip(){return this.Zip;}
   public String getCountry(){return this.country;}
   public TYPE getType(){return this.type;}
   public int getContactId(){return this.contactId;}

   public void setStreet(String street){this.street = street;}
   public void setCity(String city){this.city = city;}
   public void setState(String state){this.state = state;}
   public void setZip(int Zip){this.Zip = Zip;}
   public void setCountry(String country){this.country = country;}
   public void setType(TYPE type){this.type = type;}

}

static class Phone implements mySQL<Phone>{
    int id;
    String phone;
    TYPE type;
    int contactId;
    public Phone(String phone, TYPE type, int contactId){
        this.id = this.hashCode();
        this.phone = phone;
        this.type = type;
        this.contactId = contactId;
    }
    @Override
    public Phone save() {
        Connection connection = db.getConnection();
        String query = "INSERT INTO phone (id, number, type, idcontact)" + " values(?,?,?,?)";
        try{
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, this.getId());
            preparedStmt.setString(2, this.getPhone());
            preparedStmt.setString(3, this.getType().name());
            preparedStmt.setInt(4, this.getContactId());
            preparedStmt.execute();
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return this;
    }
    @Override
    public void update() {
        Connection connection = db.getConnection();
        String query = "UPDATE phone SET number=?, type=? WHERE id = ?";
        try{
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, this.getPhone());
            preparedStmt.setString(2, this.getType().name());
            preparedStmt.setInt(3, this.getId());
            preparedStmt.execute();
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete() {
        Connection connection = db.getConnection();
        String query = "DELETE FROM phone WHERE id = ?";
        try{
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, this.getId());
            preparedStmt.execute();
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    public int getId(){return this.id;}
    public String getPhone(){return this.phone;}
    public TYPE getType(){return this.type;}
    public int getContactId(){return this.contactId;}

    public void setPhone(String phone){this.phone = phone;}
    public void setType(TYPE type){this.type = type;}
}

static class Email implements mySQL<Email>{
    int id;
String email;
int contactid;
@Override
public Email save() {
    Connection connection = db.getConnection();
    String query = "INSERT INTO email (id, mail, idcontact)" + " values(?,?,?)";
    try{
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1, this.getId());
        preparedStmt.setString(2, this.getEmail());
        preparedStmt.setInt(3, this.getContactId());
        preparedStmt.execute();
        connection.close();
    } catch(SQLException e){
        e.printStackTrace();
    }
    return this;
}
@Override
public void update() {
    Connection connection = db.getConnection();
    String query = "UPDATE email SET mail = ? WHERE id = ?";
    try{
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, this.getEmail());
        preparedStmt.setInt(2, this.getId());
        preparedStmt.execute();
        connection.close();
    } catch(SQLException e){
        e.printStackTrace();
    }
}
@Override
public void delete() {
 Connection connection = db.getConnection();
 String query = "DELETE FROM email WHERE id=?";
 try{
     PreparedStatement preparedStmt = connection.prepareStatement(query);
     preparedStmt.setInt(1, this.getId());
     preparedStmt.execute();
     connection.close();
 } catch(SQLException e){
     e.printStackTrace();
 }
}

public int getId(){return this.id;}
public String getEmail(){return this.email;}
public int getContactId(){return this.contactid;}

public void setEmail(String email){this.email = email;}
}

    ArrayList<Email> emails;
    ArrayList<Phone> phones;
   ArrayList<Address> addresses;
    
    public Contact(ArrayList<Email> emails, ArrayList<Phone> phones, ArrayList<Address> addresses){
this.emails = emails;
this.phones = phones;
this.addresses = addresses;            
    }
}
