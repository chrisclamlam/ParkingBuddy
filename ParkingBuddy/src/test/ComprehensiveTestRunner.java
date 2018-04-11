package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ComprehensiveTestRunner {
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(AppDatabaseTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	    }
		
		System.out.println("AppDatabase test Successful: " + result.wasSuccessful());
		
		result = JUnitCore.runClasses(ServletTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	    }
		System.out.println("Servlet test Successful: " + result.wasSuccessful());
		
		result = JUnitCore.runClasses(UtilTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	    }
		
		System.out.println("Util test Successful: " + result.wasSuccessful());
	}

}
