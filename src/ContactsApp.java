// TODO input validation
// TODO for GUI: Make the buttons call on methods for updating and deleting a contact(reading and saving done)

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        new Gui();
    }
}

class Gui extends JFrame {

    // Create text fields, buttons and text display area  
    private JTextField id = new JTextField();
    private JTextField firstName = new JTextField();
    private JTextField lastName = new JTextField();
    private JTextField phoneNumber = new JTextField();
    private JTextField address = new JTextField();
    private JTextField email = new JTextField();
    private JTextArea contactsDisplay = new JTextArea();
    private JButton save = new JButton("Save Contact");
    private JButton read = new JButton("View Contacts");
    private JButton update = new JButton("Update Contact");
    private JButton delete = new JButton("Delete Contact");

    public Gui() {
        // Frame layout and title for main window
        setLayout(new GridLayout(8,2));
        setTitle("Contacts Application");
        setSize(800, 600);

        // Program shuts down when clicking "x" in main window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add labels and their matching text fields
        add(new JLabel("ID (mandatory):"));
        add(id);
        add(new JLabel("First Name (mandatory):"));
        add(firstName);
        add(new JLabel("Last Name (mandatory):"));
        add(lastName);
        add(new JLabel("Phone Number (mandatory):"));
        add(phoneNumber);
        add(new JLabel("Address (optional):"));
        add(address);
        add(new JLabel("e-mail (optional):"));
        add(email);

        // Add buttons
        add(save);
        add(read);
        add(update);
        add(delete);

        // Add display for reading contacts
        contactsDisplay.setEditable(false);
        setVisible(true);

        // Add action listeners
        save.addActionListener(e -> saveButtonPress());
        read.addActionListener(e -> readButtonPress());
    }

    /**
     * Checks for errors in textfield input.
     * Displays an error message if mandatory fields are not filled or if the Finnish ID length is incorrect.
     * @return true if there are errors, false otherwise
     */
    public boolean errorChecks() {
        if(id.getText().isEmpty() || firstName.getText().isEmpty() || 
            lastName.getText().isEmpty() || phoneNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Fill mandatory fields", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(id.getText().length() != 11) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter Finnish Id", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * Saves the contact information to a file.
     * @param contactToSave The Contact to be saved
     */
    public void saveFile(Contact contactToSave) {
        try {
            String stringContactToSave = contactToSave.getAll();
            BufferedWriter writer = new BufferedWriter(new FileWriter("contacts.txt", true));
            writer.append(stringContactToSave);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            
        }
    }

    /**
     * Clears the input fields.
     */
    public void clear() {
        id.setText("");
        firstName.setText("");
        lastName.setText("");
        phoneNumber.setText("");
        address.setText("");
        email.setText("");
    }

    /**
     * Handles the button press for saving a contact.
     */
    public void saveButtonPress() {
        if(errorChecks()) {
            return;
        }
        Contact contactToSave = new Contact(id.getText(), firstName.getText(), lastName.getText(),
                                    phoneNumber.getText(), address.getText(), email.getText());
        saveFile(contactToSave);
        clear();
        JOptionPane.showMessageDialog(new JFrame(), "Saving Successful", "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the button press for reading and displaying contacts
     * from a file.
     */
    public void readButtonPress() {
        // Frame layout and title for displaying contact list
        JFrame viewWindow = new JFrame();
        viewWindow.setTitle("Contact List");
        viewWindow.setSize(600, 400);
        viewWindow.add(new JScrollPane(contactsDisplay));
        contactsDisplay.selectAll();
        contactsDisplay.replaceSelection("");

        try {
            FileReader fr = new FileReader("contacts.txt");
            BufferedReader reader = new BufferedReader(fr);
            contactsDisplay.read(reader, "contacts.txt");
            viewWindow.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "No existing contacts", "Error", JOptionPane.ERROR_MESSAGE);
        }
        contactsDisplay.append("\n");
    }
}


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
        return "Phone number: " + phoneNumber + "\n";
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
