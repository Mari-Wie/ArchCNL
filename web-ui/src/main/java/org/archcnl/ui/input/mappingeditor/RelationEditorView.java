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
import org.archcnl.ui.input.mappingeditor.triplet.SubjectComponent;
import org.archcnl.ui.input.mappingeditor.triplet.VariableStringBoolSelectionView;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;
    private TextField relationNameField;
    private SubjectComponent subjectComponent;
    private VariableStringBoolSelectionView objectView;

    public RelationEditorView(
            MappingEditorContract.Presenter<View> presenter, InputContract.Remote inputRemote) {
        super(presenter, inputRemote, "Relation");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        subjectComponent = new SubjectComponent(presenter.getVariableManager());
        subjectComponent.setLabel("Subject");
        thenTriplet.add(subjectComponent);
        relationNameField = new TextField("Predicate");
        relationNameField.setReadOnly(true);
        thenTriplet.add(relationNameField);
        objectView =
                new VariableStringBoolSelectionView(presenter.getVariableManager(), true, true);
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
        return new Variable(subjectComponent.getValue());
    }

    @Override
    public Optional<ObjectType> getThenTripletObject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return Optional.of(objectView.getObject());
    }

    @Override
    public void showThenSubjectErrorMessage(String message) {
        subjectComponent.setErrorMessage(message);
        subjectComponent.setInvalid(true);
    }

    @Override
    public void showThenSubjectOrObjectErrorMessage(String message) {
        // TODO Fix
        // try {
        //    subjectComponent.getSubject();
        // } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
        //    showThenSubjectErrorMessage(message);
        // }
        // try {
        //    objectView.getObject();
        // } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
        //    objectView.showErrorMessage(message);
        // }
    }

    @Override
    public void setSubjectInThenTriplet(Variable subject) {
        // TODO: remove
        // subjectComponent.setSubject(subject);
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
