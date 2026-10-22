package helpDeskTicketManagement;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void createUser(Scanner scan, Admin admin , ArrayList<User> users){
		
			boolean usernameExists =false;
				System.out.println("Enter the new username");
				String newUserName=scan.next();
				for(User user:users) {
					if(newUserName.equalsIgnoreCase(user.getUsername())) {
						usernameExists=true;
					}
				}
					while(usernameExists) {
						System.out.println("The Username already exists, please try new Username");
						newUserName=scan.next();
						usernameExists = false;
						for(User username:users) {
							if(newUserName.equalsIgnoreCase(username.getUsername())) {
								usernameExists=true;
							}
							
						}
					}
				System.out.println("Enter the temporary Password");
				String tempPass = scan.next();
				if(tempPass.length()< 8 || tempPass.length()>12 || !tempPass.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
					while(tempPass.length()< 8 || tempPass.length()>12 || !tempPass.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
						System.out.println("The password must be 8-12 characters and contain letters and numbers only.");
						tempPass = scan.next();
					}
				}
				System.out.println("Enter the role (ADMIN/SUPPORT_ENGINEER)");
				String newRole = scan.next();
				while(!newRole.equalsIgnoreCase("ADMIN") && (!newRole.equalsIgnoreCase("SUPPORT_ENGINEER"))) {
					System.out.println("The role should be only (ADMIN/SUPPORT_ENGINEER)");
					newRole=scan.next();
				}
				System.out.println("Enter the Employee ID");
				String newEmpId = scan.next();
				System.out.println("Selected role: " + newRole);
				User newUser =admin.createUser(newUserName, tempPass, newRole, newEmpId);
				users.add(newUser);
	}
	
	public static void manageUserAccounts(Scanner scan, ArrayList<User> users){
		
				System.out.println("Enter the username to manage");
				String targetUsername=scan.next();
				
				boolean userNameFound=false;
				for(User targetuser:users) {
					int accStatus=0;
					
					if(targetUsername.equalsIgnoreCase(targetuser.getUsername())) {
						System.out.println("Username found");
						userNameFound = true;
						
						if(targetuser.isAccountStatus()) {
							System.out.println("Account is currently active");
							
							System.out.println("1. Deactivate Account");
							accStatus=scan.nextInt();
							if(accStatus==1) {
							targetuser.setAccountStatus(false);
							System.out.println("Account deactivated successfully");
							}else {
								System.out.println("Invalid Choice, please press 1 to deactivate");
								
							}
							
						}
						else {
							System.out.println("Account is currently disabled");
							System.out.println("1.Activate Account");
							accStatus=scan.nextInt();
							if(accStatus==1) {
							targetuser.setAccountStatus(true);
							System.out.println("Account activated successfully");
							}else {
								System.out.println("Invalid Choice, please press 1 to activate");
							}
						}
					}
					
					
				}if(!userNameFound) {
					System.out.println("Username not found");
				}
				
			}
	
	public static void login(Scanner scan, Admin admin,ArrayList<User> users){
		System.out.println("Enter the UserName");
		String userName= scan.next();
		System.out.println("Enter the Password");
		String pass = scan.next();
		
		boolean isAdmin = false;
		boolean loggedOut = false;
		int adminChoice = 0;
		boolean userFound = false;
		boolean createNewUser = false;
		
		for(User user:users) {
			if(user.getUsername().equalsIgnoreCase(userName)) {
				userFound=true;
				
				if(user.matchesPassword(pass)) {
					
				 	if(user.isAccountStatus()) {
				 		
				 		if(user.isPasswordChangeStatus()) {
				 			if(user.getRole().equalsIgnoreCase("ADMIN")) {
				 				isAdmin=true;
				 				System.out.println("Admin Dashboard");
				 				System.out.println("1.create a user");
				 				System.out.println("2. Activate/Deactivate User");
				 				System.out.println(("3.Logout"));
				 				adminChoice = scan.nextInt();
				 				
				 				if(isAdmin && adminChoice == 2) {
				 					manageUserAccounts(scan, users);
				 					
				 				}
				 				if(isAdmin && adminChoice == 1) {
									createNewUser = true;
				 				}
				 				
				 				if(isAdmin && adminChoice==3) {
				 					loggedOut=true;
				 					break;
				 				}
				 			}else {
				 				System.out.println("Ticketing dashboard");
				 			}
				 			
				 		}
				 		else {
				 			System.out.println("Enter the New password");
				 			String newPass=scan.next();
				 			if(newPass.length()< 8 || newPass.length()>12 || !newPass.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
		 						while(newPass.length()< 8 || newPass.length()>12 || !newPass.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
		 							System.out.println("The password must be 8-12 characters and contain letters and numbers only.");
		 							newPass = scan.next();
		 						}
		 					}
				 			
				 			if(user.matchesPassword(newPass)) {
				 				boolean passChange=false;
				 				while(!passChange) {
				 					System.out.println("Enter a different password:");
				 					String newpassword=scan.next();
				 					if(newpassword.length()< 8 || newpassword.length()>12 || !newpassword.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
				 						while(newpassword.length()< 8 || newpassword.length()>12 || !newpassword.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$")) {
				 							System.out.println("The password must be 8-12 characters and contain letters and numbers only.");
				 							newpassword = scan.next();
				 						}
				 					}
				 					if(user.matchesPassword(newpassword)) {
				 						System.out.println("New password cannot be the same as old password");
				 					}
				 					else {
				 						user.setPassword(newpassword);
				 						user.setPasswordChangeStatus(true);
				 						passChange=true;
				 					}
				 				}
				 				
				 			}else {
				 				user.setPassword(newPass);
				 				user.setPasswordChangeStatus(true);
				 				System.out.println("Password updated and you can continue");
				 			}
				 		}
					}
				 	else {
				 		System.out.println("User account is disbaled, please contact Administrator");
				 	}
			
				}else {
					System.out.println("Invalid password");
				}
				
				
		
			}
		}
		
		if(createNewUser) {
			createUser(scan, admin, users);
		}
		
		if(!userFound) {
			System.out.println("Invalid UserName");
		}
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Admin admin = new Admin();
		ArrayList<User> users = new ArrayList<User>();
			User employee=admin.createUser("Hari", "xxxx", "ADMIN", "5689");
			users.add(employee);
		boolean exit = false;
		while(!exit) {
			System.out.println("Please press 1 to login or 2 to exit");
			int choice = scan.nextInt();
			if(choice == 1) {
				login(scan, admin, users);
			}else if(choice == 2) {
				exit = true;
			}else {
				System.out.println("Invalid Choice, Please press 1 or 2");
			}		
		}
	}
		
}
		
