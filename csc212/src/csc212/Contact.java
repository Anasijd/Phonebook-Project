package csc212;
import java.util.Date;
public class Contact implements Comparable<Contact> {

	String name;
	String phonenumber;
	String email;
	String address;
	Date birthday;
	String notes;
	//LinkedList<Event> events;
	BST<Event> events;
	

	// Default constructor
	public Contact() {
		name = "";
		phonenumber = "";
		email = "";
		address = "";
		birthday = null;
		notes = "";
		//events = new LinkedList<Event>();
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
		//events = new LinkedList<Event>();
		events = new BST<Event>();
	}

	// method for adding events
	//public boolean addEvent(Event e) {
		// checks if the event linkedList is empty
		//if (BSTevents.empty()) {
			//BSTevents.insert(x.compareTo(e.title), e);
			//return true;

		//} else {
			//events.findFirst();
			// checks if there is an event with the same time or data
			//for (int i = 0; i < events.size; i++) {
				//if ((events.retrieve().time.compareTo(e.time) == 0) && (events.retrieve().date.compareTo(e.date) == 0))
					//return false;
			//}
			// if it's not empty and there are no events with the same time or data it inserts the event
			//events.insertSort(e);
			//return true;
		//}
	//}
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

	// method for removing events from the event linkedList using the event title
	//public boolean removeEvent(String eventTitle) {
		// if the event linkedList is empty it returns false
	// 	//if (events.empty())
	// 		return false;
	// 	// making a temporary object to search for and remove the event
	// 	Event tmp = new Event();
	// 	tmp.title = eventTitle;

	// 	if (events.search(tmp)) {
	// 		events.remove(tmp);
	// 		return true;

	// 	} else
	// 		return false;
	// }

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
