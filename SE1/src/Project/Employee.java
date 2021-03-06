package Project;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	
	/*
	 * FIELDS
	 */
	@SuppressWarnings("unused")
	private SysApp sysApp;
	private String initials;
	private List<Project> projectList;
	private List<Activity> activityList;
	private List<Object> workHourList;
	
	/*
	 * CONSTRUCTOR
	 */
	public Employee(String initials){
		this.initials = initials.toUpperCase();
		projectList = new ArrayList<Project>();
		activityList = new ArrayList<Activity>();
		workHourList = new ArrayList<Object>();
	}
	
	/*
	 * GETTERS
	 */
	public String getInitials() {
		return initials;
	}
	
	public List<Project> getProjectList(){
		return projectList;
	}
	
	public List<Activity> getActivityList(){
		return activityList;
	}
	
	public List<Object> getWorkHourList(){
		return workHourList;
	}
	
	public boolean assignProject(Project p) {
		if (p != null && !projectList.contains(p)) {
			projectList.add(0,p);
			return true;
		}
		return false;
	}
	
	/*
	 * METHODS
	 */
	public boolean assignActivity(Activity a) {
		if (a != null && !activityList.contains(a)) {
			activityList.add(0,a);
			addWorkHourList(a);
			return true;
		}
		return false;
	}
	
	private void addWorkHourList(Activity a){
		List<double[]> a_list = new ArrayList<double[]>();
		int k = a.getStartWeek().weekDifference(a.getEndWeek());
		for(int i=0; i<=k; i++){
			double[] hours = new double[7];
			for(int j = 0; j < 7; j++){
				hours[j] = 0.0;
			}
			a_list.add(0,hours);
		}
		workHourList.add(0,a_list);
	}
	
	@SuppressWarnings("unchecked")
	public void updateActivityWeeks(Activity a, int newSize){
		List<double[]> list = (List<double[]>) (workHourList.get(activityList.indexOf(a)));
		int size = list.size();
		while(size != newSize){
			if(size > newSize){
				size--;
				list.remove(size);
			}
			else{
				double[] hours = new double[7];
				for(int j = 0; j < 7; j++){
					hours[j] = 0.0;
				}
				list.add(size, hours);
				size++;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean setHours(Activity a, double hours, Week w, int weekday) {
		if (	a != null && activityList.contains(a) &&
				hours >= 0.0 && hours <= 24 &&
				a.getStartWeek().compareTo(w) <= 0 && a.getEndWeek().compareTo(w) >= 0 &&
				weekday > 0 && weekday <= 7
				
		){
			int currentWeek = a.getStartWeek().weekDifference(a.getEndWeek()) - a.getEndWeek().weekDifference(w);
			List<double[]> list = (List<double[]>) workHourList.get(activityList.indexOf(a));
			double[] d = list.get(currentWeek);
			a.spendHours(hours - d[weekday-1]);
			d[weekday-1] = hours;
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
				count += d;
			} catch (IllegalOperationException e) {
				//should never happen
			}
		}
		return count; 
	}
	
	@SuppressWarnings("unchecked")
	public double[] getWorkHours(Activity a, Week w) throws IllegalOperationException {
		double[] hourList = new double[8];
		if(a.getStartWeek().compareTo(w) <= 0 && a.getEndWeek().compareTo(w) >= 0){
			List<double[]> list = (List<double[]>) workHourList.get(activityList.indexOf(a));
			int currentWeek = a.getStartWeek().weekDifference(a.getEndWeek()) - a.getEndWeek().weekDifference(w);
			for(int i = 0; i<7; i++){
				double d = list.get(currentWeek)[i]; 
				hourList[7] += d;
				hourList[i] = d; 
			}
		}
		else{
			throw new IllegalOperationException("Week "+w.getWeek()+" is not within "+a.getType(), this.getClass());
		}
		return hourList;
	}
	
	public ArrayList<Activity> getWeeklyActivities(Week w) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		for(Activity a : activityList){
			if(a.getStartWeek().compareTo(w) <= 0 && a.getEndWeek().compareTo(w) >= 0){
				list.add(a);
			}
		}
		return list;
	}
	
	public List<Project> getProjectToManage(){
		ArrayList<Project> list = new ArrayList<Project>();
		for(Project p : projectList){
			if(p.getManager() == this){
				list.add(p);
			}
		}
		return list;
	}
}