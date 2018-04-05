package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LosAngelesDatabase {
	
	// The only variables needed are the connection and statement
	private String hostname;
	private String outFields;
		
	public LosAngelesDatabase() {
		// Populate from the remote Database
		hostname = "http://maps.lacity.org/lahub/rest/services/LADOT/MapServer/16/query?";
		outFields = "&outFields=SENSOR_STATUS,ADDRESS_SPACE,GPSX,GPSY,OBJECTID,SENSOR_UNIQUE_ID";
	}
	
	private String sendRequest(String url, String method) {
		String response = "";
		try {
			int responseCode;
			URL request = new URL(url);
			// Open up the connection
			HttpURLConnection conn = (HttpURLConnection) request.openConnection();
			conn.setRequestMethod(method);
			responseCode = conn.getResponseCode();
			// Check response code
			if(responseCode < 200 || responseCode > 299) {
				System.out.println("Malformed Query obtaining parking spot by id: " + responseCode);
				System.out.println(conn.getResponseMessage());
				return "";
			}
			// Read the response
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				response += output;
			}
		} catch (MalformedURLException mue) {
			System.out.println(mue.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		// Return the response
		return response;
	}
	
	public ParkingSpot getSpotById(int id) {
		/*
		 * http://maps.lacity.org/lahub/rest/services/LADOT/MapServer/16/query?
		 * The method then just adds the non-used filters to the query URL
		 * At the end of the URL, the idFilter is added to get the specific parking spot
		 * This url is the sent via a get Request to get the data from LA's servers
		 * Which is formatted as a ParkingSpot object and presumably returned to the server
		 */
		
		// Form the request URL
		String nonUsedFilters = "where=&text=&time=&geometry=&geometryType=esriGeometryEnvelope&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&returnGeometry=true&returnTrueCurves=false&maxAllowableOffset=&geometryPrecision=&outSR=&returnIdsOnly=false&returnCountOnly=false&orderByFields=&groupByFieldsForStatistics=&outStatistics=&returnZ=false&returnM=false&gdbVersion=&returnDistinctValues=false&resultOffset=&resultRecordCount=&f=html";
		String idFilter = "&objectIds=" + id;
		String reqURL = hostname + nonUsedFilters + idFilter + outFields;
		
		// Send the request
		String response = sendRequest(reqURL, "GET");
		
		// Parse the data and instantiate a ParkingSpot object
		Map<String,String> map = new HashMap<String,String>();
		// Parse the request using jsoup
		Document doc = Jsoup.parse(response);
		Elements elements = doc.getElementsByTag("i");
		String key, value;
		for(Element e : elements) {
			if(e.nextSibling() == null) {
				continue;
			}
			// Get the key and value
			key = e.text();
			value = e.nextSibling().toString();
			// Trim and remove colons
			key = key.replace(":", "");
			value = value.trim();
			// Add pair to map
			map.put(key, value);
		}
		// Return the spot
		// Use the id param to check PB's DB to get the local ID
		return new ParkingSpot(-1, 1, map.get("SENSOR_UNIQUE_ID"), 3, Double.parseDouble(map.get("GPSY")), Double.parseDouble(map.get("GPSX")));
	}
}
