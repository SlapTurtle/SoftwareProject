package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputField{
	String defaultText = "";
	JTextField obj;
	private ArrayList<InputListener> listeners = new ArrayList<InputListener>();
	
	public InputField(int offset) {
		this.obj = new JTextField(defaultText);
		obj.setBounds(20, offset, UserInterface.dim.width - 60, 20);
		obj.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				SysApp.ui.print(obj.getText(), SysApp.ui.style[1]);
				processEvent(new InputEvent(obj.getText()));
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
