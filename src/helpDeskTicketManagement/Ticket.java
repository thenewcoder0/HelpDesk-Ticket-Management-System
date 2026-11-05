package helpDeskTicketManagement;

import java.time.LocalDateTime;

public class Ticket {
	private int ticketId;
	private String description;
	private String status;
	private String priority;
	private LocalDateTime resolvedAt;
	
	
	public Ticket(int ticketId, String description,String status, String priority){
		this.ticketId=ticketId;
		setDescription(description);
		this.status=status;
		setPriority(priority);
		}
	public Ticket(String description, String priority) {
	    this.status = "OPEN";
	    this.priority = "MEDIUM";
	    setDescription(description);
	    setPriority(priority);
	}
	
	public int getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(int ticketId){
		this.ticketId=ticketId;
	}
	
	public String getDescription(){
		return description;
		
	}
	
	public void setDescription(String description){
		
		if(description.trim().isEmpty()==false) {
			this.description = description;
		}else {
			System.out.println("Descrpition cannot be empty");
		}
		
	}
	
	public String getStatus() {
		return status;
		
	}
	
	public void setStatus(String status){
		if(status.equalsIgnoreCase("OPEN")|| status.equalsIgnoreCase("IN_PROGRESS") || status.equalsIgnoreCase("RESOLVED")) {

			String newStatus = status.toUpperCase();
			
			if(!"RESOLVED".equalsIgnoreCase(this.status) && newStatus.equals("RESOLVED")) {
			    this.resolvedAt = LocalDateTime.now();
			}else if(!newStatus.equals("RESOLVED")) {
				this.resolvedAt=null;
			}
			this.status=newStatus;
		}else {
			System.out.println("Invalid Status");
		}
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority){
		if(priority.equalsIgnoreCase("LOW") || priority.equalsIgnoreCase("MEDIUM") || priority.equalsIgnoreCase("HIGH")) {
			this.priority=priority.toUpperCase();
			
		}else {
			System.out.println("Invalid Priority");
		}
		
	}
	
	public LocalDateTime getResolvedAt() {
		return resolvedAt;
	}
	
	public void setResolvedAt(LocalDateTime resolvedAt) {
		this.resolvedAt=resolvedAt;
	}
	
	@Override
	public String toString() {
		return "Ticket Id: " + ticketId + "\n Description: " + description + "\n Status: " + status + "\nPriority: " + priority + "\nResolved At: " + resolvedAt;
	}

}
