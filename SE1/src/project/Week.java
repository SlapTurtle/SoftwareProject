package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week implements Comparable<Week> {
	//Fields
	private static final int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
	private int year;
	private int week;
	
	//Constructor
	public Week(int year, int week) throws IllegalOperationException {
		if(year < 0){
			throw new IllegalOperationException("Invalid Year; must be above 0", this.getClass());
		}
		if(week <= 0 || week > maxWeeksOfYear){
			throw new IllegalOperationException("Invalid Week; must be between 1-"+maxWeeksOfYear, this.getClass());
		}
		this.year = year;
		this.week = week;
	}
	
	//Getters
	public int getWeek() {
		return week;
	}
	
	public int getYear() {
		return year;
	}
	
	//Methods
	@Override
	public int compareTo(Week other) {
		if(this.getYear() - other.getYear() == 0){
			return this.week - other.getWeek();
		}
		return this.getYear() - other.getYear();
	}
	
	@Override
	public String toString(){
		return "Year: "+year+".   Week:"+week;
	}
	
	public int yearDifference(Week other){
		return Math.abs(this.getYear() - other.getYear());
	}
	
	public int weekDifference(Week other){
		int ydiff = this.yearDifference(other);
		int wdiff = this.getWeek() - other.getWeek();
		if(ydiff == 0){
			return Math.abs(wdiff);
		}
		if(this.getYear() < other.getYear()){
			wdiff *= -1;
		}
		return wdiff + (ydiff * (maxWeeksOfYear));
	}
}
