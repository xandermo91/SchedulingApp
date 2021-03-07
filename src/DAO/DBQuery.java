package DAO;
/**
 *
 * Class DBQuery.java
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Frank Xander Morales
 */
public class DBQuery {

    private static PreparedStatement statement;

    /**
     *  This method creates a statement object.
     *  @param conn Connection object.
     *  @param sqlStatement
     *  @exception IOException Failed to open scene.
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     *  This method returns a statement object.
     *  @return PreparedStatement
     *  @exception IOException Failed to open scene.
     */
    public static PreparedStatement getPrepareStatement() throws SQLException {
        return statement;
    }
}
