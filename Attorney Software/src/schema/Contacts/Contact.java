package schema.Contacts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import config.db;
import interfaces.mySQL;


public abstract class Contact{
   public enum TYPE {work, home};
   public static class Address implements mySQL<Address> {
     int id;
    String street;
    String city;
    String state;
    int Zip;
    String country;
    TYPE type;
    int contactId;
   public Address(int id, String street, String city, String state, int Zip, String country, TYPE type, int contactId){
    this.id = id;
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
                  } catch(SQLException e){
e.printStackTrace();
                  }
   }

   public static ArrayList<Address> find(Integer id, String street, String city, String state, Integer Zip, String country, TYPE type, Integer idContact){
Connection connection = db.getConnection();
    String idString = id==null?"IS NOT NULL":"="+id.toString();
String ZipString = Zip == null?"IS NOT NULL":"="+Zip.toString(); 
String typeString = type == null?"IS NOT NULL":"="+type.name();
String idContacString = idContact==null?"IS NOT NULL":"="+idContact.toString();
String query = "SELECT * FROM address"+ 
"WHERE(id "+idString+" AND street LIKE '%"+street+"%' AND city LIKE '%"+state+"%' AND state LIKE '%"+state+"%' AND Zip "+ZipString+" AND country LIKE '%"+country+"%' AND type "+typeString+" idcontact "+idContacString+")";
ArrayList<Address> result = new ArrayList<Address>();
try{
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    
    while(rs.next()){
        result.add(new Address(rs.getInt("id"), rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getInt("Zip"), rs.getString("country"), TYPE.valueOf(rs.getString("type")), rs.getInt("idcontact")));
        
    }
    System.out.println(result.size());
} catch(SQLException e){
    e.printStackTrace();
}
return result;
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

public static class Phone implements mySQL<Phone>{
    int id;
    String phone;
    TYPE type;
    int contactId;
    public Phone(int id, String phone, TYPE type, int contactId){
        this.id = id;
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
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

   public static ArrayList<Phone> find(Integer id, String number, Integer idcontact, TYPE type){
Connection connection = db.getConnection();
String idString = id == null?"IS NOT NULL":"="+id.toString();
String idContactString = idcontact == null?"IS NOT NULL":"="+idcontact.toString();
String typeString = type == null ?"IS NOT NULL":"="+type.name();
String query = "SELECT * FROM phone WHERE (id "+idString+" AND number LIKE '%"+number+"%' AND type "+typeString+" AND idcontact "+idContactString+")";
ArrayList<Phone> result = new ArrayList<Phone>();
try{
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    while(rs.next()){
        result.add(new Phone(rs.getInt("id"), rs.getString("number"),TYPE.valueOf(rs.getString("type")), rs.getInt("idcontact")));
    }
    System.out.println(result.size());
} catch(SQLException e){
    e.printStackTrace();
}
return result;
   }

    public int getId(){return this.id;}
    public String getPhone(){return this.phone;}
    public TYPE getType(){return this.type;}
    public int getContactId(){return this.contactId;}

    public void setPhone(String phone){this.phone = phone;}
    public void setType(TYPE type){this.type = type;}
}

public static class Email implements mySQL<Email>{
    int id;
String email;
int contactid;
public Email(int id,String email, int contactid){
    this.id = id;
    this.email = email;
    this.contactid = contactid;

}
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
 } catch(SQLException e){
     e.printStackTrace();
 }
}
public static ArrayList<Email> find(Integer id, String mail, Integer idcontact){
    Connection connection = db.getConnection();
    String idString = id==null?"IS NOT NULL":"="+id.toString();
    String idContactString = idcontact==null?"IS NOT NULL":"="+idcontact.toString();
    String query = "SELECT * FROM WHERE (id "+idString+" AND mail LIKE '%"+mail+"%' AND idcontact "+idContactString+")";
    ArrayList<Email> result = new ArrayList<Email>();
    try{
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        while(rs.next()){
            result.add(new Email(rs.getInt("id"), rs.getString("mail"), rs.getInt("idcontact")));
        }
        System.out.println(result.size());

    } catch(SQLException e){
        e.printStackTrace();
    }
    return result;
}

public int getId(){return this.id;}
public String getEmail(){return this.email;}
public int getContactId(){return this.contactid;}

public void setEmail(String email){this.email = email;}
}

    ArrayList<Email> emails;
    ArrayList<Phone> phones;
   ArrayList<Address> addresses;

   Contact(){
       emails = new ArrayList<Email>();
       phones = new ArrayList<Phone>();
       addresses = new ArrayList<Address>();
   }

   public ArrayList<Address> getAddresses(){return this.addresses;}
public ArrayList<Phone> getPhones(){return this.phones;}
public ArrayList<Email> getEmails(){return this.emails;}
}
