package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.AppDatabase;
import database.ParkingSpot;
import database.User;

/**
 * Servlet implementation class GetUserDetails
 */
@WebServlet("/GetUserDetails")
public class GetUserDetails extends MiddlewareServlet {
	private static final long serialVersionUID = 1L;
	
	private String userToJson(User u) {
		Gson gson = new Gson();
		String json = gson.toJson(u);
		return json;
	}
	
	private String spotsToJson(ArrayList<ParkingSpot> spots) {
		String json = "[";
		Gson gson = new Gson();
		Iterator<ParkingSpot> it = spots.iterator();
		while(it.hasNext()) {
			ParkingSpot spot = (ParkingSpot)it.next();
			json += gson.toJson(spot);
			if(it.hasNext()) {
				json += ",";
			}
		}
		json += "]";
		return json;
	}
	
	private String friendsToJson(ArrayList<String> friends) {
		String json = "[";
		Gson gson = new Gson();
		Iterator<String> it = friends.iterator();
		while(it.hasNext()) {
			String username = (String)it.next();
			json += gson.toJson(username);
			if(it.hasNext()) {
				json += ",";
			}
		}
		json += "]";
		return json;
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// Get the db manager instance 
		AppDatabase db = (AppDatabase) getServletContext().getAttribute("db");
		//  Check to see if it's an authenticated request
		if (user == null) {
			System.out.println("Error getting user details. Unauthenticated request.");
			response.setStatus(400);
			return;
		}
		// The only variable we need is the target username/id
		String username = request.getParameter("username");
		// Get the favorite spots, friends, and user info
		User u = db.getUserByUsername(username);
		if (u == null) {
			response.setStatus(400);
			System.out.println("Error getting details for user: " + username + ". User doesn't exist.");
			return;
		}

		// Write the json to the response DataOutputStream
		String userJson = userToJson(u);
		String spotsJson = spotsToJson(db.getUserSpots(u.getId()));
		String friendsJson = friendsToJson(db.getUserFriends(u.getId()));
		System.out.println("User json: " + userJson);
		System.out.println("Spots json: " + spotsJson);
		System.out.println("Friends json: " + friendsJson);
		String json = "{\"user\":" + userJson + ",\"spots\":" + spotsJson + ",\"friends\":" + friendsJson + "}";
		System.out.println("Final json: " + json);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException ioe) {
			System.out.println("Error writing user details response to client: " + ioe.getMessage());
		}
		
	}
}
