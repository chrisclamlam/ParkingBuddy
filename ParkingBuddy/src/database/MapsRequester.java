package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import interpreter.MapsSpots;
import interpreter.Result;

public class MapsRequester {
	
	private static String host = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

	public static ArrayList<ParkingSpot> getNearbyParking(double latitude, double longitude, double radius) {
		String requestString = host;
		requestString += "location=" + latitude + "," + longitude + "&";
		requestString += "radius=" + radius + "&";
		requestString += "type=parking&";
		requestString += "key=" + "AIzaSyBjpBzb-segfA_rM4r14z-PKmZ61_3wv74";
		System.out.println(requestString);
		String response = "";
		try {
			// Connect through a GET requeset
			URL url = new URL(requestString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			// Check the status and parse the response
			if(conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
				System.out.println("Bad Request");
				return null; // change to return null when you can
			}
			// Read the response
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line = br.readLine()) != null) {
				response += line;
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return null;
		}
		// JSON -> MapsSpots object
		Gson gson = new Gson();
		MapsSpots spots = null;
		try {
			spots = gson.fromJson(response, MapsSpots.class);
		} catch (JsonSyntaxException jse) {
			System.out.println("Response parsing Google Maps API response: " + jse.getMessage());
			return null;
		}
		if(spots == null) {
			System.out.println("No Google Maps results found");
			return null;
		}
		// MapsSpots object -> ArrayList<database.ParkingSpot> object
		ArrayList<ParkingSpot> ourSpots = new ArrayList<ParkingSpot>();
		ParkingSpot s = null;
		String remoteid = "";
		String label = "";
		double lng, lat;
		int spotType = 4;
		
		// get the data types from the spots oject
		// add the resulting spot to ourSpots
		for(Result result : spots.getResults()) {
			
			System.out.println(remoteid);
			System.out.println(remoteid.length());
			remoteid = result.getId();
			label = result.getName();
			lng = result.getGeometry().getLocation().getLng();
			lat = result.getGeometry().getLocation().getLat();
			s = new ParkingSpot(remoteid, label, spotType, lng, lat);
			ourSpots.add(s);
		}
		return ourSpots;
	}
	
	public static void main(String[] args) {
		ArrayList<ParkingSpot> spots = getNearbyParking(34.060677, -118.445892, 400);
	}
}
