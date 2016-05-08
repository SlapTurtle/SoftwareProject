package Project;

@SuppressWarnings("serial")
public class IllegalOperationException extends Exception {
	
	/*
	 * FIELD
	 */
	private Class<?> errorClass;
	
	/*
	 * CONSTRUCTOR
	 */
	public IllegalOperationException(String errormsg, Class<?> errorClass){
		super(errormsg);
		this.errorClass = errorClass;
	}
	
	/*
	 * GETTER
	 */
	public Class<?> getErrorClass() {
		return this.errorClass;
	}
}

