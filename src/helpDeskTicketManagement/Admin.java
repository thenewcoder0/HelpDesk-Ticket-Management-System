package helpDeskTicketManagement;

import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;

public class Admin {

   private final DatabaseConnection db = new DatabaseConnection();

    public User createUser(String userName, String pass, String role, String empId) {

        String sql = "INSERT INTO users "
                + "(emp_id, username, password, role, account_status, password_change_status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(12));

        try (Connection connection = db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, empId);
            ps.setString(2, userName);
            ps.setString(3, hashedPassword);
            ps.setString(4, role);
            ps.setBoolean(5, true);
            ps.setBoolean(6, false);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(rows + " User inserted successfully!");

                User u1 = new User(userName, hashedPassword, role, true, false, empId);

                return u1;
            } else {
                throw new SQLException("User insertion failed.");
            }

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {

                System.out.println("Employee ID already exists. Please use a different ID.");

            } else {

                System.out.println("Unable to create user due to a database error.");
                e.printStackTrace();
            }
        }

        return null;
    }
}