package schema;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.db;
import interfaces.mySQL;

public final class Attorney implements mySQL<Attorney> {
    int id;
    String firstName;
    String lastName;
    int yearBirth;
     String education;
    String workExperience;
    String languages;

    public Attorney(int id, String firstName, String lastName, int yearBirth, String education, String workExperience,
    String languages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearBirth = yearBirth;
        this.education = education;
        this.workExperience = workExperience;
        this.languages = languages;
        this.id = id;
       }

       public Attorney(String firstName, String lastName, int yearBirth, String education, String workExperience,
       String languages) {
           this.firstName = firstName;
           this.lastName = lastName;
           this.yearBirth = yearBirth;
           this.education = education;
           this.workExperience = workExperience;
           this.languages = languages;
           this.id = this.hashCode();
          }
   

    public Attorney() {

        
 
    
        this.firstName = "Lazaar";
        this.lastName = "Polovissna";
        this.yearBirth = 1962;
        this.education = "Pravni fakultet u Novom Sadu\n 1962-1965\n Pravni fakultet u Beogradu\n 1961-1965";
        this.workExperience = "Pravni fakultet u Novom Sadu\n 1962-1965\n Pravni fakultet u Beogradu\n 1961-1965";
        this.languages = "engleski, francuski, nemacki";
        this.id = this.hashCode();
    }
 

   
    @Override
    public Attorney save() {

        Connection connection = db.getConnection();
        String query = "insert into attorney (id, firstName, lastName, yearBirth, education, workExperience, languages)"
                + " values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, this.hashCode());
            preparedStmt.setString(2, this.getFirstName());
            preparedStmt.setString(3, this.getLastName());
            preparedStmt.setInt(4, this.getYearBirth());
            preparedStmt.setString(5, this.getEducation());
            preparedStmt.setString(6, this.getWorkExperience());
           preparedStmt.setString(7, this.getLanguages());
                     preparedStmt.execute();
 
        } catch (SQLException e) {
                     e.printStackTrace();
        }

 

    return this;
}
@Override
public void delete() {
Connection connection = db.getConnection();
String query = "DELETE FROM attorney WHERE id = ?";
PreparedStatement preparedStmt;
try {
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setInt(1, this.id);
    preparedStmt.execute();

} catch (SQLException e) {
    e.printStackTrace();
}

}
@Override
public void update() {
Connection connection = db.getConnection();
String query = "UPDATE attorney SET firstName=?, lastName=?, yearBirth=?, education=?, workExperience=?, languages=? WHERE id = ?";
PreparedStatement preparedStatement;
try{
    preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, this.getFirstName());
    preparedStatement.setString(2, this.getLastName());
    preparedStatement.setInt(3, this.getYearBirth());
    preparedStatement.setString(4, this.getEducation());
    preparedStatement.setString(5, this.getWorkExperience());
    preparedStatement.setString(6, this.getLanguages());
    preparedStatement.setInt(7, this.id);
    preparedStatement.execute();
 
} catch(SQLException e){
    e.printStackTrace();
}
}
 public static  ArrayList<Attorney> find(Integer id, String firstName, String lastName, Integer yearBirth, String education, String workExperience, String languages){

Connection connection = db.getConnection();
String idString = id == null?"IS NOT NULL":"="+id.toString();
String yearBirthString = yearBirth == null?"IS NOT NULL":"="+yearBirth.toString();
String query = "SELECT * FROM attorney "+ 
"WHERE (id "+idString+" AND firstName LIKE '%"+firstName+"%' AND lastName LIKE '%"+lastName+"%' AND yearBirth "+yearBirthString+" AND education LIKE '%"+education+"%' AND workExperience LIKE '%"+workExperience+"%' AND languages LIKE '%" + languages + "%')";
ArrayList<Attorney> result = new ArrayList<Attorney>();
try{
ResultSet rs = connection.prepareStatement(query).executeQuery();
while(rs.next()){
result.add(new Attorney(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getInt("yearBirth"),
 rs.getString("education"), rs.getString("workExperience"), rs.getString("languages")));
}
System.out.println(result.size());
} catch(SQLException e){
    e.printStackTrace();
}

return result;
}




public String getFirstName() {return this.firstName;}
public String getLastName() {return this.lastName;}
public int getYearBirth() {return this.yearBirth;}
public String getEducation() {return this.education;}
public String getWorkExperience() {return this.workExperience;}
public String getLanguages() {return this.languages;}

public void setFirstName(String firstName) {this.firstName = firstName;}
public void setLastName(String lastName) {this.lastName = lastName;}
public void setYearBirth(int yearBirth) {this.yearBirth = yearBirth;}
public void setEducation(String education) {this.education = education;}
public void setWorkExperience(String workExperience) {this.workExperience = workExperience;}
public void setLanguages(String languages) {this.languages = languages;}

}
