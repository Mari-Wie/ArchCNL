package datatypes;

public class StatementTriple {
	
	private String subject;
	private String predicate;
	private String object;

	public StatementTriple(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getObject() {
		return object;
	}
	
	public String getPredicate() {
		return predicate;
	}
}
