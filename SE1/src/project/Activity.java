package project;

import java.util.List;
public class Activity {
	
	public Activity(String name){
		
	}
	private String name;
	private String activityID;
	private SysApp sysApp;
	public Week startWeek; 
	public Week endWeek;	
	public List<Employee> employeelist;
	public List<Project> projectlist;
	public double hourBudget;
	public double hoursSpent;
	public String type;
	
	// -------- Sets the activity ID and planed work weeks ----
	
	public Activity(SysApp sysApp, String name, Week startWeek, Week endWeek) {
		this.sysApp = sysApp;
		this.type = name.toUpperCase();
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.activityID = setUniqueID();
	}
	
	private String setUniqueID() {
		String newID = "ID" + sysApp.getAcount();
		return newID;
	}
	
	public String checkUniqueID() {
		return this.activityID;
	}
	
	//Makes it possible to extend the end week.
	public void setEndWeek(Week endweek){
		if (endweek != this.startWeek && endweek != this.endWeek){
			this.endWeek = endweek;
		}
	}
	
	public Week getEndWeek(){
		return this.endWeek;
	}
	
	public Week getStartWeek(){
		return this.startWeek;
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

	public String getName() {
		return this.name;
	}
	
	//If needed add more methods
}
