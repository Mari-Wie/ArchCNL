package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Optional;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectView;
import org.archcnl.ui.input.mappingeditor.triplet.VariableStringBoolSelectionView;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;
    private TextField relationNameField;
    private SubjectPresenter subjectPresenter;
    private SubjectView subjectView;
    private VariableStringBoolSelectionView objectView;

    public RelationEditorView(
            MappingEditorContract.Presenter<View> presenter, InputContract.Remote inputRemote) {
        super(presenter, inputRemote, "Relation");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        subjectPresenter = new SubjectPresenter(presenter.getVariableManager());
        subjectView = new SubjectView(subjectPresenter);
        thenTriplet.add(subjectView);
        relationNameField = new TextField("Predicate");
        relationNameField.setReadOnly(true);
        thenTriplet.add(relationNameField);
        objectView =
                new VariableStringBoolSelectionView(
                        presenter.getVariableManager(), true, true, Optional.of(presenter));
        thenTriplet.add(objectView);
        add(thenTriplet);
    }

    @Override
    public void updateNameFieldInThenTriplet(String newName) {
        relationNameField.setValue(newName);
    }

    @Override
    public Variable getThenTripletSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return subjectPresenter.getSubject();
    }

    @Override
    public Optional<ObjectType> getThenTripletObject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return Optional.of(objectView.getObject());
    }

    @Override
    public void showThenSubjectErrorMessage(String message) {
        subjectView.setErrorMessage(message);
        subjectView.setInvalid(true);
    }

    @Override
    public void showThenSubjectOrObjectErrorMessage(String message) {
        try {
            subjectPresenter.getSubject();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
            showThenSubjectErrorMessage(message);
        }
        try {
            objectView.getObject();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
            objectView.showErrorMessage(message);
        }
    }

    @Override
    public void setSubjectInThenTriplet(Variable subject) {
        subjectPresenter.setSubject(subject);
    }

    @Override
    public void setObjectInThenTriplet(ObjectType object) {
        objectView.setObject(object);
    }

    @Override
    public ObjectType getSelectedObjectTypeInThenTriplet() {
        return objectView.getSelectedObjectType();
    }
}
