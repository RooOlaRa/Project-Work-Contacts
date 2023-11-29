// TODO input validation
// TODO for ContactsApp: Make the buttons call on methods
// for updating and deleting a contact(reading and saving done)

import java.awt.GridLayout;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
* Simple contacts application for creating,
* reading, updating and deleting contacts.
* Contacts stored in a file persistently.
* @author Roope Raparanta
*/

public class ContactsApp extends JFrame {
    /**
    * Main-method of the project.
    * @param args Takes commandline arguments. Not used.
    */
    public static void main(final String[] args) {
        // Start ContactsApp
        new ContactsApp();
    }

    /**
    * JTextField for entering the ID of a contact.
    */
    private JTextField id = new JTextField();

    /**
    * JTextField for entering the first name of a contact.
    */
    private JTextField firstName = new JTextField();

    /**
     * JTextField for entering the last name of a contact.
    */
    private JTextField lastName = new JTextField();

    /**
    * JTextField for entering the phone number of a contact.
    */
    private JTextField phoneNumber = new JTextField();

    /**
    * JTextField for entering the address of a contact (optional).
    */
    private JTextField address = new JTextField();

    /**
    * JTextField for entering the email of a contact (optional).
    */
    private JTextField email = new JTextField();

    /**
    * JTextArea for displaying the list of contacts.
    */
    private JTextArea contactsDisplay = new JTextArea();

    /**
    * JButton for saving a contact.
    */
    private JButton save = new JButton("Save Contact");

    /**
    * JButton for viewing contacts.
    */
    private JButton read = new JButton("View Contacts");

    /**
    * JButton for updating a contact (not implemented yet).
    */
    private JButton update = new JButton("Update Contact");

    /**
    * JButton for deleting a contact (not implemented yet).
    */
    private JButton delete = new JButton("Delete Contact");

    ContactsApp() {
        // Frame layout and title for main window
        final int gridY = 8;
        final int gridX = 2;
        final int mainY = 600;
        final int mainX = 800;
        setLayout(new GridLayout(gridY, gridX));
        setTitle("Contacts Application");
        setSize(mainX, mainY);

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
     * Displays an error message if mandatory fields are not filled
     * or if the Finnish ID length is incorrect.
     * @return true if there are errors otherwise false
     */
    public boolean errorChecks() {
        final int idLength = 11;
        if (id.getText().isEmpty() || firstName.getText().isEmpty()
        || lastName.getText().isEmpty() || phoneNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Fill mandatory fields",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (id.getText().length() != idLength) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter Finnish Id",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    /**
     * Saves the contact information to a file.
     * @param contactToSave The Contact to be saved
     */
    public void saveFile(final Contact contactToSave) {
        try {
            String stringContactToSave = contactToSave.getAll();
            FileWriter fWriter = new FileWriter("contacts.txt", true);
            BufferedWriter writer = new BufferedWriter(fWriter);
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
     * Makes new contact from user input in
     * textfields.
     * @return Constructed contact
     */
    public Contact newContact() {
        return new Contact(id.getText(), firstName.getText(),
                            lastName.getText(), phoneNumber.getText(),
                            address.getText(), email.getText());
    }

    /**
     * Handles the button press for saving a contact.
     */
    public void saveButtonPress() {
        if (errorChecks()) {
            return;
        }
        Contact contactToSave = newContact();
        saveFile(contactToSave);
        clear();
        JOptionPane.showMessageDialog(new JFrame(), "Saving Successful",
        "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gets data from file to String.
     * @return String with from text file.
     */
    public String fileToString() {
        try {
            return new String(Files.readAllBytes(Paths.get("contacts.txt")));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "No existing contacts file",
            "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    /**
     * Handles the button press for reading and displaying contacts
     * from a file.
     */
    public void readButtonPress() {
        // Frame layout and title for displaying contact list
        JFrame viewWindow = new JFrame();
        final int contactsX = 600;
        final int contactsY = 400;
        viewWindow.setTitle("Contact List");
        viewWindow.setSize(contactsX, contactsY);
        viewWindow.add(new JScrollPane(contactsDisplay));
        contactsDisplay.selectAll();
        contactsDisplay.replaceSelection("");
        // Call fileToString() to get string to display
        String strToDisplay = fileToString();
        // Format string to look nice
        strToDisplay = strToDisplay.replaceAll(">", "\n");
        // Append string to display
        contactsDisplay.append(strToDisplay);
        viewWindow.setVisible(true);
    }
}
