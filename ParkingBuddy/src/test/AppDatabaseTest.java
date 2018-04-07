package test;

import org.junit.Test;

import database.AppDatabase;
import database.User;

import static org.junit.Assert.assertEquals;

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
	public void initDatabase() {
		// Initialize the Database with variables for testing
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		
		// Add users
		for(int i = 0; i < 10; i++) {
			db.registerUser(new User("test" + i, "fname" + i, "lname" + i, "test" + i + "@test.com", ("yeee" + i).getBytes()));
		}
		// Add spots
		
		// Add comments
		// Add Favorites
		// Add friends
	}
	
	@Test
	public void testGetUserById() {
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User u = db.getUserById(1);
		
		// Asserting
		assertEquals(u.getUsername(), "a");
		assertEquals(u.getEmail(), "a@u.com");
	}
	
	@Test
	public void testExists() {
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		assertEquals(db.exists("a"), true);
		assertEquals(db.exists("z"), false);
	}
	
    @Test
	public void testLoginuser() {
    	AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User goodUser = new User(-1, "test", "fname", "lname", "test@test.com", "yeee".getBytes());
		// Add a new user and login
		assertEquals(db.registerUser(goodUser), true);
		assertEquals(db.loginUser("test", "yeee".getBytes()), true);
		// delete the user and make sure it worked
		db.delete("test");
		assertEquals(db.exists("test"), false);
	}
	
	@Test
	public void testRegisterUser(){
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User goodUser = new User(-1, "test", "fname", "lname", "test@test.com", "yeee".getBytes());
		User badUser = new User(-1, "a", "fname", "lname", "test@test.com", "yeee".getBytes());
		
		// Try to register a good and bad user
		assertEquals(db.registerUser(goodUser), true);
		assertEquals(db.registerUser(badUser), false);
		
		// Delete a user and make sure they don't exist
		db.delete("test");
		assertEquals(db.exists("test"), false);
	}
}
