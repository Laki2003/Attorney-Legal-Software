package schema.Contacts;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTable;

import config.db;
import interfaces.mySQL;
import net.proteanit.sql.DbUtils;


public class Contacts extends Contact implements mySQL<Contacts>{
  public enum TITLE {CEO, director, worker, ortacko_drustvo, komanditno_drustvo, doo, akcionarsko_drustvo};
  public enum TYPEC{fizicko_lice, pravno_lice};
  @Override
  public String ObjectId() {
   byte[] array = new byte[7];
   new Random().nextBytes(new byte[7]);
      return Integer.toHexString(this.getName().hashCode()) + 
      Integer.toHexString(this.getTitle().name().hashCode()) + Integer.toHexString(this.getCustomer() == true ? 1:0) + Integer.toHexString(this.getType().name().hashCode())
      + Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
   }
   String id; 
   String name;
       TITLE title;
Boolean customer;
TYPEC type;
public Contacts(){
   this.id = "";
}
 public   Contacts(String name, TITLE title, boolean customer, TYPEC type){
    super();
    this.name = name;
this.title = title;
this.customer = customer;
this.type = type;
this.id = this.ObjectId();
   }
   public   Contacts(String id, String name, TITLE title, Boolean customer,TYPEC type,ArrayList<Address> addresses, ArrayList<Phone> phones, ArrayList<Email> emails){
      super(addresses, phones, emails);
      this.id = id;
  this.name = name;
  this.title = title;
  this.customer = customer;
  this.type = type;
     }
 @Override
 public Contacts save() {
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
     String query = "insert into contacts (id, name, title, customer, type)" + " values(?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
     try{
PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1, this.getId());
preparedStatement.setString(2, this.getName());
preparedStatement.setString(3, this.getTitle().name());
preparedStatement.setBoolean(4, this.getCustomer());
preparedStatement.setString(5, this.getType().name());
preparedStatement.setString(6, this.ObjectId());
preparedStatement.executeUpdate();
ResultSet rs = preparedStatement.getGeneratedKeys();
if(rs.next()){
    this.id = rs.getString(1);
}
rs.close();     } catch(SQLException e){
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
      String query = "UPDATE contacts SET name=?, title=?, customer=?, type = ? WHERE id = ?";
      try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, this.getName());
         preparedStatement.setString(2, this.getTitle().name());
         preparedStatement.setBoolean(3, this.getCustomer());
         preparedStatement.setString(4, this.getType().name());
         preparedStatement.setString(5, this.getId());
         preparedStatement.execute();
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
      String query = "DELETE FROM contacts WHERE id = ?";
      try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, this.getId());
         preparedStatement.execute();
      } catch(SQLException e){
         e.printStackTrace();
      }
   }
public static Contacts findById(String id){
Connection connection = db.getConnection();
String query = "SELECT contacts.id, contacts.name, contacts.title, contacts.customer, contacts.type, GROUP_CONCAT(DISTINCT CONCAT_WS(',', address.id, address.street, address.city, address.state, address.Zip, address.country, address.type) SEPARATOR ';') AS addresses,"+
" GROUP_CONCAT(DISTINCT CONCAT_WS(',', phone.id, phone.number, phone.type) SEPARATOR ';') AS phones, GROUP_CONCAT(DISTINCT CONCAT_WS(',', email.id, email.mail) SEPARATOR ';') AS emails FROM contacts LEFT JOIN address ON contacts.id = address.idcontact"+
" LEFT JOIN phone ON contacts.id = phone.idcontact LEFT JOIN email ON contacts.id = email.idcontact WHERE(contacts.id ='"
+id+"')";
Contacts person = null;

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
         emails.add(new Email(fields[0], fields[1], rs.getString("contacts.id")));
      }
      objects = rs.getString("phones").split(";");
     
      for(int i = 0;i<objects.length;i++){
         String[] fields = objects[i].split(",");
         if(fields.length> 1)
         phones.add(new Phone(fields[0], fields[1], TYPE.valueOf(fields[2]), rs.getString("contacts.id")));
      }
      objects = rs.getString("addresses").split(";");
       for(int i = 0;i<objects.length;i++){
         String[] fields = objects[i].split(",");
         if(fields.length>1)
         addresses.add(new Address(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), fields[5], TYPE.valueOf(fields[6]), rs.getString("contacts.id")));
      }
      person = new Contacts(rs.getString("contacts.id"),rs.getString("contacts.name"), TITLE.valueOf(rs.getString("contacts.title")), rs.getBoolean("contacts.customer"), TYPEC.valueOf(rs.getString("contacts.type")), addresses, phones, emails);
      }
   } catch(SQLException e){
      e.printStackTrace();
   }

