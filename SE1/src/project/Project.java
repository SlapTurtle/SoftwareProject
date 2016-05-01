package project;

import java.util.ArrayList;
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
	public List<Employee> projectManagers;
	public List<Activity> activityList;
	public List<double[]> timeSpentList;
	public String type;
	public int budget;
	
	
	public Project(String ID, Week sW,Week eW,Week dL){
		this.name = name;
		this.startWeek = sW;
		this.endWeek = eW;
		this.deadline = dL;
		this.projectID = ID;
		this.employeeList = new ArrayList<Employee>();
		this.activityList = new ArrayList<Activity>();
		this.projectManagers = new ArrayList<Employee>();
		this.budget = 0;
	}

	public boolean assignManager(Employee employee){
		if(projectManagers.size()==0){
			projectManagers.add(employee);
			// error if PM is already assigned
			// ArrayList is used to be able to configure program to handle more project managers
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
		String ID = "ID";
		ID = ID + sysApp.getIDCount();
		return ID;
	}
	
	public String checkUniqueID() {
		return this.projectID;
	}
	
	public boolean setReportComment(Week w){
		
		return false;
	
	 }

	public boolean getWeeklyReport(Week w){
		return true;}
	 
	 public boolean getTotalProjectBudget(){
		return true;
	 
	 }
	 public boolean getActivityDiversion(Activity a, Week w){
		return false;
		 
	 }
	 //Positive integer values adds to budget, negative removes.
	 public void manageExpense(int value){
		 this.budget = this.budget + value;
	 }
	 
	 
	

}
