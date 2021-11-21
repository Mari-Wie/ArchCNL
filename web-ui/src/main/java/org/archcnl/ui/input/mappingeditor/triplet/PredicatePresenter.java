package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicatePresenter implements Presenter<View> {

    private static final long serialVersionUID = 6266956055576570360L;
    private View view;
    private ObjectPresenter objectPresenter;

    public PredicatePresenter(ObjectPresenter objectPresenter) {
        this.objectPresenter = objectPresenter;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public List<String> getRelationNames() {
        return RulesConceptsAndRelations.getInstance().getRelationManager().getRelations().stream()
                .map(Relation::getName)
                .collect(Collectors.toList());
    }

    public Relation getPredicate()
            throws RelationDoesNotExistException, RelationNotDefinedException {
        String relationName = view.getSelectedItem().orElseThrow(RelationNotDefinedException::new);
        return RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .getRelationByName(relationName);
    }

    public void setPredicate(Relation predicate) {
        view.setItem(predicate.getName());
    }

    @Override
    public void handleDropEvent(Object data) {
        if (data instanceof Relation) {
            Relation relation = (Relation) data;
            view.setItem(relation.getName());
        } else {
            view.showErrorMessage("Not a Relation");
        }
    }

    @Override
    public void valueHasChanged() {
        Relation relation = null;
        try {
            String newValue = view.getSelectedItem().orElseThrow(NoSuchElementException::new);
            relation =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getRelationByName(newValue);
        } catch (RelationDoesNotExistException | NoSuchElementException e) {
            // leave relation == null
        }
        objectPresenter.predicateHasChanged(Optional.ofNullable(relation));
    }

    public void highlightWhenEmpty() {
        try {
            getPredicate();
        } catch (RelationDoesNotExistException e) {
            view.showErrorMessage("Relation does not exist");
        } catch (RelationNotDefinedException e) {
            view.showErrorMessage("Predicate not set");
        }
    }
}
