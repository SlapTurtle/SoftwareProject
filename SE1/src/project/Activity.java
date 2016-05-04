package project;

import java.util.ArrayList;
import java.util.List;
public class Activity {
	
	private SysApp sysApp;
	private String activityID;
	private String type;
	private Week startWeek; 
	private Week endWeek;	
	private List<Employee> employeeList;
	private List<Project> projectList;
	private double hourBudget;
	private double hoursSpent;
	
	
	// -------- Sets the activity ID and planed work weeks ----
	public Activity(SysApp sysApp, String type, Week startWeek, Week endWeek) {
		this.sysApp = sysApp;
		this.activityID = setUniqueID();
		this.type = type.toUpperCase();
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.employeeList = new ArrayList<Employee>();
		this.projectList = new ArrayList<Project>();
		this.hourBudget = 0.0;
		this.hoursSpent = 0.0;
	}
	
	public String getUniqueID() {
		return activityID;
	}
	
	private String setUniqueID() {
		return "ID" + sysApp.getAcount();
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Week getEndWeek(){
		return endWeek;
	}
	
	public void setEndWeek(Week w){
		if (w.compareTo(startWeek) >= 0 && w.compareTo(endWeek) != 0) {
			endWeek = w;
			int newSize = endWeek.weekDifference(startWeek)+1;
			for(Employee e : employeeList){
				e.updateActivityWeeks(this, newSize);
			}
		}
	}
	
	public Week getStartWeek(){
		return this.startWeek;
	}
	
	public void setStartWeek(Week w){
		if (w.compareTo(endWeek) <= 0 && w.compareTo(startWeek) != 0){
			startWeek = w;
			int newSize = endWeek.weekDifference(startWeek)+1;
			for(Employee e : employeeList){
				e.updateActivityWeeks(this, newSize);
			}
		}
	}
	
	public List<Employee> getEmployeeList(){
		return employeeList;
	}
	
	public List<Project> getProjectList(){
		return projectList;
	}
	
	public double getHourBudget(){
		return hourBudget;
	}
	
	public void setHourBudget(double d){
		hourBudget = d;
	}
	
	public double getSpentBudget(){
		return hoursSpent;
	}
	
	public void spendHours(double d){
		hoursSpent += d;
	}
	
	public boolean assignEmployee(Employee employee) {
		if (employee!=null && !employeeList.contains(employee)) {
			employeeList.add(0,employee);
			return true;
		}
		return false;
	}

	public boolean assignProject(Project project) {
		if (project!=null && !projectList.contains(project)) {
			projectList.add(0,project);
			return true;
		}
		return false;
	}

	public double getEmployeeHours(Employee emp, Week w) throws IllegalOperationException {
		if(emp != null && employeeList.contains(emp)){
			return emp.getWorkHours(this, w)[7];
		}
		else {
			throw new IllegalOperationException("Employee not assigned to activity", this.getClass());
		}
	}
}
