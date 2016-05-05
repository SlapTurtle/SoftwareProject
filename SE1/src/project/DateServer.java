package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {
	
	public Calendar getCalendar(){
		return GregorianCalendar.getInstance();
	}
	
	public Week getToday() {
		Calendar cal = getCalendar();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return new Week(year, week);
	}
}
