package parser;
public enum ProgrammingLanguage {
	
	JAVA;

	public static String getFileExtensionWildCard(ProgrammingLanguage language) {
		if(language.toString().equals(JAVA.toString())) {
			return "*.java";
		}
		return null;
	}

	

}
