package project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week implements Comparable<Week> {
	//Fields
	private static final int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
	private int year;
	private int week;
	
	//Constructor
	public Week(int year, int week) {
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
	
	//Comparable
	@Override
	public int compareTo(Week other) {
		if(this.getYear() - other.getYear() == 0){
			return this.getWeek() - other.getWeek();
		}
		return this.getYear() - other.getYear();
	}
	
	//Other Methods
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
		return wdiff + maxWeeksOfYear + (ydiff * (maxWeeksOfYear-1));
	}
}
