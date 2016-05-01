package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

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
		new MessageStyle(Font.BOLD, Color.WHITE, Color.BLUE.darker()), //header
		new MessageStyle(Font.PLAIN, Color.WHITE, Color.GREEN.darker()) //information
	};
	public String[] actionbreakers = {
			"!cancel",
			"!restart"
	};
	public String latestInput = "";
	public final Object lock = new Object();
	private boolean wakeUp = false;
	
	public UserInterface(SysApp sys) {
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
		input.obj.setBounds(24, offset + HEIGHT, UserInterface.dim.width - 60, 20);
		return msg;
	}
	
	public Message print(String message, MessageStyle style) {
		if (offset + 8 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, style, offset);
		game.add(msg.lbl);
		console.add(msg);
		latestInput = message;
		input.obj.setBounds(26, offset + HEIGHT, UserInterface.dim.width - 60, HEIGHT);
		return msg;
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
	
	public String next() throws Exception{
		synchronized(lock) {
			while (!wakeUp) {
				try {
					lock.wait();
				} catch (InterruptedException e) {}
			}
		}
		wakeUp = false;
		for(String s : actionbreakers){
			if (s.equals(latestInput)){
				throw new Exception(latestInput.toLowerCase());
			}
		}
		return latestInput;
	}
	
	public boolean wakeUpThread() {
		wakeUp = true;
		return wakeUp;
	}
	
	public void setFontSize() throws Exception {
		print("Enter your desired new font size:");
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
				print("Error: Invalid input. Please try again:", style[3]);
			}
		}
	}
	
	public void help() {
		clear();
		print("Available Commands", style[4]);
		print("\"help\" - displays a list of commands");
		print("\"clear\" - clears the console");
		print("\"exit\" - terminates the current session");
		print("\"!cancel\" - cancels the current action");
		print("\"!restart\" - restart the current action");
	}
	
	public boolean yesNoQuestion(String message) throws Exception{
		this.print(message + " (yes / no)");
		while(true){
			String s = this.next();
			if(s.equals("y") || s.equals("yes")){
				return true;
			}
			else if(s.equals("n") || s.equals("no")){
				return false;
			}
		}
	}
	
}
