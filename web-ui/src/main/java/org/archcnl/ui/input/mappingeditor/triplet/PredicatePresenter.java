package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.Relation;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicatePresenter implements Presenter<View> {

    private static final long serialVersionUID = 6266956055576570360L;
    private View view;

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
}
