package csc212;

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
						events.update(tmpEvent.title,tmpEvent);
						//
						desiredContact.events.insert(tmpEvent.title, tmpEvent);
						contacts.update(desiredContact.name,desiredContact);
						// awdawd
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
		if (contacts.current.left == null && 
			contacts.current.right == null&& contacts.current.data.name.split(" ")[0].equalsIgnoreCase(name)) 
		{ 
			
			contacts.current.toString();
			return; 
		} 
		else if(contacts.current.left == null && contacts.current.right == null ){
			return;
		}
	
		// If left child exists, check for leaf 
		// recursively 
		if (contacts.current.left != null) 
			searchFirstName(contacts.current.left,name); 
	
		// If right child exists, check for leaf 
		// recursively 
		if (contacts.current.right != null) 
			searchFirstName(contacts.current.right,name); 
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
		contacts.findRoot();
		searchFirstName(contacts.current, firstName);

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
	


	 }


	public static void DeleteContact() {
		if (!contacts.empty()) {
			Contact enteredContact = new Contact();
			System.out.print("Enter the contact's name: ");
			input.nextLine();
			enteredContact.name = input.nextLine();
			enteredContact = contacts.remove(enteredContact);
			// method remove returns null if it doesn't find the node
			if (enteredContact == null) {
				System.out.println("Contact not found!");
				return;
			}
			// if the deleted contact has events
			if (!enteredContact.events.empty()) {
				enteredContact.events.findFirst();
				for (int i = 0; i < enteredContact.events.size; i++) {
					Event contactEvents = enteredContact.events.retrieve();
					if (events.search(contactEvents)) {
						Event deleted_Contact_Event = events.retrieve();
						deleted_Contact_Event.removeContact(enteredContact.name);
						// if the event has no contact remove it
						if (deleted_Contact_Event.contactNames.empty()) {
							events.remove(contactEvents);
							System.out.println("Delete event, No cantact particapate");
						} else // if the event still has contacts update it
							events.update(deleted_Contact_Event);
					}
					enteredContact.events.findNext();
				}
			}
			System.out.println("Contact Deleted Successfully !");
			System.out.println(enteredContact.toString());
		} else
			System.out.println("there is no contacts");

	}

	public static void printEventByContactName() {
		boolean contactNotFound = true;
		System.out.print("enter contact's name: ");
		input.nextLine();
		String contactName = input.nextLine();
		if (!contacts.empty()) {
			contacts.findFirst();
			for (int i = 0; i < contacts.size; i++) {
				if (contacts.retrieve().name.compareTo(contactName) == 0) {// checks for contact
					Event event = contacts.retrieve().events.retrieve();// stores in Event obj
					if (!contacts.retrieve().events.empty()) {/* if contact has event it prints */
						System.out.println(event.toString());/* the event with all contacts */
						contactNotFound = false;
					} else {
						System.out.println("No events found for this contact!");
					}
				}
				contacts.findNext();
			}
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
			events.findFirst();
			boolean eventNotFound = true;
			for (int i = 0; i < events.size; i++) {// loops through the list
				if (events.retrieve().title.compareTo(enteredEvent.title) == 0) {// looks for event
																					// with the
																					// same title
					System.out.println("Event found!");
					System.out.println(events.retrieve().toString());
					eventNotFound = false;
					break;
				}
				events.findNext();
			}
			if (eventNotFound)
				System.out.println("event not found!");
		}
	}

	public static void searchContact() {
		boolean contactNotFound = true;
		boolean numberNotFound = true;
		boolean emailNotFound = true;
		boolean addressNotFound = true;
		boolean birthdayNotFound = true;
		contacts.findFirst();
		do {
			switch (choice) {
			case 1:
				System.out.println("enter contacts name: ");
				input.nextLine();
				enteredName = input.nextLine();
				for (int i = 0; i < contacts.size; i++) {
					if (contacts.retrieve().name.compareTo(enteredName) == 0) {
						System.out.println("contact found:\n" + contacts.retrieve());
						contactNotFound = false;
						break;
					}
					contacts.findNext();

				}
				if (contactNotFound)
					System.out.println("contact not found");
				break;
			case 2:
				System.out.println("enter contact phone number: ");
				entetedPhoneNumber = input.next();
				for (int i = 0; i < contacts.size; i++) {
					if (contacts.retrieve().phonenumber.compareTo(entetedPhoneNumber) == 0) {
						System.out.println("contact found:\n" + contacts.retrieve());
						numberNotFound = false;
						break;
					}
					contacts.findNext();

				}
				if (numberNotFound)
					System.out.println("contact not found");
				break;
			case 3:
				System.out.println("enter contact email address: ");
				int nEmail = 1;
				enteredEmailAddress = input.next();
				for (int i = 0; i < contacts.size; i++) {
					if (contacts.retrieve().email.compareTo(enteredEmailAddress) == 0) {
						System.out.println(nEmail++ + "-" + contacts.retrieve());
						emailNotFound = false;

					}
					contacts.findNext();
				}
				if (emailNotFound)
					System.out.println("contact not found");
				break;

			case 4:
				System.out.println("enter contact address: ");
				int nAddress = 1;
				enteredAddress = input.next();
				for (int i = 0; i < contacts.size; i++) {
					if (contacts.retrieve().address.compareTo(enteredAddress) == 0) {
						System.out.println(nAddress++ + "-" + contacts.retrieve());
						addressNotFound = false;
					}
					contacts.findNext();
				}
				if (addressNotFound)
					System.out.println("contact not found");
				break;
			case 5:
				System.out.println("enter contact birthday: ");
				int nBirthday = 1;
				enteredBirthday = new Date(input.next());
				for (int i = 0; i < contacts.size; i++) {
					if (contacts.retrieve().birthday.compareTo(enteredBirthday) == 0) {
						System.out.println(nBirthday++ + "-" + contacts.retrieve());
						birthdayNotFound = false;
					}
					contacts.findNext();
				}
				if (birthdayNotFound)
					System.out.println("contact not found");
				break;
			default:
				System.out.println("not a choice");
			}
		} while (choice > 5 || choice < 1);

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
				System.out.println("\nEnter your choice: ");
				choice = input.nextInt();
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
				System.out.println("1. contact name\n2. Event tittle");
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
				else {
					events.findFirst();
					for (int i = 0; i < events.size; i++) {
						System.out.println("-" + events.retrieve().title);// retrieve title and prints it
						events.findNext();
					}
				}
				break;
			case 8:
				System.out.println("Goodbye");
				break;
			default:
				System.out.println("Not a choice");
			}

		} while (choice != 8);

	}
}
