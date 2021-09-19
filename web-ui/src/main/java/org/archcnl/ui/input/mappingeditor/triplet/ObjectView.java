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
    private BooleanSelectionView booleanSelectionView;
    private StringSelectionView stringSelectionView;
    private VariableSelectionView variableSelectionView;
    private VariableSelectionPresenter variableSelectionPresenter;
    private VariableManager variableManager;

    public ObjectView(ObjectContract.Presenter<View> presenter, VariableManager variableManager) {
        this.presenter = presenter;
        this.presenter.setView(this);
        this.variableManager = variableManager;
    }

    private void resetViews() {
        conceptSelectionView = null;
        booleanSelectionView = null;
        stringSelectionView = null;
        variableSelectionView = null;
        variableSelectionPresenter = null;
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
    public void switchToVariableView() {
        clearView();
        variableSelectionPresenter = new VariableSelectionPresenter(variableManager);
        variableSelectionView = new VariableSelectionView(variableSelectionPresenter);
        add(variableSelectionView);
    }

    @Override
    public void switchToBooleanView() {
        clearView();
        booleanSelectionView = new BooleanSelectionView();
        add(booleanSelectionView);
    }

    @Override
    public void switchToStringView() {
        clearView();
        stringSelectionView = new StringSelectionView();
        add(stringSelectionView);
    }

    @Override
    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    VariableDoesNotExistException, SubjectNotDefinedException {
        ObjectType object;
        if (conceptSelectionView != null) {
            object = conceptSelectionView.getObject();
        } else if (booleanSelectionView != null) {
            object = booleanSelectionView.getObject();
        } else if (stringSelectionView != null) {
            object = stringSelectionView.getObject();
        } else if (variableSelectionView != null && variableSelectionPresenter != null) {
            object = variableSelectionPresenter.getSelectedVariable();
        } else {
            // should never happen
            throw new RuntimeException("ObjectView implementation is faulty.");
        }
        return object;
    }
}
