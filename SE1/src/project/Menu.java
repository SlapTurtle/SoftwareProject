package project;

import java.util.ArrayList;

public class Menu {

	SysApp sys;
	String header;
	Menu[] m;
	Menu parent;
	Project currentProject;
	Activity currentActivity;
	Employee currentEmployee;
	boolean method = false;
	boolean showHeader;
	boolean returnOption;
	
	public Menu(SysApp sys, String header) {
		this.sys = sys;
		this.header = header;
		this.method = true;
	}
	
	public Menu(SysApp sys, String header, Menu[] m, boolean showHeader, boolean returnOption) {
		this.sys = sys;
		this.header = header;
		this.m = m;
		this.showHeader = showHeader;
		this.returnOption = returnOption;
		if (m != null) {
			for (Menu menu : m) { menu.parent = this; }
		}
	}
	
	public void show() {
		sys.currentMenu = this;
		if (method == true) { runMethod(); return; }
		if (m == null) { return; }
		if (!(header == null) && !(header.equals(""))) { sys.ui.print(header, sys.ui.style[4]); }
		if (showHeader == true) {
			for (int i = 0; i < m.length; i++) {
				sys.ui.print(i+1 + "   " + m[i].header);
			}
			if (returnOption) { sys.ui.print(m.length+1 + "   " + "Return"); }
		} else {
			for (int i = 0; i < m.length; i++) {
				sys.ui.print(m[i].header);
			}
			if (returnOption) { sys.ui.print("return"); }
		}
		boolean b = false;
		String str;
		while (!b) {
			try {
				str = sys.ui.next().toLowerCase();
			} catch (Exception e) {
				str = e.getMessage();
			}
			if (returnOption && (str.equals("return") || str.equals(Integer.toString(m.length+1)))) {
				sys.ui.clear();
				parent.show();
				break;
			}
			for (int i = 0; i < m.length; i++) {
				if (str.equals(m[i].header.toLowerCase()) || str.equals(Integer.toString(i + 1))) {
					b = true;
					if (!m[i].method) { sys.ui.clear(); }
					m[i].show();
					break;
				}
			}
			if (!b) { sys.ui.invalidInput(); }
		}
	}
	
