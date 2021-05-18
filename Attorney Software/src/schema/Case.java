package schema;

import config.db;
import interfaces.mySQL;
import net.proteanit.sql.DbUtils;
import schema.Contacts.Contacts;
import schema.Contacts.Contacts.TITLE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;




public class Case implements mySQL<Case>{
    public enum STATUS{open, closed, pending};
    public enum PERMISSION{everyone, attorney};
       String id;
    String description;
    Attorney attorney;
    Contacts customer;
   Contacts against;
    ArrayList<Contacts> contacts;
    Date openDate;
    Date closeDate;
STATUS status;
PERMISSION permission;
@Override
public String ObjectId() {
  return "";
}
public Case(String id,  String description, Attorney attorney, Contacts customer, Contacts against, ArrayList<Contacts> contacts, Date openDate, Date closeDate, STATUS status, PERMISSION permission){
this.id = id;
this.description = description;
this.attorney = attorney;
this.customer = customer;
this.against = against;
this.contacts = contacts;
this.openDate = openDate;
this.closeDate = closeDate;
this.status = status;
this.permission = permission;

}

@Override
public Case save() {
    String personsIds = "";
    int size = contacts == null?0:contacts.size();
    for(int i = 0;i<size;i++){
        personsIds += contacts.get(i).getId();
        personsIds +=",";
    }
    Connection connection = db.getConnection();
    String query = "insert into cases (id, description, attorneyId, customerId, againstId, contactsIds, openDate, closeDate, status, permission)" + " values(?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE id=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, this.getId());
        preparedStatement.setString(2, this.getDescription());
        preparedStatement.setString(3, this.getAttorney().getId());
        preparedStatement.setString(4, this.getCustomer().getId());
        preparedStatement.setString(5, this.getAgainst().getId());
        preparedStatement.setString(6, personsIds);
        preparedStatement.setDate(7, this.getOpenDate());
        preparedStatement.setDate(8, this.getCloseDate());
        preparedStatement.setString(9, this.getStatus().name());
        preparedStatement.setString(10, this.getPermission().name());
        preparedStatement.setString(11, this.ObjectId());
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
    String personsIds = "";
    for(int i = 0;i<contacts.size();i++){
        personsIds += contacts.get(i).getId();
        personsIds +=",";
    }
    String query = "UPDATE cases SET description=?, attorneyId=?, customerId=?, againstId=?, personsIds=?, openDate=?, closeDate=?, status=?, permission=? WHERE id=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getDescription());
        preparedStatement.setString(2, this.getAttorney().getId());
        preparedStatement.setString(3, this.getCustomer().getId());
        preparedStatement.setString(4, this.getAgainst().getId());
        preparedStatement.setString(5, personsIds);
        preparedStatement.setDate(6, this.getOpenDate());
        preparedStatement.setDate(7, this.getCloseDate());
        preparedStatement.setString(8, this.getStatus().name());
        preparedStatement.setString(9, this.getPermission().name());
        preparedStatement.setString(10, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}

@Override
public void delete() {
    Connection connection = db.getConnection();
    String query = "DELETE FROM cases WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, this.getId());
        preparedStatement.execute();
    } catch(SQLException e){
        e.printStackTrace();
    }
}


public static ArrayList<Case> find(JTable table, String id, String search){
Connection connection = db.getConnection();
String query = "SELECT cases.id, cases.description, cases.openDate, cases.closeDate, cases.permission, CONCAT_WS(',',attorney.id,attorney.firstName, attorney.lastName) AS attorney, CONCAT_WS(',',customer.id,customer.name) AS customer, CONCAT_WS(',', against.id, against.name) AS against,"+ 
"GROUP_CONCAT(DISTINCT CONCAT_WS(',',contacts.id, contacts.name, contacts.title) SEPARATOR ';') AS contacts  FROM cases "+ 
"left join attorney on cases.attorneyId = attorney.id "+
 "left join contacts customer  on cases.customerId = customer.id left join contacts against on cases.againstId = against.id left join contacts  on cases.contactsIds like concat('%',contacts.id,'%') WHERE(cases.id "+id == null?"IS NOT NULL":"= '"+id+"') GROUP BY cases.id having concat_ws(',', cases.description, cases.id, cases.openDate, cases.closeDate, cases.permission, attorney, customer, against, contacts) LIKE '%"+search+"%'";
ArrayList<Case> cases = new ArrayList<Case>();
try{
 ResultSet rs = connection.prepareStatement(query).executeQuery();
 if(table!=null)
 table.setModel(DbUtils.resultSetToTableModel(rs));
 else{
while(rs.next()){
    ArrayList<Contacts> contacts = new ArrayList<Contacts>();
    String[] objects = rs.getString("contacts").split(";");
    for(int i = 0;i<objects.length;i++){
        String[] fields = objects[i].split(",");
        contacts.add(new Contacts(fields[0], fields[1], TITLE.valueOf(objects[2]), null, null, null, null, null));
            }
    String[] object = rs.getString("attorney").split(",");
    Attorney attorney = new Attorney(object[0], object[1], object[2], 0, "", "", "");
    object = rs.getString("customer").split(",");
    Contacts customer = new Contacts(object[0], object[1], TITLE.valueOf(object[2]), null, null, null, null, null);
    object = rs.getString("against").split(",");
    Contacts against = new Contacts(object[0], object[1], TITLE.valueOf(object[2]), null, null, null, null, null);
    cases.add(new Case(rs.getString("cases.id"), rs.getString("cases.description"), attorney, customer, against, contacts, rs.getDate("contacts.openDate"), rs.getDate("contacts.closeDate"), STATUS.valueOf(rs.getString("cases.status")), PERMISSION.valueOf("cases.permission")));
}
 }
} catch(SQLException e){
    e.printStackTrace();
}

return cases;
}

public String getId(){return this.id;}
public String getDescription(){return this.description;}
public Attorney getAttorney(){return this.attorney;}
public Contacts getCustomer(){return this.customer;}
public Contacts getAgainst(){return this.against;}
public ArrayList<Contacts> getPersons(){return this.contacts;}
public Date getOpenDate(){return this.openDate;}
public Date getCloseDate(){return this.closeDate;}
public STATUS getStatus(){return this.status;}
public PERMISSION getPermission(){return this.permission;}

public void setDescription(String description){this.description = description;}
public void setAttorney(Attorney attorney){this.attorney = attorney;}
public void setCustomer(Contacts customer){this.customer = customer;}
public void setAgainst(Contacts against){this.against = against;}
public void setPersons(ArrayList<Contacts> contacts){this.contacts = contacts;}
public void setOpenDate(Date openDate){this.openDate = openDate;}
public void setCloseDate(Date closeDate){this.closeDate = closeDate;}
public void setStatus(STATUS status){this.status = status;}
public void setPermission(PERMISSION permission){this.permission = permission;}
}
