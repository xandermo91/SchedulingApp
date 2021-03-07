package sample;
/**
 *
 * Class Country.java
 */
/**
 *
 * @author Frank Xander Morales
 */
public class Country {

    private int countryId;
    private String countryName;

    /**
     * Constructor
     * @param id
     * @param name
     */
    public Country(int id, String name){
        countryId = id;
        countryName = name;
    }

    /**
     * Getter for countryID
     * @return int
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for countryID
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Getter for countryName
     * @return String
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for countryName
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Display country name
     * @return String
     */
    @Override
    public String toString(){
        return countryName;
    }
}
