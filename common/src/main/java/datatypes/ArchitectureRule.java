package datatypes;

/**
 * Class for exchanging architecture rules between modules.
 */
public class ArchitectureRule {
	// TODO: change to value class?
	private Integer id;
	private String cnlSentence;
	private String owlAxiom;
	private String secondOWLAxiom; //for domain range constraint
	private String contraintFile;
	
	private RuleType type;
	
	/**
	 * @return the ID of this rule
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * sets this rule's ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the CNL sentence represented by this rule
	 */
	public String getCnlSentence() {
		return cnlSentence;
	}

	/**
	 * sets the CNL sentence which is represented by this rule
	 */
	public void setCnlSentence(String cnlSentence) {
		this.cnlSentence = cnlSentence;
	}

	/**
	 * @return the OWL axiom associated with this rule (as a string)
	 */
	public String getOwlAxiom() {
		return owlAxiom;
	}

	/**
	 * sets the OWL axiom associated with this rule (as a string)
	 */
	public void setOwlAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}

	public void setSecondOWLAxiom(String axiom) {
		this.secondOWLAxiom = axiom;
	}
	
	public String getSecondOWLAxiom() {
		return secondOWLAxiom;
	}

	/**
	 * @return The type of this rule. It corresponds to the way how this rule's CNL sentence can be deferred from the grammar.
	 */
	public RuleType getType() {
		return type;
	}

	/**
	 * Sets the type of this rule. It corresponds to the way how this rule's CNL sentence can be deferred from the grammar.
	 */
	public void setType(RuleType type) {
		this.type = type;
	}

	/**
	 * @return the path to the file which stores this rule as an OWL constraint
	 */
	public String getContraintFile() {
		return contraintFile;
	}

	/**
	 * @param contraintFile the path to the file which stores this rule as an OWL constraint
	 */
	public void setContraintFile(String contraintFile) {
		this.contraintFile = contraintFile;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnlSentence == null) ? 0 : cnlSentence.hashCode());
		result = prime * result + ((contraintFile == null) ? 0 : contraintFile.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owlAxiom == null) ? 0 : owlAxiom.hashCode());
		result = prime * result + ((secondOWLAxiom == null) ? 0 : secondOWLAxiom.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (contraintFile == null) {
			if (other.contraintFile != null)
				return false;
		} else if (!contraintFile.equals(other.contraintFile))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owlAxiom == null) {
			if (other.owlAxiom != null)
				return false;
		} else if (!owlAxiom.equals(other.owlAxiom))
			return false;
		if (secondOWLAxiom == null) {
			if (other.secondOWLAxiom != null)
				return false;
		} else if (!secondOWLAxiom.equals(other.secondOWLAxiom))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public String toString() {
		String result = "";
			result += "Id         :" + id  + "\n";
			result += "Cnl        : " + cnlSentence + "\n";

			if(type!=null) 
			{
				result += "Type       : " + type.toString() + "\n";
			}
		return result;
	}
	

}