	private int getUserInputInt(int low, int high, String prompt, String errorMessage){
		int i = -1;
		while(true){
			try{
				sys.ui.print(prompt, sys.ui.style[6]);
				i = Integer.parseInt(sys.ui.next());
				if(!(low==0 && high==0) && !(i >= low && i <= high)){
					throw new NumberFormatException();
				}
				else{
					break;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: "+errorMessage, sys.ui.style[3]);
			}
		}
		return i;
		
	}
	
	public void runMethod() {
		switch (header) {
		// Employee - Top-Menu
		case "Add Employee": addEmployee(); break;
		case "Manage Employee": manageEmployee(); break;
		case "Get All Employees": getAllEmployees(); break;
		// Employee Sub-Menu
		case "Assign To Project": assignToProject(); break;
		case "Assign To Activity": assignToActivity(); break;
		case "Set Work Hours For Activity By Week": setWorkHoursForActivityForWeek(); break;
		case "Get Work Hours For Activity By Week": getWorkHoursForActivitiesForWeek(); break;
		case "Get Work Hours For Activity": getWorkHoursForActivity(); break;
		case "Get Work Hours For Week": getWorkHoursForWeek(); break;
		case "Get Activities for Week": getActivitiesForWeek(); break;
		case "Remove Employee": removeEmployee(); break;
		
		// Project Top-Menu
		case "Add Project": addProject(); break;
		case "Manage Project": manageProject(); break;
		case "Get All Projects": getAllProjects(); break;
		// Project Sub-Menus
		case "Add Employee to Project": addEmployeeToProject(); break;
		case "Get All Employees on Project": getAllEmployeesOnProject(); break;
		case "Add Project Activity": addActivityToProject(); break;
		case "Get All Activities on Project": setRepportComment(); break;
		case "Set Time Budget of Project": setTimeBudgetOfProject(); break;
		case "Get Total Project Budget Price": getTotalProjectBudget(); break;
		case "Get Activeness of Activity in Project": getProjectActiveness(); break;
		case "Set Report Comment": setProjectReportComment(); break;
		case "View Weekly Report": getWeeklyReport(); break;
		case "Remove Project": removeProject(); break;
		
		// Activity Top-Menu
		case "Add Activity": addActivity(); break;
		case "Manage Activity": manageActivity(); break;
		case "Get All Activities": getAllActivities(); break;
		// Activity Sub-Menu
		case "Set Activity Name": setActivityName(); break;
		case "Add Employee to Activity": addEmployeeToProject(); break;
		case "Set Start Date Of Activity": setStartDateOfActivity(); break;
		case "Set End Date of Activity": setEndDateOfActivity(); break;
		case "Add Activity to Project": addActivityToProject(); break;
		case "Get All Employees on Activity": getEmployeeListForActivity(); break;
		case "Get Hours Spent on Activity": getHoursSpentActivity(); break;
		case "Set Time Budget": changeBudgetActivity(); break;
		case "Remove Activity": removeActivity(); break;
		
		// Other
		case "Set Font Size": sys.ui.setFontSize(); break;
		case "Show Logs": ShowFuckingLogs(); break;
		case "Exit": System.exit(0); return;
		case "Help": sys.ui.help(); break;
		
		//Error?
		default: break;
		}
		parent.show();
	}

	/*
	 *  EMPLOYEE MENUES
	 */
	private void addEmployee(){
		sys.ui.print("Enter initials of new employee:", sys.ui.style[6]);
		String initials = sys.ui.next().toUpperCase();
		Employee employee = new Employee(initials);
		if (sys.ui.yesNoQuestion("Are you sure you want to add \"" + initials + "\" to the system?")) {
			if(sys.addEmployee(employee)){
				sys.ui.clear();
				sys.ui.print("Successfully added employee \"" + initials + "\" to the system.", sys.ui.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("Error: Employee with initials \"" + initials + "\" already exists.", sys.ui.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void manageEmployee(){
		sys.currentMenu = this;
		sys.ui.print("Enter initials of employee to manage:", sys.ui.style[6]);
		String initials = sys.ui.next().toUpperCase();
		Employee e = sys.employeeByInitials(initials);
		if(e == null){
			sys.ui.clear();
			sys.ui.print("Error: Employee with initials \"" + initials + "\" does not exist.", sys.ui.style[3]);
		}
		else{
			Menu manageEmployee = sys.menus.get(2);
			sys.currentMenu = manageEmployee;
			manageEmployee.currentEmployee = e;
			manageEmployee.header = "Manage Employee \""+e.getInitials()+"\"";
			sys.ui.clear();
			manageEmployee.show();
		}
	}
	
	private void getAllEmployees() {
		int sz = sys.getEmployeeList().size();
		String[] s = new String[sz];
		for (int i = 0; i < sz; i++) {
			s[i] = sys.getEmployeeList().get(i).getInitials();
		}
		sys.ui.listDisplay(s, "Registered Employees", 10);
	}
	
	/*
	 * EMPLOYEE SUB-MENUS
	 */
	private void assignToProject(){
		sys.ui.print("Enter Name or ID of Project:", sys.ui.style[6]);
		String project = sys.ui.next().toUpperCase();
		Project p = sys.projectByID(project);
		if(p == null){
			p = sys.projectByName(project);
		}
		if(p == null){
			sys.ui.clear();
			sys.ui.print("Error: Project with ID or Name \"" + project + "\" dosen't exist.", sys.ui.style[3]);
		}
		else{
			p.addEmployee(currentEmployee);
			currentEmployee.assignProject(p);
			sys.ui.print("Successfully added \"" + currentEmployee.getInitials() + "\" to \""+ project +"\"", sys.ui.style[2]);
		}
	}
	
	private void assignToActivity(){
		sys.ui.print("Enter Name or ID of Activity:", sys.ui.style[6]);
		String act = sys.ui.next().toUpperCase();
		Activity a = sys.activityByID(act);
		if(a == null){
			a = sys.activityByName(act);
		}
		if(a == null){
			sys.ui.clear();
			sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", sys.ui.style[3]);
		}
		else{
			a.assignEmployee(currentEmployee);
			currentEmployee.assignActivity(a);
			sys.ui.print("Successfully added \"" + currentEmployee.getInitials() + "\" to \""+ act +"\"", sys.ui.style[2]);
		}
	}
	
	private void setWorkHoursForActivityForWeek(){
		//Gets Activity
		Activity a;
		while(true){
			sys.ui.print("Enter Name or ID of Activity:", sys.ui.style[6]);
			String act = sys.ui.next().toUpperCase();
			a = sys.activityByID(act);
			if(a == null){
				a = sys.activityByName(act);
			}
			if(a == null){
				sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", sys.ui.style[3]);
			}
			else{
				break;
			}
		}
		//Gets week
		int i = getUserInputInt(1, 53, "Enter Week within \""+a.type+"\"", "Invalid Week");
		Week w = sys.getDateServer().getWeek(i);
		//Gets weekday
		int j = getUserInputInt(1,7, "Enter weekday (1-7)","Invalid weekday.");
		//Gets Hours
		double d = 0;
		while(true){
			try{
				sys.ui.print("Enter amount of hours of work to to \"" + a.type + "\" in week "+w.getWeek(), sys.ui.style[6]);
				d = Double.parseDouble(sys.ui.next());
				if(d <= 0.0){
					throw new NumberFormatException();
				}
				else{
					break;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: Invalid week.", sys.ui.style[3]);
			}
		}
		//Puts it all together
		if(sys.ui.yesNoQuestion("Are you sure you want to add "+d+" hours to \""+a.type+"\" on weekday "+j+" of week "+w.getWeek()+"?")){
			currentEmployee.setHours(a, d, w, j);
			sys.ui.clear();
			sys.ui.print("Successfully added "+d+" hours to \""+a.type+"\" on weekday "+j+" of week "+w.getWeek(), sys.ui.style[2]);
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void getWorkHoursForActivitiesForWeek() {
		// TODO Auto-generated method stub
	}
	
	private void getWorkHoursForActivity() {
		// TODO Auto-generated method stub
	}
	
	private void getWorkHoursForWeek() {
		// TODO Auto-generated method stub
	}

	private void getActivitiesForWeek(){
		int i = -1;
		while(true){
			try{
				sys.ui.print("Enter week");
				i = Integer.parseInt(sys.ui.next());
				if(!(i > 0 && i <= 53)){
					throw new NumberFormatException();
				}
				else{
					break;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: Invalid week.", sys.ui.style[3]);
			}
		}
		Week w = sys.getDateServer().getWeek(i);
		ArrayList<Activity> a_list = currentEmployee.getWeeklyActivities(w);
		ArrayList<String> list = new ArrayList<String>();
		for(Activity a : a_list){
			String s;
			try {
				s = a.type + " : " + currentEmployee.getWorkHours(a, w)+" hours";
				list.add(s);
			} catch (IllegalOperationException e) { continue;}
		}
		sys.ui.listDisplay(list.toArray(new String[list.size()]), "Logs And Shit", 10);
	}
	
	private void removeEmployee() {
		Employee e = parent.currentEmployee;
		if (sys.ui.yesNoQuestion("Are you sure you want to remove \"" + e.getInitials() + "\" from the system?")) {
			if(sys.getEmployeeList().remove(e)){
				sys.ui.clear();
				sys.ui.print("Successfully removed employee \"" + e.getInitials() + "\" from the system.", sys.ui.style[2]);
			}
			else{
				sys.ui.print("Error: Employee with initials \"" + e.getInitials() + "\" doesn't exists.", sys.ui.style[3]);
			}
		} else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	/*
	 * ACTVITY MENUES
	 */
	private void addActivity() {
		String name = null;
		while(true){
			sys.ui.print("Enter name of new Activity:", sys.ui.style[6]);
			name = sys.ui.next().toUpperCase();
			/*  does name meet the criteria ?
			b = (name == acceptable);
			if(!b) {
				{sys.ui.print("Error: Invalid name. Please try again:", sys.ui.style[3]);
		 	}
		 	*/
			break;
		}
		int i = getUserInputInt(1, 53, "Enter starting week of Activity \"" + name + "\"", "Invalid week");
		Week start = sys.getDateServer().getWeek(i);
		
		int j = getUserInputInt(i, 53, "Enter ending week of Activity \"" + name + "\"", "Invalid week");
		Week end = sys.getDateServer().getWeek(j);
		
		Activity a = new Activity(sys, name, start, end);
		if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + name + "\" to the system?")){
			if(sys.addActicity(a)){
				sys.ui.clear();
				sys.ui.print("Successfully added Activity \"" + name + "\" to the system.", sys.ui.style[2]);
			}
			else{
				sys.ui.print("Error: Invalid Activity. Please try again:", sys.ui.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void manageActivity() {
		sys.currentMenu = this;
		sys.ui.print("Enter initials of Activities to manage:", sys.ui.style[6]);
		String name = sys.ui.next().toUpperCase();
		Activity a = sys.activityByName(name);
		if(a == null){
			sys.ui.clear();
			sys.ui.print("Error: Activity \"" + name + "\" does not exist.", sys.ui.style[3]);
		}
		else{
			Menu manageEmployee = sys.menus.get(10);
			sys.currentMenu = manageEmployee;
			manageEmployee.currentActivity = a;
			manageEmployee.header = "Manage Activity \""+a.type+"\"";
			sys.ui.clear();
			manageEmployee.show();
		}
	}

	private void getAllActivities() {
		int sz = sys.getActivityList().size();
		String[] s = new String[sz];
		for (int i = 0; i < sz; i++) {
			s[i] = sys.getActivityList().get(i).type;
		}
		sys.ui.listDisplay(s, "Registered Activities", 10);
	}
	
	/*
	 * ACTIVITY SUB-MENUS
	 */
	private void setActivityName() {
		// TODO Auto-generated method stub
		
	}

	private void setStartDateOfActivity() {
		// TODO Auto-generated method stub
		
	}

	private void setEndDateOfActivity() {
		// TODO Auto-generated method stub
		
	}

	private void addActivityToProject() {
		// TODO Auto-generated method stub
		
	}

	private void getEmployeeListForActivity() {
		// TODO Auto-generated method stub
		
	}

	private void getHoursSpentActivity() {
		// TODO Auto-generated method stub
		
	}

	private void changeBudgetActivity() {
		// TODO Auto-generated method stub
		
	}

	private void removeActivity() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * PROJECT MENUS
	 */
	private void addProject() {
		// TODO Auto-generated method stub
		
	}
	
	private void manageProject(){
		sys.currentMenu = this;
		sys.ui.print("Enter ID of project to manage:", sys.ui.style[6]);
		String ID = sys.ui.next().toUpperCase();
		Project p = sys.projectByID(ID);
		if(p == null){
			sys.ui.clear();
			sys.ui.print("Error: Project with ID \"" + ID + "\" does not exist.", sys.ui.style[3]);
		}
		else{
			Menu manageProject = sys.menus.get(6);
			sys.currentMenu = manageProject;
			manageProject.currentProject = p;
			manageProject.header = "Manage Project \""+p.type+"\"";
			sys.ui.clear();
			manageProject.show();
		}
	}

	private void getAllProjects() {
		int sz = sys.getProjectList().size();
		String[] s = new String[sz];
		for (int i = 0; i < sz; i++) {
			s[i] = sys.getProjectList().get(i).type;
		}
		sys.ui.listDisplay(s, "Registered Activities", 10);
	}
	
	/*
	 * PROJECT SUB-MENUS
	 */
	private void addEmployeeToProject() {
		// TODO Auto-generated method stub
		
	}

	private void getAllEmployeesOnProject() {
		// TODO Auto-generated method stub
		
	}

	private void setRepportComment() {
		// TODO Auto-generated method stub
		
	}

	private void setTimeBudgetOfProject() {
		// TODO Auto-generated method stub
		
	}

	private void getTotalProjectBudget() {
		// TODO Auto-generated method stub
		
	}

	private void getProjectActiveness() {
		// TODO Auto-generated method stub
		
	}

	private void setProjectReportComment() {
		// TODO Auto-generated method stub
		
	}

	private void getWeeklyReport() {
		// TODO Auto-generated method stub
		
	}

	private void removeProject() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * OTHER MENUES
	 */
	public void ShowFuckingLogs() {
		String[] s = new String[44];
		for (int i = 0; i < s.length; i++) {
			s[i] = Integer.toString(i);
		}
		sys.ui.listDisplay(s, "Logs And Shit", 10);
	}
}
