package org.archcnl.ui.input.mappingeditor;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.domain.input.model.mappings.Triplet;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.View;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;

public class AndTripletsEditorPresenter implements Presenter<View> {

    private static final long serialVersionUID = 5409700467865922127L;
    private View view;
    private VariableManager variableManager;
    private MappingEditorContract.Presenter<MappingEditorContract.View> mappingEditorPresenter;

    public AndTripletsEditorPresenter(
            VariableManager variableManager,
            MappingEditorContract.Presenter<MappingEditorContract.View> mappingEditorPresenter) {
        this.variableManager = variableManager;
        this.mappingEditorPresenter = mappingEditorPresenter;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public VariableManager getVariableManager() {
        return variableManager;
    }

    @Override
    public void addNewTripletViewAfter(TripletContract.View tripletView) {
        view.addNewTripletViewAfter(tripletView);
    }

    @Override
    public void deleteTripletView(TripletContract.View tripletView) {
        view.deleteTripletView(tripletView);
    }

    public AndTriplets getAndTriplets() {
        List<Triplet> triplets =
                view.getTripletPresenters().stream()
                        .map(
                                presenter -> {
                                    try {
                                        return presenter.getTriplet();
                                    } catch (TripletNotDefinedException
                                            | UnsupportedObjectTypeInTriplet e) {
                                        // ignore this incomplete/faulty Triplet
                                        return null;
                                    }
                                })
                        .collect(Collectors.toList());
        return new AndTriplets(triplets);
    }

    @Override
    public void mouseEnter() {
        view.setAddButtonVisible(true);
    }

    @Override
    public void mouseLeave() {
        view.setAddButtonVisible(false);
    }

    @Override
    public void addButtonPressed() {
        mappingEditorPresenter.addNewAndTripletsViewAfter(view);
    }

    @Override
    public boolean isLastAndTripletsEditor() {
        return mappingEditorPresenter.numberOfAndTriplets() == 1;
    }

    @Override
    public void delete() {
        mappingEditorPresenter.deleteAndTripletsView(view);
    }
}
