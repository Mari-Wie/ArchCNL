package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.VariableSelectionComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.VariableStringBoolSelectionView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableCreationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableFilterChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;
    private TextField relationNameField;
    private VariableSelectionComponent subjectComponent;
    private VariableStringBoolSelectionView objectView;

    public RelationEditorView(AndTripletsEditorView emptyAndTripletsView) {
        super("Relation", emptyAndTripletsView);
    }

    @Override
    protected void addThenTripletView() {
        subjectComponent = new VariableSelectionComponent();
        subjectComponent.setLabel("Subject");
        addListenersToSubjectComponent();
        relationNameField = new TextField("Predicate");
        relationNameField.setReadOnly(true);
        objectView = new VariableStringBoolSelectionView();
        objectView.setLabel("Object");
        addListenersToObjectView();
        HorizontalLayout thenTriplet = new HorizontalLayout();
        thenTriplet.add(subjectComponent, relationNameField, objectView);
        add(thenTriplet);
    }

    private void addListenersToSubjectComponent() {
        subjectComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void addListenersToObjectView() {
        objectView.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        objectView.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        objectView.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
    }

    @Override
    public void updateNameFieldInThenTriplet(String newName) {
        relationNameField.setValue(newName);
    }

    public Variable getThenTripletSubject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return subjectComponent.getVariable();
    }

    public ObjectType getThenTripletObject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return objectView.getObject();
    }

    public void showThenSubjectOrObjectErrorMessage(String message) {
        try {
            subjectComponent.getVariable();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
            subjectComponent.showErrorMessage(message);
        }
        try {
            objectView.getObject();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e) {
            objectView.showErrorMessage(message);
        }
    }

    public void setSubjectInThenTriplet(Variable subject) {
        subjectComponent.setVariable(subject);
    }

    public void setObjectInThenTriplet(ObjectType object) {
        objectView.setObject(object);
    }

    public ObjectType getSelectedObjectTypeInThenTriplet() {
        return objectView.getSelectedObjectType();
    }
}