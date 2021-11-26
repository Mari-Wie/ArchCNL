package org.archcnl.ui.input.mappingeditor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract.View;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;

public class AndTripletsEditorPresenter implements Presenter<View> {

    private static final long serialVersionUID = 5409700467865922127L;
    private View view;
    private VariableManager variableManager;
    private MappingEditorContract.Presenter<MappingEditorContract.View> mappingEditorPresenter;
    // does not contain changes made in this editor, only for internal use
    private AndTriplets andTriplets;

    public AndTripletsEditorPresenter(
            VariableManager variableManager,
            MappingEditorContract.Presenter<MappingEditorContract.View> mappingEditorPresenter,
            AndTriplets andTriplets) {
        this.variableManager = variableManager;
        this.mappingEditorPresenter = mappingEditorPresenter;
        this.andTriplets = andTriplets;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        showTriplets();
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
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        return new AndTriplets(triplets);
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

    private void showTriplets() {
        if (!andTriplets.getTriplets().isEmpty()) {
            view.clearContent();
            andTriplets
                    .getTriplets()
                    .forEach(
                            triplet ->
                                    view.addNewTripletView(
                                            new TripletPresenter(this, Optional.of(triplet))));
        }
    }

    public boolean hasIncompleteTriplets() {
        return view.getTripletPresenters().stream().anyMatch(TripletPresenter::isIncomplete);
    }

    public void highlightIncompleteTriplets() {
        view.getTripletPresenters().stream().forEach(TripletPresenter::highlightIncompleteParts);
    }
}
