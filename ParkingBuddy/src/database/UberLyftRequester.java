
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class UberLyftRequester {
	private static String uberhost = "https://api.uber.com/v1.2/estimates/price?";
	private static String lyfthost = "https://api.lyft.com/v1/cost?";
	private static String uberkey = "-673kLvZUxb4p8tJbEqQrqilKoqwQRt3nqJBRlk-";
	//READ ME
	//keys are not correct --must do 
	public static void main(String[] args)
	{
		ArrayList<Price> test = getUberPrice(34.022677,-118.289497,34.034702, -118.292584);
	}
	
	public ArrayList<Price> getUberPrice(double latitude, double longitude, double endlatitude, double endlongitude)
	{
		String requestString = uberhost;
		requestString += "start_latitude=" + latitude + "&start_longitude=" + longitude + "&";
		requestString += "endlatitude=" + endlatitude+ "&end_longitude=" + endlongitude;
		//requestString += "key=" + "-673kLvZUxb4p8tJbEqQrqilKoqwQRt3nqJBRlk-";
		String response = "";

		try {
			URL url = new URL (requestString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization",uberkey);
//			// Check the status and parse the response
			if(conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
					System.out.println("Bad Request");
					return null; // change to return null when you can
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			System.out.println("Searching  " + latitude + ", " + longitude);
			while((line = br.readLine()) != null) {
				System.out.println(line);
				response += line;
//			}
			}
		} catch (IOException ioe) {
		System.out.println(ioe.getMessage());
		return null;
	}

		Gson gson = new Gson();
		UberPrices UberPrices = null;
		try {
			UberPrices = gson.fromJson(response, UberPrices.class);
		} catch (JsonSyntaxException jse) {
			System.out.println("Response parsing UBER API response: " + jse.getMessage());
			return null;
		}
		if(UberPrices == null) {
			System.out.println("No Uber price results found");
			return null;
		}
//		
//		
		ArrayList<Price> up = new ArrayList<Price>();
		Price s = null;
		String current;
		int pricemax;
		int pricemin;
//		
//		// get the data types from the spots oject
//		// add the resulting spot to ourSpots
		for(Result result : UberPrices.getPrices()) {
			pricemax = result.getLowEstimate();
			pricemin = result.getHighEstimate();
			s = new Price(pricemin, pricemax);
			up.add(s);
		}
		return up;
	}
	public ArrayList<CostEstimate> getLyftPrice(double latitude, double longitude, double endlatitude, double endlongitude)
	{
		String requestString = lyfthost;
		requestString += "start_lat=" + latitude + "&start_lng=" + longitude + "&";
		requestString += "end_lat=" + endlatitude+ "&end_lng=" + endlongitude;
		requestString += "key=" + "-673kLvZUxb4p8tJbEqQrqilKoqwQRt3nqJBRlk-";
		String response = "";

		try {
			URL url = new URL (requestString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
//			// Check the status and parse the response
			if(conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
//					System.out.println("Bad Request");
					return null; // change to return null when you can
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
//			System.out.println("Searching for cities near " + latitude + ", " + longitude);
			while((line = br.readLine()) != null) {
				System.out.println(line);
				response += line;
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return null;
		}

		Gson gson = new Gson();
		LyftPrices LyftPrices = null;
		try {
			LyftPrices = gson.fromJson(response, LyftPrices.class);
		} catch (JsonSyntaxException jse) {
			System.out.println("Response parsing LYFT API response: " + jse.getMessage());
			return null;
		}
		if(price == null) {
			System.out.println("No Lyft price results found");
			return null;
		}

		ArrayList<CostEstimate> LyftPrices = new ArrayList<Price>();
		CostEstimate s= null;
		String current;
		int pricemax;
		int pricemin;

	// get the data types from the spots oject
	// add the resulting spot to ourSpots
		for(Result result : LyftPrices.getCostEstimates()) {
			pricemax = result.getEstimatedCostCentsMin();
			pricemin = result.getEstimatedCostCentsMax();
			s = new CostEstimate(pricemin, pricemax);
			LyftPrices.add(s);
		}
		return LyftPrices;
	}


}
