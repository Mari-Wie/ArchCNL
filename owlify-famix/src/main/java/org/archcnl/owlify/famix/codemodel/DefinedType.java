package org.archcnl.owlify.famix.codemodel;

import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public abstract class DefinedType {
    private final String name;
    private List<AnnotationInstance> annotations;
    private List<Modifier> modifiers;

    protected DefinedType(
            String name, List<AnnotationInstance> annotations, List<Modifier> modifiers) {
        this.name = name;
        this.annotations = annotations;
        this.modifiers = modifiers;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void firstPass(FamixOntologyNew ontology) {
        Individual individual = createIndividual(ontology);

        individual.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);
        individual.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME), name);
        individual.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.IS_EXTERNAL), false);

        ontology.typeCache().addDefinedType(name, individual);

        firstPassPostProcess(ontology);
    }

    public void secondPass(FamixOntologyNew ontology) {
        Individual individual = ontology.typeCache().getIndividual(name);

        annotations.forEach(anno -> anno.modelIn(ontology, individual));
        modifiers.forEach(mod -> mod.modelIn(ontology, individual));

        secondPassProcess(ontology, individual);
    }

    public List<String> getNestedTypeNames() {
        return Arrays.asList(name);
    }

    protected abstract Individual createIndividual(FamixOntologyNew ontology);

    protected abstract void firstPassPostProcess(FamixOntologyNew ontology);

    protected abstract void secondPassProcess(FamixOntologyNew ontology, Individual individual);
}
