package org.archcnl.owlify.core;

import java.io.InputStream;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralSoftwareArtifactOntology {
    private static final Logger LOG = LogManager.getLogger(GeneralSoftwareArtifactOntology.class);

    private OntModel model;
    private GeneralSoftwareArtifactClassesAndProperties classesAndProperties;

    public GeneralSoftwareArtifactOntology(InputStream ontologyInputStream) {
        loadOntology(ontologyInputStream);
        classesAndProperties = new GeneralSoftwareArtifactClassesAndProperties();
    }

    private void loadOntology(InputStream ontologyInpuStream) {
        LOG.debug("Reading resource ontology");
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        model.read(ontologyInpuStream, null);
    }

    private void setSoftwareArtifactFileContainsSoftwareArtifact(
            Individual artifact, Individual file) {
        ObjectProperty property = classesAndProperties.getFileContainsProperty(model);
        artifact.addProperty(property, file);
    }

    /**
     * Returns a new SoftwareArtifactFile individual. Sets the properties linking the individual to
     * its path and the famix type individual it contains.
     *
     * @param path Path to the file represented by the individual.
     * @param famixTypeIndividual Individual referring to the famix type contained/defined in the
     *     file.
     * @return An SoftwareArtifactFile individual. The aforementioned properties are already set.
     */
    public Individual getSoftwareArtifactFileIndividual(
            String path, Individual famixTypeIndividual) {
        Individual softwareArtifact =
                classesAndProperties.getSoftwareArtifactFileIndividual(model, path);

        setHasFilePath(softwareArtifact, path);
        setSoftwareArtifactFileContainsSoftwareArtifact(softwareArtifact, famixTypeIndividual);
        return softwareArtifact;
    }

    private void setHasFilePath(Individual fileIndividual, String filePath) {
        DatatypeProperty property = classesAndProperties.getHasPathProperty(model);
        fileIndividual.addLiteral(property, filePath);
    }

    public OntModel getOntology() {
        return model;
    }
}
