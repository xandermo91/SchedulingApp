package sample;
/**
 *
 * Class Division.java
 */
/**
 *
 * @author Frank Xander Morales
 */
public class Division {

    private int divisionId;
    private String divisionName;

    /**
     * Constructor
     * @param id
     * @param name
     */
    public Division(int id, String name){
        divisionId = id;
        divisionName = name;
    }

    /**
     * Getter for divisionID
     * @return int
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for divisionID
     * @param id
     */
    public void setDivisionId(int id) {
        divisionId = id;
    }

    /**
     * Getter for divisionName
     * @return String
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Setter for divisionName
     * @param name
     */
    public void setDivisionName(String name) {
        divisionName = name;
    }

    /**
     * Display division name
     * @return String
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
