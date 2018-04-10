package server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import database.AppDatabase;

public class Startup implements ServletContextListener{
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("Context initialized");
		String keyPath = servletContextEvent.getServletContext().getRealPath("key.txt");
        servletContextEvent.getServletContext().setAttribute("util", new Util(keyPath));
        servletContextEvent.getServletContext().setAttribute("db", new AppDatabase("jdbc:mysql://localhost/test?user=root&password=OwrzTest"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
