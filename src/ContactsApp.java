// DONE: Basic framework for GUI and Contact class.
// TODO for class Contact: Getters and input validation.
// TODO for GUI: Make the buttons call on methods 
// TODO New classes: ButtonMethods(?), FileMethods, viewWindow

import java.io.Console;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public Gui() {
        // Frame layout and title for main window
        setTitle("Contacts");
        setSize(800, 400);
        // Shuts down the program instead of hiding it when
        // clicking "x" to shut down the GUI.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        // Create text fields
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();
        JTextField textField4 = new JTextField();
        JTextField textField5 = new JTextField();
        JTextField textField6 = new JTextField();

        // Create buttons
        JButton button1 = new JButton("Save Contact");
        JButton button2 = new JButton("Read Contact");
        JButton button3 = new JButton("Update Contact");
        JButton button4 = new JButton("Delete Contact");

        // Add text fields, labels and buttons
        add(new JLabel("Personal ID:"));
        add(textField1);
        add(new JLabel("First Name:"));
        add(textField2);
        add(new JLabel("Last Name:"));
        add(textField3);
        add(new JLabel("Phone Number:"));
        add(textField4);
        add(new JLabel("Address (optional):"));
        add(textField5);
        add(new JLabel("e-mail (optional):"));
        add(textField6);
        add(button1);
        add(button2);
        add(button3);
        add(button4);

        // Add action listeners to the buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 1 clicked");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 2 clicked");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 3 clicked");
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 4 clicked");
            }
        });
    }
}

/**
 * Class for creating and updating contacts.
 * Will mostly use this for input validation.
 */
class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;

    // only setters are done for now
    // _do input validation_

    public void setId (String id) {
        this.id = id;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void saveContact() {

    }
}
