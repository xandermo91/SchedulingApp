package sample;
/**
 *
 * Class Appointments.java
 */

import java.time.LocalDateTime;

/**
 *
 * @author Frank Xander Morales
 */
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private int customerId;
    private int userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Constructor
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param customerId
     * @param userId
     * @param startDateTime
     * @param endDateTime
     */
    public Appointments(int appointmentId, String title, String description, String location, String contact, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.customerId = customerId;
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Getter for appointment ID
     * @return int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter for appointment ID
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter for title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location
     * @return String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for description
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for contact
     * @return String
     */
    public String getContact() {
        return contact;
    }

    /**
     * Setter for contact
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Getter for type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for customer ID
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for customer ID
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for user ID
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for user ID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for start date
     * @return LocalDateTime
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Setter for start date
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Getter for end date
     * @return LocalDateTime
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Setter for end date
     * @param endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
