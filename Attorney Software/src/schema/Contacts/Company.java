package schema.Contacts;

import interfaces.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.db;


public class Company extends Contact implements mySQL<Company>{
  public enum VRSTAKOMPANIJE {ortacko_drustvo, komanditno_drustvo, doo, akcionarsko_drustvo}
   int id;
    String name;
    VRSTAKOMPANIJE vrstakompanije;
    boolean customer;
   public Company(String name, VRSTAKOMPANIJE vrstakompanije, boolean customer){
this.name = name;
this.vrstakompanije = vrstakompanije;
this.customer = customer;
this.id = this.hashCode();
    }
    public Company(int id,String name, VRSTAKOMPANIJE vrstakompanije, boolean customer, ArrayList<Address> addresses, ArrayList<Phone> phones, ArrayList<Email> emails){
      this.name = name;
      this.vrstakompanije = vrstakompanije;
      this.customer = customer;
      this.id = id;
      this.addresses = addresses;
      this.phones = phones;
      this.emails = emails;
          }
          

    @Override
    public Company save() {
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
         String query = "insert into company (id, name, vrstaKompanije, customer)" + " values(?,?,?,?)";
         try{
             PreparedStatement  preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1, this.getId());
             preparedStatement.setString(2, this.getName());
             preparedStatement.setString(3, this.getVrstaKompanije().name());
             preparedStatement.setBoolean(4, this.getCustomer());
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
      String query = "UPDATE company SET name=?, vrstaKompanije=?, customer=? WHERE id = ?";
      try{
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, this.getName());
         preparedStatement.setString(2, this.getVrstaKompanije().name());
         preparedStatement.setBoolean(3, this.getCustomer());
         preparedStatement.setInt(4, this.getId());
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
         String query = "DELETE FROM company WHERE id=?";
         try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.getId());
            preparedStatement.execute();
         } catch(SQLException e){
             e.printStackTrace();
         }

    }
    public static ArrayList<Company> find(Integer id, String name, VRSTAKOMPANIJE vrstaKompanije, Boolean customer, String addressString, String phoneString, String emailString){
Connection connection = db.getConnection();
String idString = id == null?"IS NOT NULL":"="+id.toString();
String vrstaKompanijeString = vrstaKompanije == null?"IS NOT NULL":"="+vrstaKompanije.name();
String customerString = customer == null?"IS NOT NULL":"="+customer.toString();
String query = "SELECT company.id,company.name, company.vrstaKompanije, company.customer,GROUP_CONCAT(DISTINCT CONCAT_WS(',', address.id, address.street, address.city, address.state, address.Zip, address.country, address.type) SEPARATOR ';') AS addresses,"+
" GROUP_CONCAT(DISTINCT CONCAT_WS(',', phone.id, phone.number, phone.type) SEPARATOR ';') AS phones, GROUP_CONCAT(DISTINCT CONCAT_WS(',', email.id, email.mail) SEPARATOR ';') AS emails FROM person LEFT JOIN address ON company.id = address.idcontact"+
" LEFT JOIN phone ON company.id = phone.idcontact LEFT JOIN email ON company.id = email.idcontact WHERE(company.id "
+idString+" company LIKE '%"+name+"%' AND company.vrstaKompanije "+vrstaKompanijeString+" AND company.customer "+customerString+")" + " GROUP BY company.id HAVING addresses LIKE '%"+addressString+"%' AND phones LIKE '%"+phoneString+"%' AND emails LIKE '%"+emailString+"%'";
ArrayList<Company> result = new ArrayList<Company>();
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
      emails.add(new Email(Integer.parseInt(fields[0]), fields[1], rs.getInt("company.id")));
   }
   objects = rs.getString("phones").split(";");
  
   for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length> 1)
      phones.add(new Phone(Integer.parseInt(fields[0]), fields[1], TYPE.valueOf(fields[2]), rs.getInt("company.id")));
   }
   objects = rs.getString("addresses").split(";");
    for(int i = 0;i<objects.length;i++){
      String[] fields = objects[i].split(",");
      if(fields.length>1)
      addresses.add(new Address(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3], Integer.parseInt(fields[4]), fields[5], TYPE.valueOf(fields[6]), rs.getInt("company.id")));
   }
   result.add(new Company(rs.getInt("company.id"), rs.getString("company.name"), VRSTAKOMPANIJE.valueOf(rs.getString("company.vrstaKompanije")), rs.getBoolean("company.customer"), addresses, phones, emails));
   }
} catch(SQLException e){
   e.printStackTrace();
}
if(result.size() == 0)
return null;
return result; 
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public VRSTAKOMPANIJE getVrstaKompanije(){return this.vrstakompanije;}
    public boolean getCustomer(){return this.customer;}

    public void setName(String name){this.name = name;}
    public void setVrstaKompanije(VRSTAKOMPANIJE vrstakompanije){this.vrstakompanije = vrstakompanije;}
    public void setCustomer(boolean customer){this.customer = customer;}
}
