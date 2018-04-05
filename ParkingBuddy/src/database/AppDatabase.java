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
			if(rs == null || rs.getFetchSize() == 0) { // empty check
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		}
	}
	
	public User getUserById(int id) {
		ResultSet rs;
		int uid;
		String username, fname, lname, email;
		byte[] passhash;
		try {
			rs = st.executeQuery("SELECT DISTINCT FROM Users WHERE id =  '" + id + "'");
			if(rs == null) { // empty check
				return null;
			}
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
		}
		return null;
	}	
	
	public boolean loginUser(String username, byte[] passhash) {
		// Check password
		try {
			ResultSet rs = st.executeQuery("SELECT COUNT(1) FROM Users WHERE username = '" + username 
			+ "' AND passhash = '" + passhash + "'");
			if(rs == null || rs.getFetchSize() == 0) {
				return false;
			}
		} catch (SQLException sqle) {
			return false;
		}
		return true;
	}
	
	
	public boolean registerUser(User u) {
		// If user exists, return false
		if(exists(u.getUsername())) {
			return false;
		}
		// If not addUser to database
		return insertUser(u);
	}
	
	public ArrayList<User> getUserFriends(String username){
		return null;
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
