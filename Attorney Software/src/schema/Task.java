package schema;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import config.db;
import interfaces.mySQL;

public class Task implements mySQL<Task>{
    enum PRIORITY{normal, important}
    enum STATUS {pending, finished}
    String id;
    String taskName;
    String description;
    Case case1;
    PRIORITY priority;
    STATUS status;
    Date date;
    @Override
    public String ObjectId() {
        byte[] array = new byte[7];
        new Random().nextBytes(new byte[7]);
        return Integer.toHexString(this.getTaskName().hashCode())+Integer.toHexString(this.getDescription().hashCode())+Integer.toHexString(this.getCase().getId().hashCode())
        + Integer.toHexString(this.getPriority().name().hashCode())+Integer.toHexString(this.getStatus().name().hashCode())+Integer.toHexString(this.getDate().toString().hashCode())
        +Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
    }
    public Task(String id, String taskName, String description, Case case1, PRIORITY priority, STATUS status, Date date){
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.case1 = case1;
        this.priority = priority;
        this.status = status;
        this.date = date;
    }
    public Task(String taskName, String description, Case case1, PRIORITY priority, STATUS status, Date date){
          this.taskName = taskName;
        this.description = description;
        this.case1 = case1;
        this.priority = priority;
        this.status = status;
        this.date = date;
        this.id = this.ObjectId();
    }

    @Override
    public Task save() {
        Connection connection = db.getConnection();
        String query = "insert into tasks (id, taskName, description, caseId, priority, status, date) values(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
        try{
PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1, this.getId());
preparedStatement.setString(2, this.getTaskName());
preparedStatement.setString(3, this.getDescription());
preparedStatement.setString(4, this.getCase().getId());
preparedStatement.setString(5, this.getPriority().name());
preparedStatement.setString(6, this.getStatus().name());
preparedStatement.setDate(7, this.getDate());
preparedStatement.setString(8, this.ObjectId());
preparedStatement.executeUpdate();
ResultSet rs = preparedStatement.getGeneratedKeys();
if(rs.next()){
    this.id = rs.getString(1);
}
rs.close();          } catch(SQLException e){
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void update() {
        Connection connection = db.getConnection();
        String query = "UPDATE tasks SET taskName=?, description=?, caseId = ?, priority=?, status=?, date=? WHERE id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.getTaskName());
            preparedStatement.setString(2, this.getDescription());
            preparedStatement.setString(3, this.getCase().getId());
            preparedStatement.setString(4, this.getPriority().name());
            preparedStatement.setString(5, this.getStatus().name());
            preparedStatement.setDate(6, this.getDate());
            preparedStatement.setString(7, this.getId());
            preparedStatement.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        Connection connection = db.getConnection();
        String query = "DELETE tasks WHERE id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.getId());
            preparedStatement.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

public static ArrayList<Task> findByCaseId(String id){
    Connection connection = db.getConnection();
    String query = "SELECT id, taskName, description, priority, status, date, caseId from tasks where caseId = " +id;
ArrayList<Task> result = new ArrayList<Task>();
    try{
    ResultSet rs = connection.prepareStatement(query).executeQuery();
 while(rs.next()){
     result.add(new Task(rs.getString("id"), rs.getString("taskName"), rs.getString("description"), null, PRIORITY.valueOf(rs.getString("priority")), STATUS.valueOf(rs.getString("status")), rs.getDate("date")));
      } 
    }catch(SQLException e){
        e.printStackTrace();
          } 
        return result;
}

    public static ArrayList<Task> find(){
Connection connection = db.getConnection();
String query = "SELECT "
    }

    public String getId(){return this.id;}
    public String getTaskName(){return this.taskName;}
    public String getDescription(){return this.description;}
    public Case getCase(){return this.case1;}
    public PRIORITY getPriority(){return this.priority;}
    public STATUS getStatus(){return this.status;}
    public Date getDate(){return this.date;}

    public void setTaskName(String taskName){this.taskName = taskName;}
    public void setDescription(String description){this.description = description;}
    public void setCase(Case case1){this.case1 = case1;}
    public void setPriority(PRIORITY priority){this.priority = priority;}
    public void setStatus(STATUS status){this.status = status;}
    public void setDate(Date date){this.date = date;}
}
