
package ch.heigvd.amt.amtbootcampapp.rest;

import ch.heigvd.amt.amtbootcampapp.model.User;
import ch.heigvd.amt.amtbootcampapp.rest.dto.UserDTO;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import ch.heigvd.amt.amtbootcampapp.services.dao.UsersManagerDAOLocal;
import ch.heigvd.amt.amtbootcampapp.services.UsersManagerServiceLocal;

/**
 * Entry point for all HTTP requests which treat user resources.
 *
 * @author F. Franchini, S. Henneberger
 */
@Stateless
@Path("/users")
public class UserResource {

   /**
    * Dependence injection for <code>UsersManagerServiceLocal</code>.
    */
   @EJB
   private UsersManagerServiceLocal usersManagerService;

   /**
    * Dependence injection for <code>UsersManagerDAOLocal</code>.
    */
   @EJB
   private UsersManagerDAOLocal usersManagerDAO;

   @Context
   UriInfo uriInfo;

   /**
    * GET HTTP request to get all <code>User</code> saved in database.
    *
    * @param byName <code>User</code> ordered by name.
    * @return JSON representation of users saved in database.
    */
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<UserDTO> getUsers(@QueryParam(value = "byName") String byName) {
      List<User> user = usersManagerDAO.findAllUsers();
      return user.stream()
              .filter(p -> byName == null || p.getUsername().equalsIgnoreCase(byName))
              .map(p -> toDTO(p))
              .collect(toList());
   }

