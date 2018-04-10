package database;

public class Comment {
	
	private int commentId;
	private int userId;
	private int spotId;
	private int rating;
	private String comment;
	
	public Comment(int cid, int uid, int sid, int rtg, String comm) {
		commentId = cid;
		userId = uid;
		spotId = sid;
		rating = rtg;
		comment = comm;
	}
	
	
	public Comment(int uid, int sid, int rtg, String comm) {
		commentId = -1;
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


	public int getRating() {
		return rating;
	}


	public String getComment() {
		return comment;
	}

}
