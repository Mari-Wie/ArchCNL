package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.ConceptEditorPresenter;

public class ChangeConceptNameRequestedEvent extends ComponentEvent<ConceptEditorPresenter> {

    private static final long serialVersionUID = -7623251446832259935L;
    private CustomConcept concept;
    private String newName;

    public ChangeConceptNameRequestedEvent(
            ConceptEditorPresenter source,
            boolean fromClient,
            CustomConcept concept,
            String newName) {
        super(source, fromClient);
        this.concept = concept;
        this.newName = newName;
    }

    public void handleEvent(ConceptManager conceptManager) {
        try {
            concept.changeName(newName, conceptManager);
        } catch (final ConceptAlreadyExistsException e) {
            if (!newName.equals(concept.getName())) {
                getSource().showNameAlreadyTakenErrorMessage();
            }
        }
    }
}
