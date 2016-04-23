package project;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Message {

	public JLabel lbl = new JLabel();
	public int offset;
	
	public Message(String message, MessageStyle style, int offset) {
		this.offset = offset;
		lbl.setText(message);
		lbl.setOpaque(true);
		lbl.setBackground(style.background);
		lbl.setForeground(style.foreground);
		lbl.setBounds(20, offset, UserInterface.dim.width - 60, UserInterface.HEIGHT);
		lbl.setFont(style.font);
	}	
}
