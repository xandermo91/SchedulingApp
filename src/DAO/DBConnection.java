package DAO;
/**
 *
 * Class DBConnection.java
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Frank Xander Morales
 */
public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ089PF";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    // username
    private static final String username = "U089PF";
    private static final String password = "53689227796";

    /**
     * This method returns the connection.
     * @return conn Connection to be returned.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
        } catch(ClassNotFoundException e){
            System.out.println(e);
        } catch(SQLException e){
            System.out.println(e);
        }
        return conn;
    }

    /**
     * This method closes the connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public static Connection getConnection(){
        return conn;
    }
}
