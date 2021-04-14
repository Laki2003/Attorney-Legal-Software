package schema;

import config.db;
import interfaces.mySQL;
import schema.Contacts.Contact;
import schema.Contacts.Person;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class Case<T extends Contact> implements mySQL<Case<T>>{
    public enum STATUS{open, closed, pending};
    public enum PERMISSION{everyone, attorney};
    int id;
    Attorney attorney;
    T customer;
    Person[] persons;
    Date openDate;
    Date closeDate;
STATUS status;
PERMISSION permission;

public Case(int id, Attorney attorney, T customer, Person[] persons, Date openDate, Date closeDate, STATUS status, PERMISSION permission){
this.id = id;
this.attorney = attorney;
this.customer = customer;
this.persons = persons;
this.openDate = openDate;
this.closeDate = closeDate;
this.status = status;
this.permission = permission;
}
public Case(Attorney attorney, T customer, Person[] persons, Date openDate, Date closeDate, STATUS status, PERMISSION permission){
    this.id = this.hashCode();
    this.attorney = attorney;
    this.customer = customer;
    this.persons = persons;
    this.openDate = openDate;
    this.closeDate = closeDate;
    this.status = status;
    this.permission = permission;
    }
@Override
public Case<T> save() {
    String personsIds = "";
    for(int i = 0;i<persons.length;i++){
        personsIds += persons[i].getId();
        personsIds +=",";
    }
    Connection connection = db.getConnection();
    String query = "insert into case (id, attorneyId, customerId, personsIds, openDate, closeDate, status, permission)" + " values(?,?,?,?,?,?,?,?)";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.getId());
        preparedStatement.setInt(2, this.getAttorney().getId());
        preparedStatement.setInt(3, this.getCustomer().hashCode());
        preparedStatement.setString(4, personsIds);
        preparedStatement.setDate(5, this.getOpenDate());
        preparedStatement.setDate(6, this.getCloseDate());
        preparedStatement.setString(7, this.getStatus().name());
        preparedStatement.setString(8, this.getPermission().name());
    } catch(SQLException e){
        e.printStackTrace();
    }
    return this;
}

@Override
public void update() {
    Connection connection = db.getConnection();
    String personsIds = "";
    for(int i = 0;i<persons.length;i++){
        personsIds += persons[i].getId();
        personsIds +=",";
    }
    String query = "UPDATE case SET attorneyId=?, customerId=?, personsIds=?, openDate=?, closeDate=?, status=?, permission=? WHERE id=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.getAttorney().getId());
        preparedStatement.setInt(2, this.getCustomer().hashCode());
        preparedStatement.setString(3, personsIds);
        preparedStatement.setDate(4, this.getOpenDate());
        preparedStatement.setDate(5, this.getCloseDate());
        preparedStatement.setString(6, this.getStatus().name());
        preparedStatement.setString(7, this.getPermission().name());
        preparedStatement.setInt(8, this.getId());
    } catch(SQLException e){
        e.printStackTrace();
    }
}

@Override
public void delete() {
    Connection connection = db.getConnection();
    String query = "DELETE FROM case WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}

public static <T extends Contact> ArrayList<Case<T>> find(){

}

public int getId(){return this.id;}
public Attorney getAttorney(){return this.attorney;}
public T getCustomer(){return this.customer;}
public Person[] getPersons(){return this.persons;}
public Date getOpenDate(){return this.openDate;}
public Date getCloseDate(){return this.closeDate;}
public STATUS getStatus(){return this.status;}
public PERMISSION getPermission(){return this.permission;}

public void setAttorney(Attorney attorney){this.attorney = attorney;}
public void setCustomer(T customer){this.customer = customer;}
public void setPersons(Person[] persons){this.persons = persons;}
public void setOpenDate(Date openDate){this.openDate = openDate;}
public void setCloseDate(Date closeDate){this.closeDate = closeDate;}
public void setStatus(STATUS status){this.status = status;}
public void setPermission(PERMISSION permission){this.permission = permission;}
    

}
