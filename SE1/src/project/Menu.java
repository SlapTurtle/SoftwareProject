package project;

import java.lang.reflect.Method;

public class Menu {

	SysApp sys;
	String header;
	Menu[] m;
	Menu currentPrevious;
	Method method;
	boolean showHeader;
	boolean returnOption;
	
	public Menu(SysApp sys, String header, Method method) {
		this.sys = sys;
		this.header = header;
		this.method = method;
	}
	
	public Menu(SysApp sys, String header, Menu[] m, boolean showHeader, boolean returnOption) {
		this.sys = sys;
		this.header = header;
		this.m = m;
		this.showHeader = showHeader;
		this.returnOption = returnOption;
	}
	
	public void show() {
		// if !method
		currentPrevious = sys.currentMenu;
		sys.currentMenu = this;
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
		//if (m == null) { return; }
		boolean b = false;
		String str;
		while (!b) {
			str = sys.ui.next().toLowerCase();
			if (returnOption && (str.equals("return") || str.equals(Integer.toString(m.length+1)))) {
				currentPrevious.show(); 
				break;
			}
			for (int i = 0; i < m.length; i++) {
				if (str.equals(m[i].header.toLowerCase()) || str.equals(Integer.toString(i + 1))) {
					b = true;
					m[i].show();
					break;
				}
			}
			if (!b) { sys.ui.print("Error: Invalid input. Please try again:", sys.ui.style[3]); }
		}
	}
	
	
	
}
