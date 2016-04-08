package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class UserInterface {
	
	public JFrame frame = new JFrame("UI");
	public JLayeredPane game = frame.getLayeredPane();
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static int HEIGHT = 16;
	public ArrayList<Message> console = new ArrayList<Message>();
	public int offset = 0;
	
	public UserInterface() {
		frame.setBounds(0, 0, dim.width, dim.height);
		print("A new session has been initialized.");
	}
	
	public Message print(String message) {
		offset += HEIGHT;
		Message msg = new Message(message, Color.WHITE, Color.BLACK, offset);
		console.add(msg);
		return msg;
	}
	
	public Message print(String message, Color background, Color foreground) {
		offset += HEIGHT;
		Message msg = new Message(message, background, foreground, offset);
		console.add(msg);
		return msg;
	}
	
	public void clearAll() {
		while (console.size() > 0) {
			console.get(0).lbl.removeAll();
			console.remove(0);
		}
		offset = 0;
	}
	
	public void shiftUp() {
		console.remove(0);
		for (int i = 0; i < console.size(); i++) {
			console.get(i).offset -= HEIGHT;
		}
	}
	
}
