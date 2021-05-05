package schema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.db;
import interfaces.mySQL;

public class Account implements mySQL<Account> {
String id;
String username;
String password;

public Account(String id, String username, String password){
    this.id = id;
    this.username = username;
    this.password = password;
}
@Override
public String ObjectId() {
    return id;
}
@Override
public Account save() {
Connection connection = db.getConnection();
    String query = "insert into accounts (id, username, password) values(?,?,?) ";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getId());
        preparedStatement.setString(2, this.getUserName());
        preparedStatement.setString(3, this.getPassword());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
    return this;
}

@Override
public void update() {
    Connection connection = db.getConnection();
    String query = "UPDATE accounts SET username = ?, password = ? WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getUserName());
        preparedStatement.setString(2, this.getPassword());
        preparedStatement.setString(3, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}

@Override
public void delete() {
    Connection connection = db.getConnection();
    String query = "DELETE FROM accounts WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getId());
    } catch(SQLException e){
        e.printStackTrace();
    }
}
public static Account find(String username){
    Connection connection = db.getConnection();
    String query = "SELECT id, username, password from accounts WHERE username = '" + username + "'";
    Account account = null;
    try{
        ResultSet rs = connection.prepareCall(query).executeQuery();
        while(rs.next()){
            account = new Account(rs.getString("id"), rs.getString("username"), rs.getString("password"));
        }
    } catch(SQLException e){
        e.printStackTrace();
        
    }
    return account;
}
public String getId(){return this.id;}
public String getUserName(){return this.username;}
public String getPassword(){return this.password;}

public void setId(String id){this.id = id;}
public void setUserName(String username){this.username = username;}
public void setPassword(String password){this.password = password;}
}
