package database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.tomcat.util.http.parser.MediaType;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import interpreter.CostEstimate;
import interpreter.LyftPrices;
import interpreter.LyftToken;
import interpreter.Price;
import interpreter.UberPrices;


public class UberLyftRequester {
	private static String uberhost = "https://api.uber.com/v1.2/estimates/price?";
	private static String lyfthost = "https://api.lyft.com/v1/cost?";
	private static String uberkey = "fjggm_dSQsN3zUDNrg0OkneSNTr1dJBdkO-soy9J";
	//READ ME
	//uncomment below for testing purposes
	
//	public static void main(String[] args)
//	{
//		//System.out.println("Running");
//		ArrayList<Price> uber= getUberPrice(34.022677,-118.289497,34.034702, -118.292584);
//		//System.out.println("test "+ test.get(0).getLowEstimate());
//		UberLyftRequester request = new UberLyftRequester();
//	
//		for(int i=0; i< uber.size() ;i++)
//		{
////			System.out.println(test.get(i).getLocalizedDisplayName());
//			if(uber.get(i).getLocalizedDisplayName().equals("uberX"))
//			{
//				//System.out.println("test "+ uber.get(i).getEstimate());
//				double min = (double) uber.get(i).getHighEstimate();
//				double max = (double) uber.get(i).getLowEstimate();
//				String estimate = "$" +Double.toString(min) + "-"+ Double.toString(max);
//				System.out.println(estimate);
//				
//				break;
//			}
//		}
//		ArrayList<CostEstimate> lyft= getLyftPrice(34.022677,-118.289497,34.034702, -118.292584);
//		//System.out.println("Test2 "+ test2.get(0).getEstimatedCostCentsMin());
//		for(int i=0; i< lyft.size() ;i++)
//		{
////			System.out.println(test.get(i).getLocalizedDisplayName());
//			if(lyft.get(i).getRideType().equals("lyft"))
//			{
//				//System.out.println("test "+ lyft.get(i).getEstimate());
//				int max = lyft.get(i).getEstimatedCostCentsMax()/100;
//				int min = lyft.get(i).getEstimatedCostCentsMin()/100;
//				String estimate = "$" +Integer.toString(min) + "-"+ Integer.toString(max);
//				System.out.println(estimate);
//				break;
//			}
//		}
//	}
	
	public static ArrayList<Price> getUberPrice(double d, double e, double f, double g)
	{
		//String temp =" https://api.uber.com/v1.2/products?latitude=" +d + "&longitude="+e;
		String temp = "https://api.uber.com/v1.2/products?latitude=37.7759792&longitude=-122.41823";
		String requestString = uberhost;
		requestString += "start_latitude=" + d + "&start_longitude=" + e + "&";
		requestString += "end_latitude=" + f+ "&end_longitude=" + g;

		String response = "";

		try {
			URL url = new URL (requestString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Authorization", "Token " + uberkey);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Language", "en_US");
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
//			SessionConfiguration config = new SessionConfiguration.Builder()
//				    .setClientId("<CLIENT_ID>")
//				    .setServerToken("<SERVER_TOKEN>")
//				    .build();
//
//				ServerTokenSession session = new ServerTokenSession(config);
			
			
			
//			// Check the status and parse the response
			if(conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
					System.out.println("Bad Request");
					return null; // change to return null when you can
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			System.out.println("Searching  " + d + ", " + e);
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
		double pricemax;
		double pricemin;
		String ridetype;
//		// get the data types from the spots oject
//		// add the resulting spot to ourSpots
		for(Price result : UberPrices.getPrices()) {
			pricemax = (double) result.getLowEstimate();
			pricemin = (double) result.getHighEstimate();
			ridetype = result.getLocalizedDisplayName();
			s = new Price(pricemin, pricemax);
			up.add(s);
		}
		return up;
	}
	public static ArrayList<CostEstimate> getLyftPrice(double latitude, double longitude, double endlatitude, double endlongitude)
	{
		String requestString = lyfthost;
		requestString += "start_lat=" + latitude + "&start_lng=" + longitude + "&";
		requestString += "end_lat=" + endlatitude+ "&end_lng=" + endlongitude;
//		requestString += "key=" + "-673kLvZUxb4p8tJbEqQrqilKoqwQRt3nqJBRlk-";
		String response = "";

		try {
			
			String urlaccesstokenrequest = "https://api.lyft.com/oauth/token";
//			URL url = new URL (urlrequest);
			String username = "k_sJacGyfBGp";
			String password = "ZUXwS-rNNXIqYobF-Lf4ldnr4PDS0Pgx";
			HttpURLConnection conn = (HttpURLConnection) new URL(urlaccesstokenrequest).openConnection();

			String login = username+":"+password;
			String base64login = new String(Base64.encodeBase64(login.getBytes()));
			String encodeuser = new String(Base64.encodeBase64(username.getBytes()));
			String encodepassword = new String(Base64.encodeBase64(password.getBytes()));
			conn.setRequestMethod("POST");
			//conn.setRequestProperty(encodeuser,encodepassword);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("grant_type", "client_credentials");
			conn.setRequestProperty("scope", "public");
			conn.setDoOutput(true);
			
			conn.setRequestProperty("Authorization", "Basic "+base64login);
			System.out.println(base64login);
			String data =  "{\"grant_type\": \"client_credentials\", \"scope\": \"public\"}";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data);
            out.close();

//			// Check the status and parse the response
			if(conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
					System.out.println("Bad Request");
					return null; // change to return null when you can
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			//System.out.println("Searching for cities near " + latitude + ", " + longitude);
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				response += line;
			}
			//System.out.println(response);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return null;
		}

		Gson gson = new Gson();
		LyftToken token = new LyftToken();
		try {
			token = gson.fromJson(response, LyftToken.class);
		}
		catch (JsonSyntaxException jse) {
			System.out.println("Response parsing LYFT API Token repsonse: " + jse.getMessage());
			return null;
		}
		if(token == null) {
			System.out.println("No token found");
			return null;
		}
		//System.out.println("TOKEN IS "+ token.getAccessToken());
		String response2="";
		try {
			
			HttpURLConnection conn2 = (HttpURLConnection) new URL(requestString).openConnection();
			System.out.println(requestString);
			conn2.setRequestProperty("Authorization", token.getTokenType()+" "+token.getAccessToken());
			conn2.setRequestMethod("GET");
			
			if(conn2.getResponseCode() < 200 || conn2.getResponseCode() > 299) {
				System.out.println("Bad Request");
				return null; // change to return null when you can
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
			String line;
			//System.out.println("Searching for cities near " + latitude + ", " + longitude);
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				response2 += line;
			}
	//		System.out.println(response2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LyftPrices lyftPrices = null;
		
		try {
			Gson gson2 = new Gson();
			System.out.println(response2);
			lyftPrices = gson2.fromJson(response2, LyftPrices.class);
		} catch (JsonSyntaxException jse) {
			System.out.println("Response parsing LYFT API response: " + jse.getMessage());
			return null;
		}
		if(lyftPrices == null) {
			System.out.println("No Lyft price results found");
			return null;
		}
//
		ArrayList<CostEstimate> LPArray = new ArrayList<CostEstimate>();
		CostEstimate s= null;
		String current;
		int pricemax;
		int pricemin;
		String ridetype;
		for(CostEstimate result : lyftPrices.getCostEstimates()) {
			pricemax = result.getEstimatedCostCentsMin();
			pricemin = result.getEstimatedCostCentsMax();
			ridetype = result.getRideType();
			s = new CostEstimate(pricemin, pricemax);
			LPArray.add(s);
		}
		return LPArray;
	}


}
