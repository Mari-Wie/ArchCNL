package api;

/**
 * Factory for the interface ExecuteMappingAPI.
 */
public class ExecuteMappingAPIFactory {
	
	/**
	 * Returns a freshly created object of a class implementing the interface
	 * ExecuteMappingAPI.
	 */
	public static ExecuteMappingAPI get() {
		return new ExecuteMappingAPIImpl();
	}

}
