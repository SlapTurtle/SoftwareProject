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
		case "Remove Employee": break;
		case "Manage Project": manageProject(); break;
		case "Set Font Size": sys.ui.setFontSize(); break;
		case "Add Activity":
			String name = null;
			Boolean b;
			int i;
			
			b = false;
			while(!b){
				sys.ui.print("Enter name of new Activity:", sys.ui.style[6]);
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
					sys.ui.print("Enter starting week of Activity \"" + name + "\"", sys.ui.style[6]);
					i = Integer.parseInt(sys.ui.next());
					b = i > 0 && i <= 53;
					if(!b){
						throw new NumberFormatException();
					}
				} catch(NumberFormatException e){
					sys.ui.invalidInput();
				}
			}
			Week start = sys.getDateServer().getWeek(i);
			
			i = -1;
			b = false;
			while(!b){
				try{
					sys.ui.print("Enter ending week of Activity \"" + name + "\"", sys.ui.style[6]);
					i = Integer.parseInt(sys.ui.next());
					b = i > 0 && i <= 53 && start.compareTo(sys.getDateServer().getWeek(i)) <= 0;
					if(!b){
						throw new NumberFormatException();
					}
				} catch(NumberFormatException e){
					sys.ui.invalidInput();
				}
			}
			Week end = sys.getDateServer().getWeek(i);
			
			Activity A = new Activity(String.valueOf(sys.getIDCount()), start, end);
			A.type = name; //A.setType(name);
			if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + A.type + "\" to the system?")){
				if(sys.addActicity(A)){
					sys.ui.clear();
					sys.ui.print("Successfully added Activity \"" + name + "\" to the system.", sys.ui.style[2]);
				}
				else{
					sys.ui.invalidInput();
				}
			}
			else{
				sys.ui.cancel();
			}
			break;
	
		default:
			sys.ui.clear();
			sys.ui.print("Error: Unidentified action performed.", sys.ui.style[3]); break;
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
		if (sys.addEmployee(employee)) {
			if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + initials + "\" to the system?")){
				sys.ui.clear();
				sys.ui.print("Successfully added employee \"" + initials + "\" to the system.", sys.ui.style[2]);
			}
			else{
				sys.ui.cancel();
			}
		} else {
			sys.ui.print("Error: Employee with initials \"" + initials + "\" already exists.", sys.ui.style[3]);
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
	
	private void AssignToActivity(){
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
	
	private void setWorkhousForActivityforWeek(){
		
	}
	
	private void getActivitiesForWeek(){
		
	}
	
	private void remove(){
		
	}
	
	/*
	 * END OG EMPLOYEE MENUES
	 */

}
