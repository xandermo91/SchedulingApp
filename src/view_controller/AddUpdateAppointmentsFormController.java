package view_controller;
/**
 *
 * Class AddUpdateAppointmentsFormController.java
 */
import DAO.DBAppointments;
import DAO.DBCountriesDivisions;
import DAO.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import DAO.DBCustomer;
import sample.*;

import java.io.IOException;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 *
 * @author Frank Xander Morales
 */
public class AddUpdateAppointmentsFormController {
    private Appointments appointment;
    String formType = "Add Appointment";

    @FXML private Label titleLabel;
    @FXML private TextField appointmentIdTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private ComboBox<Contacts> contactComboBox;
    @FXML private TextField typeTextField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;
    @FXML private ComboBox<Customer> customerIdComboBox;
    @FXML private ComboBox<User> userIdComboBox;
    @FXML private Label infoLabel;
    @FXML private Label idLabel;
    @FXML private Label titleFieldLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label contactLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label customerIdLabel;
    @FXML private Label userIdLabel;
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
     *  This initialize method is for adding new appointments.
     *
     */
    public void initialize(){
        titleLabel.setText(m.splitMessage(formType));

        // Initialize combo boxes
        contactComboBox.setItems(DBAppointments.getAllContacts());
        customerIdComboBox.setItems(DBCustomer.getAllCustomers());
        userIdComboBox.setItems(DBUser.getObservableListUsers());

        // This sets up the time combo boxes
        LocalDate ESTDate = LocalDate.of(2020, 1, 1);
        LocalTime ESTTimeStart = LocalTime.of(6, 00);
        LocalDate ESTEndDate = LocalDate.of(2020, 1, 1);
        LocalTime ESTTimeEnd = LocalTime.of(22, 00);
        ZoneId ESTZoneId = ZoneId.of("America/New_York");
        ZonedDateTime startzdt = ZonedDateTime.of(ESTDate,ESTTimeStart,ESTZoneId);
        ZonedDateTime endzdt = ZonedDateTime.of(ESTEndDate,ESTTimeEnd,ESTZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startToLocalZDT = startzdt.withZoneSameInstant(localZoneId);
        ZonedDateTime endToLocalZDT = endzdt.withZoneSameInstant(localZoneId);
        LocalTime start = startToLocalZDT.toLocalTime();
        LocalTime end = endToLocalZDT.toLocalTime();
        while(start.isBefore(end.plusSeconds(1))){
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(15);
        }

        idLabel.setText(m.splitMessage("ID"));
        titleFieldLabel.setText(m.splitMessage("Title"));
        descriptionLabel.setText(m.splitMessage("Description"));
        locationLabel.setText(m.splitMessage("Location"));
        contactLabel.setText(m.splitMessage("Contact"));
        typeLabel.setText(m.splitMessage("Type"));
        dateLabel.setText(m.splitMessage("Date"));
        startLabel.setText(m.splitMessage("Start Time"));
        endLabel.setText(m.splitMessage("End Time"));
        customerIdLabel.setText(m.splitMessage("Customer ID"));
        userIdLabel.setText(m.splitMessage("User ID"));
        saveButton.setText(m.splitMessage("Save"));
        cancelButton.setText(m.splitMessage("Cancel"));
    }

    /**
     *  This initialize method is for updating appointments
     * @param a
     */
    public void initialize(Appointments a){
        appointment = a;
        formType = "Update Appointment";
        titleLabel.setText(m.splitMessage(formType));

        //Initialize all the fields according to the appointment sent in
        appointmentIdTextField.setText(String.valueOf(a.getAppointmentId()));
        titleTextField.setText(a.getTitle());
        descriptionTextField.setText(a.getDescription());
        locationTextField.setText(a.getLocation());
        typeTextField.setText(a.getType());

        contactComboBox.setItems(DBAppointments.getAllContacts());
        contactComboBox.setValue(DBAppointments.getContactObject(a.getContact()));
        customerIdComboBox.setItems(DBCustomer.getAllCustomers());
        customerIdComboBox.setValue(DBCustomer.getCustomerObject(a.getCustomerId()));
        userIdComboBox.setItems(DBUser.getObservableListUsers());
        userIdComboBox.setValue(DBUser.getUserObject(a.getUserId()));

        LocalDateTime startDateTime = a.getStartDateTime();
        LocalDateTime endDateTime = a.getEndDateTime();

        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        startTimeComboBox.setValue(startTime);
        endTimeComboBox.setValue(endTime);

        LocalDate date = startDateTime.toLocalDate();

        datePicker.setValue(date);
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
        if(titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() ||
                locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || datePicker.getValue() == null ||
                startTimeComboBox.getSelectionModel().isEmpty() || endTimeComboBox.getSelectionModel().isEmpty()){
            infoLabel.setText(m.splitMessage("No empty fields permitted."));
            return;
        }

        if(formType.matches("Add Appointment")){
            if(contactComboBox.getSelectionModel().isEmpty() || customerIdComboBox.getSelectionModel().isEmpty() || userIdComboBox.getSelectionModel().isEmpty()){
                infoLabel.setText(m.splitMessage("No empty fields permitted."));
                return;
            }
        }

        // Validate time choices
        if(startTimeComboBox.getSelectionModel().getSelectedItem().isAfter(endTimeComboBox.getSelectionModel().getSelectedItem())){
            infoLabel.setText(m.splitMessage("Start Time and Date must be before End Time and Date"));
            return;
        }

        LocalDateTime s = LocalDateTime.of(datePicker.getValue(),startTimeComboBox.getValue());
        LocalDateTime e = LocalDateTime.of(datePicker.getValue(),endTimeComboBox.getValue());

        if(DBAppointments.overlap(s, e)){
            infoLabel.setText(m.splitMessage("Start or End Time and Date overlaps with another appointment"));
            return;
        }

        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        int contactId = contactComboBox.getSelectionModel().getSelectedItem().getContactId();
        String type = typeTextField.getText();
        int customerId = customerIdComboBox.getSelectionModel().getSelectedItem().getCustomerId();
        int userId = userIdComboBox.getSelectionModel().getSelectedItem().getId();

        //createAppointment(String title, String description, String location, int contactId, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId)
        if(formType.matches("Add Appointment"))
            DBAppointments.createAppointment(title,description,location,contactId,type,s,e,customerId,userId);

        if(formType.matches("Update Appointment"))
            DBAppointments.updateAppointment(appointment.getAppointmentId(),title,description,location,contactId,type,s,e,customerId,userId);

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
}