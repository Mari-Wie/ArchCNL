package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;

public class SubjectPresenter extends VariableSelectionPresenter {

    private static final long serialVersionUID = 7992050926821966999L;

    public SubjectPresenter(VariableManager variableManager) {
        super(variableManager);
    }

    public Variable getSubject() throws VariableDoesNotExistException, SubjectNotDefinedException {
        return getSelectedVariable();
    }
}
