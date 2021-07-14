package org.archcnl.owlify.core;

import java.io.InputStream;
import java.util.List;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainOntology {
    public static final String namespace = "http://arch-ont.org/ontologies/main.owl#";
    private static final Logger LOG = LogManager.getLogger(MainOntology.class);

    public enum MainOntClasses {
        SoftwareArtifactFile;

        /**
         * Creates a new individual for this ontology class.
         *
         * @param model The model in which the individual will be created.
         * @param name A unique identifier of this individual. Must only be unique within the same
         *     ontology class.
         * @return The created individual.
         */
        public Individual create(OntModel model, String name) {
            return model.getOntClass(uri()).createIndividual(uri() + "/" + name);
        }

        /** @return The URI of this class in the base ontology. */
        public String uri() {
            return namespace + this.name();
        }
    }

    public enum MainObjectProperties {
        containsArtifact;

        /**
         * Returns this property.
         *
         * @param model The model.
         * @return The property as represented in the given model.
         */
        public ObjectProperty get(OntModel model) {
            return model.getObjectProperty(uri());
        }

        /** @return The URI of this property in the base ontology. */
        public String uri() {
            return namespace + this.name();
        }
    }

    public enum MainDatatypeProperties {
        hasPath;

        /**
         * Returns this property.
         *
         * @param model The model.
         * @return The property as represented in the given model.
         */
        public DatatypeProperty get(OntModel model) {
            return model.getDatatypeProperty(uri());
        }

        /** @return The URI of this property in the base ontology. */
        public String uri() {
            return namespace + this.name();
        }
    }

    private OntModel model;

    public MainOntology(InputStream ontologyInputStream) {
        loadOntology(ontologyInputStream);
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
    /**
     * Models a software artifact file in this ontology. Software artifact files are all files which
     * are analyzed by "input parsers", e.g. a Maven pom.xml or a source code file.
     *
     * @param path The path to the file or another unique identifier.
     * @param containedIndividuals A list of OWL individuals of "top level concepts" which are
     *     defined in the file, e.g. the top-level classes.
     */
    public void modelSoftwareArtifactFile(String path, List<Individual> containedIndividuals) {
        Individual artifact = MainOntClasses.SoftwareArtifactFile.create(model, path);
        artifact.addLiteral(MainDatatypeProperties.hasPath.get(model), path);
        containedIndividuals.forEach(
                i -> artifact.addProperty(MainObjectProperties.containsArtifact.get(model), i));
    }

    public OntModel getOntology() {
        return model;
    }

    private void loadOntology(InputStream ontologyInpuStream) {
        LOG.debug("Reading resource ontology");
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        model.read(ontologyInpuStream, null);
    }
}
