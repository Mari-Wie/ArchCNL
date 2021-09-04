package org.archcnl.domain.input.datatypes.mappings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.datatypes.mappings.Relation.RelationType;
import org.archcnl.domain.input.exceptions.RecursiveRelationException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;
    private String name;

    public RelationMapping(
            String name,
            Variable subjectVariable,
            Variable objectVariable,
            Concept typeOfObjectVariable,
            List<AndTriplets> whenTriplets)
            throws RecursiveRelationException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet {
        super(whenTriplets);

        this.name = name;
        getVariableManager().addVariable(subjectVariable);
        getVariableManager().addVariable(objectVariable);
        List<Concept> concepts = new LinkedList<>(Arrays.asList(typeOfObjectVariable));
        Relation thisRelation = new Relation(name, RelationType.architecture, concepts);
        thenTriplet = new Triplet(subjectVariable, thisRelation, objectVariable);
    }

    public RelationMapping(
            String name,
            Variable subjectVariable,
            Variable objectVariable,
            List<AndTriplets> whenTriplets)
            throws RecursiveRelationException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet {
        super(whenTriplets);

        this.name = name;
        getVariableManager().addVariable(subjectVariable);
        getVariableManager().addVariable(objectVariable);
        List<Concept> concepts =
                new LinkedList<>(); // Here is the difference between the constructors
        Relation thisRelation = new Relation(name, RelationType.architecture, concepts);
        thenTriplet = new Triplet(subjectVariable, thisRelation, objectVariable);
    }

    public void updateSubjectInThenTriplet(Variable newSubject)
            throws VariableDoesNotExistException {
        if (getVariableManager().doesVariableExist(newSubject)) {
            thenTriplet.setSubject(newSubject);
        } else {
            throw new VariableDoesNotExistException(newSubject.getName());
        }
    }

    public void updateObjectInThenTriplet(Variable newObject)
            throws VariableDoesNotExistException, RecursiveRelationException,
                    UnsupportedObjectTypeInTriplet {
        if (getVariableManager().doesVariableExist(newObject)) {
            thenTriplet.setObject(newObject);
        } else {
            throw new VariableDoesNotExistException(newObject.getName());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void updateName(String newName) throws RelationAlreadyExistsException {
        List<Concept> concepts = thenTriplet.getPredicate().getConcepts();
        Relation newRelation = new Relation(newName, RelationType.architecture, concepts);
        name = newName;
        thenTriplet.setPredicate(newRelation);
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    @Override
    public String getMappingNameRepresentation() {
        return name + "Mapping";
    }
}
