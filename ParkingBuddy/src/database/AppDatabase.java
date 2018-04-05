package database;

import java.sql.*;
import java.util.ArrayList;

public class AppDatabase {
	
	// The only variables needed are the connection and statement
	// Everything else will be passed to methods
	private Connection conn;
	private Statement st;
	
	public AppDatabase(String hostname) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(hostname); //("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
			st = conn.createStatement();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
	}
	
	private boolean insertUser(User u) {
		if(u == null) return false;
		try {
			st.executeUpdate("INSERT INTO Users (username, fname, lname, email, passhash) VALUES ("
					+ "'" + u.getUsername() + "',"
					+ "'" + u.getFname() + "',"
					+ "'" + u.getLname() + "',"
					+ "'" + u.getEmail() + "',"
					+ "'" + u.getPasshash() + "')");
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		}
	}
	
	public boolean exists(String username) {
		// Check if the username exists
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT COUNT(1) FROM Users WHERE username = '" + username + "'");
			if(rs == null || !rs.next()) { // empty check
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		}
	}
	
	private User createUser(ResultSet rs) {
		int uid;
		String username, fname, lname, email;
		byte[] passhash;
		
		try {
			// Get data to instantiate class
			uid = rs.getInt(1);
			username = rs.getString(2);
			fname = rs.getString(3);
			lname = rs.getString(4);
			email = rs.getString(5);
			passhash = rs.getBytes(6);
			return new User(uid, username, fname, lname, email, passhash);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	public User getUserById(int id) {
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT DISTINCT FROM Users WHERE id =  '" + id + "'");
			if(rs == null || !rs.next()) { // empty check
				return null;
			}
			rs.beforeFirst();
			// Get data to instantiate class
			return createUser(rs);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}	
	
	public boolean loginUser(String username, byte[] passhash) {
		// Check password
		try {
			ResultSet rs = st.executeQuery("SELECT COUNT(1) FROM Users WHERE username = '" + username 
			+ "' AND passhash = '" + passhash + "'");
			if(rs == null || !rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			return false;
		}
	}
	
	
	public boolean registerUser(User u) {
		if(u == null) return false;
		// If user exists, return false
		if(exists(u.getUsername())) {
			return false;
		}
		// If not addUser to database
		return insertUser(u);
	}
	
	// Query the database for the result set
	// iterate through the set and create an array of users
	// if the size of the list is 0, return null
	public ArrayList<User> getUserFriends(String username){
		
		// Query the database for the result set
		ResultSet rs;
		ArrayList<User> users = new ArrayList<User>();
		try {
			// Execute the Query
			rs = st.executeQuery("SELECT FROM Users WHERE username = '" + username + "'");
			if(rs == null || !rs.next()) {
				return null;
			}
			rs.beforeFirst();
			
			// Parse and iterate over rs
			while(rs.next()) {
				User u = createUser(rs);
				// Add User to list
				if(u != null) {
					users.add(u);
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		// if the size of the list is 0, return null
		if(users.size() == 0) {
			return null;
		}
		return users;
	}
	
	public ArrayList<User> searchUsersByName(String name){
		return null;
	}
	
	public ArrayList<User> searchUsersByUsername(String username){
		return null;
	}
	
	public ArrayList<User> searchUsersByEmail(String email){
		return null;
	}
	
	public ArrayList<ParkingSpot> getUserSpots(String username){
		return null;
	}
	
	public ParkingSpot getSpotById(int id) {
		return null;
	}
	
	public ArrayList<Comment> getSpotComments(int id){
		return null;
	}
}
