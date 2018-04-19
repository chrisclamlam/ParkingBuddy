package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.AppDatabase;
import database.User;

/**
 * Servlet implementation class GetUserDetails
 */
@WebServlet("/GetUserDetails")
public class GetUserDetails extends MiddlewareServlet {
	private static final long serialVersionUID = 1L;
	
	private String makeUserJson(User u) {
		Gson gson = new Gson();
		String json = gson.toJson(u);
		System.out.println("User details: " + json);
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
		u.setFavoriteSpots(db.getUserSpots(username));
		u.setFriends(db.getUserFriends(username));
		// Write the json to the response DataOutputStream
		String json = makeUserJson(u);
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
