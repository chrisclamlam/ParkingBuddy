package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;

/**
 * Servlet implementation class AddingCustomizeSpot
 */
@WebServlet("/AddingCustomizeSpot")
public class AddingCustomizeSpot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingCustomizeSpot() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parkingname = request.getParameter("parkingname");
		String username = request.getParameter("username");
		String spottype = request.getParameter("spottype");
		String address = request.getParameter("address");
		String price = request.getParameter("price");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		
		if(database.exists(parkingname) && !database.existsParking(parkingname))
		{
			//ParkingSpot ps = new ParkingSpot(0, parkingname,  Integer.parseInt(spottype),  double latitude, double longitude)
			//database.addSpot(ps);
			
			//waiting for a function to turn address to long/lat
			response.setStatus(200);
		}
		
		response.setStatus(400);
	}

}
