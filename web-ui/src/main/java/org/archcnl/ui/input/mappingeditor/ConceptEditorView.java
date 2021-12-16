package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;
    private TextField conceptNameField;
    private VariableSelectionComponent subjectComponent;

    public ConceptEditorView(AndTripletsEditorView emptyAndTripletsView) {
        super("Concept", emptyAndTripletsView);
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        subjectComponent = new VariableSelectionComponent();
        subjectComponent.setLabel("Subject");
        addListenersToSubjectComponent();
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

    public void showThenSubjectErrorMessage(String message) {
        subjectComponent.setErrorMessage(message);
        subjectComponent.setInvalid(true);
    }

    public Variable getThenTripletSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return subjectComponent.getVariable();
    }

    public void setSubjectInThenTriplet(Variable subject) {
        subjectComponent.setVariable(subject);
    }

    private void addListenersToSubjectComponent() {
        subjectComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
    }
}
