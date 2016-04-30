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
	
	SysApp sysApp;
	int year;
	int week;
	Week week1;
	Week week2;
	Week week3;
	Project p1;
	Project p2;
	Project p3;
	Activity a1;
	Activity a2;
	Activity a3;
	Employee e1;
	Employee e2;
	Employee e3;
	
	@Before
	public void setup(){
		//makes systemApp
		sysApp = new SysApp();
		
		//more TO-DO...
	}
}
