package schema.Contacts;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.sql.ResultSet;
import config.db;
import interfaces.mySQL;


public abstract class Contact{
   public enum TYPE {work, home};
  
   public static class Address implements mySQL<Address> {
       @Override
    public String ObjectId(){
        byte[] array = new byte[7];
        new Random().nextBytes(new byte[7]);
       
        return Integer.toHexString(this.getStreet().hashCode())+Integer.toHexString(this.getCity().hashCode())+Integer.toHexString(this.getState().hashCode())+
        Integer.toHexString(this.getZip())+Integer.toHexString(this.getCountry().hashCode())+Integer.toHexString(this.getType().name().hashCode())+Integer.toHexString(this.getContactId().hashCode())
        + Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
    }
    String id;
    String street;
    String city;
    String state;
    int Zip;
    String country;
    TYPE type;
    String contactId;
   public Address(String id, String street, String city, String state, int Zip, String country, TYPE type, String contactId){
    this.id = id;
this.street = street;
this.city = city;
this.state = state;
this.Zip = Zip;
this.country = country;
this.type = type;
this.contactId = contactId;
   }

   public Address(String street, String city, String state, int Zip, String country, TYPE type, String contactId){
   this.street = street;
this.city = city;
this.state = state;
this.Zip = Zip;
this.country = country;
this.type = type;
this.contactId = contactId;
this.id = this.ObjectId();
   }

   @Override
   public Address save() {
       Connection connection = db.getConnection();
       String query = "INSERT INTO address (id, street, city, state, Zip, country, type, idcontact)" + " values(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
       try{
           PreparedStatement preparedStmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStmt.setString(1, this.getId());
           preparedStmt.setString(2, this.getStreet());
           preparedStmt.setString(3, this.getCity());
           preparedStmt.setString(4, this.getState());
           preparedStmt.setInt(5, this.getZip());
           preparedStmt.setString(6, this.getCountry());
           preparedStmt.setString(7, this.getType().name());
           preparedStmt.setString(8, this.getContactId());
           preparedStmt.setString(9, this.ObjectId());
           preparedStmt.executeUpdate();
           ResultSet rs = preparedStmt.getGeneratedKeys();
           if(rs.next()){
               this.id = rs.getString(1);
           }
          rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return this;
   }
   @Override
   public void update() {
       Connection connection = db.getConnection();
       String query = "UPDATE address SET  street=?, city=?, state=?, Zip=?, country=?, type=? WHERE id = ?";
       try{
           PreparedStatement preparedStmt = connection.prepareStatement(query);
          preparedStmt.setString(1, this.getStreet());
           preparedStmt.setString(2, this.getCity());
           preparedStmt.setString(3, this.getState());
           preparedStmt.setInt(4, this.getZip());
           preparedStmt.setString(5, this.getCountry());
           preparedStmt.setString(6, this.getType().name());
           preparedStmt.setString(7, this.getId());
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
           preparedStmt.setString(1, this.getId());
           preparedStmt.execute();
                  } catch(SQLException e){
e.printStackTrace();
                  }
   }

   public static ArrayList<Address> find(String id, String street, String city, String state, Integer Zip, String country, TYPE type, String idContact){
Connection connection = db.getConnection();
    String idString = id==null?"IS NOT NULL":"="+id;
String ZipString = Zip == null?"IS NOT NULL":"="+Zip.toString(); 
String typeString = type == null?"IS NOT NULL":"="+type.name();
String idContacString = idContact==null?"IS NOT NULL":"="+idContact;
String query = "SELECT * FROM address"+ 
"WHERE(id "+idString+" AND street LIKE '%"+street+"%' AND city LIKE '%"+state+"%' AND state LIKE '%"+state+"%' AND Zip "+ZipString+" AND country LIKE '%"+country+"%' AND type "+typeString+" idcontact "+idContacString+")";
ArrayList<Address> result = new ArrayList<Address>();
try{
    ResultSet rs = connection.prepareStatement(query).executeQuery();
    
    while(rs.next()){
        result.add(new Address(rs.getString("id"), rs.getString("street"), rs.getString("city"), rs.getString("state"), rs.getInt("Zip"), rs.getString("country"), TYPE.valueOf(rs.getString("type")), rs.getString("idcontact")));
        
    }
    System.out.println(result.size());
} catch(SQLException e){
    e.printStackTrace();
}
return result;
   }
   

   public String getId(){return this.id;}
   public String getStreet(){return this.street;}
   public String getCity(){return this.city;}
   public String getState(){return this.state;}
   public int getZip(){return this.Zip;}
   public String getCountry(){return this.country;}
   public TYPE getType(){return this.type;}
   public String getContactId(){return this.contactId;}

   public void setStreet(String street){this.street = street;}
   public void setCity(String city){this.city = city;}
   public void setState(String state){this.state = state;}
   public void setZip(int Zip){this.Zip = Zip;}
   public void setCountry(String country){this.country = country;}
   public void setType(TYPE type){this.type = type;}

}

public static class Phone implements mySQL<Phone>{

