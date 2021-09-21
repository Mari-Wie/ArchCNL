package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectView extends HorizontalLayout implements ObjectContract.View {

    private static final long serialVersionUID = -1105253743414019620L;
    private Presenter<View> presenter;
    private ConceptSelectionView conceptSelectionView;
    private VariableStringBoolSelectionView variableStringBoolSelectionView;

    public ObjectView(ObjectContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    private void resetViews() {
        conceptSelectionView = null;
        variableStringBoolSelectionView = null;
    }

    @Override
    public void clearView() {
        removeAll();
        resetViews();
    }

    @Override
    public void switchToConceptView() {
        clearView();
        conceptSelectionView = new ConceptSelectionView();
        add(conceptSelectionView);
    }

    @Override
    public void switchToVariableStringBooleanView(
            VariableManager variableManager, boolean stringsAllowed, boolean booleansAllowed) {
        clearView();
        variableStringBoolSelectionView =
                new VariableStringBoolSelectionView(
                        variableManager, stringsAllowed, booleansAllowed);
        add(variableStringBoolSelectionView);
    }

    @Override
    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    VariableDoesNotExistException, SubjectNotDefinedException {
        ObjectType object;
        if (conceptSelectionView != null) {
            object = conceptSelectionView.getObject();
        } else {
            object = variableStringBoolSelectionView.getObject();
        }
        return object;
    }
}
