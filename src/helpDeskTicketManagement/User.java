package helpDeskTicketManagement;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private String username;
	public String getUsername() {
		return username;
	}
	private String password;
	
	public boolean matchesPassword(String inputPassword) {
	    if (inputPassword == null || password == null) {
	        return false;
	    }
	    return BCrypt.checkpw(inputPassword, password);
	}
	public void setPassword(String newPassword){
		this.password = newPassword;
	}
	
	public String getPassword() {
		return password;
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
}