package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchLocation
 */
@WebServlet("/SearchLocation")
public class SearchLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String address = request.getParameter("address");
		//turn address into location first STILL IN WORKING PROGRESS
		
		AppDatabase db = (AppDatabase) getServletContext().getAttribute("db");
		double latitude = Double.parseDouble(request.getParameter("lat"));
		double longitude = Double.parseDouble(request.getParameter("lng"));
		// Search for the location
		ArrayList<ParkingSpot> spots = db.searchSpotByLocation(latitude, longitude);
		if(spots == null) {
			response.setStatus(400);
			return;
		}
		// Turn into JSON and write reponse
		
		String json = new Gson().toJson(spots);
		response.setStatus(200);
		response.setContentType(json);
	}
	
}
