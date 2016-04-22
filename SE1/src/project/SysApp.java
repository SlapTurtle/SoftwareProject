package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SysApp {
	//Fields

	public static UserInterface ui = new UserInterface();
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<Project> projectList = new ArrayList<Project>();
	private List<Activity> activityList = new ArrayList<Activity>();
	private DateServer dateServer = new DateServer();
	private Employee currentUser = null;
	private static File systemLog = new File("systemLog");
	private static int ID_Count = 0;
	
	
	public static void main(String[] args) {
		/*if(systemLog.exists()){
			try {
				this.systemLog.createNewFile();
				this.writeToLog("File Created");
			} catch (IOException e) {
				//ERROR in creating new File
				e.printStackTrace();
			}
		}*/
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
	
	public boolean login(String initials){
		if (!this.loggedIn()){
			for(Employee e : this.employeeList){
				if(e.getInitials().equals(initials)){
					this.currentUser = e;
					return true;
				}
			}
			//error - no user with initials found in system
		}
		//error - another employee is already logged in
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
	
	public boolean addEmployee(String Initials){
		Employee employee = new Employee(Initials);
		return this.addEmployee(employee);
	}
	
	public boolean addEmployee(Employee employee){
		for(Employee e : this.employeeList){
			if(e.getInitials().equals(employee.getInitials())){
				//error - another user with those initials exist
				return false;
			}
		}
		return this.employeeList.add(employee);
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
		Calendar cal = this.dateServer.getToday();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String s = " | "+year+"/"+month+"/"+day+" - "+entry+" | ";
		note.write(s);
		note.flush();
		note.close();
		return true;
	}
	private static void incrementIDCount() {
		ID_Count++;
	}
	public static int getIDCount(){
		incrementIDCount();
		return ID_Count;	
	}
	public Project projectByID(String ID){
		for(Project x : projectList)
			if (x.checkUniqueID()==ID){
				return x;
			}
		return null;
	}
	public Employee employeeByInitials(String initi){
		for(Employee x : employeeList)
			if (x.getInitials()==initi){
				return x;
			}
		return null;
	}
	
}
