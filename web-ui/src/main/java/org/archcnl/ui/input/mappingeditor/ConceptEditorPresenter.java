package org.archcnl.ui.input.mappingeditor;

import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.model.mappings.Variable;

public class ConceptEditorPresenter extends MappingEditorPresenter {

	private static final long serialVersionUID = 1636256374259524105L;
	private ConceptMapping mapping;

	public ConceptEditorPresenter() throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException, InvalidVariableNameException {
		this.mapping =  new ConceptMapping(new Variable("Test"), null, null);
	}
	
	public ConceptEditorPresenter(ConceptMapping mapping) {
		this.mapping = mapping;
	}

	@Override
	public Mapping getMapping() {
		return mapping;
	}

}
