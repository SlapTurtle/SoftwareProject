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
	public void setup() throws IllegalOperationException{
		//Mocks dateServer
		DateServer dS = mock(DateServer.class);
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year); 			//set Year
		cal.set(Calendar.WEEK_OF_YEAR, week); 	//Set Week
		when(dS.getToday()).thenReturn(cal);
		
		sysApp = new SysApp(true);
		
		year = 2016;
		week = 10;

		week1 = new Week(year, week);
		week2 = new Week(year, week+1);
		week3 = new Week(year, week+2);
		
		p1 = new Project(sysApp, "Project1", week1, week2, week3);
		p2 = new Project(sysApp, "Project2", week1, week2, week3);
		p3 = new Project(sysApp, "Project3", week1, week2, week3);
		
		a1 = new Activity(sysApp, "Activity01", week1, week3);
		a2 = new Activity(sysApp, "Activity02", week2, week2);
		a3 = new Activity(sysApp, "Activity03", week2, week3);
		
		e1 = new Employee("AAAA");
		e2 = new Employee("BBBB");
		e3 = new Employee("CCCC");
	}
}
