package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SysApp {
	//Fields
	private List<Employee> employeeList;
	private List<Project> projectList;
	private List<Activity> activityList;
	private DateServer dateServer;
	private Employee currentUser;
	private boolean isLogin;
	private File systemLog;
	
	//Constructor
	public SysApp(){
		this.employeeList = new ArrayList<Employee>();
		this.projectList = new ArrayList<Project>();
		this.activityList = new ArrayList<Activity>();
		this.dateServer = new DateServer();
		this.currentUser = null;
		this.isLogin = false;
		this.systemLog = new File("systemLog");
		
		if(!this.systemLog.exists()){
			try {
				this.systemLog.createNewFile();
			} catch (IOException e) {
				//ERROR in creating new File
				e.printStackTrace();
			}
		}
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
	public boolean loggedIn(){
		return isLogin;
	}
	
	public boolean login(Employee employee){
		if (this.isLogin == false){
			this.isLogin = this.employeeList.contains(employee);
			if(this.isLogin == true){
				this.currentUser = employee;
			}
		}
		return isLogin;
	}
	
	public boolean logoff(){
		this.isLogin = false;
		this.currentUser = null;
		return true;
	}
	
	public boolean addEmployee(Employee employee){
		boolean works = employeeList.add(employee);
		return works;
	}
	
	public boolean addProject(Project ID){
		return projectList.add(ID);
	}
	
	public boolean addActicity(Activity ID){
		return activityList.add(ID);
	}
	
	public ArrayList<String> getAvailableEmployees(Activity activity){
		//TO-DO
		//return activity.getEmployeeList;
		return null;
	} 
	 
	private boolean writeToLog(String entry) throws IOException {
		FileWriter note = new FileWriter(this.systemLog);
		note.write(entry);
		note.flush();
		note.close();
		return true;
	}
	
}
