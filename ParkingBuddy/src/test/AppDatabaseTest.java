package test;

import org.junit.Test;
import database.AppDatabase;
import database.ParkingSpot;
import database.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

/*TODO
 * User getUserByUsername(String username) 
 * ArrayList<User> searchUsersByUsername(String username)
 * ArrayList<User> searchUsersByName(String name)
 * ArrayList<User> getUserFriends(String username)
 * ArrayList<User> searchUsersByEmail(String email)
 * ArrayList<ParkingSpot> getUserSpots(String username)
 * ParkingSpot getSpotById(int id)
 * ArrayList<Comment> getSpotComments(int id)
 * */

public class AppDatabaseTest {
	
	@Test
	public void testGetUserById() {
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User u = db.getUserById(1);
		
		// Asserting
		assertNotNull(u);
		assertEquals(u.getUsername(), "test0");
		assertEquals(u.getEmail(), "test0@test.com");
	}
	
	@Test
	public void testExists() {
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		assertEquals(db.exists("test1"), true);
		assertEquals(db.exists("z"), false);
	}
	
    @Test
	public void testLoginuser() {
    	AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User goodUser = new User(-1, "autoTest", "fname", "lname", "test@test.com", "yeee".hashCode());
		// Add a new user and login
		db.registerUser(goodUser);
		assertEquals(true, db.loginUser("autoTest", "yeee".hashCode()));
		// delete the user and make sure it worked
		db.delete("autoTest");
		assertEquals(db.exists("autoTest"), false);
	}
	
	@Test
	public void testRegisterUser(){
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User goodUser = new User(-1, "autoTest", "fname", "lname", "test@test.com", "yeee".hashCode());
		User badUser = new User(-1, "test1", "fname", "lname", "test@test.com", "yeee".hashCode());
		
		// Try to register a good and bad user
		db.delete(goodUser.getUsername());
		assertEquals(db.registerUser(goodUser), true);
		assertEquals(db.registerUser(badUser), false);
		
		// Delete a user and make sure they don't exist
		db.delete("autoTest");
		assertEquals(db.exists("autoTest"), false);
	}
	
	@Test
	public void testSearch() {
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		// Search somewhere in LA
		ArrayList<ParkingSpot> spots = db.searchSpotByLocation(34.0522, -118.445892);
		assertNotNull(spots);
		// Search somewhere away from LA
		spots = db.searchSpotByLocation(40.7128, -74.0060);
		assertNotNull(spots);
	}
}
