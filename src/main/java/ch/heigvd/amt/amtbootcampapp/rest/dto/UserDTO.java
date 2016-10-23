
package ch.heigvd.amt.amtbootcampapp.rest.dto;

/**
 * Real user representation that is serialized and deserialized in JSON,
 * received by and sent to the client. It's the transferred resource between
 * client tier and the presentation tier.
 *
 * @author F. Franchini, S. Henneberger
 */
public class UserDTO {

   /**
    * The unique identifier
    */
   long id;

   /**
    * The username
    */
   private String username;

   public UserDTO() {
   }

   /**
    * Create a new <code>UserDTO</code> instance with the id and the username.
    *
    * @param id       the unique identfier
    * @param username the username
    */
   public UserDTO(long id, String username) {
      this.id = id;
      this.username = username;
   }

   public long getId() {
      return id;
   }

   public String getUsername() {
      return username;
   }

}