   /**
    * POST HTTP request to create a new <code>User</code> and save it in
    * database.
    *
    * @param userDTO the new abstract <code>User</code> to create.
    * @return the request status : <code>BAD_REQUEST</code>, if username isn't
    *         syntactically valid or if it is more long than 45 characters.
    *         <code>INTERNAL_SERVER_ERROR</code>, if processing SQL request
    *         doesn't work. <code>CONFLICT</code>, if username already exists in
    *         database. <code>CREATED</code>, if new <code>User</code> could be
    *         created.
    */
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response createUser(UserDTO userDTO) {

      final String DEFAULT_PASSWORD = "password";

      String username = userDTO.getUsername();

      // Check if username isn't syntactically valid
      if (!usersManagerService.isSyntacticallyValid(username)) {
         return Response.status(Response.Status.BAD_REQUEST)
                 .entity("Error, Username can't be empty !")
                 .build();
      }

      // Check username length
      if (username.length() > 45) {
         return Response.status(Response.Status.BAD_REQUEST)
                 .entity("Error, Username is too long ! (max length = 45)")
                 .build();
      }

      // User creation because we want to set a default password
      User user = fromDTO(userDTO);
      user.setPassword(DEFAULT_PASSWORD);

      // Check if username is available via business service
      if (usersManagerService.usernameIsAvailable(username)) {

         // Insertion in DB via DAO service
         long userId = usersManagerDAO.saveUser(user);

         if (userId != 0) {
            // Creation of the URI that we'll return to the user
            URI href = uriInfo
                    .getBaseUriBuilder()
                    .path(UserResource.class)
                    .path(UserResource.class, "getUser")
                    .build(userId);

            return Response
                    .created(href)
                    .build();
         }
         else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during the processing of the SQL request !")
                    .build();
         }
      }
      else {
         // username isn't available
         // We return 409 Conflict http response
         return Response
                 .status(Response.Status.CONFLICT)
                 .entity("Error ! Username already exists !")
                 .build();
      }
   }

   /**
    * GET HTTP request to get a specific <code>User</code> saved in database
    * with the id.
    *
    * @param id the identifier of the <code>User</code> to get.
    * @return JSON representation of the <code>User</code> with
    *         <code>FOUND</code> staus, if no error occurs.
    *         <code>INTERNAL_SERVER_ERROR</code>, if error occurs during SQL
    *         processing. <code>NOT_FOUND</code>, if id doesn't exist.
    */
   @Path("{id}")
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getUser(@PathParam(value = "id") long id) {

      // Check if user_id exists via business service
      if (usersManagerService.userIdExists(id)) {

         // User loading from DB via DAO service
         User user = usersManagerDAO.loadUser(id);

         if (user != null) {
            return Response.status(Response.Status.FOUND)
                    .entity(toDTO(user))
                    .build();
         }
         else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during the processing of the SQL request !")
                    .build();
         }
      }
      else {
         // user_id doesn't exist
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("Error, there isn't an User with this user_id !")
                 .build();
      }
   }

   /**
    * DELETE HTTP request to remove a specific <code>User</code> on database
    * with the id.
    *
    * @param id the identifier of the <code>User</code> to remove.
    * @return <code>OK</code> staus, if no error occurs.
    *         <code>INTERNAL_SERVER_ERROR</code>, if error occurs during SQL
    *         processing. <code>NOT_FOUND</code>, if id doesn't exist.
    */
   @Path("{id}")
   @DELETE
   @Consumes(MediaType.APPLICATION_JSON)
   public Response deleteUser(@PathParam(value = "id") long id) {

      // Check if user_id exists via business service
      if (usersManagerService.userIdExists(id)) {

         // Deletion in DB via DAO service
         if (usersManagerDAO.deleteUser(id)) {
            return Response.status(Response.Status.OK)
                    .build();
         }
         else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during the processing of the SQL request !")
                    .build();
         }
      }
      else {
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("Error, there isn't an User with this user_id !")
                 .build();
      }
   }

   /**
    * PATCH HTTP request to update the username of a <code>User</code> with the
    * id.
    *
    * @param id      the identifier of the <code>User</code> to update.
    * @param userDTO partial user to update.
    * @return the request status : <code>BAD_REQUEST</code>, if username isn't
    *         syntactically valid or if it is more long than 45 characters.
    *         <code>INTERNAL_SERVER_ERROR</code>, if error occurs during
    *         processing SQL request. <code>CONFLICT</code>, if username already
    *         exists in database. <code>NOT_FOUND</code>, if id doesn't exist.
    *         <code>OK</code>, if new <code>User</code> could be updated.
    */
   @Path("{id}")
   @PATCH
   @Consumes(MediaType.APPLICATION_JSON)
   public Response patchUser(@PathParam(value = "id") long id, UserDTO userDTO) {

      String username = userDTO.getUsername();

      // Check if username isn't syntactically valid
      if (!usersManagerService.isSyntacticallyValid(username)) {
         return Response.status(Response.Status.BAD_REQUEST)
                 .entity("Error, Username can't be empty !")
                 .build();
      }

      // Check username length
      if (username.length() > 45) {
         return Response.status(Response.Status.BAD_REQUEST)
                 .entity("Error, Username is too long ! (max length = 45)")
                 .build();
      }

      // Check if user_id exists via business service
      if (usersManagerService.userIdExists(id)) {

         // Check if user_name is available via business service
         if (usersManagerService.usernameIsAvailable(username)) {

            // Patching the user via DAO service
            if (usersManagerDAO.patchUser(id, fromDTO(userDTO))) {
               return Response.status(Response.Status.OK)
                       .build();
            }
            else {
               // Error during the procesing of the SQL request
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error during the processing of the SQL request !")
                       .build();
            }
         }
         else {
            // Username already exists
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error ! New username already exists !")
                    .build();
         }
      }
      else {
         // User_id doesn't exist
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("Error, there isn't an User with this user_id !")
                 .build();
      }
   }

   /**
    * Create a new <code>User</code> instance from a <code>UserDTO</code>.
    *
    * @param dto UserDTO used to create the User.
    * @return the User created from UserDTO.
    */
   public User fromDTO(UserDTO dto) {
      return new User(dto.getUsername());
   }

   /**
    * Create a new <code>UserDTO</code> from a <code>User</code>.
    *
    * @param user the User used to create the UserDTO.
    * @return the UserDTO created from User.
    */
   public UserDTO toDTO(User user) {
      return new UserDTO(user.getId(), user.getUsername());
   }

}
