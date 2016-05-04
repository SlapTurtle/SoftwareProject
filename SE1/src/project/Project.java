package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Project {
	
	private String name;
	private String projectID;
	private SysApp sysApp;
	private Week startWeek;
	private Week endWeek;
	private Week deadline;
	private List<Employee> employeeList;
	private Employee projectManager;
	private List<Activity> activityList;
	private double budget;
	private HashMap<Week, String> reports = new HashMap<Week, String>();
	private String reportComment;
	
	public Project(SysApp sys, String name, Week sW,Week eW,Week dL){
		this.sysApp = sys;
		this.projectID = setUniqueID();
		this.name = name.toUpperCase();
		this.startWeek = sW;
		this.endWeek = eW;
		this.deadline = dL;
		this.employeeList = new ArrayList<Employee>();
		this.projectManager = null;
		this.activityList = new ArrayList<Activity>();
		this.budget = 0;
	}

	public boolean assignManager(Employee employee){
		if(projectManager==null){
			this.projectManager=employee;
			return true;
		}
		return false;
	}
	
	public boolean addEmployee(Employee employee){
		if(employee != null && !employeeList.contains(employee)){
			return employeeList.add(employee);
		}
		return false;
	}
	
	public boolean addActivity(Activity activity){
		if(activity != null && !activityList.contains(activity)){
			return activityList.add(activity);
		}
		return false;
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
	 
	public double getBudget(){
		return this.budget;
	}
	
	public double getSpentBudget(){
		double d = 0.0;
		for(Activity a : activityList){
			for(Employee e : a.getEmployeeList()){
				List<Double[]> list = (List<Double[]>) e.getWorkHourList().get(e.getActivityList().indexOf(a));
				for(Double[] weeklyHours : list){
					for(double hours : weeklyHours){
						d += hours;
					}
				}
			}
		}
		return d;
	}
	
	public double getActivityDiversion(Employee e, Activity a, Week w) throws IllegalOperationException{
		return e.getWorkHours(a, w)[7];
	}

	
	public void setBudget(double d) {
		budget = d;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}
	 
	 
	

}
