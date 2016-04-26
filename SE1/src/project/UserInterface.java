package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class UserInterface {
	
	public JFrame frame = new JFrame("UI");
	public JLayeredPane game = frame.getLayeredPane();
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static int HEIGHT = 24;
	public ArrayList<Message> console = new ArrayList<Message>();
	public InputField input;
	public int offset = 0;
	public MessageStyle[] style = new MessageStyle[]{
		new MessageStyle(Font.PLAIN, Color.BLACK, Color.WHITE), // Standard print format
		new MessageStyle(Font.ITALIC, Color.DARK_GRAY, Color.WHITE), // User input
		new MessageStyle(Font.PLAIN, Color.BLACK, Color.GREEN.brighter()), // Green notification
		new MessageStyle(Font.PLAIN, Color.WHITE, Color.RED) // Error message
	};
	public String latestInput = "";
	public final Object lock = new Object();
	private boolean wakeUp = false;
	
	public UserInterface() {
		dim.height -= 100;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		input = new InputField(dim.height - 6 * HEIGHT);
		game.add(input.obj);
		input.obj.requestFocusInWindow();
		print("A new session has been initialized.", style[2]);
		//print("Example use");
		//print("User Input", style[1]);
		//print("Error: You fucked up, son.", style[3]);
		//print("line 3");  
		//clear();
		//shiftUp();
	/*	for (int i = 0; i < 71; i++) {
			print("message " + i);
		}	*/
	}
	
	public Message print(String message) {
		if (offset + 8 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, style[0], offset);
		game.add(msg.lbl);
		console.add(msg);
		latestInput = message;
		return msg;
	}
	
	public Message print(String message, MessageStyle style) {
		if (offset + 8 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, style, offset);
		game.add(msg.lbl);
		console.add(msg);
		latestInput = message;
		return msg;
	}
	
	public void clear() {
		while (console.size() > 0) {
			console.remove(0);
		}
		game.removeAll();
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
				} catch (InterruptedException e) {}
			}
		}
		wakeUp = false;
		return latestInput;
	}
	
	public void wakeUpThread() {
		wakeUp = true;
	}
	
}
