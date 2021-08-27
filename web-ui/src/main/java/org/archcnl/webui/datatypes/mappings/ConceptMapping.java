package org.archcnl.webui.datatypes.mappings;

import java.util.List;
import org.archcnl.webui.datatypes.mappings.Concept.ConceptType;
import org.archcnl.webui.exceptions.ConceptAlreadyExistsException;
import org.archcnl.webui.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;
import org.archcnl.webui.exceptions.VariableDoesNotExistException;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;
    private String name;

    public ConceptMapping(
            String name,
            Variable thenVariable,
            List<AndTriplets> whenTriplets,
            Relation typeRelation)
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet {
        super(whenTriplets);

        this.name = name;
        getVariableManager().addVariable(thenVariable);
        Concept thisConcept = new Concept(name, ConceptType.architecture);
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

    public void updateName(String newName)
            throws ConceptAlreadyExistsException, UnsupportedObjectTypeInTriplet {
        Concept newObject = new Concept(newName, ConceptType.architecture);
        name = newName;
        thenTriplet.setObject(newObject);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMappingNameRepresentation() {
        return "is" + name;
    }
}
