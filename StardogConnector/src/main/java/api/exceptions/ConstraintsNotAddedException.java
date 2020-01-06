package api.exceptions;

public class ConstraintsNotAddedException extends Exception {
	
	@Override
	public String getMessage() {

		return "Constraints were not added to database.";
	
	}

}
