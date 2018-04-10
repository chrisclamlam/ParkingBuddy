package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
		AppDatabase adb = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		
	}
}
