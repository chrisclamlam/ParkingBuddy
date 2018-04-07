package test;

import org.junit.Test;

import com.google.gson.Gson;

import database.AppDatabase;
import database.ParkingSpot;
import database.User;
import interpreter.Feature;
import interpreter.GISSpots;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
		User goodUser = new User(-1, "autoTest", "fname", "lname", "test@test.com", "yeee".getBytes());
		// Add a new user and login
		assertEquals(db.registerUser(goodUser), true);
		assertEquals(db.loginUser("autoTest", "yeee".getBytes()), true);
		// delete the user and make sure it worked
		db.delete("autoTest");
		assertEquals(db.exists("autoTest"), false);
	}
	
	@Test
	public void testRegisterUser(){
		// This line may change, because our schemas and credentials are probably different
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User goodUser = new User(-1, "autoTest", "fname", "lname", "test@test.com", "yeee".getBytes());
		User badUser = new User(-1, "test1", "fname", "lname", "test@test.com", "yeee".getBytes());
		
		// Try to register a good and bad user
		assertEquals(db.registerUser(goodUser), true);
		assertEquals(db.registerUser(badUser), false);
		
		// Delete a user and make sure they don't exist
		db.delete("autoTest");
		assertEquals(db.exists("autoTest"), false);
	}
}
