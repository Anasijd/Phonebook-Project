package project;

import java.util.Date;
public class Contact implements Comparable<Contact> {

	String name;
	String phonenumber;
	String email;
	String address;
	Date birthday;
	String notes;
	BST<Event> events;
	// Default constructor
	public Contact() {
		name = "";
		phonenumber = "";
		email = "";
		address = "";
		birthday = null;
		notes = "";
		events = new BST<Event>();
		
	}

	// Constructor
	public Contact(String name, String phonenumber, String email, String address, Date birthday, String notes){
		this.name = name;
		this.phonenumber = phonenumber;
		this.email = email;
		this.address = address;
		this.birthday = birthday;
		this.notes = notes;
		events = new BST<Event>();
		
	}

	public boolean addEvent(Event event) {
        if(events.findkey(event.title))
			return false;
		events.insert(event.title, event);
		
		return true;
    
	
	}

    public boolean removeEvent(String title) {
        if (events.findkey(title)) {
			
            return events.removeKey(title);
        }
        return false;
    }


	public String toString() {
		return "\nName:" + name + "\nPhone Number:" + phonenumber + "\nEmail Address:" + email + "\nAddress:" + address
				+ "\nBirhtday:" + birthday + "\nNotes:" + notes + "\n";
	}

	public int compareTo(Contact o) {
		try {
			return (this.name.compareTo(o.name));

		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}

}

