package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputField{
	String defaultText = "";
	public JTextField obj;
	private ArrayList<InputListener> listeners = new ArrayList<InputListener>();
	
	public InputField(int offset) {
		this.obj = new JTextField(defaultText) {
			@Override public void setBorder(Border border) {
			}
		};
		obj.setFont(UserInterface.style[2].font);
		obj.setBounds(20, offset, UserInterface.dim.width - 60, 20);
		obj.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				redirectInput();
			}
		});
		constructListener(new InputListener() {
			
			@Override
			public void inputSent(InputEvent e) {
				synchronized (SysApp.ui.lock) {
					SysApp.ui.wakeUpThread();
					SysApp.ui.lock.notifyAll();
					obj.setText("");
				}
			}
		});
	}
	
	public boolean redirectInput() {
		String msg = obj.getText();
		switch (msg) {
		case "clear": SysApp.ui.clear();
		default: SysApp.ui.print(msg, SysApp.ui.style[1]);
		}
		processEvent(new InputEvent(obj.getText()));
		return true;
	}
	
	public synchronized void constructListener(InputListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	private void processEvent(InputEvent inputEvent) {
		ArrayList<InputListener> tempList;
		synchronized (this) {
			if (listeners.size() == 0) return;
			tempList = (ArrayList<InputListener>) listeners.clone();
		}
		for (InputListener l : tempList) {
			l.inputSent(inputEvent);
		}
	}
}
