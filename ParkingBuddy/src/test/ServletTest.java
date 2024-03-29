package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.junit.Test;

import database.AppDatabase;

public class ServletTest {
	
	@Test
	public void testRegisterUser() {
		
		String host = "http://localhost:8080/ParkingBuddy/SignUp";
		AppDatabase adb = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		try {
			// Delete the test user
			adb.delete("username");
			assertEquals(adb.exists("username"), false);
			// Connect to the host
			URL url = new URL(host);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			// Create the body
			String body = "username=username&fname=fname&lname=lname&email=email&password=password";
			conn.setDoOutput(true);
			// Write the Data
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			// Test the response code
			assertEquals(200, conn.getResponseCode());
			assertNotNull(conn.getHeaderField("Set-Cookie"));
			adb.delete("username");
			assertEquals(adb.exists("username"), false);
			return;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		// The code should never reach this line, this means an exception was thrown
		assertEquals(true, false);
	}
	
	@Test
	public void testLoginUser() {
		String host = "http://localhost:8080/ParkingBuddy/Login";
		try {
			URL url = new URL(host);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			String body = "username=test0&passhash=yeee0";
			conn.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			assertEquals(200, conn.getResponseCode());
			assertNotNull(conn.getHeaderField("Set-Cookie"));
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	@Test
	public void testAddComment() {
		String loginEndpoint = "http://localhost:8080/ParkingBuddy/Login";
		String addCommentEndpoint = "http://localhost:8080/ParkingBuddy/AddComment";
		
		try {
			// Login to profile
			// Send a request to the host IP
			URL url = new URL(loginEndpoint);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			String body = "username=test0&passhash=yeee0";
			conn.setDoOutput(true);
			// Send the data through an output stream
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			// Check that the request was properly handled
			assertEquals(200, conn.getResponseCode());
			assertNotNull(conn.getHeaderField("Set-Cookie"));
			// Store the token and use it for subsequent requests
			String token = conn.getHeaderField("Set-Cookie");
			System.out.println("Token from request: " + token);
			// Make a subsequent request to the AddComment endpoint
			url = new URL(addCommentEndpoint);
			conn = (HttpURLConnection)url.openConnection();
			conn.addRequestProperty("Token", token);
			// Send the body
			conn.setRequestMethod("POST");
			body = "spot=20&rating=5&input=It's a good spot";
			conn.setDoOutput(true);
			out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			// Check the response
			assertEquals(200, conn.getResponseCode());
			return;
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		assertEquals(true, false);
	}
	
	@Test
	public void testSearchSpot() {
		
		String searchEndpoint = "http://localhost:8080/ParkingBuddy/SearchSpot?lat=34.060677&lng=-118.445892";
		String NYCSearchEndpoint = "http://localhost:8080/ParkingBuddy/SearchSpot?lat=40.7128&lng=-74.0060";
		try {
			URL url = new URL(searchEndpoint);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			assertEquals(200, conn.getResponseCode());
			
			url = new URL(NYCSearchEndpoint);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			assertEquals(200, conn.getResponseCode());
			
			
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		
	}
	
	@Test
	public void testSearchLocation() {
		String searchEndpoint = "http://localhost:8080/ParkingBuddy/SearchLocation?keyword=USC&lat=34.060677&lng=-118.445892";
		try {
			URL url = new URL(searchEndpoint);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			assertEquals(200, conn.getResponseCode());
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	@Test
	public void testAddCustomSpot() {
		String customSpotEndpoint = "http://localhost:8080/ParkingBuddy/SearchLocation?keyword=USC&lat=34.060677&lng=-118.445892";
	}
	
	@Test
	public void testGetUserDetails() {
		String loginEndpoint = "http://localhost:8080/ParkingBuddy/Login";
		String detailsEndpoint = "http://localhost:8080/ParkingBuddy/GetUserDetails?username=test0";
		String body = "username=test0&passhash=yeee0";
		try {
			// Set up the Login POST request
			URL url = new URL(loginEndpoint);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// Send it
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(body);
			out.flush();
			out.close();
			assertEquals(200, conn.getResponseCode());
			// Get the "Set-Cookie" header and use it as a token in the next request
			String token = conn.getHeaderField("Set-Cookie");
			// Set up the Details GET Request
			url = new URL(detailsEndpoint);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.addRequestProperty("Token", token);
			conn.setDoOutput(true);
			assertEquals(200, conn.getResponseCode());
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
}