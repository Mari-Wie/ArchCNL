package datatypes;

public class CurrentArchitectureRule {
	
	private static ArchitectureRule rule;
	
	public static ArchitectureRule get() {
		return rule;
	}
	
	public static void set(ArchitectureRule r) {
		rule = r;
	}

}
