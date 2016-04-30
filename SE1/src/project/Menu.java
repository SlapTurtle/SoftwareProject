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
			if (!b) { sys.ui.print("Error: Invalid input. Please try again:", sys.ui.style[3]); }
		}
	}
	
	public void runMethod() {
		switch (header) {
		case "Exit": System.exit(0); break;
		case "Help": sys.ui.help(); break;
		case "Add Employee": sys.addEmployee(); break;
		case "Remove Employee": break;
		case "Set Font Size": sys.ui.setFontSize(); break;
		/*case "Add Project": break;
		case "Manage Project": break;
		case "Add Activity": break;
		case "Show Logs": break;*/
		default: sys.ui.print("Error: Unidentified action performed.", sys.ui.style[3]); break;
		}
		parent.show();
	}
	
}
