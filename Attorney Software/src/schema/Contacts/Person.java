package schema.Contacts;
import java.util.ArrayList;
import interfaces.mySQL;

enum TITLE {CEO, director, worker}

public class Person extends Contact implements mySQL<Person>{
    String firstName;
    String lastName;
    Company company;
    TITLE title;
   Person(String firstName, String lastName, TITLE title, Company company, ArrayList<Address> addresses, ArrayList<Phone> phones, ArrayList<Email> emails){
    super(emails, phones, addresses);
this.firstName = firstName;
this.lastName = lastName;
this.title = title;
this.company = company;
   }
 @Override
 public Person save() {
     for(int i = 0;i<addresses.size();i++){
        addresses.get(i).save();
     }
     for(int i = 0;i<phones.size();i++){
        phones.get(i).save();
     }

 }
}
