package impl;

import api.StardogICVAPI;

public class StardogAPIFactory {
	private static StardogICVAPI icvAPI;
	
	public static StardogICVAPI getICVAPI() {
		if(icvAPI == null) {
			icvAPI = new StardogICVAPIImpl();
		}
		return icvAPI;
	}

}
