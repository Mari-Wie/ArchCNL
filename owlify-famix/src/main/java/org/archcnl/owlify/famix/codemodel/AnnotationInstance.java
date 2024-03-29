package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationType;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

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
    private Path path;
    private Optional<Integer> beginning;

    /**
     * Constructor.
     *
     * @param name Fully qualified name of the instantiated annotation type.
     * @param values List of member value pairs present in the instance.
     */
    public AnnotationInstance(
            String name,
            List<AnnotationMemberValuePair> values,
            Path path,
            Optional<Integer> beginning) {
        super();
        this.name = name;
        this.values = values;
        this.path = path;
        this.beginning = beginning;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the values */
    public List<AnnotationMemberValuePair> getValues() {
        return values;
    }

    /** @return the path */
    public Path getPath() {
        return path;
    }

    /** @return the beginning */
    public Optional<Integer> getBeginning() {
        return beginning;
    }

    /**
     * Models this annotation instance in the given famix ontology.
     *
     * @param ontology The famix ontology in which this will be modeled.
     * @param parentName A unique name identifying the node which is annotated by this instance.
     * @param entity The (OWL) individual of the node which is annotated by this instance.
     */
    public void modelIn(FamixOntology ontology, String parentName, Individual entity) {
        final String uri = parentName + "-" + name;
        Individual individual = ontology.createIndividual(AnnotationInstance, uri);

        entity.addProperty(ontology.get(hasAnnotationInstance), individual);

        if (!ontology.typeCache().isDefined(name)) {
            createNewAnnotationType(ontology);
        }

        individual.addProperty(
                ontology.get(hasAnnotationType), ontology.typeCache().getIndividual(name));

        String location = path.toString();
        if (beginning.isPresent()) {
            location += ", Line: " + String.valueOf(beginning.get());
        }
        individual.addLiteral(ontology.get(isLocatedAt), location);

        for (AnnotationMemberValuePair memberValuePair : values) {
            memberValuePair.modelIn(ontology, name, uri, individual);
        }
    }

    private void createNewAnnotationType(FamixOntology ontology) {
        // this code is different from the similar code in Type.getIndividual()
        // in this case, the external type is known to be an annotation
        Individual annotationType = ontology.createIndividual(AnnotationType, name);
        annotationType.addLiteral(ontology.get(isExternal), true);
        annotationType.addLiteral(ontology.get(hasFullQualifiedName), name);

        ontology.typeCache().addDefinedType(name, annotationType);
    }
}
