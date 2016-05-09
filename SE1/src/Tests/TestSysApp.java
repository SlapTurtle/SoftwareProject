package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Project.*;

public class TestSysApp extends TestBasis{
	
	@Test
	public void testConstructor() {
		sysApp = new SysApp(false);
		assertEquals(sysApp.getEmployeeList(), new ArrayList<Employee>());
		assertEquals(sysApp.getProjectList(),new ArrayList<Project>());
		assertEquals(sysApp.getActivityList(), new ArrayList<Activity>());
		assertEquals(sysApp.getDateServer().getClass(), new DateServer().getClass());
		assertEquals(sysApp.getSystemLog().getName(), "systemLog");
		assertEquals(sysApp.getCurrentUser(), null);
		assertEquals(sysApp.getAcount(), 1); //0 when called
		assertEquals(sysApp.getAcount(), 2);
		assertEquals(sysApp.getPcount(), 1); //0 when called
		assertEquals(sysApp.getPcount(), 2);
	}
	
	@Test
	public void testLogin(){
		//tests login status
		assertFalse(sysApp.loggedIn()); //Initially not logged in
		assertFalse(sysApp.login(e1));	//only added employees may login
		
		//adds employee and logs in
		sysApp.addEmployee(e1);	
		assertTrue(sysApp.login(e1));
		assertEquals(sysApp.getCurrentUser(), e1);
		
		//already logged in
		sysApp.addEmployee(e2);
		assertFalse(sysApp.login(e2));
		assertFalse(sysApp.login(e2.getInitials()));
		assertNotEquals(sysApp.getCurrentUser(), e2);
		
		//log off
		assertTrue(sysApp.logoff());
		assertFalse(sysApp.logoff());	//already logged off
		
		//Attempt at logging in as non-existing user
		assertFalse(sysApp.login("NONE"));
		
		//another user logs in
		assertTrue(sysApp.login(e2.getInitials()));
		assertEquals(sysApp.getCurrentUser(), e2);
	}
	
	@Test
	public void testAddEmployee() {
		//adds employee e1
		assertTrue(sysApp.addEmployee(e1));
		assertTrue(sysApp.getEmployeeList().contains(e1));
		assertEquals(e1, sysApp.employeeByInitials(e1.getInitials()));
		assertEquals(sysApp.getEmployeeList().size(), 1);
		
		//adds employee e2
		assertTrue(sysApp.addEmployee(e2));
		assertEquals(e2, sysApp.employeeByInitials(e2.getInitials()));
		assertTrue(sysApp.getEmployeeList().contains(e2));
		assertEquals(sysApp.getEmployeeList().size(), 2);
		
		//adds employee "BRAN" / e3  by initials
		assertTrue(sysApp.addEmployee("BRAN"));
		e3 = sysApp.employeeByInitials("BRAN");
		assertTrue(sysApp.getEmployeeList().contains(e3));
		assertEquals(sysApp.getEmployeeList().size(), 3);
		
		//tests that no double assigning can happen.
		assertFalse(sysApp.addEmployee(e1));
		assertFalse(sysApp.addEmployee(e2));
		assertFalse(sysApp.addEmployee("BRAN"));
	}
	
