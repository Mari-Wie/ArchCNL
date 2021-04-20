package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Field {
    private final String name;
    private final Type type;
    private List<AnnotationInstance> annotations;
    private List<Modifier> modifiers;

    public Field(
            String name,
            Type type,
            List<AnnotationInstance> annotations,
            List<Modifier> modifiers) {
        super();
        this.name = name;
        this.type = type;
        this.annotations = annotations;
        this.modifiers = modifiers;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the type */
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

    public void modelIn(FamixOntologyNew ontology, String parentName, Individual parent) {
        Individual attribute =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ATTRIBUTE)
                        .createIndividual(parentName + "." + name);

        parent.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.DEFINES_ATTRIBUTE), attribute);
        attribute.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_TYPE),
                type.getIndividual(ontology));
        attribute.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);

        modifiers.forEach(mod -> mod.modelIn(ontology, attribute));
        annotations.forEach(anno -> anno.modelIn(ontology, attribute));
    }
}
