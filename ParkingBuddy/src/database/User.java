package database;

import java.util.ArrayList;

public class User {

	// Declare the member Variables
	private int id;
	private String username;
	private String fname;
	private String lname;
	private String email;
	private int    passhash;
	// These are used for getting the details from other tables
	private ArrayList<ParkingSpot> favoriteSpots;
	private ArrayList<User>        friends;
	
	// Constructors set everything
	public User(int id, String username, String fname, String lname, String email, int passhash) {
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passhash = passhash;
		favoriteSpots = null;
		friends = null;
	}
	
	public User(String username, String fname, String lname, String email, int passhash) {
		this.id = -1;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passhash = passhash;
		favoriteSpots = null;
		friends = null;
	}
	
	public User(int id, String username, String fname, String lname, String email) {
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		favoriteSpots = null;
		friends = null;
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
	public int getPasshash() {
		return passhash;
	}
	
	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}
	
	public ArrayList<User> getFriends(){
		return friends;
	}
	
	public void setFavoriteSpots(ArrayList<ParkingSpot> favoriteSpots) {
		this.favoriteSpots = favoriteSpots;
	}
	
	public ArrayList<ParkingSpot> getFavoriteSpots(){
		return favoriteSpots;
	}
}
