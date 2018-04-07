package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.User;

/**
 * Servlet implementation class SearchUsersServlet
 */
@WebServlet("/SearchUsersServlet")
public class SearchUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchValue = request.getParameter("searchingUser");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		String pageToForward = "nextpage.js";
		PrintWriter out = response.getWriter();
		ArrayList<User> myResults = database.searchUsersByUsername(searchValue);
		if(myResults!= null)  
		{
			//if there is a user with this username return the list of users
			MakeJson json = new MakeJson();
			out.write(json.makeJsonString(myResults));
			response.setStatus(200);
		}
		response.setStatus(400);
//		response.sendRedirect(pageToForward);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
