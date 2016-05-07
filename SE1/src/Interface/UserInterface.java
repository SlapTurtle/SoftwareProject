package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Project.SysApp;
 
public class UserInterface {
	public SysApp sys;
	public JFrame frame = new JFrame("UI");
	public JLayeredPane game = frame.getLayeredPane();
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static int HEIGHT = 30;
	public ArrayList<Message> console = new ArrayList<Message>();
	public InputField input;
	public int offset = 0;
	public static MessageStyle[] style = new MessageStyle[]{
		new MessageStyle(Font.PLAIN, Color.BLACK, Color.WHITE), // Standard print format
		new MessageStyle(Font.ITALIC, Color.DARK_GRAY, Color.WHITE), // User input
		new MessageStyle(Font.PLAIN, Color.BLACK, Color.GREEN.brighter()), // Green notification
		new MessageStyle(Font.PLAIN, Color.WHITE, Color.RED), // Error message
		new MessageStyle(Font.BOLD, Color.WHITE, Color.BLUE.darker()), // Header
		new MessageStyle(Font.PLAIN, Color.BLACK, Color.yellow.brighter()), // confirmations
		new MessageStyle(Font.BOLD, Color.BLACK, Color.WHITE) //promt
	};
	public String latestInput = "";
	public final Object lock = new Object();
	private boolean wakeUp = false;
	
	public UserInterface(SysApp sys) {
		this.sys = sys;
		dim.height -= 100;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		input = new InputField(sys, dim.height - 6 * HEIGHT);
		game.add(input.obj);
		input.obj.requestFocusInWindow();
		print("A new session has been initialized.", style[2]);
	}
	
	public Message print(String message) {
		if (offset + 8 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, style[0], offset);
		game.add(msg.lbl);
		console.add(msg);
		latestInput = message;
		input.obj.setBounds(28, offset + HEIGHT, UserInterface.dim.width - 60, 20);
		return msg;
	}
	
	public Message print(String message, MessageStyle style) {
		if (offset + 8 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, style, offset);
		game.add(msg.lbl);
		console.add(msg);
		latestInput = message;
		input.obj.setBounds(28, offset + HEIGHT, UserInterface.dim.width - 60, HEIGHT);
		return msg;
	}
	
	public void invalidInput() {
		sys.ui.print("Error: Invalid week. Please try again:", sys.ui.style[3]);
	}
	
	public void clear() {
		while (console.size() > 0) {
			shiftUp();
		}
		offset = 0;
	}
	
	public void shiftUp() {
		game.remove(console.get(0).lbl);
		console.remove(0);
		offset -= HEIGHT;
		for (int i = 0; i < console.size(); i++) {
			console.get(i).offset -= HEIGHT;
			console.get(i).lbl.setBounds(20, console.get(i).offset, console.get(i).lbl.getWidth(), console.get(i).lbl.getHeight());
		}
	}
	
	public String next() {
		synchronized(lock) {
			while (!wakeUp) {
				try {
					lock.wait();
				} catch (InterruptedException e) { }
			}
		}
		wakeUp = false;
		return latestInput;
	}
	
	public String next(boolean b) throws ActionCancelledException {
		synchronized(lock) {
			while (!wakeUp) {
				try {
					lock.wait();
				} catch (InterruptedException e) { }
			}
		}
		wakeUp = false;
		if (latestInput.toLowerCase().equals("!cancel")) { throw new ActionCancelledException(sys); }
		return latestInput;
	}
	
	public boolean wakeUpThread() {
		wakeUp = true;
		return wakeUp;
	}
	
	public void setFontSize() {
		print("Enter your desired new font size:", style[6]);
		while (true) {
			String s = next();
			try {
				int i = Integer.parseInt(s);
				if (i >= 10 && i <= 20) {
					clear();
					HEIGHT = i + 16;
					print("Font size has been set to " + i + ".", style[2]);
					break;
				} else {
					print("Error: Only integers in range 10-20 permitted. Please try again:", style[3]);
				}
			} catch(NumberFormatException e) {
				invalidInput();
			}
		}
	}

	public void listDisplay(String[] l, String header, int size) {
		int i = 0,j = 0, k;
		while (i < l.length) {
			j = 0;
			if (i+size > l.length) { k = l.length; }
			else { k = i+size; }
			print(header + "  (" + (int)(k) + " / " + (int)(l.length) + ")", style[4]);
			while (j < size && i < l.length) {
				print(l[i]);
				i++;
				j++;
			}
			if (i == l.length) { break; }
			print("Press enter to proceed to next page in list.", style[6]);
			next();
			clear();
		}
		print("Press enter to return from list view.", style[6]);
		next();
		clear();
	}
	
	public void help() {
		clear();
		print("Available Commands", style[4]);
		print("\"!help\" - displays a list of commands");
		print("\"!clear\" - clears the console");
		print("\"!exit\" - terminates the current session");
		print("\"!cancel\" - cancels the current action");
	}
	
	public void cancel() {
		clear();
		print("Action has been cancelled.", style[3]);
		sys.currentMenu.parent.show();
	}
	
