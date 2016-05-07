package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.ProcessBuilder.Redirect;

import org.junit.*;

import Interface.*;
import Project.*;

public class TestUserInterface extends TestBasis {
	
	@Override
	public void setup() {
		super.setup();
		sysApp.ui = new UserInterface(sysApp);
	} 
	
	@Test
	public void TestUserInterface() throws IllegalOperationException {
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
		String[] txt = new String[] {"!clear", "text"};
		int i = 0;
		do {
			ui.input.obj.setText(txt[i]);
			ui.input.redirectInput();
			if (i == 0) { assertTrue(ui.console.size() == 0); }
			i++;
		} while (i < 2);
		assertTrue(ui.console.size() == 1);
		//assertTrue(ui.latestInput.equals(txt[2]));
		//assertTrue(ui.input.redirectInput());
		//assertTrue(ui.input.obj.getText() == "");
		//assertTrue(ui.console.size() == 1);
		//assertFalse(ui.wakeUpThread());
	}
	
}
