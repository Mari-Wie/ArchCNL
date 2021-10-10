package org.archcnl.ui.input.mappingeditor;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.exceptions.MappingAlreadyExistsException;

public abstract class MappingEditorPresenter implements MappingEditorContract.Presenter<View> {

    private static final long serialVersionUID = -9123529250149326943L;
    protected View view;
    private VariableManager variableManager;

    protected MappingEditorPresenter(List<AndTriplets> andTriplets) {
        this.variableManager = new VariableManager();
    }

    @Override
    public void nameHasChanged(String newName) {
        view.updateNameField(newName);
        view.updateNameFieldInThenTriplet(newName);
        try {
            updateMappingName(newName);
        } catch (MappingAlreadyExistsException e) {
            view.showNameFieldErrorMessage("The name is already taken");
        }
    }

    @Override
    public VariableManager getVariableManager() {
        return variableManager;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        initInfoFieldAndThenTriplet();
    }

    @Override
    public void addNewAndTripletsViewAfter(AndTripletsEditorContract.View andTripletsView) {
        view.addNewAndTripletsViewAfter(andTripletsView);
    }

    @Override
    public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView) {
        view.deleteAndTripletsView(andTripletsView);
    }

    @Override
    public int numberOfAndTriplets() {
        return getAndTriplets().size();
    }

    protected List<AndTriplets> getAndTriplets() {
        return view.getAndTripletsPresenters().stream()
                .map(AndTripletsEditorPresenter::getAndTriplets)
                .collect(Collectors.toList());
    }

    protected abstract void updateMappingName(String newName) throws MappingAlreadyExistsException;

    protected abstract void initInfoFieldAndThenTriplet();
}
