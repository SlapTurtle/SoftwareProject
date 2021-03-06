package Tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import Project.*;

public class TestUseCases extends TestBasis{
	@Override
	public void setup(){
		super.setup();
		sysApp.addEmployee(e1);
		sysApp.addEmployee(e2);
		sysApp.addEmployee(e3);
	}
	
		@Test
		public void TestAddEmployee(){
			//INIT:
				sysApp.login(e1);
				p1.assignManager(e1);
				
				
			/*	Main scenario
			*	Project manager tries to add an activity to a project
			*/
			
			assertTrue(p1.addActivity(a1));
			
			/*
			*	Alternate Scenarios
			*/

			//The employee isn�t project manager and another employee is
			sysApp.logoff();
			sysApp.login(e2);
			assertFalse(p1.addActivity(a2)); //Error msg handled in Menu.java
			assertFalse(p1.getActivityList().contains(a2));
				
			//No project manager is assigned
			assertTrue(p2.addActivity(a2));
			assertTrue(p2.getActivityList().contains(a2));
			
			//The activity is already added to the project
			assertFalse(p2.addActivity(a2));
			
			//The project doesn�t exist
			//Trying to access an uncreated project is handled by the Menu class
			

			
		}
		@Test
		public void TestAssignEmployee(){
			//INIT:
				sysApp.login(e1);
				p3.assignManager(e1);
			
			/*	Main scenario
			*	Project manager tries to assign an employee to an activity
			*/
		
			assertTrue(p3.addActivity(a3));
			assertTrue(p3.getManager().equals(e1));
			int index = p3.getActivityList().indexOf(a3);
			//Activity has to be added on both ends, this is handled in Menu.java
			assertTrue(p3.getActivityList().get(index).assignEmployee(e2));
			assertTrue(e2.assignActivity(a2));
			//Final assertion
			assertTrue(p3.getActivityList().get(index).getEmployeeList().contains(e2) && e2.getActivityList().contains(a2));
			
			
			/*
			*	Alternate Scenarios
			*/
			
			//The employee is already on the activity
			assertFalse(p3.getActivityList().get(index).assignEmployee(e2)); //Tries to add employee again
			//Checks if the first if e2 occurs several times
			assertEquals(p3.getActivityList().get(index).getEmployeeList().indexOf(e2),p3.getActivityList().get(index).getEmployeeList().lastIndexOf(e2));
		}
		
			@Test
			public void TestEmployeesOnActivity() throws IllegalOperationException{
				//INIT:
					sysApp.login(e1);
					//Creating relations between project, activity and employees. Handled by Menu class
					p1.addActivity(a1);
					a1.assignProject(p1);
					a1.assignEmployee(e1);
					a1.assignEmployee(e2);
					a1.assignEmployee(e3);
					p1.addEmployee(e1);
					p1.addEmployee(e2);
					p1.addEmployee(e3);
					e1.assignActivity(a1);
					e2.assignActivity(a1);
					e3.assignActivity(a1);
					e1.setHours(a1, 5, week1, 1);
					e2.setHours(a1, 10, week1, 2);
					e3.setHours(a1, 15, week1, 3);
				
				/*Main scenario
				*	Employee tries to check employees assigned to an activity
				*/
				
				double[] d = new double[a1.getEmployeeList().size()];
				for(Employee e : a1.getEmployeeList()){
					d[a1.getEmployeeList().indexOf(e)] = e.getWorkHours(a1, week1)[7];
				}
				assertTrue(d[0] == 15);
				assertTrue(d[1] == 10);
				assertTrue(d[2] == 5);
				
				
				/*
				*	Alternate Scenarios
				*/
				
				//The activity isn�t existing
				//Scenario handled by Menu class
			}
			
