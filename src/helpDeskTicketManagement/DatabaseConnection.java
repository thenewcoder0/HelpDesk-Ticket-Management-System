package helpDeskTicketManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DatabaseConnection {
	
	public Connection getConnection() throws SQLException{
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD"); 
		return DriverManager.getConnection(url, username,password);
		
	}
	
	public ArrayList<User>getAllUsers() throws SQLException{
		
		ArrayList<User>users = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
			
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String role = rs.getString("role");
				boolean accountStatus = rs.getBoolean("account_status");
				boolean passwordChangeStatus = rs.getBoolean("Password_change_status");
				String empId = rs.getString("emp_id");
				
				User user = new User(username, password,role,accountStatus,passwordChangeStatus,empId);
				users.add(user);
			}
			
		}
		
		return users;
		
	}
	
	
	public ArrayList<Ticket> getAllTickets() throws SQLException{
		ArrayList<Ticket>tickets = new ArrayList<>();
		String sql = "SELECT * FROM tickets";
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
			
			while(rs.next()) {
				int ticketId = rs.getInt("ticket_id");
				String description = rs.getString("description");
				String status = rs.getString("status");
				String priority = rs.getString("priority");
				java.sql.Timestamp timestamp = rs.getTimestamp("resolved_at");
				LocalDateTime resolveAt = timestamp == null ? null : timestamp.toLocalDateTime();
				
				Ticket ticket = new Ticket(ticketId,description,status,priority);
				ticket.setResolvedAt(resolveAt);
				tickets.add(ticket);
			}
		}
		return tickets;
		
	}
	
	public void insertTicket(Ticket ticket)throws SQLException{
		String sql = "INSERT INTO tickets (description,status,priority)VALUES(?,?,?)";
		
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
			ps.setString(1, ticket.getDescription());
			ps.setString(2, ticket.getStatus());
			ps.setString(3, ticket.getPriority());
			
			int rows = ps.executeUpdate();
			try(ResultSet rs = ps.getGeneratedKeys()){
			if(rs.next()) {
				int generatedId = rs.getInt(1);
				ticket.setTicketId(generatedId);
				System.out.println("Generated Ticket ID: " + generatedId);
			}
			System.out.println(rows + " Ticket Inserted Successfully");
			
			}
		}
	}
	
	public void updateTicket(Ticket ticket)throws SQLException{
		String sql = "UPDATE tickets SET description = ?, status = ?, priority = ?, resolved_at = ? WHERE ticket_id = ?";
		try(Connection connection = getConnection(); 
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setString(1, ticket.getDescription());
			ps.setString(2, ticket.getStatus());
			ps.setString(3, ticket.getPriority());
			ps.setTimestamp(4, ticket.getResolvedAt() == null ? null : java.sql.Timestamp.valueOf(ticket.getResolvedAt()));
			ps.setInt(5,ticket.getTicketId());
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
			
			System.out.println(rows + " Ticket Updated Successfully");
			}else {
				throw new SQLException("Ticket update failed. Ticket ID: " + ticket.getTicketId());
			}
		}
		
	}
	
	
	public void updatePassword(String empId, String newPassword) throws SQLException{
		String sql = "UPDATE users SET password = ?, password_change_status = ? WHERE emp_id = ? ";
		
		try(Connection connection = getConnection(); 
				PreparedStatement ps = connection.prepareStatement(sql)){
			
			ps.setString(1, newPassword);
			ps.setBoolean(2, true);
			ps.setString(3, empId);
			
			int rows = ps.executeUpdate();
			
			if(rows == 0) {
				throw new SQLException("Password update failed. Employee ID: " +empId);
			}
		}
		
	}
	
	
	
	public boolean updateAccountStatus(String empId, boolean status) throws SQLException{
		String sql = "UPDATE users SET account_status = ? WHERE emp_id = ?";
		try(Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){
			ps.setBoolean(1, status);
			ps.setString(2, empId);
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				System.out.println("Account status updated successfully");
				return true;
			}else {
				System.out.println("User not found. Account status not updated");
				return false;
			}
			
		}
	}
	


	public static void main(String[] args) {
		

	}

}
