package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.RelationEditorPresenter;

public class AddCustomRelationRequestedEvent extends ComponentEvent<RelationEditorPresenter> {

    private static final long serialVersionUID = -937210551886454814L;
    private CustomRelation relation;
    private RelationMapping mapping;

    public AddCustomRelationRequestedEvent(
            RelationEditorPresenter source,
            boolean fromClient,
            CustomRelation relation,
            RelationMapping mapping) {
        super(source, fromClient);
        this.relation = relation;
        this.mapping = mapping;
    }

    public void handleEvent(RelationManager relationManager, ConceptManager conceptManager) {
        try {
            relation.setMapping(mapping, conceptManager);
        } catch (UnrelatedMappingException e) {
            throw new RuntimeException(e.getMessage());
        }
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
