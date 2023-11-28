/**
* Class for saving contact information. Used in
* ContactsApp.
* @author Roope Raparanta
*/

public class Contact {
    /**
    * String representing the ID of a contact.
    */
    private String id;

    /**
    * String representing the first name of a contact.
    */
    private String firstName;

    /**
    * String representing the last name of a contact.
    */
    private String lastName;

    /**
    * String representing the phone number of a contact.
    */
    private String phoneNumber;

    /**
    * String representing the address of a contact (optional).
    */
    private String address;

    /**
    * String representing the email of a contact (optional).
    */
    private String email;

    Contact(final String pId, final String pFirstName,
            final String pLastName, final String pPhoneNumber,
            final String pAddress, final String pEmail) {
        this.id = pId;
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.phoneNumber = pPhoneNumber;
        this.address = pAddress;
        this.email = pEmail;
    }

    /**
    * @return Formatted string that includes this.id
    */
    public String getId() {
        return "Id: " + id + "\n";
    }

    /**
    * @return Formatted string that includes this.firstName
    */
    public String getFirstName() {
        return "First name: " + firstName + "\n";
    }

    /**
    * @return Formatted string that includes this.lastName
    */
    public String getLastName() {
        return "Last name: " + lastName + "\n";
    }

    /**
    * @return Formatted string that includes this.phoneNumber
    */
    public String getPhoneNumber() {
        return "Phone number: " + phoneNumber + "\n";
    }

    /**
    * @return Formatted string that includes this.address
    */
    public String getAddress() {
        return "Address: " + address + "\n";
    }

    /**
    * @return Formatted string that includes this.email
    */
    public String getEmail() {
        return "Email: " + email + "\n";
    }

    /**
    * @return Formatted string that includes all parameters of contact.
    */
    public String getAll() {
        return getId() + getFirstName() + getLastName()
                + getPhoneNumber() + getAddress() + getEmail();
    }
}
