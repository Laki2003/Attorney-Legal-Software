import schema.Contacts.Person;
import schema.Contacts.Company.VRSTAKOMPANIJE;
import schema.Contacts.Contact.TYPE;
import schema.Contacts.Person.TITLE;
import schema.Attorney;
import schema.Contacts.Company;
import schema.Contacts.Contact;
import java.util.ArrayList;

import GUI.gui;
public class App {
  

 

    public static void main(String[] args) throws Exception {
     /*   Company c = new Company("The Half", VRSTAKOMPANIJE.doo, false);
        c.getAddresses();
Person p = new Person("Lazar", "Polovina",TITLE.CEO, true);
Person q = new Person("Milan", "Polovina", TITLE.director, true);
p.getAddresses().add(new Contact.Address("Narodnog fronta 77","Novi Sad", "Novi Sad", 21000, "Serbia", TYPE.home, p.getId()));
q.getAddresses().add(new Contact.Address("Narodnog fronta 77","Novi Sad", "Novi Sad", 21000, "Serbia", TYPE.home, q.getId()));
p.getEmails().add(new Contact.Email("lazarpolovina03@gmail.com", p.getId()));
q.getEmails().add(new Contact.Email("milanpolovina03@gmail.com", q.getId()));
p.getPhones().add(new Contact.Phone("+381644033298", TYPE.home, p.getId()));
p.save();
q.save();
ArrayList<Person> pList= Person.find(null, "", "", null, null, "Narodnog fronta", "", "");
if(pList!=null)
System.out.println(pList.get(0).getEmails().get(0).getEmail());
System.out.println(pList.get(1).getEmails().get(0).getEmail());

p.delete();
q.delete();
Attorney a = new Attorney();
a.save();
a.setLastName("Half");
a.update();
Attorney.find(null, "L", "H",null, "", "", "");
a.delete();*/
gui g = new gui();

    }
}