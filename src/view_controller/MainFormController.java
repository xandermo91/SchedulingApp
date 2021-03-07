package view_controller;
/**
 *
 * Class MainFormController.java
 */
import DAO.DBAppointments;
import DAO.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Appointments;
import sample.Customer;
import DAO.DBCustomer;
import sample.Message;
import sample.Translation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Frank Xander Morales
 */
public class MainFormController {
    private DBCustomer s = new DBCustomer();

    // Customer table buttons
    @FXML private Button addCustomerButton;
    @FXML private Button updateCustomerButton;
    @FXML private Button deleteCustomerButton;

    // Tableview and columns for Customer Table
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIdColumn;
    @FXML private TableColumn<Customer, String> customerNameColumn;
    @FXML private TableColumn<Customer, String> customerAddressColumn;
    @FXML private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML private TableColumn<Customer, String> customerPhoneNumberColumn;
    @FXML private TableColumn<Customer, String> customerCountryColumn;
    @FXML private TableColumn<Customer, String> customerFirstDivisionColumn;

    // Appointments table buttons
    @FXML private Button addAppointmentsButton;
    @FXML private Button updateAppointmentsButton;
    @FXML private Button deleteAppointmentsButton;
    @FXML private Button reportsButton;
    @FXML private RadioButton monthRadioButton;
    @FXML private RadioButton weekRadioButton;

    // Tableview and columns for Appointments table
    @FXML private TableView<Appointments> appointmentsTable;
    @FXML private TableColumn<Appointments, Integer> appointmentsIdColumn;
    @FXML private TableColumn<Appointments, String> appointmentsTitleColumn;
    @FXML private TableColumn<Appointments, String> appointmentsDescriptionColumn;
    @FXML private TableColumn<Appointments, String> appointmentsLocationColumn;
    @FXML private TableColumn<Appointments, String> appointmentsContactColumn;
    @FXML private TableColumn<Appointments, String> appointmentsTypeColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentsStartColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentsEndColumn;
    @FXML private TableColumn<Appointments, Integer> appointmentsCustomerIdColumn;

    @FXML private Label infoLabel;
    @FXML private Label cusTableLabel;
    @FXML private Label appTableLabel;

