package testttt;

import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;

public class Phonebook {
	public static Scanner input = new Scanner(System.in);
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
	static boolean searchFirstNamefound;

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
			if (contacts.findNumber(newContact.phonenumber)) {
				System.out.println("Contact found!");
				System.out.println(contacts.retrieve().toString());
				return;
			}
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
				contacts.update(desiredContact.name, desiredContact);
				if (events.findkey(newEvent.title)) {
					Event tmpEvent = events.retrieve();
					if ((tmpEvent.date.compareTo(newEvent.date) == 0)
							&& (tmpEvent.location.compareTo(newEvent.location) == 0)) {
						if (tmpEvent.appointment || newEvent.appointment) {
							System.out.println("conflict");
							return;
						}

						tmpEvent.contactNames.insert(desiredContact.name, desiredContact.name);
						desiredContact.events.update(tmpEvent.title, tmpEvent);
						events.update(tmpEvent.title, tmpEvent);
						contacts.update(desiredContact.name, desiredContact);

						oldEvent = true;
					}
				}
				if (!oldEvent) {
					newEvent.contactNames.insert(desiredContact.name, desiredContact.name);
					events.insert(newEvent.title, newEvent);
				}
				System.out.println("Event scheduled successfully!   ");
			} else
				System.out.println("Contact has conflict Event !  ");
		} else
			System.out.println("Contact not found !");
	}

	public static void searchFirstName(BSTNode current, String name) {

		// If node is null, return
		if (contacts.current == null)
			return;

		// If node is leaf node, print its data
		if (((Contact) current.data).name.split(" ")[0].equalsIgnoreCase(name)) {
			System.out.println(((Contact) current.data).toString());
			searchFirstNamefound = true;
		}

		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			searchFirstName(current.left, name);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			searchFirstName(current.right, name);
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
		searchFirstNamefound = false;
		searchFirstName(contacts.root, firstName);
		if(!searchFirstNamefound) {
			System.out.println("No contacts found!");
		}
	}
	public static void DeleteContact() {
		if (!contacts.empty()) {
			Contact enteredContact = new Contact();
			System.out.print("Enter the contact's name: ");
			input.nextLine();
			String name = input.nextLine();

			if (contacts.findkey(name)) {
				enteredContact = contacts.retrieve();
				contacts.removeKey(name);
				if (!enteredContact.events.empty()) {
					DeleteContactname(enteredContact.events.root, name);
				}
			}
		}

	}

	public static void DeleteContactname(BSTNode current, String name) {

		if (current == null)
			return;

		if (((Event)(current.data)).contactNames.removeKey(name)) {
			if (((Event)(current.data)).contactNames.empty()) {
				events.removeKey(((Event)(current.data)).title);
			}else{
				
				updatecontacts(((Event)(current.data)).contactNames.root,((Event)(current.data)));
			}
		}
		// If node is leaf node, print its data
		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			DeleteContactname(current.left, name);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			DeleteContactname(current.right, name);
	}
	public static void updatecontacts(BSTNode current, Event e) {
		
		if (current == null) 
	        return; 
	    
	    contacts.findkey((String)current.data);
	    Contact c = contacts.retrieve();
	    boolean d = c.events.update(e.title, e);
	    System.out.println("d = "+d);
	    if (current.left == null && 
	    		current.right == null) 
	    { 
	        return; 
	    } 

	    // If left child exists, check for leaf 
	    // recursively 
	    if (current.left != null) 
	    	updatecontacts(current.left, e); 

	    // If right child exists, check for leaf 
	    // recursively 
	    if (current.right != null) 
	    	updatecontacts(current.right, e); 
	} 
		
	



	public static void printEventByContactName() {
		contactNotFound = true;
		System.out.print("enter contact's name: ");
		input.nextLine();
		String contactName = input.nextLine();

		if (!contacts.empty()) {
			contacts.findRoot();
			findEventBycontact(contacts.current, contactName);
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
			}
			if (!eventFound)
				System.out.println("event not found!");

		}
	}

	public static void searchContact() {
		contactFound = false;
		contacts.findRoot();
		do {
			switch (choice) {
			case 1:
				System.out.println("enter contacts name: ");
				input.nextLine();
				enteredName = input.nextLine();
				contactFound = contacts.findkey(enteredName);
				if (contactFound) {
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
				searchByEmail(contacts.root, enteredEmailAddress);
				break;
			case 4:
				System.out.println("enter contact address: ");
				enteredAddress = input.next();
				searchByAddress(contacts.root, enteredAddress);
				break;
			case 5:
				while (true) {
					try {
						System.out.println("enter contact birthday: ");
						enteredBirthday = new Date(input.next());
						searchByBirthday(contacts.root, enteredBirthday);
						break;
					} catch (Exception e) {
						System.out.println("try again");
					}
				}
				break;
			default:
				System.out.println("not a choice");

				while (true) {
					System.out.print("\nEnter your choice: ");
					try {
						choice = Integer.parseInt(input.next());
						break;// 9
					} catch (NumberFormatException e) {
						System.out.println("please enter a valid number");
					}

				}

			}
		} while (choice > 5 || choice < 1);
		if (!contactFound)
			System.out.println("contact not found!");
	}


	static void findEventBycontact(BSTNode<Contact> current, String name) {

		// If node is null, return
		if (current == null)
			return;
		if (current.data.name.equalsIgnoreCase(name)) {
			current.data.events.printAll(current.data.events.root);
			contactNotFound = false;
		}
		// If node is leaf node, print its data
		if (current.left == null && current.right == null) {
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

	// --------------------------------------------------
	public static void searchByPhoneNumber(BSTNode<Contact> current, String phoneNumber) {

		// If node is null, return
		if (current == null)
			return;

		// If node is leaf node, print its data
		if (current.data.phonenumber.equalsIgnoreCase(phoneNumber)) {

			System.out.print(current.data.toString());
			contactFound = true;	
		}
		
		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			searchByPhoneNumber(current.left, phoneNumber);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			searchByPhoneNumber(current.right, phoneNumber);
	}

	public static void searchByEmail(BSTNode<Contact> current, String Email) {

		// If node is null, return
		if (current == null)
			return;

		// If node is leaf node, print its data
		if (current.data.email.equalsIgnoreCase(Email)) {
			System.out.print(current.data.toString());
			contactFound = true;
		} 
		
		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			searchByEmail(current.left, Email);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			searchByEmail(current.right, Email);
	}

	public static void searchByAddress(BSTNode<Contact> current, String Address) {

		// If node is null, return
		if (current == null)
			return;

		// If node is leaf node, print its data
		if (current.data.address.equalsIgnoreCase(Address)) {

			System.out.print(current.data.toString());
			contactFound = true;
	
		}
		
		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			searchByAddress(current.left, Address);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			searchByAddress(current.right, Address);
	}

	public static void searchByBirthday(BSTNode<Contact> current, Date Birthday) {

		// If node is null, return
		if (current == null)
			return;

		// If node is leaf node, print its data
		if (current.data.birthday.compareTo(Birthday) == 0) {

			System.out.print(current.data.toString());
			contactFound = true;
	
		}  
		
		if (current.left == null && current.right == null) {
			return;
		}

		// If left child exists, check for leaf
		// recursively
		if (current.left != null)
			searchByBirthday(current.left, Birthday);

		// If right child exists, check for leaf
		// recursively
		if (current.right != null)
			searchByBirthday(current.right, Birthday);
	}



	
	
	public static void main(String arg[]) {// 1

		System.out.println("Welcome to the BST Phonebook!");// 2
		do {// 3
			System.out.println("\nPlease choose an option:"// 4
					+ "\n1.Add a contact\n2.Search for a contact"
					+ "\n3.Delete a contact \n4.Schedule an event/Appointment"
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
				while (true) {// 5
					try {// 7
						choice = Integer.parseInt(input.next());// 8
						break;// 9
					} catch (NumberFormatException e) {// 10
						System.out.println("please enter a valid number");// 11
					}
				}
				if (choice == 1)
					ScheduleEvent(false);
				else if (choice == 2)
					ScheduleEvent(true);
				break;
			case 5:
				System.out.println("Enter search criteria: ");
				System.out.println("1. contact name\n2. Event title");
				while (true) {// 5
					try {// 7
						choice = Integer.parseInt(input.next());// 8
						break;// 9
					} catch (NumberFormatException e) {// 10
						System.out.println("please enter a valid number");// 11
					}
				}
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
				events.preorder(events.root);

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