package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import Project.*;

public class TestUseCases extends TestBasis{
	
	
		@Test
		public void TestAddEmployee(){
			//INIT:
				sysApp.login(e1);
				p1.assignManager(e1);
				
				
			/*	Main scenario
			*	Project manager tries to add an activity to a project
			*/
			
			p1.addActivity(a1);
			
			/*
			*	Alternate Scenarios
			*/

			//The employee isn’t project manager and another employee is
			sysApp.logoff();
			assertFalse(p1.addActivity(a2)); //Error msg handled in Menu.java
			assertFalse(p1.getActivityList().contains(a2));
				
			//No project manager is assigned
			assertTrue(p2.addActivity(a2));
			assertTrue(p2.getActivityList().contains(a2));
			
			//The activity is already added to the project
			assertFalse(p2.addActivity(a2));
			
			//The project doesn’t exist
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
			assertTrue(p3.getActivityList().get(index).getEmployeeList().indexOf(e2)==p3.getActivityList().get(index).getEmployeeList().lastIndexOf(e2));
		}
		
			@Test
			public void TestEmployeesOnActivity(){
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
				
				/*Main scenario
				*	Employee tries to check employees assigned to an activity
				*/
			
				assertTrue(p1.getActivityList().get(0).getEmployeeList().size()==3);
				for(Employee e : p1.getActivityList().get(0).getEmployeeList()){
					if(e.equals(e1) || e.equals(e2) || e.equals(e3)){
						assertTrue(true);
					}
				}
				
				/*
				*	Alternate Scenarios
				*/
				
				//The activity isn’t existing
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
				assertTrue(sysApp.getProjectList().contains(p4));
				/*
				 *	Alternate Scenarios
				 */
				
				//A project with the same name already exist
				assertTrue(sysApp.addProject(p5));
				assertTrue(sysApp.getProjectList().contains(p5));
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
				assertFalse(p1.assignManager(e2));
				
			}
			
			@Test
			public void TestEmployeeActivityHours() throws IllegalOperationException{ //TODO
				//INIT:
					sysApp.login(e1);
					p2.addActivity(a3);
					a3.assignEmployee(e1);
					e1.setHours(a3, 5, week2, 1); //5, 5.5 & 4 hours spent during the first 4 days of week 2.
					e1.setHours(a3, 5.5, week2, 2);
					e1.setHours(a3, 4, week2, 4);
			

				
				
				/*	Main scenario
				*	Employee tries to check hours spent on an activity
				*/
				double temp0 = 0;
				double[] temp1 = sysApp.getCurrentUser().getWorkHours(a3, week2);

				for(double x: temp1){
					temp0 += x;
				}
				
				/*
				 *	Alternate Scenarios
				 */
			
				//The employee isn’t assigned to the activity
				
				
			}
			
			
			@Test
			public void TestAvailableEmployees() throws IllegalOperationException{
				//INIT: 
				
			
				/*	Main scenario
				 *	Employee tries to access available employees for a given week.
				 */
				
				//Size is expected to be 3 since there is 3 employees added in TestBasis and none of them 
				// has any hours assigned yet
				assertTrue(sysApp.getAvailableEmployees(week1).size()==3);
	
			
				/*
				 *	Alternate Scenarios
				 */
		
				//The week requested is before the current week
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