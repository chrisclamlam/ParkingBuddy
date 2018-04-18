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
	private static String key  = "AIzaSyBjpBzb-segfA_rM4r14z-PKmZ61_3wv74";
	
	private static String makeRequest(String requestString) {
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
			return response;
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return null;
		}
	}
	
	private static ArrayList<ParkingSpot> parseResponse(String response, int spotType){
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
		
		// get the data types from the spots oject
		// add the resulting spot to ourSpots
		for(Result result : spots.getResults()) {
			remoteid = result.getId();
			label = result.getName();
			lng = result.getGeometry().getLocation().getLng();
			lat = result.getGeometry().getLocation().getLat();
			s = new ParkingSpot(remoteid, label, spotType, lat, lng);
			ourSpots.add(s);
		}
		return ourSpots;
	}

	public static ArrayList<ParkingSpot> getNearbyParking(double latitude, double longitude, double radius) {
		String requestString = host;
		requestString += "location=" + latitude + "," + longitude + "&";
		requestString += "radius=" + radius + "&";
		requestString += "type=parking&";
		requestString += "key=" + key;
		String response = makeRequest(requestString);
		return parseResponse(response, 3);
	}
	
	public static ArrayList<ParkingSpot> getLocations(String name, double latitude, double longitude){
		String requestString = host;
		requestString += "location=" + latitude + "," + longitude + "&";
		requestString += "keyword=" + name + "&";
		requestString += "key=" + key + "&";
		requestString += "rankby=distance";
		String response = makeRequest(requestString);
		System.out.println("Response from google: " + response);
		return parseResponse(response, -1);
	}
}
