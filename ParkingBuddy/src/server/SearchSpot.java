package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.AppDatabase;
import database.ParkingSpot;

/**
 * Servlet implementation class SearchSpot
 */
@WebServlet("/SearchSpot")
public class SearchSpot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSpot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the location

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
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
