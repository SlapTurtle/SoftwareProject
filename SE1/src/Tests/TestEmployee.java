package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import org.junit.*;

import project.*;


public class TestEmployee extends TestBasis{
	
	@Test
	public void testInitials() throws IllegalOperationException {
		Employee e = new Employee("BRAN");
		assertEquals(e.getInitials(), "BRAN");
	}
	
	@Test
	public void testAssignProject(){
		
		assertEquals(e1.getProjectList().size(), 0);
		
		assertTrue(e1.assignProject(p1));
		assertEquals(e1.getProjectList().size(), 1);
		
		assertTrue(e1.assignProject(p2));
		assertEquals(e1.getProjectList().size(), 2);
		
		assertTrue(e1.assignProject(p3));
		assertEquals(e1.getProjectList().size(), 3);
		
		assertFalse(e1.assignProject(p1));
		assertFalse(e1.assignProject(p2));
		assertFalse(e1.assignProject(p3));
		assertEquals(e1.getProjectList().size(), 3);
	}
	
	@Test
	public void testAssignActivity() throws IllegalOperationException{
		assertEquals(e1.getActivityList().size(), 0);

		assertTrue(e1.assignActivity(a1));
		assertEquals(e1.getActivityList().size(), 1);
		
		assertTrue(e1.assignActivity(a2));
		assertEquals(e1.getActivityList().size(), 2);
		
		assertTrue(e1.assignActivity(a3));
		assertEquals(e1.getActivityList().size(), 3);
		
		assertFalse(e1.assignActivity(a1));
		assertFalse(e1.assignActivity(a2));
		assertFalse(e1.assignActivity(a2));
		assertEquals(e1.getActivityList().size(), 3);
	}
	
	@Test
	public void testWeeklyHours() throws IllegalOperationException{
		e1.assignActivity(a1);
		e1.assignActivity(a2);
		e1.assignActivity(a3);
		
		//test for setHours
		boolean[] addedHours1 = { //for a1
			e1.setHours(a1, 8.0, week3, 0),
			e1.setHours(a1, 8.0, week3, 1),
			e1.setHours(a1, 8.0, week3, 2),
			e1.setHours(a1, 8.0, week3, 3),
			e1.setHours(a1, 8.0, week3, 4),
			e1.setHours(a1, 8.0, week3, 5),
			e1.setHours(a1, 8.0, week3, 6),
			e1.setHours(a1, 8.0, week3, 7),
			e1.setHours(a1, 8.0, week3, 8)
		};
		
		boolean[] addedHours2 = { //for a3
			e1.setHours(a3, 4.0, week3, 0),
			e1.setHours(a3, 4.0, week3, 1),
			e1.setHours(a3, 4.0, week3, 2),
			e1.setHours(a3, 4.0, week3, 3),
			e1.setHours(a3, 4.0, week3, 4),
			e1.setHours(a3, 4.0, week3, 5),
			e1.setHours(a3, 4.0, week3, 6),
			e1.setHours(a3, 4.0, week3, 7),
			e1.setHours(a3, 4.0, week3, 8)
		};
		
		assertFalse(addedHours1[0]);
		assertFalse(addedHours2[0]);
		for(int i = 1; i<8; i++){
			assertTrue(addedHours1[i]);
			assertTrue(addedHours2[i]);
		}
		assertFalse(addedHours1[8]);
		assertFalse(addedHours2[8]);
		
		//Tests for getWorkHours
		double d1 = e1.getWorkHours(a1, week3)[7];
		double d2 = e1.getWorkHours(a3, week3)[7];
		double actual1 = 8.0*7.0;
		double actual2 = 4.0*7.0;
		
		assertEquals(d1, actual1, Double.MAX_VALUE);
		assertEquals(d2, actual2, Double.MAX_VALUE);
		
		//Forcing bad week in getWorkHours
		Activity a = new Activity(sysApp, "ACT4", week2, week2);
		try{
			d1 = e1.getWorkHours(a, week1)[7];
			fail();
		} catch(IllegalOperationException e){
			assertEquals(e.getErrorClass(), Employee.class);
			assertEquals(e.getMessage(), "Week "+week1.getWeek()+" is not within "+a.getType());
		}
		try{
			d2 = e1.getWorkHours(a, week3)[7];
			fail();
		} catch(IllegalOperationException e){
			assertEquals(e.getErrorClass(), Employee.class);
			assertEquals(e.getMessage(), "Week "+week3.getWeek()+" is not within "+a.getType());
		}
		
		//Tests for getWeeklyActivities
		ArrayList<Activity> list1 = e1.getWeeklyActivities(week1);
		ArrayList<Activity> list2 = e1.getWeeklyActivities(week2);
		ArrayList<Activity> list3 = e1.getWeeklyActivities(week3);
		assertEquals(list1.size(), 1);
		assertEquals(list2.size(), 3);
		assertEquals(list3.size(), 2);
		
		//Tests for getWeeklyHours
		double d = e1.getWeeklyHours(week3);
		double actual = actual1 + actual2;
		assertEquals(d, actual, Double.MAX_VALUE);
	}
}
