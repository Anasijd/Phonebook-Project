package csc212;
import java.util.Date; 
import java.util.Scanner;
public class Event implements Comparable<Event>  {
    public static Scanner input = new Scanner (System.in); 
    String title;
    Date date;
    String time;
    String location;
    LinkedList<String> contactNames;
    
    public Event(){ //default constructor
         this.title = " ";
         this.date = null;
         this.time = " ";
         this.location = " ";
         this.contactNames = new LinkedList<String>();
        
    }
    public Event(String title, Date date, String time, String location, String contact) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.contactNames = new LinkedList<String>();
        this.contactNames.insertSort(contact);
        
    }
    public boolean addContact(String contact){
        return contactNames.insertSort(contact);
            
        

    }
    public boolean removeContact(String contact){
        String name = contactNames.remove(contact);//name of contact removed is stored in 'name' 
        if(name == null)//if name == null there is no contact with that name
            return false;//returns false no name matches
        return true;//contact found and remvoed

    }
    @Override
    public String toString() {
       String str = "\nEvent title: " + title + "\nEvent date and time (MM/DD/YYYY HH:MM) " + date + time + "\nEvent location " + location +"\n"+
        "\nContacts names: ";
        contactNames.findFirst();
        for(int i = 0;i<contactNames.size;i++){
            str += contactNames.retrieve() + "    ";

            contactNames.findNext();

                
        }
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



