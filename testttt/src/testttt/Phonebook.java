package testttt;



import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;

public class Phonebook {
	public static Scanner input = new Scanner(System.in);
	//public static LinkedList<Contact> contacts = new LinkedList<Contact>();
	//public static LinkedList<Event> events = new LinkedList<Event>();
	public static BST<Contact> contacts = new BST<Contact>();
	public static BST<Event> events = new BST<Event>();
	static Integer choice;
	static String enteredName;
	static String entetedPhoneNumber;
	static String enteredEmailAddress;
	static String enteredAddress;
	static Date enteredBirthday;
	static boolean contactNotFound;
	static boolean contactFound;
	
	public static void addContact() {
		System.out.print("Enter contact\'s name: ");
		Contact newContact = new Contact();
		input.nextLine();
		newContact.name = input.nextLine();
		if (contacts.findkey(newContact.name)) {
			System.out.println("Contact found!");// contact already exists
			System.out.println(contacts.retrieve().toString());
			return;
		}
		while (true) {
			System.out.print("Enter the contact's phone number: ");
			try {
				Integer x = Integer.parseInt(input.next());
				newContact.phonenumber = x.toString();
				break;
			} catch (NumberFormatException e) {
				System.out.println("please enter a valid number");
			}
		}
		if (!contacts.empty()) {
			if(contacts.findNumber(newContact.phonenumber)){
				System.out.println("Contact found!");
				System.out.println(contacts.retrieve().toString());
				return;
			}
				
			// for (int i = 0; i < contacts.size; i++) {// n
			// 	if (contacts.retrieve().phonenumber.compareTo(newContact.phonenumber) == 0) {// n-1
			// 		System.out.println("Contact found!");
			// 		System.out.println(contacts.retrieve().toString());
			// 		return;
			// 	}
			// 	contacts.findNext();
			// }


		}
		System.out.print("Enter the contact's email address: ");
		input.nextLine();
		newContact.email = input.nextLine();
		System.out.print("Enter the contact's address: ");
		newContact.address = input.nextLine();
		while (true) {
			try {
				System.out.print("Enter the contact's birthday: ");
				newContact.birthday = new Date(input.nextLine());
				break;
			} catch (Exception e) {
				System.out.println("try again");
			}
		}
		System.out.print("Enter any notes for the contact: ");
		newContact.notes = input.nextLine();
		//contacts.insertSort(newContact);
		contacts.insert(newContact.name, newContact);
		System.out.println();
		System.out.println("Contact added successfully!");
	}

