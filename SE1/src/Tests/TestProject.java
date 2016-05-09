package Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import Project.*;

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
		
		//Test assignManager
		assertFalse(p2.assignManager(e1));
		
		//Test addActiviy
		assertTrue(p1.addActivity(a1));
		assertTrue(p1.addActivity(a2));
		assertFalse(p1.addActivity(a2));
		
		 
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
		
		
		//Test setName
		p1.setName("ProjectTest");
		assertEquals("ProjectTest", p1.getName());
	}
	
	@Test
	public void testWeek(){
		Week week4 = new Week(year, week*4);
		
		assertEquals(p1.getStartWeek(),week1);
		assertEquals(p1.getEndWeek(),week2);
		assertEquals(p1.getDeadline(),week3);
		
		p1.setDeadline(week4);
		assertEquals(p1.getDeadline(),week4);
		
		p1.setEndWeek(week3);
		assertEquals(p1.getEndWeek(),week3);
		
		p1.setStartWeek(week1);
		assertEquals(p1.getStartWeek(), week1);
	}
	
	@Test
	public void testBudget(){
		//adds activity, project and employee to sysApp
		sysApp.addActivity(a1);
		sysApp.addActivity(a2);	
		sysApp.addProject(p1);
		sysApp.addEmployee(e1);
		
		//assigns to each other
		p1.addActivity(a1);
		p1.addActivity(a2);
		a1.assignProject(p1);
		a2.assignProject(p2);
		e1.assignActivity(a1);
		e1.assignActivity(a2);
		a1.assignEmployee(e1);
		a2.assignEmployee(e1);
		
		//logs in
		sysApp.login(e1);
		p1.addEmployee(e1);
		p1.assignManager(e1);

		assertTrue(p1.getSpentBudget()[0]==0.0);	//spent budget == 0
		assertTrue(p1.getSpentBudget()[1]==0.0);
		assertTrue(p1.getSpentBudget()[2]==0.0);
		assertEquals(p1.getSpentBudget().length, 3);
		
		//spends hours on a1
		e1.setHours(a1, 20.0, week1, 2);
		assertTrue(p1.getSpentBudget()[0]==20.0);	//spent budget == 20
		assertTrue(p1.getSpentBudget()[1]==20.0);	//20 hours on a1
		assertTrue(p1.getSpentBudget()[2]==0.0);	//0 hours on a2
		
		//spends hours on a2
		e1.setHours(a2, 20.0, week2, 3);
		assertTrue(p1.getSpentBudget()[0]==40.0);	//spent budget == 40
		assertTrue(p1.getSpentBudget()[1]==20.0);	//20 hours on a1
		assertTrue(p1.getSpentBudget()[2]==20.0);	//20 hours on a2
		
		//corrects spent hours on a2
		e1.setHours(a2, 10.0, week2, 3);
		assertTrue(p1.getSpentBudget()[0]==30.0);	//spent budget == 30
		assertTrue(p1.getSpentBudget()[1]==20.0);	//20 hours on a1
		assertTrue(p1.getSpentBudget()[2]==10.0);	//10 hours on a2
	}
	
}