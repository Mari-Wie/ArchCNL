package api;

public class ExecuteMappingAPIFactory {
	
	public static ExecuteMappingAPI get() {
		return new ExecuteMappingAPIImpl();
	}

}
