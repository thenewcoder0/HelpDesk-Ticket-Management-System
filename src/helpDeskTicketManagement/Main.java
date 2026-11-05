package helpDeskTicketManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

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
				newRole=newRole.toUpperCase();
				
				String newEmpId;
				System.out.println("Selected role: " + newRole);
				boolean empIdExists = false;
				
				do {
					System.out.println("Enter the Employee ID");
					newEmpId = scan.next();
					
					empIdExists = false;
					
					for(User user:users) {
						if(newEmpId.equalsIgnoreCase(user.getEmpId())) {
							empIdExists = true;
							System.out.println("Employee ID already exists, Please enter a different ID.");
							break;
						}
					}
				}while(empIdExists);
				User newUser =admin.createUser(newUserName, tempPass, newRole, newEmpId);
				if(newUser != null) {
						users.add(newUser);
				}
	}
	
	public static void manageUserAccounts(Scanner scan, ArrayList<User> users, DatabaseConnection db) {

	    System.out.println("Enter the username to manage");
	    String targetUsername = scan.next();

	    boolean userNameFound = false;

	    for (User targetuser : users) {

	        int accStatus = 0;

	        if (targetUsername.equalsIgnoreCase(targetuser.getUsername())) {

	            System.out.println("Username found");
	            userNameFound = true;

	            if (targetuser.isAccountStatus()) {

	                System.out.println("Account is currently active");
	                System.out.println("1. Deactivate Account");

	                while (true) {

	                    if (scan.hasNextInt()) {

	                        accStatus = scan.nextInt();

	                        if (accStatus == 1) {
	                            break;
	                        } else {
	                            System.out.println("Invalid Choice, please press 1 to deactivate");
	                        }

	                    } else {

	                        System.out.println("Invalid input. Please enter 1.");
	                        scan.next();
	                    }
	                }

	                try {

	                    boolean updated = db.updateAccountStatus(targetuser.getEmpId(), false);

	                    if (updated) {

	                        targetuser.setAccountStatus(false);
	                        System.out.println("Account deactivated successfully");

	                    }

	                } catch (SQLException e) {

	                    System.out.println("Unable to deactivate account due to a database error.");
	                    e.printStackTrace();

	                }

	            } else {

	                System.out.println("Account is currently disabled");
	                System.out.println("1. Activate Account");

	                while (true) {

	                    if (scan.hasNextInt()) {

	                        accStatus = scan.nextInt();

	                        if (accStatus == 1) {
	                            break;
	                        } else {
	                            System.out.println("Invalid Choice, please press 1 to activate");
	                        }

	                    } else {

	                        System.out.println("Invalid input. Please enter 1.");
	                        scan.next();
	                    }
	                }

	                try {

	                    boolean updated = db.updateAccountStatus(targetuser.getEmpId(), true);

	                    if (updated) {

	                        targetuser.setAccountStatus(true);
	                        System.out.println("Account activated successfully");

	                    }

	                } catch (SQLException e) {

	                    System.out.println("Unable to activate account due to a database error.");
	                    e.printStackTrace();

	                }

	            }

	            break;
	        }
	    }

	    if (!userNameFound) {
	        System.out.println("Username not found");
	    }
	}
				
	
	public static void login(Scanner scan, Admin admin,ArrayList<User> users,ArrayList<Ticket> tickets, DatabaseConnection db) throws SQLException{
		System.out.println("Enter the UserName");
		String userName= scan.next();
		System.out.println("Enter the Password");
		String pass = scan.next();
		
		boolean isAdmin = false;
		boolean loggedOut = false;
		int adminChoice = 0;
		int userChoice =0;
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
				 				while(!loggedOut) {
				 				System.out.println("Admin Dashboard");
				 				System.out.println("1.create a user");
				 				System.out.println("2. Activate/Deactivate User");
				 				System.out.println(("3.Logout"));
				 				if(scan.hasNextInt()) {
				 					adminChoice = scan.nextInt();
				 				}else {
				 					System.out.println("Invalid input, Please enter a number: ");
				 					scan.next();
				 					continue;
				 				}
				 				
				 				
				 				if(isAdmin && adminChoice == 2) {
				 					manageUserAccounts(scan, users, db);
				 					
				 				}
				 				if(isAdmin && adminChoice == 1) {
									createUser(scan, admin, users);
				 				}
				 				
				 				if(isAdmin && adminChoice==3) {
				 					loggedOut=true;
				 					
				 				}
				 				if(isAdmin && adminChoice !=1 && adminChoice !=2 && adminChoice !=3) {
				 					System.out.println("Invalid Choice, Please select 1, 2 or 3");
				 				}
				 				}
				 				return;
				 			}else {
				 				loggedOut=false;
				 				
				 				while(!loggedOut) {
				 				System.out.println("Ticketing dashboard");
				 				System.out.println("1.View All Tickets");
				 				System.out.println("2.Search Ticket");
				 				System.out.println("3.Update Ticket");
				 				System.out.println("4.Create Ticket");
				 				System.out.println("5.Log out");
				 				if(scan.hasNextInt()) {
				 					userChoice=scan.nextInt();
				 				}else {
				 					System.out.println("Invalid input. Please Enter a number: ");
				 					scan.next();
				 					continue;
				 				}
				 				
				 				
				 				if(userChoice==1) {
				 					if(tickets.isEmpty()) {
				 						System.out.println("No Tickets Available");
				 					}else {
				 					for(Ticket ticket:tickets) {
				 						System.out.println(ticket);
				 					}
				 					}
				 				}
				 				else if(userChoice==2) {
				 					System.out.println("Enter the ticket no");
				 					int ticketNo;
				 					if(scan.hasNextInt()) {
				 						ticketNo=scan.nextInt();
				 					}else {
				 						System.out.println("Invalid ticket ID, Please enter a valid Ticket No: ");
				 						scan.next();
				 						continue;
				 					}
				 					
				 					Ticket ticket=findTicket(tickets,ticketNo);
				 					
				 					if(ticket==null) {
				 						System.out.println("Ticket not found");
				 					}else {
				 						System.out.println(ticket);
				 					}
				 				}
				 				else if(userChoice==3) {
				 					System.out.println("Enter the Ticket no");
				 					int ticketNo;
				 					if(scan.hasNextInt()) {
				 						ticketNo=scan.nextInt();
				 					}else {
				 						System.out.println("Invalid Ticket ID, Please enter a valid Ticket No: ");
				 						scan.next();
				 						continue;
				 					}
				 					scan.nextLine();
				 					
				 					Ticket ticket=findTicket(tickets,ticketNo);
				 					
				 					if(ticket==null) {
				 						System.out.println("Ticket not found");
				 					}else {
				 						System.out.println(ticket);
				 						
				 						System.out.println("Enter the New Description: ");
				 						String description = scan.nextLine();
				 						
				 						while(description.trim().isEmpty()){
				 							System.out.println("Description cannot be empty, Please Enter the description");
				 							description = scan.nextLine();
				 						}
				 						
				 						System.out.println("Enter the Status: ");
				 						String status = scan.nextLine();
				 						
				 						while(!(status.equalsIgnoreCase("OPEN")|| status.equalsIgnoreCase("IN_PROGRESS") || status.equalsIgnoreCase("RESOLVED"))){
				 							System.out.println("Invalid status. Please enter OPEN, IN_PROGRESS or RESOLVED: ");
				 							status=scan.nextLine();
				 						}
				 						
				 						System.out.println("Enter the Priority: ");
				 						String priority = scan.nextLine();
				 						
				 						while(!(priority.equalsIgnoreCase("LOW") || priority.equalsIgnoreCase("MEDIUM") || priority.equalsIgnoreCase("HIGH"))) {
				 							System.out.println("Invalid Priority. Please enter LOW, MEDIUM or HIGH: ");
				 							priority=scan.nextLine();
				 						}
				 							
				 							
				 							try {
				 								Ticket updatedTicket = new Ticket(ticket.getTicketId(),description,"OPEN",priority);
				 								updatedTicket.setStatus(status);
				 								
				 								if(ticket.getStatus().equalsIgnoreCase("RESOLVED")
				 								        && status.equalsIgnoreCase("RESOLVED")) {

				 								    updatedTicket.setResolvedAt(ticket.getResolvedAt());
				 								}
				 							
					 						db.updateTicket(updatedTicket);
					 						
					 						ticket.setDescription(description);
				 							ticket.setStatus(status);
				 							ticket.setPriority(priority);
				 							
				 							ticket.setResolvedAt(updatedTicket.getResolvedAt());
					 						
				 							System.out.println("Ticket Updated");
				 							System.out.println(ticket);
				 							
				 					}catch(SQLException e) {
				 						System.out.println("Unable to update ticket due to a database error.");
				 						e.printStackTrace();
				 					}
				 				}
				 				}
				 				else if(userChoice==4) {
				 					scan.nextLine();
				 					createTicket(scan, tickets, db);
				 				}
				 				else if(userChoice==5) {
				 					loggedOut=true;
				 					System.out.println("Logging Out");
				 				}
				 				else {
				 					System.out.println("Invalid Choice");
				 				}
				 				
				 				}
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
				 						
				 						try {
				 							
				 							String hashedPassword = BCrypt.hashpw(newpassword, BCrypt.gensalt(12));
				 							db.updatePassword(user.getEmpId(), hashedPassword);
				 							
				 							user.setPassword(hashedPassword);
					 						user.setPasswordChangeStatus(true);
					 						passChange=true;
					 						
					 						System.out.println("Password updated successfully");
				 							
				 						}catch(SQLException e) {
				 							System.out.println("Unable to update password due to a database error.");
				 							e.printStackTrace();
				 						}
				 					}
				 				}
				 				
				 			}else {
				 				
				 				try {
				 				String hashedPassword = BCrypt.hashpw(newPass, BCrypt.gensalt(12));
				 				
				 				db.updatePassword(user.getEmpId(), hashedPassword);
				 				
				 				user.setPassword(hashedPassword);
				 				user.setPasswordChangeStatus(true);
				 				
				 				System.out.println("Password updated and you can continue");
				 				}catch(SQLException e) {
				 					System.out.println("Unable to update password due to a database error.");

				 					e.printStackTrace();
				 				}
				 				
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
		
		if(loggedOut) {
			return;
		}
		
		if(!userFound) {
			System.out.println("Invalid UserName");
		}
	}
	public static Ticket findTicket(ArrayList<Ticket> tickets, int ticketNo){
		for(Ticket ticket:tickets) {
			if(ticket.getTicketId()==ticketNo) {
				return ticket;
			}
		}
		return null;
	}
	
	public static void createTicket(Scanner scan, ArrayList<Ticket>tickets,DatabaseConnection db) throws SQLException{
		System.out.println("Enter the Ticket Description");
		String description=scan.nextLine();
		while(description.trim().isEmpty()) {
			System.out.println("Description cannot be empty, Please enter description: ");
			description = scan.nextLine();
		}
		
		System.out.println("Enter the Priority (LOW/MEDIUM/HIGH");
		String priority=scan.nextLine();
		while(!priority.equalsIgnoreCase("LOW") && !priority.equalsIgnoreCase("MEDIUM") && !priority.equalsIgnoreCase("HIGH"))
		 {
			System.out.println("Priority can be either low, medium, high");
			priority=scan.nextLine();
		} 
		
		Ticket ticket = new Ticket(description,priority);
		try {
		db.insertTicket(ticket);
		tickets.add(ticket);
		
		
		System.out.println("Ticket created successfully");
		System.out.println(ticket);
		}catch(SQLException e) {
			System.out.println("Unable to create ticket due to a database error.");
		    e.printStackTrace();
		}
		
		}

	public static void main(String[] args) throws SQLException {
		Scanner scan = new Scanner(System.in);
		Admin admin = new Admin();
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		DatabaseConnection db = new DatabaseConnection();
		
		try {
			users=db.getAllUsers();
			tickets=db.getAllTickets();
		}catch(SQLException e) {
			e.printStackTrace();
			return;
		}
		boolean exit = false;

		while(!exit) {
			System.out.println("Total users loaded: " + users.size());
			System.out.println("Please press 1 to login or 2 to exit");
			
			if(scan.hasNextInt()) {
				int choice = scan.nextInt();
			if(choice == 1) {
				login(scan, admin, users,tickets,db);
			}else if(choice == 2) {
				exit = true;
			}else {
				System.out.println("Invalid Choice, Please press 1 or 2");
			}
			
			}else {
				System.out.println("Invalid Input. Please enter a number: ");
				scan.next();
			}
		}
		System.out.println("Thank you for using HelpDesk Ticket Management System!");
		
			}
}
		
