package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.VariableStringBoolSelectionView;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;
    private TextField relationNameField;
    private VariableSelectionComponent subjectComponent;
    private VariableStringBoolSelectionView objectView;

    public RelationEditorView(AndTripletsEditorView emptyAndTripletsView) {
        super("Relation", emptyAndTripletsView);
        mappingNameField.setPlaceholder("e.g. hasName");
        mappingNameField.setPattern("[a-z][a-zA-Z]*");
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

    public Variable getThenTripletSubject() throws SubjectOrObjectNotDefinedException {
        return subjectComponent.getVariable();
    }

    public ObjectType getThenTripletObject() throws SubjectOrObjectNotDefinedException {
        return objectView.getObject();
    }

    public void showThenSubjectOrObjectErrorMessage(String message) {
        try {
            subjectComponent.getVariable();
        } catch (SubjectOrObjectNotDefinedException e) {
            subjectComponent.showErrorMessage(message);
        }
        try {
            objectView.getObject();
        } catch (SubjectOrObjectNotDefinedException e) {
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
