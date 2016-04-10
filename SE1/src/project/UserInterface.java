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
		dim.height -= 100;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.setVisible(true);
	/*  print("A new session has been initialized.");
		print("line 2");
		print("line 3");  */
		//clear();
		//shiftUp();
	/*	for (int i = 0; i < 71; i++) {
			print("message " + i);
		}	*/
	}
	
	public Message print(String message) {
		if (offset + 7 * HEIGHT >= dim.height) { shiftUp(); }
		offset += HEIGHT;
		Message msg = new Message(message, Color.WHITE, Color.BLACK, offset);
		game.add(msg.lbl);
		console.add(msg);
		return msg;
	}
	
	public Message print(String message, Color background, Color foreground) {
		offset += HEIGHT;
		Message msg = new Message(message, background, foreground, offset);
		game.add(msg.lbl);
		console.add(msg);
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
	
}
