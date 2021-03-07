package DAO;
/**
 *
 * Class DBAppointments.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.*;

import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 *
 * @author Frank Xander Morales
 */
public class DBAppointments {
    private static Connection conn = DBConnection.getConnection();

    /**
     * This static method returns the ObservableList
     * for the appointments.
     * @return ObservableList
     */
    public static ObservableList<Appointments> getAllAppointments(String viewType) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        String selectStatement = "Select Appointment_ID, Title, Description, Location, contacts.Contact_Name, Type, Start, End, Customer_ID, User_ID\n" +
                "FROM appointments, contacts\n" +
                "WHERE appointments.Contact_ID = contacts.Contact_ID;";
        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int appointmentId;
            String title;
            String description;
            String location;
            String contact;
            String type;
            Timestamp start;
            Timestamp end;
            int customerId;
            int userId;

            // Forward scroll ResultSet
            while (rs.next()) {
                appointmentId = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                description = rs.getString("Description");
                location = rs.getString("Location");
                contact = rs.getString("Contact_Name");
                type = rs.getString("Type");
                start = rs.getTimestamp("Start");
                end = rs.getTimestamp("End");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");

                LocalDate startDate = rs.getTimestamp("Start").toLocalDateTime().toLocalDate();
                double intervalDay = ChronoUnit.DAYS.between(startDate,LocalDate.now()) *  -1;
                double intervalMonth = ChronoUnit.DAYS.between(startDate,LocalDate.now()) *  -1;

                if(viewType.matches("Month")) {
                    if (intervalMonth >= 0 && intervalMonth <= 31)
                        allAppointments.add(new Appointments(appointmentId, title, description, location, contact, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId, userId));
                } else{
                    if (intervalDay >= 0 && intervalDay <= 7)
                        allAppointments.add(new Appointments(appointmentId, title, description, location, contact, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId, userId));
                }

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return allAppointments;
    }

    /**
     * This static method returns the ObservableList
     * for the appointments.
     * @return ObservableList
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        String selectStatement = "Select Contact_ID, Contact_Name\n" +
                "FROM contacts;";
        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int contactId;
            String contactName;

            // Forward scroll ResultSet
            while (rs.next()) {
                contactId = rs.getInt("Contact_ID");
                contactName = rs.getString("Contact_Name");

                allContacts.add(new Contacts(contactId, contactName));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allContacts;
    }

    /**
     * This static method returns a contact object
     * using its name
     * @param contactName
     * @return Contacts object
     */
    public static Contacts getContactObject(String contactName) {
        ObservableList<Contacts> listOfContacts = getAllContacts();
        for(Contacts c : listOfContacts){
            if(c.getContactName().matches(contactName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * This static method creates an appointment
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     */
    public static void createAppointment(String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId){
        try{
            String sql = "INSERT INTO appointments VALUES(NULL,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,NULL,?,?,?,?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, DBUser.getUserName());
            ps.setString(8, DBUser.getUserName());
            ps.setInt(9,customerId);
            ps.setInt(10,userId);
            ps.setInt(11,contactId);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method updates an appointment
     * @param id
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param userId
     * @param customerId
     */
    public static void updateAppointment(int id, String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId){
        try{
            String sql = "UPDATE appointments " +
                    "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? " +
                    "WHERE Appointment_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6,Timestamp.valueOf(end));
            ps.setString(7,DBUser.getUserName());
            ps.setInt(8,customerId);
            ps.setInt(9,userId);
            ps.setInt(10,contactId);
            ps.setInt(11, id);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method deletes an appointment
     * @param id
     */
    public static void deleteAppointment(int id){

        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method checks for any appointments within 15 minutes.
     * @return boolean
     */
    public static boolean appointmentIn15(){
        String selectStatement = "Select Start FROM appointments;";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            // Forward scroll ResultSet
            while (rs.next()) {

                LocalDateTime startTimeDate = rs.getTimestamp("Start").toLocalDateTime();
                LocalTime startTime = startTimeDate.toLocalTime();
                LocalDate startDate = startTimeDate.toLocalDate();
                long interval = ChronoUnit.MINUTES.between(startTime,LocalTime.now()) *  -1;
                if(interval > 0 && interval <= 15 && startDate.equals(LocalDate.now()))
                    return true;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * This static method checks for any appointments within 15 minutes.
     * @return Appointment
     */
    public static Appointments getAppointmentIn15(){
        ObservableList<Appointments> allAppointments = getAllAppointments();

        for(Appointments a:allAppointments){
            LocalDateTime startTimeDate = a.getStartDateTime();
            LocalTime startTime = startTimeDate.toLocalTime();
            LocalDate startDate = startTimeDate.toLocalDate();
            long interval = ChronoUnit.MINUTES.between(startTime,LocalTime.now()) *  -1;
            if(interval > 0 && interval <= 15 && startDate.equals(LocalDate.now()))
                return a;
        }

        return null;
    }

    /**
     * This static method returns the ObservableList
     * for the appointments.
     * @return ObservableList
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        String selectStatement = "Select Appointment_ID, Title, Description, Location, contacts.Contact_Name, Type, Start, End, Customer_ID, User_ID\n" +
                "FROM appointments, contacts\n" +
                "WHERE appointments.Contact_ID = contacts.Contact_ID;";
        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int appointmentId;
            String title;
            String description;
            String location;
            String contact;
            String type;
            Timestamp start;
            Timestamp end;
            int customerId;
            int userId;

            // Forward scroll ResultSet
            while (rs.next()) {
                appointmentId = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                description = rs.getString("Description");
                location = rs.getString("Location");
                contact = rs.getString("Contact_Name");
                type = rs.getString("Type");
                start = rs.getTimestamp("Start");
                end = rs.getTimestamp("End");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");

                allAppointments.add(new Appointments(appointmentId, title, description, location, contact, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId, userId));

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return allAppointments;
    }

    /**
     * This static method checks if Local date time overlaps with
     * local date time of any other appointments.
     * @param s
     * @param e
     * @return boolean
     */
    public static boolean overlap(LocalDateTime s, LocalDateTime e){
        ObservableList<Appointments> allAppointments = getAllAppointments();

        for(Appointments a:allAppointments){
            if((s.isAfter(a.getStartDateTime()) && s.isBefore(a.getEndDateTime())) ||
                    (e.isAfter(a.getStartDateTime()) && e.isBefore(a.getEndDateTime())) &&
                            (a.getStartDateTime().isAfter(s) && a.getStartDateTime().isBefore(e)))
                return true;
        }

        return false;
    }
}