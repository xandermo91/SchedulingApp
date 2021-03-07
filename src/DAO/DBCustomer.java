package DAO;
/**
 *
 * Class DBCustomer.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Country;
import sample.Customer;

import java.sql.*;

/**
 *
 * @author Frank Xander Morales
 */
public class DBCustomer {
    private static Connection conn = DBConnection.getConnection();

    /**
     * This static method returns the ObservableList
     * for the customers.
     * @return ObservableList
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String selectStatement = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, countries.Country, first_level_divisions.Division\n" +
                "FROM customers\n" +
                "INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID\n" +
                "INNER JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID;";
        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int customerId;
            String customerName;
            String customerAddress;
            String customerPostalCode;
            String customerPhoneNumber;
            String customerCountry;
            String customerFirstDivision;

            // Forward scroll ResultSet
            while (rs.next()) {
                customerId = rs.getInt("Customer_ID");
                customerName = rs.getString("Customer_Name");
                customerAddress = rs.getString("Address");
                customerPostalCode = rs.getString("Postal_Code");
                customerPhoneNumber = rs.getString("Phone");
                customerCountry = rs.getString("Country");
                customerFirstDivision = rs.getString("Division");

                allCustomers.add(new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerCountry, customerFirstDivision));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return allCustomers;
    }

    /**
     * This static method creates a customer
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionId
     */
    public static void createCustomer(String name, String address, String postalCode, String phone, int divisionId){
        try{
            String sql = "INSERT INTO customers VALUES(NULL,?,?,?,?,CURRENT_TIMESTAMP,?,NULL,?,?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, DBUser.getUserName());
            ps.setString(6, DBUser.getUserName());
            ps.setInt(7, divisionId);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method updates a customer
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionId
     */
    public static void updateCustomer(int id, String name, String address, String postalCode, String phone, int divisionId){
        try{
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, Division_ID=? WHERE customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5,DBUser.getUserName());
            ps.setInt(6, divisionId);
            ps.setInt(7, id);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method deletes a customer
     * @param id
     */
    public static void deleteCustomer(int id){

        try{
            String sql = "DELETE FROM customers WHERE Customer_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.execute();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This static method returns true if customer has appointment
     * or false if they don't have any more appointments.
     * @param id
     * @return boolean
     */
    public static boolean doesCustomerHaveAppointment(int id){

        //Connection conn = DBConnection.getConnection();
        String selectStatement = "SELECT Customer_ID FROM appointments;";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            // Forward scroll ResultSet
            while (rs.next()) {
                if(rs.getInt("Customer_ID") == id)
                    return true;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    /**
     * This static method returns a customer object based
     * on its name
     * @param customerId
     * @return Customer object
     */
    public static Customer getCustomerObject(int customerId) {
        ObservableList<Customer> listOfCustomers = getAllCustomers();
        for (Customer c : listOfCustomers) {
            if (c.getCustomerId() == customerId) {
                return c;
            }
        }
        return null;
    }
}
