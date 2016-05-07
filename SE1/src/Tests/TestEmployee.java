package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import Project.*;


public class TestEmployee extends TestBasis{
	@Before @Override
	public void setup(){
		super.setup();
		sysApp.addEmployee(e1);
		sysApp.addEmployee(e2);
		sysApp.addEmployee(e3);
	}
	
	@Test
	public void testFields() {
		Employee e = new Employee("BRAN");
		assertEquals(e.getInitials(), "BRAN");
		assertEquals(e.getProjectList().size(), 0);
		assertEquals(e.getActivityList().size(), 0);
		assertEquals(e.getWorkHourList().size(), 0);
	}
	
	@Test
	public void testProject(){
		//tests assigning of projects.
		assertEquals(e1.getProjectList().size(), 0);
		
		assertTrue(e1.assignProject(p1));
		assertEquals(e1.getProjectList().size(), 1);
		
		assertTrue(e1.assignProject(p2));
		assertEquals(e1.getProjectList().size(), 2);
		
		assertTrue(e1.assignProject(p3));
		assertEquals(e1.getProjectList().size(), 3);
		
		//tests that no double assigning happens.
		assertFalse(e1.assignProject(p1));
		assertFalse(e1.assignProject(p2));
		assertFalse(e1.assignProject(p3));
		assertEquals(e1.getProjectList().size(), 3);
		
		//tests that employee can show which projects it is manager of.
		p1.addEmployee(e1);
		p2.addEmployee(e1);
		p3.addEmployee(e1);
		
		assertTrue(p1.assignManager(e1));
		assertEquals(e1.getProjectToManage().size(), 1);
		
		assertTrue(p2.assignManager(e1));
		assertEquals(e1.getProjectToManage().size(), 2);
		
		assertTrue(p3.assignManager(e1));
		assertEquals(e1.getProjectToManage().size(), 3);
	}
	
	@Test
	public void testAssignActivity() {
		//test assigning of activities
		assertEquals(e1.getActivityList().size(), 0);
		
		assertTrue(e1.assignActivity(a1));
		assertEquals(e1.getActivityList().size(), 1);
		
		assertTrue(e1.assignActivity(a2));
		assertEquals(e1.getActivityList().size(), 2);
		
		assertTrue(e1.assignActivity(a3));
		assertEquals(e1.getActivityList().size(), 3);
		
		//tests that no double assigning happens.
		assertFalse(e1.assignActivity(a1));
		assertFalse(e1.assignActivity(a2));
		assertFalse(e1.assignActivity(a2));
		assertEquals(e1.getActivityList().size(), 3);
	}
	
	@Test
	public void testWorkhourList(){
		//assigns activity to employee
		e1.assignActivity(a1);
		a1.assignEmployee(e1);
		
		//gets amount of weeks, which workhours can be assiged to, and tests
		testWorkhourList(e1, a1, 0);
		int kA1 = a1.getStartWeek().weekDifference(a1.getEndWeek())+1;
		
		//Changes the weeks of the activity
		a1.setStartWeek(week1);
		a1.setEndWeek(new Week(year, week*4));
		
		//tests that weeksize has changed
		testWorkhourList(e1, a1, kA1);
		
		//assigns new activity
		e1.assignActivity(a3);
		a3.assignEmployee(e1);
		
		//gets amount of weeks, for activity 2, and tests that both activities are correct
		int kA3 = a3.getStartWeek().weekDifference(a3.getEndWeek())+1;
		testWorkhourList(e1, a3, 0);
		testWorkhourList(e1, a1, 0);
		
		//changes the weeks of activity2
		a3.setStartWeek(week1);
		a3.setEndWeek(week1);
		
		//tests that weeksize has changed
		testWorkhourList(e1, a3, kA3);
		testWorkhourList(e1, a1, 0);
	}
	
	@SuppressWarnings("unchecked")
	private void testWorkhourList(Employee e, Activity a, int oldSize){
		//tests creation of workhours lists
		int index = e.getActivityList().indexOf(a);
		List<double[]> list = (List<double[]>) e.getWorkHourList().get(index);
		
		//tests that the list includes k amount of double[] workhour lists.
		int newSize = a.getStartWeek().weekDifference(a.getEndWeek())+1;
		assertEquals(newSize, list.size());
		assertNotEquals(newSize, oldSize);
		
		//tests that all workhours lists are empty for every week.
		for(int i=0; i<newSize; i++){
			assertEquals(7, list.get(i).length);
			for(int j = 0; j<7; j++){
				assertTrue(0.0 == list.get(i)[j]);
			}
		}
	}
	
