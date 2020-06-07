package parser;

import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;

public class ExternalLibraryParser {

	private OntModel ontoModel;
	private String namespace;
	
	public ExternalLibraryParser(OntModel model, String namespace) {
		this.ontoModel = model;
		this.namespace = namespace;
	}
	
	public void detectExternalLibraries() {
		Map<String, Individual> typeIndividuals = IndividualCache.getInstance().getTypeIndividuals();
		for(String type: typeIndividuals.keySet()) {
			Individual individual = typeIndividuals.get(type);
			//an external library class is an individual of a JavaClass that has no fields or methods. This means, the properties definesField and declaredMethod are not assigned to this individual
			ObjectProperty declaresMethod = ontoModel.getObjectProperty(namespace + "declaresMethod");
			ObjectProperty definesField = ontoModel.getObjectProperty(namespace + "definesField");
			DatatypeProperty isExternalProperty = ontoModel.getDatatypeProperty(namespace + "isExternal");
			boolean isExternal = !individual.hasProperty(declaresMethod) && !individual.hasProperty(definesField);
			if(isExternal) {
				individual.addProperty(isExternalProperty, "true");
			}
		}
	}

}
