package Tests;

import Classes.*;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class TestSysApp {


	@Test
	public void SysApp() {
		
		
		// Test that no employee is logged in
		assertFalse(sysapp.loggedIn());
		
		//Tests if addEmployee works
		
		assertTrue(sysapp.addEmployee("Jens"));
		
		//Test if login now works
		boolean login = sysapp.login("Jens");
		assertTrue(login);
		
		//Testing if logoff works
		sysapp.logoff();
		
		assertFalse(sysapp.loggedIn());
		
		//Test add project random ID
		
		assertTrue(sysapp.addProject("143D92"));
		
		//Test add activity random ID
		
		assertTrue(sysapp.addActicity("143A33"));
	}

	
	
	// Test get week
	
	@Test
	public void DateServer(){
		
		DateServer dateServer = new DateServer();
		
		System.out.println(dateServer.getWeek());
		
		System.out.println(dateServer.getCalendar());
		
	}
	
	
}
