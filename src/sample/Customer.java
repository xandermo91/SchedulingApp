package sample;
/**
 *
 * Class Customer.java
 */

import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/**
 *
 * @author Frank Xander Morales
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerCountry;
    private String customerFirstDivision;

    /**
     * Constructor method to create Customer object.
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param customerCountry
     * @param customerFirstDivision
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String customerCountry, String customerFirstDivision) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCountry = customerCountry;
        this.customerFirstDivision = customerFirstDivision;
    }

    /**
     * Getter method for customerId.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId.
     * @param customerId
     */
    public void setCustomerID(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for customerName.
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter method for customerName.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter method for customerAddress.
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter method for customer address.
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter method for customerPostalCode.
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Setter method for customerPostalCode.
     * @param customerPostalCode
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Getter method for customerPhoneNumber.
     * @return customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Setter method for customerPhoneNumber.
     * @param customerPhoneNumber
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Getter method for customerCountry.
     * @return customerCountry
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * Setter method for customerCountry.
     * @param customerCountry
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    /**
     * Getter method for customerFirstDivision.
     * @return customerFirstDivision
     */
    public String getCustomerFirstDivision() {
        return customerFirstDivision;
    }

    /**
     * Setter method for customerFirstDivision.
     * @param customerFirstDivision
     */
    public void setCustomerFirstDivision(String customerFirstDivision) {
        this.customerFirstDivision = customerFirstDivision;
    }

    /**
     * Display customer ID and name
     * @return String
     */
    @Override
    public String toString(){
        return customerId + " " + customerName;
    }
}
