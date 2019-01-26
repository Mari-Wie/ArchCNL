package api;

public class JavaCodeOntologyAPIFactory {

	public static JavaCodeOntologyAPI get() {
		return new JavaCodeOntologyAPIImpl();
	}
	
}
