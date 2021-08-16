package org.archcnl.webui.mappings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.archcnl.webui.exceptions.RecursiveRelationException;
import org.archcnl.webui.exceptions.RelationAlreadyExistsException;
import org.archcnl.webui.exceptions.VariableDoesNotExistException;
import org.archcnl.webui.mappings.Relation.RelationType;

public class RelationMapping extends Mapping {
	
	private Triplet thenTriplet;
	private String name;

	public RelationMapping(String name, Variable subjectVariable, Concept typeOfObjectVariable, Variable objectVariable, List<AndTriplets> whenTriplets, ConceptManager conceptManager,
			RelationManager relationManager) throws RecursiveRelationException, RelationAlreadyExistsException {
		super(whenTriplets, conceptManager, relationManager);
		
		this.name = name;
		List<Concept> concepts = new LinkedList<>(Arrays.asList(typeOfObjectVariable));
		Relation thisRelation = new Relation(name, RelationType.architecture, concepts);
		getRelationManager().addRelation(thisRelation);
		thenTriplet = new Triplet(subjectVariable, thisRelation, objectVariable);
	}
	
	public void updateSubjectInThenTriplet(Variable newSubject) throws VariableDoesNotExistException {
		if (getVariableManager().doesVariableExist(newSubject)) {
			thenTriplet.setSubject(newSubject);
		}
		else {
			throw new VariableDoesNotExistException(newSubject.getName());
		}
	}
	
	public void updateObjectInThenTriplet(Variable newObject) throws VariableDoesNotExistException, RecursiveRelationException {
		if (getVariableManager().doesVariableExist(newObject)) {
			thenTriplet.setObject(newObject);
		}
		else {
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
		getRelationManager().addRelation(newRelation);
		name = newName;
		thenTriplet.setPredicate(newRelation);
	}

	@Override
	public Triplet getThenTriplet() {
		return thenTriplet;
	}

}
