package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Attribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasPath;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a field/attribute for a given type.
 *
 * <p>Represented by the "Attribute" ontology class.
 */
public class Field {
    private final String name;
    private final Type type;
    private List<AnnotationInstance> annotations;
    private List<Modifier> modifiers;
    private String location;

    /**
     * Constructor.
     *
     * @param name Simple name of the field.
     * @param type Declared type of the field.
     * @param annotations List of annotation instances for this field.
     * @param modifiers List of modifiers for this field.
     */
    public Field(
    		String location,
            String name,
            Type type,
            List<AnnotationInstance> annotations,
            List<Modifier> modifiers) {
        super();
        this.name = name;
        this.type = type;
        this.annotations = annotations;
        this.modifiers = modifiers;
        this.location = location;
    }

    /** @return the simple name */
    public String getName() {
        return name;
    }

    /** @return the declared type */
    public Type getType() {
        return type;
    }

    /** @return the annotations */
    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    /**
     * Models this field in the given ontology.
     *
     * @param ontology The famix ontology in which this field will be modeled.
     * @param parentName A unique name identifying the type to which this field belongs.
     * @param parent The OWL individual of the type to which this field belongs.
     */
    public void modelIn(FamixOntology ontology, String parentName, Individual parent) {
        String uri = parentName + "." + name;
        Individual attribute = ontology.createIndividual(Attribute, uri);
        parent.addProperty(ontology.get(definesAttribute), attribute);
        attribute.addProperty(ontology.get(hasDeclaredType), type.getIndividual(ontology));
        attribute.addLiteral(ontology.get(hasName), name);
        attribute.addLiteral(ontology.get(hasPath), location);

        modifiers.forEach(mod -> mod.modelIn(ontology, attribute));
        annotations.forEach(anno -> anno.modelIn(ontology, uri, attribute));
    }
}