	public boolean yesNoQuestion(String message){
		print(message + " (y / n)", style[5]);
		while(true){
			String s = next();
			if(s.equals("y") || s.equals("yes")){
				return true;
			}
			else if(s.equals("n") || s.equals("no")){
				return false;
			}
			invalidInput();
		}
	}
	
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public void initializeMenus(){
		
		ArrayList<Menu> menuEmpMng = new ArrayList<Menu>();
		ArrayList<Menu> menuPrjMng = new ArrayList<Menu>();
		ArrayList<Menu> menuActMng = new ArrayList<Menu>();
		
			
			menuEmpMng.add(new Menu(sys, "Assign To Project"));
			menuEmpMng.add(new Menu(sys, "Get All Assigned Projects"));
			menuEmpMng.add(new Menu(sys, "Get Manager Assigned Projects"));
			menuEmpMng.add(new Menu(sys, "Assign To Activity"));
			menuEmpMng.add(new Menu(sys, "Get All Assigned Activities"));
			menuEmpMng.add(new Menu(sys, "Set Work Hours For Activity By Week"));
			menuEmpMng.add(new Menu(sys, "Get Work Hours For Activity By Week"));
			menuEmpMng.add(new Menu(sys, "Get Work Hours For Activity"));
			menuEmpMng.add(new Menu(sys, "Get Work Hours For Week"));
			menuEmpMng.add(new Menu(sys, "Get Activities For Week"));
			menuEmpMng.add(new Menu(sys, "Remove Employee"));
			
			menuPrjMng.add(new Menu(sys, "Assign Manager"));
			menuPrjMng.add(new Menu(sys, "Set Project Name"));
			menuPrjMng.add(new Menu(sys, "Add Employee to Project"));
			menuPrjMng.add(new Menu(sys, "Get All Employees on Project"));
			menuPrjMng.add(new Menu(sys, "Add Project Activity"));
			menuPrjMng.add(new Menu(sys, "Get All Activities on Project"));
			menuPrjMng.add(new Menu(sys, "Set Start Date of Project"));
			menuPrjMng.add(new Menu(sys, "Set End Date of Project"));
			menuPrjMng.add(new Menu(sys, "Set Deadline of Project"));
			menuPrjMng.add(new Menu(sys, "Set Time Budget of Project"));
			menuPrjMng.add(new Menu(sys, "Get Total Project Budget Price"));
			menuPrjMng.add(new Menu(sys, "Get Activeness of Activity in Project"));
			menuPrjMng.add(new Menu(sys, "Set Report Comment"));
			menuPrjMng.add(new Menu(sys, "Get Weekly Report"));
			menuPrjMng.add(new Menu(sys, "Remove Project"));
			
			menuActMng.add(new Menu(sys, "Set Activity Name"));
			menuActMng.add(new Menu(sys, "Add Employee to Activity"));
			menuActMng.add(new Menu(sys, "Get All Employees on Activity"));
			menuActMng.add(new Menu(sys, "Add Activity to Project"));
			menuActMng.add(new Menu(sys, "Get Assigned Projects"));
			menuActMng.add(new Menu(sys, "Set Start Date Of Activity"));
			menuActMng.add(new Menu(sys, "Set End Date of Activity"));
			menuActMng.add(new Menu(sys, "Set Time Budget"));
			menuActMng.add(new Menu(sys, "Get Hours Spent on Activity"));
			menuActMng.add(new Menu(sys, "Get Activity Status By Week"));
			menuActMng.add(new Menu(sys, "Get Activity Status"));
			menuActMng.add(new Menu(sys, "Remove Activity"));
			
			menus.add(new Menu(sys, "Add Employee")); // 0
			menus.add(new Menu(sys, "Manage Employee"));
			menus.add(new Menu(sys, "Manage Employee", menuEmpMng.toArray(new Menu[menuEmpMng.size()]), true, true));
			menus.add(new Menu(sys, "Get All Employees"));
			menus.add(new Menu(sys, "Add Project")); // 4
			menus.add(new Menu(sys, "Manage Project"));
			menus.add(new Menu(sys, "Manage Project", menuPrjMng.toArray(new Menu[menuPrjMng.size()]), true, true));
			menus.add(new Menu(sys, "Get All Projects"));
			menus.add(new Menu(sys, "Add Activity")); // 8
			menus.add(new Menu(sys, "Manage Activity"));
			menus.add(new Menu(sys, "Manage Activity", menuActMng.toArray(new Menu[menuActMng.size()]), true, true));
			menus.add(new Menu(sys, "Get All Activities"));
			menus.add(new Menu(sys, "Show Logs"));	// 12
			menus.add(new Menu(sys, "Show Date"));
			menus.add(new Menu(sys, "Set Font Size"));
			Menu[] m = new Menu[] {
					new Menu(sys, "Employees", new Menu[] {menus.get(0), menus.get(1), menus.get(3)}, true, true),
					new Menu(sys, "Projects", new Menu[] {menus.get(4), menus.get(5), menus.get(7)}, true, true),
					new Menu(sys, "Activities", new Menu[] {menus.get(8), menus.get(9), menus.get(11)}, true, true),
					new Menu(sys, "System", new Menu[] {menus.get(12), menus.get(13)}, true, true ),
					new Menu(sys, "Settings", new Menu[] {menus.get(14)}, true, true),
					new Menu(sys, "Help"),
					new Menu(sys, "Log Off"),
					new Menu(sys, "Exit"),
			};
			sys.mainmenu = new Menu(sys, "Main Menu", m, true, false);
			menus.get(2).parent = m[0];
			menus.get(6).parent = m[1];
			menus.get(10).parent = m[2];
	}
	
}
