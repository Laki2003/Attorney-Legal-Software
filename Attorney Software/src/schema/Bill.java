package schema;

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
import schema.Contacts.Contacts;
import schema.Contacts.Contacts.TITLE;
import schema.Contacts.Contacts.TYPEC;

public class Bill implements mySQL<Bill>{
  public  enum PAYMENT {cash, card, check}
   String id;
   Contacts customer;
   String description;
   String toPay;
   PAYMENT payment;
@Override
public String ObjectId() {
    byte[] array = new byte[7];
    new Random().nextBytes(new byte[7]);
    return Integer.toHexString(this.getCustomer().getId().hashCode())+Integer.toHexString(this.getDescription().hashCode())+
    Integer.toHexString(this.getToPay().hashCode()) + Integer.toHexString(this.getPayment().name().hashCode())+  Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
}
   public Bill(String id,  Contacts customer, String description, String toPay, PAYMENT payment){
       this.id = id;
           this.customer = customer;
       this.description = description;
       this.toPay = toPay;
       this.payment = payment;
   }
   public Bill(Contacts customer, String description, String toPay, PAYMENT payment){
       this.customer = customer;
    this.description = description;
    this.toPay = toPay;
    this.payment = payment;
    this.id = this.ObjectId();
}

@Override
public Bill save() {
Connection connection = db.getConnection();
    String query = "insert into bill (id,  customerId, description, toPay, payment)" + " values(?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, this.getId());
        preparedStatement.setString(2, this.getCustomer().getId());
        preparedStatement.setString(3, this.getDescription());
        preparedStatement.setString(4, this.getToPay());
        preparedStatement.setString(5, this.getPayment().name());
        preparedStatement.setString(6, this.ObjectId());
        preparedStatement.executeUpdate();
ResultSet rs = preparedStatement.getGeneratedKeys();
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
    String query = "UPDATE bill SET  customerId=?, description=?, toPay=?, payment=? WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getCustomer().getId());
        preparedStatement.setString(2, this.getDescription());
        preparedStatement.setString(3, this.getToPay());
        preparedStatement.setString(4, this.getPayment().name());
        preparedStatement.setString(5, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}

@Override
public void delete() {
    Connection connection = db.getConnection();
    String query = "DELETE FROM bill WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}


public static ArrayList<Bill> find(JTable table, String id, String string, PAYMENT payment){
Connection connection = db.getConnection();
String idString = id == null?"IS NOT NULL":"= '"+id.toString() + "'";
String paymentString = payment == null?"IS NOT NULL":"= '"+payment.toString() + "'";
String query = "SELECT bill.id, description, toPay, payment, contacts.id AS customerId, contacts.name, contacts.title, contacts.customer, contacts.type FROM bill LEFT JOIN contacts ON bill.customerId = contacts.id " +
"WHERE(contacts.customer = true AND bill.id "+idString+" AND payment "+paymentString+ ") GROUP BY bill.id HAVING CONCAT_WS(description, contacts.name, contacts.title) LIKE '%"+string+"%'";
ArrayList<Bill> bills = new ArrayList<Bill>();
try{

    ResultSet rs = connection.prepareStatement(query).executeQuery();
    if(table!=null)
    table.setModel(DbUtils.resultSetToTableModel(rs));
    else{
while(rs.next()){
    bills.add(new Bill(rs.getString("id"),new Contacts(rs.getString("customerId"), rs.getString("name"), TITLE.valueOf(rs.getString("title")), true, TYPEC.valueOf(rs.getString("type")), null, null, null),
    rs.getString("description"), rs.getString("toPay"), PAYMENT.valueOf(rs.getString("payment"))));
}
    }
} catch(SQLException e){
    e.printStackTrace();
}
return bills;
}


public String getId(){return this.id;}

public String getDescription(){return this.description;}
public String getToPay(){return this.toPay;}
public PAYMENT getPayment(){return this.payment;}
public Contacts getCustomer(){return this.customer;}


public void setCustomer(Contacts customer){this.customer = customer;}
public void setDescription(String description){this.description = description;}
public void setToPay(String toPay){this.toPay = toPay;}
public void setPayment(PAYMENT payment){this.payment = payment;}


}
