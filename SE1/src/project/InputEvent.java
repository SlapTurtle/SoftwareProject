package project;

import java.util.EventObject;

public class InputEvent extends EventObject{
	private String message;
	
	public InputEvent(Object source) {
		super(source);
	}
	
	public String getMessage() {
		return message;
	}
	
}
