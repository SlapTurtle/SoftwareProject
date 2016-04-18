package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private transient Vector listeners;
	
	public InputField(int offset) {
		this.obj = new JTextField(defaultText);
		obj.setBounds(20, offset, UserInterface.dim.width - 60, 20);
		/*obj.getDocument().addDocumentListener(new DocumentListener() {
			public void react() {
				if (obj.getText().contains(defaultText)) {
					
				}
			}
			public void changedUpdate(DocumentEvent arg0) {
				react();	
			}
			public void insertUpdate(DocumentEvent arg0) {
				react();
			}
			public void removeUpdate(DocumentEvent arg0) {
				react();
			}
			;
		});*/
		obj.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				SysApp.ui.print(obj.getText(), SysApp.ui.style[1]);
				obj.setText("");
				if (listeners != null && !listeners.isEmpty()) {
					InputEvent event = new InputEvent(this);
					Vector targets = (Vector) listeners.clone();
					Enumeration enu = targets.elements();
					while (enu.hasMoreElements()) {
						InputListener l = (InputListener) enu.nextElement();
						l.inputSent(event);
					}
				}
			}
		});
	}
}
