package Project;

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
	private HashMap<Integer, String> reports = new HashMap<Integer, String>();
	
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
		 if(sysApp.loggedIn()){
		 if(projectManager==null){
		 projectManager = employee;
		 return true;
		 }
		 else{
		 	if(projectManager.equals(sysApp.getCurrentUser()));{
		 		projectManager = employee;
		 	}
			
		 }	
		 }
		return false;
	}
	
	public Employee getManager(){
		return this.projectManager;
	}
	
	public boolean addEmployee(Employee employee){
		if(employee != null && !employeeList.contains(employee)){
			return employeeList.add(employee);
		}
		return false;
	}
	
	public boolean addActivity(Activity activity){
		if(projectManager==null || projectManager.equals(sysApp.getCurrentUser())){
			if(activity != null && !activityList.contains(activity)){
				return activityList.add(activity);
			}
		}
		return false;
	}
	
	public boolean removeActivity(Activity activity){
		if(activityList.contains(activity)){
			activityList.remove(activity);
			return true;
		}
			return false;
	}
	
	private String setUniqueID() {
		return "ID" + sysApp.getPcount();
	}
	
	public String checkUniqueID() {
		return this.projectID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Week getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(Week startWeek) {
		this.startWeek = startWeek;
	}

	public Week getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(Week endWeek) {
		this.endWeek = endWeek;
	}

	public Week getDeadline() {
		return deadline;
	}

	public void setDeadline(Week deadline) {
		this.deadline = deadline;
	}

	public void setReportComment(String comment,Week w){
		this.reports.put((w.getYear()-2016)*53+w.getWeek(), comment);
	}
 
	public String getWeeklyReport(Week w){
		return this.reports.get((w.getYear()-2016)*53+w.getWeek());
	}
	 
	public double getBudget(){
		return this.budget;
	} 
	
	public double[] getSpentBudget(){
		int size = activityList.size();
		double[] d = new double[size+1];
		d[0] = 0.0;
		for(int i=1; i<size+1; i++){
			Activity a = activityList.get(i-1);
			double count = 0.0;
			for(Employee e : a.getEmployeeList()){
				List<double[]> list = (List<double[]>) e.getWorkHourList().get(e.getActivityList().indexOf(a));
				for(double[] weeklyHours : list){
					for(double hours : weeklyHours){
						count += hours;
						d[0] += hours; 
					}
				}
			}
			d[i] = count;
		}
		return d;
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
