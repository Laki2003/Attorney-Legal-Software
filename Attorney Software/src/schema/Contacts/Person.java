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
       TITLE title;
boolean customer;
 public   Person(String firstName, String lastName, TITLE title, boolean customer){
    super();
    this.id = this.hashCode();
this.firstName = firstName;
this.lastName = lastName;
this.title = title;
this.customer = customer;
   }
   public   Person(Integer id, String firstName, String lastName, TITLE title, boolean customer,ArrayList<Address> addresses, ArrayList<Phone> phones, ArrayList<Email> emails){
      super(addresses, phones, emails);
      this.id = id;
  this.firstName = firstName;
  this.lastName = lastName;
  this.title = title;
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
public static Person findById(int id){
Connection connection = db.getConnection();
String query = "SELECT person.id, person.firstName, person.lastName, person.title, person.customer,GROUP_CONCAT(DISTINCT CONCAT_WS(',', address.id, address.street, address.city, address.state, address.Zip, address.country, address.type) SEPARATOR ';') AS addresses,"+
" GROUP_CONCAT(DISTINCT CONCAT_WS(',', phone.id, phone.number, phone.type) SEPARATOR ';') AS phones, GROUP_CONCAT(DISTINCT CONCAT_WS(',', email.id, email.mail) SEPARATOR ';') AS emails FROM person LEFT JOIN address ON person.id = address.idcontact"+
" LEFT JOIN phone ON person.id = phone.idcontact LEFT JOIN email ON person.id = email.idcontact WHERE(person.id ="
+id+")";
Person person = null;

   try{
      ResultSet rs = connection.prepareStatement(query).executeQuery();
      while(rs.next()){
      ArrayList<Email> emails = new ArrayList<Email>();
      ArrayList<Phone> phones = new ArrayList<Phone>();
      ArrayList<Address> addresses = new ArrayList<Address>();
      String[] objects = rs.getString("emails").split(";");
    
      for(int i = 0;i<objects.length;i++){
         String[] fields = objects[i].split(",");
         if(fields.length > 1)
         emails.add(new Email(Integer.parseInt(fields[0]), fields[1], rs.getInt("person.id")));
      }
      objects = rs.getString("phones").split(";");
     
      for(int i = 0;i<objects.length;i++){
         String[] fields = objects[i].split(",");
         if(fields.length> 1)
         phones.add(new Phone(Integer.parseInt(fields[0]), fields[1], TYPE.valueOf(fields[2]), rs.getInt("person.id")));
      }
      objects = rs.getString("addresses").split(";");
       for(int i = 0;i<objects.length;i++){
         String[] fields = objects[i].split(",");
         if(fields.length>1)
         addresses.add(new Address(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), fields[5], TYPE.valueOf(fields[6]), rs.getInt("person.id")));
      }
      person = new Person(rs.getInt("person.id"),rs.getString("person.firstName"), rs.getString("person.lastName"), TITLE.valueOf(rs.getString("person.title")), rs.getBoolean("person.customer"), addresses, phones, emails);
      }
   } catch(SQLException e){
      e.printStackTrace();
   }

return person;
}
public static ArrayList<Person> find(Integer id, String firstName, String lastName, TITLE title, Boolean customer,String addressString, String phoneString, String emailString){
Connection connection = db.getConnection();
   String idString = id==null?"IS NOT NULL":"="+id.toString();
   String titleString = title==null?"IS NOT NULL":"="+title.name();
String customerString = customer == null?"IS NOT NULL":"="+customer.toString();
String query = "SELECT person.id, person.firstName, person.lastName, person.title, person.customer,GROUP_CONCAT(DISTINCT CONCAT_WS(',', address.id, address.street, address.city, address.state, address.Zip, address.country, address.type) SEPARATOR ';') AS addresses,"+
" GROUP_CONCAT(DISTINCT CONCAT_WS(',', phone.id, phone.number, phone.type) SEPARATOR ';') AS phones, GROUP_CONCAT(DISTINCT CONCAT_WS(',', email.id, email.mail) SEPARATOR ';') AS emails FROM person LEFT JOIN address ON person.id = address.idcontact"+
" LEFT JOIN phone ON person.id = phone.idcontact LEFT JOIN email ON person.id = email.idcontact WHERE(person.id "
+idString+" AND person.firstName LIKE '%"+firstName+"%' AND person.lastName LIKE '%"+lastName+"%' AND person.title "+titleString+" AND person.customer "+customerString+")" + " GROUP BY person.id HAVING addresses LIKE '%"+addressString+"%' AND phones LIKE '%"+phoneString+"%' AND emails LIKE '%"+emailString+"%'";
ArrayList<Person> result = new ArrayList<Person>();
try{
   ResultSet rs = connection.prepareStatement(query).executeQuery();
   while(rs.next()){
   ArrayList<Email> emails = new ArrayList<Email>();
   ArrayList<Phone> phones = new ArrayList<Phone>();
   ArrayList<Address> addresses = new ArrayList<Address>();
   String[] objects = rs.getString("emails").split(";");
 
   for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length > 1)
      emails.add(new Email(Integer.parseInt(fields[0]), fields[1], rs.getInt("person.id")));
   }
   objects = rs.getString("phones").split(";");
  
   for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length> 1)
      phones.add(new Phone(Integer.parseInt(fields[0]), fields[1], TYPE.valueOf(fields[2]), rs.getInt("person.id")));
   }
   objects = rs.getString("addresses").split(";");
    for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length>1)
      addresses.add(new Address(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), fields[5], TYPE.valueOf(fields[6]), rs.getInt("person.id")));
   }
   result.add(new Person(rs.getInt("person.id"),rs.getString("person.firstName"), rs.getString("person.lastName"), TITLE.valueOf(rs.getString("person.title")), rs.getBoolean("person.customer"), addresses, phones, emails));
   }
} catch(SQLException e){
   e.printStackTrace();
}
if(result.size() == 0)
return null;
return result; 
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
