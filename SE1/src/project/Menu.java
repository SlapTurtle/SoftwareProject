package project;

public class Menu {

	SysApp sys;
	String header;
	Menu[] m;
	Menu currentPrevious;
	Menu parent;
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
		currentPrevious = sys.currentMenu;
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
		try{
			switch (header) {
			case "Exit": System.exit(0); return;
			
			case "Help": sys.ui.help(); break;
			
			case "Add Employee": 
				sys.ui.print("Enter initials of new employee:");
				String initials = sys.ui.next().toUpperCase();
				Employee employee = new Employee(initials);
				if (sys.addEmployee(employee)) {
					if(sys.ui.yesNoQuestion("Are you sure you want to add \"" + initials + "\" to the system?")){
						sys.ui.clear();
						sys.ui.print("Successfully added employee \"" + initials + "\" to the system.", sys.ui.style[2]);
					}
					else{
						throw new Exception("!cancel");
					}
					
				} else {
					sys.ui.print("Error: Employee with initials \"" + initials + "\" already exists.", sys.ui.style[3]);
				}
				break;
				
			case "Remove Employee": break;
			case "Set Font Size": sys.ui.setFontSize(); break;
			
			case "Add Activity":
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
				
				Activity A = new Activity(String.valueOf(sys.getIDCount()), start, end);
				A.type = name; //A.setType(name);
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
					throw new Exception("!cancel");
				}
				break;
				
			/*case "Manage Project": break;
			case "Add Activity": break;
			case "Show Logs": break;*/
				
			default: sys.ui.print("Error: Unidentified action performed.", sys.ui.style[3]); break;
			}
			
		} catch (Exception e){
			switch (e.getMessage()){
			case "!cancel":
				sys.ui.clear();
				sys.ui.print("Action canceled", sys.ui.style[5]);
				break;
				
			case "!restart":
				sys.ui.print("Action restarted", sys.ui.style[5]);
				runMethod();
				return;
				
			default: sys.ui.print("Error: Unidentified Error, "+e.getMessage()+", performed.", sys.ui.style[3]); break;
			}
		}
		parent.show();
	}
	
}
