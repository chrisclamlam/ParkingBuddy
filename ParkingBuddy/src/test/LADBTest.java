package test;

import database.*;

public class LADBTest {
	// Need the DB abstractions
	private LosAngelesDatabase ladb;
	
	public LADBTest() {
		ladb = new LosAngelesDatabase();
	}
	
	public void run() {
		ParkingSpot spot = ladb.getSpotById(1);
	}
	
	public static void main(String[] args) {
		LADBTest test = new LADBTest();
		test.run();
	}
}
