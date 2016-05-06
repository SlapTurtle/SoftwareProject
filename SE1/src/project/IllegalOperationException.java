package project;

@SuppressWarnings("serial")
public class IllegalOperationException extends Exception {
	//Fields
	private Class<?> errorClass;
	
	//Constructor 
	public IllegalOperationException(String errormsg, Class<?> errorClass){
		super(errormsg);
		this.errorClass = errorClass;
	}
	
	//Getter
	public Class<?> getErrorClass() {
		return this.errorClass;
	}
}

