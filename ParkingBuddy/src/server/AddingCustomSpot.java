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
@WebServlet("/AddCustomSpot")
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the db instance
		AppDatabase db = (AppDatabase) getServletContext().getAttribute("db");
		// Get the data needed to create a parking Spot and a favoritesList entry
		String label;
		int uid, spotType;
		double lat, lng;
		try {
			uid = user.getId();
			label = request.getParameter("name");
			spotType = Integer.parseInt(request.getParameter("spotType"));
			lat = Double.parseDouble(request.getParameter("lat"));
			lng = Double.parseDouble(request.getParameter("lng"));
		} catch (NullPointerException npe) {
			response.setStatus(400);
			System.out.println("Error parsing CustomSpot GET Endpoint");
			return;
		} catch (NumberFormatException nfe) {
			response.setStatus(400);
			System.out.println("Incorrect arguments sent from client in CustomSpot GET Endpoint>");
			return;
		}
		ParkingSpot ps = new ParkingSpot("-1", label, spotType, lat, lng);
		
		/*// Add the spot and get the spot id
		int sid = db.addCustomSpot(ps);
		if (sid == -1) {
			response.setStatus(400);
			return;
		}
		
		// Add the user and spot to the favorites list
		if(db.addFavoriteParking(uid, sid)) {
			response.setStatus(200);
			return;
		}*/
		response.setStatus(400);
		return;
	}

}
