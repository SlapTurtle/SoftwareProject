package Interface;

import java.awt.Color;
import java.awt.Font;

public class MessageStyle {
	Color foreground;
	Color background;
	Font font;
	 
	public MessageStyle(int style, Color fg, Color bg) {
		this.foreground = fg;
		this.background = bg;
		this.font = new Font("Arial", style, 14);
	}
	
}
