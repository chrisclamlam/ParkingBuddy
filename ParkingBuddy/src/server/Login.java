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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Login() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username;
		int password;
		try {
			username = request.getParameter("username");
			password = request.getParameter("passhash").hashCode();
		} catch (NullPointerException npe) {
			System.out.println("Incorrectly formatted Login Request");
			response.setStatus(400);
			return;
		}
		
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		if(database.loginUser(username, password))
		{
			User u = database.getUserByUsername(username);
			if(u == null) {
				response.setStatus(400);
				System.out.println("Tried logging in a user that doesn't exist");
				return;
			}
			String token = Util.generateToken(u, getServletContext().getRealPath("key.txt"));
			response.setHeader("Set-Cookie", token);
			response.setStatus(200);
			return;
		} else {
			System.out.println("Unsuccessful login for user: " + username);
		}
		response.setStatus(400);
	}

}
