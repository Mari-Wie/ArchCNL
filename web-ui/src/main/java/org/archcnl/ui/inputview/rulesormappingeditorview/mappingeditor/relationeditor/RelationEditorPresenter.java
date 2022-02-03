package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.AddCustomRelationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.ChangeRelationNameRequestedEvent;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private RelationEditorView view;
    private Optional<CustomRelation> relation;

    public RelationEditorPresenter() {
        super();
        this.relation = Optional.empty();
        view =
                new RelationEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
        addThenTripletListeners();
        initializeView(view);
    }

    public RelationEditorPresenter(final CustomRelation relation) {
        super();
        this.relation = Optional.of(relation);
        view =
                new RelationEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
        addThenTripletListeners();
        initializeView(view, RelationEditorPresenter.extractAndTriplets(relation));
    }

    private void addThenTripletListeners() {
        view.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private static List<AndTriplets> extractAndTriplets(final CustomRelation relation) {
        if (relation == null || relation.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return relation.getMapping().get().getWhenTriplets();
        }
    }

    // Stub
    @Override
    protected void updateMappingName(final String newName) {
        if (relation.isEmpty()) {
            final List<ActualObjectType> relatableObjectTypes = new LinkedList<>();
            final ObjectType selectedObjectType = view.getSelectedObjectTypeInThenTriplet();
            if (selectedObjectType instanceof ActualObjectType) {
                relatableObjectTypes.add((ActualObjectType) selectedObjectType);
            }
            relation = Optional.of(new CustomRelation(newName, "", relatableObjectTypes));
        } else {
            fireEvent(new ChangeRelationNameRequestedEvent(this, true, relation.get(), newName));
        }
    }

    @Override
    protected void initInfoFieldAndThenTriplet() {
        if (relation.isPresent()) {
            view.updateNameField(relation.get().getName());
            view.updateDescription(relation.get().getDescription());
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

    @Override
    protected void updateMapping() {
        if (relation.isPresent()) {
            try {
                final CustomRelation thenRelation = relation.get();
                final ObjectType thenObject = view.getThenTripletObject();
                thenRelation.setRelatableObjectType(thenObject);
                final Triplet thenTriplet =
                        new Triplet(view.getThenTripletSubject(), thenRelation, thenObject);

                final RelationMapping mapping =
                        new RelationMapping(thenTriplet, getAndTripletsList());

                relation.get().setMapping(mapping);
                fireEvent(new AddCustomRelationRequestedEvent(this, true, relation.get()));

                fireEvent(new RuleEditorRequestedEvent(this, true));
            } catch (UnrelatedMappingException | InvalidVariableNameException e) {
                // not possible/fatal
                throw new RuntimeException(e.getMessage());
            } catch (final SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectOrObjectErrorMessage("Setting this is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    @Override
    public void descriptionHasChanged(final MappingDescriptionFieldChangedEvent event) {
        relation.get().setDescription(event.getNewDescription());
    }
}
