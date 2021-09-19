package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.View;

public class SubjectPresenter implements Presenter<View> {

    private static final long serialVersionUID = 7992050926821966999L;
    private MappingEditorContract.Presenter<MappingEditorContract.View> superPresenter;
    private View view;

    public SubjectPresenter(
            MappingEditorContract.Presenter<MappingEditorContract.View> superPresenter) {
        this.superPresenter = superPresenter;
    }

    public Variable getSubject() throws VariableDoesNotExistException, SubjectNotDefinedException {
        String variableName = view.getSelectedItem().orElseThrow(SubjectNotDefinedException::new);
        return superPresenter.getVariableManager().getVariableByName(variableName);
    }

    @Override
    public List<String> getVariableNames() {
        return superPresenter.getVariableManager().getVariables().stream()
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
            superPresenter.getVariableManager().addVariable(newVariable);
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
            superPresenter.getVariableManager().getVariableByName(variableName);
            return true;
        } catch (VariableDoesNotExistException e) {
            return false;
        }
    }
}
