
package ch.heigvd.amt.amtbootcampapp.services.dao;

import ch.heigvd.amt.amtbootcampapp.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 * Tier service which implements the UserManagerDAO. Used to manage user data on
 * database.
 *
 * @author F. Franchini, S. Henneberger
 */
@Stateless
public class UsersManagerDAO implements UsersManagerDAOLocal {

   /**
    * Reference to the datasource
    */
   @Resource(lookup = "java:/jdbc/amtbootcamp")
   private DataSource dataSource;

   @Override
   public List<User> findAllUsers() {

      List<User> users = new LinkedList<>();

      try {
         Connection connection = dataSource.getConnection();
         PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user");
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            long id = rs.getLong("user_id");
            String username = rs.getString("user_name");
            String password = rs.getString("user_password");
            users.add(new User(id, username, password));
         }
         connection.close();

      } catch (SQLException ex) {
         Logger.getLogger(UsersManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return users;
   }

   @Override
   public long saveUser(User user) {

      long id = 0L;

      try {
         Connection connection = dataSource.getConnection();
         PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user (user_name, user_password) VALUES (?,?)", new String[]{"user_id"});
         pstmt.setString(1, user.getUsername());
         pstmt.setString(2, user.getPassword());
         pstmt.executeUpdate();
         ResultSet rs = pstmt.getGeneratedKeys();
         if (rs.next()) {
            id = rs.getLong(1);
         }
         connection.close();
      } catch (SQLException ex) {
         Logger.getLogger(UsersManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return id;
   }

   @Override
   public User loadUser(long id) {

      String username = "";
      String password = "";
      User loadedUser = null;

      try {
         Connection connection = dataSource.getConnection();
         PreparedStatement pstmt = connection.prepareStatement("SELECT user_name, user_password FROM user WHERE user_id = ?");
         pstmt.setLong(1, id);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            username = rs.getString("user_name");
            password = rs.getString("user_password");
            loadedUser = new User(id, username, password);
         }

         connection.close();

      } catch (SQLException ex) {
         Logger.getLogger(UsersManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return loadedUser;
   }

   @Override
   public boolean deleteUser(long id) {

      boolean isSuppressionOK = false;

      try {
         Connection connection = dataSource.getConnection();
         PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE user_id = ?");
         pstmt.setLong(1, id);
         pstmt.executeUpdate();

         connection.close();
         isSuppressionOK = true;

      } catch (SQLException ex) {
         Logger.getLogger(UsersManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return isSuppressionOK;
   }

   @Override
   public boolean patchUser(long id, User user) {

      String newUsername = user.getUsername();
      boolean isPatchOk = false;

      try {
         Connection connection = dataSource.getConnection();
         PreparedStatement pstmt = connection.prepareStatement("UPDATE user SET user_name = ? WHERE user_id = ?");
         pstmt.setString(1, newUsername);
         pstmt.setLong(2, id);
         pstmt.executeUpdate();

         connection.close();
         isPatchOk = true;

      } catch (SQLException ex) {
         Logger.getLogger(UsersManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
      return isPatchOk;
   }
}
