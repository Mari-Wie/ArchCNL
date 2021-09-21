package org.archcnl.ui.input.mappingeditor;

import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private CustomRelation relation;

    public RelationEditorPresenter() {
        this.relation = null;
    }

    public RelationEditorPresenter(CustomRelation relation) {
        this.relation = relation;
        // TODO: import mapping
    }

    @Override
    protected void updateMappingName(String newName)
            throws ConceptAlreadyExistsException, RelationAlreadyExistsException {
        if (relation == null) {
            relation = new CustomRelation(newName);
        } else {
            relation.changeName(newName);
        }
    }

    @Override
    public void doneButtonClicked(InputView parent) {
        if (relation != null) {
            try {
                RelationMapping mapping =
                        new RelationMapping(
                                view.getThenTripletSubject(),
                                view.getThenTripletObject().get(),
                                getAndTriplets(),
                                relation);
                relation.setMapping(mapping);
                if (!RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .doesRelationExist(relation)) {
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .addRelation(relation);
                }
                parent.switchToArchitectureRulesView();
            } catch (UnrelatedMappingException
                    | UnsupportedObjectTypeInTriplet
                    | InvalidVariableNameException
                    | RelationAlreadyExistsException e) {
                // not possible/fatal
                throw new RuntimeException();
            } catch (SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectOrObjectErrorMessage("Setting this is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }
}
