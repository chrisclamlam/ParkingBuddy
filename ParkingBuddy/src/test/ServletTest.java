package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import database.AppDatabase;

public class ServletTest {

	@Test
	public void testRegisterUser() {
		
		String host = "http://localhost:8080/ParkingBuddy/CreateAccountServlet";
		AppDatabase adb = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		try {
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
			// Get the response
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = "";
//			String res = "";
//			while((line = br.readLine()) != null) {
//				res += line;
//			}
			// Test the response
			assertEquals(200, conn.getResponseCode());
			adb.delete("username");
			assertEquals(adb.exists("username"), false);
			return;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		// The code should never reach this line, this means an exception was thrown
		assertEquals(true, false);
	}
	
}
