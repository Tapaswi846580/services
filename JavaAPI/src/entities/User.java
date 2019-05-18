package entities;

public class User {
	int id = 0;
	String emailId = "";
	String password = ""; 
	String type = "";
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String emailId, String password, String type) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.emailId = emailId;
		this.password = password;
		this.type = type;
	}
	
	
	public User(int id, String emailId, String password) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.emailId = emailId;
		this.password = password;
	}
	
	public User(String emailId, String password) {
		// TODO Auto-generated constructor stub
		this.emailId = emailId;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}public void setId(int id) {
		this.id = id;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
