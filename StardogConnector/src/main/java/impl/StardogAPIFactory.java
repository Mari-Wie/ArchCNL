package impl;

import api.StardogConnectionAPI;
import api.StardogICVAPI;

public class StardogAPIFactory {
	
	private static StardogConnectionAPI connectionAPI;
	private static StardogICVAPI icvAPI;
	
	public static StardogConnectionAPI getConnectionAPI() {
		if(connectionAPI == null) {
			connectionAPI = new StardogConnectionAPIImpl();
		}
		return connectionAPI;
	}
	
	public static StardogICVAPI getICVAPI() {
		if(icvAPI == null) {
			icvAPI = new StardogICVAPIImpl();
		}
		return icvAPI;
	}

}
