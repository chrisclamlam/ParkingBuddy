package database;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import interpreter.CostEstimate;
import interpreter.Price;

public class AppDatabase {
	
	// The only variables needed are the connection and statement
	// Everything else will be passed to methods
	private ComboPooledDataSource cpds;
	
	public AppDatabase(String hostname) {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException pve) {
			System.out.println(pve.getMessage());
		}
		cpds.setJdbcUrl(hostname);
		cpds.setUser("root");
		cpds.setPassword("OwrzTest");
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(180);
	}
	
	private Connection getConnection() {
		try {
			return (Connection) cpds.getConnection();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private Statement getStatement(Connection conn) {
		try {
			return (Statement) conn.createStatement();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private void close(Connection conn, Statement st, ResultSet rs) {
		// Result sets, statements do not need to be closed, as they are ended by the connectionProxy closing
		try {
			if(conn != null) {
				conn.close();
			}
		}
		catch (SQLException se) {
			System.out.println("Error closing connection: " + se.getMessage());
		}
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
			System.out.println("Inserting user: " + u.getUsername() + ", " + u.getPasshash());
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
		if(exists(ps.getRemoteId())) {
			return false;
		}
		try {
			st.executeUpdate("INSERT INTO ParkingSpots (remoteid, label, longitude, latitude, spotType) VALUES("
					+ "'" + ps.getRemoteId() + "',"
					+ "'" + ps.getLabel() + "',"
					+ "'" + ps.getLongitude() + "'," 
					+ "'" + ps.getLatitude() + "'," 
					+ "'" + ps.getSpotType() + "')");
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return false;
		} finally {
			close(conn, st, null);
		}
	}
	
	private User createUser(ResultSet rs) {
		int uid, passhash;
		String username, fname, lname, email;
		
		try {
			// Get data to instantiate class
			uid = rs.getInt(1);
			username = rs.getString(2);
			fname = rs.getString(3);
			lname = rs.getString(4);
			email = rs.getString(5);
			passhash = rs.getInt(6);
			System.out.println("Creating user (id, username); " + uid + ", " + username);
			return new User(uid, username, fname, lname, email, passhash);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			return null;
		}
	}
	
	private String createUsername(ResultSet rs) {
		String username;
		try {
			username = rs.getString(1);
			return username;
		} catch (SQLException sqle) {
			System.out.println("Error while getting usernames: " + sqle.getMessage());
			return null;
		}
	}
	
	private ParkingSpot createParkingSpot(ResultSet rs) {
		// Variables needed for ParkingSpot constructor
		int id, spotType;
		String label, remoteid;
		double latitude, longitude;
		// Parse the ResultSet
		try {
			id = rs.getInt(1);
			remoteid = rs.getString(2);
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
	
	private ArrayList<String> getUsernamesFromQuery(String query) {
		Connection conn = getConnection();
		if(conn == null) return null;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return null;
		}
		ResultSet rs = null;
		ArrayList<String> usernames = new ArrayList<String>();
		try {
			// Execute the Query
			rs = st.executeQuery(query);
			if(rs == null || !rs.next()) {
				return null;
			}	
			// Parse and iterate over rs
			while(rs.next()) {
				String username = createUsername(rs);
				// Add User to list
				if(username != null) {
					usernames.add(username);
				}
				return usernames;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			close(conn, st, rs);
		}
		// if the size of the list is 0, return null
		if(usernames.size() == 0) {
			System.out.println("No friends found");
		}
		return null;
		
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
	
	private ArrayList<ParkingSpot> searchGoogleMaps(double latitude, double longitude){
		return MapsRequester.getNearbyParking(latitude, longitude, 400);
	}
	
	public boolean addSpot(ParkingSpot ps) {
		return insertSpot(ps);
	}

	public User getUserById(int id) {
		return getUserFromQuery("SELECT * FROM Users WHERE id = '" + id + "'");
	}
	
	public User getUserByUsername(String username) {
		return getUserFromQuery("SELECT * FROM users WHERE username = '" + username + "'");
	}
	
	public ArrayList<String> getUserFriends(int id){	
		// Query the database for the result set
		return getUsernamesFromQuery("SELECT DISTINCT users.username FROM users INNER JOIN friendslist ON users.id = friendslist.firstid OR users.id = friendslist.secondid WHERE (friendslist.firstid = '" + id + "' OR friendslist.secondid = '" + id + "') AND users.id != '" + id + "'");
	}
	
	public ArrayList<ParkingSpot> getUserSpots(int id){
		System.out.println("Getting user " + id + " spots");
		ArrayList<ParkingSpot> spots = getParkingSpotsFromQuery("SELECT * FROM parkingspots INNER JOIN favoriteslist ON parkingspots.id = favoriteslist.spotid WHERE favoriteslist.userid = '" + id + "'");
		if(spots == null) {
			System.out.println("User has no spots");
		}
		return spots;
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
			close(conn, st, rs);
		}
	}
	
	public boolean loginUser(String username, int passhash) {
		// Check password
		Connection conn = getConnection();
		if(conn == null) return false;
		Statement st = getStatement(conn);
		if(st == null) {
			close(conn, null, null);
			return false;
		}
		System.out.println("About to login user");
		ResultSet rs = null;
		try {
			rs = st.executeQuery("SELECT passhash FROM Users WHERE username = '" + username + "'");
			if(rs == null || !rs.next()) {
				return false;
			}
			int ph = rs.getInt(1);
			if(ph == passhash) {
				return true;
			}
			return false;
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
	
	
	
	
	
	public ArrayList<User> searchUsersByName(String name){
		// Query the database for the result set
		return getUsersFromQuery("SELECT * FROM Users WHERE fname LIKE '" + name + "%' OR lname LIKE '" + name + "'");
	}
	
	public ArrayList<User> searchUsersByEmail(String email){
		return getUsersFromQuery("SELECT * FROM USERS WHERE email = '" + email + "'");
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
	public boolean existsParking(String remoteid)
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
			rs = st.executeQuery("SELECT COUNT(1) FROM ParkingSpots WHERE remoteid = '" + remoteid + "'");
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
	
	public ArrayList<ParkingSpot> searchLocations(String name, double latitude, double longitude){
		ArrayList<ParkingSpot> spots = MapsRequester.getLocations(name, latitude, longitude);
		if(spots == null) {
			System.out.println("No locations found from " + name);
			return spots;
		}
		return spots;
	}
	
	public ArrayList<ParkingSpot> searchSpotByLocation(double latitude, double longitude){
		// Calculate the great circle distance between the destination (latitude and longitude vars) ^
		// Check to see if the distance is within a quarter mile
		// return good results
		ArrayList<ParkingSpot> spots, mapsSpots;
		/*String query = "SELECT * , ( 3959 * 2 * \r\n" + 
				"			ASIN( \r\n" + 
				"				 SQRT(\r\n" + 
				"					 POW( SIN( RADIANS( '" + latitude + "' - latitude ) ) , 2 ) / 2 +\r\n" + 
				"					 COS( RADIANS('" + latitude + "') ) *\r\n" + 
				"					 COS( RADIANS( latitude ) ) *\r\n" + 
				"					 POW( SIN( RADIANS( '" + longitude + "' - longitude) ) , 2 ) / 2\r\n" + 
				"				 )\r\n" + 
				"			 )\r\n" + 
				"			)\r\n" + 
				"		 AS distance\r\n" + 
				"		 FROM ParkingSpots\r\n" + 
				"         WHERE ( 3959 * 2 * \r\n" + 
				"			ASIN( \r\n" + 
				"				 SQRT(\r\n" + 
				"					 POW( SIN( RADIANS( '" + latitude + "' - latitude ) ) , 2 ) / 2 +\r\n" + 
				"					 COS( RADIANS('" + latitude + "') ) *\r\n" + 
				"					 COS( RADIANS( latitude ) ) *\r\n" + 
				"					 POW( SIN( RADIANS( '" + longitude + "' - longitude) ) , 2 ) / 2\r\n" + 
				"				 )\r\n" + 
				"			 )\r\n" + 
				"			) < .25\r\n" + 
				"		 ORDER BY distance\r\n" + 
				"         LIMIT 0 , 20;";*/
		String query = "SELECT DISTINCT *, (3959 * 2 * ASIN(SQRT( POW( SIN( RADIANS('" + latitude + "' - latitude) ), 2) / 2 + COS( RADIANS('" + latitude + "') ) * COS( RADIANS( latitude ) ) * POW( SIN( RADIANS('" + longitude + "' - longitude) ) , 2 ) / 2) )) AS distance FROM ParkingSpots WHERE (3959 * 2 * ASIN(SQRT( POW( SIN( RADIANS('" + latitude + "' - latitude) ), 2) / 2 + COS( RADIANS('" + latitude + "') ) * COS( RADIANS( '" + latitude + "' ) ) * POW( SIN( RADIANS('" + longitude + "'- longitude) ) , 2 ) / 2) ))  < .25 ORDER BY distance LIMIT 0,20";
		System.out.println("Query: " + query);
		// Search out database
		spots = getParkingSpotsFromQuery(query);
		if(spots == null) {
			System.out.println("Database didn't return any spot results");
			spots = new ArrayList<ParkingSpot>();
		}
		System.out.println("Database returned " + spots.size() + " spots");
		// Search GoogleMaps for spots
		mapsSpots = searchGoogleMaps(latitude, longitude);
		if(mapsSpots == null || mapsSpots.size() == 0) {
			System.out.println("Google Maps returned no results");
			return spots;
		}
		// Add the MapsSpot to the database if it doesn't already exist
		
		for(ParkingSpot spot : mapsSpots) {
			insertSpot(spot);
			spots.add(spot);
		}
		System.out.println("Spots from location search: ");
		for(ParkingSpot spot : spots) {
			System.out.println(spot.getLabel());
		}
		// Run the query again to get the proper id's from the new spots
		spots = getParkingSpotsFromQuery(query);
		return spots;
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
	public String uberPrice(double start_lat, double start_long, double end_lat, double end_long)
	{
		UberLyftRequester request = new UberLyftRequester();
		ArrayList<Price> uber = request.getUberPrice(start_lat, start_long, end_lat, end_long);
		for(int i=0; i< uber.size() ;i++)
		{
			String estimate = null;
			if(uber.get(i).getLocalizedDisplayName().equals("uberX"))
			{
				//System.out.println("test "+ uber.get(i).getEstimate());
				double min = (double) uber.get(i).getHighEstimate();
				double max = (double) uber.get(i).getLowEstimate();
				if(min == max)
				{
					estimate = "$" +Double.toString(min);
					System.out.println(estimate);
				}else {
					estimate = "$" +Double.toString(min) + "-"+ Double.toString(max);
				}
				
				return estimate;
			}
		}
		return null;//can't find value
	}
	public String lyftPrice(double start_lat, double start_long, double end_lat, double end_long)
	{
		UberLyftRequester request = new UberLyftRequester();
		ArrayList<CostEstimate> lyft = request.getLyftPrice(start_lat, start_long, end_lat, end_long);
		String estimate =null;
		for(int i=0; i< lyft.size() ;i++)
		{
//			System.out.println(test.get(i).getLocalizedDisplayName());
			if(lyft.get(i).getRideType().equals("lyft"))
			{
				//System.out.println("test "+ lyft.get(i).getEstimate());
				double max = lyft.get(i).getEstimatedCostCentsMax()/100;
				double min = lyft.get(i).getEstimatedCostCentsMin()/100;
				if(max == min)
				{
					estimate = "$"+Double.toString(max);
				}
				else
				{
					estimate = "$" +Double.toString(min) + " "+ Double.toString(max);
				}
				
				return estimate;
			}
		}
		return null;//can't find value
	}
}
