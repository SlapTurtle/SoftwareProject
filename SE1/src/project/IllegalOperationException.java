package project;

@SuppressWarnings("serial")
public class IllegalOperationException extends Exception {
	//Fields
	private String operationMessage;
	
	//Constructor
	public IllegalOperationException(String errormsg, String operationMsg){
		super(errormsg);
		this.operationMessage = operationMsg;
	}
	
	//Getter
	public String getOperation() {
		return this.operationMessage;
	}
}

