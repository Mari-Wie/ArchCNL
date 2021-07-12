package org.archcnl.owlify.famix.ontology;

import java.io.InputStream;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.core.MainOntology;

public class FamixOntology {
    public static final String PREFIX = "http://arch-ont.org/ontologies/famix.owl#";

    private OntModel codeModel;
    private MainOntology mainModel;
    private DefinedTypeCache definedTypes;
    private AnnotationAttributeCache annotationAttributes;

    public FamixOntology(InputStream famixOntology, InputStream mainOntology) {
        loadBaseOntologies(famixOntology);
        mainModel = new MainOntology(mainOntology);
        definedTypes = new DefinedTypeCache();
        annotationAttributes = new AnnotationAttributeCache();
    }

    public OntModel codeModel() {
        return codeModel;
    }

    public MainOntology mainModel() {
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

    /**
     * Creates a new individual of the given ontology class.
     *
     * @param clazz The ontology class to which the individual will belong.
     * @param uri The "base name" of the individual's URI. Invalid characters will be displayed in
     *     percent-notation. Spaces will be replaced by a -. Also, the class will be added as a
     *     prefix. No other individual of the same class should have this name.
     * @return The created individual.
     */
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
        Parameter,
        LocalVariable,
        Inheritance;

        public Individual createIndividual(OntModel model, String name) {
            return model.getOntClass(uri()).createIndividual(individualUri(name));
        }

        private String replaceSpecialCharacters(String name) {
            // uses percent-notation, replaces spaces by '-'
            return name.replace(" ", "-")
                    .replace("%", "%25") // must be before the replacements using %
                    .replace("!", "%21")
                    .replace("#", "%23")
                    .replace("$", "%24")
                    .replace("&", "%26")
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace("*", "%2A")
                    .replace("+", "%2B")
                    .replace(",", "%2C")
                    .replace("/", "%2F")
                    .replace(":", "%3A")
                    .replace(";", "%3B")
                    .replace("<", "%3C")
                    .replace("=", "%3D")
                    .replace(">", "%3E")
                    .replace("?", "%3F")
                    .replace("@", "%40")
                    .replace("[", "%5B")
                    .replace("]", "%5D");
        }

        public String uri() {
            return PREFIX + this.name();
        }

        public String individualUri(String name) {
            return this.uri() + "/" + replaceSpecialCharacters(name);
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
