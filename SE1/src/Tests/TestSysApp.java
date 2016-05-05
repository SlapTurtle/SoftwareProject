package Tests;

import project.*;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TestSysApp extends TestBasis{


	@Test
	public void SysApp() throws IllegalOperationException, NoSuchMethodException, SecurityException, IOException {
		//setup
		setup();
		
		
		
		// Test that no employee is logged in
		assertFalse(sysApp.loggedIn());
		
		
		//Tests if addEmployee works
		
		assertTrue(sysApp.addEmployee(e1));
		
		//Test if login now works
		boolean login = sysApp.login(e1);
		assertTrue(login);
		
		//Testing if logoff works
		sysApp.logoff();
		
		assertFalse(sysApp.loggedIn());
		
	
		
		// test if ad projekt works and if it is possible to add the same project twice
		
		assertTrue(sysApp.addProject(p1));
		assertFalse(sysApp.addProject(p1));
		
		// test if get projekt by name works
		Project p = sysApp.projectByName(p1.getName());
		assertEquals(p,p1);
		
		//Test if add activity works and if you can add the same twice
		
		assertTrue(sysApp.addActivity(a1));
		assertFalse(sysApp.addActivity(a1));
		
		//Test if get activity by name works
		
		Activity a = sysApp.activityByName(a1.getType());
		assertEquals(a, a1);
		
		// test that get available employees works
		
		a1.assignEmployee(e1);
		a1.assignEmployee(e2);
		
		sysApp.addEmployee(e1);
		sysApp.addEmployee(e2);
		sysApp.addEmployee(e3);
		
		assertTrue(sysApp.getEmployeeList().contains(e1));
	}
		
		//System log, test?
		
		
		//String s = "Hey this is a report";
		//assertTrue(sysApp.setWeekReport(s));
		//(sysApp.getSystemLog());
		
}
