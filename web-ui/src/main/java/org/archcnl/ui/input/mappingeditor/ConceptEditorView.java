package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectView;

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;
    private TextField conceptNameField;
    private SubjectPresenter subjectPresenter;
    private SubjectView subjectView;

    public ConceptEditorView(
            MappingEditorContract.Presenter<View> presenter, InputContract.Remote inputRemote) {
        super(presenter, inputRemote, "Concept");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        subjectPresenter = new SubjectPresenter(presenter.getVariableManager());
        subjectView = new SubjectView(subjectPresenter);
        thenTriplet.add(subjectView);
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
        return subjectPresenter.getSubject();
    }

    @Override
    public Optional<ObjectType> getThenTripletObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showThenSubjectErrorMessage(String message) {
        subjectView.setErrorMessage(message);
        subjectView.setInvalid(true);
    }

    @Override
    public void showThenSubjectOrObjectErrorMessage(String message) {
        showThenSubjectErrorMessage(message);
    }

    @Override
    public void setSubjectInThenTriplet(Variable subject) {
        subjectPresenter.setSubject(subject);
    }

    @Override
    public void setObjectInThenTriplet(ObjectType object) {
        throw new UnsupportedOperationException(
                "The object in the \"then\" part of a concept cannot be changed.");
    }
}