    private Translation translate = message -> {
        if(Locale.getDefault().getLanguage().matches("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("files/Lang", Locale.getDefault());
            return rb.getString(message);
        }
        return message;
    };

    private Message m = message -> {
        String finalMessage = "";
        String[] array = message.split(" ");
        for(String s : array){
            finalMessage += translate.translate(s) + " ";
        }
        return finalMessage;
    };

    /**
     *  This initialize method sets up the tables in
     *  the Main Form.
     */
    public void initialize(){
        // set up the columns for the Parts table, and load data into table
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerIdColumn.setText(m.splitMessage("ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerNameColumn.setText(m.splitMessage("Name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        customerAddressColumn.setText(m.splitMessage("Address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostalCode"));
        customerPostalCodeColumn.setText(m.splitMessage("Postal Code"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhoneNumber"));
        customerPhoneNumberColumn.setText(m.splitMessage("Phone Number"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerCountry"));
        customerCountryColumn.setText(m.splitMessage("Country"));
        customerFirstDivisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerFirstDivision"));
        customerFirstDivisionColumn.setText(m.splitMessage("First Division"));

        customerTable.setItems(DBCustomer.getAllCustomers());

        // Set up Appointments table
        appointmentsIdColumn.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentId"));
        appointmentsIdColumn.setText(m.splitMessage("ID"));
        appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        appointmentsTitleColumn.setText(m.splitMessage("Title"));
        appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        appointmentsDescriptionColumn.setText(m.splitMessage("Description"));
        appointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        appointmentsLocationColumn.setText(m.splitMessage("Location"));
        appointmentsContactColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contact"));
        appointmentsContactColumn.setText(m.splitMessage("Contact"));
        appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        appointmentsTypeColumn.setText(m.splitMessage("Type"));
        appointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDateTime>("startDateTime"));
        appointmentsStartColumn.setText(m.splitMessage("Start"));
        appointmentsEndColumn.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDateTime>("endDateTime"));
        appointmentsEndColumn.setText(m.splitMessage("End"));
        appointmentsCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerId"));
        appointmentsCustomerIdColumn.setText(m.splitMessage("Customer ID"));

        appointmentsTable.setItems(DBAppointments.getAllAppointments("Month"));

        cusTableLabel.setText(m.splitMessage("Customer Table"));
        appTableLabel.setText(m.splitMessage("Appointments Table"));
        addAppointmentsButton.setText(m.splitMessage("Add"));
        updateAppointmentsButton.setText(m.splitMessage("Update"));
        deleteAppointmentsButton.setText(m.splitMessage("Delete"));
        reportsButton.setText(m.splitMessage("Reports"));
        monthRadioButton.setText(m.splitMessage("By Month"));
        weekRadioButton.setText(m.splitMessage("By Week"));
        addCustomerButton.setText(m.splitMessage("Add"));
        updateCustomerButton.setText(m.splitMessage("Update"));
        deleteCustomerButton.setText(m.splitMessage("Delete"));
    }

    /**
     *  This method opens the Add/Update Customer Pane
     *  to add a customer to the database.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void addCustomerButtonListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddUpdateCustomerForm.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);

        // access controller and call setInventory method
        AddUpdateCustomerFormController controller = loader.getController();
        controller.initialize("Add Customer", 0);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *  This method opens the Add/Update Customer Pane
     *  to add a customer to the database.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void updateCustomerButtonListener(ActionEvent event) throws IOException {
        // Verify selection has been made
        if(customerTable.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No selection made."));
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddUpdateCustomerForm.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);

        // access controller and call setInventory method
        AddUpdateCustomerFormController controller = loader.getController();
        controller.initialize("Update Customer", customerTable.getSelectionModel().getSelectedIndex());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *  This method deletes a customer from the database
     *  when the Delete button is pressed.
     *  @param event Is an ActionEvent object.
     */
    public void deleteCustomerButtonListener(ActionEvent event) {
        // Verify selection has been made
        if(customerTable.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No selection made."));
            return;
        }

        int id = customerTable.getSelectionModel().getSelectedItem().getCustomerId();

        // Verify to make sure the customer does not have any appointments
        if(DBCustomer.doesCustomerHaveAppointment(id)){
            infoLabel.setText(m.splitMessage("Delete appointments before deleting customer."));
            return;
        }

        // confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(m.splitMessage("Customer"));
        alert.setHeaderText(m.splitMessage("Delete"));
        alert.setContentText(m.splitMessage("Delete?"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DBCustomer.deleteCustomer(id);
            customerTable.setItems(DBCustomer.getAllCustomers());
        } else {
            return;
        }

    }

    /**
     *  This method opens the Add/Update Appointments Pane
     *  to add an appointment to the database.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void addAppointmentsButtonListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddUpdateAppointmentsForm.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *  This method opens the Add/Update Appointments Pane
     *  to add an appointment to the database.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void updateAppointmentsButtonListener(ActionEvent event) throws IOException {
        // Verify selection has been made
        if(appointmentsTable.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No selection made."));
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddUpdateAppointmentsForm.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);

        // access controller and call setInventory method
        AddUpdateAppointmentsFormController controller = loader.getController();
        controller.initialize(appointmentsTable.getSelectionModel().getSelectedItem());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     *  This method deletes an appointment from the database
     *  when the Delete button is pressed.
     *  @param event Is an ActionEvent object.
     */
    public void deleteAppointmentsButtonListener(ActionEvent event) {
        // Verify selection has been made
        if(appointmentsTable.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No selection made."));
            return;
        }

        int id = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();

        // confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(m.splitMessage("Appointment"));
        alert.setHeaderText(m.splitMessage("Delete"));
        alert.setContentText(m.splitMessage("Delete?"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DBAppointments.deleteAppointment(id);
            if(monthRadioButton.isSelected())
                appointmentsTable.setItems(DBAppointments.getAllAppointments("Month"));
            else
                appointmentsTable.setItems(DBAppointments.getAllAppointments("Week"));
        } else {
            return;
        }

    }

    /**
     *  This method opens the Reports pane when
     *  the Reports button is pressed.
     *  @param event Is an ActionEvent object.
     */
    public void reportsButtonListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReportsForm.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *  This method displays the appointments by month
     *  when the Month radio button is selected
     *  @param event Is an ActionEvent object.
     */
    public void monthRadioButtonListener(ActionEvent event)  {
        if(monthRadioButton.isSelected()){
            appointmentsTable.setItems(DBAppointments.getAllAppointments("Month"));
        }
    }

    /**
     *  This method displays the appointments by week
     *  when the Week radio button is selected
     *  @param event Is an ActionEvent object.
     */
    public void weekRadioButtonListener(ActionEvent event)  {
        if(weekRadioButton.isSelected()){
            appointmentsTable.setItems(DBAppointments.getAllAppointments("Week"));
        }

    }

}
