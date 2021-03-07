package sample;
/**
 *
 * Class Main.java
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.DBConnection;

import java.sql.*;
import java.util.Locale;

/**
 *
 * @author Frank Xander Morales
 */
public class Main extends Application {

    /**
     * The start method starts the program.
     * @param primaryStage the Stage to start
     * @exception Exception Failed get fxml resource.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("../view_controller/LoginForm.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    /**
     * The main method.
     * @param args the standard main method argument.
     */
    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
        // Locale.setDefault(Locale.FRANCE);
        launch(args);
        DBConnection.closeConnection();
    }
}
