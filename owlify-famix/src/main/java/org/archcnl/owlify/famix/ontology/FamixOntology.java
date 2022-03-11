package org.archcnl.owlify.famix.ontology;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
            try {
                var url = URLEncoder.encode(name, "UTF-8");
                // The URLEncoder does not encode '*' because in an URL it is not a reserved
                // character.
                // However, we are generating an URI and technically '*' is a reserved character
                // there.
                // See RFC3986 page 12-13
                return url.replace("*", "%2A");
            } catch (UnsupportedEncodingException ex) {
                // It is very unlikely that a system does not support UTF-8.
                // Even if it does not, we cannot do anything against it.
                throw new RuntimeException(
                        "System does not support UTF-8 encoding, cannot encode URIs");
            }
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
        isLocatedAt,
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
        hasSuperClass,
        definesNestedType;

        public ObjectProperty getProperty(OntModel model) {
            return model.getObjectProperty(uri());
        }

        public String uri() {
            return PREFIX + this.name();
        }
    }
}