return person;
}
public static ArrayList<Contacts> find(JTable table, String id, String name, TITLE title, Boolean customer, TYPEC type, String addressString, String phoneString, String emailString){
Connection connection = db.getConnection();
   String idString = id==null?"IS NOT NULL":"= '"+id.toString() + "'";
   String titleString = title==null?"IS NOT NULL":"='"+title.name()+ "'";
   String typeString = type==null?"IS NOT NULL":"= '"+type.name()+"'";
String customerString = customer == null?"IS NOT NULL":"="+customer;
String query = "SELECT contacts.id, contacts.name, contacts.title, contacts.customer, contacts.type,GROUP_CONCAT(DISTINCT CONCAT_WS(',', address.id, address.street, address.city, address.state, address.Zip, address.country, address.type) SEPARATOR ';') AS addresses,"+
" GROUP_CONCAT(DISTINCT CONCAT_WS(',', phone.id, phone.number, phone.type) SEPARATOR ';') AS phones, GROUP_CONCAT(DISTINCT CONCAT_WS(',', email.id, email.mail) SEPARATOR ';') AS emails FROM contacts LEFT JOIN address ON contacts.id = address.idcontact"+
" LEFT JOIN phone ON contacts.id = phone.idcontact LEFT JOIN email ON contacts.id = email.idcontact WHERE(contacts.id "
+idString+" AND contacts.name LIKE '%"+name+"%' AND contacts.title "+titleString+" AND contacts.customer "+customerString+" and contacts.type "+typeString+")" + " GROUP BY contacts.id HAVING addresses LIKE '%"+addressString+"%' AND phones LIKE '%"+phoneString+"%' AND emails LIKE '%"+emailString+"%'";
ArrayList<Contacts> result = new ArrayList<Contacts>();
try{
  ResultSet rs = connection.prepareStatement(query).executeQuery();
  if(table!=null)
  table.setModel(DbUtils.resultSetToTableModel(rs));
  else{
   while(rs.next()){
   ArrayList<Email> emails = new ArrayList<Email>();
   ArrayList<Phone> phones = new ArrayList<Phone>();
   ArrayList<Address> addresses = new ArrayList<Address>();
   String[] objects = rs.getString("emails").split(";");
 
   for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length > 1)
      emails.add(new Email(fields[0], fields[1], rs.getString("contacts.id")));
   }
   objects = rs.getString("phones").split(";");
  
   for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length> 1)
      phones.add(new Phone(fields[0], fields[1], TYPE.valueOf(fields[2]), rs.getString("contacts.id")));
   }
   objects = rs.getString("addresses").split(";");
    for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length>1)
      addresses.add(new Address(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), fields[5], TYPE.valueOf(fields[6]), rs.getString("contacts.id")));
   }
   result.add(new Contacts(rs.getString("contacts.id"),rs.getString("contacts.name"), TITLE.valueOf(rs.getString("contacts.title")), rs.getBoolean("contacts.customer"), TYPEC.valueOf(rs.getString("contacts.type")), addresses, phones, emails));
   }
}
} catch(SQLException e){
   e.printStackTrace();
}
if(result.size() == 0)
return null;
return result; 
}

 public String getId(){return this.id;}
 public String getName(){return this.name;}
 public TITLE getTitle(){return this.title;}
 public boolean getCustomer(){return this.customer;}
public TYPEC getType(){return this.type;}

 public void setName(String name){this.name = name;}
 public void setTitle(TITLE title){this.title = title;}
 public void setCustomer(boolean customer){this.customer = customer;}
 public void setType(TYPEC type){this.type = type;}
 }
