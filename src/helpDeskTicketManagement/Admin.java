package helpDeskTicketManagement;

public class Admin {
	
	public User createUser(String userName,String pass, String role, String empId) {
		
		User u1 = new User(userName, pass, role, true, false, empId);
		return u1;
	}


}	
