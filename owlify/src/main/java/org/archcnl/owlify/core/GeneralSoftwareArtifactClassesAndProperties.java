package org.archcnl.owlify.core;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

public class GeneralSoftwareArtifactClassesAndProperties {

    private static final String namespace = "http://arch-ont.org/ontologies/main.owl#";
    public static final String SOFTWARE_ARTIFACT_FILE = namespace + "SoftwareArtifactFile";

    public GeneralSoftwareArtifactClassesAndProperties() {}

    public Individual getSoftwareArtifactFileIndividual(OntModel model, String name) {
        OntClass artifactClass = model.getOntClass(SOFTWARE_ARTIFACT_FILE);
        return artifactClass.createIndividual(SOFTWARE_ARTIFACT_FILE + "/" + name);
    }

    public ObjectProperty getFileContainsProperty(OntModel model) {
        return model.getObjectProperty(namespace + "containsArtifact");
    }

    public DatatypeProperty getHasPathProperty(OntModel model) {
        return model.getDatatypeProperty(namespace + "hasPath");
    }
}
