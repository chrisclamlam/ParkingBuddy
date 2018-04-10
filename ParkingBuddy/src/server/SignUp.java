package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;
import database.User;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username, fname, lname, email;
		byte[] password;
		
		try {
			username = request.getParameter("username");
			System.out.println(username);
			password = request.getParameter("password").getBytes();
			System.out.println(password);
			fname = request.getParameter("fname");
			System.out.println(fname);
			lname = request.getParameter("lname");
			System.out.println(lname);
			email = request.getParameter("email");
			System.out.println(email);
			
		} catch (NullPointerException npe) {
			response.setStatus(400);
			return;
		}
		
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		User newUser = new User( username, fname, lname, email, password);
		if(database.registerUser(newUser)){
			// Generate a token and set the "Set-Cookie" header for the response
			String token = Util.generateToken(newUser, getServletContext().getRealPath("key.txt"));
			response.setHeader("Set-Cookie", token);
			response.setStatus(200);
			return;
		}
		response.setStatus(400);
	}

}
