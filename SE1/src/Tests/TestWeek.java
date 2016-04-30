package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import project.*;

public class TestWeek extends TestBasis {
	private static final int year  = 2016;	//year used for testing
	private static final int week  = 20;		//week used for testing
	private static final int yInc = 3;		//year increment
	private static final int wInc = 5;		//week increment
	private static final int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
	
	@Test
	public void testConstructor(){
		//creates valid week
		try {
			//Create week
			Week w = new Week(year, week);
			
			//Tests fields
			assertEquals(w.getYear(), year);
			assertEquals(w.getWeek(), week);
			
		} catch(Exception e){
			//incorrect Exception
			e.printStackTrace();
			fail(); 
		}
		
		//forcing bad constructions
		testWeekConstructor(year, -week);				//week < 1
		testWeekConstructor(year, week+maxWeeksOfYear);	//week > 53
		testWeekConstructor(-year, week);				//year < 0
		testWeekConstructor(-year, -week);				//year < 0 && week < 1
		testWeekConstructor(-year, week+maxWeeksOfYear);//year < 0 && week > 53
	}
	
	private void testWeekConstructor(int y, int w){
		try {
			Week weekt = new Week(y, w);
			fail();
		} catch(IllegalOperationException e){
			String s;
			if(y < 0){
				s = "Invalid Year; must be above 0";
			}
			else{
				s = "Invalid Week; must be between 1-"+maxWeeksOfYear;
			}
			assertEquals(e.getMessage(), s);
			assertEquals(e.getErrorClass(), Week.class);
		}
	}
	
	@Test
	public void testMethods() throws Exception{
		//creates every scenario
		Week week1 = new Week(year, week); 				// basis
		Week week2 = new Week(year, week); 				// == basis
		Week week3 = new Week(year+yInc, week); 		// + year
		Week week4 = new Week(year, week+wInc); 		// + week
		Week week5 = new Week(year+yInc, week+wInc); 	// + year and + week
		Week week6 = new Week(year+yInc, week-wInc);	// + year and - week
		Week week7 = new Week(year-yInc, week);			// - year
		Week week8 = new Week(year, week-wInc);			// - week
		Week week9 = new Week(year-yInc, week-wInc);	// - year and - week
		Week week10 = new Week(year-yInc, week+wInc);	// - year and + week
		
		testWeek(week1, week2, 0, 0, 0);
		testWeek(week1, week3, yInc, yInc*maxWeeksOfYear, -yInc);
		testWeek(week1, week4, 0, wInc, -wInc);
		testWeek(week1, week5, yInc, wInc+yInc*maxWeeksOfYear, -yInc);
		testWeek(week1, week6, yInc, -wInc+yInc*maxWeeksOfYear, -yInc);
		testWeek(week1, week7, yInc, yInc*maxWeeksOfYear, yInc);
		testWeek(week1, week8, 0, wInc, wInc);
		testWeek(week1, week9, yInc, wInc+yInc*maxWeeksOfYear, yInc);
		testWeek(week1, week10, yInc, -wInc+yInc*maxWeeksOfYear, yInc);
	}
	
	private void testWeek(Week w1, Week w2, int yearDiff, int weekDiff, int i) {
		int yDiff = w1.yearDifference(w2);
		int wDiff = w1.weekDifference(w2);
		assertTrue(w1.compareTo(w2) == i);	//test compareTo
		assertEquals(yearDiff, yDiff);		//test yearDifference
		assertEquals(weekDiff, wDiff);		//test weekDifference
	}
}
