package org.archcnl.webui.mappings;

import java.util.List;
import org.archcnl.webui.exceptions.ConceptAlreadyExistsException;
import org.archcnl.webui.exceptions.RelationCannotRelateToObjectException;
import org.archcnl.webui.exceptions.RelationDoesNotExistException;
import org.archcnl.webui.exceptions.VariableDoesNotExistException;
import org.archcnl.webui.mappings.Concept.ConceptType;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;
    private String name;

    protected ConceptMapping(
            String name,
            Variable thenVariable,
            List<AndTriplets> whenTriplets,
            ConceptManager conceptManager,
            RelationManager relationManager)
            throws ConceptAlreadyExistsException, RelationDoesNotExistException,
                    RelationCannotRelateToObjectException {
        super(whenTriplets, conceptManager, relationManager);

        this.name = name;
        Relation typeRelation = getRelationManager().getRelationByName("is-of-type");
        Concept thisConcept = new Concept(name, ConceptType.architecture);
        getConceptManager().addConcept(thisConcept);
        thenTriplet = new Triplet(thenVariable, typeRelation, thisConcept);
    }

    public void updateThenTriplet(Variable subject) throws VariableDoesNotExistException {
        if (getVariableManager().doesVariableExist(subject)) {
            thenTriplet.setSubject(subject);
        } else {
            throw new VariableDoesNotExistException(subject.getName());
        }
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    public void updateName(String newName) throws ConceptAlreadyExistsException {
        Concept newObject = new Concept(newName, ConceptType.architecture);
        getConceptManager().addConcept(newObject);
        name = newName;
        thenTriplet.setObject(newObject);
    }

    @Override
    public String getName() {
        return name;
    }
}
