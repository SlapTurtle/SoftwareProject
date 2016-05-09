package Project;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Interface.ManuelTestSetup;
import Interface.Menu;
import Interface.UserInterface;

public class SysApp {
	
	/*
	 * FIELDS
	 */
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<Project> projectList = new ArrayList<Project>();
	private List<Activity> activityList = new ArrayList<Activity>();
	private DateServer dateServer = new DateServer();
	private Employee currentUser = null;
	private File systemLog = new File("systemLog");
	private int aIDcount = 0;
	private int pIDcount = 0;
	
	public UserInterface ui;
	public Menu currentMenu;
	public Menu mainmenu;
	
	/*
	 * CONSTRUCTOR
	 */
	public SysApp(boolean b){
		if(b){	//TEST ONLY
			ManuelTestSetup.main(this);
		}
		
		if(b){	//Initiates UI if needed.
			ui = new UserInterface(this);
			ui.initializeMenus();
			mainmenu.logOff("New Instance Initiated.");
		}
	}
	
	/***
	 * For Testing Only
	 */
	public void setDateServer(DateServer dateServer) {
		this.dateServer = dateServer;
	}
	/***/
	
	/*
	 * GETTERS
	 */
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public DateServer getDateServer() {
		return dateServer;
	}

	public Employee getCurrentUser() {
		return currentUser;
	}

	public File getSystemLog() {
		return systemLog;
	}

	public boolean loggedIn(){
		if(this.currentUser != null){
			return true;
		}
		return false;
	}
	
	/*
	 * GETTER BY CRITERIA
	 */
	public Project projectByID(String ID){
		for(Project x : projectList)
			if (x.checkUniqueID().equals(ID)){
				return x;
			}
		return null;
	}
	
	public Employee employeeByInitials(String initials){
		for(Employee e : employeeList) {
			if (e.getInitials().equals(initials)){
				return e;
			}
		}
		return null;
	}

	public Project projectByName(String project) {
		for(Project x : projectList)
			if (x.getName().equals(project)){
				return x;
			}
		return null;
	}

	public Activity activityByID(String ID) {
		for(Activity a : activityList){
			if(a.getUniqueID().equals(ID)){
				return a;
			}
		}
		return null;
	}

	public Activity activityByName(String name) {
		for(Activity a : activityList){
			if(a.getType().equals(name)){
				return a;
			}
		}
		return null;
	}

	public boolean removeEmployee(Employee e) {
		if(employeeList.size() > 1){
			for(Project p : e.getProjectList()){
				p.getEmployeeList().remove(e);
			}
			for(Activity a : e.getActivityList()){
				a.getEmployeeList().remove(e);
			}
			employeeList.remove(e);
			return true;
		}
		return false;
	}
	
	/*
	 * METHODS
	 */
	public boolean login(String initials){
		if (!loggedIn()){
			for(Employee e : this.employeeList){
				if(e.getInitials().equals(initials.toUpperCase())){
					this.currentUser = e;
					return true;
				}
			}
			//error - no user with those initials found
			return false;
		}
		//error - a user is already logged in
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
		return false;
	}
	
	public boolean addEmployee(String initials){
		Employee employee = new Employee(initials.toUpperCase());
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
	
	public boolean addProject(Project p){
		if(projectList.contains(p) || p == null){
			return false;
		}
		else{
			for(Project pro : projectList){
				if(pro.getName().equals(p.getName())){
					return false;
				}
			}
			return this.projectList.add(p);
		}
	}
	
	public boolean addActivity(Activity ID){
		if(activityList.contains(ID) || ID == null){
			return false;
		}
		else{
			for(Activity a : activityList){
				if(a.getType().equals(ID.getType())){
					return false;
				}
			}
			return this.activityList.add(ID);
		}
	}
	
	public List<Employee> getAvailableEmployees(Activity activity, Week week) throws IllegalOperationException{
		List<Employee> available = new ArrayList<Employee>();
		if(dateServer.getToday().compareTo(week) > 0){
			return available;
		}
		for(Employee e : employeeList){
			if(!activity.getEmployeeList().contains(e) && e.getWeeklyActivities(week).size() < 20){
				available.add(e);
			}
		}	
		return available;
	} 
	
	public List<Employee> getAvailableEmployees(Week week) throws IllegalOperationException{
		List<Employee> available = new ArrayList<Employee>();
		if(dateServer.getToday().compareTo(week) > 0){
			return available;
		}
		for(Employee e : employeeList){
			if(e.getWeeklyActivities(week).size() < 20){
				available.add(e);
			}
		}	
		return available;
	}
	
	public int getPcount(){ //ID counters for Project
		pIDcount++;
		return pIDcount;	
	}
	
	public int getAcount(){ //ID counters for Activity
		aIDcount++;
		return aIDcount;	
	}

	public boolean removeActivity(Activity a) {
		for(Project p : a.getProjectList()){
			p.getActivityList().remove(a);
		}
		for(Employee e : a.getEmployeeList()){
			e.getActivityList().remove(a);
		}
		activityList.remove(a);
		return true;
	}
	
	public boolean removeProject(Project p) {
		for(Activity a : p.getActivityList()){
			a.getProjectList().remove(p);
		}
		for(Employee e : p.getEmployeeList()){
			e.getProjectList().remove(p);
		}
		projectList.remove(p);
		return true;
	}
	
	public boolean writeToLog(String entry) {
		String s = entry + " - \"" + currentUser.getInitials()+"\"";
        try {
        	Logger logger = Logger.getLogger(systemLog.getAbsolutePath());
        	FileHandler fh = new FileHandler(systemLog.getAbsolutePath());
        	fh.setFormatter(new SimpleFormatter());
        	logger.addHandler(fh);
            logger.info(s);
        } catch (Exception e) {
        	//should never happen
        }
		return true;
	}
}
