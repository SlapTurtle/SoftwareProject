package Classes;

import java.util.Calendar;

public class DateServer {
	
	private static Calendar calender = Calendar.getInstance();

	
	
	
	public static int getWeek(){
		return calender.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static Calendar getCalendar(){
		return calender;
		
	}
	
	
	
}
