package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.AnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixObjectProperties.hasAnnotationType;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

/**
 * Models an annotation instance, i.e. the "use" of an annotation. For instance, when some class is
 * annotated with the annotation "(at)Deprecated", this is an annotation instance of the annotation
 * (type) "Deprecated".
 *
 * <p>Represented by the "AnnotationInstance" ontology class.
 */
public class AnnotationInstance {
    private final String name;
    private List<AnnotationMemberValuePair> values;

    /**
     * Constructor.
     *
     * @param name Fully qualified name of the instantiated annotation type.
     * @param values List of member value pairs present in the instance.
     */
    public AnnotationInstance(String name, List<AnnotationMemberValuePair> values) {
        super();
        this.name = name;
        this.values = values;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the values */
    public List<AnnotationMemberValuePair> getValues() {
        return values;
    }

    /**
     * Models this annotation instance in the given famix ontology.
     *
     * @param ontology The famix ontology in which this will be modeled.
     * @param parentName A unique name identifying the node which is annotated by this instance.
     * @param entity The (OWL) individual of the node which is annotated by this instance.
     */
    public void modelIn(FamixOntologyNew ontology, String parentName, Individual entity) {
        final String uri = parentName + "-" + name;
        Individual individual = ontology.createIndividual(AnnotationInstance, uri);

        entity.addProperty(ontology.get(hasAnnotationInstance), individual);

        if (!ontology.typeCache().isDefined(name)) {
            createNewAnnotationType(ontology);
        }

        individual.addProperty(
                ontology.get(hasAnnotationType), ontology.typeCache().getIndividual(name));

        for (AnnotationMemberValuePair memberValuePair : values) {
            memberValuePair.modelIn(ontology, name, uri, individual);
        }
    }

    private void createNewAnnotationType(FamixOntologyNew ontology) {
        // this code is different from the similar code in Type.getIndividual()
        // in this case, the external type is known to be an annotation
        Individual annotationType = ontology.createIndividual(AnnotationType, name);
        annotationType.addLiteral(ontology.get(isExternal), true);
        annotationType.addLiteral(ontology.get(hasFullQualifiedName), name);
        ontology.typeCache().addDefinedType(name, annotationType);
    }
}
