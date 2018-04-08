package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.AppDatabase;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public LoginServlet() {
        super();
     
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		byte[] password = null;
		//(byte[])request.getParameter("password");
		AppDatabase database = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
		if(database.loginUser(username, password))
		{
			
			response.setStatus(200);
		}
		else
		{
			response.setStatus(400);
		}
		
		
		
	}

}
