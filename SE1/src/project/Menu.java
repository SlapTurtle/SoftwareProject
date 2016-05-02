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
			if (!b) { sys.ui.print("Error: Invalid input. Please try again:", sys.ui.style[3]); }
		}
	}
	
	public void runMethod() {
		String initials;
		switch (header) {
		case "Exit": System.exit(0); return;
		case "Help": sys.ui.help(); break;		
		case "Add Employee": addEmployee(); break;
		case "Manage Employee": manageEmployee(); break;
		case "Assign To Project": assignToProject(); break;
		case "Assign To Activity": assignToActivity(); break;
		case "Set Work Hours For Activities For Week": setWorkhousForActivityForWeek(); break;
		case "Get Activities for Week": getActivitiesForWeek(); break;
		case "Remove Employee": removeEmployee(); break;
		case "Manage Project": manageProject(); break;
		case "Set Font Size": sys.ui.setFontSize(); break;
		case "Show Logs": ShowFuckingLogs(); break;
		default: break;
		}
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
			manageEmployee.show();
		}
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
			manageProject.show();
		}
	}
	
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
	
	private void setWorkhousForActivityForWeek(){
		//Gets Activity
		Activity a;
		while(true){
			sys.ui.print("Enter Name or ID of Activity:");
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
		int i = -1;
		while(true){
			try{
				sys.ui.print("Enter week within Activity \"" + a.type + "\"");
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
		//Gets weekday
		int j = -1;
		while(true){
			try{
				sys.ui.print("Enter weekday (1-7)");
				j = Integer.parseInt(sys.ui.next());
				if(!(j > 0 && j <= 7)){
					throw new NumberFormatException();
				}
				else{
					break;
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: Invalid weekday.", sys.ui.style[3]);
			}
		}
		//Gets Hours
		double d = 0;
		while(true){
			try{
				sys.ui.print("Enter amount of hours of work to to \"" + a.type + "\" in week "+w.getWeek());
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
	
	private void removeEmployee(){
		sys.ui.print("Enter initials of employee to remove:");
		String initials = sys.ui.next().toUpperCase();
		Employee employee = new Employee(initials);
		if (sys.ui.yesNoQuestion("Are you sure you want to remove \"" + initials + "\" from the system?")) {
			if(sys.getEmployeeList().remove(employee)){
				sys.ui.clear();
				sys.ui.print("Successfully removed employee \"" + initials + "\" from the system.", sys.ui.style[2]);
			}
			else{
				sys.ui.print("Error: Employee with initials \"" + initials + "\" doesn't exists.", sys.ui.style[3]);
			}
			
		} else {
			sys.ui.clear();
			sys.ui.cancel();
		}
	}
	
	/*
	 * END OF EMPLOYEE MENUES
	 */
	
	public void ShowFuckingLogs() {
		String[] s = new String[44];
		for (int i = 0; i < s.length; i++) {
			s[i] = Integer.toString(i);
		}
		sys.ui.listDisplay(s, "Logs And Shit", 10);
	}
	
	/*
	 * ACTVITY MENUES
	 */
	private void addActivity() {
		String name = null;
		Boolean b;
		int i;
		
		b = false;
		while(!b){
			sys.ui.print("Enter name of new Activity:");
			name = sys.ui.next();
			/*  does name meet the criteria ?
			b = (name == acceptable);
			if(!b) {
				{sys.ui.print("Error: Invalid name. Please try again:", sys.ui.style[3]);
		 	}
		 	*/
			b = true;
		}
		
		i = -1;
		b = false;
		while(!b){
			try{
				sys.ui.print("Enter starting week of Activity \"" + name + "\"");
				i = Integer.parseInt(sys.ui.next());
				b = i > 0 && i <= 53;
				if(!b){
					throw new NumberFormatException();
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: Invalid week. Please try again:", sys.ui.style[3]);
			}
		}
		Week start = sys.getDateServer().getWeek(i);
		
		i = -1;
		b = false;
		while(!b){
			try{
				sys.ui.print("Enter ending week of Activity \"" + name + "\"");
				i = Integer.parseInt(sys.ui.next());
				b = i > 0 && i <= 53 && start.compareTo(sys.getDateServer().getWeek(i)) <= 0;
				if(!b){
					throw new NumberFormatException();
				}
			} catch(NumberFormatException e){
				sys.ui.print("Error: Invalid week. Please try again:", sys.ui.style[3]);
			}
		}
		Week end = sys.getDateServer().getWeek(i);
		
		Activity A = new Activity(sys, name, start, end);
		if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + A.type + "\" to the system?")){
			if(sys.addActicity(A)){
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
}
