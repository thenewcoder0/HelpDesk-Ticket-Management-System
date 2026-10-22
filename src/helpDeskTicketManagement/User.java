package helpDeskTicketManagement;
//Represents a user in the HelpDesk system.
public class User {
	private String username;
	public String getUsername() {
		return username;
	}
	private String password;
	public boolean matchesPassword(String inputPassword){
		return password.equals(inputPassword);
	}
	public void setPassword(String newPassword){
		this.password = newPassword;
	}
	private String role;
	public String getRole(){
		return role;
		
	}
	private boolean accountStatus;
	public boolean isAccountStatus(){
		return accountStatus;
	}
	public void setAccountStatus(boolean accountStatus){
		this.accountStatus = accountStatus;
	}
	private boolean passwordChangeStatus;
	
	public boolean isPasswordChangeStatus(){
		return passwordChangeStatus;
	}
	
	public void setPasswordChangeStatus(boolean passwordChangeStatus){
		this.passwordChangeStatus = passwordChangeStatus;
	}
	private String empId;
	
	public String getEmpId(){
		return empId;
		
	}
	
	//Represent a User here.
	public User(String username, String pass, String role, boolean accStatus,boolean passChangeStatus, String empId){
		this.username = username;
		this.password=pass;
		this.role=role;
		this.accountStatus=accStatus;
		this.passwordChangeStatus=passChangeStatus;
		this.empId=empId;
		
	}


public static void main(String[]args) {
	User u1= new User("Hari123","cmx5J", "Engineer", true, false, "123");
	System.out.println("UserName: " + u1.getUsername() );
	System.out.println("Role: " + u1.getRole());
	System.out.print("Account Status: " + u1.isAccountStatus());
	
}
}