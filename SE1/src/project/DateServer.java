package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateServer {
	
	public DateServer(){
		
	}
	
	public Week getWeek(int weak){
		Calendar temp = this.getToday();
		int year = temp.get(Calendar.YEAR);
		try {
			return new Week(year, weak);
		} catch (IllegalOperationException e) {
			//error - week is invalid
			e.printStackTrace();
			return null;
		}
	}
	
	public Calendar getToday() {
		return GregorianCalendar.getInstance();
	}
}
