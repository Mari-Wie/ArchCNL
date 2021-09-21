package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectView;
import org.archcnl.ui.input.mappingeditor.triplet.VariableStringBoolSelectionView;

public class RelationEditorView extends MappingEditorView {

    private static final long serialVersionUID = -335119786400292325L;
    private TextField relationNameField;

    public RelationEditorView(MappingEditorContract.Presenter<View> presenter, InputView parent) {
        super(presenter, parent, "Relation");
    }

    @Override
    protected void addThenTripletView() {
        HorizontalLayout thenTriplet = new HorizontalLayout();
        SubjectPresenter subjectPresenter = new SubjectPresenter(presenter.getVariableManager());
        thenTriplet.add(new SubjectView(subjectPresenter));
        relationNameField = new TextField("Predicate");
        relationNameField.setReadOnly(true);
        thenTriplet.add(relationNameField);
        VariableStringBoolSelectionView variableStringBoolSelectionView =
                new VariableStringBoolSelectionView(presenter.getVariableManager(), true, true);
        thenTriplet.add(variableStringBoolSelectionView);
        add(thenTriplet);
    }

    @Override
    public void updateNameFieldInThenTriplet(String newName) {
        relationNameField.setValue(newName);
    }
}
