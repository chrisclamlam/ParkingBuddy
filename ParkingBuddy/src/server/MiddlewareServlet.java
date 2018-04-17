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
	protected User user;
	
	public MiddlewareServlet() {
		super();
		user = null;
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {

		// Decrypt the token
		String token = req.getHeader("Token");
		System.out.println("Token received by middleware: " + token);
		// Get the initialized Util class
		System.out.println("Encryption type: " + ((Util)getServletContext().getAttribute("util")).getKey().getKeyType());
		user = ((Util)getServletContext().getAttribute("util")).readToken(token);
		// if the user token didn't parse correctly, return a bad response code
		if(user == null) {
			res.setStatus(400);
			System.out.println("Error decryption token in MiddlewareServlet");
			return;
		}
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
