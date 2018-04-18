package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;

/**
 * Servlet implementation class CompareLyftandUber
 */
@WebServlet("/CompareLyftandUber")
public class CompareLyftandUber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompareLyftandUber() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String start_lat = request.getParameter("start_lat");
		String start_long = request.getParameter("start_long");
		String end_lat = request.getParameter("end_lat");
		String end_long = request.getParameter("end_long");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		String uber = database.uberPrice(Double.parseDouble(start_lat), Double.parseDouble(start_long), Double.parseDouble(end_lat), Double.parseDouble(end_long));
		String lyft = database.lyftPrice(Double.parseDouble(start_lat), Double.parseDouble(start_long), 
				Double.parseDouble(end_lat), Double.parseDouble(end_long));
		if(uber != null && lyft != null) //if both have values of estimation rides
		{
			response.setStatus(200);
			response.getWriter().write(uber+ " "+ lyft);
			response.getWriter().flush();
			response.getWriter().close();
		}
		response.setStatus(400);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
