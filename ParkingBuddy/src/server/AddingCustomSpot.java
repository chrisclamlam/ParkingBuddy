package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.ParkingSpot;

/**
 * Servlet implementation class AddingCustomizeSpot
 */
@WebServlet("/AddingCustomSpot")
public class AddingCustomSpot extends MiddlewareServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingCustomSpot() {
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
		
		// Get the db instance
		AppDatabase db = (AppDatabase) getServletContext().getAttribute("db");
		// Get the data needed to create a parking Spot and a favoritesList entry
		String username = user.getUsername();
		String label = request.getParameter("label");
		String spotType = request.getParameter("spotType");
		String latS = request.getParameter("lat");
		String lngS = request.getParameter("lngS");
		
		ParkingSpot ps = new ParkingSpot("-1", label, Integer.parseInt(spotType), Double.parseDouble(latS), Double.parseDouble(lngS));
		
		
		/*if(db.exists(parkingname) && !db.existsParking(parkingname))
		{
			//ParkingSpot ps = new ParkingSpot(0, parkingname,  Integer.parseInt(spottype),  double latitude, double longitude)
			//database.addSpot(ps);
			
			//waiting for a function to turn address to long/lat
			response.setStatus(200);
		}
		
		response.setStatus(400);*/
	}

}
