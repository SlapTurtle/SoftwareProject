package project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SysApp {
	//Fields
	public UserInterface ui;
	private List<Employee> employeeList = new ArrayList<Employee>();
	private List<Project> projectList = new ArrayList<Project>();
	private List<Activity> activityList = new ArrayList<Activity>();
	private DateServer dateServer = new DateServer();
	private Employee currentUser = null;
	private File systemLog = new File("systemLog");
	private int pIDcount = 0;
	private int aIDcount = 0;
	public Menu currentMenu;
	public Menu mainmenu;
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public ArrayList<Menu> menuEmpMng = new ArrayList<Menu>();
	public ArrayList<Menu> menuPrjMng = new ArrayList<Menu>();
	public ArrayList<Menu> menuActMng = new ArrayList<Menu>();
	
	public SysApp() {
		
		ui = new UserInterface(this);
		addEmployee("admin");
		ui.print("Welcome. Please enter your initials to proceed:", ui.style[6]);
		while (!loggedIn()) {
			try {
				loginUI(ui.next());
			} catch (Exception e) {
				ui.print("Error: No Actions are allowed until after login is performed", ui.style[3]);
			}
		}
		menus.add(new Menu(this, "Manage Employee"));	//0
		menus.add(new Menu(this, "Add Employee")); 		//1
		menus.add(new Menu(this, "Remove Employee"));	//2
		
		menuEmpMng.add(new Menu(this, "Assign to Project"));
		menuEmpMng.add(new Menu(this, "Assign to Activity"));
		menuEmpMng.add(new Menu(this, "Set Work Hours for Activity By Week"));
		menuEmpMng.add(new Menu(this, "Get Work Hours for Week"));
		menuEmpMng.add(new Menu(this, "Get Work Hours for Activity By Week"));
		menuEmpMng.add(new Menu(this, "Get Activities for Week"));
		menuEmpMng.add(new Menu(this, "Remove Employee"));
		
		menuPrjMng.add(new Menu(this, "Add Employee to Project"));
		menuPrjMng.add(new Menu(this, "Get All Employees on Project"));
		menuPrjMng.add(new Menu(this, "Add Activity to Project"));
		menuPrjMng.add(new Menu(this, "Get All Activities on Project"));
		menuPrjMng.add(new Menu(this, "Set Report Comment"));
		menuPrjMng.add(new Menu(this, "View Weekly Report"));
		menuPrjMng.add(new Menu(this, "Set Time Budget of Project"));
		menuPrjMng.add(new Menu(this, "Get Total Project Budget Price"));
		menuPrjMng.add(new Menu(this, "Get Activeness of Activity"));
		
		
		menus.add(new Menu(this, "Add Employee")); 		//0
		menus.add(new Menu(this, "Manage Employee"));	//1
		menus.add(new Menu(this, "Manage Employee", menuEmpMng.toArray(new Menu[menuEmpMng.size()]), true, true));	//1
		menus.add(new Menu(this, "Get All Employees"));	//2
		menus.add(new Menu(this, "Add Project"));		//3
		menus.add(new Menu(this, "Manage Project"));
		menus.add(new Menu(this, "Manage Project", menuPrjMng.toArray(new Menu[menuPrjMng.size()]), true, true));
		menus.add(new Menu(this, "Get All Projects"));
		menus.add(new Menu(this, "Manage Project"));	//4
		menus.add(new Menu(this, "Add Activity"));		//5
		menus.add(new Menu(this, "Show Logs"));			//6
		menus.add(new Menu(this, "Set Font Size"));		//7
		
		Menu[] m = new Menu[] {
				new Menu(this, "Employees", new Menu[] {menus.get(0), menus.get(1), menus.get(2)}, true, true),
				new Menu(this, "Projects", new Menu[] {menus.get(4), menus.get(5), menus.get(7)}, true, true),
				new Menu(this, "Activities", new Menu[] {menus.get(5)}, true, true),
				new Menu(this, "System", new Menu[] {menus.get(6)}, true, true ),
				new Menu(this, "Settings", new Menu[] {menus.get(7)}, true, true),
				new Menu(this, "Help"),
				new Menu(this, "Exit"),
		};
		mainmenu = new Menu(this, "Main Menu", m, true, false);
		while(true)
		try {
			mainmenu.show();
		} catch (Exception e) {
			ui.clear();
			ui.print("Error: Unexpected Error. You have been returned to the main menu.", ui.style[3]);
		}
	}
	
	public SysApp(boolean b){
		
	}
	
	/*public static void main(String[] args) {
		/*if(systemLog.exists()){
			try {
				createNewFile();
				writeToLog("File Created");
			} catch (IOException e) {
				//ERROR in creating new File
				e.printStackTrace();
			}
		}*/
		/*
		Employee e = new Employee("BRIAN");
		addEmployee("BRIAN");
		while (true) {
			ui.print("Attempting to log in as \"" + ui.next() + "\".");
			ui.print("Error: No employee with such initials. Please try again:", ui.style[3]);
		}
	}*/

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
	
	public boolean loginUI(String initials){
		if (!loggedIn()){
			for(Employee e : this.employeeList){
				if(e.getInitials().equals(initials.toUpperCase())){
					ui.clear();
					ui.print("Successfully logged in as \"" + initials.toUpperCase() + "\".", ui.style[2]);
					this.currentUser = e;
					return true;
				}
			}
			ui.print("Error: No employee with initials \"" + initials.toUpperCase() + "\". Please try again:", ui.style[3]);
			return false;
		}
		ui.print("Error: An employee is already logged in.", ui.style[3]);
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
		return true;
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
	
	public boolean addProject(String string){
		return true;
		//return this.projectList.add(string);
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
			if (x.checkUniqueID()==ID){
				return x;
			}
		return null;
	}
	public Employee employeeByInitials(String initi){
		for(Employee x : employeeList) {
			if (x.getInitials().equals(initi)){
				return x;
			}
		}
		return null;
	}

	public Project projectByName(String project) {
		// TODO Auto-generated method stub
		return null;
	}

	public Activity activityByID(String act) {
		// TODO Auto-generated method stub
		return null;
	}

	public Activity activityByName(String act) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
