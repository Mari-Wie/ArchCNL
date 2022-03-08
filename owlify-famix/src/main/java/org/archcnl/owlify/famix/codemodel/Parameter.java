package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesParameter;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;

import java.util.List;
import java.util.Optional;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;

import com.github.javaparser.Position;

/**
 * Models a parameter of a method.
 *
 * <p>Represented by the "Parameter" ontology class.
 */
public class Parameter {
    private final String name;
    private final Type type;
    private List<Modifier> modifiers;
    private List<AnnotationInstance> annotations;
    private String path;
    private Optional<Position> beginning;

    /**
     * Constructor.
     *
     * @param name Simple name of this parameter.
     * @param type Declared type of this parameter.
     * @param modifiers List of modifiers which apply to this parameter.
     * @param annotations List of annotation instances which appliy to this parameter.
     */
    public Parameter(
            String name,
            Type type,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations,
            String path,
            Optional<Position> beginning) {
        super();
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.annotations = annotations;
        this.path = path;
        this.beginning = beginning;
    }

    /** @return the simple name */
    public String getName() {
        return name;
    }

    /** @return the declared type */
    public Type getType() {
        return type;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    /** @return the annotations */
    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the beginning
	 */
	public Optional<Position> getBeginning() {
		return beginning;
	}

	/**
     * Models this parameter in the given ontology.
     *
     * @param ontology The famix ontology in which this parameter will be modeled.
     * @param parentName A unique name identifying the method to which this parameter belongs.
     * @param method The OWL individual of the method to which this parameter belongs.
     */
    public void modelIn(FamixOntology ontology, String parentName, Individual method) {
        final String uri = parentName + "." + name;
        Individual individual = ontology.createIndividual(FamixClasses.Parameter, uri);
        individual.addProperty(ontology.get(hasDeclaredType), type.getIndividual(ontology));
        individual.addLiteral(ontology.get(hasName), name);
        
        String location = path;
        if (beginning.isPresent()) {
            location += ", Line: " + String.valueOf(beginning.get().line);
        }        
        individual.addLiteral(ontology.get(isLocatedAt), location);
        
        modifiers.forEach(mod -> mod.modelIn(ontology, individual));
        annotations.forEach(anno -> anno.modelIn(ontology, uri, individual));

        method.addProperty(ontology.get(definesParameter), individual);
    }
}
