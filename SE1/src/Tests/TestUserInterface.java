package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.*;
import project.*;

public class TestUserInterface extends TestBasis {
	
	@Test
	public void TestUserInterface() {
		setup();
		UserInterface ui = sysApp.ui;
		int size = ui.console.size();
		
		// Tests if console initialized successfully
		assertTrue(size != 0);
		
		// Tests if top-most message is cleared
		ui.shiftUp();
		assertTrue(ui.console.size() == size - 1);
		
		// Tests if a message is printed to the console
		sysApp.ui.print("test message");
		assertTrue(ui.console.size() == size);
		
		// Tests if console is properly cleared
		sysApp.ui.clear();
		assertTrue(ui.console.size() == 0);
		
		//Tests if user inputs are properly passed along
		String txt = "user input";
		ui.input.obj.setText(txt);
		ui.input.constructListener(new InputListener() {
			@Override
			public void inputSent(InputEvent e) {
				ui.input.obj.setText("");
			}
		});
		//assertTrue(ui.input.redirectInput());
		//assertTrue(ui.input.obj.getText() == "");
		//assertTrue(ui.console.size() == 1);
		//assertFalse(ui.wakeUpThread());
	}
	
}
