package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.ConceptEditorPresenter;

public class AddCustomConceptRequestedEvent extends ComponentEvent<ConceptEditorPresenter> {

    private static final long serialVersionUID = -7878722422837601581L;
    private CustomConcept concept;

    public AddCustomConceptRequestedEvent(
            ConceptEditorPresenter source, boolean fromClient, CustomConcept concept) {
        super(source, fromClient);
        this.concept = concept;
    }

    public void handleEvent(ConceptManager conceptManager) {
        if (!conceptManager.doesConceptExist(concept)) {
            try {
                conceptManager.addToParent(concept, "Custom Concepts");
            } catch (ConceptAlreadyExistsException e) {
                new RuntimeException(
                        "Adding of concept " + concept.getName() + " failed unexpectedly.");
            }
        }
    }
}
