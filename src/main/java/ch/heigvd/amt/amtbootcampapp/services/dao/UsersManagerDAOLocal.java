
package ch.heigvd.amt.amtbootcampapp.services.dao;

import ch.heigvd.amt.amtbootcampapp.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 * Tier service which defines the UserManagerDAO. Used to manage user data on
 * database.
 *
 * @author F. Franchini, S. Henneberger
 */
@Local
public interface UsersManagerDAOLocal {

    /**
     * Return all Users who are saved in database.
     *
     * @return a List of User.
     */
    public List<User> findAllUsers();

    /**
     * Save the User provided in database.
     *
     * @param user the user to save.
     * @return the unique identifier of the user saved. Return 0, if an error
     *         occurs.
     */
    public long saveUser(User user);

    /**
     * Get from the database the User who have the id provided.
     * @param id the id of the user to get.
     * @return the user who has the provided id. If an error occurs, return null.
     */
    public User loadUser(long id);

    /**
     * Delete in database the user who has the provided id.
     * @param id the id of the user to remove
     * @return true if the User has been deleted, false if an error occurs.
     */
    public boolean deleteUser(long id);

    /**
     * Patch in database the user provided.
     * @param id the id of the user to patch.
     * @param user to patch
     * @return true if the user has been patched, false if an error occurs.
     */
    public boolean patchUser(long id, User user);

}
