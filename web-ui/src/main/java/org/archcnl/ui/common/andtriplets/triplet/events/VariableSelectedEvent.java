package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;

public class VariableSelectedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -1969439375456440034L;
    private AndTripletsEditorPresenter andTripletsPresenter;

    public VariableSelectedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void setAndTripletsPresenter(AndTripletsEditorPresenter andTripletsPresenter) {
        this.andTripletsPresenter = andTripletsPresenter;
    }

    public void handleEvent(final ConceptManager conceptManager) {
        VariableManager variableManager = new VariableManager();
        List<Variable> conflictingVariables =
                variableManager.getConflictingVariables(
                        andTripletsPresenter.getAndTriplets(), conceptManager);
        andTripletsPresenter.showConflictingDynamicTypes(conflictingVariables);
    }
}
