package Tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;

import Project.*;

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
	public void setup() {
		//Makes work constants
		year = 2016;
		week = 20;
		
		//Creates instance of sysApp
		sysApp = new SysApp(false);
		
		//mocks dateServer
		//mocks return calendar for dS.getToday()
		DateServer dS = mock(DateServer.class);
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		when(dS.getCalendar()).thenReturn(cal);
		when(dS.getToday()).thenReturn(new Week(year, week));
		
		sysApp.setDateServer(dS);
		
		//three dummy weeks
		week1 = new Week(year, week*1);
		week2 = new Week(year, week*2);
		week3 = new Week(year, week*3);
		
		//three dummy projects
		p1 = new Project(sysApp, "Project1", week1, week2, week3);
		p2 = new Project(sysApp, "Project2", week1, week2, week3);
		p3 = new Project(sysApp, "Project3", week1, week2, week3);
		
		//three dummy activities
		a1 = new Activity(sysApp, "Activity01", week1, week3);
		a2 = new Activity(sysApp, "Activity02", week2, week2);
		a3 = new Activity(sysApp, "Activity03", week2, week3);
		
		//three dummy employees
		e1 = new Employee("AAAA");
		e2 = new Employee("BBBB");
		e3 = new Employee("CCCC");
	}
	
	public void updateDateServer(){
		DateServer dS = mock(DateServer.class);
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		when(dS.getCalendar()).thenReturn(cal);
		when(dS.getToday()).thenReturn(new Week(year, week));
		
		sysApp.setDateServer(dS);
	}
}
