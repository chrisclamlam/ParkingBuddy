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
@WebServlet("/AddingCommentsServlet")
public class AddingCommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingCommentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usercomment = request.getParameter("usercomment");
		String username = request.getParameter("username");
		String parkingspotname = request.getParameter("parkingname");
		String rating = request.getParameter("rating");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		int parkingid =  database.getSpotByName(parkingspotname);
		int userid = database.getUserIdByUsername(username);
		if(database.getSpotByName(parkingspotname) != -1)//if parking name exists
		{
			
			database.addSpotComments(parkingid, userid, Integer.parseInt(rating), usercomment);
			response.setStatus(200);
		}
		response.setStatus(400);
		
	}

}
