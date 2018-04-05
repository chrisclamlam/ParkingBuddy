package test;

import org.junit.Test;

import database.AppDatabase;
import database.User;

import static org.junit.Assert.assertEquals;

/*TODO
 * User getUserById(int)
 * boolean exists(String)
 * boolean loginUser(String username, byte[] passhash)
 * boolean registerUser(User u)
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
		assertEquals(u.getUsername(), "a");
		assertEquals(u.getEmail(), "a@u.com");
	}
}
