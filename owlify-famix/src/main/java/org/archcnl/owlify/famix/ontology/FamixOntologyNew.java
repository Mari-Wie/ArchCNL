package org.archcnl.owlify.famix.ontology;

import java.io.InputStream;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.core.GeneralSoftwareArtifactOntology;

public class FamixOntologyNew {
    private OntModel codeModel;
    private GeneralSoftwareArtifactOntology mainModel;
    private DefinedTypeCache definedTypes;
    private AnnotationAttributeCache annotationAttributes;

    public FamixOntologyNew(InputStream famixOntology, InputStream mainOntology) {
        loadBaseOntologies(famixOntology);
        mainModel = new GeneralSoftwareArtifactOntology(mainOntology);
        definedTypes = new DefinedTypeCache();
        annotationAttributes = new AnnotationAttributeCache();
    }

    public OntModel codeModel() {
        return codeModel;
    }

    public GeneralSoftwareArtifactOntology mainModel() {
        return mainModel;
    }

    public DefinedTypeCache typeCache() {
        return definedTypes;
    }

    public AnnotationAttributeCache annotationAttributeCache() {
        return annotationAttributes;
    }

    /**
     * This method finalizes the ontology and returns the result. DO NOT call this method more than
     * once. DO NOT call it before the modelling is completed.
     */
    public Model getFinalModel() {
        codeModel.add(mainModel.getOntology());
        return codeModel;
    }

    private void loadBaseOntologies(InputStream famixOntology) {
        codeModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        codeModel.read(famixOntology, null);
    }
}
