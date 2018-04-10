package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.User;

public class MiddlewareServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private User user;
	
	public MiddlewareServlet() {
		super();
		user = null;
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		// Decrypt the token
		String token = req.getParameter("Token");
		// Set the variables
		user = Util.readToken(token);
		// Get the request method
		String method = req.getMethod();
		// Invoke the request
		// We will only be supporting GET and POST requests
		// If other methods are needed we can add them here
		if(method.equals("GET")) {
			doGet(req, res);
		} else if (method.equals("POST")) {
			doPost(req, res);
		}
	}
	
}