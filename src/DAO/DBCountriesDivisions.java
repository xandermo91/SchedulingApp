package DAO;
/**
 *
 * Class DBCountriesDivisions.java
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Country;
import sample.Customer;
import sample.Division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Frank Xander Morales
 */
public class DBCountriesDivisions {
    private static Connection conn = DBConnection.getConnection();

    /**
     * This method adds a new appointment
     * @return ObservableList
     */
    public static ObservableList<Country> getCountryList(){
        ObservableList<Country> listOfCountries = FXCollections.observableArrayList();

        String selectStatement = "Select Country_ID, Country FROM countries;";


        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int countryId;
            String countryName;

            // Forward scroll ResultSet
            while (rs.next()) {
                countryId = rs.getInt("Country_ID");
                countryName = rs.getString("Country");

                listOfCountries.add(new Country(countryId, countryName));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        return listOfCountries;
    }

    /**
     * This method adds a new appointment
     * @param countryID the product to add
     * @return ObservableList
     */
    public static ObservableList<Division> getDivisionList(int countryID){
        ObservableList<Division> listOfDivisions = FXCollections.observableArrayList();

        String selectStatement = "Select Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?;";


        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPrepareStatement();
            ps.setInt(1, countryID);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            int divisionId;
            String divisionName;

            // Forward scroll ResultSet
            while (rs.next()) {
                divisionId = rs.getInt("Division_ID");
                divisionName = rs.getString("Division");

                listOfDivisions.add(new Division(divisionId, divisionName));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return listOfDivisions;
    }

    /**
     * This method adds a new appointment
     * @param countryName the product to add
     * @return Country object
     */
    public static Country getCountryObject(String countryName){
        ObservableList<Country> listOfCountries = getCountryList();
        for(Country c : listOfCountries){
            if(c.getCountryName().matches(countryName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * This method adds a new appointment
     * @param divisionName the product to add
     * @param countryId
     * @return Division object
     */
    public static Division getDivisionObject(String divisionName, int countryId){
        ObservableList<Division> listOfDivision = getDivisionList(countryId);
        for(Division d : listOfDivision){
            if(d.getDivisionName().matches(divisionName)) {
                return d;
            }
        }
        return null;
    }
}
