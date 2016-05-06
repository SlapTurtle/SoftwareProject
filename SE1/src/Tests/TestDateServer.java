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
	public void testDateServer(){
		//creates DateServer
		DateServer ds = new DateServer();
		
		//tests getCalendar
		Calendar cal = new GregorianCalendar();
		assertEquals(ds.getCalendar(), cal);
		
		//tests getWeek
		Week w = new Week(cal.get(Calendar.YEAR), cal.get(Calendar.WEEK_OF_YEAR));
		assertEquals(ds.getToday().compareTo(w), 0);
	}
}
