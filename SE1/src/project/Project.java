package project;

import java.util.List;

public class Project {
	
	//Parameters
	private String ID;
	//private systemApp sysapp;
	public Week startWeek;
	public Week endWeek;
	public Week deadline;
	
	//methods
	public List<Employee> employeeList;
	public Employee PM;
	public List<Activity> activityList;
	public List<double[]> timeSpentList;
	public String type;
	
	
	public Project(String ID, Week sW,Week eW,Week dL){
		this.ID = ID;
		this.startWeek = sW;
		this.endWeek = eW;
		this.deadline = dL;
	}
	
	
	public boolean assignManager(Employee employee){
		if(PM == null){
			PM = employee;
			// error if PM is already assigned
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
	
	 public boolean setReportComment(Week w){
	 }
	
	 public boolean getWeeklyReport(Week w){}
	 
	 public boolean getTotalProjectBudget(){
		 
	 }
	 
	 public boolean getActivityDiversion(Activity a, Week w){
		 
	 }
	 
	 
	

}
