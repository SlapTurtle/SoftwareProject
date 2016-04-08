package project;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Message {

	public JLabel lbl = new JLabel();
	public int offset;
	
	public Message(String message, Color background, Color foreground, int offset) {
		this.offset = offset;
		lbl.setText(message);
		lbl.setBackground(background);
		lbl.setForeground(foreground);
		lbl.setBounds(0, offset, UserInterface.dim.width, UserInterface.HEIGHT);
		lbl.setFont(new Font("Arial", Font.PLAIN, 12));
	}
	
}
