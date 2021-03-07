package DAO;
/**
 *
 * Class DBUser.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Appointments;
import sample.Contacts;
import sample.Country;
import sample.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Frank Xander Morales
 */
public class DBUser {
    private static Connection conn = DBConnection.getConnection();
    private static int userId;
    private static String userName = "";

    /**
     * This method gets a list of users.
     * @return ArrayList
     */
    public static ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();

        String selectStatement = "Select User_ID, User_Name, Password FROM users;";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int userId;
            String userName;
            String password;

            // Forward scroll ResultSet
            while (rs.next()) {
                userId = rs.getInt("User_ID");
                userName = rs.getString("User_Name");
                password = rs.getString("Password");

                userList.add(new User(userName, password, userId));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return userList;
    }

    /**
     * This method gets a list of users.
     * @return ArrayList
     */
    public static ObservableList<User> getObservableListUsers(){
        ObservableList<User> userList = FXCollections.observableArrayList();

        String selectStatement = "Select User_ID, User_Name, Password FROM users;";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int userId;
            String userName;
            String password;

            // Forward scroll ResultSet
            while (rs.next()) {
                userId = rs.getInt("User_ID");
                userName = rs.getString("User_Name");
                password = rs.getString("Password");

                userList.add(new User(userName, password, userId));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return userList;
    }


    /**
     * This method sets the user ID.
     * @param id
     */
    public static void setUserId(int id){
        userId = id;
    }

    /**
     * This method gets the user ID.
     * @return int
     */
    public static int getUserId(){
        return userId;
    }

    /**
     * This method sets the user name.
     * @param n
     */
    public static void setUserName(String n){
        userName = n;
    }

    /**
     * This method gets the user name.
     * @return String
     */
    public static String getUserName(){
        return userName;
    }

    /**
     * This static method returns a User object
     * using its id
     * @param id
     * @return User object
     */
    public static User getUserObject(int id) {
        ArrayList<User> listOfUsers = getUsers();
        for(User u : listOfUsers){
            if(u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * This static method writes out in a text file
     * the login attempts
     * @param hasFound
     * @exception IOException
     */
    public static void loginAttempts(boolean hasFound) throws IOException {
        FileWriter fw = new FileWriter("src/files/login_attempts.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        String successful = "";
        if(hasFound)
            successful = "Successful Login taken place on   ";
        else
            successful = "Unsuccessful Login taken place on ";

        pw.print(successful + LocalDate.now() + " " + LocalTime.now());
        pw.println();

        fw.close();

    }
}
