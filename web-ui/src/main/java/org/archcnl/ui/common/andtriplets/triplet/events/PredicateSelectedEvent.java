package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.ObjectView;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateSelectionComponent> {

    private static final long serialVersionUID = 1L;
    private ObjectView objectView;
    private AndTripletsEditorPresenter andTripletsPresenter;

    public PredicateSelectedEvent(
            final PredicateSelectionComponent source, final boolean fromClient) {
        super(source, fromClient);
    }

    public void setObjectView(ObjectView objectView) {
        this.objectView = objectView;
    }

    public void setAndTripletsPresenter(AndTripletsEditorPresenter andTripletsPresenter) {
        this.andTripletsPresenter = andTripletsPresenter;
    }

    public void handleEvent(
            final RelationManager relationManager, final ConceptManager conceptManager) {
        VariableManager variableManager = new VariableManager();
        List<Variable> conflictingVariables =
                variableManager.getConflictingVariables(
                        andTripletsPresenter.getAndTriplets(), conceptManager);
        andTripletsPresenter.showConflictingDynamicTypes(conflictingVariables);

        final Optional<String> value = getSource().getSelectedItem();
        Optional<Relation> relation = Optional.empty();
        if (value.isPresent()) {
            relation = relationManager.getRelationByName(value.get());
            if (relation.isEmpty()) {
                getSource().showErrorMessage("Relation does not exist");
            } else {
                getSource().setInternalRelation(relation.get());
            }
        }
        objectView.predicateHasChanged(relation);
    }
}
