package core;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class GeneralSoftwareArtifactClassesAndProperties {

	private String namespace;
	
	public GeneralSoftwareArtifactClassesAndProperties() {
		namespace = "http://arch-ont.org/ontologies/main.owl#";
	}
	
	public Individual getSoftwareArtifactFileIndividual(OntModel model, long artifactId) {
		OntClass artifactClass = model.getOntClass(namespace + "SoftwareArtifactFile");
		return model.createIndividual(namespace+"SoftwareArtifactFile"+artifactId, artifactClass);
	}

	public ObjectProperty getFileContainsProperty(OntModel model) {
		return model.getObjectProperty(namespace+"containsArtifact");
	}

	public DatatypeProperty getHasPathProperty(OntModel model) {
		return model.getDatatypeProperty(namespace+"hasPath");
	}

}
