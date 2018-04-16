package database;

public class ParkingSpot {
	
	private int id;
	private String remoteId;
	private int spotType;
	private String label;
	private double latitude;
	private double longitude;
	
	/* SpotTypes
	 * 1 - Meter
	 * 2 - Street
	 * 3 - Structure/Lot
	 */
	
	public ParkingSpot(int id, String remoteid, String label, int spotType,  double latitude, double longitude) {
		this.id = id;
		this.remoteId = remoteid;
		this.label = label;
		this.spotType = spotType;
		this.latitude = latitude;
		this.longitude = longitude;	
	}
	
	public ParkingSpot(String remoteid, String label, int spotType,  double latitude, double longitude) {
		this.id = -1;
		this.remoteId = remoteid;
		this.label = label;
		this.spotType = spotType;
		this.latitude = latitude;
		this.longitude = longitude;	
	}


	public int getId() {
		return id;
	}


	public String getRemoteId() {
		return remoteId;
	}


	public String getLabel() {
		return label;
	}


	public int getSpotType() {
		return spotType;
	}


	public double getLatitude() {
		return latitude;
	}


	public double getLongitude() {
		return longitude;
	}
}