	public static void ScheduleEvent(boolean appointment) {
		Contact desiredContact = new Contact();
		Event newEvent = new Event();
		newEvent.appointment = appointment;
		boolean oldEvent = false;
		System.out.print("Enter event title: ");
		input.nextLine();
		newEvent.title = input.nextLine();
		System.out.print("Enter contact name: ");
		desiredContact.name = input.nextLine();
		if (contacts.findkey(desiredContact.name)) {
			desiredContact = contacts.retrieve();
			while (true) {
				System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
				try {
					newEvent.date = new Date(input.nextLine());
					break;
				} catch (Exception e) {
					System.out.println("try again");
				}
			}
			System.out.print("Enter event location: ");
			newEvent.location = input.nextLine();
			if (desiredContact.addEvent(newEvent)) {
				// adds the new event to the contact
				contacts.update(desiredContact.name,desiredContact);
				if (events.findkey(newEvent.title)) {
					Event tmpEvent = events.retrieve();
					if ((tmpEvent.date.compareTo(newEvent.date) == 0)
							&& (tmpEvent.location.compareTo(newEvent.location) == 0)) {
								if(tmpEvent.appointment||newEvent.appointment){
									System.out.println("conflict");
									return;
								}		
								
								
						tmpEvent.contactNames.insert(desiredContact.name,desiredContact.name);
						desiredContact.events.insert(tmpEvent.title, tmpEvent);

						events.update(tmpEvent.title,tmpEvent);
						contacts.update(desiredContact.name,desiredContact);
						
						oldEvent = true;
					}
				}
				if (!oldEvent) {
					newEvent.contactNames.insert(desiredContact.name,desiredContact.name);
					events.insert(newEvent.title,newEvent);
				}
				System.out.println("Event scheduled successfully!   ");
			} else
				System.out.println("Contact has conflict Event !  ");
		} else
			System.out.println("Contact not found !");
	}
	public static void searchFirstName(BSTNode current,String name) 
	{ 
	
		// If node is null, return 
		if (contacts.current == null) 
			return; 
	
		// If node is leaf node, print its data
		if (((Contact)current.data).name.split(" ")[0].equalsIgnoreCase(name))  
		{ 
			System.out.println(((Contact)current.data).toString());
		} 
		
		if(current.left == null && current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (current.left != null) 
			searchFirstName(current.left,name); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (current.right != null) 
			searchFirstName(current.right,name); 
	} 

	public static void PrintFirstName() {
		boolean nameNotFound = true;
		// if the linkedList is empty
		if (contacts.empty()) {
			System.out.println("there are no contacts");
			return;
		}
		System.out.print("Enter the first name:");
		input.nextLine();
		String firstName = input.nextLine();
		searchFirstName(contacts.root, firstName);
	}
	// 	contacts.findFirst();
	// 	for (int i = 0; i < contacts.size; i++) {
	// 		String name = contacts.retrieve().name;
	// 		// method split divides the string into an array by spaces
	// 		String fullName[] = name.split(" ");
	// 		// checks if the entered first name matches the first contact name
	// 		if (fullName[0].compareToIgnoreCase(firstName) == 0) {
	// 			System.out.println(contacts.retrieve() + "\n");
	// 			nameNotFound = false;
	// 		}
	// 		contacts.findNext();
	// 	}
	// 	if (nameNotFound)
	// 		System.out.println("First name not found");
	


	

 
	public static void DeleteContact() {
		if (!contacts.empty()) {
			Contact enteredContact = new Contact();
			System.out.print("Enter the contact's name: ");
			input.nextLine();
			String name = input.nextLine();
			//enteredContact = contacts.remove(enteredContact);
			contacts.findkey(name);
			enteredContact = contacts.retrieve();
			contacts.removeKey(name);
			//
			// method remove returns null if it doesn't find the node
			if (enteredContact == null) {
				System.out.println("Contact not found!");
				return;
			}
			// if the deleted contact has events
			if (!enteredContact.events.empty()) {
				enteredContact.events.findRoot();
				// for (int i = 0; i < enteredContact.events.size; i++) {
					Event contactEvents = enteredContact.events.retrieve();
					if (events.findkey(contactEvents.title)) {
						Event deleted_Contact_Event = events.retrieve();
						deleted_Contact_Event.removeContact(enteredContact.name);
						// if the event has no contact remove it
						if (deleted_Contact_Event.contactNames.empty()) {
							events.removeKey(contactEvents.title);
							System.out.println("Delete event, No cantact particapate");
						} else // if the event still has contacts update it
							events.update(deleted_Contact_Event.title,deleted_Contact_Event);
					}
					// enteredContact.events.findNext();
				// }
			}
			System.out.println("Contact Deleted Successfully !");
			System.out.println(enteredContact.toString());
		} else
			System.out.println("there is no contacts");

	}

	public static void printEventByContactName() {
		 contactNotFound = true;
		System.out.print("enter contact's name: ");
		input.nextLine();
		String contactName = input.nextLine();

		if (!contacts.empty()) {
			contacts.findRoot();
			//for (int i = 0; i < contacts.size; i++) {
				findEventBycontact(contacts.current,contactName);
			// 	if (contacts.retrieve().name.compareTo(contactName) == 0) {// checks for contact
			// 		Event event = contacts.retrieve().events.retrieve();// stores in Event obj
			// 		if (!contacts.retrieve().events.empty()) {/* if contact has event it prints */
			// 			System.out.println(event.toString());/* the event with all contacts */
			// 			contactNotFound = false;
			// 		} else {
			// 			System.out.println("No events found for this contact!");
			// 		}
			// 	}
			// 	contacts.findNext();
			// }
			if (contactNotFound)
				System.out.println("Contact not found!");
		}

	}

	public static void printEventByEventTitle() {
		if (events.empty()) {
			System.out.println("Events empty");// empty list
		} else {

			System.out.print("Enter the event title: ");
			input.nextLine();
			Event enteredEvent = new Event();
			enteredEvent.title = input.nextLine();// store the title with event object
			events.findRoot();
			
			boolean eventFound = events.findkey(enteredEvent.title);
			if (eventFound) {
				System.out.println(events.retrieve().toString());
				//current.data.events.retrieve().contactNames.printAll(current.data.events.retrieve().contactNames.root);
			}
			if (!eventFound)
				System.out.println("event not found!");
			// for (int i = 0; i < events.size; i++) {// loops through the list
			// 	if (events.retrieve().title.compareTo(enteredEvent.title) == 0) {// looks for event
			// 																		// with the
			// 																		// same title
			// 		System.out.println("Event found!");
			// 		System.out.println(events.retrieve().toString());
			// 		eventNotFound = false;
			// 		break;
			// 	}
			// 	events.findNext();
			// }
			
		}
	}

	public static void searchContact() {
		contactFound = false;
		// contacts.findFirst();
		contacts.findRoot();
		do {
			switch (choice) {
			case 1:
				System.out.println("enter contacts name: ");
				input.nextLine();
				enteredName = input.nextLine();
				contactFound = contacts.findkey(enteredName);
				if(contactFound){
					System.out.println(contacts.retrieve().toString());
				}
				break;
			case 2:
				System.out.println("enter contact phone number: ");
				entetedPhoneNumber = input.next();
				searchByPhoneNumber(contacts.root, entetedPhoneNumber);
				break;
			case 3:
				System.out.println("enter contact email address: ");
				enteredEmailAddress = input.next();
				searchByEmail(contacts.root,enteredEmailAddress);	
				break;
			case 4:
				System.out.println("enter contact address: ");
				enteredAddress = input.next();
				searchByAddress(contacts.root, enteredAddress);
				break;
			case 5:
				System.out.println("enter contact birthday: ");
				enteredBirthday = new Date(input.next());
				searchByBirthday(contacts.root, enteredBirthday);
				
				
				break;
			default:
				System.out.println("not a choice");
				
				
				while (true) {// 5
					System.out.print("\nEnter your choice: ");// 6
					try {// 7
						choice = Integer.parseInt(input.next());// 8
						break;// 9
					} catch (NumberFormatException e) {// 10
						System.out.println("please enter a valid number");// 11
					}
				
				}
				
			}
		} while (choice > 5 || choice < 1);
		if(!contactFound)
			System.out.println("contact not found!");
	}

	public static void main(String arg[]) {// 1

		System.out.println("Welcome to the BST Phonebook!");// 2
		do {// 3
			System.out.println("\nPlease choose an option:"// 4
					+ "\n1.Add a contact\n2.Search for a contact" + "\n3.Delete a contact \n4.Schedule an event/Appointment"
					+ "\n5.Print event details\n6.Print contacts by first name"
					+ "\n7.Print all events alphabetically \n8.Exit");

			while (true) {// 5
				System.out.print("\nEnter your choice: ");// 6
				try {// 7
					choice = Integer.parseInt(input.next());// 8
					break;// 9
				} catch (NumberFormatException e) {// 10
					System.out.println("please enter a valid number");// 11
				}

			}
			switch (choice) {
			case 1:
				addContact();
				break;

			case 2:
				if (contacts.empty()) {
					System.out.println("there are no contacts");
					break;
				}
				System.out.println("Enter search criteria:");
				System.out.println("1.Name");
				System.out.println("2.Phone Number");
				System.out.println("3.Email Address");
				System.out.println("4.Address");
				System.out.println("5.Birthday");
				while (true) {// 5
					System.out.print("\nEnter your choice: ");// 6
					try {// 7
						choice = Integer.parseInt(input.next());// 8
						break;// 9
					} catch (NumberFormatException e) {// 10
						System.out.println("please enter a valid number");// 11
					}
				
				}
				searchContact();
				break;
			case 3:
				DeleteContact();
				break;
			case 4:
				if (contacts.empty()) {
					System.out.println("you need to add contacts first!");
					break;
				}
				System.out.println("Enter type:\n1.Event\n2.appointment");
				choice = input.nextInt();
				if(choice ==1)
					ScheduleEvent(false);
				else if(choice ==2)
					ScheduleEvent(true);
				break;
			case 5:
				System.out.println("Enter search criteria: ");
				System.out.println("1. contact name\n2. Event title");
				choice = input.nextInt();
				switch (choice) {
				case 1:
					printEventByContactName();
					break;
				case 2:
					printEventByEventTitle();
					break;
				}
				break;
			case 6:
				PrintFirstName();
				break;
			case 7:
				if (events.empty())// Print all events alphabetically
					System.out.println("there are no events");
				events.printAll(events.root);
				
				break;
			case 8:
				System.out.println("Goodbye");
				break;
			default:
				System.out.println("Not a choice");
			}

		} while (choice != 8);

	}
//	public static void findEventBycontact(BSTNode<Contact> current,String name) 
//	{ 
//	
//		// If node is null, return 
//		if (contacts.current == null) 
//			return; 
//	if(contacts.current.data.name.equalsIgnoreCase(name)) {
//		contactNotFound = false;
//		BST<Event> foundEvent = contacts.retrieve().events;
//		foundEvent.findRoot();
//		foundEvent.printAll(events.current);
//		foundEvent.retrieve().contactNames.findRoot();
//		//foundEvent.retrieve().contactNames.printAll(foundEvent.retrieve().contactNames.current);
//		return;
//		
//	}
//		if (contacts.current.left == null && 
//			contacts.current.right == null ) 
//		{ 
//			
//			
//			return; 
//		} 
//		
//	
//		// If left child exists, check for leaf 
//		// recursively 
//		if (contacts.current.left != null) 
//			findEventBycontact(contacts.current.left,name); 
//	
//		// If right child exists, check for leaf 
//		// recursively 
//		if (contacts.current.right != null) 
//			findEventBycontact(contacts.current.right,name); 
//	}
	//--------------------------------------------------
	
	static void findEventBycontact(BSTNode<Contact> current,String name) 
	{ 

	    // If node is null, return 
	    if (current == null) 
	        return; 
	    if(current.data.name.equalsIgnoreCase(name)) {
	    	current.data.events.printAll(current.data.events.root);
	        contactNotFound = false;
	    }
	    // If node is leaf node, print its data
	    if (current.left == null && 
	    		current.right == null) 
	    { 
	        return; 
	    } 

	    // If left child exists, check for leaf 
	    // recursively 
	    if (current.left != null) 
	    	findEventBycontact(current.left, name); 

	    // If right child exists, check for leaf 
	    // recursively 
	    if (current.right != null) 
	    	findEventBycontact(current.right, name); 
	} 
	
	
	//--------------------------------------------------
	public static void searchByPhoneNumber(BSTNode<Contact> current,String phoneNumber) 
	{ 
	
		// If node is null, return 
		if (contacts.current == null) 
			return; 
	
		// If node is leaf node, print its data
		if (contacts.current.left == null && 
			contacts.current.right == null&& contacts.current.data.phonenumber.equalsIgnoreCase(phoneNumber)) 
		{ 
			
			System.out.print(contacts.retrieve().toString());
			contactFound = true;
			// contacts.retrieve().events.printAll(events.root);
			
			return; 
		} 
		else if(contacts.current.left == null && contacts.current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (contacts.current.left != null) 
			searchByPhoneNumber(contacts.current.left,phoneNumber); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (contacts.current.right != null) 
			searchByPhoneNumber(contacts.current.right,phoneNumber); 
	}
		public static void searchByEmail(BSTNode<Contact> current,String Email) 
	{ 
	
		// If node is null, return 
		if (contacts.current == null) 
			return; 
	
		// If node is leaf node, print its data
		if (contacts.current.left == null && 
			contacts.current.right == null&& contacts.current.data.email.equalsIgnoreCase(Email)) 
		{ 
			
			System.out.print(contacts.retrieve().toString());
			contactFound = true;
			// contacts.retrieve().events.printAll(events.root);
			
			return; 
		} 
		else if(contacts.current.left == null && contacts.current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (contacts.current.left != null) 
			searchByEmail(contacts.current.left,Email); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (contacts.current.right != null) 
			searchByEmail(contacts.current.right,Email); 
	}
	public static void searchByAddress(BSTNode<Contact> current,String Address) 
	{ 
	
		// If node is null, return 
		if (contacts.current == null) 
			return; 
	
		// If node is leaf node, print its data
		if (contacts.current.left == null && 
			contacts.current.right == null&& contacts.current.data.address.equalsIgnoreCase(Address)) 
		{ 
			
			System.out.print(contacts.retrieve().toString());
			contactFound = true;
			// contacts.retrieve().events.printAll(events.root);
			
			return; 
		} 
		else if(contacts.current.left == null && contacts.current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (contacts.current.left != null) 
			searchByAddress(contacts.current.left,Address); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (contacts.current.right != null) 
			searchByAddress(contacts.current.right,Address); 
	}
	public static void searchByBirthday(BSTNode<Contact> current,Date Birthday) 
	{ 
	
		// If node is null, return 
		if (contacts.current == null) 
			return; 
	
		// If node is leaf node, print its data
		if (contacts.current.left == null && 
			contacts.current.right == null&& contacts.current.data.birthday.compareTo(Birthday) ==0) 
		{ 
			
			System.out.print(contacts.retrieve().toString());
			contactFound = true; 
			// contacts.retrieve().events.printAll(events.root);
			
			return; 
		} 
		else if(contacts.current.left == null && contacts.current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (contacts.current.left != null) 
			searchByBirthday(contacts.current.left,Birthday); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (contacts.current.right != null) 
			searchByBirthday(contacts.current.right,Birthday); 
	}
	 
}
