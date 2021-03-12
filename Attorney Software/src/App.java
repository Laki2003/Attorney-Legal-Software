import schema.Contacts.Person;
import schema.Contacts.Company.VRSTAKOMPANIJE;
import schema.Contacts.Contact.TYPE;
import schema.Contacts.Person.TITLE;
import schema.Attorney;
import schema.Contacts.Company;
import schema.Contacts.Contact;
public class App {
  

 

    public static void main(String[] args) throws Exception {
        Company c = new Company("The Half", VRSTAKOMPANIJE.doo, false);
Person p = new Person("Lazar", "Polovina",TITLE.CEO, true, c);
p.getAddresses().add(new Contact.Address(,"Narodnog fronta 77","Novi Sad", "Novi Sad", 21000, "Serbia", TYPE.home, p.getId()));
p.getEmails().add(new Contact.Email("lazarpolovina03@gmail.com", p.getId()));
p.getPhones().add(new Contact.Phone("+381644033298", TYPE.home, p.getId()));
p.save();
p.delete();
Attorney a = new Attorney();
a.save();
a.setLastName("Half");
a.update();
Attorney.find(null, "L", "H",null, "", "", "");
a.delete();
    }
}
