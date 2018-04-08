package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.Comment;
import database.ParkingSpot;

/**
 * Servlet implementation class AddingCommentsServlet
 */
@WebServlet("/AddingComments")
public class AddingComments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingComments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Add comment endpoint hit");
		String usercomment = request.getParameter("usercomment");
		String username = request.getParameter("username");
		String parkingspotname = request.getParameter("parkingname");
		String rating = request.getParameter("rating");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		int parkingid =  database.getSpotByName(parkingspotname).getId();
		int userid = database.getUserByUsername(username).getId();
		if(database.getSpotByName(parkingspotname).getId() != -1)//if parking name exists
		{
			
			database.addSpotComments(parkingid, userid, Integer.parseInt(rating), usercomment);
			response.setStatus(200);
		}
		response.setStatus(400);
		
	}

}
