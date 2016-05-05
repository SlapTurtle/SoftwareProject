package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {
	public Week getToday() {
		Calendar cal = GregorianCalendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return new Week(year, week);
	}
	
	public String stringToday(){
		return getToday().toString();
	}
}
