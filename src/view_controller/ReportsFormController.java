package view_controller;
/**
 *
 * Class ReportsFormController.java
 */

import DAO.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Appointments;
import sample.Contacts;
import sample.Message;
import sample.Translation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Frank Xander Morales
 */
public class ReportsFormController {

    @FXML private TextArea firstReportTextArea;
    @FXML private TextArea secondReportTextArea;
    @FXML private TextArea thirdReportTextArea;
    @FXML private Tab firstTab;
    @FXML private Tab secondTab;
    @FXML private Tab thirdTab;
    @FXML private Button backButton;

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
     *  Initialize method to generate the reports
     */
    public void initialize(){

        firstTab.setText(m.splitMessage("Report 1"));
        secondTab.setText(m.splitMessage("Report 2"));
        thirdTab.setText(m.splitMessage("Report 3"));
        backButton.setText(m.splitMessage("Back"));

        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        ArrayList<String> typeList = new ArrayList<>();
        String message = "";
        for(Appointments a : allAppointments){
            if(!typeList.contains(a.getType())) {
                typeList.add(a.getType());
            }
        }


        for(String s:typeList){
            ArrayList<Appointments> appTypeList = new ArrayList<>();
            message += m.splitMessage("Type of Appointment") + ": " + s;
            int count = 0;
            for(Appointments a:allAppointments){
                if(a.getType().matches(s)){
                    appTypeList.add(a);
                    count++;
                }
            }
            for(Appointments x:appTypeList) {
                message += "\n" + m.splitMessage("Date") + ": " + x.getStartDateTime().toLocalDate();
            }
            message += "\n" + m.splitMessage("Number of appointments") + ": " + count + "\n\n";
        }
        firstReportTextArea.setText(message);



        ObservableList<Contacts> allContacts = DBAppointments.getAllContacts();
        message = "";
        for(Contacts c:allContacts){
            message += m.splitMessage("CONTACT") + ": " + c.getContactName();
            for(Appointments a:allAppointments){
                if(a.getContact().matches(c.getContactName())){
                    message += "\n" + m.splitMessage("Appointment ID") + ": " + a.getAppointmentId() +
                            " -- " + m.splitMessage("Title") + ": " + a.getTitle() +
                            " -- " + m.splitMessage("Type") + ": " + a.getType() +
                            " -- " + m.splitMessage("Description") + ": " + a.getDescription() +
                            "\n" + m.splitMessage("Start Date and Time") + ": " + a.getStartDateTime() +
                            " -- " + m.splitMessage("End Date and Time") + ": " + a.getEndDateTime() +
                            " -- " + m.splitMessage("Customer ID") + ": " + a.getCustomerId() + "\n";
                }
            }
            message += "\n\n";
        }
        secondReportTextArea.setText(message);




        message = m.splitMessage("Appointments past") + ":\n";
        for(Appointments a:allAppointments){
            if(a.getEndDateTime().isBefore(LocalDateTime.now())){
                message += "\n" + m.splitMessage("Appointment ID") + ": " + a.getAppointmentId() +
                        " -- " + m.splitMessage("Title") + ": " + a.getTitle() +
                        " -- " + m.splitMessage("Type") + ": " + a.getType() +
                        " -- " + m.splitMessage("Description") + ": " + a.getDescription() +
                        "\n" + m.splitMessage("Start Date and Time") + ": " + a.getStartDateTime() +
                        " -- " + m.splitMessage("End Date and Time") + ": " + a.getEndDateTime() +
                        " -- " + m.splitMessage("Customer ID") + ": " + a.getCustomerId() + "\n";
            }
        }
        thirdReportTextArea.setText(message);

    }

    /**
     *  This method goes directly back to the Main Form.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void backButtonListener(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
