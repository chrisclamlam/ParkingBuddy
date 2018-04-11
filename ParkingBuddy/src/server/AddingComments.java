package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.Comment;

/**
 * Servlet implementation class AddingCommentsServlet
 */
@WebServlet("/AddComment")
public class AddingComments extends MiddlewareServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddingComments() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Data needed to instantiate a comment
		int uid, sid, rating;
		String comment;
		// Get that data from the request and the parsed token from MiddlewareServlet service() method
		try {
			uid = user.getId();
			sid = Integer.parseInt(request.getParameter("spot"));
			rating = Integer.parseInt(request.getParameter("rating"));
			comment = request.getParameter("comment");
		} catch (NullPointerException npe) {
			System.out.println(npe.getMessage());
			response.setStatus(400);
			return;
		}
		// Send a request to the database to add a comment
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		Comment c  = new Comment(uid, sid, rating, comment);
		if(database.addSpotComments(c)) {//if parking name exists
			response.setStatus(200);
			return;
		}
		response.setStatus(400);
		
	}

}
