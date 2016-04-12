package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import project.*;

public class TestDateServer extends TestBasis {
	@Test
	public void testDate(){
		//setup
		setup();
		int year = 2020;
		int week = 20;
		
		int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
		
		//try catch for failures with creation of week object
		try {
			//mocks dateServer
			DateServer dS = mock(DateServer.class);
			
			//mocks return calendar for dS.getToday()
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, year); 			//set Year
			cal.set(Calendar.WEEK_OF_YEAR, week); 	//Set Week
			
			when(dS.getToday()).thenReturn(cal);
			
				when(dS.getWeek()).thenReturn(new Week(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR)));
			
			
			//makes the sysApp use the mocked dateServer
			sysApp.setDateServer(dS);
			
			//tests that the sysApp returns dS
			assertEquals(dS, sysApp.getDateServer());
			
			//tests that the year is correctly changed
			year = sysApp.getDateServer().getToday().get(Calendar.YEAR);
			assertEquals(cal.get(Calendar.YEAR), year);
			
			//tests that a newly created week is correctly changed as well
			Week week1 = new Week(year, cal.get(Calendar.WEEK_OF_YEAR));
			Week week2 = sysApp.getDateServer().getWeek();
			assertTrue(week1.compareTo(week2) == 0);
			
			//tests that weeks created with DateServer can be stored
			int increase = 20;
			Week week3 = sysApp.getDateServer().getWeek();
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + increase); //increase week of year by 20.
			Week week4 = new Week(year, cal.get(Calendar.WEEK_OF_YEAR));
			assertTrue(week3.compareTo(week4) < 0);
			assertFalse(week3.compareTo(week4) > 0);
			assertTrue(week3.getWeek() == (week4.getWeek() - increase));
			
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) - increase); //resets for more testing
		} catch (Exception e) {
			//error - week has some sort of failure
			fail();
			e.printStackTrace();
		}
	}
}
