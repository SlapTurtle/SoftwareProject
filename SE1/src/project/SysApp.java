package project;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SysApp {
	//Fields
	public UserInterface ui;
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<Project> projectList = new ArrayList<Project>();
	private List<Activity> activityList = new ArrayList<Activity>();
	private DateServer dateServer = new DateServer();
	private Employee currentUser = null;
	private File systemLog = new File("systemLog");
	private int aIDcount = 0;
	private int pIDcount = 0;
	public Menu currentMenu;
	public Menu mainmenu;
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public ArrayList<Menu> menuEmpMng = new ArrayList<Menu>();
	public ArrayList<Menu> menuPrjMng = new ArrayList<Menu>();
	public ArrayList<Menu> menuActMng = new ArrayList<Menu>();
	
	public SysApp() {
		
		menuEmpMng.add(new Menu(this, "Assign To Project"));
		menuEmpMng.add(new Menu(this, "Get All Assigned Projects"));
		menuEmpMng.add(new Menu(this, "Get Manager Assigned Projects"));
		menuEmpMng.add(new Menu(this, "Assign To Activity"));
		menuEmpMng.add(new Menu(this, "Get All Assigned Activities"));
		menuEmpMng.add(new Menu(this, "Set Work Hours For Activity By Week"));
		menuEmpMng.add(new Menu(this, "Get Work Hours For Activity By Week"));
		menuEmpMng.add(new Menu(this, "Get Work Hours For Activity"));
		menuEmpMng.add(new Menu(this, "Get Work Hours For Week"));
		menuEmpMng.add(new Menu(this, "Get Activities For Week"));
		menuEmpMng.add(new Menu(this, "Remove Employee"));
		
		menuPrjMng.add(new Menu(this, "Set Project Name"));
		menuPrjMng.add(new Menu(this, "Add Employee to Project"));
		menuPrjMng.add(new Menu(this, "Get All Employees on Project"));
		menuPrjMng.add(new Menu(this, "Add Project Activity"));
		menuPrjMng.add(new Menu(this, "Get All Activities on Project"));
		menuPrjMng.add(new Menu(this, "Set Start Date of Project"));
		menuPrjMng.add(new Menu(this, "Set End Date of Project"));
		menuPrjMng.add(new Menu(this, "Set Deadline of Project"));
		menuPrjMng.add(new Menu(this, "Set Time Budget of Project"));
		menuPrjMng.add(new Menu(this, "Get Total Project Budget Price"));
		menuPrjMng.add(new Menu(this, "Get Activeness of Activity in Project"));
		menuPrjMng.add(new Menu(this, "Set Report Comment"));
		menuPrjMng.add(new Menu(this, "Get Weekly Report"));
		menuPrjMng.add(new Menu(this, "Remove Project"));
		
		menuActMng.add(new Menu(this, "Set Activity Name"));
		menuActMng.add(new Menu(this, "Add Employee to Activity"));
		menuActMng.add(new Menu(this, "Get All Employees on Activity"));
		menuActMng.add(new Menu(this, "Add Activity to Project"));
		menuActMng.add(new Menu(this, "Get Assigned Projects"));
		menuActMng.add(new Menu(this, "Set Start Date Of Activity"));
		menuActMng.add(new Menu(this, "Set End Date of Activity"));
		menuActMng.add(new Menu(this, "Set Time Budget"));
		menuActMng.add(new Menu(this, "Get Hours Spent on Activity"));
		menuActMng.add(new Menu(this, "Get Activity Status By Week"));
		menuActMng.add(new Menu(this, "Get Activity Status"));
		menuActMng.add(new Menu(this, "Remove Activity"));
		
		menus.add(new Menu(this, "Add Employee")); // 0
		menus.add(new Menu(this, "Manage Employee"));
		menus.add(new Menu(this, "Manage Employee", menuEmpMng.toArray(new Menu[menuEmpMng.size()]), true, true));
		menus.add(new Menu(this, "Get All Employees"));
		menus.add(new Menu(this, "Add Project")); // 4
		menus.add(new Menu(this, "Manage Project"));
		menus.add(new Menu(this, "Manage Project", menuPrjMng.toArray(new Menu[menuPrjMng.size()]), true, true));
		menus.add(new Menu(this, "Get All Projects"));
		menus.add(new Menu(this, "Add Activity")); // 8
		menus.add(new Menu(this, "Manage Activity"));
		menus.add(new Menu(this, "Manage Activity", menuActMng.toArray(new Menu[menuActMng.size()]), true, true));
		menus.add(new Menu(this, "Get All Activities"));
		menus.add(new Menu(this, "Show Logs"));	// 12
		menus.add(new Menu(this, "Show Date"));
		menus.add(new Menu(this, "Set Font Size"));
		Menu[] m = new Menu[] {
				new Menu(this, "Employees", new Menu[] {menus.get(0), menus.get(1), menus.get(3)}, true, true),
				new Menu(this, "Projects", new Menu[] {menus.get(4), menus.get(5), menus.get(7)}, true, true),
				new Menu(this, "Activities", new Menu[] {menus.get(8), menus.get(9), menus.get(11)}, true, true),
				new Menu(this, "System", new Menu[] {menus.get(12), menus.get(13)}, true, true ),
				new Menu(this, "Settings", new Menu[] {menus.get(14)}, true, true),
				new Menu(this, "Help"),
				new Menu(this, "Log Off"),
				new Menu(this, "Exit"),
		};
		mainmenu = new Menu(this, "Main Menu", m, true, false);
		menus.get(2).parent = m[0];
		menus.get(6).parent = m[1];
		menus.get(10).parent = m[2];
		
		/**
		 * TEST!!!!
		 */
		try{
			//Dummy Project
			Week week1 = new Week(2016, 43);
			Week week2 = new Week(2017, 3);
			Week week3 = new Week(2017, 5);
			
			Project p1 = new Project(this, "pro1", week1,week2,week3);
			Project p2 = new Project(this, "pro2", week1,week2,week3);
			addProject(p1);
			addProject(p2);
			
			//Dummy Activities
			Activity a1 = new Activity(this, "act1", week1, week2);
			Activity a2 = new Activity(this, "act2", week2, week3);
			addActivity(a1);
			addActivity(a2);
			
			//Dummy Employees
			Employee e1 = new Employee("emp1");
			Employee e2 = new Employee("emp2");
			addEmployee(e1);
			addEmployee(e2);
			
			//Dummy Evaluation
			p1.addActivity(a1);
			a1.assignProject(p1);
			
			p1.addActivity(a2);
			a2.assignProject(p1);
			
			p1.addEmployee(e1);
			e1.assignProject(p1);
			
			p1.addEmployee(e2);
			e2.assignProject(p1);
			
			a1.assignEmployee(e1);
			e1.assignActivity(a1);
			
			a1.assignEmployee(e2);
			e2.assignActivity(a1);
			
			a2.assignEmployee(e1);
			e1.assignActivity(a2);
			
			a2.assignEmployee(e2);
			e2.assignActivity(a2);
			
			e1.setHours(a1, 3, week2, 2);
			e1.setHours(a1, 5, week2, 3);
			e1.setHours(a1, 2, week2, 4);
			
			e1.setHours(a2, 5, week2, 5);
			e1.setHours(a2, 5, week2, 6);
			e1.setHours(a2, 10, week2, 7);
			
			e2.setHours(a1, 7, week2, 2);
			e2.setHours(a1, 3, week2, 3);
			e2.setHours(a1, 15, week2, 4);
			
			e2.setHours(a2, 2, week2, 5);
			e2.setHours(a2, 9, week2, 6);
			e2.setHours(a2, 9, week2, 7);
			
			p1.assignManager(e1);
			p2.assignManager(e2);

		}
		catch(Exception e){
			System.exit(0);
		}
		/***/

		ui = new UserInterface(this);
		mainmenu.logOff("New Instance Initiated.");
		
		while(true)
		try {
			mainmenu.show();
		} catch (Exception e) {
			ui.clear();
			ui.print("Error: "+e.getMessage()+". You have been returned to the main menu.", UserInterface.style[3]);
		}
	}
	
	public SysApp(boolean b){
		
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
		}else{
		return this.projectList.add(p);
	}}
	
	public boolean addActivity(Activity ID){
		if(activityList.contains(ID) || ID == null){
			return false;
		}else{
		return this.activityList.add(ID);
		}
	}
	
	public List<Employee> getAvailableEmployees(Activity activity, Week week) throws IllegalOperationException{
		List<Employee> available = new ArrayList<Employee>();
		for(Employee e : employeeList){
			if(!activity.getEmployeeList().contains(e) && e.getWeeklyActivities(week).size() < 20){
				available.add(e);
			}
		}	
		return available;
	} 
	public List<Employee> getAvailableEmployees(Week week) throws IllegalOperationException{
		List<Employee> available = new ArrayList<Employee>();
		for(Employee e : employeeList){
			if(e.getWeeklyActivities(week).size() < 20){
				available.add(e);
			}
		}	
		return available;
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
        	//should 
        }

		return true;
	}
	
	//ID counters for project and activity
	public int getPcount(){
		pIDcount++;
		return pIDcount;	
	}
	public int getAcount(){
		aIDcount++;
		return aIDcount;	
	}
	
	//Method to get a project by its ID.
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
	
	
}
