
package ch.heigvd.amt.amtbootcampapp.services.business;

import javax.ejb.Local;

/**
 * Tier service which defines the business logic.
 *
 * @author F. Franchini, S. Henneberger
 */
@Local
public interface UsersManagerServiceLocal {

   /**
    * Check if the provided String is not null, is not empty and doesn't contain
    * only space.
    *
    * @param stringToTest the string to test.
    * @return True if string contains at least one character, false otherwise.
    */
   public boolean isSyntacticallyValid(String stringToTest);

   /**
    * Check in database if the identifier provided exists.
    *
    * @param id identifier to check.
    * @return true if identifier exists on database, false otherwise.
    */
   public boolean userIdExists(long id);

   /**
    * Check in database if the username provided isn't already used.
    *
    * @param username the username to check
    * @return true if username doesn't exist in database.
    */
   public boolean usernameIsAvailable(String username);

   /**
    * Check in database if there is a User who have the credentials provided.
    *
    * @param username first part of the credentials
    * @param password second part of the credentials
    * @return true, if there is a User who have the credentials provided.
    */
   public boolean areCredentialsCorrect(String username, String password);

}
