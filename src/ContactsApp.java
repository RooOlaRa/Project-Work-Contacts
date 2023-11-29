// TODO input validation

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;

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
        update.addActionListener(e -> updateButtonPress());
        delete.addActionListener(e -> deleteButtonPress());
    }
//----------------------SAVING-CONTACT-------------------------
    /**
     * Handles the button press for saving a contact.
     */
    public void saveButtonPress() {
        // If errors found in textfield input return
        if (textFieldErrorChecks()) {
            return;
        }
        try {
            // Get Contact information as String
            String stringContactToSave = getInformationString();
            // Make writers
            FileWriter fWriter = new FileWriter("contacts.txt", true);
            BufferedWriter writer = new BufferedWriter(fWriter);
            // Write stringContactToSave to end of file
            writer.append(stringContactToSave);
            // Close writers
            writer.close();
            fWriter.close();
        } catch (IOException e) {
        }
        // Clear text fields
        clearTextFields();
        JOptionPane.showMessageDialog(new JFrame(), "Saving Successful",
        "Saved", JOptionPane.INFORMATION_MESSAGE);
    }
//----------------------READING-CONTACTS-------------------------
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
        // Empty contactsDisplay text area
        contactsDisplay.selectAll();
        contactsDisplay.replaceSelection("");
        // Call fileToString() to get string for display
        String strToDisplay = fileToString();
        // Format string to look nice
        strToDisplay = strToDisplay.replaceAll(">", "\n");
        // Append string to display
        contactsDisplay.append(strToDisplay);
        viewWindow.setVisible(true);
    }
//----------------------UPDATING-CONTACT-------------------------
    /**
     * Handles the button press for updating contact in
     * existing file. Gets all existing contact information and update
     * wanted contact.
     */
    public void updateButtonPress() {
        if (textFieldErrorChecks()) {
            return;
        }
        // String that replaces old information
        String replacement = getInformationString();
        // Call editContact with replacement String
        editContact(replacement);
        JOptionPane.showMessageDialog(new JFrame(), "Contact updated",
                            "Update", JOptionPane.INFORMATION_MESSAGE);
    }

//----------------------DELETING-CONTACT-------------------------
    /**
     * Handles the button press for deleting contact in
     * existing file. Is also the method that
     * handles the deleting itself.
     */
    public void deleteButtonPress() {
        // String that replaces old information
        String replacement = "";
        // Call editContact with replacement String
        editContact(replacement);
        JOptionPane.showMessageDialog(new JFrame(), "Contact deleted",
                            "Delete", JOptionPane.INFORMATION_MESSAGE);
    }
//----------------------ERROR-HANDLING-------------------------
    /**
     * Checks for errors in textfield input.
     * @return true if there are errors, otherwise false
     */
    public boolean textFieldErrorChecks() {
        final int idLength = 11;
        // If ID is already saved in file then ERROR
        if (fileToString().contains(id.getText().toUpperCase())) {
            JOptionPane.showMessageDialog(new JFrame(), "Id already saved",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        // For checking if '-' is in the right index of id
        final int idCheck = 5;
        // If ID is in wrong format
        if (id.getText().length() != idLength
            || id.getText().charAt(id.getText().length() - idCheck) != '-') {
            JOptionPane.showMessageDialog(new JFrame(), "Enter Finnish Id",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        // If mandatory fields are not filled
        if (id.getText().isEmpty() || firstName.getText().isEmpty()
        || lastName.getText().isEmpty() || phoneNumber.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Fill mandatory fields",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        // If email doesnt contain a dot or @
        if (!email.getText().isEmpty() && (!email.getText().contains("@")
            || !email.getText().contains("."))) {
            JOptionPane.showMessageDialog(new JFrame(),
            "Enter valid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }
//----------------------MISCELLANEOUS-------------------------
    /**
     * Updates or deletes contact from file
     * based on replacement String.
     * @param replacement String that replaces contact line in file
     */
    public void editContact(final String replacement) {
        // Get Id from textfield
        String idToUpdate = id.getText().toUpperCase();
        // Get String from File data
        String currentContacts = fileToString();
        // if idToUpdate is not found in currentContacts
        if (!currentContacts.contains(idToUpdate)) {
            JOptionPane.showMessageDialog(new JFrame(),
            "ID to update not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Start BufferedReader
        BufferedReader bReader = new BufferedReader(
                                new StringReader(currentContacts));
        // New ArrayList to save lines of text from file
        ArrayList<String> lines = new ArrayList<>();
        // String used for storing a line
        String rLine;
        try {
            // while there are lines left
            while ((rLine = bReader.readLine()) != null) {
                // add String rLine to ArrayList lines
                lines.add(rLine);
            }
            bReader.close();
        } catch (IOException e) {
        }
        // iterate through lines saved in ArrayList
        for (int i = 0; i < lines.size(); i++) {
            // get line at ArrayList index i
            String line = lines.get(i);
            if (line.contains(idToUpdate)) {
                // replace line that contains idToUpdate
                // with replacement parameter
                lines.set(i, replacement);
                break;
            }
        }
        try {
            // Start new BufferedWriter that also empties file of text
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(
                        "contacts.txt"), StandardOpenOption.TRUNCATE_EXISTING);
            // Write updated lines to contacts.txt
            // from ArrayList lines
            for (String line : lines) {
                // formatting for Strings so saved data stays
                // consistent
                line = line.replaceAll("\n", "");
                writer.write(line);
                writer.write("\n");
            }
            writer.close();
            // Clear text fields
            clearTextFields();
        } catch (IOException e) {
        }
    }

    /**
     * Get current contact information as string.
     * @return string with contact information
     */
    public String getInformationString() {
        Contact updatedContact = newContact();
        return updatedContact.getAll();
    }

    /**
     * Clears the input fields.
     */
    public void clearTextFields() {
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
        return new Contact(id.getText().toUpperCase(), firstName.getText(),
                            lastName.getText(), phoneNumber.getText(),
                            address.getText(), email.getText());
    }

    /**
     * Gets data from file to String.
     * @return String with from text file.
     */
    public String fileToString() {
        try {
            return new String(Files.readAllBytes(Paths.get("contacts.txt")));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(),
            "No existing contacts file. Save a contact first.", "Error",
            JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }
}
