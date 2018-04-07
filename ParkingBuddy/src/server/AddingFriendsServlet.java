package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;

/**
 * Servlet implementation class AddingFriendsServlet
 */
@WebServlet("/AddingFriendsServlet")
public class AddingFriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingFriendsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String friendsusername = request.getParameter("friendsusername");
		String username = request.getParameter("username");
		String rating = request.getParameter("rating");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		int userid = database.getUserIdByUsername(username);
		if(database.exists(friendsusername) && database.exists(username))
		{
			database.addFriends(username, friendsusername);
			response.setStatus(200);
		}
		
		response.setStatus(400);
	}

}
