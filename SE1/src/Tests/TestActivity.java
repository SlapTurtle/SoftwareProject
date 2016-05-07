package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import Project.*;

public class TestActivity extends TestBasis{
	
	@Test
	public void testFields() {
		sysApp = new SysApp(false);
		Activity act1 = new Activity(sysApp, "act1", week1, week3);
		assertEquals("act1".toUpperCase(), act1.getType());
		assertEquals(week1, act1.getStartWeek());
		assertEquals(week3, act1.getEndWeek());
		assertEquals(0, act1.getEmployeeList().size());
		assertEquals(0, act1.getProjectList().size());
		assertTrue(0.0 == act1.getHourBudget());
		assertTrue(0.0 == act1.getSpentBudget());
		assertEquals(act1.getUniqueID(), "ID1");	//id increases
		
		Activity act2 = new Activity(sysApp, "act2", week1, week3);
		assertEquals("act2".toUpperCase(), act2.getType());
		assertEquals(week1, act2.getStartWeek());
		assertEquals(week3, act2.getEndWeek());
		assertEquals(0, act2.getEmployeeList().size());
		assertEquals(0, act2.getProjectList().size());
		assertTrue(0.0 == act2.getHourBudget());
		assertTrue(0.0 == act2.getSpentBudget());
		assertEquals(act2.getUniqueID(), "ID2");	//id increases
		
		act1.setType("act3");
		assertEquals("act3".toUpperCase(), act1.getType());
		assertEquals(act1.getUniqueID(), "ID1");
		
		act2.setType("act4");
		assertEquals("act4".toUpperCase(), act2.getType());
		assertEquals(act2.getUniqueID(), "ID2");
	}
	
	
	@Test
	public void testsWeekChange() {
		//new end week later than startWeek
		a1.setEndWeek(week2);
		assertEquals(week2, a1.getEndWeek());
		
		//new start week earlier than endWeek
		a1.setStartWeek(week2);
		assertEquals(week2, a1.getStartWeek());
		
		//new end week earlier than start week
		a1.setEndWeek(week1);
		assertNotEquals(week1, a1.getEndWeek());
		assertEquals(week2, a1.getEndWeek());	//unchanged
		
		//new start week later than endWeek
		a1.setStartWeek(week3);
		assertNotEquals(week3, a1.getEndWeek());
		assertEquals(week2, a1.getStartWeek());	//unchanged
	}
	
	@Test
	public void testBudget() {
		//Changes Budget
		assertTrue(a1.getHourBudget() == 0);
		a1.setHourBudget(100.00);
		assertTrue(a1.getHourBudget() == 100.00);
		a1.setHourBudget(200.00);
		assertTrue(a1.getHourBudget() == 200.00);
		
		//Assigned Employee spends 25 hours.
		e1.assignActivity(a1);
		a1.assignEmployee(e1);
		for(int i=1; i<6; i++){
			e1.setHours(a1, 5.0, week2, i);
		}
		double d1 = 5.0*5;
		assertTrue(a1.getSpentBudget()==d1);
		
		//Another assigned Employee spends 35 hours.
		e2.assignActivity(a1);
		a1.assignEmployee(e2);
		for(int i=1; i<6; i++){
			e2.setHours(a1, 7, week2, i);
		}
		double d2 = 7.0*5;
		assertTrue(a1.getSpentBudget()==d1+d2);
		
		//First employee changes hours to 20 hours.
		for(int i=1; i<6; i++){
			e1.setHours(a1, 4.0, week2, i);
		}
		double d3 = 4.0*5;
		assertTrue(a1.getSpentBudget()==d3+d2);
	}
	
	@Test
	public void testEmployeeHours() throws IllegalOperationException{
		//tries to get hours from unassigned employee
		try{
			a1.getEmployeeHours(e1, week1);
			fail();
		}
		catch(IllegalOperationException e){
			assertEquals(e.getMessage(), "Employee not assigned to activity");
			assertEquals(e.getErrorClass(), Activity.class);
		}
		
		//makes employee have hours in multiple weeks
		e1.assignActivity(a1);
		a1.assignEmployee(e1);
			
		for(int i=1; i<6; i++){
			e1.setHours(a1, 5.0, week1, i);
		}
		for(int i=1; i<6; i++){
			e1.setHours(a1, 4.0, week2, i);
		}
		for(int i=1; i<6; i++){
			e1.setHours(a1, 3.0, week3, i);
		}
		double d1 = 5.0*5, d2 = 4.0*5, d3 = 3.0*5;
		
		//checks that getEmployeeHours for each week works
		assertTrue(a1.getEmployeeHours(e1, week1) == d1);
		assertTrue(a1.getEmployeeHours(e1, week2) == d2);
		assertTrue(a1.getEmployeeHours(e1, week3) == d3);
		
		//checks that total spent budget is correctly updated.
		assertTrue(a1.getSpentBudget()==d1+d2+d3);
	}
	
	@Test
	public void testAssignEmployee() {
		//tests assigning of Employees.
		assertEquals(0, a1.getEmployeeList().size());
		
		assertTrue(a1.assignEmployee(e1));
		assertEquals(1, a1.getEmployeeList().size());
		
		assertTrue(a1.assignEmployee(e2));
		assertEquals(2, a1.getEmployeeList().size());
		
		assertTrue(a1.assignEmployee(e3));
		assertEquals(3, a1.getEmployeeList().size());
		
		//tests that no double assigning happens.
		assertFalse(a1.assignEmployee(e1));
		assertFalse(a1.assignEmployee(e2));
		assertFalse(a1.assignEmployee(e3));
		assertEquals(3, a1.getEmployeeList().size());
	} 
	
	
	@Test
	public void testAssignProject() {
		//tests assigning of projects.
		assertEquals(a1.getProjectList().size(), 0);
		
		assertTrue(a1.assignProject(p1));
		assertEquals(a1.getProjectList().size(), 1);
		
		assertTrue(a1.assignProject(p2));
		assertEquals(a1.getProjectList().size(), 2);
		
		assertTrue(a1.assignProject(p3));
		assertEquals(a1.getProjectList().size(), 3);
		
		//tests that no double assigning happens.
		assertFalse(a1.assignProject(p1));
		assertFalse(a1.assignProject(p2));
		assertFalse(a1.assignProject(p3));
		assertEquals(a1.getProjectList().size(), 3);
	}
}
