package project;

import java.util.List;

public class Activity {
	
	public Activity(String name){
		
	}
	private String ID;
	//private SysApp sysapp;
	public Week startWeek; 
	public Week endWeek;	
	public List<Employee> employeelist;
	public List<Project> projectlist;
	public double hourBudget;
	public double hoursSpent;
	public String type;
	
	// -------- Sets the activity ID and planed work weeks ----
	
	public Activity(String ID, Week startWeek, Week endWeek) {
		this.ID = ID;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
	}
	
	public String getID() {
		return this.ID;
	}
	
	//Makes it possible to extend the end week.
	public void setEndWeek(Week endweek){
		if (endweek != this.startWeek && endweek != this.endWeek){
			this.endWeek = endweek;
		}
	}
	
	public boolean assignEmployee(Employee employee) {
		
		if (employee!=null && !employeelist.contains(employee)) {
			employeelist.add(employee);
			return true;
		}
		return false;
	}

	
	public boolean assignProject(Project project) {

		if (project!=null && !projectlist.contains(project)) {
			projectlist.add(project);
			return true;
		}
		return false;
	}

	public double getEmployeeHours(Employee employee, Week weeknumber) {
		return employee.getWeeklyHours(weeknumber);
	}
	
	public List<Employee> getEmployeeOnAcitivy(){
		return this.employeelist;
	}
	
	public List<Project> getProject(){
		return this.projectlist;
	}
	
	//If needed add more methods
}