package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.google.gson.Gson;

import database.AppDatabase;
import database.ParkingSpot;
import database.User;
import interpreter.Feature;
import interpreter.GISSpots;

public class AdbTestRunner {
	
	public static void main(String[] args) {
		
		initDB();
		
		/*Result result = JUnitCore.runClasses(AppDatabaseTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	    }
		
		System.out.println("Successful: " + result.wasSuccessful());*/
	}
	
	public static void initDB() {
		// Initialize the Database with variables for testing
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		
		// Add users
		for(int i = 0; i < 10; i++) {
			db.registerUser(new User("test" + i, "fname" + i, "lname" + i, "test" + i + "@test.com", ("yeee" + i).getBytes()));
		}

		// Add spots
		Gson gson = new Gson();
		FileReader fr;
		GISSpots laSpots = null;
		ArrayList<ParkingSpot> ourSpots = null;
		try {
			fr = new FileReader("ParkingMeterSensors.json");
			laSpots = gson.fromJson(fr, GISSpots.class);
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		
		if(laSpots == null) {
			System.out.println("couldn't parse json.");
			return;
		}
		
		for(Feature spot : laSpots.getFeatures()) {
			// Get the data for each spot
			int id, remoteid;
			String label;
			double latitude, longitude;
			id = -1;
			remoteid = spot.getProperties().getOBJECTID();
			label = spot.getProperties().getSENSORUNIQUEID();
			latitude = spot.getProperties().getGPSX();
			longitude = spot.getProperties().getGPSY();
			db.addSpot(new ParkingSpot(id, remoteid, label, 1, latitude, longitude));
		}
		
		// Add comments
		// Add Favorites
		// Add friends
	}
}
