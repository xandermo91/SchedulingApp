package sample;
/**
 *
 * Class Contacts.java
 */

/**
 *
 * @author Frank Xander Morales
 */
public class Contacts {
    int contactId;
    String contactName;

    /**
     * Constructor
     * @param contactId
     * @param contactName
     */
    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Getter for contact ID
     * @return int
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter for contact ID
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for contact name
     * @return String
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Display contact name
     * @return String
     */
    @Override
    public String toString(){
        return contactName;
    }
}
