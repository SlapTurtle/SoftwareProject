package Tests;

import project.*;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class TestSysApp extends TestBasis{


	@Test
	public void SysApp() {
		//setup
		setup();
		
		// Test that no employee is logged in
		assertFalse(sysApp.loggedIn());
		
		//Tests if addEmployee works
		
		assertTrue(sysApp.addEmployee("Jens"));
		
		//Test if login now works
		boolean login = sysApp.login("Jens");
		assertTrue(login);
		
		//Testing if logoff works
		sysApp.logoff();
		
		assertFalse(sysApp.loggedIn());
		
		//Test add project random ID
		
		assertTrue(sysApp.addProject("143D92"));
		
		//Test add activity random ID
		
		assertTrue(sysApp.addActicity("143A33"));
	}

	
	
	// Test get week
	
	@Test
	public void DateServer(){
		
		DateServer dateServer = new DateServer();
		
		System.out.println(dateServer.getWeek());
		
		System.out.println(dateServer.getCalendar());
		
	}
	
	
}