	@Test
	public void testSetGetHours() throws IllegalOperationException {
		//assigns e1 for three different activities
		e1.assignActivity(a1);
		e1.assignActivity(a2);
		e1.assignActivity(a3);
		
		//test for setHours
		boolean[][] addedHours = {
			setFullWeekHours(e1, a1, 8.0, week2), //for a1
			setFullWeekHours(e1, a2, 6.0, week2), //for a2
			setFullWeekHours(e1, a3, 4.0, week2), //for a3
		};
		
		//Tests the only hours in weekdays (1-7) is allowed
		assertFalse(addedHours[0][0]);
		assertFalse(addedHours[1][0]);
		assertFalse(addedHours[2][0]);
		for(int i = 1; i<8; i++){
			assertTrue(addedHours[0][i]);
			assertTrue(addedHours[1][i]);
			assertTrue(addedHours[2][i]);
		}
		assertFalse(addedHours[0][8]);
		assertFalse(addedHours[1][8]);
		assertFalse(addedHours[2][8]);
		
		//Tests for getWorkHours
		double[] d1 = e1.getWorkHours(a1, week2);
		double[] d2 = e1.getWorkHours(a2, week2);
		double[] d3 = e1.getWorkHours(a3, week2);
		
		//Tests that correct values are given
		for(int i = 0; i<7; i++){
			assertTrue(d1[i] == 8.0);
			assertTrue(d2[i] == 6.0);
			assertTrue(d3[i] == 4.0);
		}
		
		//Tests the the total amount is correct
		double actual1 = 8.0*7;
		double actual2 = 6.0*7;
		double actual3 = 4.0*7;
		
		assertTrue(d1[7] == actual1);
		assertTrue(d2[7] == actual2);
		assertTrue(d3[7] == actual3);
		
		//Forcing bad week in getWorkHours
		Activity a = new Activity(sysApp, "ACT4", week2, week2);
		try{
			d1 = e1.getWorkHours(a, week1);
			fail();
		} catch(IllegalOperationException e){
			assertEquals(e.getErrorClass(), Employee.class);
			assertEquals(e.getMessage(), "Week "+week1.getWeek()+" is not within "+a.getType());
		}
		try{
			d2 = e1.getWorkHours(a, week3);
			fail();
		} catch(IllegalOperationException e){
			assertEquals(e.getErrorClass(), Employee.class);
			assertEquals(e.getMessage(), "Week "+week3.getWeek()+" is not within "+a.getType());
		}
	}
	
	@Test
	public void testGetWeekly() throws IllegalOperationException{
		//assigns e1 for three different activities
		e1.assignActivity(a1);
		e1.assignActivity(a2);
		e1.assignActivity(a3);
		
		//Tests for getWeeklyActivities
		ArrayList<Activity> list1 = e1.getWeeklyActivities(week1);
		ArrayList<Activity> list2 = e1.getWeeklyActivities(week2);
		ArrayList<Activity> list3 = e1.getWeeklyActivities(week3);
		assertEquals(list1.size(), 1);
		assertEquals(list2.size(), 3);
		assertEquals(list3.size(), 2);
		
		//sets workhours for weeks
		setFullWeekHours(e1, a1, 8.0, week1);
		setFullWeekHours(e1, a1, 8.0, week2);
		setFullWeekHours(e1, a1, 8.0, week3);
		setFullWeekHours(e1, a2, 6.0, week2);
		setFullWeekHours(e1, a3, 4.0, week2);
		setFullWeekHours(e1, a3, 4.0, week3);
	 	
		//Tests for getWeeklyHours
		double d1 = e1.getWeeklyHours(week1);
		double d2 = e1.getWeeklyHours(week2);
		double d3 = e1.getWeeklyHours(week3);
		
		double actual1 = 8.0*7;
		double actual2 = 8.0*7 + 6.0*7 + 4.0*7;
		double actual3 = 8.0*7 + 4.0*7;
		
		assertTrue(d1 == actual1);
		assertTrue(d2 == actual2);
		assertTrue(d3 == actual3);
	}
	
	private boolean[] setFullWeekHours(Employee e, Activity a, double hours, Week w){
		boolean[] bool = new boolean[9];
		for(int i=0; i<=8; i++){
			bool[i] = e.setHours(a, hours, w, i);
		}
		return bool;
	}
}