	@Test
	public void RemoveEmployee(){
		//adds four employees
		sysApp.addEmployee(e1);
		sysApp.addEmployee(e2);
		sysApp.addEmployee(e3);
		sysApp.addEmployee("TEST");
		assertEquals(sysApp.getEmployeeList().size(), 4);
		
		// * added projects + added Activities checked by Menu-Class (UI).
		//assigns e1 to some activities
		e1.assignActivity(a1); a1.assignEmployee(e1);
		e1.assignActivity(a2); a2.assignEmployee(e1);
		assertEquals(e1.getActivityList().size(), 2);
		assertEquals(a1.getEmployeeList().size(), 1);
		assertEquals(a2.getEmployeeList().size(), 1);
		
		//assigns e2 to some projects
		e2.assignProject(p1); p1.addEmployee(e2);
		e2.assignProject(p2); p2.addEmployee(e2);
		assertEquals(e2.getProjectList().size(), 2);
		assertEquals(p1.getEmployeeList().size(), 1);
		assertEquals(p2.getEmployeeList().size(), 1);
		
		//assigns e3 to both types
		e3.assignActivity(a3); a3.assignEmployee(e3);
		e3.assignProject(p3);  p3.addEmployee(e3);
		assertEquals(a3.getEmployeeList().size(), 1);
		assertEquals(p3.getEmployeeList().size(), 1);
		assertEquals(e3.getActivityList().size(), 1);
		assertEquals(e3.getProjectList().size(), 1);
		
		//removes employee e1
		assertTrue(sysApp.removeEmployee(e1));
		assertFalse(sysApp.getEmployeeList().contains(e1));
		assertEquals(null, sysApp.employeeByInitials(e1.getInitials()));
		assertEquals(sysApp.getEmployeeList().size(), 3);
		assertEquals(a1.getEmployeeList().size(), 0);
		assertEquals(a2.getEmployeeList().size(), 0);
		
		//removes employee e2
		assertTrue(sysApp.removeEmployee(e2));
		assertFalse(sysApp.getEmployeeList().contains(e2));
		assertEquals(null, sysApp.employeeByInitials(e2.getInitials()));
		assertEquals(p1.getEmployeeList().size(), 0);
		assertEquals(p2.getEmployeeList().size(), 0);
		
		//removes Employee e3
		assertTrue(sysApp.removeEmployee(e3));
		assertFalse(sysApp.getEmployeeList().contains(e3));
		assertEquals(null, sysApp.employeeByInitials(e3.getInitials()));
		assertEquals(sysApp.getEmployeeList().size(), 1);
		assertEquals(a3.getEmployeeList().size(), 0);
		assertEquals(p3.getEmployeeList().size(), 0);
		assertEquals(a3.getEmployeeList().size(), 0);
		assertEquals(p3.getEmployeeList().size(), 0);
		
		//attempts to remove the last and only employee
		assertFalse(sysApp.removeEmployee(sysApp.employeeByInitials("TEST")));
	}
	
