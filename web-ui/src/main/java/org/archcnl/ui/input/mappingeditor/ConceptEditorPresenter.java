package org.archcnl.ui.input.mappingeditor;

import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class ConceptEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = 1636256374259524105L;
    private CustomConcept concept;

    public ConceptEditorPresenter() {
        this.concept = null;
    }

    public ConceptEditorPresenter(CustomConcept concept) {
        this.concept = concept;
        // TODO: import mapping
    }

    @Override
    public void doneButtonClicked(InputView parent) {
        if (concept != null) {
            try {
                ConceptMapping mapping =
                        new ConceptMapping(view.getThenTripletSubject(), getAndTriplets(), concept);
                concept.setMapping(mapping);
                if (!RulesConceptsAndRelations.getInstance()
                        .getConceptManager()
                        .doesConceptExist(concept)) {
                    RulesConceptsAndRelations.getInstance().getConceptManager().addConcept(concept);
                }
                parent.switchToArchitectureRulesView();
            } catch (UnrelatedMappingException
                    | UnsupportedObjectTypeInTriplet
                    | RelationDoesNotExistException
                    | ConceptAlreadyExistsException
                    | InvalidVariableNameException e) {
                // not possible/fatal
                throw new RuntimeException();
            } catch (SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectErrorMessage("Setting a subject is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    @Override
    protected void updateMappingName(String newName) throws ConceptAlreadyExistsException {
        if (concept == null) {
            concept = new CustomConcept(newName);
        } else {
            concept.changeName(newName);
        }
    }
}
