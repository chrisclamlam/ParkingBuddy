package database;

public class Comment {
	
	private int userId;
	private int spotId;
	private double rating;
	private String comment;
	
	
	public Comment(int id, int uid, int sid, int rtg, String comm) {
		userId = uid;
		spotId = sid;
		rating = rtg;
		comment = comm;
	}


	public int getUserId() {
		return userId;
	}


	public int getSpotId() {
		return spotId;
	}


	public double getRating() {
		return rating;
	}


	public String getComment() {
		return comment;
	}

}
