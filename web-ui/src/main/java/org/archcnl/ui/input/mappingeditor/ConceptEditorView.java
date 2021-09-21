package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectView;

public class ConceptEditorView extends MappingEditorView {

    private static final long serialVersionUID = 1260768304754207599L;
    private TextField conceptNameField;

    public ConceptEditorView(MappingEditorContract.Presenter<View> presenter, InputView parent) {
        super(presenter, parent, "Concept");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        SubjectPresenter subjectPresenter = new SubjectPresenter(presenter.getVariableManager());
        thenTriplet.add(new SubjectView(subjectPresenter));
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
}
