package schema;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTable;

import config.db;
import interfaces.mySQL;
import net.proteanit.sql.DbUtils;

public class Task implements mySQL<Task>{
   public enum PRIORITY{normal, important}
   public enum STATUST {pending, finished}
    String id;
    String taskName;
    String description;
    Case case1;
    PRIORITY priority;
    STATUST status;
    Date date;
    @Override
    public String ObjectId() {
        byte[] array = new byte[7];
        new Random().nextBytes(new byte[7]);
        return Integer.toHexString(this.getTaskName().hashCode())+Integer.toHexString(this.getDescription().hashCode())+Integer.toHexString(this.getCase().getId().hashCode())
        + Integer.toHexString(this.getPriority().name().hashCode())+Integer.toHexString(this.getStatus().name().hashCode())+Integer.toHexString(this.getDate().toString().hashCode())
        +Integer.toHexString(new String(array, Charset.forName("UTF-8")).hashCode());
    }
    public Task(String id, String taskName, String description, Case case1, PRIORITY priority, STATUST status, Date date){
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.case1 = case1;
        this.priority = priority;
        this.status = status;
        this.date = date;
    }
    public Task(String taskName, String description, Case case1, PRIORITY priority, STATUST status, Date date){
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
        String query = "DELETE FROM tasks WHERE id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.getId());
            preparedStatement.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Task> find(JTable table, String id, String search, PRIORITY priority){
Connection connection = db.getConnection();
String priorityString = priority==null?"IS NOT NULL":"= '"+priority.name() + "'";

String query = "Select tasks.id, taskName, tasks.description, caseId, priority, tasks.status, date FROM tasks LEFT JOIN cases ON tasks.caseId = cases.id "+ 
"where(priority "+priorityString + " AND tasks.id LIKE '%"+id+"%') GROUP BY tasks.id "+
 "HAVING CONCAT_WS(taskName, tasks.description, caseId) LIKE '%" + search +"%'";
ArrayList<Task> tasks = new ArrayList<Task>();
 try{
     ResultSet rs = connection.prepareStatement(query).executeQuery();
     if(table!=null)
     table.setModel(DbUtils.resultSetToTableModel(rs));
     else{
     while(rs.next()){
         tasks.add(new Task(rs.getString("tasks.id"), rs.getString("taskName"), rs.getString("tasks.description"), new Case(rs.getString("caseId"), "", null, null, null, null, null, null, null, null),
          PRIORITY.valueOf(rs.getString("priority")), STATUST.valueOf(rs.getString("tasks.status")), rs.getDate("date")));

     }
    }
 }catch(SQLException e){
     e.printStackTrace();
 }
 return tasks;
    }

    public String getId(){return this.id;}
    public String getTaskName(){return this.taskName;}
    public String getDescription(){return this.description;}
    public Case getCase(){return this.case1;}
    public PRIORITY getPriority(){return this.priority;}
    public STATUST getStatus(){return this.status;}
    public Date getDate(){return this.date;}

    public void setTaskName(String taskName){this.taskName = taskName;}
    public void setDescription(String description){this.description = description;}
    public void setCase(Case case1){this.case1 = case1;}
    public void setPriority(PRIORITY priority){this.priority = priority;}
    public void setStatus(STATUST status){this.status = status;}
    public void setDate(Date date){this.date = date;}
}
