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
    private List<AndTriplets> andTripletsList;

    protected MappingEditorPresenter(List<AndTriplets> andTripletsList) {
        this.variableManager = new VariableManager();
        this.andTripletsList = andTripletsList;
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
        List<AndTripletsEditorView> andTripletsViews =
                andTripletsList.stream()
                        .map(this::createAndTripletsView)
                        .collect(Collectors.toList());
        if (!andTripletsViews.isEmpty()) {
            view.clearContent();
        }
        andTripletsViews.forEach(andTripletsView -> view.addAndTripletsView(andTripletsView));
    }

    @Override
    public void addNewAndTripletsViewAfter(AndTripletsEditorContract.View andTripletsView) {
        int previousIndex = view.getIndexOf(andTripletsView);
        view.addAndTripletsViewAtIndex(previousIndex + 1, createAndTripletsView(new AndTriplets()));
    }

    @Override
    public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView) {
        view.deleteAndTripletsView(andTripletsView);
    }

    @Override
    public int numberOfAndTriplets() {
        return getAndTriplets().size();
    }

    @Override
    public void lastAndTripletsDeleted() {
        view.addAndTripletsView(createAndTripletsView(new AndTriplets()));
    }

    @Override
    public void showFirstAndTripletsView() {
        view.addAndTripletsView(createAndTripletsView(new AndTriplets()));
    }

    private AndTripletsEditorView createAndTripletsView(AndTriplets andTriplets) {
        AndTripletsEditorPresenter andTripletsEditorPresenter =
                new AndTripletsEditorPresenter(getVariableManager(), this, andTriplets);
        return new AndTripletsEditorView(andTripletsEditorPresenter);
    }

    protected List<AndTriplets> getAndTriplets() {
        return view.getAndTripletsPresenters().stream()
                .map(AndTripletsEditorPresenter::getAndTriplets)
                .collect(Collectors.toList());
    }

    protected abstract void updateMappingName(String newName) throws MappingAlreadyExistsException;

    protected abstract void initInfoFieldAndThenTriplet();
}
