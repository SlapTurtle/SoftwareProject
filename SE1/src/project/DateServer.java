package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {
	
	public DateServer(){
		
	}
	
	public Week getWeek(){
		Calendar temp = this.getToday();
		int year = temp.get(Calendar.YEAR);
		int week = temp.get(Calendar.WEEK_OF_YEAR);
		try {
			return new Week(year, week);
		} catch (Exception e) {
			//error - DateServer has invalid calendar.
			e.printStackTrace();
			return null;
		}
	}
	
	public Calendar getToday() {
		return GregorianCalendar.getInstance();
	}
}
