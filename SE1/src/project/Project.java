package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Project {
	
	//Parameters
	private String name;
	private String projectID;
	private SysApp sysApp;
	public Week startWeek;
	public Week endWeek;
	public Week deadline;
	
	//methods
	public List<Employee> employeeList;
	public Employee projectManager;
	public List<Activity> activityList;
	public List<double[]> timeSpentList;
	public String type;
	public double budget;
	public HashMap<Week, String> reports = new HashMap<Week, String>();
	public String reportComment;
	
	
	
	public Project(SysApp sys, String name, Week sW,Week eW,Week dL){
		this.sysApp = sys;
		this.name = name;
		this.startWeek = sW;
		this.endWeek = eW;
		this.deadline = dL;
		this.projectID = setUniqueID();
		this.employeeList = new ArrayList<Employee>();
		this.activityList = new ArrayList<Activity>();
		this.budget = 0;
		this.projectManager = null;
	}

	public boolean assignManager(Employee employee){
		if(projectManager==null){
			this.projectManager=employee;
			return true;
		}
		return false;
	}
	
	public boolean addEmployee(Employee employee){
		if(!employeeList.contains(employee) && employee != null){
		return employeeList.add(employee);
		
		}
		return false;
	}
	
	public boolean addActivity(Activity activity){
		if(!activityList.contains(activity) && activity != null){
			return activityList.add(activity);
		}
		return false;
	}
	
	public boolean findEmployee(Employee x){
		return employeeList.contains(x);
		
	}
	
	//ID counter implemented in SysApp
	private String setUniqueID() {
		String newID = "ID" + sysApp.getPcount();
		return newID;
	}
	
	public String checkUniqueID() {
		return this.projectID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setReportComment(String comment,Week w){
		this.reports.put(w, comment);
	}
 
	public String getWeeklyReport(Week w){
		return this.reports.get(w);
	}
	 
	public double getTotalProjectBudget(){
		return this.budget;
	 
	}
	public double getActivityDiversion(Employee e, Activity a, Week w) throws IllegalOperationException{
		return e.getWorkHours(a, w);
		 
	}
	//Positive integer values adds to budget, negative removes.
	public void manageExpense(double value){
		 this.budget = this.budget + value;
	}
	 
	 
	

}