			@Test
			public void TestAddProject(){
				//INIT:
					Project p4 = new Project(sysApp, "Project4", week1, week2, week3);//Input for adding project handled by Menu class
					Project p5 = new Project(sysApp, "Project4", week1, week2, week3);//Project with already existing name.
					sysApp.login(e1);
				
				/*	Main scenario
				*	Employee tries to check employees assigned to an activity
				*/	
				
				assertTrue(sysApp.addProject(p4));
				assertEquals(p4.checkUniqueID(), "ID4");
				assertTrue(sysApp.getProjectList().contains(p4));
				/*
				 *	Alternate Scenarios
				 */
				
				//A project with the same name already exist
				assertFalse(sysApp.addProject(p5));
				assertFalse(sysApp.getProjectList().contains(p5));
			}
			
			
			@Test
			public void TestAssignProjectManager(){
				//INIT:
					sysApp.login(e1);
					
				
				/*	Main scenario
				*	Employee assigns a co-worker as project manager
				*/
				
				p1.assignManager(e2);
				assertEquals(p1.getManager(),e2);
			
				
				/*
				 *	Alternate Scenarios
				 */
				
				//The project already has a project manager
				assertFalse(p1.assignManager(e1));
				
				//The e2 already is the project manager
				assertFalse(p1.assignManager(e2));
				
			}
			
			@Test
			public void TestEmployeeActivityHours() throws IllegalOperationException{ //TODO
				//INIT:
					sysApp.login(e1);
					p2.addActivity(a3);
					a3.assignEmployee(e1);
					e1.assignActivity(a3);
					e1.setHours(a3, 5, week2, 1); //5, 5.5 & 4 hours spent during the first 4 days of week 2.
					e1.setHours(a3, 5.5, week2, 2);
					e1.setHours(a3, 4, week2, 4);
			

				
				
				/*	Main scenario
				*	Employee tries to check hours spent on an activity
				*/
				double d = e1.getWorkHours(a3, week2)[7];
				assertTrue(d == 5.0+5.5+4.0);
				/*
				 *	Alternate Scenarios
				 */
				
			
				//The employee isn�t assigned to the activity
				try{
					e1.getWorkHours(a2, week2);
					fail();
				} catch (Exception e){
					//test passes
				}
				
			}
			
			
			@Test
			public void TestAvailableEmployees() throws IllegalOperationException{
				//INIT: 
				
			
				/*	Main scenario
				 *	Employee tries to access available employees for a given week.
				 */
				
				//Size is expected to be 3 since there are 3 employees added during the Setup and none of them 
				// has been assigned to any activities yet
				assertEquals(sysApp.getAvailableEmployees(week1).size(), 3);
	
			
				/*
				 *	Alternate Scenarios
				 */
		
				//The week requested is before the current week
				Calendar cal = new GregorianCalendar().getInstance();
				week = week1.getWeek()+4;
				updateDateServer();
				
				assertEquals(sysApp.getAvailableEmployees(week1).size(), 0);
				
				
				//Scenario handled in Menu class
			
				
			}
			
			
			@Test
			public void TestSetWorkhours(){
				//INIT: 
					e1.assignActivity(a1);

				/*	Main scenario
				*	Employee tries to set amount of hours worked
				*/
				
				e1.setHours(a1, 6.5, week2, 1);
				assertTrue(e1.getWeeklyHours(week2)==6.5);
				e1.setHours(a1, 6, week2, 1);
				assertTrue(e1.getWeeklyHours(week2)==6.0);
				
				/*
				 *	Alternate Scenarios
				 */
			
				//The employee sets minus hours
				e1.setHours(a1, -6, week2, 2);
				assertFalse(e1.getWeeklyHours(week2)==0.0);
				assertTrue(e1.getWeeklyHours(week2)==6.0);
				
				
				//The employee set hours more then 24 hours
				e1.setHours(a1, 25.0, week2, 3);
				assertFalse(e1.getWeeklyHours(week2)==31.0);
				assertTrue(e1.getWeeklyHours(week2)==6.0);
			}
}