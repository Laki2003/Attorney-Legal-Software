package schema;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

import config.db;
import interfaces.mySQL;

public class Bill implements mySQL<Bill>{
    enum PAYMENT {cash, card, check}
   String id;
   Case case1;
   String description;
   float toPay;
   PAYMENT payment;
@Override
public String ObjectId() {
    byte[] array = new byte[7];
    new Random().nextBytes(new byte[7]);
    return Integer.toHexString(this.getCase().getId().hashCode())+Integer.toHexString(this.getDescription().hashCode())+
    Integer.toHexString((int) this.getToPay()) + Integer.toHexString(this.getPayment().name().hashCode())+  Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
}
   public Bill(String id, Case case1, String description, float toPay, PAYMENT payment){
       this.id = id;
       this.case1 = case1;
       this.description = description;
       this.toPay = toPay;
       this.payment = payment;
   }
   public Bill( Case case2, String description, float toPay, PAYMENT payment){
       this.case1 = case2;
    this.description = description;
    this.toPay = toPay;
    this.payment = payment;
    this.id = this.ObjectId();
}

@Override
public Bill save() {
Connection connection = db.getConnection();
    String query = "insert into bill (id, caseId, description, toPay, payment)" + " values(?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, this.getId());
        preparedStatement.setString(2, this.getCase().getId());
        preparedStatement.setString(3, this.getDescription());
        preparedStatement.setFloat(4, this.getToPay());
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
    String query = "UPDATE bill SET caseId=?, description=?, toPay=?, payment=? WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getCase().getId());
        preparedStatement.setString(2, this.getDescription());
        preparedStatement.setFloat(3, this.getToPay());
        preparedStatement.setString(4, this.getPayment().name());
        preparedStatement.setString(5, this.getId());
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
    } catch(SQLException e){
        e.printStackTrace();
    }
}

public static ArrayList<Bill> findByCaseId(String id){
    Connection connection = db.getConnection();
    String query = "SELECT id, caseId, description, toPay, payment from bill where caseId = "+id;
    ArrayList<Bill> result = new ArrayList<Bill>();
    try{
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        while(rs.next()){
            result.add(new Bill(null, rs.getString("description"), rs.getFloat("toPay"), PAYMENT.valueOf(rs.getString("payment"))));
        }
    } catch(SQLException e){
        e.printStackTrace();
    }
    return result;
}

public static ArrayList<Bill> find(String bill){
Connection connection = db.getConnection();

}


public String getId(){return this.id;}
public Case getCase(){return this.case1;}
public String getDescription(){return this.description;}
public float getToPay(){return this.toPay;}
public PAYMENT getPayment(){return this.payment;}

public void setCase(Case case1){this.case1 = case1;}
public void setDescription(String description){this.description = description;}
public void setToPay(float toPay){this.toPay = toPay;}
public void setPayment(PAYMENT payment){this.payment = payment;}


}
