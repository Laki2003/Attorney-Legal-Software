package schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import config.db;
import interfaces.mySQL;
import schema.Contacts.Contact;

public class Bill<T extends Contact> implements mySQL<Bill<T>>{
    enum PAYMENT {cash, card, check}
   int id;
   Case<T> case1;
   String description;
   float toPay;
   PAYMENT payment;

   public Bill(int id, Case<T> case2, String description, float toPay, PAYMENT payment){
       this.id = id;
       this.case1 = case2;
       this.description = description;
       this.toPay = toPay;
       this.payment = payment;
   }
   public Bill( Case<T> case2, String description, float toPay, PAYMENT payment){
    this.id = this.hashCode();
    this.case1 = case2;
    this.description = description;
    this.toPay = toPay;
    this.payment = payment;
}

@Override
public Bill<T> save() {
Connection connection = db.getConnection();
    String query = "insert into bill (id, caseId, description, toPay, payment)" + " values(?,?,?,?,?)";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.getId());
        preparedStatement.setInt(2, this.getCase().getId());
        preparedStatement.setString(3, this.getDescription());
        preparedStatement.setFloat(4, this.getToPay());
        preparedStatement.setString(5, this.getPayment().name());
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
        preparedStatement.setInt(1, this.getCase().getId());
        preparedStatement.setString(2, this.getDescription());
        preparedStatement.setFloat(3, this.getToPay());
        preparedStatement.setString(4, this.getPayment().name());
        preparedStatement.setInt(5, this.getId());
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
        preparedStatement.setInt(1, this.getId());
    } catch(SQLException e){
        e.printStackTrace();
    }
}

public static <T extends Contact> ArrayList<Bill<T>> find(){

}


public int getId(){return this.id;}
public Case<T> getCase(){return this.case1;}
public String getDescription(){return this.description;}
public float getToPay(){return this.toPay;}
public PAYMENT getPayment(){return this.payment;}

public void setCase(Case<T> case1){this.case1 = case1;}
public void setDescription(String description){this.description = description;}
public void setToPay(float toPay){this.toPay = toPay;}
public void setPayment(PAYMENT payment){this.payment = payment;}


}
