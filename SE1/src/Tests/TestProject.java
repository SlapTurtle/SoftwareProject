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

		assertEquals(p1.getName(), "Project1"); //Checks that project is created and that budget is set to 0.
		
		//Test addEmployee
		Employee e1 = new Employee("EMPL1"); //Tests that employees can be added, and that dublicates are not created.
		assertTrue(p1.addEmployee(e1));
		assertTrue(p1.addEmployee(e2));
		assertFalse(p1.addEmployee(e2));
		
		
		//Test addActiviy
		assertTrue(p1.addActivity(a1));
		assertTrue(p1.addActivity(a2));
		assertFalse(p1.addActivity(a2));
		
		
		//Test UniqueID
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
		
		//Assign project manager
		/*sysApp.login(sysApp.employeeByInitials("EMPL1"));
		//p1.assignManager(e1);
		System.out.println(p1.assignManager(sysApp.employeeByInitials("EMPL1")));
		System.out.println(p1.projectManager.getInitials());
		assertTrue(p1.projectManager.getInitials().equals(e1.getInitials()));
		//Tests that the right employee became project manager
		//assertFalse(makeCastle.projectManagers.get(0).equals(brianAnderson));
		//assertTrue(makeCastle.projectManagers.get(0).equals(timHansson));
		*/
		
		
		
		
		
	}
	/*public void testDublicates() throws Exception {
		Project makeCastle = new Project("Build a Castle", new Week(2017, 5), new Week(2017, 6), new Week(2017, 7));
		Project makeCastle = new Project("Build a Castle", new Week(2017, 5), new Week(2017, 6), new Week(2017, 7));
		
		Employee timHansson = new Employee("TIHAN");
		sysApp.projectByID("ID1").addEmployee(timHansson);
		assertTrue(makeCastle.findEmployee(timHansson));
		
		//Test UniqueID
		assertEquals(makeCastle.checkUniqueID(),"ID1");
		assertEquals(makeCastle.checkUniqueID(),"ID2");
	}
*/
	
}