    String id;
    String phone;
    TYPE type;
    String contactId;
    @Override
    public String ObjectId() {
        byte[] array = new byte[7];
        new Random().nextBytes(new byte[7]);
        return Integer.toHexString(this.getPhone().hashCode())+Integer.toHexString(this.getType().name().hashCode())+Integer.toHexString(this.getContactId().hashCode()) 
         + Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
    }
    public Phone(String id, String phone, TYPE type, String contactId){
        this.id = id;
        this.phone = phone;
        this.type = type;
        this.contactId = contactId;
    }
    public Phone(String phone, TYPE type, String contactId){
              this.phone = phone;
        this.type = type;
        this.contactId = contactId;
        this.id = this.ObjectId();
    }


    @Override
    public Phone save() {
        Connection connection = db.getConnection();
        String query = "INSERT INTO phone (id, number, type, idcontact)" + " values(?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
        try{
            PreparedStatement preparedStmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, this.getId());
            preparedStmt.setString(2, this.getPhone());
            preparedStmt.setString(3, this.getType().name());
            preparedStmt.setString(4, this.getContactId());
            preparedStmt.setString(5, this.ObjectId());
            preparedStmt.executeUpdate();
            ResultSet rs = preparedStmt.getGeneratedKeys();
            if(rs.next()){
                this.id = rs.getString(1);
            }
           rs.close();        } catch(SQLException e){
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
            preparedStmt.setString(3, this.getId());
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
            preparedStmt.setString(1, this.getId());
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
        result.add(new Phone(rs.getString("id"), rs.getString("number"),TYPE.valueOf(rs.getString("type")), rs.getString("idcontact")));
    }
    System.out.println(result.size());
} catch(SQLException e){
    e.printStackTrace();
}
return result;
   }

    public String getId(){return this.id;}
    public String getPhone(){return this.phone;}
    public TYPE getType(){return this.type;}
    public String getContactId(){return this.contactId;}

    public void setPhone(String phone){this.phone = phone;}
    public void setType(TYPE type){this.type = type;}
}

public static class Email implements mySQL<Email>{
    String id;
String email;
String contactid;
@Override
public String ObjectId() {
    byte[] array = new byte[7];
    new Random().nextBytes(new byte[7]);
    return Integer.toHexString(this.getEmail().hashCode())+Integer.toHexString(this.getContactId().hashCode())+ Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
}
public Email(String id,String email, String contactid){
    this.id = id;
    this.email = email;
    this.contactid = contactid;

}

public Email(String email, String contactid){
      this.email = email;
    this.contactid = contactid;
    this.id = this.ObjectId();
}

@Override
public Email save() {
    Connection connection = db.getConnection();
    String query = "INSERT INTO email (id, mail, idcontact)" + " values(?,?,?) ON DUPLICATE KEY UPDATE id=?";
    try{
        PreparedStatement preparedStmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, this.getId());
        preparedStmt.setString(2, this.getEmail());
        preparedStmt.setString(3, this.getContactId());
        preparedStmt.setString(4, this.ObjectId());
        preparedStmt.executeUpdate();
        ResultSet rs = preparedStmt.getGeneratedKeys();
        if(rs.next()){
            this.id = rs.getString(1);
        }
       rs.close();    } 
       catch(SQLException e){
        e.printStackTrace();
    }
    return this;
}
@Override
public void update() {
    Connection connection = db.getConnection();
    String query = "UPDATE email SET  mail = ? WHERE id = ?";
    try{
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, this.getEmail());
        preparedStmt.setString(2, this.getId());
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
     preparedStmt.setString(1, this.getId());
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
            result.add(new Email(rs.getString("id"), rs.getString("mail"), rs.getString("idcontact")));
        }
        System.out.println(result.size());

    } catch(SQLException e){
        e.printStackTrace();
    }
    return result;
}

public String getId(){return this.id;}
public String getEmail(){return this.email;}
public String getContactId(){return this.contactid;}

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
   Contact(ArrayList<Address> addresses, ArrayList<Phone> phones, ArrayList<Email> emails){
       this.addresses = addresses;
       this.phones = phones;
       this.emails = emails;
   }
   public ArrayList<Address> getAddresses(){return this.addresses;}
public ArrayList<Phone> getPhones(){return this.phones;}
public ArrayList<Email> getEmails(){return this.emails;}
}
