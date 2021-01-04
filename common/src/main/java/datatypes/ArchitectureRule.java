package datatypes;

/**
 * Class for exchanging architecture rules between modules.
 * 
 * Value object.
 */
public class ArchitectureRule {
	private final int id;
	private final String cnlSentence;
	private final String constraintFile;
	private final RuleType type;
	
	/**
	 * 
	 * @param id the ID of the rule
	 * @param cnlSentence the CNL sentence which is represented by this rule
	 * @param type the type of this rule. It corresponds to the way how this rule's CNL sentence can be deferred from the grammar.
	 * @param contraintFile the path to the file which stores this rule as an OWL constraint
	 */
	public ArchitectureRule(int id, String cnlSentence, RuleType type, String constraintFilePath) {
		this.id = id;
		this.cnlSentence = cnlSentence;
		this.constraintFile = constraintFilePath;
		this.type = type;
	}
	
	/**
	 * @return the ID of this rule
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the CNL sentence represented by this rule
	 */
	public String getCnlSentence() {
		return cnlSentence;
	}

	/**
	 * @return The type of this rule. It corresponds to the way how this rule's CNL sentence can be deferred from the grammar.
	 */
	public RuleType getType() {
		return type;
	}

	/**
	 * @return the path to the file which stores this rule as an OWL constraint
	 */
	public String getContraintFile() {
		return constraintFile;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnlSentence == null) ? 0 : cnlSentence.hashCode());
		result = prime * result + ((constraintFile == null) ? 0 : constraintFile.hashCode());
		result = prime * result + id;
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
		if (constraintFile == null) {
			if (other.constraintFile != null)
				return false;
		} else if (!constraintFile.equals(other.constraintFile))
			return false;
		if (id != other.id)
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