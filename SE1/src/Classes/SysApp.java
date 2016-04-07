package Classes;

	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.ArrayList;

	public class SysApp {	
		
		private ArrayList<Employee> employeeList = new ArrayList<>();
		private ArrayList<Project> projectList = new ArrayList<>();
		private ArrayList<Activity> activityList= new ArrayList<>();
		private DateServer dateServer;
		private Employee currentUser;
		private File file = new File("WeeklyRapport");
		
		private String logedInUser;
		private boolean isLogin = false;
		private boolean FindsEmployee = false;
		
		public SystemApp(){
			
		}
		public boolean loggedIn(){
			return isLogin;
		}
		public boolean login(String employee){
			if (isLogin == false){
			isLogin = employeeList.contains(employee);
			if(isLogin == true){
			logedInUser = employee;
			}
			}
			return isLogin;
			}
		
		public boolean logoff(){
			isLogin = false;
			logedInUser = null;
			return isLogin;
		}
		
		public boolean addEmployee(Employee employee){
			boolean works = employeeList.add(employee);
			return works;
		}
		
		public boolean addProject(Project ID){
			return projectList.add(ID);
		}
		
		public boolean addActicity(Activity ID){
			return activityList.add(ID);
		}
		
		public DateServer getDateServer(){
			return dateServer;
		} 
		
		public ArrayList<String> getAvailableEmployees(Activity activity){
			return activity.employeeList;
		} 
		 
		@SuppressWarnings("unused")
		private boolean writeToLog(String rapport) throws IOException{
			if(file.exists() == false){
			file.createNewFile();
			}
			FileWriter note = new FileWriter(file);
			note.write(rapport);
			note.flush();
			note.close();
			
			return true;
			}
		
		



	}

