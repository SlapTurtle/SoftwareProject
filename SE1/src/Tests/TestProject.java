package Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import project.Employee;
import project.Project;
import project.Week;

public class TestProject extends TestBasis{
	
	@Test 
	public void Project() throws Exception{ //TODO Handle Exception on Week
		//Test add project
		Project makeCastle = new Project(sysApp, "Build a Castle", new Week(2017, 5), new Week(2017, 6), new Week(2017, 7));
		Project makeFort = new Project(sysApp, "Build a Castle", new Week(2017, 5), new Week(2017, 6), new Week(2017, 7));
		assertTrue(makeCastle.budget==0); //Checks that project is created and that budget is set to 0.
		
		//Test addEmployee - UseCase 4
		Employee timHansson = new Employee("TIHAN");
		makeCastle.addEmployee(timHansson);
		assertTrue(makeCastle.findEmployee(timHansson));
		
		//Test addActiviy
		//Activity gatherStones();
		
		//Test UniqueID
		assertEquals(makeCastle.checkUniqueID(),"ID4"); //3 project created earlier
		assertEquals(makeFort.checkUniqueID(),"ID5");   //4 project created earlier
		
		
		//Assign project manager - Case 5
		//Brian is added to the project - after logging in he assigns Tim to project manager on the Make Castle Project.
		Employee brianAnderson = new Employee("BRIAN");
		makeCastle.addEmployee(sysApp.employeeByInitials("BRIAN"));
		sysApp.login(sysApp.employeeByInitials("BRIAN")); 
		makeCastle.assignManager(timHansson);
		//Tests that the right employee became project manager
		assertFalse(makeCastle.projectManagers.get(0).equals(brianAnderson));
		assertTrue(makeCastle.projectManagers.get(0).equals(timHansson));
		
		
		
		
		
		
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
	

	@Test
	public void testID(){
		
	}
	
	@Test
	public void testInit(){
		assertTrue(true);
		Integer x = 1;
		
		assertTrue(x.equals((Integer) 1));
	}
	@Test
	public void testFalse(){
		assertTrue(true);
	}
}
