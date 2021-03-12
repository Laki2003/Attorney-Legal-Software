package schema.Contacts;

import interfaces.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public VRSTAKOMPANIJE getVrstaKompanije(){return this.vrstakompanije;}
    public boolean getCustomer(){return this.customer;}

    public void setName(String name){this.name = name;}
    public void setVrstaKompanije(VRSTAKOMPANIJE vrstakompanije){this.vrstakompanije = vrstakompanije;}
    public void setCustomer(boolean customer){this.customer = customer;}
}
