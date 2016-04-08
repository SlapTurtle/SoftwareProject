package project;

import java.util.ArrayList;

public class Employee {
	
	private String initials;
	public ArrayList<Project> projectList;
	public ArrayList<Activity> activityList;
	public ArrayList<double[]> workHourList;
	
	public Employee(String initials){
		this.initials = initials;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public boolean assignProject(Project p) {
		if (p != null && !projectList.contains(p)) {
			projectList.add(p);
			return true;
		}
		return false;
	}
	
	public boolean assignActivity(Activity a) {
		if (a != null && !activityList.contains(a)) {
			activityList.add(a);
			return true;
		}
		return false;
	}
	
	public boolean setHours(Activity a, double hours, Week w, int weekday) {
		if (a != null && hours > 0 && w.getWeek() > 0 && w.getWeek() <= 53 && weekday >= 1 && weekday <= 7) {
			// set hours
			return true;
		}
		return false;
	}
	
	public double getWeeklyHours(Week w) {
		double count = 0;
		for (int i = 0; i < 7; i++) {
			// iterate through weekdays
		}
		return count;
	}
	
	public double getWorkHours(Activity a, Week w) {
		double count = 0;
		for (int i = 0; i < 7; i++) {
			// iterate through weekdays
		}
		return count;
	}
	
	public ArrayList<Activity> getWeeklyActivities(Week w) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		return list;
	}
	
}


