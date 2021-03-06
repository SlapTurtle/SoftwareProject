package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import Project.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
 
public class TestDateServer extends TestBasis {
	@Test
	public void testDateServer(){
		//creates DateServer
		DateServer ds = new DateServer();
		
		//tests getCalendar
		Calendar cal = new GregorianCalendar();
		assertEquals(ds.getCalendar().get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(ds.getCalendar().get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.WEEK_OF_YEAR)); 
		assertEquals(ds.getCalendar().get(Calendar.DAY_OF_YEAR), cal.get(Calendar.DAY_OF_YEAR)); 
		
		//tests getWeek
		Week w = new Week(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR));
		assertEquals(ds.getToday().compareTo(w), 0);
	}
}
