package database;

import java.sql.*;
import java.util.ArrayList;

public class AppDatabase {
	
	// The only variables needed are the connection and statement
	// Everything else will be passed to methods
	private String host;
	private String driver = "com.mysql.jdbc.Driver";
	
	public AppDatabase(String hostname) {
		host = hostname;
	}
	
	private Connection getConnection() {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(host); //("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
		return null;
	}
	
	private Statement getStatement(Connection conn) {
		try {
			return conn.createStatement();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			
		}
		catch (SQLException se) {}
		try {
			if(st != null) {
				st.close();
			}
			
		}
		catch (SQLException se) {}
		
		try {
			if(conn != null) {
				conn.close();
			}
			
		}
		catch (SQLException se) {}
	}
	
	private boolean insertUser(User u) {
		if(u == null) return false;
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		try {
			st = conn.createStatement();
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
		} finally {
			System.out.println("Closing insert connection");
			close(conn, st, null);
		}
	}
	
	private boolean insertSpot(ParkingSpot ps) {
		if(ps == null) return false;
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		try {
			st.executeUpdate("INSERT INTO ParkingSpots (remoteid, label, longitude, latitude, spotType) VALUES("
					+ "'" + ps.getRemoteId() + "',"
					+ "'" + ps.getLabel() + "',"
					+ "'" + ps.getLongitude() + "'," 
					+ "'" + ps.getLatitude() + "'," 
					+ "'" + 1 + "')");
			return true;
		} catch (SQLException sqle) {
			System.out.print(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, null);
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
	
	private ParkingSpot createParkingSpot(ResultSet rs) {
		// Variables needed for ParkingSpot constructor
		int id, remoteid, spotType;
		String label;
		double latitude, longitude;
		// Parse the ResultSet
		try {
			id = rs.getInt(1);
			remoteid = rs.getInt(2);
			label = rs.getString(3);
			longitude = rs.getDouble(4);
			latitude = rs.getDouble(5);
			spotType = rs.getInt(6);
			return new ParkingSpot(id, remoteid, label, spotType, longitude, latitude);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private Comment createComment(ResultSet rs) {
		int id, uid, sid, rating;
		String comment;
		try {
			id = rs.getInt(1);
			uid = rs.getInt(2);
			sid = rs.getInt(3);
			rating = rs.getInt(4);
			comment = rs.getString(5);
			return new Comment(id, uid, sid, rating, comment);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private int getUserId(String username) {

		Connection conn = getConnection();
		if(conn == null) return -1;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return -1;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT id FROM users WHERE username = '" + username + "'");
			if(rs == null || !rs.next()) { // empty check
				return -1;
			}
			return rs.getInt(1);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return -1;
		} finally {
			close(conn, st, rs);
		}
	}
	
	private User getUserFromQuery(String query) {
		
		Connection conn = getConnection();
		if(conn == null) return null;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return null;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery(query);
			if(rs == null || !rs.next()) { // empty check
				return null;
			}
			// Get data to instantiate class
			return createUser(rs);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		} finally {
			close(conn, st, rs);
		}
	}
	
	public boolean addSpot(ParkingSpot ps) {
		return insertSpot(ps);
	}

	public User getUserById(int id) {
		return getUserFromQuery("SELECT * FROM users WHERE id = '" + id + "'");
	}
	
	public User getUserByUsername(String username) {
		return getUserFromQuery("SELECT * FROM users WHERE username = '" + username + "'");
	}
	
	public void delete(String username) {
		Connection conn = getConnection();
		if(conn == null) return;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return;
		}
		try {
			st.executeUpdate("DELETE FROM Users WHERE username =  '" + username + "'");
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			close(conn, st, null);
		}
	}
	
	public boolean exists(String username) {
		// Check if the username exists
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT COUNT(1) FROM Users WHERE username = '" + username + "'");
			if(rs == null || !rs.next() || rs.getBoolean(1) == false) { // empty check
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			System.out.println("Closing exists connection");
			close(conn, st, rs);
		}
	}
	
	public boolean loginUser(String username, byte[] passhash) {
		// Check password
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT COUNT(1) FROM Users WHERE username = '" + username 
			+ "' AND passhash = '" + passhash + "'");
			if(rs == null || !rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			return false;
		} finally {
			close(conn, st, rs);
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
	private ArrayList<User> getUsersFromQuery(String query){
		Connection conn = getConnection();
		if(conn == null) return null;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return null;
		}
		ResultSet rs = null;;
		ArrayList<User> users = new ArrayList<User>();
		try {
			// Execute the Query
			rs = st.executeQuery(query);
			if(rs == null || !rs.next()) {
				return null;
			}	
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
		} finally {
			close(conn, st, rs);
		}
		// if the size of the list is 0, return null
		if(users.size() == 0) {
			return null;
		}
		return users;
	}
	
	public ArrayList<User> getUserFriends(String username){	
		// Query the database for the result set
		int id = getUserId(username);
		if(id == -1) return null;
		return getUsersFromQuery("SELECT * FROM FriendsList WHERE firstid = '" + id + "' OR secondid = '" + id + "'");
	}
	
	public ArrayList<User> searchUsersByName(String name){
		// Query the database for the result set
		return getUsersFromQuery("SELECT * FROM Users WHERE fname LIKE '" + name + "%' OR lname LIKE '" + name + "'");
	}
	
	public ArrayList<User> searchUsersByUsername(String username){
		return getUsersFromQuery("SELECT * FROM USERS WHERE username = '" + username + "'");
	}
	
	public ArrayList<User> searchUsersByEmail(String email){
		return getUsersFromQuery("SELECT * FROM USERS WHERE email = '" + email + "'");
	}
	
	private ArrayList<ParkingSpot> getParkingSpotsFromQuery(String query){
		Connection conn = getConnection();
		if(conn == null) return null;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return null;
		}
		ResultSet rs = null;
		ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
		try {
			// Execute the Query
			rs = st.executeQuery(query);
			if(rs == null || !rs.next()) {
				return null;
			}	
			// Parse and iterate over rs
			while(rs.next()) {
				ParkingSpot ps = createParkingSpot(rs);
				// Add User to list
				if(ps != null) {
					spots.add(ps);
				}
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			close(conn, st, rs);
		}
		// if the size of the list is 0, return null
		if(spots.size() == 0) {
			return null;
		}
		return spots;
	}
	
	public ArrayList<ParkingSpot> getUserSpots(String username){
		return getParkingSpotsFromQuery("SELECT * FROM FavoritesList WHERE firstid = '" + getUserId(username) + "'");
	}
	
	public ParkingSpot getSpotById(int id) {
		ArrayList<ParkingSpot> ps = getParkingSpotsFromQuery("SELECT * FROM ParkingSpots WHERE id = '" + id + "'");
		if(ps == null) {
			return null;
		}
		return ps.get(0);
	}
	
	public ArrayList<Comment> getCommentsFromQuery(String query){
		Connection conn = getConnection();
		if(conn == null) return null;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return null;
		}
		ResultSet rs = null;
		ArrayList<Comment> comments = new ArrayList<Comment>();
		try {
			// Execute the Query
			rs = st.executeQuery(query);
			if(rs == null || !rs.next()) {
				return null;
			}	
			// Parse and iterate over rs
			while(rs.next()) {
				Comment comment = createComment(rs);
				// Add User to list
				if(comment != null) {
					comments.add(comment);
				}
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			close(conn, st, rs);
		}
		// if the size of the list is 0, return null
		if(comments.size() == 0) {
			return null;
		}
		return comments;
	}
	
	public ArrayList<Comment> getSpotComments(int id) {
		return getCommentsFromQuery("SELECT * FROM Comments WHERE spotid = '" + id + "'");
	}

	public boolean addSpotComments(Comment c)
	{
		if(c.getSpotId() == -1 || c.getUserId() == -1) return false;
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		try {
			
			st.executeUpdate("INSERT INTO Comments(userid, spotid, rating, input) VALUES ("
					+ "'" + c.getUserId() + "',"
					+ "'" + c.getSpotId() + "',"
					+ "'" + c.getRating() + "',"
					+ "'" + c.getComment() + "')" );
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, null);
		}
	}
	
	public boolean addFriends(String username, String friendsusername)
	{
		if(username == null || friendsusername == null) return false;
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		try {
			int firstid =  getUserByUsername(username).getId();
			int secondid =  getUserByUsername(friendsusername).getId(); 
			st.executeUpdate("INSERT INTO FriendsList(firstid, secondid) VALUES ("
					+ "'" + firstid + "',"
					+ "'" + secondid + "')" );
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, null);
		}
	}
	public boolean existsParking(String parkingname)
	{
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT COUNT(1) FROM ParkingSpots WHERE label = '" + parkingname + "'");
			if(rs == null || !rs.next() || rs.getBoolean(1) == false) { // empty check
				return false;
			}
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, rs);
		}
	
	}
	public ParkingSpot getSpotByName(String parkingname) {
		ArrayList<ParkingSpot> ps = getParkingSpotsFromQuery("SELECT * FROM ParkingSpots WHERE label = '" + parkingname + "'");
		if(ps == null) {
			return null;
		}
		return ps.get(0);
	}
	
	public ArrayList<ParkingSpot> searchSpotByLocation(double latitude, double longitude){
		// Calculate the great circle distance between the destination (latitude and longitude vars) ^
		// Check to see if the distance is within a quarter mile
		// return good results
		String query = "SELECT * FROM ParkingSpots WHERE "
				+ "(3959 * ACOS( COS( RADIANS('" + latitude + "' ) ) * COS ( RADIANS (latitude) ) * COS ( RADIANS(latitude) - RADIANS('" + longitude + "') ) +  "
				+ "SIN('" + longitude + "') * SIN(longitude))) < .25";
		return getParkingSpotsFromQuery(query);
	}
	
	public boolean addFavoriteParking(String username, String parkingname)
	{
		if(username == null || parkingname == null) return false;
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		try {
			int firstid =  getUserByUsername(username).getId();
			ParkingSpot spot = getSpotByName(parkingname); 
			st.executeUpdate("INSERT INTO FavoritesList (userid, parkingspots) VALUES ("
					+ "'" + firstid + "',"
					+ "'" + spot + "')" );
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, null);
		}
	}
}
