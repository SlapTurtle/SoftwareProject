package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;

import project.*;

public class TestBasis {
	
	protected SysApp sysApp;
	
	@Before
	public void setup(){
		//makes systemApp
		sysApp = new SysApp();
		
		//more TO-DO...
	}
}
