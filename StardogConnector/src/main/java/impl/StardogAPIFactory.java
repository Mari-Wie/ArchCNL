package impl;

import api.StardogDatabaseAPI;
import api.StardogICVAPI;

public class StardogAPIFactory {
	private static StardogICVAPI icvAPI;
	
	/**
	 * Builds a new instance of a class implementing StardogICVAPI
	 * @param db The database to be used by the instance.
	 * @return the created instance
	 */
	public static StardogICVAPI getICVAPI(StardogDatabaseAPI db) {
		if(icvAPI == null) {
			icvAPI = new StardogICVAPIImpl(db);
		}
		return icvAPI;
	}

}
