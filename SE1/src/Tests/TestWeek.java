package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import project.*;

public class TestWeek extends TestBasis {
	@Test
	public void testWeek(){
		//setup
		setup();
		
		int year = 2016;	//year used for testing
		int week = 15;		//week used for testing
		int i	 = 10;		//year and week increase/decrease used for testing
		int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
		
		//forcefully creates invalid week
		try {
			Week w = new Week(year, week);
		} catch(Exception e){
			//error - incorrectly thrown IllegalOperationException
			fail();
		}
		try {
			Week w = new Week(year, 0);
			fail();
		} catch(IllegalOperationException e){
			if(!e.getErrorClass().equals(Week.class)){
				//error not correctly thrown
				fail();
			}
			//correctly thrown
		}
		try {
			Week w = new Week(year, -i);
			fail();
		} catch(Exception e){
			//correctly thrown
		}
		try {
			Week w = new Week(-i, week);
			fail();
		} catch(Exception e){
			//correctly thrown
		}
		try {
			Week w = new Week(-i, -i);
			fail();
		} catch(Exception e){
			//correctly thrown
		}

		//try catch for detecting failures in creation of object
		try{
			Week week1 = new Week(year, week); 		// basis
			Week week2 = new Week(year, week); 		// == basis
			Week week3 = new Week(year+i, week); 	// + year
			Week week4 = new Week(year, week+i); 	// + week
			Week week5 = new Week(year+i, week+i); 	// + year and + week
			Week week6 = new Week(year-i, week);	// - year
			Week week7 = new Week(year, week-i);	// - week
			Week week8 = new Week(year-i, week-i);	// - year and - week
			Week week9 = new Week(year+i, week-i);	// + year and - week
			Week week10 = new Week(year-i, week+i);	// - year and + week
			
			//Year overlapping
			Week week11 = new Week(year, maxWeeksOfYear-i);	// For Year Overlapping
			Week week12 = new Week(year+i, i);				// Week11's week + 2*i
			
			//tests fields
			assertEquals(week1.getYear(), year);
			assertEquals(week1.getWeek(), week);
			
			//test compareTo
			//week2
			assertTrue(week1.compareTo(week2) == 0);
			
			//week3
			int yearDiff = week1.yearDifference(week3);
			assertTrue(week1.compareTo(week3) < 0);
			assertEquals(yearDiff, i);
			
			//week4
			int weekDiff = week1.weekDifference(week4);
			assertTrue(week1.compareTo(week4) < 0);
			assertEquals(weekDiff, i);
			
			//week5
			yearDiff = week1.yearDifference(week5);
			weekDiff = week1.weekDifference(week5);
			assertTrue(week1.compareTo(week5) < 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week5.getWeek()+maxWeeksOfYear-week1.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
			//week6
			yearDiff = week1.yearDifference(week6);
			assertTrue(week1.compareTo(week6) > 0);
			assertEquals(yearDiff, i);
			
			//week7
			weekDiff = week1.weekDifference(week7);
			assertTrue(week1.compareTo(week7) > 0);
			assertEquals(weekDiff, i);
			
			//week8
			yearDiff = week1.yearDifference(week8);
			weekDiff = week1.weekDifference(week8);
			assertTrue(week1.compareTo(week8) > 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week1.getWeek()+maxWeeksOfYear-week8.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
			//week9
			yearDiff = week1.yearDifference(week9);
			weekDiff = week1.weekDifference(week9);
			assertTrue(week1.compareTo(week9) < 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week9.getWeek()+maxWeeksOfYear-week1.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
			//Week10
			yearDiff = week1.yearDifference(week10);
			weekDiff = week1.weekDifference(week10);
			assertTrue(week1.compareTo(week10) > 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week1.getWeek()+maxWeeksOfYear-week10.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
			//year overlap part 1
			yearDiff = week11.yearDifference(week12);
			weekDiff = week11.weekDifference(week12);
			assertTrue(week11.compareTo(week12) < 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week12.getWeek()+maxWeeksOfYear-week11.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
			//year overlap part 2
			yearDiff = week12.yearDifference(week11);
			weekDiff = week12.weekDifference(week11);
			assertTrue(week12.compareTo(week11) > 0); 
			assertEquals(yearDiff, i);
			assertEquals(weekDiff, week12.getWeek()+maxWeeksOfYear-week11.getWeek()+yearDiff*(maxWeeksOfYear-1));
			
		} catch(Exception e){
			//error - week object misbehaving
			fail();
			e.printStackTrace();
		}
	}
}
