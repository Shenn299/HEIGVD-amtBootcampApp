
package ch.heigvd.amt.amtbootcampapp.model;

/**
 * An abstract user representation.
 *
 * @author F. Franchini, S. Henneberger
 */
public class User {

    /**
     * The unique identifier
     */
    private long id;

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * Create a new <code>User</code> instance with the id, the username and the
     * password.
     *
     * @param id       the unique identfier
     * @param username the username
     * @param password the password
     * @see #User(String, String)
     */
    public User(long id, String username, String password) {
        this(username, password);
        this.id = id;
    }

    /**
     * Create a new <code>User</code> instance with the username and the
     * password.
     *
     * @param username the username
     * @param password the password
     * @see #User(String)
     */
    public User(String username, String password) {
        this(username);
        this.password = password;
    }

    /**
     * Create a new <code>User</code> instance with the username.
     *
     * @param username the username
     */
    public User(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
