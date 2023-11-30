package testttt;


import java.util.Date; 
import java.util.Scanner;
public class Event implements Comparable<Event>  {
    public static Scanner input = new Scanner (System.in); 
    String title;
    Date date;
    String time;
    String location;
    boolean appointment;
    //LinkedList<String> contactNames;
    BST<String> contactNames;
    public Event(){ //default constructor
         this.title = " ";
         this.date = null;
         this.time = " ";
         this.location = " ";
         //this.contactNames = new LinkedList<String>();
        this.contactNames = new BST<String>();
        appointment = false;
    }
    public Event(String title, Date date, String time, String location, String contact,boolean appointment) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        //this.contactNames = new LinkedList<String>();
        //this.contactNames.insertSort(contact);
        this.contactNames = new BST<String>();
        this.contactNames.insert( contact,contact);
        this.appointment = appointment;
    }
    public boolean addContact(String contact){
        //return contactNames.insertSort(contact);
        if(!appointment)
            return contactNames.insert(contact, contact);
        return false;

    }
    public boolean removeContact(String contact){
        //String name = contactNames.remove(contact);//name of contact removed is stored in 'name'
        
         boolean contactRemoved= contactNames.removeKey(contact);
        if(contactRemoved)//if name == null there is no contact with that name
            return true;//returns false no name matches
        return false;//contact found and remvoed

    }
    @Override
    public String toString() {
       String str = "\nEvent title: " + title + "\nEvent date and time (MM/DD/YYYY HH:MM) " + date + time + "\nEvent location " + location +"\n"+
        "\nContacts names: ";
       contactNames.findRoot();
        contactNames.printAll(contactNames.current);
        return str;


    }
    public int compareTo(Event obj) {  
        try {  
            return (this.title.compareToIgnoreCase(obj.title));  




        }  
        catch (Exception e)  
        {  
            //To change body of generated methods, choose Tools | Templates.  
            throw new UnsupportedOperationException("Not supported yet.");  
        }  
    }  


    }  



