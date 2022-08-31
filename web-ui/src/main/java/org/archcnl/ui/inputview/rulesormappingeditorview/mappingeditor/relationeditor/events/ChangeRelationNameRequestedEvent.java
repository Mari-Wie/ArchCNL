package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.RelationEditorPresenter;

public class ChangeRelationNameRequestedEvent extends ComponentEvent<RelationEditorPresenter> {

    private static final long serialVersionUID = 470267733288498594L;
    private CustomRelation relation;
    private String newName;

    public ChangeRelationNameRequestedEvent(
            RelationEditorPresenter source,
            boolean fromClient,
            CustomRelation relation,
            String newName) {
        super(source, fromClient);
        this.relation = relation;
        this.newName = newName;
    }

    public void handleEvent(RelationManager relationManager) {
        try {
            relationManager.updateName(relation.getName(), newName);
        } catch (final RelationAlreadyExistsException e) {
            getSource().showNameAlreadyTakenErrorMessage();
        }
    }
}
