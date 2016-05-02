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
	SysApp sys;
	String defaultText = "";
	public JTextField obj;
	private ArrayList<InputListener> listeners = new ArrayList<InputListener>();
	
	public InputField(SysApp sys, int offset) {
		this.sys = sys;
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
				synchronized (sys.ui.lock) {
					sys.ui.wakeUpThread();
					sys.ui.lock.notifyAll();
					obj.setText("");
				}
			}
		});
	}
	
	public void redirectInput() {
		String msg = obj.getText();
		boolean b = false;
		switch (msg) {
		case "!help": sys.ui.help(); break;
		case "!clear": sys.ui.clear(); break;
		case "!cancel": sys.ui.cancel(); break;
		default:
			sys.ui.print(msg, sys.ui.style[1]);
			b = true;
			processEvent(new InputEvent(obj.getText()));
			break;
		}
		if (!b) { sys.ui.input.obj.setText(""); }
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
