package server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;

/**
 * Servlet implementation class ShareSpot
 */
@WebServlet("/ShareSpot")
public class ShareSpot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareSpot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String parkingspotname = request.getParameter("parkingname");
		String friendusername = request.getParameter("friendusername");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		
		if(database.exists(username) && database.exists(friendusername) && database.existsParking(parkingspotname))
		{
			response.setStatus(200);
		}
		response.setStatus(400);
	}

}
