package org.archcnl.owlify.famix.ontology;

import java.io.InputStream;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.core.GeneralSoftwareArtifactOntology;

public class FamixOntologyNew {
    public static final String PREFIX = "http://arch-ont.org/ontologies/famix.owl#";

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

    public Individual createIndividual(FamixClasses clazz, String uri) {
        return clazz.createIndividual(codeModel, uri);
    }

    public DatatypeProperty get(FamixDatatypeProperties prop) {
        return prop.getProperty(codeModel);
    }

    public ObjectProperty get(FamixObjectProperties prop) {
        return prop.getProperty(codeModel);
    }

    public enum FamixClasses {
        // names match the URIs in the base ontology (minus the namespace)
        Namespace,
        Enum,
        FamixClass,
        AnnotationType,
        AnnotationTypeAttribute,
        AnnotationInstance,
        AnnotationInstanceAttribute,
        Attribute,
        Method,
        DeclaredException,
        Parameter,
        CaughtException,
        ThrownException,
        LocalVariable,
        Inheritance;

        public Individual createIndividual(OntModel model, String name) {
            return model.getOntClass(uri()).createIndividual(individualUri(name));
        }

        public String uri() {
            return PREFIX + this.name();
        }

        public String individualUri(String name) {
            return this.name() + "/" + name;
        }
    }

    public enum FamixDatatypeProperties {
        // names match the URIs in the base ontology (minus the namespace)
        isExternal,
        hasName,
        hasFullQualifiedName,
        hasValue,
        hasModifier,
        hasSignature,
        isConstructor,
        isInterface;

        public DatatypeProperty getProperty(OntModel model) {
            return model.getDatatypeProperty(uri());
        }

        public String uri() {
            return PREFIX + this.name();
        }
    }

    public enum FamixObjectProperties {
        // names match the URIs in the base ontology (minus the namespace)
        namespaceContains,
        imports,
        hasAnnotationInstance,
        hasAnnotationType,
        hasAnnotationTypeAttribute,
        hasAnnotationInstanceAttribute,
        definesAttribute,
        hasDeclaredType,
        definesMethod,
        hasDefiningClass,
        hasDeclaredException,
        definesParameter,
        hasCaughtException,
        throwsException,
        definesVariable,
        hasSubClass,
        hasSuperClass;

        public ObjectProperty getProperty(OntModel model) {
            return model.getObjectProperty(uri());
        }

        public String uri() {
            return PREFIX + this.name();
        }
    }
}
