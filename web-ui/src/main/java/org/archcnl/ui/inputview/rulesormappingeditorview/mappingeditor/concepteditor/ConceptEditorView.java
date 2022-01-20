package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;
    private TextField conceptNameField;
    private VariableSelectionComponent subjectComponent;

    public ConceptEditorView(AndTripletsEditorView emptyAndTripletsView) {
        super("Concept", emptyAndTripletsView);
    }

    @Override
    protected void addThenTripletView() {
        subjectComponent = new VariableSelectionComponent();
        subjectComponent.setLabel("Subject");
        addListenersToSubjectComponent();
        TextField predicateField = new TextField("Predicate");
        predicateField.setValue("is-of-type");
        predicateField.setReadOnly(true);
        conceptNameField = new TextField("Object");
        conceptNameField.setReadOnly(true);
        HorizontalLayout thenTriplet = new HorizontalLayout();
        thenTriplet.add(subjectComponent, predicateField, conceptNameField);
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
