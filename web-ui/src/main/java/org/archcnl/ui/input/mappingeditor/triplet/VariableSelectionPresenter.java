package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionContract.View;

public class VariableSelectionPresenter implements VariableSelectionContract.Presenter<View> {

    private static final long serialVersionUID = -7216588985374428140L;
    private VariableManager variableManager;
    private View view;

    public VariableSelectionPresenter(VariableManager variableManager) {
        this.variableManager = variableManager;
    }

    public Variable getSelectedVariable()
            throws VariableDoesNotExistException, SubjectNotDefinedException {
        String variableName = view.getSelectedItem().orElseThrow(SubjectNotDefinedException::new);
        return variableManager.getVariableByName(variableName);
    }

    @Override
    public List<String> getVariableNames() {
        return variableManager.getVariables().stream()
                .map(Variable::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void addCustomValue(String variableName) {
        try {
            Variable newVariable = new Variable(variableName);
            variableManager.addVariable(newVariable);
            view.updateItems();
            view.setItem(newVariable.getName());
        } catch (InvalidVariableNameException e) {
            view.showErrorMessage("Invalid variable name");
        } catch (VariableAlreadyExistsException e) {
            view.showErrorMessage("Variable already exists");
        }
    }

    @Override
    public boolean doesVariableExist(String variableName) {
        try {
            return variableManager.doesVariableExist(new Variable(variableName));
        } catch (InvalidVariableNameException e) {
            return false;
        }
    }

    @Override
    public void handleDropEvent(Object data) {
        if (data instanceof Variable) {
            Variable variable = (Variable) data;
            view.setItem(variable.getName());
        } else {
            view.showErrorMessage("Not a Variable");
        }
    }
}
