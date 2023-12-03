package project;

import java.util.Date; 
import java.util.Scanner;
public class Event implements Comparable<Event>  {
    public static Scanner input = new Scanner (System.in); 
    String title;
    Date date;
    String time;
    String location;
    boolean appointment;
    BST<String> contactNames;
    
    public Event(){ //default constructor
         this.title = " ";
         this.date = null;
         this.time = " ";
         this.location = " ";
        this.contactNames = new BST<String>();
        appointment = false;
    }
    public Event(String title, Date date, String time, String location, String contact,boolean appointment) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.contactNames = new BST<String>();
        this.contactNames.insert( contact,contact);
        this.appointment = appointment;
    }
    public boolean addContact(String contact){
        if(!appointment)
            return contactNames.insert(contact, contact);
        return false;

    }
    public boolean removeContact(String contact){
        
         boolean contactRemoved= contactNames.removeKey(contact);
        if(contactRemoved)//if name == null there is no contact with that name
            return true;//returns false no name matches
        return false;//contact found and remvoed

    }
    @Override
    public String toString() {
       String str = ((appointment)? "\n Appointment" : " \nEvent") + " title: " + title + "\nEvent date and time (MM/DD/YYYY HH:MM) " + date + time + "\nEvent location " + location +"\n"+
        "\nContacts names: " + inOrderTraversalToString(contactNames.root);

        return str;


    }
    public String inOrderTraversalToString(BSTNode node) {
        StringBuilder sb = new StringBuilder();
        tooString(node, sb);
        return sb.toString();
    }
    public void tooString(BSTNode root, StringBuilder sb) {
	    // If node is null, return 
	    if (root == null) 
	        return; 
	   
	    sb.append(root.data + " ");
	    // If node is leaf node, print its data
	    if (root.left == null && 
	        root.right == null) 
	    { 
	        
	        return; 
	    } 

	    // If left child exists, check for leaf 
	    // recursively 
	    if (root.left != null) 
	    	tooString(root.left, sb); 

	    // If right child exists, check for leaf 
	    // recursively 
	    if (root.right != null) 
	    	tooString(root.right, sb); 
      
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
