package api.exceptions;

public class StardogDatabaseDoesNotExist extends Exception {

	private static final long serialVersionUID = 8967745832412454291L;

	private String database;
	
	public StardogDatabaseDoesNotExist(String database) {

		this.database = database;
	}
	
	@Override
	public String getMessage() {

		return "The specified database " + this.database + " does not exist. Please create a database first.";
		
	}

}
