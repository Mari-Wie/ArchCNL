package org.archcnl.ui.input.mappingeditor;

import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private RelationMapping mapping;

    public RelationEditorPresenter()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    InvalidVariableNameException {
        super();
    }

    public RelationEditorPresenter(RelationMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    protected Mapping getMapping() {
        return mapping;
    }
}
