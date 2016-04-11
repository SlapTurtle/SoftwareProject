package project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SysApp {
	//Fields
	private List<Employee> employeeList;
	private List<Project> projectList;
	private List<Activity> activityList;
	private DateServer dateServer;
	private Employee currentUser;
	private File systemLog;
	
	private String logedInUser;
	private boolean isLogin = false;
	private boolean FindsEmployee = false;
	
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
	public boolean loggedIn(){
		return isLogin;
	}
	
	public boolean login(String employee){
		if (isLogin == false){
			isLogin = employeeList.contains(employee);
			if(isLogin == true){
				logedInUser = employee;
			}
		}
		return isLogin;
	}
	
	public boolean logoff(){
		isLogin = false;
		logedInUser = null;
		return isLogin;
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
	
	public DateServer getDateServer(){
		return dateServer;
	} 
	
	public ArrayList<String> getAvailableEmployees(Activity activity){
		return activity.employeeList;
	} 
	 
	@SuppressWarnings("unused")
	private boolean writeToLog(String rapport) throws IOException{
		if(file.exists() == false){
			file.createNewFile();
		}
		FileWriter note = new FileWriter(file);
		note.write(rapport);
		note.flush();
		note.close();
		
		return true;
	}
	
}
