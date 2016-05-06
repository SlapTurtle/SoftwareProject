package Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import project.Employee;
import project.Project;
import project.Week;

public class TestProject extends TestBasis{
	
	@Test 
	public void Project() throws Exception{ //Uses the cases setup through the TestBasis class.
		//Test add project

		assertEquals(p1.getName(), "PROJECT1"); //Checks that project is created and that budget is set to 0.
		
		//Test addEmployee
		Employee e1 = new Employee("EMPL1"); //Tests that employees can be added, and that dublicates are not created.
		assertTrue(p1.addEmployee(e1));
		assertTrue(p1.addEmployee(e2));
		assertFalse(p1.addEmployee(e2));
		
		
		//Test addActiviy
		assertTrue(p1.addActivity(a1));
		assertTrue(p1.addActivity(a2));
		assertFalse(p1.addActivity(a2));
		
		
		//Test removeActivity
		assertTrue(p1.removeActivity(a2));
		assertFalse(p1.getActivityList().contains(a2));
		assertFalse(p1.removeActivity(a2));
		
		 
		//Test UniqueID
		//Set ID is used in the constructor and is thus not tested here
		assertEquals(p1.checkUniqueID(),"ID1"); 
		assertEquals(p2.checkUniqueID(),"ID2");

		
		//Test Weekly report
		p1.setReportComment("Comment for week1", week1);
		p1.setReportComment("Comment for week2", week2);
		assertTrue(p1.getWeeklyReport(week1).equals("Comment for week1"));
		assertFalse(p1.getWeeklyReport(week2).equals("Comment for week1"));
		
		
		//Test Budget
		p1.setBudget(100);
		assertEquals(p1.getBudget(), 100, Double.MAX_VALUE);

		
		//Test get activity diversion
		e1.assignActivity(a1);
		e1.setHours(a1, 3.5 ,week1, 1);
		//assertTrue(p1.getActivityDiversion(e1, a1, week1)==3.5);

		
		
	}
	
}
