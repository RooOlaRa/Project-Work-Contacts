// TODO input validation
// TODO for GUI: Make the buttons call on methods (reading done)
// TODO methods for saving, updating and deleting a contact

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Simple contacts application for creating, reading, updating and deleting contacts.
 * Contacts stored in a file persistently.
 * @author Roope Raparanta
 */

class ContactsApp {
    /**
     * Main-method of the project.
     * @param args Takes commandline arguments. Not used.
     */
    public static void main(String[] args) {
        // Start GUI
        Gui.run();
    }
}

class Gui extends JFrame {
    // Method for creating a new window.
    public static void run() {
        new Gui().setVisible(true);
    }

    // Create arraylist for saving contacts
    private ArrayList<Contact> contactList = new ArrayList<>();

    // Create text fields, buttons and text display area  
    private JTextField id = new JTextField();
    private JTextField firstName = new JTextField();
    private JTextField lastName = new JTextField();
    private JTextField phoneNumber = new JTextField();
    private JTextField address = new JTextField();
    private JTextField email = new JTextField();
    private JTextArea forReading = new JTextArea();
    private JButton save = new JButton("Save Contact");
    private JButton read = new JButton("Read Contact");
    private JButton update = new JButton("Update Contact");
    private JButton delete = new JButton("Delete Contact");

    public Gui() {
        // Frame layout and title for main window
        setTitle("Contacts");
        setSize(800, 600);
        setLayout(new FlowLayout());

        // Add labels and their matching text fields
        add(new JLabel("ID:"));
        add(id);

        add(new JLabel("First Name:"));
        add(firstName);

        add(new JLabel("Last Name:"));
        add(lastName);

        add(new JLabel("Phone Number:"));
        add(phoneNumber);

        add(new JLabel("Address (optional):"));
        add(address);

        add(new JLabel("e-mail (optional):"));
        add(email);

        // Methods for buttons
        public void saveFile() {
            FileOutputStream outputFile = new FileOutputStream("Contacts.txt");
            ObjectOutputStream outputObject = new ObjectOutputStream(outputFile);
            outputObject.writeObject(Gui.contactList);
            outputObject.close();
        }

        public void saveButtonPress() {
            saveFile();
        }

        public void readButtonPress() {
            display.selectAll();
            display.replaceSelection("");
            for(Contact contact : Gui.contactList) {
                forReading.append(contact.getAll());
            }
            forReading.append("\n");
        }

        public void updateButtonPress() {
            for(Contact contact : Gui.contactList) {

            }
        }

        public void deleteButtonPress() {
            for(Contact contact : Gui.contactList) {
                
            }
        }

        // Add buttons
        add(save);
        add(read);
        add(update);
        add(delete);

        // Add action listeners
        save.addActionListener(e -> saveButtonPress());
        read.addActionListener(e -> readButtonPress());
        update.addActionListener(e -> updateButtonPress());
        delete.addActionListener(e -> deleteButtonPress());
    }
}

/**
 * Class for saving contact information
 */
class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;

    public Contact(String id, String firstName, String lastName, String phoneNumber,String address, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
    // returns attributes of class as string for saving data to text file
    public String getId() {
        return "Id: " + id + "\n";
    }

    public String getFirstName() {
        return "First name: " + firstName + "\n";
    }

    public String getLastName() {
        return "Last name: " + lastName + "\n";
    }

    public String getPhoneNumber() {
        return "Phone number: " phoneNumber + "\n";
    }

    public String getAddress() {
        return "Address: " + address + "\n";
    }

    public String getEmail() {
        return "Email: " + email + "\n";
    }

    public String getAll() {
        return getId() + getFirstName() + getLastName() +
                getPhoneNumber() + getAddress() + getEmail();
    }
}
