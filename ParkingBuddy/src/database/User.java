package database;

public class User {

	// Declare the member Variables
	private int id;
	private String username;
	private String fname;
	private String lname;
	private String email;
	private byte[] passhash;
	
	// Constructors set everything
	public User(int id, String username, String fname, String lname, String email, byte[] passhash) {
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passhash = passhash;
	}
	
	public User(String username, String fname, String lname, String email, byte[] passhash) {
		this.id = -1;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passhash = passhash;
	}
	
	public User(int id, String username, String fname, String lname, String email) {
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
	}
	
	// Only use getters as everything is set in constructors
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public String getEmail() {
		return email;
	}
	public byte[] getPasshash() {
		return passhash;
	}
}