	@Test
	public void testGetAvailableEmployee() throws IllegalOperationException{
		//Adds 50 employees
		for(int i=0; i<50; i++){
			sysApp.addEmployee("EMP"+i);
		}
		assertEquals(sysApp.getAvailableEmployees(week1).size(),50);
		assertEquals(sysApp.getAvailableEmployees(week2).size(),50);
		assertEquals(sysApp.getAvailableEmployees(week3).size(),50);
		
		//Adds 20 Activities
		for(int i=0; i<20; i++){
			sysApp.addActivity(new Activity(sysApp, "WE1"+i, week1, week1));
			sysApp.addActivity(new Activity(sysApp, "WE2"+i, week2, week2));
			sysApp.addActivity(new Activity(sysApp, "WE3"+i, week3, week3));
		}
		
		//Assigns 25 employees to all WE1 activities
		assertEquals(sysApp.getAvailableEmployees(week1).size(),50);
		for(int i=0; i<25; i++){
			Employee e1 = sysApp.employeeByInitials("EMP"+i);
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE1"+j);
				e1.assignActivity(a);
				a.assignEmployee(e1);
			}
			//Checks that employee is now not on list
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE1"+j);
				assertEquals(sysApp.getAvailableEmployees(a, week1).size(), 50-(i+1));
			}
		}
		assertEquals(sysApp.getAvailableEmployees(week1).size(),25);
		assertEquals(sysApp.getAvailableEmployees(week2).size(),50);
		assertEquals(sysApp.getAvailableEmployees(week3).size(),50);
		
		//Assigns the other 25 employees to all WE2 activities.
		for(int i=25; i<50; i++){
			Employee e1 = sysApp.employeeByInitials("EMP"+i);
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE2"+j);
				e1.assignActivity(a);
				a.assignEmployee(e1);
			}
			//Checks that employee is now not on list
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE2"+j);
				assertEquals(sysApp.getAvailableEmployees(a, week2).size(), 50-((i-25)+1));
			}
		}
		assertEquals(sysApp.getAvailableEmployees(week1).size(),25);
		assertEquals(sysApp.getAvailableEmployees(week2).size(),25);
		assertEquals(sysApp.getAvailableEmployees(week3).size(),50);
		
		//Assigns all employees to all WE3 activities
		for(int i=0; i<50; i++){
			Employee e1 = sysApp.employeeByInitials("EMP"+i);
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE3"+j);
				e1.assignActivity(a);
				a.assignEmployee(e1);
			}
			//Checks that employee is now not on list
			for(int j=0; j<20; j++){
				Activity a = sysApp.activityByName("WE3"+j);
				assertEquals(sysApp.getAvailableEmployees(a, week3).size(), 50-(i+1));
			}
		}
		assertEquals(sysApp.getAvailableEmployees(week1).size(),25);
		assertEquals(sysApp.getAvailableEmployees(week2).size(),25);
		assertEquals(sysApp.getAvailableEmployees(week3).size(),0);
		
	}
	
	@Test
	public void testAddProject() {
		//adds Project p1
		assertTrue(sysApp.addProject(p1));
		assertEquals(p1.checkUniqueID(), "ID1");
		assertEquals(p1, sysApp.projectByName(p1.getName()));
		assertEquals(p1, sysApp.projectByID("ID1"));
		assertTrue(sysApp.getProjectList().contains(p1));
		assertEquals(sysApp.getProjectList().size(), 1);
		
		//adds Project p2
		assertTrue(sysApp.addProject(p2));
		assertEquals(p2.checkUniqueID(), "ID2");
		assertEquals(p2, sysApp.projectByName(p2.getName()));
		assertEquals(p2, sysApp.projectByID("ID2"));
		assertTrue(sysApp.getProjectList().contains(p2));
		assertEquals(sysApp.getProjectList().size(), 2);
		
		//tests that no double assigning can happen
		assertFalse(sysApp.addProject(p1));
		assertFalse(sysApp.addProject(p2));
		p3.setName(p2.getName());
		assertFalse(sysApp.addProject(p3));
	}
	
	@Test
	public void testRemoveProject(){
		//add three projects
		sysApp.addProject(p1);
		sysApp.addProject(p2);
		sysApp.addProject(p3);
		
		//adds some employees to p1
		e1.assignProject(p1); p1.addEmployee(e1);
		e2.assignProject(p1); p1.addEmployee(e2);
		assertEquals(p1.getEmployeeList().size(), 2);
		assertEquals(e1.getProjectList().size(), 1);
		assertEquals(e2.getProjectList().size(), 1);
		
		//adds some activities to p2
		a1.assignProject(p2); p2.addActivity(a1);
		a2.assignProject(p2); p2.addActivity(a2);
		assertEquals(p2.getActivityList().size(), 2);
		assertEquals(e1.getProjectList().size(), 1);
		assertEquals(e2.getProjectList().size(), 1);
		
		//adds 1 of each to p3
		e3.assignProject(p3); p3.addEmployee(e3);
		a3.assignProject(p3); p3.addActivity(a3);
		assertEquals(p3.getEmployeeList().size(), 1);
		assertEquals(p3.getActivityList().size(), 1);
		assertEquals(e3.getProjectList().size(), 1);
		assertEquals(a3.getProjectList().size(), 1);
		
		//removes p1
		assertTrue(sysApp.removeProject(p1));
		assertEquals(null, sysApp.projectByName(p1.getName()));
		assertEquals(null, sysApp.projectByID("ID1"));
		assertFalse(sysApp.getProjectList().contains(p1));
		assertEquals(e1.getProjectList().size(), 0);
		assertEquals(e2.getProjectList().size(), 0);
		
		//removes p2
		assertTrue(sysApp.removeProject(p2));
		assertEquals(null, sysApp.projectByName(p2.getName()));
		assertEquals(null, sysApp.projectByID("ID2"));
		assertFalse(sysApp.getProjectList().contains(p2));
		assertEquals(a1.getProjectList().size(), 0);
		assertEquals(a2.getProjectList().size(), 0);
		
		//removes p3
		assertTrue(sysApp.removeProject(p3));
		assertEquals(null, sysApp.projectByName(p3.getName()));
		assertEquals(null, sysApp.projectByID("ID3"));
		assertFalse(sysApp.getProjectList().contains(p3));
		assertEquals(e3.getProjectList().size(), 0);
		assertEquals(a3.getProjectList().size(), 0);
	}
	
	@Test
	public void testAddActivity() {
		//adds Activity a1
		assertTrue(sysApp.addActivity(a1));
		assertEquals(a1.getUniqueID(), "ID1");
		assertEquals(a1, sysApp.activityByName(a1.getType()));
		assertEquals(a1, sysApp.activityByID("ID1"));
		assertTrue(sysApp.getActivityList().contains(a1));
		assertEquals(sysApp.getActivityList().size(), 1);
		
		//adds Activity a2
		assertTrue(sysApp.addActivity(a2));
		assertEquals(a2.getUniqueID(), "ID2");
		assertEquals(a2, sysApp.activityByName(a2.getType()));
		assertEquals(a2, sysApp.activityByID("ID2"));
		assertTrue(sysApp.getActivityList().contains(a2));
		assertEquals(sysApp.getActivityList().size(), 2);
		
		//tests that no double assigning can happen
		assertFalse(sysApp.addActivity(a1));
		assertFalse(sysApp.addActivity(a2));
		a3.setType(a2.getType());
		assertFalse(sysApp.addActivity(a3));
		
	}
	
	@Test
	public void testRemoveActivity() {
		//add three activities
		sysApp.addActivity(a1);
		sysApp.addActivity(a2);
		sysApp.addActivity(a3);
		
		//adds some employees to a1
		e1.assignActivity(a1); a1.assignEmployee(e1);
		e2.assignActivity(a1); a1.assignEmployee(e2);
		assertEquals(a1.getEmployeeList().size(), 2);
		assertEquals(e1.getActivityList().size(), 1);
		assertEquals(e2.getActivityList().size(), 1);
		
		//adds some activities to a2
		p1.addActivity(a2); a2.assignProject(p1);
		p2.addActivity(a2); a2.assignProject(p2);
		assertEquals(a2.getProjectList().size(), 2);
		assertEquals(e1.getActivityList().size(), 1);
		assertEquals(e2.getActivityList().size(), 1);
		
		//adds 1 of each to a3
		e3.assignActivity(a3); a3.assignEmployee(e3);
		p3.addActivity(a3); a3.assignProject(p3);
		assertEquals(a3.getEmployeeList().size(), 1);
		assertEquals(a3.getProjectList().size(), 1);
		assertEquals(e3.getActivityList().size(), 1);
		assertEquals(a3.getProjectList().size(), 1);
		
		//removes a1
		assertTrue(sysApp.removeActivity(a1));
		assertEquals(null, sysApp.activityByName(a1.getType()));
		assertEquals(null, sysApp.activityByID("ID1"));
		assertFalse(sysApp.getActivityList().contains(a1));
		assertEquals(e1.getActivityList().size(), 0);
		assertEquals(e2.getActivityList().size(), 0);
		
		//removes a2
		assertTrue(sysApp.removeActivity(a2));
		assertEquals(null, sysApp.activityByName(a2.getType()));
		assertEquals(null, sysApp.activityByID("ID2"));
		assertFalse(sysApp.getActivityList().contains(a2));
		assertEquals(p1.getActivityList().size(), 0);
		assertEquals(p2.getActivityList().size(), 0);
		
		//removes a3
		assertTrue(sysApp.removeActivity(a3));
		assertEquals(null, sysApp.activityByName(a3.getType()));
		assertEquals(null, sysApp.activityByID("ID3"));
		assertFalse(sysApp.getActivityList().contains(a3));
		assertEquals(e3.getActivityList().size(), 0);
		assertEquals(p3.getActivityList().size(), 0);
	}
	
	@Test
	public void testLog() throws IOException{
		Employee e = new Employee("ADMIN");
		sysApp.addEmployee(e);
		sysApp.login(e);
		
		//Makes input/expected output for three lines
		String in1 = "TEST1";
		String in2 = "TEST2";
		String in3 = "TEST3";
		
		//writes to log
		sysApp.writeToLog(in1);
		sysApp.writeToLog(in2);
		sysApp.writeToLog(in3);
		
		//Reads file
		FileReader reader = new FileReader(sysApp.getSystemLog());
        BufferedReader bufferedReader = new BufferedReader(reader);
        
        String[] str = new String[3*2];
        String line;
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
        	str[i] = line;
        	i++;
        }
        reader.close();
		
		//compares
		assertEquals("INFO: "+in1+" - \"ADMIN\"", str[1]);
		assertEquals("INFO: "+in2+" - \"ADMIN\"", str[3]);
		assertEquals("INFO: "+in3+" - \"ADMIN\"", str[5]);
	}
}
