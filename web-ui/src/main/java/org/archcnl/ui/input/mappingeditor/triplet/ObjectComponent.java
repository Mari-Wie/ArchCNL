package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.Optional;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class ObjectComponent extends HorizontalLayout {

    private static final long serialVersionUID = -1105253743414019620L;
    private ConceptSelectionView conceptSelectionView;
    private VariableStringBoolSelectionView variableStringBoolSelectionView;
    private VariableManager variableManager;

    public ObjectComponent(VariableManager variableManager) {
        this.variableManager = variableManager;
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    public void switchToConceptView() {
        clearView();
        conceptSelectionView = new ConceptSelectionView();
        add(conceptSelectionView);
    }

    public void switchToVariableStringBooleanView(
            VariableManager variableManager, boolean stringsAllowed, boolean booleansAllowed) {
        clearView();
        variableStringBoolSelectionView =
                new VariableStringBoolSelectionView(
                        variableManager,
                        stringsAllowed,
                        booleansAllowed,
                        Optional.ofNullable(null));
        add(variableStringBoolSelectionView);
    }

    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (conceptSelectionView != null) {
            object = conceptSelectionView.getObject();
        } else if (variableStringBoolSelectionView != null) {
            object = variableStringBoolSelectionView.getObject();
        } else {
            throw new ObjectNotDefinedException();
        }
        return object;
    }

    public void setObject(ObjectType object) throws PredicateCannotRelateToObjectException {
        if (conceptSelectionView != null && object instanceof Concept) {
            conceptSelectionView.setObject((Concept) object);
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
                switchToVariableStringBooleanView(variableManager, stringsAllowed, booleansAllowed);
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
        if (conceptSelectionView != null) {
            conceptSelectionView.showErrorMessage(errorMessage);
        } else if (variableStringBoolSelectionView != null) {
            variableStringBoolSelectionView.showErrorMessage(errorMessage);
        }
        // there is no need to show the errorMessage when both views are null
        // as in that case the predicate is not set and the actual error message
        // is shown there
    }

    private void clearView() {
        removeAll();
        conceptSelectionView = null;
        variableStringBoolSelectionView = null;
    }
}