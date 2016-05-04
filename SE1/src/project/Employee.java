package project;

import java.util.ArrayList;

public class Employee {
	
	private SysApp sysApp;
	private String initials;
	public ArrayList<Project> projectList;
	public ArrayList<Activity> activityList;
	public ArrayList<double[]> workHourList;
	
	public Employee(String initials){
		this.initials = initials;
		projectList = new ArrayList<Project>();
		activityList = new ArrayList<Activity>();
		workHourList = new ArrayList<double[]>();
	}
	
	public String getInitials() {
		return initials;
	}
	
	public boolean assignProject(Project p) {
		if (p != null && !projectList.contains(p)) {
			projectList.add(0,p);
			return true;
		}
		return false;
	}
	
	public boolean assignActivity(Activity a) {
		if (a != null && !activityList.contains(a)) {
			activityList.add(0,a);
			addWorkHourList(7 * (a.startWeek.weekDifference(a.endWeek)+1));
			return true;
		}
		return false;
	}
	
	private void addWorkHourList(int size){
		double[] hours = new double[size];
		for(int i = 0; i < size; i++){
			hours[i] = 0.0;
		}
		workHourList.add(0,hours);
	}
	
	public boolean setHours(Activity a, double hours, Week w, int weekday) {
		if (	a != null && activityList.contains(a) && hours > 0.0 && 
				w.getWeek() > 0 && w.getWeek() <= 53 &&
				weekday > 0 && weekday <= 7 &&
				a.startWeek.compareTo(w) <= 0 && a.endWeek.compareTo(w) >= 0)
		{
			int currentweek = a.startWeek.weekDifference(a.endWeek) - a.endWeek.weekDifference(w);
			double temp = workHourList.get(activityList.indexOf(a))[(currentweek)*7+(weekday-1)];
			workHourList.get(activityList.indexOf(a))[(currentweek)*7+(weekday-1)] = hours;
			a.spendHours(hours - temp);
			return true;
		}
		return false;
	}
	
	public double getWeeklyHours(Week w) {
		double count = 0;
		ArrayList<Activity> list = getWeeklyActivities(w);
		for(Activity a : list){
			try {
				double d = getWorkHours(a,w)[7];
				count = d;
			} catch (IllegalOperationException e) {}
			/* Possibly print work hours for every activity */
			/*
			String s = a.type+" : "+d; 
			sysApp.ui.print(s, sysApp.ui.style[2]);
			//System.out.println(s);
			*/
		}
		return count;
	}
	
	public double[] getWorkHours(Activity a, Week w) throws IllegalOperationException {
		double[] hourList = new double[8];
		if(a.startWeek.compareTo(w) <= 0 && a.endWeek.compareTo(w) >= 0){
			double[] list = workHourList.get(activityList.indexOf(a));
			int currentweek = a.startWeek.weekDifference(a.endWeek) - a.endWeek.weekDifference(w);
			for(int i = 0; i<7; i++){
				double d = list[(currentweek)*7 + i]; 
				hourList[7] += d;
				hourList[i] = d; 
			}
		}
		else{
			throw new IllegalOperationException("Week "+w.getWeek()+" is not within "+a.type, this.getClass());
		}
		return hourList;
	}
	
	public ArrayList<Activity> getWeeklyActivities(Week w) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		for(Activity a : activityList){
			if(a.startWeek.compareTo(w) <= 0 && a.endWeek.compareTo(w) >= 0){
				list.add(a);
			}
		}
		return list;
	}
}


