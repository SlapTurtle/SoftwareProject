package project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SysApp {
	
	// ---- test? ----
	public static UserInterface ui = new UserInterface();
	
	public static void main(String[] args) {
		//ui.print("hej");
	}
	
	// ---- Actual SysApp ----
	
	//Fields
	private List<Employee> employeeList;
	private List<Project> projectList;
	private List<Activity> activityList;
	private DateServer dateServer;
	private Employee currentUser;
	private File systemLog;
	
	//Constructor
	public SysApp(){
		this.employeeList = new ArrayList<Employee>();
		this.projectList = new ArrayList<Project>();
		this.activityList = new ArrayList<Activity>();
		this.dateServer = new DateServer();
		this.currentUser = null;
		this.systemLog = new File(""); /* TO-DO */;
	}

	// ---- Getter and Setter Methods are only used for testing ----
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	public DateServer getDateServer() {
		return dateServer;
	}

	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;
	}

	public Employee getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Employee currentUser) {
		this.currentUser = currentUser;
	}

	public File getSystemLog() {
		return systemLog;
	}

	public void setSystemLog(File systemLog) {
		this.systemLog = systemLog;
	}

	// ---- Rest of the methods  ----
	
	
}
