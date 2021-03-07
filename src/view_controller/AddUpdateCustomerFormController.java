package view_controller;
/**
 *
 * Class AddUpdateCustomerFormController.java
 */
import DAO.DBCountriesDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.*;
import DAO.DBCustomer;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Frank Xander Morales
 */
public class AddUpdateCustomerFormController {
    //private DBCustomer s = new DBCustomer();
    String formType = "Add Customer";
    int index = 0;
    //private Connection conn = DBConnection.getConnection();

    @FXML private Label titleLabel;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label postalCodeLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label countryLabel;
    @FXML private Label divisionLabel;
    @FXML private TextField customerIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private ComboBox<Division> firstDivisionComboBox;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private Label infoLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;


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
     *  This initialize method determines whether to populate
     *  the fields or leave them blank depending on the
     *  form requested.
     *  @param formType String to determine form type
     *  @param i
     */
    public void initialize(String formType, int i){
        //s = sc;
        this.formType = formType;
        index = i;
        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();

        // Set title label.
        titleLabel.setText(m.splitMessage(this.formType));

        countryComboBox.setItems(DBCountriesDivisions.getCountryList());

        if(this.formType.matches("Update Customer")){
            customerIdTextField.setText(String.valueOf(allCustomers.get(index).getCustomerId()));
            customerNameTextField.setText(allCustomers.get(index).getCustomerName());
            addressTextField.setText(allCustomers.get(index).getCustomerAddress());
            postalCodeTextField.setText(allCustomers.get(index).getCustomerPostalCode());
            phoneNumberTextField.setText(allCustomers.get(index).getCustomerPhoneNumber());

            String countryName = allCustomers.get(index).getCustomerCountry();
            Country countryObject = DBCountriesDivisions.getCountryObject(countryName);
            countryComboBox.setValue(countryObject);

            firstDivisionComboBox.setItems(DBCountriesDivisions.getDivisionList(countryObject.getCountryId()));
            String divisionName = allCustomers.get(index).getCustomerFirstDivision();
            Division divisionObject = DBCountriesDivisions.getDivisionObject(divisionName, countryObject.getCountryId());
            firstDivisionComboBox.setValue(divisionObject);
        }

        idLabel.setText(m.splitMessage("Customer ID"));
        nameLabel.setText(m.splitMessage("Customer Name"));
        addressLabel.setText(m.splitMessage("Address"));
        postalCodeLabel.setText(m.splitMessage("Postal Code"));
        phoneNumberLabel.setText(m.splitMessage("Phone Number"));
        countryLabel.setText(m.splitMessage("Country"));
        divisionLabel.setText(m.splitMessage("First Division"));
        saveButton.setText(m.splitMessage("Save"));
        cancelButton.setText(m.splitMessage("Cancel"));
    }

    /**
     *  This method adds or updates the customer object and
     *  sends the DBCustomer object back to the Main Form.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void saveButtonListener(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainForm.fxml"));
        Parent mainScreen = loader.load();
        Scene mainScene = new Scene(mainScreen);
        MainFormController controller = loader.getController();



        // Validate text boxes
        if(customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                postalCodeTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() ||
                countryComboBox.getSelectionModel().isEmpty() || firstDivisionComboBox.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No empty fields permitted."));
            return;
        }
        String name = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneNumberTextField.getText();
        int divisionId = firstDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionId();

        if(formType.matches("Add Customer"))
            DBCustomer.createCustomer(name, address, postalCode, phone, divisionId);

        if(formType.matches("Update Customer"))
            DBCustomer.updateCustomer(DBCustomer.getAllCustomers().get(index).getCustomerId(), name, address, postalCode, phone, divisionId);

        controller.initialize();

        // this line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

    /**
     *  This method goes directly back to the Main Form.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void cancelButtonListener(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *  This method changes the first division combo box
     *  based on the country combo box selection.
     *  @param event Is an ActionEvent object.
     */
    public void comboBoxListener(ActionEvent event){
        firstDivisionComboBox.getSelectionModel().clearSelection();
        firstDivisionComboBox.setItems(DBCountriesDivisions.getDivisionList(countryComboBox.getSelectionModel().getSelectedItem().getCountryId()));
    }
}
