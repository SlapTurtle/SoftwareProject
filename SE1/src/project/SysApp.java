package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SysApp {
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
		this.systemLog = new File("systemLog");
		
		//Improvement of systemLog TO-DO
		if(!this.systemLog.exists()){
			try {
				this.systemLog.createNewFile();
				Calendar cal = this.dateServer.getToday();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				this.writeToLog("| "+year+"/"+month+"/"+day+" - File created");
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
		if(this.currentUser != null){
			return true;
		}
		return false;
	}
	
	public boolean login(Employee employee){
		if (!this.loggedIn()){
			if( this.employeeList.contains(employee)){
				this.currentUser = employee;
				return true;
			}
			//error - employee is not in current system
		}
		//error - another employee is already logged in
		return false;
	}
	
	public boolean logoff(){
		if(this.loggedIn()){
			this.currentUser = null;
			return true;
		}
		//error - no user is currently logged in
		return true;
	}
	
	public boolean addEmployee(Employee ID){
		return this.employeeList.add(ID);
	}
	
	public boolean addProject(Project ID){
		return this.projectList.add(ID);
	}
	
	public boolean addActicity(Activity ID){
		return this.activityList.add(ID);
	}
	
	public ArrayList<String> getAvailableEmployees(Activity activity){
		//TO-DO
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
