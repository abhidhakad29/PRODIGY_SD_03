import java.io.*;
import java.util.*;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class ContactManager {
    private List<Contact> contacts;
    private final String filePath = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(String name, String phoneNumber, String email) {
        contacts.add(new Contact(name, phoneNumber, email));
        saveContacts();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void editContact(String name, String newPhoneNumber, String newEmail) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                contact.setPhoneNumber(newPhoneNumber);
                contact.setEmail(newEmail);
                saveContacts();
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        saveContacts();
    }

    private void loadContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                bw.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager manager = new ContactManager();
        String command;

        do {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    manager.addContact(name, phoneNumber, email);
                    break;
                case "2":
                    manager.viewContacts();
                    break;
                case "3":
                    System.out.print("Enter the name of the contact to edit: ");
                    String editName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    manager.editContact(editName, newPhoneNumber, newEmail);
                    break;
                case "4":
                    System.out.print("Enter the name of the contact to delete: ");
                    String deleteName = scanner.nextLine();
                    manager.deleteContact(deleteName);
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;
                default:
                   System.out.println("Invalid choice. Please try again.");
            }

        }while(command=="5");
    }
}
