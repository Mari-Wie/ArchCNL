package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class AnnotationInstance {
    private final String name;
    private List<AnnotationMemberValuePair> values;

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

    public void modelIn(FamixOntologyNew ontology, Individual entity) {
        OntClass clazz = ontology.codeModel().getOntClass(FamixURIs.ANNOTATION_INSTANCE);
        Individual individual = clazz.createIndividual("TODO" + "-" + name); // TODO: name

        entity.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_INSTANCE),
                individual);

        // TODO: somewhat similar to getTypeIndividual()

        if (!ontology.typeCache().isUserDefined(name)) {
            Individual annotationType =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.ANNOTATION_TYPE)
                            .createIndividual(name);
            annotationType.addLiteral(
                    ontology.codeModel().getDatatypeProperty(FamixURIs.IS_EXTERNAL), true);
            annotationType.addLiteral(
                    ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);
        }

        Individual annotationType = ontology.typeCache().getIndividual(name);

        individual.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_TYPE),
                annotationType);

        for (AnnotationMemberValuePair memberValuePair : values) {
            memberValuePair.modelIn(ontology, name, individual);
        }
    }
}
