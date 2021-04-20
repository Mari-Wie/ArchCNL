package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Parameter {
    private final String name;
    private final Type type;
    private List<Modifier> modifiers;
    private List<AnnotationInstance> annotations;

    public Parameter(
            String name,
            Type type,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations) {
        super();
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.annotations = annotations;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the type */
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

    public void modelIn(FamixOntologyNew ontology, String parentName, Individual method) {
        Individual individual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.PARAMETER)
                        .createIndividual(parentName + "." + name);

        individual.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_TYPE),
                type.getIndividual(ontology));
        individual.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);

        modifiers.forEach(mod -> mod.modelIn(ontology, individual));
        annotations.forEach(anno -> anno.modelIn(ontology, individual));

        method.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.DEFINES_PARAMETER), individual);
    }
}
