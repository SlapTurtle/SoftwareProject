package project;

import java.util.ArrayList;
import java.util.List;

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
		if (!(header == null) && !(header.equals(""))) { sys.ui.print(header, UserInterface.style[4]); }
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
			str = sys.ui.next().toLowerCase();
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
			if (!b) { sys.ui.print("Error: Invalid choice of menu", UserInterface.style[3]); }
		}
	}
	
	private int getUserInputInt(int low, int high, String prompt, String errorMessage){
		while(true){
			try{
				String s = "("+low+"-"+high+")";
				if(low == 0 && high == 0){
					s = "";
				}
				else if(low == 0){
					s = "( <= "+high+" )";
				}
				else if(high == 0){
					s = "( >= "+low+" )";
				}
				
				sys.ui.print(prompt+s, UserInterface.style[6]);
				int i = Integer.parseInt(sys.ui.next());
				if(!((low <= i || low == 0) && (high >= i || high == 0))){
					throw new NumberFormatException();
				}
				
				else{
					return i;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: "+errorMessage, UserInterface.style[3]);
			}
		}
	}
	
	private double getUserInputDouble(String prompt, String errorMessage){
		while(true){
			try{
				sys.ui.print(prompt, UserInterface.style[6]);
				double d = Double.parseDouble(sys.ui.next());
				if(d <= 0){
					throw new NumberFormatException();
				}
				else{
					return d;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: "+errorMessage, UserInterface.style[3]);
			}
		}
	}
	
	public void runMethod() {
		switch (header) {
		// Employee - Top-Menu
		case "Add Employee": addEmployee(); break;
		case "Manage Employee": manageEmployee(); break;
		case "Get All Employees": getAllEmployees(); break;
		// Employee Sub-Menu
		case "Assign To Project": assignToProject(); break;
		case "Get All Assigned Projects": getProjectListEmployee(); break;
		case "Assign To Activity": assignToActivity(); break;
		case "Get All Assigned Activities": getActivityListEmployee(); break;
		case "Set Work Hours For Activity By Week": setWorkHoursForActivityForWeek(); break;
		case "Get Work Hours For Activity By Week": getWorkHoursForActivitiesForWeek(); break;
		case "Get Work Hours For Activity": getWorkHoursForActivity(); break;
		case "Get Work Hours For Week": getWorkHoursForWeek(); break;
		case "Get Activities For Week": getActivitiesForWeek(); break;
		case "Remove Employee": removeEmployee(); break;
		
		// Project Top-Menu
		case "Add Project": addProject(); break;
		case "Manage Project": manageProject(); break;
		case "Get All Projects": getAllProjects(); break;
		// Project Sub-Menus
		case "Set Project Name": setProjectName(); break;
		case "Add Employee to Project": addEmployeeToProject(); break;
		case "Get All Employees on Project": getAllEmployeesOnProject(); break;
		case "Add Project Activity": addProjectActivity(); break;
		case "Get All Activities on Project": getAllActivitiesOnProject(); break;
		case "Set Start Date of Project": setProjectStartDate(); break;
		case "Set End Date of Project": setProjectEndDate(); break;
		case "Set Deadline of Project": setPrjocetDeadline(); break;
		case "Set Time Budget of Project": setTimeBudgetOfProject(); break;
		case "Get Total Project Budget Price": getTotalProjectBudget(); break;
		case "Get Activeness of Activity in Project": getProjectActiveness(); break;
		case "Set Report Comment": setProjectReportComment(); break;
		case "Get Weekly Report": getWeeklyReport(); break;
		case "Remove Project": removeProject(); break;
		
		// Activity Top-Menu
		case "Add Activity": addActivity(); break;
		case "Manage Activity": manageActivity(); break;
		case "Get All Activities": getAllActivities(); break;
		// Activity Sub-Menu
		case "Set Activity Name": setActivityName(); break;
		case "Add Employee to Activity": addEmployeeToActivity(); break;
		case "Get All Employees on Activity": getEmployeeListForActivity(); break;
		case "Add Activity to Project": addActivityToProject(); break;
		case "Get Assigned Projects": getAssignedProjectsForActivity(); break;
		case "Set Start Date Of Activity": setStartDateOfActivity(); break;
		case "Set End Date of Activity": setEndDateOfActivity(); break;
		case "Set Time Budget": changeBudgetActivity(); break;
		case "Get Hours Spent on Activity": getHoursSpentActivity(); break;
		case "Get Activity Status By Week": getActivityStatusByWeek(); break;
		case "Get Activity Status": getActivityStatus(); break;
		case "Remove Activity": removeActivity(); break;
		
		// Other
		case "Set Font Size": sys.ui.setFontSize(); break;
		case "Show Logs": ShowFuckingLogs(); break;
		case "Exit": System.exit(0); return;
		case "Log Off": logOff("Log Off Succesfull. Goodbye"); return;
		case "Show Date": showDate(); break;
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
		String initials = null;
		boolean b = false;
		while(!b){
			sys.ui.print("Enter initials of new employee:", UserInterface.style[6]);
			initials = sys.ui.next().toUpperCase();
			b = true;
			for(Employee e : sys.getEmployeeList()){
				if(initials.equals(e.getInitials())){
					b = false;
					break;
				}
			}
			if(!b){
				sys.ui.print("Error: The name  \"" + initials + "\" is already taken.", UserInterface.style[3]);
			}
			else if (initials.length() != 4){
				sys.ui.print("Initials must be of 4 characters excatly", UserInterface.style[3]);
				b = false;
			}
		}
		Employee employee = new Employee(initials);
		if (sys.ui.yesNoQuestion("Are you sure you want to add \"" + initials + "\" to the system?")) {
			if(sys.addEmployee(employee)){
				sys.ui.clear();
				sys.ui.print("Successfully added employee \"" + initials + "\" to the system.", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("Error: Employee with initials \"" + initials + "\" already exists.", UserInterface.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void manageEmployee(){
		sys.currentMenu = this;
		sys.ui.print("Enter initials of employee to manage:", UserInterface.style[6]);
		String initials = sys.ui.next().toUpperCase();
		Employee e = sys.employeeByInitials(initials);
		if(e == null){
			sys.ui.clear();
			sys.ui.print("Error: Employee with initials \"" + initials + "\" does not exist.", UserInterface.style[3]);
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
		sys.ui.clear();
		sys.ui.listDisplay(s, "Registered Employees", 10);
	}
	
	/*
	 * EMPLOYEE SUB-MENUS
	 */
	private void assignToProject(){
		Employee e = parent.currentEmployee;
		sys.ui.print("Enter Name or ID of Project:", UserInterface.style[6]);
		String project = sys.ui.next().toUpperCase();
		Project p = sys.projectByID(project);
		if(p == null){
			p = sys.projectByName(project);
		}
		if(p == null){
			sys.ui.clear();
			sys.ui.print("Error: Project with ID or Name \"" + project + "\" dosen't exist.", UserInterface.style[3]);
		}
		else{
			if(!e.getProjectList().contains(p)){
				p.addEmployee(e);
				e.assignProject(p);
				sys.ui.clear();
				sys.ui.print("Successfully added \"" + e.getInitials() + "\" to \""+ project +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("Error: \"" + e.getInitials() + "\" is already assigned to \"" + project + "\"", UserInterface.style[3]);
			}
			
		}
	}
	
	private void getProjectListEmployee() {
		Employee e = parent.currentEmployee;
		List<Project> list = e.getProjectList();
		if(list.size() > 0){
			String[] str = new String[list.size()];
			for(Project p: list){
				str[list.indexOf(p)] = p.getName(); 
			}
			sys.ui.clear();
			sys.ui.listDisplay(str, "All Registered Projects", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("\"" + e.getInitials() + "\" not assigned to any Projects", UserInterface.style[3]);
		}
	}
	
	private void assignToActivity(){
		Employee e = parent.currentEmployee;
		sys.ui.print("Enter Name or ID of Activity:", UserInterface.style[6]);
		String act = sys.ui.next().toUpperCase();
		Activity a = sys.activityByID(act);
		if(a == null){
			a = sys.activityByName(act);
		}
		if(a == null){
			sys.ui.clear();
			sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", UserInterface.style[3]);
		}
		else{
			if(a.assignEmployee(e)){
				e.assignActivity(a);
				sys.ui.clear();
				sys.ui.print("Successfully added \"" + e.getInitials() + "\" to \""+ act +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("\"" + e.getInitials() + "\" is already assigned to \""+ act +"\"", UserInterface.style[3]);
			}
		}
	}
	
	private void getActivityListEmployee() {
		Employee e = parent.currentEmployee;
		List<Activity> list = e.getActivityList();
		if(list.size() > 0){
			String[] str = new String[list.size()];
			for(Activity a : list){
				str[list.indexOf(a)] = a.getType(); 
			}
			sys.ui.clear();
			sys.ui.listDisplay(str, "All Registered Activities", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("\"" + e.getInitials() + "\" not assigned to any activities", UserInterface.style[3]);
		}
	}
	
	private void setWorkHoursForActivityForWeek(){
		Employee emp = parent.currentEmployee;
		//Gets Activity
		Activity a;
		while(true){
			sys.ui.print("Enter Name or ID of Activity:", UserInterface.style[6]);
			String act = sys.ui.next().toUpperCase();
			a = sys.activityByID(act);
			if(a == null){
				a = sys.activityByName(act);
			}
			if(a == null){
				sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", UserInterface.style[3]);
			}
			else if(!emp.getActivityList().contains(a)){
				sys.ui.print("Error: \""+emp.getInitials()+"\" Not assigned to Activity \"" + act + "\"", UserInterface.style[3]);
			}
			else{
				break;
			}
		}
		//Gets week
		int y = getUserInputInt(a.getStartWeek().getYear(), a.getEndWeek().getYear(), "Enter Year of week", "Invalid year");
		int st = 1;
		int en = 53;
		if(y == a.getStartWeek().getYear()){
			st = a.getStartWeek().getWeek();
		}
		if(y == a.getEndWeek().getYear()){
			en = a.getEndWeek().getWeek();
		}
		int in = getUserInputInt(st, en, "Enter week within Activity \"" + a.getType() + "\"", "Invalid week");
		Week w = new Week(y, in);
		//Gets weekday
		int j = getUserInputInt(1,7, "Enter weekday","Invalid weekday.");
		//Gets Hours
		double d = getUserInputDouble("Enter amount of hours of work to to \"" + a.getType() + "\" in week "+w, "Error: Invalid amount.");
		//Puts it all together
		double k = 0.0;
		try {
			k = emp.getWorkHours(a, w)[j];
			if(sys.ui.yesNoQuestion("Are you sure you want to change workshours of \""+a.getType()+"\" on weekday "+j+" of week "+w+" from "+k+" to "+d+"?")){
				emp.setHours(a, d, w, j);
				sys.ui.clear();
				sys.ui.print("Successfully added "+d+" hours to \""+a.getType()+"\" on weekday "+j+" of week "+w, UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		} catch (IllegalOperationException e) {
			//should never happen
		}
		
	}
	
	private void getWorkHoursForActivitiesForWeek() {
		Employee emp = parent.currentEmployee;
		//Gets Activity
		Activity a;
		while(true){
			sys.ui.print("Enter Name or ID of Activity:", UserInterface.style[6]);
			String act = sys.ui.next().toUpperCase();
			a = sys.activityByID(act);
			if(a == null){
				a = sys.activityByName(act);
			}
			if(a == null){
				sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", UserInterface.style[3]);
			}
			else if(!emp.getActivityList().contains(a)){
				sys.ui.print("Error: \""+emp.getInitials()+"\" Not assigned to Activity \"" + act + "\"", UserInterface.style[3]);
			}
			else{
				break;
			}
		}
		//Gets week
		int y = getUserInputInt(a.getStartWeek().getYear(), a.getEndWeek().getYear(), "Enter Year of week", "Invalid year");
		int st = 1;
		int en = 53;
		if(y == a.getStartWeek().getYear()){
			st = a.getStartWeek().getWeek();
		}
		if(y == a.getEndWeek().getYear()){
			en = a.getEndWeek().getWeek();
		}
		int in = getUserInputInt(st, en, "Enter week within Activity \"" + a.getType() + "\"", "Invalid week");
		Week w = new Week(y, in);
		//output
		String[] str = new String[8];
		try {
			double[] d = emp.getWorkHours(a, w);
			for(int i = 0; i<7; i++){
				str[i] = "Weekday "+(i+1)+": "+d[i];
			}
			str[7] = "Total for week: "+d[7];
			sys.ui.clear();
			sys.ui.listDisplay(str, "Work hours of \""+a.getType()+"\" in week "+w, 9);
		} catch (IllegalOperationException e) {
			//should never happen
		}
	}
	
	private void getWorkHoursForActivity() {
		Employee emp = parent.currentEmployee;
		//Gets Activity
		Activity a;
		while(true){
			sys.ui.print("Enter Name or ID of Activity:", UserInterface.style[6]);
			String act = sys.ui.next().toUpperCase();
			a = sys.activityByID(act);
			if(a == null){
				a = sys.activityByName(act);
			}
			if(a == null){
				sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", UserInterface.style[3]);
			}
			else if(!emp.getActivityList().contains(a)){
				sys.ui.print("Error: \""+emp.getInitials()+"\" Not assigned to Activity \"" + act + "\"", UserInterface.style[3]);
			}
			else{
				break;
			}
		}
		//output
		int k = a.getStartWeek().weekDifference(a.getEndWeek())+1;
		String[] str = new String[9*k];
		double[] temp;
		double total = 0.0;
		for(int i = 0; i<k; i++){
			Week w = new Week(a.getStartWeek().getYear(), a.getStartWeek().getWeek()+i);
			str[i*9] = ""+w;
			try {
				temp = emp.getWorkHours(a, w);
				for(int j = 0; j<7; j++){
					str[(i*9) + (j+1)] = "Weekday "+(j+1)+": "+temp[j];
				}
				total += temp[7];
				str[i*9+8] = "Total for week: " + temp[7];
			} catch (IllegalOperationException e) {
				//Should never happen
			}
		}
		sys.ui.clear();
		sys.ui.listDisplay(str, "\""+a.getType()+"\", Total Hours: "+total, 9);
	}
	
	private void getWorkHoursForWeek() {
		Employee emp = parent.currentEmployee;
		int y = getUserInputInt(0,0, "Enter Year of week", "");
		int in = getUserInputInt(1,53, "Enter week.", "Invalid week.");
		Week w = new Week(y, in);
		ArrayList<Activity> a_list = emp.getWeeklyActivities(w);
		//output
		int k = a_list.size();
		String[] str = new String[9*k];
		double[] temp;
		double total = 0.0;
		for(int i = 0; i<k; i++){
			Activity a = a_list.get(i);
			str[i*9] = "Activity \""+a.getType()+"\"";
			try {
				temp = emp.getWorkHours(a, w);
				for(int j = 0; j<7; j++){
					str[(i*9) + (j+1)] = "Weekday "+(j+1)+": "+temp[j];
				}
				total += temp[7];
				str[i*9+8] = "Total for Activity: " + temp[7];
			} catch (IllegalOperationException e) {
				//Should never happen
			}
		}
		sys.ui.clear();
		sys.ui.listDisplay(str, "Hour for "+w+", Total Hours: "+total, 9);
	}

	private void getActivitiesForWeek(){
		Employee emp = parent.currentEmployee;
		int y = getUserInputInt(0,0, "Enter Year of week", "");
		int j = getUserInputInt(1,53, "Enter week.", "Invalid week.");
		Week w = new Week(y, j);
		ArrayList<Activity> list = emp.getWeeklyActivities(w);
		String[] str = new String[list.size()];
		for(int i=0; i<str.length; i++){
			str[i] = list.get(i).getType();
		}
		sys.ui.clear();
		sys.ui.listDisplay(str, "Activies for week "+j, 10);
	}
	
	private void removeEmployee() {
		Employee e = parent.currentEmployee;
		if (sys.ui.yesNoQuestion("Are you sure you want to remove \"" + e.getInitials() + "\" from the system?")) {
			if(sys.getCurrentUser() == e){
				sys.ui.print("Error: Cannot Remove Yourself", UserInterface.style[3]);
			}
			else if(sys.removeEmployee(e)){
				sys.ui.clear();
				sys.ui.print("Successfully removed employee \"" + e.getInitials() + "\" from the system.", UserInterface.style[2]);
				parent.parent.show();
				return;
			}
			else {
				sys.ui.clear();
				sys.ui.print("Error: Cannot remove only Employee in system", UserInterface.style[3]);
			}
		} else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	/*
	 * PROJECT MENUS
	 */
	private void addProject() {
		String name = null;
		boolean b = false;
		while(!b){
			sys.ui.print("Enter name of new Project", UserInterface.style[6]);
			name = sys.ui.next().toUpperCase();
			b = true;
			for(Project pro : sys.getProjectList()){
				if(name.equals(pro.getName())){
					b = false;
					break;
				}
			}
			if(!b){
				sys.ui.print("Error: The name  \"" + name + "\" is already taken.", UserInterface.style[3]);
			}
			else if (name.length() != 4){
				sys.ui.print("Name must be of 4 characters excatly", UserInterface.style[3]);
				b = false;
			}
		}
		int y = getUserInputInt(sys.getDateServer().getToday().getYear(), 0, "Enter Year of starting week", "Invalid year");
		int st = 1;
		int en = 53;
		if(y == sys.getDateServer().getToday().getYear()){
			st = sys.getDateServer().getToday().getWeek();
		}
		int i = getUserInputInt(st, en, "Enter starting week of Project \"" + name + "\"", "Invalid week");
		Week start = new Week(y, i);
		
		y = getUserInputInt(start.getYear(),0, "Enter Year of ending week", "Invalid year");
		if(y == start.getYear()){
			st = start.getWeek();
			en = 53;
		}
		else{
			st = 1;
			en = 53;
		}
		int j = getUserInputInt(st, en, "Enter ending week of Project \"" + name + "\"", "Invalid week");
		Week end = new Week(y, j);
		
		y = getUserInputInt(end.getYear(),0, "Enter Year of deadline week", "Invalid year");
		if(y == end.getYear()){
			st = end.getWeek();
			en = 53;
		}
		else{
			st = 1;
			en = 53;
		}
		int k = getUserInputInt(st, en, "Enter deadline week of Project \"" + name + "\"", "Invalid week");
		Week dL = new Week(y, k);
		
		Project p = new Project(sys, name, start, end, dL);
		if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + name + "\" to the system?")){
			if(sys.addProject(p)){
				sys.ui.clear();
				sys.ui.print("Successfully added Project \"" + name + "\" to the system.", UserInterface.style[2]);
			}
			else{
				sys.ui.print("Error: Invalid Project. Please try again:", UserInterface.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void manageProject(){
		sys.currentMenu = this;
		sys.ui.print("Enter ID of project to manage:", UserInterface.style[6]);
		String name = sys.ui.next().toUpperCase();
		Project p = sys.projectByName(name);
		if(p == null){
			sys.ui.clear();
			sys.ui.print("Error: Project with ID \"" + name + "\" does not exist.", UserInterface.style[3]);
		}
		else{
			Menu manageProject = sys.menus.get(6);
			sys.currentMenu = manageProject;
			manageProject.currentProject = p;
			manageProject.header = "Manage Project \""+p.getName()+"\"";
			sys.ui.clear();
			manageProject.show();
		}
	}

	private void getAllProjects() {
		int sz = sys.getProjectList().size();
		String[] s = new String[sz];
		for (int i = 0; i < sz; i++) {
			s[i] = sys.getProjectList().get(i).getName();
		}
		sys.ui.clear();
		sys.ui.listDisplay(s, "Registered Projects", 10);
	}
	
	/*
	 * PROJECT SUB-MENUS
	 */
	private void setProjectName() {
		Project p = parent.currentProject;
		String name = null;
		boolean b = false;
		while(!b){
			sys.ui.print("Enter new name of Project", UserInterface.style[6]);
			name = sys.ui.next().toUpperCase();
			b = true;
			for(Project pro : sys.getProjectList()){
				if(name.equals(pro.getName())){
					b = false;
					break;
				}
			}
			if(!b){
				sys.ui.print("Error: The name  \"" + name + "\" is already taken.", UserInterface.style[3]);
			}
			else if (name.length() != 4){
				sys.ui.print("Name must be of 4 characters excatly", UserInterface.style[3]);
				b = false;
			}
		}
		if(sys.ui.yesNoQuestion("Are you sure you change \"" + p.getName()+ "\"'s name to \"" + name + "\"?")){
			p.setName(name);
			parent.header = "Manage Project \""+p.getName()+"\"";
			sys.ui.clear();
			sys.ui.print("Successfully changed project name to  \"" + name +"\"", UserInterface.style[2]);
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
		
	}
	
	private void addEmployeeToProject() {
		Project p = parent.currentProject;
		sys.ui.print("Enter initials of employee:", UserInterface.style[6]);
		String initials = sys.ui.next().toUpperCase();
		Employee e = sys.employeeByInitials(initials);
		if(e != null){
			if(p.addEmployee(e)){
				e.assignProject(p);
				sys.ui.clear();
				sys.ui.print("Employee with initials:" + initials + " added to " + p.getName(), UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("\"" + e.getInitials() + "\" is already assigned to \""+ p.getName() +"\"", UserInterface.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Employee with \"" + initials + "\" doesn't exist.", UserInterface.style[3]);
		}
	}

	private void getAllEmployeesOnProject() {
		Project p = parent.currentProject;
		int sz = p.getEmployeeList().size();
		if(sz > 0){
			String[] s = new String[sz];
			for (int i = 0; i < sz; i++) {
				s[i] = p.getEmployeeList().get(i).getInitials();
			}
			sys.ui.clear();
			sys.ui.listDisplay(s, "Registered Employees of \""+p.getName()+"\"", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("No Employees assigned to \"" + p.getName() + "\"", UserInterface.style[3]);
		}
		
	}
	
	private void addProjectActivity() {
		Project p = parent.currentProject;
		sys.ui.print("Enter Name or ID of Activity:", UserInterface.style[6]);
		String act = sys.ui.next().toUpperCase();
		Activity a = sys.activityByID(act);
		if(a == null){
			a = sys.activityByName(act);
		}
		if(a == null){
			sys.ui.clear();
			sys.ui.print("Error: Activity with ID or Name \"" + act + "\" dosen't exist.", UserInterface.style[3]);
		}
		else{
			if(a.assignProject(p)){
				p.addActivity(a);
				sys.ui.clear();
				sys.ui.print("Successfully added \"" + p.getName() + "\" to \""+ act +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("\"" + act + "\" is already assigned to \""+ p.getName() +"\"", UserInterface.style[3]);
			}
		}
	}
	
	private void getAllActivitiesOnProject(){
		Project p = parent.currentProject;
		int sz = p.getActivityList().size();
		if(sz > 0){
			String[] s = new String[sz];
			for (int i = 0; i < sz; i++) {
				s[i] = p.getActivityList().get(i).getType();
			}
			sys.ui.clear();
			sys.ui.listDisplay(s, "Registered Activities of \""+p.getName()+"\"", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("No Activities assigned to \"" + p.getName() + "\"", UserInterface.style[3]);
		}
	}

	private void setProjectStartDate() {
		Project p = parent.currentProject;
		Week now = sys.getDateServer().getToday();
		if(now.compareTo(p.getStartWeek()) < 0){
			int y = getUserInputInt(now.getYear(), p.getEndWeek().getYear(), "Enter Year of starting week", "Invalid year");
			int st = 1;
			int en = 53;
			if(y == now.getYear()){
				st = now.getWeek();
			}
			if(y == p.getEndWeek().getYear()){
				en = p.getEndWeek().getWeek();
			}
			int i = getUserInputInt(st, en, "Enter starting week of Project \"" + p.getName() + "\"", "Invalid week");
			Week w = new Week(y, i);
			if(sys.ui.yesNoQuestion("Are you sure you want to change starting week of \""+p.getName()+"\" from "+p.getStartWeek()+" to "+w+"?")){
				p.setStartWeek(w);
				sys.ui.clear();
				sys.ui.print("Staring week changed to "+i, UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Project has already started, and starting week cannot be changed", UserInterface.style[3]);
		}
	}

	private void setProjectEndDate() {
		Project p = parent.currentProject;
		Week now = sys.getDateServer().getToday();
		if(now.compareTo(p.getEndWeek()) < 0){
			int st = 1;
			int en = 53;
			int y = getUserInputInt(now.getYear(),p.getDeadline().getYear(), "Enter Year of ending week", "Invalid year");
			if(y == p.getStartWeek().getYear()){
				if(p.getStartWeek().compareTo(now) < 0){
					st = now.getWeek();
				}
				else{
					st = p.getStartWeek().getWeek();
				}
			}
			else{
				st = 1;
			}
			if(y == p.getDeadline().getYear()){
				en = p.getDeadline().getWeek();
			}
			else{
				en = 53;
			}
			int i = getUserInputInt(st, en, "Enter ending week of Project \"" + p.getName() + "\"", "Invalid week");
			Week w = new Week(y, i);
			if(sys.ui.yesNoQuestion("Are you sure you want to change ending week of \""+p.getName()+"\" from "+p.getEndWeek()+" to "+w+"?")){
				p.setEndWeek(w);
				sys.ui.clear();
				sys.ui.print("Staring week changed to "+w, UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Project has already Ended, and ending week cannot be changed", UserInterface.style[3]);
		}
		
	}

	private void setPrjocetDeadline() {
		Project p = parent.currentProject;
		Week now = sys.getDateServer().getToday();
		if(now.compareTo(p.getDeadline()) < 0){
			int st = 1;
			int en = 53;
			int y = getUserInputInt(p.getEndWeek().getYear(),0, "Enter Year of deadline week", "Invalid year");
			if(y == p.getEndWeek().getYear()){
				if(p.getEndWeek().compareTo(now) < 0){
					st = now.getWeek();
				}
				else{
					st = p.getEndWeek().getWeek();
				}
			}
			int k = getUserInputInt(st, en, "Enter deadline week of Project \"" + p.getName() + "\"", "Invalid week");
			Week w = new Week(y, k);
			if(sys.ui.yesNoQuestion("Are you sure you want to change deadline week of \""+p.getName()+"\" from "+p.getDeadline()+" to "+w+"?")){
				p.setDeadline(w);
				sys.ui.clear();
				sys.ui.print("Deadline week changed to "+w, UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Project has already ended, and deadline week cannot be changed", UserInterface.style[3]);
		}
	}

	private void setTimeBudgetOfProject() {
		Project p = parent.currentProject;
		double d = getUserInputDouble("Enter timebudget hours on \"" + p.getName() + "\"", "Invalid time value");
		if(sys.ui.yesNoQuestion("Are you sure you want to change the budget of \""+p.getName()+"\" from "+p.getBudget()+" to "+d+"?")){
			p.setBudget(d);
			sys.ui.clear();
			sys.ui.print("Budget changed to " + p.getBudget() + " hours", UserInterface.style[2]);
		}
		else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}

	private void getTotalProjectBudget() {
		Project p = parent.currentProject;
		double d = p.getSpentBudget()[0];
		String[] str = {
				"Hours Avialable: "+p.getBudget(),
				"Hours Spent    : "+d,
				"Hours Left     : "+(p.getBudget()-d),
		};
		sys.ui.clear();
		sys.ui.listDisplay(str, "Budget Status for \""+p.getName()+"\"", str.length);
	}
	
	private void getProjectActiveness() {
		Project p = parent.currentProject;
		if(p.getActivityList().size() > 0){
			double[] list = p.getSpentBudget();
			String[] str = new String[list.length-1];
			for(int i=0; i<str.length; i++){
				str[i] = p.getActivityList().get(i).getType()+": "+list[i+1]+" hours.";
			}
			sys.ui.clear();
			sys.ui.listDisplay(str, "Activeness for \""+p.getName()+"\"", str.length);
		}
		else{
			sys.ui.clear();
			sys.ui.print("Project has no assigned Activities", UserInterface.style[3]);
		}
	}

	private void setProjectReportComment() {
		Project p = parent.currentProject;
		int st = 1;
		int en = 53;
		int y = getUserInputInt(p.getStartWeek().getYear(), p.getDeadline().getYear(), "Enter Year of week", "");
		if(y == p.getStartWeek().getYear()){
			st = p.getStartWeek().getWeek();
		}
		if(y == p.getEndWeek().getYear()){
			en = p.getEndWeek().getWeek();
		}
		int i = getUserInputInt(st, en, "Enter week for report comment", "Invalid week");
		Week w = new Week(y, i);
		sys.ui.print("Enter report comment", UserInterface.style[6]);
		String s = sys.ui.next();
		p.setReportComment(s, w);
		sys.ui.clear();
		sys.ui.print("Comment for "+w+" is set to: \"" + s + "\"", UserInterface.style[2]);
	}

	private void getWeeklyReport() {
		Project p = parent.currentProject;
		int st = 1;
		int en = 53;
		int y = getUserInputInt(p.getStartWeek().getYear(), p.getDeadline().getYear(), "Enter Year of week", "");
		if(y == p.getStartWeek().getYear()){
			st = p.getStartWeek().getWeek();
		}
		if(y == p.getEndWeek().getYear()){
			en = p.getEndWeek().getWeek();
		}
		int i = getUserInputInt(st, en, "Enter week for report comment", "Invalid week");
		Week w = new Week(y, i);
		if(p.getWeeklyReport(w) == null){
			p.setReportComment(" --- --- --- ", w);
		}
		String[] s = {
				"Report Comment for "+w+" is: " +p.getWeeklyReport(w),
				//TODO ADD MORE
		};
		sys.ui.clear();
		sys.ui.listDisplay(s, "Weekly Report, "+w, 1);
	}

	private void removeProject() {
		Project p = parent.currentProject;
		if (sys.ui.yesNoQuestion("Are you sure you want to remove \"" + p.getName() + "\" from the system?")) {
			sys.removeProject(p);
			sys.ui.clear();
			sys.ui.print("Successfully removed Project \"" + p.getName() + "\" from the system.", UserInterface.style[2]);
			parent.parent.show();
			return;
		}
		else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	
	/*
	 * ACTVITY MENUES
	 */
	private void addActivity() {
		String name = null;
		boolean b = false;
		while(!b){
			sys.ui.print("Enter name of new Activity", UserInterface.style[6]);
			name = sys.ui.next().toUpperCase();
			b = true;
			for(Activity act : sys.getActivityList()){
				if(name.equals(act.getType())){
					
					b = false;
					break;
				}
			}
			if(!b){
				sys.ui.print("Error: The name  \"" + name + "\" is already taken.", UserInterface.style[3]);
			}
			else if (name.length() != 4){
				sys.ui.print("Name must be of 4 characters excatly", UserInterface.style[3]);
				b = false;
			}
		}
		int y = getUserInputInt(sys.getDateServer().getToday().getYear(), 0, "Enter Year of starting week", "Invalid year");
		int st = 1;
		int en = 53;
		if(y == sys.getDateServer().getToday().getYear()){
			st = sys.getDateServer().getToday().getWeek();
		}
		int i = getUserInputInt(st, en, "Enter starting week of Activity \"" + name + "\"", "Invalid week");
		Week start = new Week(y, i);
		
		y = getUserInputInt(start.getYear(),0, "Enter Year of ending week", "Invalid year");
		if(y == start.getYear()){
			st = start.getWeek();
			en = 53;
		}
		else{
			st = 1;
			en = 53;
		}
		int j = getUserInputInt(st, en, "Enter ending week of Activity \"" + name + "\"", "Invalid week");
		Week end = new Week(y, j);
		
		Activity a = new Activity(sys, name, start, end);
		if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + name + "\" to the system?")){
			if(sys.addActivity(a)){
				sys.ui.clear();
				sys.ui.print("Successfully added Activity \"" + name + "\" to the system.", UserInterface.style[2]);
			}
			else{
				sys.ui.print("Error: Invalid Activity. Please try again:", UserInterface.style[3]);
			}
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void manageActivity() {
		sys.currentMenu = this;
		sys.ui.print("Enter initials of Activities to manage:", UserInterface.style[6]);
		String name = sys.ui.next().toUpperCase();
		Activity a = sys.activityByName(name);
		if(a == null){
			sys.ui.clear();
			sys.ui.print("Error: Activity \"" + name + "\" does not exist.", UserInterface.style[3]);
		}
		else{
			Menu manageEmployee = sys.menus.get(10);
			sys.currentMenu = manageEmployee;
			manageEmployee.currentActivity = a;
			manageEmployee.header = "Manage Activity \""+a.getType()+"\"";
			sys.ui.clear();
			manageEmployee.show();
		}
	}

	private void getAllActivities() {
		int sz = sys.getActivityList().size();
		String[] s = new String[sz];
		for (int i = 0; i < sz; i++) {
			s[i] = sys.getActivityList().get(i).getType();
		}
		sys.ui.clear();
		sys.ui.listDisplay(s, "Registered Activities", 10);
	}
	
	/*
	 * ACTIVITY SUB-MENUS
	 */
	private void setActivityName() {
		Activity a = parent.currentActivity;
		String name = null;
		boolean b = false;
		while(!b){
			sys.ui.print("Enter new name", UserInterface.style[6]);
			name = sys.ui.next().toUpperCase();
			b = true;
			for(Activity act : sys.getActivityList()){
				if(name.equals(act.getType())){
					
					b = false;
					break;
				}
			}
			if(!b){
				sys.ui.print("Error: The name  \"" + name + "\" is already taken.", UserInterface.style[3]);
			}
			else if (name.length() != 4){
				sys.ui.print("Name must be of 4 characters excatly", UserInterface.style[3]);
				b = false;
			}
		}
		if(sys.ui.yesNoQuestion("Are you sure you change \"" + a.getType() + "\"'s name to \"" + name + "\"?")){
			a.setType(name);
			parent.header = "Manage Activity \""+a.getType()+"\"";
			sys.ui.clear();
			sys.ui.print("Successfully changed activity name to  \"" + name +"\"", UserInterface.style[2]);
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void addEmployeeToActivity() {
		Activity a = parent.currentActivity;
		sys.ui.print("Enter initials of Employee:", UserInterface.style[6]);
		String emp = sys.ui.next().toUpperCase();
		Employee e = sys.employeeByInitials(emp);
		if(e == null){
			sys.ui.clear();
			sys.ui.print("Error: Employee with initials \"" + emp + "\" dosen't exist.", UserInterface.style[3]);
		}
		else{
			if(a.assignEmployee(e)){
				e.assignActivity(a);
				sys.ui.clear();
				sys.ui.print("Successfully added \"" + e.getInitials() + "\" to \""+ a.getType() +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.print("\"" + e.getInitials() + "\" is already assigned to \""+ a.getType() +"\"", UserInterface.style[3]);
			}
		}
	}
	
	private void getEmployeeListForActivity() {
		Activity a = parent.currentActivity;
		List<Employee> list = a.getEmployeeList();
		if(list.size() > 0){
			String[] str = new String[list.size()];
			for(int i=0; i<str.length; i++){
				str[i] = list.get(i).getInitials();
			}
			sys.ui.clear();
			sys.ui.listDisplay(str, "Registered Employees", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("No Employees assigned to \"" + a.getType() + "\"", UserInterface.style[3]);
		}
	}
	
	private void addActivityToProject() {
		Activity a = parent.currentActivity;
		sys.ui.print("Enter Name or ID of Project:", UserInterface.style[6]);
		String project = sys.ui.next().toUpperCase();
		Project p = sys.projectByID(project);
		if(p == null){
			p = sys.projectByName(project);
		}
		if(p == null){
			sys.ui.clear();
			sys.ui.print("Error: Project with ID or Name \"" + project + "\" dosen't exist.", UserInterface.style[3]);
		}
		else{
			p.addActivity(a);
			a.assignProject(p);
			sys.ui.clear();
			sys.ui.print("Successfully added \"" + a.getType() + "\" to \""+ project +"\"", UserInterface.style[2]);
		}
	}
	
	private void getAssignedProjectsForActivity() {
		Activity a = parent.currentActivity;
		List<Project> list = a.getProjectList();
		if(list.size() > 0){
			String[] str = new String[list.size()];
			for(int i=0; i<str.length; i++){
				str[i] = list.get(i).getName();
			}
			sys.ui.clear();
			sys.ui.listDisplay(str, "Registered Projects", 10);
		}
		else{
			sys.ui.clear();
			sys.ui.print("\"" + a.getType() + "\" not assigned to any Projects", UserInterface.style[3]);
		}
	}	
	
	private void setStartDateOfActivity() {
		Activity a = parent.currentActivity;
		Week now = sys.getDateServer().getToday();
		if(now.compareTo(a.getStartWeek()) < 0){
			int y = getUserInputInt(now.getYear(), a.getEndWeek().getYear(), "Enter Year of starting week", "Invalid year");
			int st = 1;
			int en = 53;
			if(y == now.getYear()){
				st = now.getWeek();
			}
			if(y == a.getEndWeek().getYear()){
				en = a.getEndWeek().getWeek();
			}
			int i = getUserInputInt(st, en, "Enter starting week of Activity \"" + a.getType() + "\"", "Invalid week");
			Week w = new Week(y, i);
			if(sys.ui.yesNoQuestion("Are you sure you want to change starting week of \""+a.getType()+"\" from "+a.getStartWeek()+" to "+w+"?")){
				a.setStartWeek(w);
				sys.ui.clear();
				sys.ui.print("Successfully change starting week of \"" + a.getType() + "\" to \""+ w +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Activity has already started, and starting week cannot be changed", UserInterface.style[3]);
		}
	}

	private void setEndDateOfActivity() {
		Activity a = parent.currentActivity;
		Week now = sys.getDateServer().getToday();
		if(now.compareTo(a.getEndWeek()) <= 0){
			int st = 1;
			int en = 53;
			int y = getUserInputInt(now.getYear(),0, "Enter Year of ending week", "Invalid year");
			if(y == a.getStartWeek().getYear()){
				if(a.getStartWeek().compareTo(now) < 0){
					st = now.getWeek();
				}
				else{
					st = a.getStartWeek().getWeek();
				}
			}
			else{
				st = 1;
			}
			int i = getUserInputInt(st, en, "Enter ending week of Project \"" + a.getType() + "\"", "Invalid week");
			Week w = new Week(y, i);
			if(sys.ui.yesNoQuestion("Are you sure you want to change ending week of \""+a.getType()+"\" from "+a.getEndWeek()+" to "+w+"?")){
				a.setEndWeek(w);
				sys.ui.clear();
				sys.ui.print("Successfully changed ending week of \"" + a.getType() + "\" to \""+ w +"\"", UserInterface.style[2]);
			}
			else{
				sys.ui.clear();
				sys.ui.cancel();
			}
		}
		else{
			sys.ui.clear();
			sys.ui.print("Activity has already ended, and ending week cannot be changed", UserInterface.style[3]);
		}
	}
	
	private void changeBudgetActivity() {
		Activity a = parent.currentActivity;
		double d = getUserInputDouble("Enter new hour-budget","Invalid value");
		if(sys.ui.yesNoQuestion("Are you sure you want to change hour-budget of \""+a.getType()+"\" from "+a.getHourBudget()+" to "+d+"?")){
			a.setHourBudget(d);
			sys.ui.clear();
			sys.ui.print("Successfully changed hour-budget of \""+a.getType()+"\" from "+a.getHourBudget()+" to "+d, UserInterface.style[2]);
		}
		else{
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	private void getHoursSpentActivity() {
		Activity a = parent.currentActivity;
		String[] str = {
				"Hours Availeble: "+a.getHourBudget(),
				"Hours Spent    : "+a.getSpentBudget(),
				"Hours Left     : "+(a.getHourBudget()-a.getSpentBudget())
		};
		sys.ui.clear();
		sys.ui.listDisplay(str, "Budget Status for \""+a.getType()+"\"", str.length);
	}
	
	private void getActivityStatusByWeek() {
		Activity a = parent.currentActivity;
		int y = getUserInputInt(a.getStartWeek().getYear(), a.getEndWeek().getYear(), "Enter Year of week", "Invalid year");
		int st = 1;
		int en = 53;
		if(y == a.getStartWeek().getYear()){
			st = a.getStartWeek().getWeek();
		}
		if(y == a.getEndWeek().getYear()){
			en = a.getEndWeek().getWeek();
		}
		int in = getUserInputInt(st, en, "Enter week within Activity \"" + a.getType() + "\"", "Invalid week");
		Week w = new Week(y, in);
		int k = a.getEmployeeList().size();
		String[] str = new String[k*9];
		double total = 0.0;
		for(int i=0; i<k; i++){
			try {
				Employee e = a.getEmployeeList().get(i);
				double[] temp = e.getWorkHours(a, w);
				str[i*9] = e.getInitials()+":";
				for(int j=0; j<7; j++){
					str[(i*9)+(j+1)] = "Weekday "+(j+1)+": "+temp[j];
				}
				total += temp[7];
				str[i*9+8] = "Total hours: "+temp[7];
			} catch (IllegalOperationException e1) {
				//should never happen
			}
		} 
		sys.ui.clear();
		sys.ui.listDisplay(str, "Hours Spent by Employees in week "+in+" on Activity \""+a.getType()+"\", Total hours: "+total, 9);
	}
	
	private void getActivityStatus() {
		Activity a = parent.currentActivity;
		int k = a.getEmployeeList().size();
		String[] str = new String[k];
		for(int i=0; i<k; i++){
			Employee emp = a.getEmployeeList().get(i);
			int weekcount = a.getStartWeek().weekDifference(a.getEndWeek())+1;
			double count = 0.0;
			for(int j=0; j<weekcount; j++) {
				try {
					Week w = new Week(a.getStartWeek().getYear(), a.getStartWeek().getWeek()+j);
					count += emp.getWorkHours(a, w)[7];
				} catch (IllegalOperationException e) {
					System.out.println("ERROR");
				}
			}
			str[i] = emp.getInitials()+": "+count;
		}
		sys.ui.clear();
		sys.ui.listDisplay(str, "Total Hours Spent by Employees on \""+a.getType()+"\"", 10);
	}
	
	private void removeActivity() {
		Activity a = parent.currentActivity;
		if (sys.ui.yesNoQuestion("Are you sure you want to remove \"" + a.getType() + "\" from the system?")) {
			sys.removeActivity(a);
			sys.ui.clear();
			sys.ui.print("Successfully removed Activity \"" + a.getType() + "\" from the system.", UserInterface.style[2]);
			parent.parent.show();
			return;
		}
		else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	/*
	 * OTHER MENUES
	 */
	public void ShowFuckingLogs() {
		String[] s = new String[44];
		for (int i = 0; i < s.length; i++) {
			s[i] = Integer.toString(i);
		}
		sys.ui.clear();
		sys.ui.listDisplay(s, "Log of events", 10);
	}
	
	public void showDate() {
		sys.ui.clear();
		sys.ui.print(sys.getDateServer().getToday().toString(), UserInterface.style[2]);
	}
	
	public void logOff(String s) {
		sys.ui.clear();
		sys.logoff();
		sys.ui.print(s, UserInterface.style[4]);
		sys.ui.print("Welcome. Please enter your initials to proceed:", UserInterface.style[6]);
		while (!sys.loggedIn()) {
			try {
				loginUI(sys.ui.next());
			} catch (Exception e) {
				sys.ui.print("Error: Action denied. Please try again:", UserInterface.style[3]);
			}
		}
		sys.mainmenu.show();
	}
	
	public boolean loginUI(String initials){
		if (!sys.loggedIn()){
			for(Employee e : sys.getEmployeeList()){
				if(e.getInitials().equals(initials.toUpperCase())){
					sys.ui.clear();
					sys.ui.print("Successfully logged in as \"" + initials.toUpperCase() + "\".", UserInterface.style[2]);
					sys.login(e);
					return true;
				}
			}
			sys.ui.print("Error: No employee with initials \"" + initials.toUpperCase() + "\". Please try again:", UserInterface.style[3]);
			return false;
		}
		sys.ui.print("Error: An employee is already logged in.", UserInterface.style[3]);
		return false;
	}
}