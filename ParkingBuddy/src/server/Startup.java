package server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import database.AppDatabase;

public class Startup implements ServletContextListener{
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
		String keyPath = servletContextEvent.getServletContext().getRealPath("key.txt");
		Util u = new Util(keyPath);
		AppDatabase db = new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest");
        servletContextEvent.getServletContext().setAttribute("util", u);
        servletContextEvent.getServletContext().setAttribute("db", db);
	}

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
