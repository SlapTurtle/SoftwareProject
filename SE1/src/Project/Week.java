package Project;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week implements Comparable<Week> {
	//Fields
	private static final int maxWeeksOfYear = new GregorianCalendar().getMaximum(Calendar.WEEK_OF_YEAR);
	private int year;
	private int week;
	
	//Constructor
	public Week(int year, int week) {
		if(week < 1) {
			while(week + maxWeeksOfYear >= 0){
				week += maxWeeksOfYear;
				year--;
			}
		}
		if(week > maxWeeksOfYear) {
			while(week - maxWeeksOfYear >= 0){
				week -= maxWeeksOfYear;
				year++;
			}
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
		return year+"/"+week;
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
