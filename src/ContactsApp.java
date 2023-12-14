import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.File;
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
     * JFrame for contactsDisplay JTextArea to be added to.
     */
    private JFrame viewWindow = new JFrame();

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

        // Frame layout and title for displaying contact list
        final int contactsX = 600;
        final int contactsY = 400;
        viewWindow.setTitle("Contact List");
        viewWindow.setSize(contactsX, contactsY);
        // Add scrollable component that has JTextArea inside.
        viewWindow.add(new JScrollPane(contactsDisplay));
        // Hide view window for contacts at start.
        viewWindow.setVisible(false);
    }
//----------------------SAVING-CONTACT-------------------------
    /**
     * Handles the button press for saving a contact.
     */
    public void saveButtonPress() {
        // Check for input errors or if id already saved in file
        if (inputValidation() || fileContainsID(true)) {
            return;
        }
        try {
            // Get Contact information as String
            String stringContactToSave = getContact();
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
        // Create information message pane
        infoPane("Saving Successful");
    }
//----------------------READING-CONTACTS-------------------------
    /**
     * Handles the button press for reading and displaying contacts
     * from a file.
     */
    public void readButtonPress() {
        final File f = new File("contacts.txt");
        final int minStrToDisplay = 5;
        if (f.exists()) {
            // Empty contactsDisplay text area
            contactsDisplay.selectAll();
            contactsDisplay.replaceSelection("");
            // Call fileToString() to get string for display
            String strToDisplay = fileToString(false);
            // If no contacts are saved
            if (strToDisplay.length() <= minStrToDisplay) {
                errorPane("Contacts are empty");
                return;
            }
            // Format string to look nice
            strToDisplay = strToDisplay.replaceAll(">", "\n");
            // Append string to display
            contactsDisplay.append(strToDisplay);
            viewWindow.setVisible(true);
            return;
        }
        // Create error message pane
        errorPane("No existing contacts file. Save a contact first.");
        return;
    }
//----------------------UPDATING-CONTACT-------------------------
    /**
     * Handles the button press for updating contact in
     * existing file. Gets all existing contact information and update
     * wanted contact.
     */
    public void updateButtonPress() {
        // Check for input errors
        if (inputValidation()) {
            return;
        }
        // String that replaces old information
        String replacement = getContact();
        // If file contains id to update
        if (fileContainsID(false)) {
            // Call editContact with replacement String
            editContact(replacement);
            // Create information message pane
            infoPane("Contact updated");
            // Refresh contact list window
            readButtonPress();
        } else {
            // Create error message pane
            errorPane("Id not found");
        }
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
        if (validateId()) {
            return;
        }
        if (fileContainsID(false)) {
            // Call editContact with replacement String
            editContact(replacement);
            // Create information message pane
            infoPane("Contact deleted");
            // Refresh contact list window
            readButtonPress();
        } else {
            // Create error message pane
            errorPane("Id not found");
        }
    }
//----------------------ERROR-HANDLING-------------------------
    /**
     * Creates JOptionPane that shows error message.
     * @param errorMessage error message to display
     */
    public void errorPane(final String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage,
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Checks if file contains unique attribute in textfield
     * (id, email, phone number).
     * @param saving true for use in saving button press,
     * false otherwise
     * @return true if file contains unique, otherwise false
     */
    public boolean fileContainsID(final boolean saving) {
        if (saving) {
            if (fileToString(true).contains(id.getText().toUpperCase())) {
                // Create error message pane
                errorPane("Id already saved");
                return true;
            }
            return false;
        } else {
            if (fileToString(false).contains(id.getText().toUpperCase())) {
                return true;
            }
            return false;
        }
    }

    /**
     * Check that all mandatory fields are filled.
     * @return true if not correct, false otherwise
     */
    public boolean validateFields() {
        // If mandatory details are not filled
        if (id.getText().isEmpty() || firstName.getText().isEmpty()
        || lastName.getText().isEmpty() || phoneNumber.getText().isEmpty()) {
            // Create error message pane
            errorPane("Fill mandatory details");
            return true;
        }
        return false;
    }

    /**
     * Validate id input with regex.
     * @return true if not correct, false otherwise
     */
    public boolean validateId() {
        // regex for checking personal id format
        final String idRegex = "^[0-9]{6}[-,A]{1}[0-9]{3}"
                                + "[0-9A-FHJ-NPS-Y]{1}$";
        // If personal id doesn't match id
        // regex
        if (!id.getText().matches(idRegex)) {
            // Create error message pane
            errorPane("Enter valid id");
            return true;
        }
        return false;
    }

    /**
     * Validate that names with regex.
     * @return true if not correct, false otherwise
     */
    public boolean validateNames() {
        // regex for checking name format
        // allows unicode characters.
        final String nameRegex = "^[\\p{L}]{2,}$";

        if (!firstName.getText().matches(nameRegex)) {
            // Create error message pane
            errorPane("Enter valid first name");
            return true;
        }
        if (!lastName.getText().matches(nameRegex)) {
            // Create error message pane
            errorPane("Enter valid last name");
            return true;
        }
        return false;
    }

    /**
     * Validate number input with regex.
     * @return true if not correct, false otherwise
     */
    public boolean validateNumber() {
        // regex for checking phone number format
        final String phoneRegex = "^[+]?[0-9]{0,2}[-]?[0-9]{0,4}"
                                    + "[0-9]{3,15}$";
        // if phone number doesn't match
        // phoneRegex
        if (!phoneNumber.getText().matches(phoneRegex)) {
            // Create error message pane
            errorPane("Enter valid phone number");
            return true;
        }
        return false;
    }

    /**
     * Validate address input with regex.
     * @return true if not correct, false otherwise
     */
    public boolean validateAddress() {
        // regex for checking address format
        final String addressRegex = "^[\\p{L}]+[ ]{1}[0-9]+[A-Za-z]?"
                                    + "[ ]?[0-9]{0,3}$";
        // If address doesn't match regex
        if (!address.getText().matches(addressRegex)) {
            // Create error message pane
            errorPane("Enter valid address");
            return true;
        }
        return false;
    }

    /**
     * Validate email input with regex.
     * @return true if not correct, false otherwise
     */
    public boolean validateEmail() {
        // Strict regex for checking email format
        // doesnt work for non-latin or unicode
        final String emailRegex = "^(?=.{1,}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)"
                                + "*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)"
                                + "*(\\.[A-Za-z]{2,})$";
        // If email doesnt match regex
        if (!email.getText().matches(emailRegex)) {
            // Create error message pane
            errorPane("Enter valid email address");
            return true;
        }
        return false;
    }

    /**
     * Calls input validation for all filled textfields.
     * @return true if there are errors, otherwise false
     */
    public boolean inputValidation() {
        // Validate mandatory input
        if (validateFields() || validateId()
        || validateNames() || validateNumber()) {
            return true;
        }

        // Validate optional input if fields are filled
        if (!email.getText().isEmpty()) {
            if (validateEmail()) {
                return true;
            }
        }

        if (!address.getText().isEmpty()) {
            if (validateAddress()) {
                return true;
            }
        }
        return false;
    }
//----------------------MISCELLANEOUS-------------------------
    /**
     * Creates JOptionPane that shows information message.
     * @param infoMessage information message to display
     */
    public void infoPane(final String infoMessage) {
        JOptionPane.showMessageDialog(new JFrame(), infoMessage,
                                "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Get current contact information as string.
     * @return string with contact information
     */
    public String getContact() {
        Contact updatedContact = newContact();
        return updatedContact.getAll();
    }

    /**
     * Clears text fields.
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
     * Gets text from file to String.
     * @param saving true for use in saving button press,
     * false otherwise
     * @return String with text from file. If
     * no current file then return empty string.
     */
    public String fileToString(final boolean saving) {
        if (saving) {
            try {
                return new String(Files.readAllBytes(
                            Paths.get("contacts.txt")));
            } catch (IOException e) {
                return "";
            }
        } else {
            try {
                return new String(Files.readAllBytes(
                            Paths.get("contacts.txt")));
            } catch (IOException e) {
                // Create error message pane
                errorPane("No existing contacts file. Save a contact first.");
                return "";
            }
        }
    }
//----------------------FOR-UPDATING-AND-DELETING-------------------------
    /**
     * Updates or deletes contact from file
     * based on replacement String.
     * @param replacement String that replaces contact line in file
     */
    public void editContact(final String replacement) {
        // Get Id from textfield
        String idToUpdate = id.getText().toUpperCase();
        // Get String from File
        String currentContacts = fileToString(false);
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
            // Start new BufferedWriter that also empties file
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
}
