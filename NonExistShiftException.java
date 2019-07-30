package shiftman.server;

/**
 * This exception class is used to catch the exception when try to assign staff into a non exist shift. 
 */

public class NonExistShiftException extends Exception {
	
	//default
	private static final long serialVersionUID = 1L;
	
	String input;
	public NonExistShiftException (String errorInput) {
		input = errorInput;
	}
	
	public String toString () {
		return "ERROR: There is no available shift at " + input;
	}
}
