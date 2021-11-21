package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class SubjectPresenter extends VariableSelectionPresenter {

    private static final long serialVersionUID = 7992050926821966999L;

    public SubjectPresenter(VariableManager variableManager) {
        super(variableManager);
    }

    public Variable getSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return getSelectedVariable();
    }

    public void setSubject(Variable subject) {
        setSelectedVariable(subject);
    }

    public void highlightWhenEmpty() {
        try {
            getSubject();
        } catch (InvalidVariableNameException e) {
            view.showErrorMessage("Invalid Variable name");
        } catch (SubjectOrObjectNotDefinedException e) {
            view.showErrorMessage("Variable not set");
        }
    }
}
