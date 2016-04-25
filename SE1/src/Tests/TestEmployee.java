package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.*;

import project.*;

public class TestEmployee extends TestBasis{
	@Test
	public void testName(){
		//Makes employee Brian Andersen
		String s = "BRIAN";
		Employee e = new Employee("BRIAN");
		
		//Tests that the initials match the constructors
		assertEquals(s, e.getInitials());
	}
	
	@Test
	public void testAssignProject() throws IllegalOperationException{
		//make employee Brian Andersen
		Employee e = new Employee("BRIAN");
		
		//make Project
		int year = 2016;
		int week = 10;
		
		Week week1 = new Week(year, week);
		Week week2 = new Week(year, week+1);
		Week week3 = new Week(year, week+2);
		
		Project a = new Project("1axxx", week1, week2, week3);
		Project b = new Project("2bxxx", week1, week2, week3);
		Project c = new Project("3cxxx", week1, week2, week3);
		
		//Assigns projects
		assertTrue(e.assignProject(a));
		assertTrue(e.assignProject(b));
		assertTrue(e.assignProject(c));
		
		//No double assigning
		assertFalse(e.assignProject(a));
		assertFalse(e.assignProject(b));
		assertFalse(e.assignProject(c));
	}
	
	@Test
	public void testAssignActivity() throws IllegalOperationException{
		//make employee Brian Andersen
		Employee e = new Employee("BRIAN");
		
		//make Activity and assign to employee
		int year = 2016;
		int week = 10;
		
		Week week1 = new Week(year, week);
		Week week2 = new Week(year, week+1);
		Week week3 = new Week(year, week+2);
		
		Activity a = new Activity("01xxx", week1, week3);
		Activity b = new Activity("02xxx", week3, week3);
		Activity c = new Activity("03xxx", week2, week3);
		
		//Assigns activities
		assertTrue(e.assignActivity(a));
		assertTrue(e.assignActivity(b));
		assertTrue(e.assignActivity(c));
		
		//No double assigning
		assertFalse(e.assignActivity(a));
		assertFalse(e.assignActivity(b));
		assertFalse(e.assignActivity(c));	
	}
	
	@Test
	public void testWeeklyHours() throws IllegalOperationException{
		//make employee Brian Andersen
		Employee e = new Employee("BRIAN");
		
		//make Activity and assign to employee
		int year = 2016;
		int week = 10;
		
		Week week1 = new Week(year, week);
		Week week2 = new Week(year, week+1);
		Week week3 = new Week(year, week+2);
		
		Activity a = new Activity("01xxx", week1, week3);
		Activity b = new Activity("02xxx", week3, week3);
		Activity c = new Activity("03xxx", week2, week3);
		a.type = "a1xxx";
		b.type = "b2xxx";
		c.type = "c3xxx";
		
		e.assignActivity(a);
		e.assignActivity(b);
		e.assignActivity(c);
		
		//test for setHours
		//for a
		boolean[] addedHours1 = {
			e.setHours(a, 8.0, week3, 0),
			e.setHours(a, 8.0, week3, 1),
			e.setHours(a, 8.0, week3, 2),
			e.setHours(a, 8.0, week3, 3),
			e.setHours(a, 8.0, week3, 4),
			e.setHours(a, 8.0, week3, 5),
			e.setHours(a, 8.0, week3, 6),
			e.setHours(a, 8.0, week3, 7),
			e.setHours(a, 8.0, week3, 8)
		};
		
		//for b
		boolean[] addedHours2 = {
			e.setHours(b, 4.0, week3, 0),
			e.setHours(b, 4.0, week3, 1),
			e.setHours(b, 4.0, week3, 2),
			e.setHours(b, 4.0, week3, 3),
			e.setHours(b, 4.0, week3, 4),
			e.setHours(b, 4.0, week3, 5),
			e.setHours(b, 4.0, week3, 6),
			e.setHours(b, 4.0, week3, 7),
			e.setHours(b, 4.0, week3, 8)
		};
		
		//asserts
		assertFalse(addedHours1[0]);
		assertFalse(addedHours2[0]);
		for(int i = 1; i<8; i++){
			assertTrue(addedHours1[i]);
			assertTrue(addedHours2[i]);
		}
		assertFalse(addedHours1[8]);
		assertFalse(addedHours2[0]);
		
		//Tests for getWorkHours
		double d1 = e.getWorkHours(a, week3);
		double d2 = e.getWorkHours(b, week3);
		double actual1 = 8*7;
		double actual2 = 4*7;
		
		assertTrue(d1 == actual1);
		assertTrue(d2 == actual2);
		
		//Tests for getWeeklyActivities
		ArrayList<Activity> list1 = e.getWeeklyActivities(week1);
		ArrayList<Activity> list2 = e.getWeeklyActivities(week2);
		ArrayList<Activity> list3 = e.getWeeklyActivities(week3);
		assertEquals(list1.size(), 1);
		assertEquals(list2.size(), 2);
		assertEquals(list3.size(), 3);
		
		//Tests for getWeeklyHours
		double d = e.getWeeklyHours(week3);
		double actual = (8*7) + (4*7);
		assertTrue(d == actual);
	}
}
