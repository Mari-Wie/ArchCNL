package datatypes;

public class ArchitectureRule {
	 
	private Integer id;
	private String cnlSentence;
	private String owlAxiom;
	private String secondOWLAxiom; //for domain range constraint
	private int count; //for cardinality constraints
	
    // Ruletype war immer NULL und wird im Code nicht gesetzt (nur in 
	// conformancecheck.StartUp.main(), die nicht aufgerufen wird, 
	// daher RuleType hier auf Default = Existential gesetzt.
	private RuleType type; //=RuleType.EXISTENTIAL; 
	
	@SuppressWarnings("unused")
	private String stardogConstraint;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public String getCnlSentence() {
		return cnlSentence;
	}

	public void setCnlSentence(String cnlSentence) {
		this.cnlSentence = cnlSentence;
	}

	public String getOwlAxiom() {
		return owlAxiom;
	}

	public void setOwlAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}

	public void setSecondOWLAxiom(String axiom) {
		this.secondOWLAxiom = axiom;
	}
	
	public String getSecondOWLAxiom() {
		return secondOWLAxiom;
	}

	public RuleType getType() {
		return type;
	}

	public void setType(RuleType type) {
		this.type = type;
	}

	public void setStardogConstraint(String constraint) {
		this.stardogConstraint = constraint;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnlSentence == null) ? 0 : cnlSentence.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArchitectureRule other = (ArchitectureRule) obj;
		if (cnlSentence == null) {
			if (other.cnlSentence != null)
				return false;
		} else if (!cnlSentence.equals(other.cnlSentence))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public String toString() {
		String result = "";
			result += "Id         :" + id  + "\n";
			result += "Cnl        : " + cnlSentence + "\n";
//			result += "OwlAxiom   : " + owlAxiom + "\n";
//			result += "Count      :" + count + "\n";
//			if(secondOWLAxiom!=null) 
//			{
//				result += "2. OwlAxiom: " + secondOWLAxiom) + "\n"
//			};
			if(type!=null) 
			{
				result += "Type       : " + type.toString() + "\n";
			}
		return result;
	}
	

}