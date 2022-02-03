package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.RelationEditorPresenter;

public class AddCustomRelationRequestedEvent extends ComponentEvent<RelationEditorPresenter> {

    private static final long serialVersionUID = -937210551886454814L;
    private CustomRelation relation;

    public AddCustomRelationRequestedEvent(
            RelationEditorPresenter source, boolean fromClient, CustomRelation relation) {
        super(source, fromClient);
        this.relation = relation;
    }

    public void handleEvent(RelationManager relationManager) {
        if (!relationManager.doesRelationExist(relation)) {
            try {
                relationManager.addToParent(relation, "Custom Relations");
            } catch (RelationAlreadyExistsException e) {
                new RuntimeException(
                        "Adding of relation " + relation.getName() + " failed unexpectedly.");
            }
        }
    }
}
