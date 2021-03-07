package sample;
/**
 *
 * Class User.java
 */

/**
 *
 * @author Frank Xander Morales
 */
public class User {
    private String name;
    private String password;
    int id;

    /**
     * Constructor.
     * @param name
     * @param password
     * @param id
     */
    public User(String name, String password, int id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }

    /**
     * Getter for the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the password.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the id.
     * @return String
     */
    public int getId() {
        return id;
    }

    /**
     * Display user ID and name
     * @return String
     */
    @Override
    public String toString(){
        return id + " " + name;
    }
}
