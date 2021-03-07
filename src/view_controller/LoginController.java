package view_controller;
/**
 *
 * Class LoginController.java
 *
 * Here in this class I have the lambda statement that helps with the
 * translation of the phrases to french. It cuts back on a lot of code
 * I would have to write to switch all the text.
 *
 * The first lambda statement accepts an entire message and breaks it
 * down into individual words, which in turn the second lambda statement
 * translates those words.
 *
 */
import DAO.DBAppointments;
import DAO.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Appointments;
import sample.Message;
import sample.Translation;
import sample.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Frank Xander Morales
 */
public class LoginController {

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label titleLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button submitButton;
    @FXML private Label countryLabel;

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
     *  This initialize method checks to make sure if any
     *  appointments are coming up within 15 minutes
     */
    public void initialize(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(m.splitMessage("Information Dialog"));
        alert.setHeaderText(m.splitMessage("Appointments"));
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 300);

        // Checks for any appointments coming up within 15 minutes and sets the information dialog box accordingly.
        if(DBAppointments.appointmentIn15()){
            String s = m.splitMessage("An appointment is coming up in 15 minutes or less.") + "\n";
            Appointments a = DBAppointments.getAppointmentIn15();
            s = s + " " + m.splitMessage("Appointment ID") + ": " + a.getAppointmentId() + "\n" +
                    " " + m.splitMessage("Start Time") + ": " + a.getStartDateTime().toLocalTime() + "\n" +
                    " " + m.splitMessage("Start Date") + ": " + a.getStartDateTime().toLocalDate();
            alert.setContentText(s);
        }
        else { alert.setContentText(m.splitMessage("No appointment is coming up in 15 minutes or less.")); }
        alert.showAndWait();

        titleLabel.setText(m.splitMessage("Login"));
        usernameLabel.setText(m.splitMessage("Username"));
        passwordLabel.setText(m.splitMessage("Password"));
        submitButton.setText(m.splitMessage("Submit"));

        countryLabel.setText(m.splitMessage("Current Location") + " " + Locale.getDefault().getDisplayCountry());
    }

    /**
     *  This method checks the username and password
     *  against what is in the database to either send
     *  the user to the Main Form, or to display an error message.
     *  @param event Is an ActionEvent object.
     *  @exception IOException Failed to open scene.
     */
    public void submitButtonListener(ActionEvent event) throws IOException {
        // Search database for username, else display error message saying "Username/Password Incorrect. Please try again."
        ArrayList<User> userList = DBUser.getUsers();
        boolean hasFound = false;
        // checks the username and password combination
        for(User u : userList) {
            if ((usernameTextField.getText().matches(u.getName())) && (passwordTextField.getText().matches(u.getPassword()))) {
                hasFound = true;
                DBUser.setUserId(u.getId());
                DBUser.setUserName(u.getName());

            }
        }

        DBUser.loginAttempts(hasFound);

        if(!hasFound){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(m.splitMessage("Error Dialog"));
            alert.setHeaderText(m.splitMessage("Error"));
            alert.setContentText(m.splitMessage("Username or Password Incorrect. Try again!"));

            alert.showAndWait();
            return;
        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainForm.fxml"));
        Parent addPartScreen = loader.load();
        Scene addPartScene = new Scene(addPartScreen);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
}
