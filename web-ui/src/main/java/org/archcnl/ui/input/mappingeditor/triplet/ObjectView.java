package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.input.mappingeditor.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.ConceptSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class ObjectView extends HorizontalLayout {

    private static final long serialVersionUID = -1105253743414019620L;
    private ConceptSelectionComponent conceptSelectionComponent;
    private VariableStringBoolSelectionView variableStringBoolSelectionView;

    public void switchToConceptView() {
        clearView();
        conceptSelectionComponent = new ConceptSelectionComponent();
        conceptSelectionComponent.addListener(
                ConceptListUpdateRequestedEvent.class, this::fireEvent);
        conceptSelectionComponent.addListener(ConceptSelectedEvent.class, this::fireEvent);
        add(conceptSelectionComponent);
    }

    public void switchToVariableStringBooleanView(boolean stringsAllowed, boolean booleansAllowed) {
        clearView();
        variableStringBoolSelectionView =
                new VariableStringBoolSelectionView(stringsAllowed, booleansAllowed);
        variableStringBoolSelectionView.addListener(
                VariableFilterChangedEvent.class, this::fireEvent);
        variableStringBoolSelectionView.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableStringBoolSelectionView.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
        add(variableStringBoolSelectionView);
    }

    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (conceptSelectionComponent != null
                && conceptSelectionComponent.getSelectedItem().isPresent()) {
            String conceptName = conceptSelectionComponent.getSelectedItem().get();
            // TODO: The RulesConceptsAndRelations call does not belong here
            object =
                    RulesConceptsAndRelations.getInstance()
                            .getConceptManager()
                            .getConceptByName(conceptName);
        } else if (variableStringBoolSelectionView != null) {
            object = variableStringBoolSelectionView.getObject();
        } else {
            throw new ObjectNotDefinedException();
        }
        return object;
    }

    public void setObject(ObjectType object) throws PredicateCannotRelateToObjectException {
        if (conceptSelectionComponent != null && object instanceof Concept) {
            conceptSelectionComponent.setObject((Concept) object);
        } else if (variableStringBoolSelectionView != null) {
            variableStringBoolSelectionView.setObject(object);
        } else {
            throw new PredicateCannotRelateToObjectException(object);
        }
    }

    public void predicateHasChanged(Optional<Relation> relationOptional) {
        if (relationOptional.isEmpty()) {
            clearView();
        } else {
            Relation relation = relationOptional.orElseThrow();
            if (relation instanceof TypeRelation) {
                switchToConceptView();
            } else {
                boolean stringsAllowed = relation.canRelateToObjectType(new StringValue(""));
                boolean booleansAllowed = relation.canRelateToObjectType(new BooleanValue(false));
                switchToVariableStringBooleanView(stringsAllowed, booleansAllowed);
            }
        }
    }

    public void highlightWhenEmpty() {
        try {
            getObject();
        } catch (ConceptDoesNotExistException e) {
            showErrorMessage("Concept does not exist");
        } catch (ObjectNotDefinedException | SubjectOrObjectNotDefinedException e) {
            showErrorMessage("Object not set");
        } catch (InvalidVariableNameException e) {
            showErrorMessage("Invalid Variable name");
        }
    }

    private void showErrorMessage(String errorMessage) {
        if (conceptSelectionComponent != null) {
            conceptSelectionComponent.showErrorMessage(errorMessage);
        } else if (variableStringBoolSelectionView != null) {
            variableStringBoolSelectionView.showErrorMessage(errorMessage);
        }
        // there is no need to show the errorMessage when both views are null
        // as in that case the predicate is not set and the actual error message
        // is shown there
    }

    private void clearView() {
        removeAll();
        conceptSelectionComponent = null;
        variableStringBoolSelectionView = null;
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
