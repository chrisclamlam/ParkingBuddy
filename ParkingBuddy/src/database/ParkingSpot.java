package database;

public class ParkingSpot {
	
	private int id;
	private int remoteId;
	private String label;
	private String spotType;
	private double latitude;
	private double longitude;
	
	
	public ParkingSpot(int id, int remoteId, String label, String spotType, double latitude, double longitude) {
		this.id = id;
		this.remoteId = remoteId;
		this.label = label;
		this.spotType = spotType;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public int getId() {
		return id;
	}


	public int getRemoteId() {
		return remoteId;
	}


	public String getLabel() {
		return label;
	}


	public String getSpotType() {
		return spotType;
	}


	public double getLatitude() {
		return latitude;
	}


	public double getLongitude() {
		return longitude;
	}
}
