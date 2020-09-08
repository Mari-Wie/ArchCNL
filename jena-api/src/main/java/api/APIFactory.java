package api;

public class APIFactory {
	
	public static OntologyAPI instance;
	
	public static OntologyAPI get() {

		if(instance == null) {
			instance = new OWLAPIImpl2();
		}
		
		return instance;
	}
	
}
