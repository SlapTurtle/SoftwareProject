package project;

import static org.mockito.Mockito.when;

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
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 2016); 			//set Year
		cal.set(Calendar.WEEK_OF_YEAR, 1); 	//Set Week
		
		return cal;
		//return GregorianCalendar.getInstance();
	}
	
	public String stringToday(){
		Calendar cal = getToday();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return "Year: "+year+" , Week: "+week;
	}
}
