package org.archcnl.ui.input.mappingeditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.mappingeditor.exceptions.MappingAlreadyExistsException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private Optional<CustomRelation> relation;

    public RelationEditorPresenter() {
        super(extractAndTriplets(null));
        this.relation = Optional.ofNullable(null);
    }

    public RelationEditorPresenter(CustomRelation relation) {
        super(extractAndTriplets(relation));
        this.relation = Optional.of(relation);
    }

    private static List<AndTriplets> extractAndTriplets(CustomRelation relation) {
        if (relation == null || relation.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return relation.getMapping().get().getWhenTriplets();
        }
    }

    @Override
    protected void updateMappingName(String newName) throws MappingAlreadyExistsException {
        if (relation.isEmpty()) {
            relation = Optional.of(new CustomRelation(newName));
        } else {
            try {
                relation.get().changeName(newName);
            } catch (RelationAlreadyExistsException e) {
                if (!newName.equals(relation.get().getName())) {
                    throw new MappingAlreadyExistsException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void doneButtonClicked(InputContract.Remote inputRemote) {
        if (relation.isPresent()) {
            try {
                RelationMapping mapping =
                        new RelationMapping(
                                view.getThenTripletSubject(),
                                view.getThenTripletObject().get(),
                                getAndTriplets(),
                                relation.get());
                relation.get().setMapping(mapping);
                if (!RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .doesRelationExist(relation.get())) {
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .addRelation(relation.get());
                }
                inputRemote.switchToArchitectureRulesView();
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

    @Override
    protected void initInfoFieldAndThenTriplet() {
        if (relation.isPresent()) {
            view.updateNameField(relation.get().getName());
            view.updateNameFieldInThenTriplet(relation.get().getName());
            relation.get()
                    .getMapping()
                    .ifPresent(
                            mapping -> {
                                view.setSubjectInThenTriplet(mapping.getThenTriplet().getSubject());
                                view.setObjectInThenTriplet(mapping.getThenTriplet().getObject());
                            });
        }
    }
}
