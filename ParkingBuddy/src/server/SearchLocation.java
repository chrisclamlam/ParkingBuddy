package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.AppDatabase;
import database.ParkingSpot;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Hit location endpoint");
		AppDatabase db = (AppDatabase) getServletContext().getAttribute("db");
		// Get the name and coordinates from the request
		String keyword = request.getParameter("keyword");
		String latS = request.getParameter("lat");
		String lngS = request.getParameter("lng");
		System.out.println("Keyword: " + keyword);
		System.out.println("lat: " + latS);
		System.out.println("lng: " + lngS);
		if(keyword == null || latS == null || lngS == null) {
			response.setStatus(400);
			return;
		}
		// Query the location on the backend
		ArrayList<ParkingSpot> spots = db.searchLocations(keyword, Double.parseDouble(latS), Double.parseDouble(lngS));
		// ArrayList<ParkingSpot> -> JSON
		Gson gson = new Gson();
		String jsonResponse = "[";
		
		// Write the response
		try {
			// Iterate through the parking spots, marshall to json
			Iterator<ParkingSpot> it = spots.iterator();
			while(it.hasNext()) {
				ParkingSpot spot = (ParkingSpot)it.next();
				jsonResponse += gson.toJson(spot);
				// Add a comma after every element in the list, except the last one
				if(it.hasNext()) {
					jsonResponse += ",";
				}	
			}
			// Close the json array
			jsonResponse += "]";
			PrintWriter pw = response.getWriter();
			response.setStatus(200);
			pw.write(jsonResponse);
			pw.flush();
			pw.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			response.setStatus(400);
			return;
		}
	}
	
}
