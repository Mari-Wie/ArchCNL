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

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;
    private TextField conceptNameField;
    private SubjectComponent subjectComponent;

    public ConceptEditorView(
            MappingEditorContract.Presenter<View> presenter, InputContract.Remote inputRemote) {
        super(presenter, inputRemote, "Concept");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        subjectComponent = new SubjectComponent(presenter.getVariableManager());
        subjectComponent.setLabel("Subject");
        thenTriplet.add(subjectComponent);
        TextField predicateField = new TextField("Predicate");
        predicateField.setValue("is-of-type");
        predicateField.setReadOnly(true);
        thenTriplet.add(predicateField);
        conceptNameField = new TextField("Object");
        conceptNameField.setReadOnly(true);
        thenTriplet.add(conceptNameField);
        add(thenTriplet);
    }

    @Override
    public void updateNameFieldInThenTriplet(String newName) {
        conceptNameField.setValue(newName);
    }

    @Override
    public Variable getThenTripletSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return subjectComponent.getSubject();
    }

    @Override
    public Optional<ObjectType> getThenTripletObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showThenSubjectErrorMessage(String message) {
        subjectComponent.setErrorMessage(message);
        subjectComponent.setInvalid(true);
    }

    @Override
    public void showThenSubjectOrObjectErrorMessage(String message) {
        showThenSubjectErrorMessage(message);
    }

    @Override
    public void setSubjectInThenTriplet(Variable subject) {
        subjectComponent.setSubject(subject);
    }

    @Override
    public void setObjectInThenTriplet(ObjectType object) {
        throw new UnsupportedOperationException(
                "The object in the \"then\" part of a concept cannot be changed.");
    }

    @Override
    public ObjectType getSelectedObjectTypeInThenTriplet() {
        throw new UnsupportedOperationException(
                "The selected ObjectType cannot change for a Concept.");
    }
}
