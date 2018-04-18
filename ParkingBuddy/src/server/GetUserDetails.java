package server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.User;

/**
 * Servlet implementation class GetUserDetails
 */
@WebServlet("/GetUserDetails")
public class GetUserDetails extends MiddlewareServlet {
	private static final long serialVersionUID = 1L;
	
	private String makeUserJson(User u) {
		String json = "";
		
		
		return null;
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
		String username = request.getParameter("user");
		// Get the favorite spots, friends, and user info
		User u = db.getUserByUsername(username);
		if (u == null) {
			response.setStatus(400);
			System.out.println("Error getting details for user: " + username + ". User doesn't exist.");
			return;
		}
		u.setFavoriteSpots(db.getUserSpots(username));
		u.setFriends(db.getUserFriends(username));
		
		
	}
}
