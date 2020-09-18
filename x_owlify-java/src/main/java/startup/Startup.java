package startup;

import parser.OntologyJavaParser;

public class Startup {

	public static void main(String[] args) {

		OntologyExtractionConfiguration config = DefaultConfigurations.defaultConfig();
		OntologyJavaParser parser = new OntologyJavaParser(config);
		parser.execute();
	}
}
