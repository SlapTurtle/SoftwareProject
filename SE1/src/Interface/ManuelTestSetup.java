package Interface;

import Project.*;

public class ManuelTestSetup {
	public static void main(SysApp sys) {
		/**
		 * TEST!!!!
		 */
		try{
			//Dummy Project
			Week week1 = new Week(2016, 43);
			Week week2 = new Week(2017, 3);
			Week week3 = new Week(2017, 5);
			
			Project p1 = new Project(sys, "pro1", week1,week2,week3);
			Project p2 = new Project(sys, "pro2", week1,week2,week3);
			sys.addProject(p1);
			sys.addProject(p2);
			
			//Dummy Activities
			Activity a1 = new Activity(sys, "act1", week1, week2);
			Activity a2 = new Activity(sys, "act2", week2, week3);
			sys.addActivity(a1);
			sys.addActivity(a2);
			
			//Dummy Employees
			Employee e1 = new Employee("emp1");
			Employee e2 = new Employee("emp2");
			sys.addEmployee(e1);
			sys.addEmployee(e2);
			
			//Dummy Evaluation
			p1.addActivity(a1);
			a1.assignProject(p1);
			
			p1.addActivity(a2);
			a2.assignProject(p1);
			
			p1.addEmployee(e1);
			e1.assignProject(p1);
			
			p1.addEmployee(e2);
			e2.assignProject(p1);
			
			a1.assignEmployee(e1);
			e1.assignActivity(a1);
			
			a1.assignEmployee(e2);
			e2.assignActivity(a1);
			
			a2.assignEmployee(e1);
			e1.assignActivity(a2);
			
			a2.assignEmployee(e2);
			e2.assignActivity(a2);
			
			e1.setHours(a1, 3, week2, 2);
			e1.setHours(a1, 5, week2, 3);
			e1.setHours(a1, 2, week2, 4);
			
			e1.setHours(a2, 5, week2, 5);
			e1.setHours(a2, 5, week2, 6);
			e1.setHours(a2, 10, week2, 7);
			
			e2.setHours(a1, 7, week2, 2);
			e2.setHours(a1, 3, week2, 3);
			e2.setHours(a1, 15, week2, 4);
			
			e2.setHours(a2, 2, week2, 5);
			e2.setHours(a2, 9, week2, 6);
			e2.setHours(a2, 9, week2, 7);
			
			p1.assignManager(e1);
			p2.assignManager(e2);
	
		}
		catch(Exception e){
			System.exit(0);
		}
		/***/
	}
}
